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
 * Outer Sphere ER Large Pulse Laser
 * Damage 10 / Heat 14 / Range 6-12-17 / -1 ToHit, 6.5t / 3 crit
 */
public class OSERPulseLaserLarge extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERPulseLaserLarge() {
        super();
        name = "ER Large Pulse Laser";
        setInternalName("OSERLargePulseLaser");
        addLookupName("OS ER Large Pulse Laser");
        addLookupName("OSERPulseLaserLarge");
        sortingName = "Laser OS 07 ERPulse 1 Std D";
        heat = 14;
        damage = 10;
        toHitModifier = -1;
        shortRange = 6;
        mediumRange = 12;
        longRange = 17;
        extremeRange = 24;
        waterShortRange = 4;
        waterMediumRange = 8;
        waterLongRange = 11;
        waterExtremeRange = 16;
        tonnage = 6.5;
        criticalSlots = 3;
        bv = 220;
        cost = 260000;
        shortAV = 10;
        medAV = 10;
        longAV = 10;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3125, 3128, 3132, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
