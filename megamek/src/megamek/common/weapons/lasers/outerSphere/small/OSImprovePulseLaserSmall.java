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
 * Outer Sphere Improved Small Pulse Laser
 * Damage 3 / Heat 2 / Range 2-3-5 / -2 ToHit, 1t / 1 crit
 */
public class OSImprovePulseLaserSmall extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImprovePulseLaserSmall() {
        super();
        name = "Improve Small Pulse Laser";
        setInternalName("OSImproveSmallPulseLaser");
        addLookupName("OS Improve Small Pulse Laser");
        addLookupName("OSImprovePulseLaserSmall");
        sortingName = "Laser OS 05 Pulse 2 Imp A";
        heat = 2;
        damage = 3;
        toHitModifier = -2;
        shortRange = 2;
        mediumRange = 3;
        longRange = 5;
        extremeRange = 7;
        waterShortRange = 1;
        waterMediumRange = 2;
        waterLongRange = 3;
        waterExtremeRange = 4;
        tonnage = 1.0;
        criticalSlots = 1;
        bv = 17;
        cost = 20000;
        shortAV = 3;
        medAV = 3;
        maxRange = RANGE_MED;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3120, 3125, 3130, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
