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
import megamek.common.equipment.AmmoType;
import megamek.common.weapons.autoCannons.UACWeapon;

/** Outer Sphere Ultra AC/5 (standard tier = IS-stat clone on OS tech base). */
public class OSUltraAC5 extends UACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSUltraAC5() {
        super();
        name = "Ultra AC/5";
        setInternalName("OSUltraAC5");
        addLookupName("OS Ultra AC/5");
        sortingName = "AC OS 3 UAC 1 Std 05";
        ammoType = AmmoType.AmmoTypeEnum.AC_ULTRA;
        heat = 1;
        damage = 5;
        rackSize = 5;
        minimumRange = 0;
        shortRange = 6;
        mediumRange = 13;
        longRange = 20;
        extremeRange = 26;
        tonnage = 9.0;
        criticalSlots = 5;
        bv = 112;
        cost = 200000;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
