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
 * Outer Sphere Standard ER PPC — RoOS-manufactured replica of the upstream IS ER PPC.
 * Stats identical to the IS reference; differentiated only by tech base.
 */
public class OSERPPC extends PPCWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERPPC() {
        super();
        name = "ER PPC";
        setInternalName("OSERPPC");
        addLookupName("OS ER PPC");
        addLookupName("Legion ER PPC");
        sortingName = "PPC OS 2 ER 2 Std 1 Std";
        heat = 15;
        damage = 10;
        shortRange = 7;
        mediumRange = 14;
        longRange = 23;
        extremeRange = 34;
        waterShortRange = 4;
        waterMediumRange = 10;
        waterLongRange = 16;
        waterExtremeRange = 24;
        tonnage = 7.0;
        criticalSlots = 3;
        bv = 229;
        cost = 300000;
        shortAV = 10;
        medAV = 10;
        longAV = 10;
        maxRange = RANGE_LONG;
        explosive = true;
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
