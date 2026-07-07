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

/** Outer Sphere Improve LRM 15 - canon LRM combat profile, OS-light launcher (IS x0.7 tonnage). */
public class OSImproveLRM15 extends LRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveLRM15() {
        super();
        name = "Improve LRM 15";
        setInternalName("OSImproveLRM15");
        addLookupName("OS Improve LRM-15");
        addLookupName("OS Improve LRM 15");
        sortingName = "Missile OS 1 1 2 15";
        heat = 5;
        rackSize = 15;
        minimumRange = 6;
        tonnage = 5.0;
        criticalSlots = 3;
        bv = 136;
        cost = 175000;
        shortAV = 9;
        medAV = 9;
        longAV = 9;
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
