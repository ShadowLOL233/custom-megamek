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

/** Outer Sphere Improve LRM 5 - canon LRM combat profile, OS-light launcher (IS x0.7 tonnage). */
public class OSImproveLRM5 extends LRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveLRM5() {
        super();
        name = "Improve LRM 5";
        setInternalName("OSImproveLRM5");
        addLookupName("OS Improve LRM-5");
        addLookupName("OS Improve LRM 5");
        sortingName = "Missile OS 1 1 2 05";
        heat = 2;
        rackSize = 5;
        minimumRange = 6;
        tonnage = 1.5;
        criticalSlots = 1;
        bv = 52;
        longRange = 24;
        extremeRange = 30;
        cost = 30000;
        shortAV = 3;
        medAV = 3;
        longAV = 3;
        maxRange = RANGE_LONG;
        flags = flags.andNot(F_PROTO_WEAPON).or(F_OS_LRM_LONG_SPEC);
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2900, 2930, 2960, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
