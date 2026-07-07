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

/** Outer Sphere Heavy LB-X Gauss - 20 dmg slug / 20-pellet cluster / 19t / 11 crit / Experimental. */
public class OSHeavyLBXGauss extends OSLBXGaussWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyLBXGauss() {
        super();
        name = "Heavy LB-X Gauss Rifle";
        setInternalName("OSHeavyLBXGauss");
        addLookupName("OS Heavy LB-X Gauss Rifle");
        sortingName = "Gauss OS 5 LBX 3 Heavy";
        heat = 2;
        damage = 20;
        rackSize = 20;
        minimumRange = 3;
        shortRange = 6;
        mediumRange = 13;
        longRange = 20;
        extremeRange = 30;
        tonnage = 19.0;
        criticalSlots = 11;
        bv = 420;
        cost = 700000;
        shortAV = 12;
        medAV = 12;
        longAV = 12;
        maxRange = RANGE_LONG;
        explosionDamage = 25;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3060, 3070, 3080, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
