/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.mgs.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.AmmoType;
import megamek.common.equipment.WeaponType;
import megamek.common.weapons.mgs.MGWeapon;

/**
 * Outer Sphere Light Machine Gun.
 * Damage 1 / 0 heat / Range 3-6-9 / 0.5t / 1 crit / 200 shots-per-ton.
 *
 * Same damage as canon Light MG but range extended ~50% (canon 2/4/6 -> 3/6/9).
 */
public class OSLightMG extends MGWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSLightMG() {
        super();
        name = "Light Machine Gun";
        setInternalName("OSLightMG");
        addLookupName("OS Light Machine Gun");
        addLookupName("OS Light MG");
        sortingName = "MG OS 1 Light";
        ammoType = AmmoType.AmmoTypeEnum.MG_LIGHT_OS;
        heat = 0;
        damage = 1;
        infDamageClass = WeaponType.WEAPON_BURST_1D6;
        rackSize = 1;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 0.5;
        criticalSlots = 1;
        bv = 6;
        cost = 6000;
        shortAV = 1;
        maxRange = RANGE_SHORT;
        atClass = CLASS_POINT_DEFENSE;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2805, 2820, 2840, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }
}
