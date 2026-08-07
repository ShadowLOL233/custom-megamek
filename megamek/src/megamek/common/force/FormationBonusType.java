/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License (GPL), version 3 or (at your option) any later version, as published by the Free Software Foundation.
 *
 * MegaMek is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for details.
 */
package megamek.common.force;

import static megamek.common.units.EntityWeightClass.WEIGHT_HEAVY;
import static megamek.common.units.EntityWeightClass.WEIGHT_LIGHT;
import static megamek.common.units.EntityWeightClass.WEIGHT_MEDIUM;
import static megamek.common.units.UnitRole.BRAWLER;
import static megamek.common.units.UnitRole.JUGGERNAUT;
import static megamek.common.units.UnitRole.MISSILE_BOAT;
import static megamek.common.units.UnitRole.SCOUT;
import static megamek.common.units.UnitRole.SKIRMISHER;
import static megamek.common.units.UnitRole.SNIPER;
import static megamek.common.units.UnitRole.STRIKER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import megamek.common.equipment.WeaponMounted;
import megamek.common.game.Game;
import megamek.common.options.IOption;
import megamek.common.options.OptionsConstants;
import megamek.common.units.Entity;
import megamek.common.units.UnitRole;
import megamek.common.weapons.artillery.ArtilleryWeapon;
import megamek.common.weapons.autoCannons.ACWeapon;
import megamek.common.weapons.autoCannons.LBXACWeapon;
import megamek.common.weapons.autoCannons.UACWeapon;

/**
 * Alpha Strike / Campaign Operations <i>Formation Bonuses</i> ported to the Total Warfare engine.
 *
 * <p>A {@link Force} (lance) whose member composition matches a formation type grants free Special Pilot
 * Abilities to (some of) its members, for as long as the formation keeps at least {@link #MIN_ACTIVE_UNITS}
 * active (deployed, undestroyed) units on the board. The granted abilities are <b>existing</b> TW pilot options
 * (e.g. {@link OptionsConstants#GUNNERY_SNIPER}); because the TW rules engine already honors those options, the
 * bonus takes effect with no further wiring.</p>
 *
 * <p>Gated by {@link OptionsConstants#ADVANCED_FORMATION_BONUSES}. {@link #refresh(Game)} runs once per round
 * (INITIATIVE-phase preparation) and re-evaluates every force, granting or revoking abilities as formations
 * qualify or drop below the active-unit threshold. Only abilities this system granted are ever revoked, so a
 * pilot's own SPAs are never disturbed.</p>
 *
 * <p>This enum lists the <b>Tier-1</b> (non-Campaign-Operations-exclusive) AS Commander's Edition ground
 * formations. Composition tests mirror the TW-native thresholds already used for RAT generation in
 * {@code client/ratgenerator/FormationType.java} (weight class + WalkMP/JumpMP + {@link UnitRole} + weapon
 * type), so detection is consistent with the rest of MegaMek. Detection is used both for the gameplay bonus and
 * for lobby <i>display</i> (formation label, bonus description, generated force names).</p>
 *
 * <p>Some AS bonuses map to an existing TW pilot option and are granted directly; others (Speed Demon, the
 * Lucky→Edge conversion, the Anti-Aircraft / Communications-Disruption SCAs, the Command multi-choice) have no
 * clean TW option yet and are <b>display-only</b> for now (empty {@link #grantedAbilities}) — their description
 * still explains the intended effect. The full catalogue, SPA effects and remaining work are documented in
 * {@code development/FORMATION_BONUS_REFERENCE.md}. Aerospace squadrons and the Tier-2 Campaign-Operations-only
 * formations are not yet implemented here.</p>
 */
public enum FormationBonusType {

    // Ordered specific -> generic: detect() returns the first qualifying type, so variants and more distinctive
    // compositions must precede their generic base (e.g. Heavy Battle before Battle, Fire Support before Fire).

