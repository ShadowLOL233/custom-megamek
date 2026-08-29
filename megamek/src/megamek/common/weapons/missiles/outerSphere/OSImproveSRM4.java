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

/** Outer Sphere Improve SRM 4 - canon SRM combat profile, OS-light launcher (IS x0.7 tonnage). */
public class OSImproveSRM4 extends SRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveSRM4() {
        super();
        name = "Improve SRM 4";
        setInternalName("OSImproveSRM4");
        addLookupName("OS Improve SRM-4");
        addLookupName("OS Improve SRM 4");
        sortingName = "Missile OS 3 1 2 04";
        heat = 3;
        rackSize = 4;
        shortRange = 6;
        mediumRange = 8;
        longRange = 10;
        extremeRange = 12;
        tonnage = 1.5;
        criticalSlots = 1;
        bv = 39;
        cost = 60000;
        shortAV = 5;
        maxRange = RANGE_SHORT;
        flags = flags.andNot(F_PROTO_WEAPON).or(F_OS_SRM_SHORT_SPEC);
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
