/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.ppc.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.weapons.ppc.PPCXWeapon;

/**
 * Outer Sphere PPC-X
 * Cluster: 6 sub-beams × 2 damage = 12 max / Heat 10 / Range 7-10-13 / no min, 6t / 3 crit
 *
 * Adapts LB-X autocannon cluster mechanics to particle weapons. Number of sub-beams
 * that hit is determined by the missile cluster hit table. Suffers a -1/+0/+1 ToHit
 * gradient by range bracket (better at close range, worse at long).
 */
public class OSPPCX extends PPCXWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSPPCX() {
        super();
        name = "PPC-X";
        setInternalName("OSPPCX");
        addLookupName("OS PPC-X");
        sortingName = "PPC OS 5 X 2 Std 1 Std";
        heat = 10;
        damage = 2;       // Per sub-beam
        rackSize = 6;     // 6 sub-beams
        shortRange = 7;
        mediumRange = 10;
        longRange = 13;
        extremeRange = 18;
        waterShortRange = 5;
        waterMediumRange = 7;
        waterLongRange = 9;
        waterExtremeRange = 12;
        tonnage = 6.0;
        criticalSlots = 3;
        bv = 205;
        cost = 350000;
        shortAV = 12;
        medAV = 12;
        maxRange = RANGE_MED;
        explosive = true;
        atClass = CLASS_PPC;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3130, 3135, 3140, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
