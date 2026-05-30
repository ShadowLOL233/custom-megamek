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
 * Outer Sphere Improve AC/2
 * Damage 3 / Heat 1 / Range 8-16-24 / 5t / 1 crit / ammo-fed
 *
 * Lightened, slightly up-gunned standard autocannon. Effectively an "AC/3" in an
 * AC/2 mount - more damage, lighter and one fewer crit than the canon AC/2.
 */
public class OSImproveAC2 extends ACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveAC2() {
        super();
        name = "Improve AC/2";
        setInternalName("OSImproveAC2");
        addLookupName("OS Improve AC/2");
        addLookupName("Improve Autocannon/2");
        sortingName = "AC OS 1 Std 2 Imp 02";
        ammoType = AmmoType.AmmoTypeEnum.AC_IMP_OS;
        heat = 1;
        damage = 3;
        rackSize = 2;
        minimumRange = 0;
        shortRange = 8;
        mediumRange = 16;
        longRange = 24;
        extremeRange = 32;
        tonnage = 5.0;
        criticalSlots = 1;
        bv = 56;
        cost = 95000;
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
