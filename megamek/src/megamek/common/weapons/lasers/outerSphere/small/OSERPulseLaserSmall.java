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
 * Outer Sphere ER Small Pulse Laser
 * Damage 3 / Heat 3 / Range 2-4-6 / -1 ToHit, 1t / 1 crit
 */
public class OSERPulseLaserSmall extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERPulseLaserSmall() {
        super();
        name = "ER Small Pulse Laser";
        setInternalName("OSERSmallPulseLaser");
        addLookupName("OS ER Small Pulse Laser");
        addLookupName("OSERPulseLaserSmall");
        sortingName = "Laser OS 07 ERPulse 1 Std A";
        heat = 3;
        damage = 3;
        toHitModifier = -1;
        shortRange = 2;
        mediumRange = 4;
        longRange = 6;
        extremeRange = 8;
        waterShortRange = 1;
        waterMediumRange = 3;
        waterLongRange = 4;
        waterExtremeRange = 5;
        tonnage = 1.0;
        criticalSlots = 1;
        bv = 38;
        cost = 50000;
        shortAV = 3;
        medAV = 3;
        maxRange = RANGE_MED;
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
