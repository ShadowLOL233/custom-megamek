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
import megamek.common.weapons.lasers.LaserWeapon;

/**
 * Outer Sphere Advanced ER Small Laser
 * Damage 5 / Heat 3 / Range 3-5-8 / -1 ToHit, 0.5t / 1 crit
 */
public class OSAdvERLaserSmall extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAdvERLaserSmall() {
        super();
        name = "Adv. ER Small Laser";
        setInternalName("OSAdvERSmallLaser");
        addLookupName("OS Adv. ER Small Laser");
        addLookupName("OSAdvERLaserSmall");
        sortingName = "Laser AdvER A";
        heat = 3;
        damage = 5;
        toHitModifier = -1;
        shortRange = 3;
        mediumRange = 5;
        longRange = 8;
        extremeRange = 10;
        waterShortRange = 2;
        waterMediumRange = 3;
        waterLongRange = 5;
        waterExtremeRange = 6;
        tonnage = 0.5;
        criticalSlots = 1;
        bv = 50;
        cost = 60000;
        shortAV = 5;
        medAV = 5;
        maxRange = RANGE_MED;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F)
              .setISAdvancement(3130, 3135, 3140, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
