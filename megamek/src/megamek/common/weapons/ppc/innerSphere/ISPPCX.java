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

package megamek.common.weapons.ppc.innerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.weapons.ppc.PPCXWeapon;

/**
 * Inner Sphere PPC-X
 * Cluster: 5 sub-beams × 2 damage = 10 max / Heat 10 / Range 6-12-18 / no min, 7t / 3 crit
 *
 * The IS-tech precursor to the OS PPC-X. Shares the cluster-dispersion mechanic
 * (LB-X style number-of-hits roll) and the -1/+0/+1 range-bracket ToHit gradient,
 * but uses 5 sub-beams instead of 6 and matches standard IS PPC range/heat. The
 * "no minimum range" property is the key trade-off over a standard PPC.
 */
public class ISPPCX extends PPCXWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public ISPPCX() {
        super();
        name = "PPC-X";
        setInternalName("ISPPCX");
        addLookupName("IS PPC-X");
        sortingName = "PPC X B";
        heat = 10;
        damage = 2;       // Per sub-beam
        rackSize = 5;     // 5 sub-beams (vs 6 for OS PPC-X)
        shortRange = 6;
        mediumRange = 12;
        longRange = 18;
        extremeRange = 24;
        waterShortRange = 4;
        waterMediumRange = 7;
        waterLongRange = 10;
        waterExtremeRange = 15;
        tonnage = 7.0;
        criticalSlots = 3;
        bv = 190;
        cost = 220000;
        shortAV = 10;
        medAV = 10;
        maxRange = RANGE_MED;
        explosive = true;
        atClass = CLASS_PPC;
        techAdvancement.setTechBase(TechBase.IS)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.E, AvailabilityValue.D)
              .setISAdvancement(3095, 3105, 3120, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.FS, Faction.LC)
              .setProductionFactions(Faction.FS)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
