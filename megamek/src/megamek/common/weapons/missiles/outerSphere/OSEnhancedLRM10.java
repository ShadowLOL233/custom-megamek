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

/** Outer Sphere Enhanced LRM 10 - Clan-weight launcher with no minimum range (no dead zone). */
public class OSEnhancedLRM10 extends LRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedLRM10() {
        super();
        name = "Enhanced LRM 10";
        setInternalName("OSEnhancedLRM10");
        addLookupName("OSAdvanceLRM10");
        addLookupName("Advance LRM 10");
        addLookupName("OS Advance LRM-10");
        addLookupName("OS Advance LRM 10");
        sortingName = "Missile OS 1 1 3 10";
        heat = 4;
        rackSize = 10;
        minimumRange = 0;
        tonnage = 2.5;
        criticalSlots = 1;
        bv = 102;
        cost = 150000;
        shortAV = 6;
        medAV = 6;
        longAV = 6;
        maxRange = RANGE_LONG;
        flags = flags.andNot(F_PROTO_WEAPON);
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
