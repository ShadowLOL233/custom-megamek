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

/** Outer Sphere Improve LRM 10 - canon LRM combat profile, OS-light launcher (IS x0.7 tonnage). */
public class OSImproveLRM10 extends LRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveLRM10() {
        super();
        name = "Improve LRM 10";
        setInternalName("OSImproveLRM10");
        addLookupName("OS Improve LRM-10");
        addLookupName("OS Improve LRM 10");
        sortingName = "Missile OS 1 1 2 10";
        heat = 4;
        rackSize = 10;
        minimumRange = 6;
        tonnage = 3.0;
        criticalSlots = 2;
        bv = 90;
        cost = 100000;
        shortAV = 6;
        medAV = 6;
        longAV = 6;
        maxRange = RANGE_LONG;
        flags = flags.andNot(F_PROTO_WEAPON);
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
