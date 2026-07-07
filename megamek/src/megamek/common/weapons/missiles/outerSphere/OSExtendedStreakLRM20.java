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

/** Outer Sphere Extended Streak LRM 20 - lock-on full salvo at extended 12/22/38 reach. */
public class OSExtendedStreakLRM20 extends StreakLRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSExtendedStreakLRM20() {
        super();
        name = "Extended Streak LRM 20";
        setInternalName("OSExtendedStreakLRM20");
        addLookupName("OS Extended Streak LRM-20");
        addLookupName("OS Extended Streak LRM 20");
        sortingName = "Missile OS 1 4 3 20";
        heat = 12;
        rackSize = 20;
        minimumRange = 10;
        shortRange = 12;
        mediumRange = 22;
        longRange = 38;
        extremeRange = 57;
        tonnage = 16.0;
        criticalSlots = 7;
        bv = 400;
        cost = 400000;
        shortAV = 20;
        medAV = 20;
        longAV = 20;
        extAV = 20;
        maxRange = RANGE_EXT;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.G)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.F)
              .setISAdvancement(3045, 3050, 3055, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.EXPERIMENTAL);
    }

    @Override
    public String getSortingName() {
        return sortingName;
    }
}
