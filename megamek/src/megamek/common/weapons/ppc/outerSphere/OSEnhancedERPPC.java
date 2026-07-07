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
 * Outer Sphere Advanced ER PPC
 * Damage 15 / Heat 15 / Range 7-14-23 / no min, 6t / 2 crit
 * Equivalent to Clan ER PPC — represents OS's parity with Clan tech.
 */
public class OSEnhancedERPPC extends PPCWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedERPPC() {
        super();
        name = "Enhanced ER PPC";
        setInternalName("OSEnhancedERPPC");
        addLookupName("OSAdvanceERPPC");
        addLookupName("Advance ER PPC");
        addLookupName("OS Advance ER PPC");
        addLookupName("OSAdvERPPC");
        addLookupName("OS Enh. ER PPC");
        sortingName = "PPC OS 2 ER 2 Std 3 Enh";
        heat = 15;
        damage = 15;
        shortRange = 7;
        mediumRange = 14;
        longRange = 23;
        extremeRange = 30;
        waterShortRange = 4;
        waterMediumRange = 9;
        waterLongRange = 15;
        waterExtremeRange = 20;
        tonnage = 6.0;
        criticalSlots = 2;
        bv = 412;
        cost = 450000;
        shortAV = 15;
        medAV = 15;
        longAV = 15;
        maxRange = RANGE_LONG;
        explosive = true;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F)
              .setISAdvancement(3000, 3025, 3050, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
