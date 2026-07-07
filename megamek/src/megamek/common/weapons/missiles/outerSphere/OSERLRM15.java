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

/** Outer Sphere Extended Range LRM 15 - 12/22/38 reach, no to-hit penalty, Artemis IV capable. */
public class OSERLRM15 extends ExtendedLRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERLRM15() {
        super();
        name = "ER LRM 15";
        setInternalName("OSERLRM15");
        addLookupName("OS ER LRM-15");
        addLookupName("OS ER LRM 15");
        sortingName = "Missile OS 1 3 1 15";
        heat = 9;
        rackSize = 15;
        tonnage = 9.0;
        criticalSlots = 5;
        bv = 156;
        cost = 350000;
        shortAV = 9;
        medAV = 9;
        longAV = 9;
        extAV = 9;
        flags = flags.or(F_ARTEMIS_COMPATIBLE);
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3060, 3070, 3080, DATE_NONE, DATE_NONE)
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
