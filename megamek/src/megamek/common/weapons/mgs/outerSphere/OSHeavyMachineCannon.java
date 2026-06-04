/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.mgs.outerSphere;

import static megamek.common.game.IGame.LOGGER;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.annotations.Nullable;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.AmmoMounted;
import megamek.common.equipment.AmmoType;
import megamek.common.equipment.Mounted;
import megamek.common.equipment.WeaponType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.units.Entity;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.MGHandler;
import megamek.common.weapons.handlers.ac.ACFlakHandler;
import megamek.common.weapons.mgs.MGWeapon;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Heavy Machine Cannon.
 * Damage 5 / 2 heat / Range 2-5-8 / 2.5t / 3 crit / 40 shots-per-ton.
 *
 * Heavier sibling of Machine Cannon: more mech damage and the strongest
 * anti-infantry burst in the OS kinetic line (10D6, ~35 average vs infantry).
 * Supports M_FLAK munitions; flak fire dispatches to ACFlakHandler.
 */
public class OSHeavyMachineCannon extends MGWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyMachineCannon() {
        super();
        name = "Heavy Machine Cannon";
        setInternalName("OSHeavyMachineCannon");
        addLookupName("OS Heavy Machine Cannon");
        sortingName = "MG OS 6 HMC";
        ammoType = AmmoType.AmmoTypeEnum.HEAVY_MACHINE_CANNON_OS;
        heat = 2;
        damage = 5;
        infDamageClass = WeaponType.WEAPON_BURST_10D6;
        rackSize = 5;
        shortRange = 2;
        mediumRange = 5;
        longRange = 8;
        extremeRange = 11;
        tonnage = 2.5;
        criticalSlots = 3;
        bv = 26;
        cost = 60000;
        shortAV = 5;
        maxRange = RANGE_SHORT;
        atClass = CLASS_POINT_DEFENSE;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.G)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.EXPERIMENTAL);
    }

    @Override
    @Nullable
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            Entity entity = game.getEntity(waa.getEntityId());
            if (entity != null) {
                Mounted<?> weapon = entity.getEquipment(waa.getWeaponId());
                AmmoMounted linked = (weapon != null) ? (AmmoMounted) weapon.getLinked() : null;
                if (linked != null
                      && linked.getType().getMunitionType().contains(AmmoType.Munitions.M_FLAK)) {
                    return new ACFlakHandler(toHit, waa, game, manager);
                }
            }
            return new MGHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
