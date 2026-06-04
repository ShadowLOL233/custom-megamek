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

/** Outer Sphere Ultra AC/20 (standard tier = IS-stat clone on OS tech base). */
public class OSUltraAC20 extends UACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSUltraAC20() {
        super();
        name = "Ultra AC/20";
        setInternalName("OSUltraAC20");
        addLookupName("OS Ultra AC/20");
        sortingName = "AC OS 3 UAC 1 Std 20";
        ammoType = AmmoType.AmmoTypeEnum.AC_ULTRA;
        heat = 8;
        damage = 20;
        rackSize = 20;
        minimumRange = 0;
        shortRange = 3;
        mediumRange = 7;
        longRange = 10;
        extremeRange = 13;
        tonnage = 15.0;
        criticalSlots = 9;
        bv = 281;
        cost = 480000;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
