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
 * Outer Sphere Heavy Snub-Nose PPC
 * Damage 15/9/6 by range / Heat 15 / Range 9-12-14 / no min, 8t / 4 crit
 */
public class OSHeavySnubNosePPC extends PPCWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavySnubNosePPC() {
        super();
        name = "Heavy Snub-Nose PPC";
        setInternalName("OSHeavySnubNosePPC");
        addLookupName("OS Heavy Snub-Nose PPC");
        sortingName = "PPC OS 3 Snub 3 Hvy 1 Std";
        heat = 15;
        damage = DAMAGE_VARIABLE;
        minimumRange = 0;
        shortRange = 9;
        mediumRange = 12;
        longRange = 14;
        extremeRange = 21;
        waterShortRange = 6;
        waterMediumRange = 8;
        waterLongRange = 9;
        waterExtremeRange = 13;
        damageShort = 15;
        damageMedium = 9;
        damageLong = 6;
        tonnage = 8.0;
        criticalSlots = 4;
        bv = 265;
        cost = 400000;
        maxRange = RANGE_MED;
        shortAV = 15;
        medAV = 9;
        explosive = true;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
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
