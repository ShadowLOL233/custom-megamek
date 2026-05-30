/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.plasma.outerSphere;

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
import megamek.common.equipment.AmmoType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.AmmoWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.plasma.OSEMPPlasmaHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere EMP Plasma Accelerator (School D)
 * Damage 0 / Heat 8 / Range 5-10-15 / 5t / 3 crit / ammo-fed
 *
 * A dedicated electronic-disruption weapon: an ionized plasma stream carries an
 * EM pulse that can only ever cause INTERFERENCE (never a full shutdown), trading
 * away all damage to stay affordable. Because OS containment stops the pulse from
 * backwashing, the firing unit suffers NO self-interference - the efficiency edge
 * over the canonical TSEMP, paid for with ammo dependency.
 */
public class OSEMPPlasmaAccelerator extends AmmoWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEMPPlasmaAccelerator() {
        name = "EMP Plasma Accelerator";
        setInternalName("OSEMPPlasmaAccelerator");
        addLookupName("OS EMP Plasma Accelerator");
        sortingName = "Plasma OS 3 EMP 1 Std";
        heat = 8;
        damage = 0;
        rackSize = 1;
        ammoType = AmmoType.AmmoTypeEnum.PLASMA_EMP_OS;
        minimumRange = WEAPON_NA;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 5.0;
        criticalSlots = 3;
        flags = flags.or(F_MEK_WEAPON).or(F_TANK_WEAPON).or(F_AERO_WEAPON)
              .or(F_DIRECT_FIRE).or(F_ENERGY);
        bv = 250;
        cost = 500000;
        shortAV = 0;
        maxRange = RANGE_MED;
        atClass = CLASS_PLASMA;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3130, 3135, 3140, DATE_NONE, DATE_NONE)
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
            return new OSEMPPlasmaHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
