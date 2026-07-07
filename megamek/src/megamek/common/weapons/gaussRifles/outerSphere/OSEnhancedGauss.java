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

/** Outer Sphere Enhanced Gauss - 10t / 5 crit / extra-long range 9-18-27 / Experimental. */
public class OSEnhancedGauss extends GaussWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedGauss() {
        super();
        name = "Enhanced Gauss Rifle";
        setInternalName("OSEnhancedGauss");
        addLookupName("OSAdvanceGauss");
        addLookupName("Advance Gauss Rifle");
        addLookupName("OS Advance Gauss Rifle");
        sortingName = "Gauss OS 2 Std 3 Enh";
        heat = 1;
        damage = 15;
        ammoType = AmmoType.AmmoTypeEnum.GAUSS_OS;
        minimumRange = 2;
        shortRange = 9;
        mediumRange = 18;
        longRange = 27;
        extremeRange = 40;
        tonnage = 10.0;
        criticalSlots = 5;
        bv = 370;
        cost = 420000;
        shortAV = 15;
        medAV = 15;
        longAV = 15;
        maxRange = RANGE_LONG;
        explosionDamage = 20;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3000, 3025, 3050, DATE_NONE, DATE_NONE)
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
