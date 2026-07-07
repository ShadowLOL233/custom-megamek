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

/** Outer Sphere Hyper-Assault Gauss/20 - cluster Gauss / 7t / 6 crit. Cooler + longer-ranged than Clan HAG. */
public class OSHAG20 extends HAGWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHAG20() {
        super();
        name = "HAG/20";
        setInternalName("OSHAG20");
        addLookupName("OS HAG/20");
        sortingName = "Gauss OS 6 HAG 20";
        ammoType = AmmoType.AmmoTypeEnum.HAG_OS;
        heat = 3;
        rackSize = 20;
        minimumRange = 2;
        shortRange = 9;
        mediumRange = 17;
        longRange = 25;
        extremeRange = 33;
        tonnage = 7.0;
        criticalSlots = 6;
        bv = 280;
        cost = 400000;
        shortAV = 12;
        medAV = 12;
        longAV = 12;
        maxRange = RANGE_LONG;
        explosionDamage = 20;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2805, 2820, 2840, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
