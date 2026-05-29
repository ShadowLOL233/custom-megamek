/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.autoCannons.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.AmmoType;
import megamek.common.weapons.autoCannons.ACWeapon;

/**
 * Outer Sphere Improve AC/20
 * Damage 20 / Heat 6 / Range 4-8-12 / 10t / 6 crit / ammo-fed
 *
 * Dramatically lightened AC/20 (10t/6 crit vs canon 14t/10 crit), cooler, and
 * longer-ranged (4-8-12 vs 3-6-9). A defining OS high-end brawler.
 */
public class OSImproveAC20 extends ACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveAC20() {
        super();
        name = "Improve AC/20";
        setInternalName("OSImproveAC20");
        addLookupName("OS Improve AC/20");
        addLookupName("Improve Autocannon/20");
        sortingName = "AC OS Imp 20";
        ammoType = AmmoType.AmmoTypeEnum.AC_IMP_OS;
        heat = 6;
        damage = 20;
        rackSize = 20;
        minimumRange = 0;
        shortRange = 4;
        mediumRange = 8;
        longRange = 12;
        extremeRange = 16;
        tonnage = 10.0;
        criticalSlots = 6;
        bv = 237;
        cost = 360000;
        explosionDamage = damage;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
