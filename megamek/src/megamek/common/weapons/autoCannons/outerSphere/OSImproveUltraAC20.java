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

/** Outer Sphere Improve Ultra AC/20 (lighter + longer than the standard Ultra tier). */
public class OSImproveUltraAC20 extends UACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveUltraAC20() {
        super();
        name = "Improve Ultra AC/20";
        setInternalName("OSImproveUltraAC20");
        addLookupName("OS Improve Ultra AC/20");
        sortingName = "AC OS 3 UAC 2 Imp 20";
        ammoType = AmmoType.AmmoTypeEnum.AC_ULTRA;
        heat = 6;
        damage = 20;
        rackSize = 20;
        minimumRange = 0;
        shortRange = 4;
        mediumRange = 8;
        longRange = 12;
        extremeRange = 16;
        tonnage = 12.0;
        criticalSlots = 8;
        bv = 337;
        cost = 625000;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2900, 2930, 2960, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
