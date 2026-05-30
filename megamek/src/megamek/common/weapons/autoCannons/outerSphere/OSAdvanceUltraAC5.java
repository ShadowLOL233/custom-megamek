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
 * Outer Sphere Advance Ultra AC/5 - fires up to 3 shots/turn. Uses canon AC_ROTARY ammo.
 */
public class OSAdvanceUltraAC5 extends RACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAdvanceUltraAC5() {
        super();
        name = "Advance Ultra AC/5";
        setInternalName("OSAdvanceUltraAC5");
        addLookupName("OS Advance Ultra AC/5");
        sortingName = "AC OS 3 UAC 3 Adv 05";
        setModes(new String[] { MODE_AC_SINGLE, MODE_RAC_TWO_SHOT, MODE_RAC_THREE_SHOT });
        heat = 1;
        damage = 5;
        rackSize = 5;
        minimumRange = 0;
        shortRange = 8;
        mediumRange = 16;
        longRange = 24;
        extremeRange = 32;
        tonnage = 7.0;
        criticalSlots = 4;
        bv = 185;
        cost = 320000;
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
