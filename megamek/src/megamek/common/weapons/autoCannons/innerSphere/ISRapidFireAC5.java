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
 * Inner Sphere Rapid-Fire AC/5 (MW5 "AC/5 RF") — flavor sidegrade of the standard AC/5 (same
 * BV / tonnage / ammo). Fixed 3-round burst of 3 damage each; cluster-hits (avg 2.0) → ~6 effective
 * (a deliberate ~120% over-tune, since 5 does not halve to an integer); one to-hit, no jam. BV kept at
 * the standard AC/5. Tech fields mirror the standard IS AC/5.
 */
public class ISRapidFireAC5 extends ISRapidFireACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public ISRapidFireAC5() {
        super();
        name = "AC/5 RF";
        setInternalName("ISRapidFireAC5");
        addLookupName("IS Rapid-Fire AC/5");
        addLookupName("AC/5 RF");
        addLookupName("IS RF AC/5");
        sortingName = "AC/05 RF";
        heat = 0;
        damage = 3;
        rackSize = 5;
        minimumRange = 3;
        shortRange = 6;
        mediumRange = 12;
        longRange = 18;
        extremeRange = 24;
        tonnage = 8.0;
        criticalSlots = 4;
        bv = 70;
        cost = 125000;
        techAdvancement.setTechBase(TechBase.IS)
              .setIntroLevel(true)
              .setTechRating(TechRating.C)
              .setAvailability(AvailabilityValue.C, AvailabilityValue.C, AvailabilityValue.D, AvailabilityValue.D)
              .setISAdvancement(2240, 2250, 2255, DATE_NONE, DATE_NONE)
              .setISApproximate(false, false, false, false, false)
              .setClanAdvancement(2240, 2250, 2255, 2850, DATE_NONE)
              .setClanApproximate(false, false, false, true, false);
    }
}
