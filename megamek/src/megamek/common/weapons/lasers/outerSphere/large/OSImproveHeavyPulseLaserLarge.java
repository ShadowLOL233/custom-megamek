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
import megamek.common.equipment.WeaponTypeFlag;
import megamek.common.weapons.lasers.PulseLaserWeapon;

/**
 * Outer Sphere Improved Heavy Large Pulse Laser
 * Damage 13 / Heat 14 / Range 5-10-15 / -2 ToHit, 6.5t / 3 crit
 */
public class OSImproveHeavyPulseLaserLarge extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveHeavyPulseLaserLarge() {
        super();
        name = "Improve Heavy Large Pulse Laser";
        setInternalName("OSImproveHeavyLargePulseLaser");
        addLookupName("OS Improve Heavy Large Pulse Laser");
        addLookupName("OSImproveHeavyPulseLaserLarge");
        sortingName = "Laser OS 06 HeavyPulse 2 Imp D";
        heat = 14;
        damage = 13;
        toHitModifier = -2;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        waterShortRange = 3;
        waterMediumRange = 6;
        waterLongRange = 9;
        waterExtremeRange = 12;
        tonnage = 6.5;
        criticalSlots = 3;
        bv = 240;
        cost = 500000;
        shortAV = 13;
        medAV = 13;
        maxRange = RANGE_MED;
        flags = flags.or(WeaponTypeFlag.HEAVY_LASER);
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3135, 3140, 3145, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
