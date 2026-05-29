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
 * Outer Sphere Advance Ultra AC/2 - fires up to 3 shots/turn (rotary-style multi-shot capped at 3).
 * Uses canon AC_ROTARY ammo.
 */
public class OSAdvanceUltraAC2 extends RACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAdvanceUltraAC2() {
        super();
        name = "Advance Ultra AC/2";
        setInternalName("OSAdvanceUltraAC2");
        addLookupName("OS Advance Ultra AC/2");
        sortingName = "AC OS UAC Adv 02";
        setModes(new String[] { MODE_AC_SINGLE, MODE_RAC_TWO_SHOT, MODE_RAC_THREE_SHOT });
        heat = 1;
        damage = 2;
        rackSize = 2;
        minimumRange = 0;
        shortRange = 9;
        mediumRange = 18;
        longRange = 25;
        extremeRange = 34;
        tonnage = 6.0;
        criticalSlots = 2;
        bv = 77;
        cost = 190000;
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
