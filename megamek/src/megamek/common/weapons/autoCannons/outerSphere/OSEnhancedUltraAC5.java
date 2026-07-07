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
import megamek.common.weapons.autoCannons.UACWeapon;

/**
 * Outer Sphere Enhanced Ultra AC/5 - true Ultra double-tap (Single / Ultra modes) with a
 * -1 to-hit refinement bonus. Uses canon AC_ULTRA ammo (inherited from UACWeapon).
 */
public class OSEnhancedUltraAC5 extends UACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedUltraAC5() {
        super();
        name = "Enhanced Ultra AC/5";
        setInternalName("OSEnhancedUltraAC5");
        addLookupName("OSAdvanceUltraAC5");
        addLookupName("Advance Ultra AC/5");
        addLookupName("OS Advance Ultra AC/5");
        sortingName = "AC OS 3 UAC 3 Enh 05";
        toHitModifier = -1;
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
        bv = 150;
        cost = 320000;
        explosionDamage = damage;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3000, 3025, 3050, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
