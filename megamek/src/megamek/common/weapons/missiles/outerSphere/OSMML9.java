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

/** Outer Sphere MML 9 - OS base multi-missile launcher (better than IS: lighter, fewer slots). */
public class OSMML9 extends MMLWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSMML9() {
        super();
        name = "MML 9";
        setInternalName("OSMML9");
        addLookupName("OS MML-9");
        addLookupName("OS MML 9");
        sortingName = "Missile OS 4 1 1 09";
        heat = 5;
        rackSize = 9;
        minimumRange = 6;
        shortRange = 7;
        mediumRange = 14;
        longRange = 21;
        extremeRange = 28;
        tonnage = 5.0;
        criticalSlots = 4;
        bv = 86;
        cost = 125000;
        shortAV = 5;
        medAV = 5;
        longAV = 5;
        maxRange = RANGE_LONG;
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
