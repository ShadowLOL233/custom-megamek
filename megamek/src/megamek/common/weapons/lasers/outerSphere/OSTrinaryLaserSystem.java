/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.lasers.outerSphere;

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
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.BinaryLaserHandler;
import megamek.common.weapons.lasers.LaserWeapon;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Trinary Laser System
 * Damage 24 / Heat 24 / Range 5-10-15, 11t / 6 crit
 * Resonance Tuning: x1.50 damage vs Standard Armor.
 */
public class OSTrinaryLaserSystem extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSTrinaryLaserSystem() {
        super();
        name = "Trinary Laser System";
        setInternalName("OSTrinaryLaserSystem");
        addLookupName("OS Trinary Laser System");
        sortingName = "Laser OS 10 Trinary 1 Std D";
        heat = 24;
        damage = 24;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        waterShortRange = 3;
        waterMediumRange = 6;
        waterLongRange = 9;
        waterExtremeRange = 12;
        tonnage = 11.0;
        criticalSlots = 6;
        bv = 369;
        cost = 480000;
        shortAV = 24;
        medAV = 24;
        maxRange = RANGE_MED;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.E)
              .setISAdvancement(3060, 3070, 3080, DATE_NONE, DATE_NONE)
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
            return new BinaryLaserHandler(toHit, waa, game, manager, 1.50);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
