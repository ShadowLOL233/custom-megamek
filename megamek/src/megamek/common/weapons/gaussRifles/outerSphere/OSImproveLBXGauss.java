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

/** Outer Sphere Improve LB-X Gauss - lighter (13t / 6 crit) + longer range than base LB-X Gauss. */
public class OSImproveLBXGauss extends OSLBXGaussWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveLBXGauss() {
        super();
        name = "Improve LB-X Gauss Rifle";
        setInternalName("OSImproveLBXGauss");
        addLookupName("OS Improve LB-X Gauss Rifle");
        sortingName = "Gauss OS 5 LBX 2 Imp";
        heat = 1;
        damage = 15;
        rackSize = 15;
        minimumRange = 2;
        shortRange = 8;
        mediumRange = 16;
        longRange = 24;
        extremeRange = 36;
        tonnage = 13.0;
        criticalSlots = 6;
        bv = 330;
        cost = 480000;
        shortAV = 9;
        medAV = 9;
        longAV = 9;
        maxRange = RANGE_LONG;
        explosionDamage = 20;
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
