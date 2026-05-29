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

/** Outer Sphere Improve Ultra AC/2 (lighter than the standard Ultra tier). */
public class OSImproveUltraAC2 extends UACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveUltraAC2() {
        super();
        name = "Improve Ultra AC/2";
        setInternalName("OSImproveUltraAC2");
        addLookupName("OS Improve Ultra AC/2");
        sortingName = "AC OS UAC Imp 02";
        ammoType = AmmoType.AmmoTypeEnum.AC_ULTRA;
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
        bv = 56;
        cost = 160000;
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
