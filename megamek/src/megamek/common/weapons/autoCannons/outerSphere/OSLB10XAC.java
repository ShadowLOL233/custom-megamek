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

/** Outer Sphere LB 10-X (standard tier = IS-stat clone on OS tech base). */
public class OSLB10XAC extends LBXACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSLB10XAC() {
        super();
        name = "LB 10-X";
        setInternalName("OSLB10XAC");
        addLookupName("OS LB 10-X AC");
        sortingName = "AC OS 2 LBX 1 Std 10";
        ammoType = AmmoType.AmmoTypeEnum.LBX_OS;
        heat = 2;
        damage = 10;
        rackSize = 10;
        minimumRange = 0;
        shortRange = 6;
        mediumRange = 12;
        longRange = 18;
        extremeRange = 24;
        tonnage = 11.0;
        criticalSlots = 6;
        bv = 148;
        cost = 400000;
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
