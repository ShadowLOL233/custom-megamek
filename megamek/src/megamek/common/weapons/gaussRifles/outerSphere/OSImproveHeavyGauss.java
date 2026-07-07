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

/** Outer Sphere Improve Heavy Gauss - fixed 22 dmg (no range decay) / 18t / 10 crit. */
public class OSImproveHeavyGauss extends GaussWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveHeavyGauss() {
        super();
        name = "Improve Heavy Gauss Rifle";
        setInternalName("OSImproveHeavyGauss");
        addLookupName("OS Improve Heavy Gauss Rifle");
        sortingName = "Gauss OS 3 Heavy 2 Imp";
        heat = 3;
        damage = 22;
        rackSize = 22;
        ammoType = AmmoType.AmmoTypeEnum.GAUSS_HEAVY_OS;
        minimumRange = 3;
        shortRange = 7;
        mediumRange = 13;
        longRange = 20;
        extremeRange = 30;
        tonnage = 18.0;
        criticalSlots = 10;
        bv = 430;
        cost = 720000;
        shortAV = 22;
        medAV = 22;
        longAV = 22;
        maxRange = RANGE_LONG;
        explosionDamage = 22;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2900, 2930, 2960, DATE_NONE, DATE_NONE)
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
            return new HGRHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
