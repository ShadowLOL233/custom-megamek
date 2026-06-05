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

/** Outer Sphere Advance LRM 20 - Clan-weight launcher with no minimum range (no dead zone). */
public class OSAdvanceLRM20 extends LRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAdvanceLRM20() {
        super();
        name = "Advance LRM 20";
        setInternalName("OSAdvanceLRM20");
        addLookupName("OS Advance LRM-20");
        addLookupName("OS Advance LRM 20");
        sortingName = "Missile OS 1 1 3 20";
        heat = 6;
        rackSize = 20;
        minimumRange = 0;
        tonnage = 5.0;
        criticalSlots = 4;
        bv = 205;
        cost = 375000;
        shortAV = 12;
        medAV = 12;
        longAV = 12;
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
