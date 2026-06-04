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

/** Outer Sphere Hyper-Assault Gauss/40 - 14t / 10 crit. Cooler + longer-ranged than Clan HAG. */
public class OSHAG40 extends HAGWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHAG40() {
        super();
        name = "HAG/40";
        setInternalName("OSHAG40");
        addLookupName("OS HAG/40");
        sortingName = "Gauss OS 6 HAG 40";
        ammoType = AmmoType.AmmoTypeEnum.HAG_OS;
        heat = 7;
        rackSize = 40;
        minimumRange = 2;
        shortRange = 9;
        mediumRange = 17;
        longRange = 25;
        extremeRange = 33;
        tonnage = 14.0;
        criticalSlots = 10;
        bv = 560;
        cost = 600000;
        shortAV = 24;
        medAV = 24;
        longAV = 24;
        maxRange = RANGE_LONG;
        explosionDamage = 40;
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
