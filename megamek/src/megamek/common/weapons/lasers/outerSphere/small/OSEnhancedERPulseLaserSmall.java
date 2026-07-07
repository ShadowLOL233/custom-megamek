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
import megamek.common.weapons.lasers.PulseLaserWeapon;

/**
 * Outer Sphere Advanced ER Small Pulse Laser
 * Damage 5 / Heat 4 / Range 3-5-7 / -3 ToHit, 1t / 1 crit
 */
public class OSEnhancedERPulseLaserSmall extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedERPulseLaserSmall() {
        super();
        name = "Enhanced ER Small Pulse Laser";
        setInternalName("OSEnhancedERSmallPulseLaser");
        addLookupName("OS Advance ER Small Pulse Laser");
        addLookupName("OSEnhancedERPulseLaserSmall");
        sortingName = "Laser OS 07 ERPulse 3 Enh A";
        heat = 4;
        damage = 5;
        toHitModifier = -3;
        shortRange = 3;
        mediumRange = 5;
        longRange = 7;
        extremeRange = 10;
        waterShortRange = 2;
        waterMediumRange = 3;
        waterLongRange = 5;
        waterExtremeRange = 6;
        tonnage = 1.0;
        criticalSlots = 1;
        bv = 90;
        cost = 120000;
        shortAV = 5;
        medAV = 5;
        maxRange = RANGE_MED;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.G)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F)
              .setISAdvancement(3000, 3025, 3050, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
