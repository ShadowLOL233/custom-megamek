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
 * Outer Sphere Improved ER Large Pulse Laser
 * Damage 11 / Heat 14 / Range 6-14-22 / -2 ToHit, 6.5t / 4 crit
 */
public class OSImproveERPulseLaserLarge extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveERPulseLaserLarge() {
        super();
        name = "Improve ER Large Pulse Laser";
        setInternalName("OSImproveERLargePulseLaser");
        addLookupName("OS Improve ER Large Pulse Laser");
        addLookupName("OSImproveERPulseLaserLarge");
        sortingName = "Laser OS 07 ERPulse 2 Imp D";
        heat = 14;
        damage = 11;
        toHitModifier = -2;
        shortRange = 6;
        mediumRange = 14;
        longRange = 22;
        extremeRange = 30;
        waterShortRange = 4;
        waterMediumRange = 9;
        waterLongRange = 14;
        waterExtremeRange = 20;
        tonnage = 6.5;
        criticalSlots = 4;
        bv = 240;
        cost = 350000;
        shortAV = 11;
        medAV = 11;
        longAV = 11;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3132, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
