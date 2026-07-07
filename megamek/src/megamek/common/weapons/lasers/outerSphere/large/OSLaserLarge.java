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
 * Outer Sphere Standard Large Laser — RoOS-manufactured replica of the upstream IS Large Laser.
 * Stats identical to the IS reference; differentiated only by tech base.
 */
public class OSLaserLarge extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSLaserLarge() {
        super();
        name = "Large Laser";
        setInternalName("OSLaserLarge");
        addLookupName("OS Large Laser");
        addLookupName("Legion Large Laser");
        sortingName = "Laser OS 01 Std 1 Std D";
        heat = 8;
        damage = 8;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        waterShortRange = 3;
        waterMediumRange = 6;
        waterLongRange = 9;
        waterExtremeRange = 12;
        tonnage = 5.0;
        criticalSlots = 2;
        bv = 123;
        cost = 100000;
        shortAV = 8;
        medAV = 8;
        maxRange = RANGE_MED;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.D, AvailabilityValue.C)
              .setISAdvancement(2805, 2820, 2840, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
