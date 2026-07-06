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
 * Inner Sphere Burst-Fire AC/20 (MW5 "AC/20 BF") — flavor sidegrade of the standard AC/20, mechanically
 * identical except for a range-bracket to-hit gradient (+0 short/med, -1 at long range, applied in
 * ComputeToHit). All stats mirror the standard IS AC/20.
 */
public class ISBurstFireAC20 extends ISBurstFireACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public ISBurstFireAC20() {
        super();
        name = "AC/20 BF";
        setInternalName("ISBurstFireAC20");
        addLookupName("IS Burst-Fire AC/20");
        addLookupName("AC/20 BF");
        addLookupName("IS BF AC/20");
        sortingName = "AC/20 BF";
        heat = 7;
        damage = 20;
        rackSize = 20;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 14.0;
        criticalSlots = 10;
        bv = 178;
        cost = 300000;
        shortAV = 20;
        maxRange = RANGE_SHORT;
        explosionDamage = damage;
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
