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
import megamek.common.weapons.lrms.ExtendedLRMWeapon;

/** Outer Sphere Extended Range LRM 20 - 12/22/38 reach, no to-hit penalty, Artemis IV capable. */
public class OSERLRM20 extends ExtendedLRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERLRM20() {
        super();
        name = "ER LRM 20";
        setInternalName("OSERLRM20");
        addLookupName("OS ER LRM-20");
        addLookupName("OS ER LRM 20");
        sortingName = "Missile OS 1 3 1 20";
        heat = 12;
        rackSize = 20;
        tonnage = 13.0;
        criticalSlots = 6;
        bv = 210;
        cost = 500000;
        shortAV = 12;
        medAV = 12;
        longAV = 12;
        extAV = 12;
        flags = flags.or(F_ARTEMIS_COMPATIBLE);
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

    @Override
    public String getSortingName() {
        return sortingName;
    }
}
