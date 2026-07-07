/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.gaussRifles.outerSphere;

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
import megamek.common.weapons.gaussRifles.GaussWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.HGRHandler;
import megamek.server.totalWarfare.TWGameManager;

/** Outer Sphere Heavy Gauss - canonical 25/20/10 by range / 16t / 10 crit. */
public class OSHeavyGauss extends GaussWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyGauss() {
        super();
        name = "Heavy Gauss Rifle";
        setInternalName("OSHeavyGauss");
        addLookupName("OS Heavy Gauss Rifle");
        sortingName = "Gauss OS 3 Heavy 1 Std";
        heat = 2;
        damage = DAMAGE_VARIABLE;
        rackSize = 25;
        ammoType = AmmoType.AmmoTypeEnum.GAUSS_HEAVY_OS;
        minimumRange = 4;
        shortRange = 6;
        mediumRange = 13;
        longRange = 20;
        extremeRange = 30;
        damageShort = 25;
        damageMedium = 20;
        damageLong = 10;
        tonnage = 16.0;
        criticalSlots = 10;
        bv = 360;
        cost = 520000;
        shortAV = 25;
        medAV = 20;
        longAV = 10;
        maxRange = RANGE_LONG;
        explosionDamage = 25;
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
    public int getDamage(int range) {
        if (range <= shortRange) return damageShort;
        if (range <= mediumRange) return damageMedium;
        return damageLong;
    }

    @Override
    @Nullable
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new HGRHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
