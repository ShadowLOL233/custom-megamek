/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.flamers.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.WeaponType;
import megamek.common.weapons.flamers.FlamerWeapon;

/**
 * Outer Sphere Improved Flamer
 * Damage 2 / Heat 2 / Range 1-2-3 / 0.5t / 1 crit / no ammo (energy)
 *
 * Efficiency variant: OS field-containment lets the flamer run at half a ton
 * and half the heat-to-fire of the canonical Flamer (3 heat) while keeping the
 * same output. The heat-management / anti-infantry flamer.
 */
public class OSImprovedFlamer extends FlamerWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImprovedFlamer() {
        super();
        name = "Improved Flamer";
        setInternalName("OSImprovedFlamer");
        addLookupName("OS Improved Flamer");
        sortingName = "Flamer OS A Imp";
        heat = 2;
        damage = 2;
        infDamageClass = WeaponType.WEAPON_BURST_4D6;
        shortRange = 1;
        mediumRange = 2;
        longRange = 3;
        extremeRange = 4;
        tonnage = 0.5;
        criticalSlots = 1;
        bv = 6;
        cost = 9000;
        shortAV = 2;
        maxRange = RANGE_SHORT;
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
