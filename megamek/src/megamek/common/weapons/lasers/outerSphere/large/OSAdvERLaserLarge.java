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
import megamek.common.weapons.lasers.LaserWeapon;

/**
 * Outer Sphere Advanced ER Large Laser
 * Damage 11 / Heat 14 / Range 8-15-22 / -1 ToHit, 4.5t / 4 crit
 */
public class OSAdvERLaserLarge extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAdvERLaserLarge() {
        super();
        name = "Adv. ER Large Laser";
        setInternalName("OSAdvERLargeLaser");
        addLookupName("OS Adv. ER Large Laser");
        addLookupName("OSAdvERLaserLarge");
        sortingName = "Laser AdvER D";
        heat = 14;
        damage = 11;
        toHitModifier = -1;
        shortRange = 8;
        mediumRange = 15;
        longRange = 22;
        extremeRange = 30;
        waterShortRange = 5;
        waterMediumRange = 10;
        waterLongRange = 14;
        waterExtremeRange = 20;
        tonnage = 4.5;
        criticalSlots = 4;
        bv = 260;
        cost = 360000;
        shortAV = 11;
        medAV = 11;
        longAV = 11;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F)
              .setISAdvancement(3130, 3135, 3140, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
