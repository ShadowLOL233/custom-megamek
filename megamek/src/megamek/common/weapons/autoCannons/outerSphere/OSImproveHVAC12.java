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

/**
 * Outer Sphere Improve HVAC/12 — Improve-tier long-barrel precision anti-armor autocannon. Single-slug,
 * high-velocity; the to-hit bonus grows with range (medium/long −1, extreme −2, applied in ComputeToHit). Pays a
 * mech-level tax in heavy tonnage, high crit count and low ammo/ton, and carries a high BV for the accuracy.
 * Aerospace-capable (AV = damage across all brackets, maxRange = extreme); electromagnetic-chemical — no cook-off.
 */
public class OSImproveHVAC12 extends OSHVACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveHVAC12() {
        super();
        name = "Improve HVAC/12";
        setInternalName("OSImproveHVAC12");
        addLookupName("OS Improve HVAC/12");
        addLookupName("Improve HVAC/12");
        sortingName = "AC OS 5 HVAC 2 Imp 12";
        heat = 4;
        damage = 12;
        rackSize = 12;
        shortRange = 7;
        mediumRange = 14;
        longRange = 21;
        extremeRange = 28;
        tonnage = 11.0;
        criticalSlots = 7;
        bv = 195;
        cost = 440000;
        shortAV = 12;
        medAV = 12;
        longAV = 12;
        extAV = 12;
        maxRange = RANGE_EXT;
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
