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

/** Outer Sphere Streak LRM 20 - lock-on, all-or-nothing full salvo; OS tech based on Improve LRM. */
public class OSStreakLRM20 extends StreakLRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSStreakLRM20() {
        super();
        name = "Streak LRM 20";
        setInternalName("OSStreakLRM20");
        addLookupName("OS Streak LRM-20");
        addLookupName("OS Streak LRM 20");
        sortingName = "Missile OS 1 4 1 20";
        heat = 6;
        rackSize = 20;
        minimumRange = 6;
        shortRange = 7;
        mediumRange = 14;
        longRange = 24;
        extremeRange = 30;
        tonnage = 11.5;
        criticalSlots = 5;
        bv = 363;
        cost = 240000;
        shortAV = 20;
        medAV = 20;
        longAV = 20;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3060, 3070, 3080, DATE_NONE, DATE_NONE)
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
