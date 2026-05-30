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
import megamek.common.weapons.lasers.PulseLaserWeapon;

/**
 * Outer Sphere Heavy Small Pulse Laser
 * Damage 4 / Heat 3 / Range 1-2-3 / -2 ToHit, 0.5t / 1 crit
 */
public class OSHeavyPulseLaserSmall extends PulseLaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyPulseLaserSmall() {
        super();
        name = "Heavy Small Pulse Laser";
        setInternalName("OSHeavySmallPulseLaser");
        addLookupName("OS Heavy Small Pulse Laser");
        addLookupName("OSHeavyPulseLaserSmall");
        sortingName = "Laser OS 06 HeavyPulse 1 Std A";
        heat = 3;
        damage = 4;
        toHitModifier = -2;
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
        bv = 22;
        cost = 40000;
        shortAV = 4;
        maxRange = RANGE_SHORT;
        flags = flags.or(WeaponTypeFlag.HEAVY_LASER);
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3130, 3135, 3140, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
