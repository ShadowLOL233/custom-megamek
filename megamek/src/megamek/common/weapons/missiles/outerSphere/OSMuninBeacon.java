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
import megamek.common.equipment.AmmoType;
import megamek.common.weapons.other.NarcWeapon;

/**
 * Outer Sphere Munin Missile Beacon - enhanced multi-pod beacon (iNarc-class). Fires Homing, ECM,
 * Haywire or Nemesis pods. Longer ranged and heavier than the standard Narc.
 */
public class OSMuninBeacon extends NarcWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSMuninBeacon() {
        super();
        name = "Munin Missile Beacon";
        setInternalName("OSMuninBeacon");
        addLookupName("OS Munin Missile Beacon");
        addLookupName("OS Munin iNarc");
        sortingName = "Missile OS 7 3 Munin";
        ammoType = AmmoType.AmmoTypeEnum.INARC;
        heat = 0;
        rackSize = 1;
        shortRange = 4;
        mediumRange = 9;
        longRange = 15;
        extremeRange = 18;
        tonnage = 5.0;
        criticalSlots = 3;
        bv = 75;
        cost = 250000;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