    FAST_ASSAULT("Fast Assault", null, List.of(OptionsConstants.GUNNERY_MULTI_TASKER), Share.HALF,
          "As Assault (Multi-Tasker to up to half the lance); additionally up to two units may gain Stand Aside "
                + "(display only).",
          m -> all(m, e -> wcAtLeast(e, WEIGHT_MEDIUM))
                && all(m, e -> e.getTotalOArmor() >= 135 && (walk(e) >= 5 || jump(e) > 0))
                && fraction(m, 0.75, e -> dmgAtRange(e, 7) >= 25)
                && count(m, e -> wcAtLeast(e, WEIGHT_HEAVY)) >= 3
                && (count(m, e -> role(e) == JUGGERNAUT) >= 1 || count(m, e -> role(e) == SNIPER) >= 2)),

    ASSAULT("Assault", JUGGERNAUT, List.of(OptionsConstants.GUNNERY_MULTI_TASKER), Share.HALF,
          "Choose Demoralizer or Multi-Tasker; up to half the lance gains it each turn. Ported: Multi-Tasker to "
                + "up to half the lance.",
          m -> all(m, e -> wcAtLeast(e, WEIGHT_MEDIUM))
                && all(m, e -> e.getTotalOArmor() >= 135)
                && fraction(m, 0.75, e -> dmgAtRange(e, 7) >= 25)
                && count(m, e -> wcAtLeast(e, WEIGHT_HEAVY)) >= 3
                && (count(m, e -> role(e) == JUGGERNAUT) >= 1 || count(m, e -> role(e) == SNIPER) >= 2)),

    ARTILLERY_FIRE("Artillery Fire", null, List.of(OptionsConstants.GUNNERY_OBLIQUE_ARTILLERY), Share.HALF,
          "Up to half the lance gains Oblique Artilleryman (-1 to-hit on indirect / off-board artillery attacks).",
          m -> count(m, FormationBonusType::hasArtilleryWeapon) >= 2),

    ANTI_AIR("Anti-Air", null, List.of(), Share.HALF,
          "Up to half the lance gains Anti-Aircraft Specialists: -2 to-hit vs airborne targets, +1 vs ground "
                + "(display only — no TW option yet).",
          m -> fraction(m, 0.75, e -> roleIsAnyOf(e, SNIPER, MISSILE_BOAT))
                && count(m, FormationBonusType::hasFlakCapableWeapon) >= 2),

    DIRECT_FIRE("Direct Fire", null, List.of(OptionsConstants.GUNNERY_WEAPON_SPECIALIST), Share.HALF,
          "Up to half the lance gains Weapon Specialist (a standard attack that misses by 1 instead deals half "
                + "damage).",
          m -> all(m, e -> wcAtLeast(e, WEIGHT_MEDIUM))
                && all(m, e -> dmgAtRange(e, 18) >= 10)
                && count(m, e -> wcAtLeast(e, WEIGHT_HEAVY)) >= 2),

    FIRE_SUPPORT("Fire Support", null, List.of(OptionsConstants.GUNNERY_OBLIQUE_ATTACKER), Share.HALF,
          "Up to half the lance gains Oblique Attacker (indirect fire -1 to-hit; may fire indirectly without a "
                + "spotter).",
          m -> count(m, FormationBonusType::hasIndirectFireWeapon) >= 3),

    FIRE("Fire", MISSILE_BOAT, List.of(OptionsConstants.GUNNERY_SNIPER), Share.HALF,
          "Up to half the lance gains Sniper (medium/long/extreme to-hit penalties reduced to +1/+2/+3).",
          m -> fraction(m, 0.75, e -> roleIsAnyOf(e, SNIPER, MISSILE_BOAT))),

    LIGHT_RECON("Light Recon", null, List.of(OptionsConstants.MISC_FORWARD_OBSERVER), Share.ALL,
          "Each unit may take a different Recon SPA (Eagle's Eyes / Forward Observer / Maneuvering Ace). Ported: "
                + "Forward Observer to the whole lance.",
          m -> all(m, e -> wcAtMost(e, WEIGHT_LIGHT)) && all(m, e -> walk(e) >= 6 && role(e) == SCOUT)),

