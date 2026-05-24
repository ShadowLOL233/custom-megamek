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
import megamek.common.equipment.WeaponTypeFlag;
import megamek.common.weapons.lasers.PulseLaserWeapon;

/**
 * Outer Sphere Improved Heavy Medium Pulse Laser
 * Damage 8 / Heat 5 / Range 3-6-9 / -2 ToHit, 1.5t / 2 crit
 */
public class OSImpHeavyPulseLaserMedium extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImpHeavyPulseLaserMedium() {
        super();
        name = "Imp. Hvy Medium Pulse Laser";
        setInternalName("OSImpHeavyMediumPulseLaser");
        addLookupName("OS Imp. Hvy Medium Pulse Laser");
        addLookupName("OSImpHeavyPulseLaserMedium");
        sortingName = "Laser ImpHeavyPulse B";
        heat = 5;
        damage = 8;
        toHitModifier = -2;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        waterShortRange = 2;
        waterMediumRange = 4;
        waterLongRange = 6;
        waterExtremeRange = 8;
        tonnage = 1.5;
        criticalSlots = 2;
        bv = 100;
        cost = 220000;
        shortAV = 8;
        medAV = 8;
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
