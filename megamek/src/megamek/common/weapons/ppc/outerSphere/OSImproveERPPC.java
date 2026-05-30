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
 * Outer Sphere Improved ER PPC
 * Damage 12 / Heat 14 / Range 7-14-23 / no min, 7t / 3 crit
 */
public class OSImproveERPPC extends PPCWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveERPPC() {
        super();
        name = "Improve ER PPC";
        setInternalName("OSImproveERPPC");
        addLookupName("OS Improve ER PPC");
        addLookupName("OSImpERPPC");
        addLookupName("OS Imp. ER PPC");
        sortingName = "PPC OS 2 ER 2 Std 2 Imp";
        heat = 14;
        damage = 12;
        shortRange = 7;
        mediumRange = 14;
        longRange = 23;
        extremeRange = 30;
        waterShortRange = 4;
        waterMediumRange = 9;
        waterLongRange = 15;
        waterExtremeRange = 20;
        tonnage = 7.0;
        criticalSlots = 3;
        bv = 290;
        cost = 400000;
        shortAV = 12;
        medAV = 12;
        longAV = 12;
        maxRange = RANGE_LONG;
        explosive = true;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3125, 3130, 3135, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
