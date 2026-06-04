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

/** Outer Sphere LB 5-X (standard tier = IS-stat clone on OS tech base). */
public class OSLB5XAC extends LBXACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSLB5XAC() {
        super();
        name = "LB 5-X";
        setInternalName("OSLB5XAC");
        addLookupName("OS LB 5-X AC");
        sortingName = "AC OS 2 LBX 1 Std 05";
        ammoType = AmmoType.AmmoTypeEnum.LBX_OS;
        heat = 1;
        damage = 5;
        rackSize = 5;
        minimumRange = 0;
        shortRange = 7;
        mediumRange = 14;
        longRange = 21;
        extremeRange = 28;
        tonnage = 8.0;
        criticalSlots = 5;
        bv = 83;
        cost = 250000;
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
