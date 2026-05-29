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

/** Outer Sphere Ultra AC/10 (standard tier = IS-stat clone on OS tech base). */
public class OSUltraAC10 extends UACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSUltraAC10() {
        super();
        name = "Ultra AC/10";
        setInternalName("OSUltraAC10");
        addLookupName("OS Ultra AC/10");
        sortingName = "AC OS UAC 10";
        ammoType = AmmoType.AmmoTypeEnum.AC_ULTRA;
        heat = 4;
        damage = 10;
        rackSize = 10;
        minimumRange = 0;
        shortRange = 6;
        mediumRange = 13;
        longRange = 18;
        extremeRange = 24;
        tonnage = 13.0;
        criticalSlots = 7;
        bv = 210;
        cost = 320000;
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
