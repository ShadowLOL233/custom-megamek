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

/**
 * Outer Sphere Improve MRM 10 — OS medium-range specialist: a huge medium bracket (short 3 / medium 13 /
 * long 16, no minimum range), accurate with a -1 to-hit inside the medium bracket (wired in ComputeToHit via
 * F_OS_MRM_MEDIUM_SPEC; a linked Diana III FCS adds a further -1 there). There is no base OS MRM (plain and
 * Improve MRM were only months apart, so RoOS fielded Improve).
 */
public class OSImproveMRM10 extends OSMRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveMRM10() {
        super();
        name = "Improve MRM 10";
        setInternalName("OSImproveMRM10");
        addLookupName("OS Improve MRM-10");
        addLookupName("OS Improve MRM 10");
        sortingName = "Missile OS 2 1 2 10";
        heat = 4;
        rackSize = 10;
        minimumRange = 0;
        shortRange = 3;
        mediumRange = 13;
        longRange = 16;
        extremeRange = 20;
        tonnage = 3.0;
        criticalSlots = 2;
        bv = 70;
        cost = 50000;
        shortAV = 6;
        medAV = 6;
        longAV = 6;
        maxRange = RANGE_LONG;
        flags = flags.or(F_OS_MRM_MEDIUM_SPEC);
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

    @Override
    public String getSortingName() {
        return sortingName;
    }
}
