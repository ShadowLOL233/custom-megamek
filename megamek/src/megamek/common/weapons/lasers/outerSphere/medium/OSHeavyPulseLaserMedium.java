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
 * Outer Sphere Heavy Medium Pulse Laser
 * Damage 7 / Heat 5 / Range 2-4-5 / -2 ToHit, 1.5t / 2 crit
 */
public class OSHeavyPulseLaserMedium extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyPulseLaserMedium() {
        super();
        name = "Heavy Medium Pulse Laser";
        setInternalName("OSHeavyMediumPulseLaser");
        addLookupName("OS Heavy Medium Pulse Laser");
        addLookupName("OSHeavyPulseLaserMedium");
        sortingName = "Laser OS 06 HeavyPulse 1 Std B";
        heat = 5;
        damage = 7;
        toHitModifier = -2;
        shortRange = 2;
        mediumRange = 4;
        longRange = 5;
        extremeRange = 8;
        waterShortRange = 1;
        waterMediumRange = 2;
        waterLongRange = 3;
        waterExtremeRange = 4;
        tonnage = 1.5;
        criticalSlots = 2;
        bv = 75;
        cost = 150000;
        shortAV = 7;
        medAV = 7;
        maxRange = RANGE_SHORT;
        flags = flags.or(WeaponTypeFlag.HEAVY_LASER);
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3060, 3070, 3080, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
