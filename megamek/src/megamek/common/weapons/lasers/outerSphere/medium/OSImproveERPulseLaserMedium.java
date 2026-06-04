/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.lasers.outerSphere.medium;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.weapons.lasers.PulseLaserWeapon;

/**
 * Outer Sphere Improved ER Medium Pulse Laser
 * Damage 8 / Heat 8 / Range 4-8-12 / -2 ToHit, 2t / 2 crit
 */
public class OSImproveERPulseLaserMedium extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveERPulseLaserMedium() {
        super();
        name = "Improve ER Medium Pulse Laser";
        setInternalName("OSImproveERMediumPulseLaser");
        addLookupName("OS Improve ER Medium Pulse Laser");
        addLookupName("OSImproveERPulseLaserMedium");
        sortingName = "Laser OS 07 ERPulse 2 Imp B";
        heat = 8;
        damage = 8;
        toHitModifier = -2;
        shortRange = 4;
        mediumRange = 8;
        longRange = 12;
        extremeRange = 16;
        waterShortRange = 3;
        waterMediumRange = 5;
        waterLongRange = 8;
        waterExtremeRange = 10;
        tonnage = 2.0;
        criticalSlots = 2;
        bv = 158;
        cost = 250000;
        shortAV = 8;
        medAV = 8;
        longAV = 8;
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
