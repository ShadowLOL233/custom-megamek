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
 * Outer Sphere Improved Heavy Small Laser
 * Damage 6 / Heat 3 / Range 1-2-3 / no ToHit, 0.5t / 1 crit
 */
public class OSImproveHeavyLaserSmall extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveHeavyLaserSmall() {
        super();
        name = "Improve Heavy Small Laser";
        setInternalName("OSImproveHeavySmallLaser");
        addLookupName("OS Improve Heavy Small Laser");
        addLookupName("OSImproveHeavyLaserSmall");
        sortingName = "Laser OS 02 Heavy 2 Imp A";
        heat = 3;
        damage = 6;
        shortRange = 1;
        mediumRange = 2;
        longRange = 3;
        extremeRange = 4;
        waterShortRange = 1;
        waterMediumRange = 2;
        waterLongRange = 2;
        waterExtremeRange = 4;
        tonnage = 0.5;
        criticalSlots = 1;
        bv = 19;
        cost = 25000;
        shortAV = 6;
        maxRange = RANGE_SHORT;
        flags = flags.or(WeaponTypeFlag.HEAVY_LASER);
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
