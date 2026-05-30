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
 * Outer Sphere Heavy PPC-X
 * Cluster: 6 sub-beams × 4 damage = 24 max / Heat 15 / Range 7-10-13 / no min, 8t / 4 crit
 *
 * Heavy variant of the PPC-X — same cluster mechanic, doubled per-beam damage.
 */
public class OSHeavyPPCX extends PPCXWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyPPCX() {
        super();
        name = "Heavy PPC-X";
        setInternalName("OSHeavyPPCX");
        addLookupName("OS Heavy PPC-X");
        sortingName = "PPC OS 5 X 3 Hvy 1 Std";
        heat = 15;
        damage = 4;       // Per sub-beam
        rackSize = 6;     // 6 sub-beams
        shortRange = 7;
        mediumRange = 10;
        longRange = 13;
        extremeRange = 18;
        waterShortRange = 5;
        waterMediumRange = 7;
        waterLongRange = 9;
        waterExtremeRange = 12;
        tonnage = 8.0;
        criticalSlots = 4;
        bv = 350;
        cost = 600000;
        shortAV = 24;
        medAV = 24;
        maxRange = RANGE_MED;
        explosive = true;
        atClass = CLASS_PPC;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F)
              .setISAdvancement(3135, 3140, 3145, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
