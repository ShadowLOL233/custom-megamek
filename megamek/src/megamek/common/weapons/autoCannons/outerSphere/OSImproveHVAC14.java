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
 * Outer Sphere Improve HVAC/14 — Improve-tier long-barrel precision anti-armor autocannon. Single-slug,
 * high-velocity; the to-hit bonus grows with range (medium/long −1, extreme −2, applied in ComputeToHit). Pays a
 * mech-level tax in heavy tonnage, high crit count and low ammo/ton, and carries a high BV for the accuracy.
 * Aerospace-capable (AV = damage across all brackets, maxRange = extreme); electromagnetic-chemical — no cook-off.
 */
public class OSImproveHVAC14 extends OSHVACWeapon {
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
        shortRange = 7;
        mediumRange = 13;
        longRange = 20;
        extremeRange = 27;
        tonnage = 12.0;
        criticalSlots = 8;
        bv = 225;
        cost = 500000;
        shortAV = 14;
        medAV = 14;
        longAV = 14;
        extAV = 14;
        maxRange = RANGE_EXT;
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

    @Override
    public double getBattleForceDamage(int range, Mounted<?> ignore) {
        return damage / 10.0;
    }
}
