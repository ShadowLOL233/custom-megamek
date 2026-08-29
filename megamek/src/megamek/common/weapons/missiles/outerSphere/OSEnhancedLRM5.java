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
import megamek.common.weapons.lrms.LRMWeapon;

/** Outer Sphere Enhanced LRM 5 - Clan-weight launcher with no minimum range (no dead zone). */
public class OSEnhancedLRM5 extends LRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedLRM5() {
        super();
        name = "Enhanced LRM 5";
        setInternalName("OSEnhancedLRM5");
        addLookupName("OSAdvanceLRM5");
        addLookupName("Advance LRM 5");
        addLookupName("OS Advance LRM-5");
        addLookupName("OS Advance LRM 5");
        sortingName = "Missile OS 1 1 3 05";
        heat = 2;
        rackSize = 5;
        minimumRange = 6;
        mediumRange = 10;
        tonnage = 1.0;
        criticalSlots = 1;
        bv = 59;
        longRange = 24;
        extremeRange = 30;
        cost = 45000;
        shortAV = 3;
        medAV = 3;
        longAV = 3;
        maxRange = RANGE_LONG;
        flags = flags.andNot(F_PROTO_WEAPON).or(F_OS_LRM_LONG_SPEC);
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