    HEAVY_RECON("Heavy Recon", null, List.of(OptionsConstants.MISC_FORWARD_OBSERVER), Share.HALF,
          "Up to half the lance gains the chosen Recon SPA. Ported: Forward Observer to up to half the lance.",
          m -> all(m, e -> wcAtLeast(e, WEIGHT_MEDIUM))
                && all(m, e -> walk(e) >= 4)
                && count(m, e -> walk(e) >= 5) >= 2
                && count(m, e -> role(e) == SCOUT) >= 2
                && count(m, e -> wcAtLeast(e, WEIGHT_HEAVY)) >= 1),

    RECON("Recon", SCOUT, List.of(OptionsConstants.MISC_FORWARD_OBSERVER), Share.ALL,
          "The whole lance gains one of Eagle's Eyes / Forward Observer / Maneuvering Ace. Ported: Forward "
                + "Observer to the whole lance.",
          m -> all(m, e -> walk(e) >= 5) && count(m, e -> roleIsAnyOf(e, SCOUT, STRIKER)) >= 2),

    SWEEP("Sweep", null, List.of(OptionsConstants.GUNNERY_BLOOD_STALKER), Share.THREE_QUARTERS,
          "Three-quarters of the lance gains Blood Stalker (-1 to-hit vs a chosen enemy, +2 vs all others).",
          m -> all(m, e -> wcAtMost(e, WEIGHT_MEDIUM)) && all(m, e -> walk(e) >= 5 && dmgAtRange(e, 6) >= 10)),

    PROBE("Probe", null, List.of(OptionsConstants.GUNNERY_BLOOD_STALKER), Share.THREE_QUARTERS,
          "Three-quarters of the lance gains Blood Stalker (-1 to-hit vs a chosen enemy, +2 vs all others).",
          m -> all(m, e -> wcAtMost(e, WEIGHT_HEAVY))
                && all(m, e -> dmgAtRange(e, 9) >= 10)
                && fraction(m, 0.75, e -> walk(e) >= 6)),

    PURSUIT("Pursuit", STRIKER, List.of(OptionsConstants.GUNNERY_BLOOD_STALKER), Share.THREE_QUARTERS,
          "Three-quarters of the lance gains Blood Stalker (-1 to-hit vs a chosen enemy, +2 vs all others); the "
                + "lance may instead designate an enemy formation.",
          m -> all(m, e -> wcAtMost(e, WEIGHT_MEDIUM))
                && fraction(m, 0.75, e -> walk(e) >= 6)
                && count(m, e -> maxWeaponDmgAtRange(e, 15) >= 5) >= 1),

    HEAVY_STRIKER_CAVALRY("Heavy Striker/Cavalry", null, List.of(), Share.THREE_QUARTERS,
          "Three-quarters of the lance gains Speed Demon (+2\" ground move per turn) (display only — no TW "
                + "option yet).",
          m -> all(m, e -> wcAtLeast(e, WEIGHT_MEDIUM))
                && all(m, e -> walk(e) >= 4)
                && count(m, e -> wcAtLeast(e, WEIGHT_HEAVY)) >= 3
                && count(m, e -> roleIsAnyOf(e, STRIKER, SKIRMISHER)) >= 2
                && count(m, e -> maxWeaponDmgAtRange(e, 18) >= 5) >= 1),

    STRIKER_CAVALRY("Striker/Cavalry", STRIKER, List.of(), Share.THREE_QUARTERS,
          "Three-quarters of the lance gains Speed Demon (+2\" ground move per turn) (display only — no TW "
                + "option yet).",
          m -> all(m, e -> wcAtMost(e, WEIGHT_HEAVY))
                && all(m, e -> walk(e) >= 5 || jump(e) >= 4)
                && fraction(m, 0.5, e -> roleIsAnyOf(e, STRIKER, SKIRMISHER))),

    HEAVY_BATTLE("Heavy Battle", null, List.of(), Share.ALL,
          "The whole lance shares Lucky (level = units + 2) for re-rolls (display only — Edge handling "
                + "pending).",
          m -> all(m, e -> wcAtLeast(e, WEIGHT_MEDIUM)) && fraction(m, 0.5, e -> wcAtLeast(e, WEIGHT_HEAVY))),

