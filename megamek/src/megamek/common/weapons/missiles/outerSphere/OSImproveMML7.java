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

/** Outer Sphere Improve MML 7 - lighter/fewer-slot refinement of the OS MML (same combat profile). */
public class OSImproveMML7 extends MMLWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveMML7() {
        super();
        name = "Improve MML 7";
        setInternalName("OSImproveMML7");
        addLookupName("OS Improve MML-7");
        addLookupName("OS Improve MML 7");
        sortingName = "Missile OS 4 1 2 07";
        heat = 4;
        rackSize = 7;
        minimumRange = 6;
        shortRange = 7;
        mediumRange = 14;
        longRange = 21;
        extremeRange = 28;
        tonnage = 3.0;
        criticalSlots = 3;
        bv = 67;
        cost = 136500;
        shortAV = 4;
        medAV = 4;
        longAV = 4;
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
