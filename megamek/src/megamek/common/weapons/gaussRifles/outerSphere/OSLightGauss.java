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

/** Outer Sphere Light Gauss - 8 dmg / 8-17-25 / 10t / 4 crit. */
public class OSLightGauss extends GaussWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSLightGauss() {
        super();
        name = "Light Gauss Rifle";
        setInternalName("OSLightGauss");
        addLookupName("OS Light Gauss Rifle");
        sortingName = "Gauss OS 1 Light 2 Std";
        heat = 1;
        damage = 8;
        rackSize = 8;
        ammoType = AmmoType.AmmoTypeEnum.GAUSS_LIGHT_OS;
        minimumRange = 3;
        shortRange = 8;
        mediumRange = 17;
        longRange = 25;
        extremeRange = 37;
        tonnage = 10.0;
        criticalSlots = 4;
        bv = 165;
        cost = 300000;
        shortAV = 8;
        medAV = 8;
        longAV = 8;
        maxRange = RANGE_LONG;
        explosionDamage = 16;
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
            return new GRHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
