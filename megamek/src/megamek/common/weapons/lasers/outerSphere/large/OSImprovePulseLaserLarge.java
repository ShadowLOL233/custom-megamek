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
 * Outer Sphere Improved Large Pulse Laser
 * Damage 9 / Heat 10 / Range 3-7-10 / -2 ToHit, 6t / 2 crit
 */
public class OSImprovePulseLaserLarge extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImprovePulseLaserLarge() {
        super();
        name = "Improve Large Pulse Laser";
        setInternalName("OSImproveLargePulseLaser");
        addLookupName("OS Improve Large Pulse Laser");
        addLookupName("OSImprovePulseLaserLarge");
        sortingName = "Laser OS 05 Pulse 2 Imp D";
        heat = 10;
        damage = 9;
        toHitModifier = -2;
        shortRange = 3;
        mediumRange = 7;
        longRange = 10;
        extremeRange = 15;
        waterShortRange = 2;
        waterMediumRange = 5;
        waterLongRange = 7;
        waterExtremeRange = 10;
        tonnage = 6.0;
        criticalSlots = 2;
        bv = 130;
        cost = 200000;
        shortAV = 9;
        medAV = 9;
        maxRange = RANGE_MED;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2900, 2930, 2960, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
