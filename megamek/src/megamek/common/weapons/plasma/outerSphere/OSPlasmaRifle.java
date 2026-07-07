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
import megamek.common.alphaStrike.AlphaStrikeElement;
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
import megamek.common.weapons.handlers.plasma.PlasmaRifleHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Plasma Rifle
 * Damage 10 (+2d6 heat) / Heat 8 / Range 6-12-18 / 5t / 2 crit / ammo-fed
 *
 * Focused-bolt plasma. Same 10-point thermal ceiling as the canonical IS Plasma
 * Rifle, but OS containment runs cooler (8 vs 10 heat), lighter (5 vs 6t) and
 * reaches further (18 vs 15). Like all plasma it must feed on ammo.
 */
public class OSPlasmaRifle extends AmmoWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSPlasmaRifle() {
        name = "Plasma Rifle";
        setInternalName("OSPlasmaRifle");
        addLookupName("OS Plasma Rifle");
        sortingName = "Plasma OS 1 Rifle 1 Std";
        heat = 8;
        damage = 10;
        rackSize = 1;
        ammoType = AmmoType.AmmoTypeEnum.PLASMA_RIFLE_OS;
        minimumRange = WEAPON_NA;
        shortRange = 6;
        mediumRange = 12;
        longRange = 18;
        extremeRange = 24;
        tonnage = 5.0;
        criticalSlots = 2;
        flags = flags.or(F_MEK_WEAPON).or(F_TANK_WEAPON).or(F_AERO_WEAPON)
              .or(F_PLASMA).or(F_DIRECT_FIRE).or(F_ENERGY);
        bv = 250;
        cost = 280000;
        shortAV = 10;
        medAV = 10;
        maxRange = RANGE_MED;
        atClass = CLASS_PLASMA;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2805, 2820, 2840, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }

    @Override
    @Nullable
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new PlasmaRifleHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }

    @Override
    public int getAlphaStrikeHeatDamage(int rangeband) {
        return (rangeband <= AlphaStrikeElement.RANGE_BAND_MEDIUM) ? 3 : 0;
    }
}
