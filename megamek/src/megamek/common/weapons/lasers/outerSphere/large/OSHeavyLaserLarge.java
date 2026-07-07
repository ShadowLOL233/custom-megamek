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
import megamek.common.weapons.lasers.LaserWeapon;

/**
 * Outer Sphere Heavy Large Laser
 * Damage 16 / Heat 18 / Range 5-10-15 / +1 ToHit, 4t / 3 crit
 */
public class OSHeavyLaserLarge extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyLaserLarge() {
        super();
        name = "Heavy Large Laser";
        setInternalName("OSHeavyLargeLaser");
        addLookupName("OS Heavy Large Laser");
        addLookupName("OSHeavyLaserLarge");
        sortingName = "Laser OS 02 Heavy 1 Std D";
        heat = 18;
        damage = 16;
        toHitModifier = 1;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        waterShortRange = 3;
        waterMediumRange = 6;
        waterLongRange = 9;
        waterExtremeRange = 12;
        tonnage = 4.0;
        criticalSlots = 3;
        bv = 244;
        cost = 350000;
        shortAV = 16;
        medAV = 16;
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
