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
 * Outer Sphere Rotary AC/10 - up to 5 shots/turn (3 heat/shot, max-fire 15 heat). Canon AC_ROTARY ammo.
 */
public class OSRotaryAC10 extends RACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSRotaryAC10() {
        super();
        name = "RAC/10";
        setInternalName("OSRotaryAC10");
        addLookupName("OS Rotary AC/10");
        sortingName = "AC OS RAC 10";
        setModes(new String[] { MODE_AC_SINGLE, MODE_RAC_TWO_SHOT, MODE_RAC_THREE_SHOT,
                                MODE_RAC_FOUR_SHOT, MODE_RAC_FIVE_SHOT });
        heat = 3;
        damage = 10;
        rackSize = 10;
        minimumRange = 0;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 14.0;
        criticalSlots = 8;
        bv = 410;
        cost = 560000;
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