    MEDIUM_BATTLE("Medium Battle", null, List.of(), Share.ALL,
          "The whole lance shares Lucky (level = units + 2) for re-rolls (display only — Edge handling "
                + "pending).",
          m -> all(m, e -> wcAtMost(e, WEIGHT_HEAVY)) && fraction(m, 0.5, e -> wcEquals(e, WEIGHT_MEDIUM))),

    LIGHT_BATTLE("Light Battle", null, List.of(), Share.ALL,
          "The whole lance shares Lucky (level = units + 2) for re-rolls (display only — Edge handling "
                + "pending).",
          m -> all(m, e -> wcAtMost(e, WEIGHT_HEAVY))
                && fraction(m, 0.75, e -> wcEquals(e, WEIGHT_LIGHT))
                && count(m, e -> role(e) == SCOUT) >= 1),

    BATTLE("Battle", BRAWLER, List.of(), Share.ALL,
          "The whole lance shares Lucky (level = units + 2) for re-rolls (display only — Edge handling "
                + "pending).",
          m -> fraction(m, 0.5, e -> wcAtLeast(e, WEIGHT_HEAVY))
                && count(m, e -> roleIsAnyOf(e, BRAWLER, SNIPER, SKIRMISHER)) >= 3),

    VEHICLE_COMMAND("Vehicle Command", null, List.of(), Share.HALF,
          "The command unit gains Tactical Genius; half the lance each gains a command SPA (display only — "
                + "pending).",
          m -> fraction(m, 0.5, e -> roleIsAnyOf(e, SNIPER, MISSILE_BOAT, SKIRMISHER, JUGGERNAUT))
                && count(m, e -> roleIsAnyOf(e, BRAWLER, STRIKER, SCOUT)) >= 1
                && count(m, e -> e.hasETypeFlag(Entity.ETYPE_TANK)) >= 2),

    COMMAND("Command", null, List.of(), Share.HALF,
          "The command unit gains Tactical Genius; half the lance each gains a command SPA (display only — "
                + "pending).",
          m -> fraction(m, 0.5, e -> roleIsAnyOf(e, SNIPER, MISSILE_BOAT, SKIRMISHER, JUGGERNAUT))
                && count(m, e -> roleIsAnyOf(e, BRAWLER, STRIKER, SCOUT)) >= 1);

    /** A formation's bonus is inactive unless it keeps at least this many active units on the board. */
    public static final int MIN_ACTIVE_UNITS = 3;

    /** Fraction of a formation's active members that receive the granted abilities. */
    enum Share { ALL, THREE_QUARTERS, HALF }

    private final String displayName;
    private final UnitRole idealRole;
    private final List<String> grantedAbilities;
    private final Share share;
    private final String bonusDescription;
    private final Predicate<List<Entity>> composition;

    FormationBonusType(String displayName, UnitRole idealRole, List<String> grantedAbilities, Share share,
          String bonusDescription, Predicate<List<Entity>> composition) {
        this.displayName = displayName;
        this.idealRole = idealRole;
        this.grantedAbilities = grantedAbilities;
        this.share = share;
        this.bonusDescription = bonusDescription;
        this.composition = composition;
    }

    /**
     * @return a display/ordering priority for this formation: higher = more forward (command &gt; assault &gt; battle
     *       &gt; striker &gt; fire &gt; pursuit &gt; recon). Used e.g. to order a company's lances in the TO&amp;E chart.
     */
    public int sortWeight() {
        return switch (this) {
            case COMMAND, VEHICLE_COMMAND -> 100;
            case FAST_ASSAULT, ASSAULT -> 90;
            case HEAVY_BATTLE -> 82;
            case BATTLE -> 80;
            case MEDIUM_BATTLE -> 76;
            case LIGHT_BATTLE -> 72;
            case HEAVY_STRIKER_CAVALRY -> 66;
            case STRIKER_CAVALRY -> 64;
            case DIRECT_FIRE -> 55;
            case FIRE_SUPPORT -> 50;
            case FIRE -> 48;
            case ANTI_AIR -> 46;
            case ARTILLERY_FIRE -> 44;
            case PROBE -> 36;
            case PURSUIT -> 35;
            case SWEEP -> 34;
            case HEAVY_RECON -> 28;
            case RECON -> 26;
            case LIGHT_RECON -> 24;
        };
    }

