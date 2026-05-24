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
import megamek.common.weapons.lasers.PulseLaserWeapon;

/**
 * Outer Sphere ER Medium Pulse Laser
 * Damage 7 / Heat 6 / Range 4-7-11 / -1 ToHit, 2t / 2 crit
 */
public class OSERPulseLaserMedium extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERPulseLaserMedium() {
        super();
        name = "ER Medium Pulse Laser";
        setInternalName("OSERMediumPulseLaser");
        addLookupName("OS ER Medium Pulse Laser");
        addLookupName("OSERPulseLaserMedium");
        sortingName = "Laser ER Pulse B";
        heat = 6;
        damage = 7;
        toHitModifier = -1;
        shortRange = 4;
        mediumRange = 7;
        longRange = 11;
        extremeRange = 14;
        waterShortRange = 2;
        waterMediumRange = 5;
        waterLongRange = 7;
        waterExtremeRange = 9;
        tonnage = 2.0;
        criticalSlots = 2;
        bv = 130;
        cost = 180000;
        shortAV = 7;
        medAV = 7;
        longAV = 7;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3125, 3128, 3132, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
