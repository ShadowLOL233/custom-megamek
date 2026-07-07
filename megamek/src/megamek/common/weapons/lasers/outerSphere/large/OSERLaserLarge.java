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

package megamek.common.weapons.lasers.outerSphere.large;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.weapons.lasers.LaserWeapon;

/**
 * Outer Sphere ER Large Laser
 * Damage 9 / Heat 12 / Range 7-14-19, 4.5t / 3 crit
 */
public class OSERLaserLarge extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERLaserLarge() {
        super();
        name = "ER Large Laser";
        setInternalName("OSERLargeLaser");
        addLookupName("OS ER Large Laser");
        addLookupName("OSERLaserLarge");
        sortingName = "Laser OS 03 ER 1 Std D";
        heat = 12;
        damage = 9;
        shortRange = 7;
        mediumRange = 14;
        longRange = 19;
        extremeRange = 28;
        waterShortRange = 3;
        waterMediumRange = 9;
        waterLongRange = 12;
        waterExtremeRange = 18;
        tonnage = 4.5;
        criticalSlots = 3;
        bv = 170;
        cost = 200000;
        shortAV = 9;
        medAV = 9;
        longAV = 9;
        maxRange = RANGE_LONG;
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
}
