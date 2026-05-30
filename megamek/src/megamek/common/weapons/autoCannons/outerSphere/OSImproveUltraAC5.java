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

/** Outer Sphere Improve Ultra AC/5 (lighter than the standard Ultra tier). */
public class OSImproveUltraAC5 extends UACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveUltraAC5() {
        super();
        name = "Improve Ultra AC/5";
        setInternalName("OSImproveUltraAC5");
        addLookupName("OS Improve Ultra AC/5");
        sortingName = "AC OS 3 UAC 2 Imp 05";
        ammoType = AmmoType.AmmoTypeEnum.AC_ULTRA;
        heat = 1;
        damage = 5;
        rackSize = 5;
        minimumRange = 0;
        shortRange = 7;
        mediumRange = 14;
        longRange = 21;
        extremeRange = 28;
        tonnage = 8.0;
        criticalSlots = 4;
        bv = 118;
        cost = 260000;
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
