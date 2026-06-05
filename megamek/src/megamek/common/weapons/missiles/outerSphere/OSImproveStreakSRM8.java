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

/** Outer Sphere Improve Streak SRM 8 - Clan-grade Streak SRM: range 4/8/12, Clan weight. New caliber. */
public class OSImproveStreakSRM8 extends StreakSRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveStreakSRM8() {
        super();
        name = "Improve Streak SRM 8";
        setInternalName("OSImproveStreakSRM8");
        addLookupName("OS Improve Streak SRM-8");
        addLookupName("OS Improve Streak SRM 8");
        sortingName = "Missile OS 3 4 2 08";
        heat = 5;
        rackSize = 8;
        shortRange = 4;
        mediumRange = 8;
        longRange = 12;
        extremeRange = 16;
        tonnage = 4.0;
        criticalSlots = 3;
        bv = 158;
        cost = 120000;
        shortAV = 16;
        medAV = 16;
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
