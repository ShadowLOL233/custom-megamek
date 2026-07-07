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
 * Outer Sphere Machine Cannon.
 * Damage 3 / 1 heat / Range 3-6-9 / 1.5t / 2 crit / 80 shots-per-ton.
 *
 * Anti-infantry specialized weapon in the dead zone between MG and AC: small
 * mech damage but 6D6 burst dice vs infantry (canon Heavy MG is only 3D6).
 * Supports loading M_FLAK munitions; in flak mode behavior switches to ACFlakHandler
 * (cluster table + flak-to-hit modifiers from ComputeToHit).
 */
public class OSMachineCannon extends MGWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSMachineCannon() {
        super();
        name = "Machine Cannon";
        setInternalName("OSMachineCannon");
        addLookupName("OS Machine Cannon");
        sortingName = "MG OS 5 MC";
        ammoType = AmmoType.AmmoTypeEnum.MACHINE_CANNON_OS;
        heat = 1;
        damage = 3;
        infDamageClass = WeaponType.WEAPON_BURST_6D6;
        rackSize = 3;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 1.5;
        criticalSlots = 2;
        bv = 14;
        cost = 30000;
        shortAV = 3;
        maxRange = RANGE_SHORT;
        atClass = CLASS_POINT_DEFENSE;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3060, 3070, 3080, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
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
