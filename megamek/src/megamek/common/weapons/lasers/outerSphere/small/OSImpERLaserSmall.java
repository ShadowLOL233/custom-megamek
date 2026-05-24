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
 * Outer Sphere Improved ER Small Laser
 * Damage 5 / Heat 2 / Range 2-4-6, 0.5t / 1 crit
 */
public class OSImpERLaserSmall extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImpERLaserSmall() {
        super();
        name = "Imp. ER Small Laser";
        setInternalName("OSImpERSmallLaser");
        addLookupName("OS Imp. ER Small Laser");
        addLookupName("OSImpERLaserSmall");
        sortingName = "Laser ImpER A";
        heat = 2;
        damage = 5;
        shortRange = 2;
        mediumRange = 4;
        longRange = 6;
        extremeRange = 8;
        waterShortRange = 1;
        waterMediumRange = 2;
        waterLongRange = 4;
        waterExtremeRange = 5;
        tonnage = 0.5;
        criticalSlots = 1;
        bv = 32;
        cost = 30000;
        shortAV = 5;
        maxRange = RANGE_SHORT;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3125, 3130, 3135, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
