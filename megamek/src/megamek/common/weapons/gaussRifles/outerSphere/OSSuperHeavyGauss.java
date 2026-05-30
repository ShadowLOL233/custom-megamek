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

/** Outer Sphere Super-Heavy Gauss - 30 dmg / 22t / 12 crit / Experimental. The biggest single-shot gun in the line. */
public class OSSuperHeavyGauss extends GaussWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSSuperHeavyGauss() {
        super();
        name = "Super-Heavy Gauss Rifle";
        setInternalName("OSSuperHeavyGauss");
        addLookupName("OS Super-Heavy Gauss Rifle");
        sortingName = "Gauss OS 4 Super";
        heat = 3;
        damage = 30;
        rackSize = 30;
        ammoType = AmmoType.AmmoTypeEnum.GAUSS_HEAVY_OS;
        minimumRange = 4;
        shortRange = 5;
        mediumRange = 11;
        longRange = 17;
        extremeRange = 25;
        tonnage = 22.0;
        criticalSlots = 12;
        bv = 520;
        cost = 1100000;
        shortAV = 30;
        medAV = 30;
        longAV = 30;
        maxRange = RANGE_LONG;
        explosionDamage = 30;
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
            return new HGRHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
