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
 * Inner Sphere Rapid-Fire AC/10 (MW5 "AC/10 RF") — flavor sidegrade of the standard AC/10 (same
 * BV / tonnage / ammo). Fixed 3-round burst of 5 damage each; cluster-hits (avg 2.0) → ~10 effective;
 * one to-hit, no jam. Tech fields mirror the standard IS AC/10.
 */
public class ISRapidFireAC10 extends ISRapidFireACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public ISRapidFireAC10() {
        super();
        name = "AC/10 RF";
        setInternalName("ISRapidFireAC10");
        addLookupName("IS Rapid-Fire AC/10");
        addLookupName("AC/10 RF");
        addLookupName("IS RF AC/10");
        sortingName = "AC/10 RF";
        heat = 1;
        damage = 5;
        rackSize = 10;
        minimumRange = 0;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 12.0;
        criticalSlots = 7;
        bv = 123;
        cost = 200000;
        techAdvancement.setTechBase(TechBase.IS)
              .setIntroLevel(true)
              .setTechRating(TechRating.C)
              .setAvailability(AvailabilityValue.C, AvailabilityValue.D, AvailabilityValue.D, AvailabilityValue.D)
              .setISAdvancement(2443, 2460, 2465, DATE_NONE, DATE_NONE)
              .setISApproximate(false, false, false, false, false)
              .setClanAdvancement(2443, 2460, 2465, 2850, DATE_NONE)
              .setClanApproximate(false, false, false, true, false);
    }
}