    public String getDisplayName() {
        return displayName;
    }

    /** @return a human-readable description of this formation's bonus, for UI display (may be empty). */
    public String getBonusDescription() {
        return bonusDescription;
    }

    public List<String> getGrantedAbilities() {
        return grantedAbilities;
    }

    /** @return a plain-English description of this formation's composition requirements, for the info popup. */
    public String getRequirements() {
        return switch (this) {
            case FAST_ASSAULT ->
                  "All units medium+ and Walk 5+ or jump-capable, armor 135+; 75% deal 25+ damage at range 7; "
                        + "3+ heavy or heavier; a Juggernaut or two Snipers.";
            case ASSAULT ->
                  "All units medium+ with armor 135+; 75% deal 25+ damage at range 7; 3+ heavy or heavier; "
                        + "a Juggernaut or two Snipers.";
            case ARTILLERY_FIRE -> "At least two units carrying an artillery weapon.";
            case ANTI_AIR ->
                  "75% Missile Boats/Snipers; at least two units with an AC/LBX/artillery (flak-capable) weapon.";
            case DIRECT_FIRE -> "All units medium+ dealing 10+ damage at range 18; at least two heavy or heavier.";
            case FIRE_SUPPORT -> "At least three units with an indirect-fire weapon.";
            case FIRE -> "At least 75% Missile Boats or Snipers.";
            case LIGHT_RECON -> "All light units with Walk 6+ and the Scout role.";
            case HEAVY_RECON ->
                  "All units medium+ with Walk 4+; two with Walk 5+; two Scouts; at least one heavy or heavier.";
            case RECON -> "All units Walk 5+; at least two Scouts or Strikers.";
            case SWEEP -> "All units medium or lighter with Walk 5+ and dealing 10+ damage at range 6.";
            case PROBE -> "All units heavy or lighter dealing 10+ damage at range 9; 75% with Walk 6+.";
            case PURSUIT ->
                  "All units medium or lighter; 75% Walk 6+; at least one weapon dealing 5+ at range 15.";
            case HEAVY_STRIKER_CAVALRY ->
                  "All units medium+ Walk 4+; three heavy or heavier; two Strikers/Skirmishers; a weapon dealing "
                        + "5+ at range 18.";
            case STRIKER_CAVALRY ->
                  "All units heavy or lighter with Walk 5+ or Jump 4+; 50% Strikers or Skirmishers.";
            case HEAVY_BATTLE -> "All units medium+; 50% heavy or heavier.";
            case MEDIUM_BATTLE -> "All units heavy or lighter; 50% medium.";
            case LIGHT_BATTLE -> "All units heavy or lighter; 75% light; at least one Scout.";
            case BATTLE -> "50% heavy or heavier; at least three Brawlers/Snipers/Skirmishers.";
            case VEHICLE_COMMAND ->
                  "50% Snipers/Missile Boats/Skirmishers/Juggernauts; one Brawler/Striker/Scout; two vehicles.";
            case COMMAND ->
                  "50% Snipers/Missile Boats/Skirmishers/Juggernauts; at least one Brawler/Striker/Scout.";
        };
    }

    /** @return friendly display names of the SPAs this formation grants in TW (empty for display-only formations). */
    public List<String> getGrantedAbilityNames() {
        List<String> names = new ArrayList<>();
        for (String ability : grantedAbilities) {
            names.add(abilityDisplayName(ability));
        }
        return names;
    }

