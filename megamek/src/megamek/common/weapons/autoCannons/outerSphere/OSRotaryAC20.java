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
 * Outer Sphere Rotary AC/20 - up to 3 shots/turn (7 heat/shot, max-fire 21 heat). Canon AC_ROTARY ammo.
 */
public class OSRotaryAC20 extends RACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSRotaryAC20() {
        super();
        name = "RAC/20";
        setInternalName("OSRotaryAC20");
        addLookupName("OS Rotary AC/20");
        sortingName = "AC OS 4 RAC 1 Std 20";
        setModes(new String[] { MODE_AC_SINGLE, MODE_RAC_TWO_SHOT, MODE_RAC_THREE_SHOT });
        heat = 7;
        damage = 20;
        rackSize = 20;
        minimumRange = 0;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 16.0;
        criticalSlots = 12;
        bv = 420;
        cost = 860000;
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
