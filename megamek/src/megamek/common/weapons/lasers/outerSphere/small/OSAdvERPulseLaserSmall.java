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
public class OSAdvERPulseLaserSmall extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAdvERPulseLaserSmall() {
        super();
        name = "Adv. ER Small Pulse Laser";
        setInternalName("OSAdvERSmallPulseLaser");
        addLookupName("OS Adv. ER Small Pulse Laser");
        addLookupName("OSAdvERPulseLaserSmall");
        sortingName = "Laser AdvER Pulse A";
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
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F)
              .setISAdvancement(3140, 3145, 3150, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.EXPERIMENTAL);
    }
}
