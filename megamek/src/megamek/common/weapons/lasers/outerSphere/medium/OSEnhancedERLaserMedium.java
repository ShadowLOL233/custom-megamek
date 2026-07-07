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
import megamek.common.weapons.lasers.LaserWeapon;

/**
 * Outer Sphere Advanced ER Medium Laser
 * Damage 8 / Heat 6 / Range 5-10-15 / -1 ToHit, 1t / 1 crit
 */
public class OSEnhancedERLaserMedium extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedERLaserMedium() {
        super();
        name = "Enhanced ER Medium Laser";
        setInternalName("OSEnhancedERMediumLaser");
        addLookupName("OS Advance ER Medium Laser");
        addLookupName("OSEnhancedERLaserMedium");
        sortingName = "Laser OS 03 ER 3 Enh B";
        heat = 6;
        damage = 8;
        toHitModifier = -1;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        waterShortRange = 3;
        waterMediumRange = 6;
        waterLongRange = 9;
        waterExtremeRange = 12;
        tonnage = 1.0;
        criticalSlots = 1;
        bv = 145;
        cost = 200000;
        shortAV = 8;
        medAV = 8;
        longAV = 8;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F)
              .setISAdvancement(3000, 3025, 3050, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
