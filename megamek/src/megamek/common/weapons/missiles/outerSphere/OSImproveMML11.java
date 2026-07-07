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

/** Outer Sphere Improve MML 11 - new caliber, lighter refinement of the OS MML. */
public class OSImproveMML11 extends MMLWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveMML11() {
        super();
        name = "Improve MML 11";
        setInternalName("OSImproveMML11");
        addLookupName("OS Improve MML-11");
        addLookupName("OS Improve MML 11");
        sortingName = "Missile OS 4 1 2 11";
        heat = 6;
        rackSize = 11;
        minimumRange = 6;
        shortRange = 7;
        mediumRange = 14;
        longRange = 21;
        extremeRange = 28;
        tonnage = 5.5;
        criticalSlots = 4;
        bv = 105;
        cost = 200000;
        shortAV = 6;
        medAV = 6;
        longAV = 6;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2900, 2930, 2960, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
