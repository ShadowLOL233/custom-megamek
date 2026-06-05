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

/** Outer Sphere Improve LRM 20 - canon LRM combat profile, OS-light launcher (IS x0.7 tonnage). */
public class OSImproveLRM20 extends LRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveLRM20() {
        super();
        name = "Improve LRM 20";
        setInternalName("OSImproveLRM20");
        addLookupName("OS Improve LRM-20");
        addLookupName("OS Improve LRM 20");
        sortingName = "Missile OS 1 1 2 20";
        heat = 6;
        rackSize = 20;
        minimumRange = 6;
        tonnage = 7.0;
        criticalSlots = 5;
        bv = 181;
        cost = 250000;
        shortAV = 12;
        medAV = 12;
        longAV = 12;
        maxRange = RANGE_LONG;
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
