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
import megamek.common.equipment.Mounted;
import megamek.common.weapons.autoCannons.HVACWeapon;

/**
 * Outer Sphere Improve HVAC/14 — Improve-tier high-velocity autocannon (heavy calibers only).
 * The heavier, earlier HVAC pattern; the Enhanced tier is the lighter/cooler refinement of this.
 * Aerospace-capable (AV = damage across all brackets, maxRange = extreme); ammo can detonate.
 */
public class OSImproveHVAC14 extends HVACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveHVAC14() {
        super();
        name = "Improve HVAC/14";
        setInternalName("OSImproveHVAC14");
        addLookupName("OS Improve HVAC/14");
        addLookupName("Improve HVAC/14");
        sortingName = "AC OS 5 HVAC 2 Imp 14";
        heat = 5;
        damage = 14;
        rackSize = 14;
        shortRange = 6;
        mediumRange = 13;
        longRange = 20;
        extremeRange = 26;
        tonnage = 12.0;
        criticalSlots = 8;
        bv = 230;
        cost = 480000;
        shortAV = 14;
        medAV = 14;
        longAV = 14;
        extAV = 14;
        maxRange = RANGE_EXT;
        explosionDamage = damage;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2900, 2930, 2960, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }

    @Override
    public double getBattleForceDamage(int range, Mounted<?> ignore) {
        return damage / 10.0;
    }
}
