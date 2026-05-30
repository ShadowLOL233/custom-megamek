/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.lasers.outerSphere.small;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.WeaponTypeFlag;
import megamek.common.weapons.lasers.LaserWeapon;

/**
 * Outer Sphere ER Heavy Small Laser
 * Damage 6 / Heat 4 / Range 2-4-5 / +1 ToHit, 1.5t / 1 crit
 */
public class OSERHeavyLaserSmall extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERHeavyLaserSmall() {
        super();
        name = "ER Heavy Small Laser";
        setInternalName("OSERHeavySmallLaser");
        addLookupName("OS ER Heavy Small Laser");
        addLookupName("OSERHeavyLaserSmall");
        sortingName = "Laser OS 04 ERHeavy 1 Std A";
        heat = 4;
        damage = 6;
        toHitModifier = 1;
        shortRange = 2;
        mediumRange = 4;
        longRange = 5;
        extremeRange = 8;
        waterShortRange = 1;
        waterMediumRange = 2;
        waterLongRange = 3;
        waterExtremeRange = 4;
        tonnage = 1.5;
        criticalSlots = 1;
        bv = 22;
        cost = 40000;
        shortAV = 6;
        maxRange = RANGE_SHORT;
        flags = flags.or(WeaponTypeFlag.HEAVY_LASER);
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3130, 3134, 3140, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
