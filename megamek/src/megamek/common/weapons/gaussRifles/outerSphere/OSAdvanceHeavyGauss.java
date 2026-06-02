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

/** Outer Sphere Advance Heavy Gauss - fixed 25 dmg (no range decay) / 17t / 9 crit / Experimental. */
public class OSAdvanceHeavyGauss extends GaussWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAdvanceHeavyGauss() {
        super();
        name = "Advance Heavy Gauss Rifle";
        setInternalName("OSAdvanceHeavyGauss");
        addLookupName("OS Advance Heavy Gauss Rifle");
        sortingName = "Gauss OS 3 Heavy 3 Adv";
        heat = 4;
        damage = 25;
        rackSize = 25;
        ammoType = AmmoType.AmmoTypeEnum.GAUSS_HEAVY_OS;
        minimumRange = 3;
        shortRange = 7;
        mediumRange = 14;
        longRange = 21;
        extremeRange = 32;
        tonnage = 17.0;
        criticalSlots = 9;
        bv = 510;
        cost = 850000;
        shortAV = 25;
        medAV = 25;
        longAV = 25;
        maxRange = RANGE_LONG;
        explosionDamage = 25;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
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
            return new HGRHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
