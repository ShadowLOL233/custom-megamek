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
 * Outer Sphere Standard AC/5 — RoOS-manufactured replica of the upstream IS AC/5.
 * Stats identical to the IS reference; differentiated only by tech base.
 */
public class OSAC5 extends ACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAC5() {
        super();
        name = "AC/5";
        setInternalName("OSAC5");
        addLookupName("OS AC/5");
        addLookupName("OS Autocannon/5");
        addLookupName("Legion AC/5");
        sortingName = "AC OS 1 Std 1 Std 05";
        ammoType = AmmoType.AmmoTypeEnum.AC_STD_OS;
        heat = 1;
        damage = 5;
        rackSize = 5;
        minimumRange = 0;
        shortRange = 6;
        mediumRange = 12;
        longRange = 18;
        extremeRange = 24;
        tonnage = 8.0;
        criticalSlots = 4;
        bv = 70;
        cost = 125000;
        shortAV = 5;
        medAV = 5;
        maxRange = RANGE_MED;
        explosionDamage = damage;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.D, AvailabilityValue.C)
              .setISAdvancement(2805, 2820, 2840, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
