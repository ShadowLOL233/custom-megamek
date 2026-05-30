/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.lasers.outerSphere.large;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.weapons.lasers.PulseLaserWeapon;

/**
 * Outer Sphere Advanced ER Large Pulse Laser
 * Damage 14 / Heat 18 / Range 6-14-22 / -3 ToHit, 6t / 4 crit
 */
public class OSAdvanceERPulseLaserLarge extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAdvanceERPulseLaserLarge() {
        super();
        name = "Advance ER Large Pulse Laser";
        setInternalName("OSAdvanceERLargePulseLaser");
        addLookupName("OS Advance ER Large Pulse Laser");
        addLookupName("OSAdvanceERPulseLaserLarge");
        sortingName = "Laser OS 07 ERPulse 3 Adv D";
        heat = 18;
        damage = 14;
        toHitModifier = -3;
        shortRange = 6;
        mediumRange = 14;
        longRange = 22;
        extremeRange = 30;
        waterShortRange = 4;
        waterMediumRange = 9;
        waterLongRange = 14;
        waterExtremeRange = 20;
        tonnage = 6.0;
        criticalSlots = 4;
        bv = 350;
        cost = 500000;
        shortAV = 14;
        medAV = 14;
        longAV = 14;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F)
              .setISAdvancement(3140, 3145, 3150, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.EXPERIMENTAL);
    }
}
