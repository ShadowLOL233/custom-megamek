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
import megamek.common.weapons.ppc.PPCWeapon;

/**
 * Outer Sphere Heavy PPC
 * Damage 18 / Heat 18 / Range 6-12-18 / min 3, 8t / 4 crit
 *
 * OS variant of the Heavy PPC at standard PPC range. Higher damage and lower
 * tonnage than the canonical IS Heavy PPC (15/15/10t) at the same 6-12-18 range,
 * reflecting OS's technological edge over IS-tech contemporaries.
 */
public class OSHeavyPPC extends PPCWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyPPC() {
        super();
        name = "Heavy PPC";
        setInternalName("OSHeavyPPC");
        addLookupName("OS Heavy PPC");
        sortingName = "PPC OS 1 Std 3 Hvy 1 Std";
        heat = 18;
        damage = 18;
        minimumRange = 3;
        shortRange = 6;
        mediumRange = 12;
        longRange = 18;
        extremeRange = 24;
        waterShortRange = 4;
        waterMediumRange = 7;
        waterLongRange = 10;
        waterExtremeRange = 15;
        tonnage = 8.0;
        criticalSlots = 4;
        bv = 380;
        cost = 500000;
        shortAV = 18;
        medAV = 18;
        maxRange = RANGE_MED;
        explosive = true;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
