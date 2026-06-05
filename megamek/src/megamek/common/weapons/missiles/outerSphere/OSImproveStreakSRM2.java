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

/** Outer Sphere Improve Streak SRM 2 - Clan-grade Streak SRM: range 4/8/12, Clan weight. */
public class OSImproveStreakSRM2 extends StreakSRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveStreakSRM2() {
        super();
        name = "Improve Streak SRM 2";
        setInternalName("OSImproveStreakSRM2");
        addLookupName("OS Improve Streak SRM-2");
        addLookupName("OS Improve Streak SRM 2");
        sortingName = "Missile OS 3 4 2 02";
        heat = 2;
        rackSize = 2;
        shortRange = 4;
        mediumRange = 8;
        longRange = 12;
        extremeRange = 16;
        tonnage = 1.0;
        criticalSlots = 1;
        bv = 40;
        cost = 30000;
        shortAV = 4;
        medAV = 4;
        maxRange = RANGE_MED;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
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
