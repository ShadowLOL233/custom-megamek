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
 * Outer Sphere Improve HVAC/16 — Improve-tier high-velocity autocannon (heavy calibers only).
 * The heavier, earlier HVAC pattern; the Enhanced tier is the lighter/cooler refinement of this.
 * Aerospace-capable (AV = damage across all brackets, maxRange = extreme); ammo can detonate.
 */
public class OSImproveHVAC16 extends HVACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveHVAC16() {
        super();
        name = "Improve HVAC/16";
        setInternalName("OSImproveHVAC16");
        addLookupName("OS Improve HVAC/16");
        addLookupName("Improve HVAC/16");
        sortingName = "AC OS 5 HVAC 2 Imp 16";
        heat = 6;
        damage = 16;
        rackSize = 16;
        shortRange = 6;
        mediumRange = 12;
        longRange = 19;
        extremeRange = 25;
        tonnage = 13.0;
        criticalSlots = 8;
        bv = 265;
        cost = 540000;
        shortAV = 16;
        medAV = 16;
        longAV = 16;
        extAV = 16;
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
