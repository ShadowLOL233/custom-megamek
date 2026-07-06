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
 * Inner Sphere Rapid-Fire AC/20 (MW5 "AC/20 RF") — flavor sidegrade of the standard AC/20 (same
 * BV / tonnage / ammo). Fixed 3-round burst of 10 damage each; cluster-hits (avg 2.0) → ~20 effective;
 * one to-hit, no jam. Tech fields mirror the standard IS AC/20.
 */
public class ISRapidFireAC20 extends ISRapidFireACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public ISRapidFireAC20() {
        super();
        name = "AC/20 RF";
        setInternalName("ISRapidFireAC20");
        addLookupName("IS Rapid-Fire AC/20");
        addLookupName("AC/20 RF");
        addLookupName("IS RF AC/20");
        sortingName = "AC/20 RF";
        heat = 2;
        damage = 10;
        rackSize = 20;
        minimumRange = 0;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 14.0;
        criticalSlots = 10;
        bv = 178;
        cost = 300000;
        techAdvancement.setTechBase(TechBase.IS)
              .setIntroLevel(true)
              .setTechRating(TechRating.C)
              .setAvailability(AvailabilityValue.D, AvailabilityValue.E, AvailabilityValue.D, AvailabilityValue.D)
              .setISAdvancement(2488, 2500, 2502, DATE_NONE, DATE_NONE)
              .setISApproximate(false, false, false, false, false)
              .setClanAdvancement(2488, 2500, 2502, 2850, DATE_NONE)
              .setClanApproximate(false, false, false, true, false);
    }
}
