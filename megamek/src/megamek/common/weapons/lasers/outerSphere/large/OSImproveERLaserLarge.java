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
 * Outer Sphere Improved ER Large Laser
 * Damage 10 / Heat 12 / Range 8-15-22, 4t / 1 crit
 */
public class OSImproveERLaserLarge extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveERLaserLarge() {
        super();
        name = "Improve ER Large Laser";
        setInternalName("OSImproveERLargeLaser");
        addLookupName("OS Improve ER Large Laser");
        addLookupName("OSImproveERLaserLarge");
        sortingName = "Laser OS 03 ER 2 Imp D";
        heat = 12;
        damage = 10;
        shortRange = 8;
        mediumRange = 15;
        longRange = 22;
        extremeRange = 30;
        waterShortRange = 5;
        waterMediumRange = 10;
        waterLongRange = 14;
        waterExtremeRange = 20;
        tonnage = 4.0;
        criticalSlots = 1;
        bv = 230;
        cost = 280000;
        shortAV = 10;
        medAV = 10;
        longAV = 10;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3125, 3130, 3135, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
