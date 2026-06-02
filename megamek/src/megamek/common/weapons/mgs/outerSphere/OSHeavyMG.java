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
 * Outer Sphere Heavy Machine Gun.
 * Damage 3 / 0 heat / Range 2-4-6 / 1.0t / 1 crit / 100 shots-per-ton.
 *
 * Same damage as canon Heavy MG, range tripled (canon 1/2/2 -> 2/4/6) so it
 * stays competitive at medium ranges where canon Heavy MG fell off.
 */
public class OSHeavyMG extends MGWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyMG() {
        super();
        name = "Heavy Machine Gun";
        setInternalName("OSHeavyMG");
        addLookupName("OS Heavy Machine Gun");
        addLookupName("OS Heavy MG");
        sortingName = "MG OS 3 Heavy";
        ammoType = AmmoType.AmmoTypeEnum.MG_HEAVY_OS;
        heat = 0;
        damage = 3;
        infDamageClass = WeaponType.WEAPON_BURST_3D6;
        rackSize = 3;
        shortRange = 2;
        mediumRange = 4;
        longRange = 6;
        extremeRange = 8;
        tonnage = 1.0;
        criticalSlots = 1;
        bv = 8;
        cost = 9000;
        shortAV = 3;
        maxRange = RANGE_SHORT;
        atClass = CLASS_POINT_DEFENSE;
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
