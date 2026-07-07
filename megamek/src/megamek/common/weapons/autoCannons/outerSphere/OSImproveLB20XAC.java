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
import megamek.common.weapons.autoCannons.LBXACWeapon;

/** Outer Sphere Improve LB 20-X (lighter + longer than the standard tier; future special munitions). */
public class OSImproveLB20XAC extends LBXACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveLB20XAC() {
        super();
        name = "Improve LB 20-X";
        setInternalName("OSImproveLB20XAC");
        addLookupName("OS Improve LB 20-X AC");
        sortingName = "AC OS 2 LBX 2 Imp 20";
        ammoType = AmmoType.AmmoTypeEnum.LBX_OS;
        heat = 5;
        damage = 20;
        rackSize = 20;
        minimumRange = 0;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 12.0;
        criticalSlots = 8;
        bv = 296;
        cost = 780000;
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
