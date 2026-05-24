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
 * Outer Sphere ER Binary Laser System
 * Damage 16 / Heat 20 / Range 8-15-22 / -1 ToHit, 7t / 4 crit
 * Resonance Tuning: x1.30 damage vs Standard Armor.
 */
public class OSERBinaryLaserSystem extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERBinaryLaserSystem() {
        super();
        name = "ER Binary Laser System";
        setInternalName("OSERBinaryLaserSystem");
        addLookupName("OS ER Binary Laser System");
        sortingName = "Laser ERBinary D";
        heat = 20;
        damage = 16;
        toHitModifier = -1;
        shortRange = 8;
        mediumRange = 15;
        longRange = 22;
        extremeRange = 30;
        waterShortRange = 5;
        waterMediumRange = 10;
        waterLongRange = 14;
        waterExtremeRange = 20;
        tonnage = 7.0;
        criticalSlots = 4;
        bv = 265;
        cost = 380000;
        shortAV = 16;
        medAV = 16;
        longAV = 16;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.E)
              .setISAdvancement(3135, 3140, 3145, DATE_NONE, DATE_NONE)
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
            return new BinaryLaserHandler(toHit, waa, game, manager, 1.30);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
