/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.missiles.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.weapons.lrms.StreakLRMWeapon;

/** Outer Sphere Improve Streak LRM 5 - Clan-weight refinement of the OS Streak LRM (same profile). */
public class OSImproveStreakLRM5 extends StreakLRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveStreakLRM5() {
        super();
        name = "Improve Streak LRM 5";
        setInternalName("OSImproveStreakLRM5");
        addLookupName("OS Improve Streak LRM-5");
        addLookupName("OS Improve Streak LRM 5");
        sortingName = "Missile OS 1 4 2 05";
        heat = 2;
        rackSize = 5;
        minimumRange = 6;
        shortRange = 7;
        mediumRange = 14;
        longRange = 21;
        extremeRange = 28;
        tonnage = 2.0;
        criticalSlots = 1;
        bv = 87;
        cost = 78000;
        shortAV = 5;
        medAV = 5;
        longAV = 5;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2900, 2930, 2960, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }

    @Override
    public String getSortingName() {
        return sortingName;
    }
}
