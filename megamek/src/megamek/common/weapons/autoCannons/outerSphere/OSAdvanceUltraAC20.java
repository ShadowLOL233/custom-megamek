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
import megamek.common.weapons.autoCannons.RACWeapon;

/**
 * Outer Sphere Advance Ultra AC/20 - fires up to 3 shots/turn. Uses canon AC_ROTARY ammo.
 */
public class OSAdvanceUltraAC20 extends RACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAdvanceUltraAC20() {
        super();
        name = "Advance Ultra AC/20";
        setInternalName("OSAdvanceUltraAC20");
        addLookupName("OS Advance Ultra AC/20");
        sortingName = "AC OS UAC Adv 20";
        setModes(new String[] { MODE_AC_SINGLE, MODE_RAC_TWO_SHOT, MODE_RAC_THREE_SHOT });
        heat = 6;
        damage = 20;
        rackSize = 20;
        minimumRange = 0;
        shortRange = 5;
        mediumRange = 9;
        longRange = 13;
        extremeRange = 17;
        tonnage = 12.0;
        criticalSlots = 8;
        bv = 500;
        cost = 770000;
        explosionDamage = damage;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3130, 3135, 3140, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.EXPERIMENTAL);
    }
}
