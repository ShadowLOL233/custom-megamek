/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPL),
 * version 3 or (at your option) any later version,
 * as published by the Free Software Foundation.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A copy of the GPL should have been included with this project;
 * if not, see <https://www.gnu.org/licenses/>.
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
 * Outer Sphere ER Medium Laser
 * Damage 6 / Heat 5 / Range 4-8-12, 1t / 1 crit
 */
public class OSERLaserMedium extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERLaserMedium() {
        super();
        name = "ER Medium Laser";
        setInternalName("OSERMediumLaser");
        addLookupName("OS ER Medium Laser");
        addLookupName("OSERLaserMedium");
        sortingName = "Laser OS 03 ER 1 Std B";
        heat = 5;
        damage = 6;
        shortRange = 4;
        mediumRange = 8;
        longRange = 12;
        extremeRange = 16;
        waterShortRange = 2;
        waterMediumRange = 5;
        waterLongRange = 8;
        waterExtremeRange = 10;
        tonnage = 1.0;
        criticalSlots = 1;
        bv = 70;
        cost = 75000;
        shortAV = 6;
        medAV = 6;
        maxRange = RANGE_MED;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3120, 3125, 3130, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
