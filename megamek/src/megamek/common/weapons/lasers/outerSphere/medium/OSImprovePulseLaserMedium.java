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
 * Outer Sphere Improved Medium Pulse Laser
 * Damage 6 / Heat 4 / Range 3-5-7 / -2 ToHit, 2t / 1 crit
 */
public class OSImprovePulseLaserMedium extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImprovePulseLaserMedium() {
        super();
        name = "Improve Medium Pulse Laser";
        setInternalName("OSImproveMediumPulseLaser");
        addLookupName("OS Improve Medium Pulse Laser");
        addLookupName("OSImprovePulseLaserMedium");
        sortingName = "Laser OS 05 Pulse 2 Imp B";
        heat = 4;
        damage = 6;
        toHitModifier = -2;
        shortRange = 3;
        mediumRange = 5;
        longRange = 7;
        extremeRange = 10;
        waterShortRange = 2;
        waterMediumRange = 3;
        waterLongRange = 4;
        waterExtremeRange = 6;
        tonnage = 2.0;
        criticalSlots = 1;
        bv = 53;
        cost = 80000;
        shortAV = 6;
        medAV = 6;
        maxRange = RANGE_MED;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3120, 3125, 3130, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
