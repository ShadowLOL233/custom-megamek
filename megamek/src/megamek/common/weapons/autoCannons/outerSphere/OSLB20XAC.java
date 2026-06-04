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

/** Outer Sphere LB 20-X (standard tier = IS-stat clone on OS tech base). */
public class OSLB20XAC extends LBXACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSLB20XAC() {
        super();
        name = "LB 20-X";
        setInternalName("OSLB20XAC");
        addLookupName("OS LB 20-X AC");
        sortingName = "AC OS 2 LBX 1 Std 20";
        ammoType = AmmoType.AmmoTypeEnum.LBX_OS;
        heat = 5;
        damage = 20;
        rackSize = 20;
        minimumRange = 0;
        shortRange = 4;
        mediumRange = 8;
        longRange = 12;
        extremeRange = 16;
        tonnage = 14.0;
        criticalSlots = 11;
        bv = 237;
        cost = 600000;
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
