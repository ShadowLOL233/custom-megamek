/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.missiles.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.weapons.missiles.MMLWeapon;

/** Outer Sphere Improve MML 5 - lighter/fewer-slot refinement of the OS MML (same combat profile). */
public class OSImproveMML5 extends MMLWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveMML5() {
        super();
        name = "Improve MML 5";
        setInternalName("OSImproveMML5");
        addLookupName("OS Improve MML-5");
        addLookupName("OS Improve MML 5");
        sortingName = "Missile OS 4 1 2 05";
        heat = 3;
        rackSize = 5;
        minimumRange = 6;
        shortRange = 7;
        mediumRange = 14;
        longRange = 21;
        extremeRange = 28;
        tonnage = 2.0;
        criticalSlots = 2;
        bv = 45;
        cost = 97500;
        shortAV = 3;
        medAV = 3;
        longAV = 3;
        maxRange = RANGE_LONG;
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
