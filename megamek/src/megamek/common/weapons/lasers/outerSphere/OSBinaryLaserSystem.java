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
 * Outer Sphere Binary Laser System
 * Damage 16 / Heat 16 / Range 5-10-15, 7t / 4 crit
 * Resonance Tuning: x1.30 damage vs Standard Armor.
 */
public class OSBinaryLaserSystem extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSBinaryLaserSystem() {
        super();
        name = "Binary Laser System";
        setInternalName("OSBinaryLaserSystem");
        addLookupName("OS Binary Laser System");
        sortingName = "Laser Binary D";
        heat = 16;
        damage = 16;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        waterShortRange = 3;
        waterMediumRange = 6;
        waterLongRange = 9;
        waterExtremeRange = 12;
        tonnage = 7.0;
        criticalSlots = 4;
        bv = 200;
        cost = 230000;
        shortAV = 16;
        medAV = 16;
        maxRange = RANGE_MED;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.E)
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
            return new BinaryLaserHandler(toHit, waa, game, manager, 1.30);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
