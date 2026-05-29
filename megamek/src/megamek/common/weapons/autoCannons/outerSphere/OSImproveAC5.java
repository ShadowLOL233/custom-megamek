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
 * Outer Sphere Improve AC/5
 * Damage 6 / Heat 1 / Range 6-12-18 / 7t / 3 crit / ammo-fed
 *
 * Lighter, cooler, up-gunned AC/5 (6 damage vs 5) at standard AC/5 range.
 */
public class OSImproveAC5 extends ACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveAC5() {
        super();
        name = "Improve AC/5";
        setInternalName("OSImproveAC5");
        addLookupName("OS Improve AC/5");
        addLookupName("Improve Autocannon/5");
        sortingName = "AC OS Imp 05";
        ammoType = AmmoType.AmmoTypeEnum.AC_IMP_OS;
        heat = 1;
        damage = 6;
        rackSize = 5;
        minimumRange = 0;
        shortRange = 6;
        mediumRange = 12;
        longRange = 18;
        extremeRange = 24;
        tonnage = 7.0;
        criticalSlots = 3;
        bv = 84;
        cost = 150000;
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
