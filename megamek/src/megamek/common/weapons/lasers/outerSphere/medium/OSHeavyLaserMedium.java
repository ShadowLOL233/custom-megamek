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
import megamek.common.weapons.lasers.LaserWeapon;

/**
 * Outer Sphere Heavy Medium Laser
 * Damage 10 / Heat 7 / Range 3-6-9 / +1 ToHit, 1t / 2 crit
 */
public class OSHeavyLaserMedium extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyLaserMedium() {
        super();
        name = "Heavy Medium Laser";
        setInternalName("OSHeavyMediumLaser");
        addLookupName("OS Heavy Medium Laser");
        addLookupName("OSHeavyLaserMedium");
        sortingName = "Laser OS 02 Heavy 1 Std B";
        heat = 7;
        damage = 10;
        toHitModifier = 1;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        waterShortRange = 2;
        waterMediumRange = 4;
        waterLongRange = 6;
        waterExtremeRange = 8;
        tonnage = 1.0;
        criticalSlots = 2;
        bv = 76;
        cost = 100000;
        shortAV = 10;
        medAV = 10;
        maxRange = RANGE_MED;
        flags = flags.or(WeaponTypeFlag.HEAVY_LASER);
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2805, 2820, 2840, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
