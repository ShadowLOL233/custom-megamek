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
 * Outer Sphere ER Heavy PPC
 * Damage 18 / Heat 22 / Range 7-14-20 / no min, 9t / 4 crit
 *
 * The ER variant of the OS Heavy PPC — extended range version with minimum range removed
 * at the cost of significantly higher heat. Long range is 3 hexes shorter than the
 * Adv. ER PPC (20 vs 23), giving the OS ER PPC lineup a clear "more damage / less range"
 * choice point.
 */
public class OSERHeavyPPC extends PPCWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERHeavyPPC() {
        super();
        name = "ER Heavy PPC";
        setInternalName("OSERHeavyPPC");
        addLookupName("OS ER Heavy PPC");
        sortingName = "PPC OS 2 ER 3 Hvy 1 Std";
        heat = 22;
        damage = 18;
        shortRange = 7;
        mediumRange = 14;
        longRange = 20;
        extremeRange = 27;
        waterShortRange = 4;
        waterMediumRange = 9;
        waterLongRange = 13;
        waterExtremeRange = 18;
        tonnage = 9.0;
        criticalSlots = 4;
        bv = 420;
        cost = 700000;
        shortAV = 18;
        medAV = 18;
        longAV = 18;
        maxRange = RANGE_LONG;
        explosive = true;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3130, 3135, 3142, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
