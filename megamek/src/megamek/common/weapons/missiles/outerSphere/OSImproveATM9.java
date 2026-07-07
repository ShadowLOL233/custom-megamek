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

/** Outer Sphere Improve ATM 9 - upgraded ATM (iATM-class), richer payloads, higher performance. */
public class OSImproveATM9 extends OSATMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveATM9() {
        super();
        name = "Improve ATM 9";
        setInternalName("OSImproveATM9");
        addLookupName("OS Improve ATM-9");
        addLookupName("OS Improve ATM 9");
        heat = 6;
        rackSize = 9;
        minimumRange = 4;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 5.5;
        criticalSlots = 4;
        bv = 247;
        cost = 375000;
        shortAV = 12;
        medAV = 12;
        maxRange = RANGE_MED;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2900, 2930, 2960, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }

    @Override
    public String getSortingName() {
        return "Missile OS 5 1 2 09";
    }
}
