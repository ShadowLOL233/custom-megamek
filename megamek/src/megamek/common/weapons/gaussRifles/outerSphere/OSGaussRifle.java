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

/** Outer Sphere Gauss Rifle - 15 dmg / 7-15-22 / 12t / 6 crit (Clan-weight baseline). */
public class OSGaussRifle extends GaussWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSGaussRifle() {
        super();
        name = "Gauss Rifle";
        setInternalName("OSGaussRifle");
        addLookupName("OS Gauss Rifle");
        sortingName = "Gauss OS 2 Std 1 Std";
        heat = 1;
        damage = 15;
        ammoType = AmmoType.AmmoTypeEnum.GAUSS_OS;
        minimumRange = 2;
        shortRange = 7;
        mediumRange = 15;
        longRange = 22;
        extremeRange = 33;
        tonnage = 12.0;
        criticalSlots = 6;
        bv = 320;
        cost = 300000;
        shortAV = 15;
        medAV = 15;
        longAV = 15;
        maxRange = RANGE_LONG;
        explosionDamage = 20;
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
