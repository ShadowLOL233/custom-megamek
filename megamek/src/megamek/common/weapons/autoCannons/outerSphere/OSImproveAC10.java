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
 * Outer Sphere Improve AC/10
 * Damage 10 / Heat 2 / Range 5-10-15 / 10t / 6 crit / ammo-fed
 *
 * Lighter (10 vs 12t), cooler (2 vs 3 heat), fewer-crit AC/10 at standard range.
 */
public class OSImproveAC10 extends ACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveAC10() {
        super();
        name = "Improve AC/10";
        setInternalName("OSImproveAC10");
        addLookupName("OS Improve AC/10");
        addLookupName("Improve Autocannon/10");
        sortingName = "AC OS Imp 10";
        ammoType = AmmoType.AmmoTypeEnum.AC_IMP_OS;
        heat = 2;
        damage = 10;
        rackSize = 10;
        minimumRange = 0;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 10.0;
        criticalSlots = 6;
        bv = 123;
        cost = 240000;
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
