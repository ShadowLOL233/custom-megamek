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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import megamek.common.game.Game;
import megamek.common.options.IOption;
import megamek.common.options.OptionsConstants;
import megamek.common.units.Entity;
import megamek.common.units.UnitRole;

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
 * <p><b>This is the first, deliberately narrow slice</b> (Fire, Fire Support). The full formation/bonus
 * catalogue and the remaining Tier-1 formations (Battle, Assault, Striker/Cavalry, Recon, Pursuit, Command,
 * aerospace squadrons, Support/Nova/Air Lance) plus the Tier-2 Campaign-Operations-only formations
 * (Berserker/Close, Rifle, …) are documented in {@code development/FORMATION_BONUS_REFERENCE.md}.</p>
 */
public enum FormationBonusType {

    /**
     * Fire Lance — at least 75% of members are Missile Boat or Sniper units. Bonus: up to half the members
     * gain the Sniper SPA. (AS grants it per-turn; this port grants it to a fixed half each round.)
     */
    FIRE("Fire", UnitRole.MISSILE_BOAT, List.of(OptionsConstants.GUNNERY_SNIPER), Share.HALF, "Sniper") {
        @Override
        boolean matchesComposition(List<Entity> members) {
            return atLeastFraction(members, 0.75, e -> roleIsAnyOf(e, UnitRole.SNIPER, UnitRole.MISSILE_BOAT));
        }
    },

    /**
     * Fire Support Lance — at least 3 members carry an indirect-fire (IF) weapon. Bonus: up to half the
     * members gain the Oblique Attacker SPA. Detected from equipment, so it works even when unit roles are
     * unset (which is common in TW play).
     */
    FIRE_SUPPORT("Fire Support", null, List.of(OptionsConstants.GUNNERY_OBLIQUE_ATTACKER), Share.HALF,
          "Oblique Attacker") {
        @Override
        boolean matchesComposition(List<Entity> members) {
            return countMatching(members, FormationBonusType::hasIndirectFireWeapon) >= 3;
        }
    };

    /** A formation's bonus is inactive unless it keeps at least this many active units on the board. */
    public static final int MIN_ACTIVE_UNITS = 3;

    /** Fraction of a formation's active members that receive the granted abilities. */
    enum Share { ALL, THREE_QUARTERS, HALF }

    private final String displayName;
    private final UnitRole idealRole;
    private final List<String> grantedAbilities;
    private final Share share;
    private final String bonusDescription;

    FormationBonusType(String displayName, UnitRole idealRole, List<String> grantedAbilities, Share share,
          String bonusDescription) {
        this.displayName = displayName;
        this.idealRole = idealRole;
        this.grantedAbilities = grantedAbilities;
        this.share = share;
        this.bonusDescription = bonusDescription;
    }

    public String getDisplayName() {
        return displayName;
    }

    /** @return a short human-readable description of this formation's bonus, for UI display (may be empty). */
    public String getBonusDescription() {
        return bonusDescription;
    }

    public List<String> getGrantedAbilities() {
        return grantedAbilities;
    }

    /** @return whether the given member units meet this formation's composition requirements. */
    abstract boolean matchesComposition(List<Entity> members);

    /**
     * @return true if this formation qualifies for the given members: the composition matches, or (ideal-role
     *       waiver) every member has this formation's ideal role.
     */
    boolean qualifies(List<Entity> members) {
        return allShareIdealRole(members) || matchesComposition(members);
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
                if (type == null) {
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
    // Composition predicates
    // -------------------------------------------------------------------------------------------------------

    private static boolean roleIsAnyOf(Entity entity, UnitRole... roles) {
        UnitRole role = entity.getRole();
        for (UnitRole candidate : roles) {
            if (role == candidate) {
                return true;
            }
        }
        return false;
    }

    private static int countMatching(List<Entity> members, Predicate<Entity> predicate) {
        return (int) members.stream().filter(predicate).count();
    }

    private static boolean atLeastFraction(List<Entity> members, double fraction, Predicate<Entity> predicate) {
        if (members.isEmpty()) {
            return false;
        }
        int required = (int) Math.ceil(fraction * members.size());
        return countMatching(members, predicate) >= required;
    }

    private static boolean hasIndirectFireWeapon(Entity entity) {
        for (var mounted : entity.getWeaponList()) {
            if (mounted.getType().hasIndirectFire()) {
                return true;
            }
        }
        return false;
    }
}
