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
import megamek.common.weapons.handlers.GRHandler;
import megamek.server.totalWarfare.TWGameManager;

/** Outer Sphere Ultra-Light Gauss - 6 dmg / 7-15-22 / 7t / 3 crit / Experimental. */
public class OSUltraLightGauss extends GaussWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSUltraLightGauss() {
        super();
        name = "Ultra-Light Gauss Rifle";
        setInternalName("OSUltraLightGauss");
        addLookupName("OS Ultra-Light Gauss Rifle");
        sortingName = "Gauss OS 1 Light 1 Ultra";
        heat = 1;
        damage = 6;
        rackSize = 6;
        ammoType = AmmoType.AmmoTypeEnum.GAUSS_LIGHT_OS;
        minimumRange = 3;
        shortRange = 7;
        mediumRange = 15;
        longRange = 22;
        extremeRange = 33;
        tonnage = 7.0;
        criticalSlots = 3;
        bv = 120;
        cost = 260000;
        shortAV = 6;
        medAV = 6;
        longAV = 6;
        maxRange = RANGE_LONG;
        explosionDamage = 12;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3130, 3135, 3140, DATE_NONE, DATE_NONE)
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
            return new GRHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
