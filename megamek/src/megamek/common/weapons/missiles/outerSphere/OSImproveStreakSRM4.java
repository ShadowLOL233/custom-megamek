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
import megamek.common.weapons.srms.StreakSRMWeapon;

/** Outer Sphere Improve Streak SRM 4 - Clan-grade Streak SRM: range 4/8/12, Clan weight. */
public class OSImproveStreakSRM4 extends StreakSRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveStreakSRM4() {
        super();
        name = "Improve Streak SRM 4";
        setInternalName("OSImproveStreakSRM4");
        addLookupName("OS Improve Streak SRM-4");
        addLookupName("OS Improve Streak SRM 4");
        sortingName = "Missile OS 3 4 2 04";
        heat = 3;
        rackSize = 4;
        shortRange = 4;
        mediumRange = 8;
        longRange = 12;
        extremeRange = 16;
        tonnage = 2.0;
        criticalSlots = 1;
        bv = 79;
        cost = 60000;
        shortAV = 8;
        medAV = 8;
        maxRange = RANGE_MED;
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
