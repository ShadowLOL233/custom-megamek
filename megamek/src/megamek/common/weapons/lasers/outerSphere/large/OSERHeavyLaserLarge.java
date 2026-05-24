/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.lasers.outerSphere.large;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.WeaponTypeFlag;
import megamek.common.weapons.lasers.LaserWeapon;

/**
 * Outer Sphere ER Heavy Large Laser
 * Damage 16 / Heat 22 / Range 7-14-19 / +1 ToHit, 5t / 3 crit
 */
public class OSERHeavyLaserLarge extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERHeavyLaserLarge() {
        super();
        name = "ER Heavy Large Laser";
        setInternalName("OSERHeavyLargeLaser");
        addLookupName("OS ER Heavy Large Laser");
        addLookupName("OSERHeavyLaserLarge");
        sortingName = "Laser ERHeavy D";
        heat = 22;
        damage = 16;
        toHitModifier = 1;
        shortRange = 7;
        mediumRange = 14;
        longRange = 19;
        extremeRange = 28;
        waterShortRange = 4;
        waterMediumRange = 9;
        waterLongRange = 12;
        waterExtremeRange = 18;
        tonnage = 5.0;
        criticalSlots = 3;
        bv = 245;
        cost = 500000;
        shortAV = 16;
        medAV = 16;
        longAV = 16;
        maxRange = RANGE_LONG;
        flags = flags.or(WeaponTypeFlag.HEAVY_LASER);
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3130, 3134, 3140, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
