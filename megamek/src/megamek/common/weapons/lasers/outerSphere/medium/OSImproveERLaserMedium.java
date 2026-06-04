/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.lasers.outerSphere.medium;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.weapons.lasers.LaserWeapon;

/**
 * Outer Sphere Improved ER Medium Laser
 * Damage 6 / Heat 5 / Range 5-10-15, 1t / 1 crit
 */
public class OSImproveERLaserMedium extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveERLaserMedium() {
        super();
        name = "Improve ER Medium Laser";
        setInternalName("OSImproveERMediumLaser");
        addLookupName("OS Improve ER Medium Laser");
        addLookupName("OSImproveERLaserMedium");
        sortingName = "Laser OS 03 ER 2 Imp B";
        heat = 5;
        damage = 6;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        waterShortRange = 3;
        waterMediumRange = 6;
        waterLongRange = 9;
        waterExtremeRange = 12;
        tonnage = 1.0;
        criticalSlots = 1;
        bv = 88;
        cost = 110000;
        shortAV = 6;
        medAV = 6;
        longAV = 6;
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
