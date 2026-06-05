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
import megamek.common.weapons.missiles.ATMWeapon;

/** Outer Sphere ATM 3 - MML upper-tier specialization firing SRTM / APTM / LRTM payloads. */
public class OSATM3 extends OSATMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSATM3() {
        super();
        name = "ATM 3";
        setInternalName("OSATM3");
        addLookupName("OS ATM-3");
        addLookupName("OS ATM 3");
        heat = 2;
        rackSize = 3;
        minimumRange = 4;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 1.5;
        criticalSlots = 2;
        bv = 53;
        cost = 50000;
        shortAV = 4;
        medAV = 4;
        maxRange = RANGE_MED;
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
        return "Missile OS 5 1 1 03";
    }
}
