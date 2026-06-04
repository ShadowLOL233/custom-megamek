/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.gaussRifles.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.AmmoType;
import megamek.common.weapons.gaussRifles.HAGWeapon;

/** Outer Sphere Hyper-Assault Gauss/30 - 11t / 8 crit. Cooler + longer-ranged than Clan HAG. */
public class OSHAG30 extends HAGWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHAG30() {
        super();
        name = "HAG/30";
        setInternalName("OSHAG30");
        addLookupName("OS HAG/30");
        sortingName = "Gauss OS 6 HAG 30";
        ammoType = AmmoType.AmmoTypeEnum.HAG_OS;
        heat = 5;
        rackSize = 30;
        minimumRange = 2;
        shortRange = 9;
        mediumRange = 17;
        longRange = 25;
        extremeRange = 33;
        tonnage = 11.0;
        criticalSlots = 8;
        bv = 420;
        cost = 500000;
        shortAV = 18;
        medAV = 18;
        longAV = 18;
        maxRange = RANGE_LONG;
        explosionDamage = 30;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
