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
 * Outer Sphere Light PPC
 * Damage 7 / Heat 7 / Range 6-12-18 / min 3, 3t / 2 crit
 * Higher damage than IS Light PPC, same range/weight.
 */
public class OSLightPPC extends PPCWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSLightPPC() {
        super();
        name = "Light PPC";
        setInternalName("OSLightPPC");
        addLookupName("OS Light PPC");
        sortingName = "PPC Light B";
        heat = 7;
        damage = 7;
        minimumRange = 3;
        shortRange = 6;
        mediumRange = 12;
        longRange = 18;
        extremeRange = 24;
        waterShortRange = 4;
        waterMediumRange = 7;
        waterLongRange = 10;
        waterExtremeRange = 15;
        tonnage = 3.0;
        criticalSlots = 2;
        bv = 125;
        cost = 180000;
        shortAV = 7;
        medAV = 7;
        maxRange = RANGE_MED;
        explosive = true;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.E, AvailabilityValue.D)
              .setISAdvancement(3120, 3125, 3130, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
