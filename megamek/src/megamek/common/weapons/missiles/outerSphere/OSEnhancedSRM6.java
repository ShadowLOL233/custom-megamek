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
import megamek.common.weapons.srms.SRMWeapon;

/** Outer Sphere Enhanced SRM 6 - Clan-weight efficiency tier (same combat profile as Improve). */
public class OSEnhancedSRM6 extends SRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedSRM6() {
        super();
        name = "Enhanced SRM 6";
        setInternalName("OSEnhancedSRM6");
        addLookupName("OSAdvanceSRM6");
        addLookupName("Advance SRM 6");
        addLookupName("OS Advance SRM-6");
        addLookupName("OS Advance SRM 6");
        sortingName = "Missile OS 3 1 3 06";
        heat = 4;
        rackSize = 6;
        shortRange = 6;
        mediumRange = 8;
        longRange = 10;
        extremeRange = 12;
        tonnage = 1.5;
        criticalSlots = 1;
        bv = 59;
        cost = 104000;
        shortAV = 8;
        maxRange = RANGE_SHORT;
        flags = flags.andNot(F_PROTO_WEAPON).or(F_OS_SRM_SHORT_SPEC);
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3000, 3025, 3050, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