    private static String abilityDisplayName(String ability) {
        return switch (ability) {
            case OptionsConstants.GUNNERY_SNIPER -> "Sniper";
            case OptionsConstants.GUNNERY_OBLIQUE_ATTACKER -> "Oblique Attacker";
            case OptionsConstants.GUNNERY_WEAPON_SPECIALIST -> "Weapon Specialist";
            case OptionsConstants.GUNNERY_BLOOD_STALKER -> "Blood Stalker";
            case OptionsConstants.GUNNERY_MULTI_TASKER -> "Multi-Tasker";
            case OptionsConstants.MISC_FORWARD_OBSERVER -> "Forward Observer";
            case OptionsConstants.GUNNERY_OBLIQUE_ARTILLERY -> "Oblique Artilleryman";
            default -> ability;
        };
    }

    /**
     * @return true if this formation qualifies for the given members: the composition matches, or (ideal-role
     *       waiver) every member has this formation's ideal role.
     */
    boolean qualifies(List<Entity> members) {
        return allShareIdealRole(members) || composition.test(members);
    }

    private boolean allShareIdealRole(List<Entity> members) {
        if ((idealRole == null) || !idealRole.hasRole() || members.isEmpty()) {
            return false;
        }
        return members.stream().allMatch(e -> e.getRole() == idealRole);
    }

    /** @return how many of {@code activeCount} members receive the granted abilities, per this formation's share. */
    int recipientCount(int activeCount) {
        return switch (share) {
            case ALL -> activeCount;
            case THREE_QUARTERS -> (int) Math.round(activeCount * 0.75);
            case HALF -> activeCount / 2; // AS: "up to half, rounded down"
        };
    }

    // -------------------------------------------------------------------------------------------------------
    // Detection + application
    // -------------------------------------------------------------------------------------------------------

    /**
     * Re-evaluates every force in the game and grants/revokes formation-bonus abilities on member crews. A no-op
     * unless {@link OptionsConstants#ADVANCED_FORMATION_BONUSES} is enabled. Idempotent and safe to call each
     * round: only abilities this system previously granted are ever revoked.
     */
    public static void refresh(Game game) {
        if (game == null) {
            return;
        }
        boolean enabled = game.getOptions().booleanOption(OptionsConstants.ADVANCED_FORMATION_BONUSES);

        // The abilities each entity (by id) should have granted this round. Empty when the feature is off, so
        // the reconcile pass below cleans up anything granted while it was on.
        Map<Integer, Set<String>> desired = new HashMap<>();
        if (enabled) {
            for (Force force : game.getForces().getAllForces()) {
                List<Entity> members = liveMembers(game, force);
                List<Entity> active = members.stream().filter(FormationBonusType::isActive).toList();
                if (active.size() < MIN_ACTIVE_UNITS) {
                    continue;
                }
                FormationBonusType type = detect(members);
                if ((type == null) || type.grantedAbilities.isEmpty()) {
                    continue;
                }
                int recipients = Math.min(type.recipientCount(active.size()), active.size());
                for (int i = 0; i < recipients; i++) {
                    desired.computeIfAbsent(active.get(i).getId(), k -> new HashSet<>())
                          .addAll(type.grantedAbilities);
                }
            }
        }

        for (Entity entity : game.getEntitiesVector()) {
            applyGrants(entity, desired.getOrDefault(entity.getId(), Set.of()));
        }
    }

    /** @return the highest-priority formation type the given members qualify for, or {@code null} for none. */
    public static FormationBonusType detect(List<Entity> members) {
        if ((members == null) || members.isEmpty()) {
            return null;
        }
        for (FormationBonusType type : values()) {
            if (type.qualifies(members)) {
                return type;
            }
        }
        return null;
    }

    private static void applyGrants(Entity entity, Set<String> want) {
        if ((entity == null) || (entity.getCrew() == null)) {
            return;
        }
        Set<String> granted = entity.getFormationGrantedAbilities();

        // Revoke previously-granted abilities that are no longer wanted. `granted` only ever holds abilities we
        // turned on from off, so switching them back off cannot clobber a pilot-native SPA.
        for (String ability : new HashSet<>(granted)) {
            if (!want.contains(ability)) {
                setOption(entity, ability, false);
                granted.remove(ability);
            }
        }
        // Grant newly-wanted abilities the pilot does not already possess.
        for (String ability : want) {
            if (!granted.contains(ability) && !entity.hasAbility(ability)) {
                if (setOption(entity, ability, true)) {
                    granted.add(ability);
                }
            }
        }
    }

