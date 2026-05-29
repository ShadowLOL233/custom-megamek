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

/** Outer Sphere Improve Ultra AC/10 (lighter than the standard Ultra tier). */
public class OSImproveUltraAC10 extends UACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveUltraAC10() {
        super();
        name = "Improve Ultra AC/10";
        setInternalName("OSImproveUltraAC10");
        addLookupName("OS Improve Ultra AC/10");
        sortingName = "AC OS UAC Imp 10";
        ammoType = AmmoType.AmmoTypeEnum.AC_ULTRA;
        heat = 3;
        damage = 10;
        rackSize = 10;
        minimumRange = 0;
        shortRange = 6;
        mediumRange = 12;
        longRange = 18;
        extremeRange = 24;
        tonnage = 11.0;
        criticalSlots = 6;
        bv = 210;
        cost = 420000;
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
