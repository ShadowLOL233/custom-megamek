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
 * Outer Sphere Enhanced Ultra AC/10 - true Ultra double-tap (Single / Ultra modes) with a
 * -1 to-hit refinement bonus. Uses canon AC_ULTRA ammo (inherited from UACWeapon).
 */
public class OSEnhancedUltraAC10 extends UACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedUltraAC10() {
        super();
        name = "Enhanced Ultra AC/10";
        setInternalName("OSEnhancedUltraAC10");
        addLookupName("OSAdvanceUltraAC10");
        addLookupName("Advance Ultra AC/10");
        addLookupName("OS Advance Ultra AC/10");
        sortingName = "AC OS 3 UAC 3 Enh 10";
        toHitModifier = -1;
        heat = 3;
        damage = 10;
        rackSize = 10;
        minimumRange = 0;
        shortRange = 7;
        mediumRange = 14;
        longRange = 20;
        extremeRange = 27;
        tonnage = 10.0;
        criticalSlots = 4;
        bv = 270;
        cost = 510000;
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