    private static boolean setOption(Entity entity, String ability, boolean value) {
        IOption option = entity.getCrew().getOptions().getOption(ability);
        if (option == null) {
            return false;
        }
        option.setValue(value);
        return true;
    }

    private static List<Entity> liveMembers(Game game, Force force) {
        List<Entity> result = new ArrayList<>();
        for (int id : force.getEntities()) {
            Entity entity = game.getEntity(id);
            if (entity != null) {
                result.add(entity);
            }
        }
        return result;
    }

    private static boolean isActive(Entity entity) {
        return entity.isDeployed() && !entity.isDestroyed();
    }

    // -------------------------------------------------------------------------------------------------------
    // Composition predicates (Entity-native, mirroring ratgenerator/FormationType thresholds)
    // -------------------------------------------------------------------------------------------------------

    private static UnitRole role(Entity entity) {
        return entity.getRole();
    }

    private static int walk(Entity entity) {
        return entity.getWalkMP();
    }

    private static int jump(Entity entity) {
        return entity.getJumpMP();
    }

    private static boolean wcAtLeast(Entity entity, int weightClass) {
        return entity.getWeightClass() >= weightClass;
    }

    private static boolean wcAtMost(Entity entity, int weightClass) {
        return entity.getWeightClass() <= weightClass;
    }

    private static boolean wcEquals(Entity entity, int weightClass) {
        return entity.getWeightClass() == weightClass;
    }

    private static boolean roleIsAnyOf(Entity entity, UnitRole... roles) {
        UnitRole role = entity.getRole();
        for (UnitRole candidate : roles) {
            if (role == candidate) {
                return true;
            }
        }
        return false;
    }

    private static int count(List<Entity> members, Predicate<Entity> predicate) {
        return (int) members.stream().filter(predicate).count();
    }

    private static boolean all(List<Entity> members, Predicate<Entity> predicate) {
        return !members.isEmpty() && members.stream().allMatch(predicate);
    }

    private static boolean fraction(List<Entity> members, double fraction, Predicate<Entity> predicate) {
        if (members.isEmpty()) {
            return false;
        }
        int required = (int) Math.ceil(fraction * members.size());
        return count(members, predicate) >= required;
    }

    /** Total damage of a unit's weapons whose long range reaches {@code rangeHexes} (crude, for detection only). */
    private static long dmgAtRange(Entity entity, int rangeHexes) {
        long sum = 0;
        for (WeaponMounted mounted : entity.getWeaponList()) {
            if (mounted.getType().getLongRange() >= rangeHexes) {
                int damage = mounted.getType().getDamage();
                if (damage > 0) {
                    sum += damage;
                }
            }
        }
        return sum;
    }

    /** Highest single-weapon damage among a unit's weapons that reach {@code rangeHexes}. */
    private static long maxWeaponDmgAtRange(Entity entity, int rangeHexes) {
        long max = 0;
        for (WeaponMounted mounted : entity.getWeaponList()) {
            if (mounted.getType().getLongRange() >= rangeHexes) {
                int damage = mounted.getType().getDamage();
                if (damage > max) {
                    max = damage;
                }
            }
        }
        return max;
    }

    private static boolean hasIndirectFireWeapon(Entity entity) {
        for (WeaponMounted mounted : entity.getWeaponList()) {
            if (mounted.getType().hasIndirectFire()) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasArtilleryWeapon(Entity entity) {
        for (WeaponMounted mounted : entity.getWeaponList()) {
            if (mounted.getType() instanceof ArtilleryWeapon) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasFlakCapableWeapon(Entity entity) {
        for (WeaponMounted mounted : entity.getWeaponList()) {
            var type = mounted.getType();
            if ((type instanceof ACWeapon) || (type instanceof LBXACWeapon) || (type instanceof UACWeapon)
                  || (type instanceof ArtilleryWeapon)) {
                return true;
            }
        }
        return false;
    }
}
