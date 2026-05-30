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
 * Outer Sphere Standard PPC — RoOS-manufactured replica of the upstream IS PPC.
 * Stats identical to the IS reference; differentiated only by tech base.
 */
public class OSPPC extends PPCWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSPPC() {
        super();
        name = "PPC";
        setInternalName("OSPPC");
        addLookupName("OS PPC");
        addLookupName("Legion PPC");
        sortingName = "PPC OS 1 Std 2 Std 1 Std";
        heat = 10;
        damage = 10;
        minimumRange = 3;
        shortRange = 6;
        mediumRange = 12;
        longRange = 18;
        extremeRange = 24;
        waterShortRange = 4;
        waterMediumRange = 7;
        waterLongRange = 10;
        waterExtremeRange = 15;
        tonnage = 7.0;
        criticalSlots = 3;
        bv = 176;
        cost = 200000;
        shortAV = 10;
        medAV = 10;
        maxRange = RANGE_MED;
        explosive = true;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.D)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.D, AvailabilityValue.C)
              .setISAdvancement(3115, 3120, 3125, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
