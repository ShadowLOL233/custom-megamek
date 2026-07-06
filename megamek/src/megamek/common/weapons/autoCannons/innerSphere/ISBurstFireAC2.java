/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.autoCannons.innerSphere;

import java.io.Serial;

import megamek.common.alphaStrike.AlphaStrikeElement;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.Mounted;

/**
 * Inner Sphere Burst-Fire AC/2 (MW5 "AC/2 BF") — flavor sidegrade of the standard AC/2, mechanically
 * identical except for a range-bracket to-hit gradient (+0 short/med, -1 at long range, applied in
 * ComputeToHit). All stats mirror the standard IS AC/2.
 */
public class ISBurstFireAC2 extends ISBurstFireACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public ISBurstFireAC2() {
        super();
        name = "AC/2 BF";
        setInternalName("ISBurstFireAC2");
        addLookupName("IS Burst-Fire AC/2");
        addLookupName("AC/2 BF");
        addLookupName("IS BF AC/2");
        sortingName = "AC/02 BF";
        heat = 1;
        damage = 2;
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
        explosive = true; // when firing incendiary ammo
        shortAV = 2;
        medAV = 2;
        longAV = 2;
        maxRange = RANGE_LONG;
        explosionDamage = damage;
        techAdvancement.setTechBase(TechBase.IS)
              .setIntroLevel(true)
              .setTechRating(TechRating.C)
              .setAvailability(AvailabilityValue.C, AvailabilityValue.D, AvailabilityValue.D, AvailabilityValue.D)
              .setISAdvancement(2290, 2300, 2305, DATE_NONE, DATE_NONE)
              .setISApproximate(false, false, false, false, false)
              .setClanAdvancement(2290, 2300, 2305, 2850, DATE_NONE)
              .setClanApproximate(false, false, false, true, false);
    }

    @Override
    public double getBattleForceDamage(int range, Mounted<?> ignore) {
        return range == AlphaStrikeElement.SHORT_RANGE ? 0.132 : 0.2;
    }
}
