/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.autoCannons.innerSphere;

import java.io.Serial;

import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;

/**
 * Inner Sphere Rapid-Fire AC/2 (MW5 "AC/2 RF") — flavor sidegrade of the standard AC/2 (same
 * BV / tonnage / ammo). Fixed 3-round burst of 1 damage each; cluster-hits (avg 2.0) → ~2 effective;
 * one to-hit, no jam. Tech fields mirror the standard IS AC/2.
 */
public class ISRapidFireAC2 extends ISRapidFireACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public ISRapidFireAC2() {
        super();
        name = "AC/2 RF";
        setInternalName("ISRapidFireAC2");
        addLookupName("IS Rapid-Fire AC/2");
        addLookupName("AC/2 RF");
        addLookupName("IS RF AC/2");
        sortingName = "AC/02 RF";
        heat = 0;
        damage = 1;
        rackSize = 2;
        minimumRange = 4;
        shortRange = 8;
        mediumRange = 16;
        longRange = 24;
        extremeRange = 32;
        tonnage = 6.0;
        criticalSlots = 1;
        bv = 37;
        cost = 75000;
        techAdvancement.setTechBase(TechBase.IS)
              .setIntroLevel(true)
              .setTechRating(TechRating.C)
              .setAvailability(AvailabilityValue.C, AvailabilityValue.D, AvailabilityValue.D, AvailabilityValue.D)
              .setISAdvancement(2290, 2300, 2305, DATE_NONE, DATE_NONE)
              .setISApproximate(false, false, false, false, false)
              .setClanAdvancement(2290, 2300, 2305, 2850, DATE_NONE)
              .setClanApproximate(false, false, false, true, false);
    }
}
