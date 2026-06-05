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

/** Outer Sphere Advance LRM 15 - Clan-weight launcher with no minimum range (no dead zone). */
public class OSAdvanceLRM15 extends LRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAdvanceLRM15() {
        super();
        name = "Advance LRM 15";
        setInternalName("OSAdvanceLRM15");
        addLookupName("OS Advance LRM-15");
        addLookupName("OS Advance LRM 15");
        sortingName = "Missile OS 1 1 3 15";
        heat = 5;
        rackSize = 15;
        minimumRange = 0;
        tonnage = 3.5;
        criticalSlots = 2;
        bv = 154;
        cost = 262500;
        shortAV = 9;
        medAV = 9;
        longAV = 9;
        maxRange = RANGE_LONG;
        flags = flags.andNot(F_PROTO_WEAPON);
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
