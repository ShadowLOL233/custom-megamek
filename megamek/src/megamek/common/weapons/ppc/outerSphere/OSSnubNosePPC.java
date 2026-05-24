/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.ppc.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.weapons.ppc.PPCWeapon;

/**
 * Outer Sphere Snub-Nose PPC
 * Damage 10/8/6 by range / Heat 10 / Range 9-13-15 / no min, 6t / 2 crit
 * Slightly better long-range damage than IS Snub-Nose (5 → 6).
 */
public class OSSnubNosePPC extends PPCWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSSnubNosePPC() {
        super();
        name = "Snub-Nose PPC";
        setInternalName("OSSnubNosePPC");
        addLookupName("OS Snub-Nose PPC");
        sortingName = "PPC Snub B";
        heat = 10;
        damage = DAMAGE_VARIABLE;
        minimumRange = 0;
        shortRange = 9;
        mediumRange = 13;
        longRange = 15;
        extremeRange = 22;
        waterShortRange = 6;
        waterMediumRange = 8;
        waterLongRange = 9;
        waterExtremeRange = 13;
        damageShort = 10;
        damageMedium = 8;
        damageLong = 6;
        tonnage = 6.0;
        criticalSlots = 2;
        bv = 180;
        cost = 250000;
        maxRange = RANGE_MED;
        shortAV = 10;
        medAV = 8;
        explosive = true;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.E, AvailabilityValue.D)
              .setISAdvancement(3120, 3125, 3130, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }

    @Override
    public int getDamage(int range) {
        if (range <= shortRange) {
            return damageShort;
        }
        if (range <= mediumRange) {
            return damageMedium;
        }
        return damageLong;
    }
}
