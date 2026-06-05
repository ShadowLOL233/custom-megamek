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

/** Outer Sphere Streak SRM 8 - lock-on full salvo; better than IS Streak, short of Clan. New caliber. */
public class OSStreakSRM8 extends StreakSRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSStreakSRM8() {
        super();
        name = "Streak SRM 8";
        setInternalName("OSStreakSRM8");
        addLookupName("OS Streak SRM-8");
        addLookupName("OS Streak SRM 8");
        sortingName = "Missile OS 3 4 1 08";
        heat = 5;
        rackSize = 8;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 5.0;
        criticalSlots = 3;
        bv = 120;
        cost = 108000;
        shortAV = 16;
        maxRange = RANGE_SHORT;
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
