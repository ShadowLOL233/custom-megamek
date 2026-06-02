/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.autoCannons.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.weapons.autoCannons.ACWeapon;

/**
 * Outer Sphere Standard AC/20 — RoOS-manufactured replica of the upstream IS AC/20.
 * Stats identical to the IS reference; differentiated only by tech base.
 */
public class OSAC20 extends ACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAC20() {
        super();
        name = "AC/20";
        setInternalName("OSAC20");
        addLookupName("OS AC/20");
        addLookupName("OS Autocannon/20");
        addLookupName("Legion AC/20");
        sortingName = "AC OS 1 Std 1 Std 20";
        heat = 7;
        damage = 20;
        rackSize = 20;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 14.0;
        criticalSlots = 9;
        bv = 178;
        cost = 300000;
        shortAV = 20;
        maxRange = RANGE_SHORT;
        explosionDamage = damage;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.C)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.D, AvailabilityValue.C)
              .setISAdvancement(3115, 3120, 3125, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
