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
 * Outer Sphere Improved ER Small Pulse Laser
 * Damage 4 / Heat 3 / Range 3-5-7 / -2 ToHit, 1t / 1 crit
 */
public class OSImproveERPulseLaserSmall extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveERPulseLaserSmall() {
        super();
        name = "Improve ER Small Pulse Laser";
        setInternalName("OSImproveERSmallPulseLaser");
        addLookupName("OS Improve ER Small Pulse Laser");
        addLookupName("OSImproveERPulseLaserSmall");
        sortingName = "Laser OS 07 ERPulse 2 Imp A";
        heat = 3;
        damage = 4;
        toHitModifier = -2;
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
        bv = 62;
        cost = 75000;
        shortAV = 4;
        medAV = 4;
        maxRange = RANGE_MED;
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
