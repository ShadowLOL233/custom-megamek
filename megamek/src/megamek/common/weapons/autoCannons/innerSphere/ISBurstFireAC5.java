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
 * Inner Sphere Burst-Fire AC/5 (MW5 "AC/5 BF") — flavor sidegrade of the standard AC/5, mechanically
 * identical except for a range-bracket to-hit gradient (+0 short/med, -1 at long range, applied in
 * ComputeToHit). All stats mirror the standard IS AC/5.
 */
public class ISBurstFireAC5 extends ISBurstFireACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public ISBurstFireAC5() {
        super();
        name = "AC/5 BF";
        setInternalName("ISBurstFireAC5");
        addLookupName("IS Burst-Fire AC/5");
        addLookupName("AC/5 BF");
        addLookupName("IS BF AC/5");
        sortingName = "AC/05 BF";
        heat = 1;
        damage = 5;
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
        shortAV = 5;
        medAV = 5;
        maxRange = RANGE_MED;
        explosionDamage = damage;
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
