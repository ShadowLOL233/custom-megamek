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
 * Inner Sphere Burst-Fire AC/10 (MW5 "AC/10 BF") — flavor sidegrade of the standard AC/10, mechanically
 * identical except for a range-bracket to-hit gradient (+0 short/med, -1 at long range, applied in
 * ComputeToHit). All stats mirror the standard IS AC/10.
 */
public class ISBurstFireAC10 extends ISBurstFireACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public ISBurstFireAC10() {
        super();
        name = "AC/10 BF";
        setInternalName("ISBurstFireAC10");
        addLookupName("IS Burst-Fire AC/10");
        addLookupName("AC/10 BF");
        addLookupName("IS BF AC/10");
        sortingName = "AC/10 BF";
        heat = 3;
        damage = 10;
        rackSize = 10;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 12.0;
        criticalSlots = 7;
        bv = 123;
        cost = 200000;
        shortAV = 10;
        medAV = 10;
        maxRange = RANGE_MED;
        explosionDamage = damage;
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
