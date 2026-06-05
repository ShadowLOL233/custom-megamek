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

/** Outer Sphere Improve SRM 2 - canon SRM combat profile, OS-light launcher (IS x0.7 tonnage). */
public class OSImproveSRM2 extends SRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveSRM2() {
        super();
        name = "Improve SRM 2";
        setInternalName("OSImproveSRM2");
        addLookupName("OS Improve SRM-2");
        addLookupName("OS Improve SRM 2");
        sortingName = "Missile OS 3 1 2 02";
        heat = 2;
        rackSize = 2;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 0.75;
        criticalSlots = 1;
        bv = 21;
        cost = 10000;
        shortAV = 3;
        maxRange = RANGE_SHORT;
        flags = flags.andNot(F_PROTO_WEAPON);
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
