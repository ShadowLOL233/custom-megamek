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
 * Outer Sphere Enhanced HVAC/12 — Enhanced-tier high-velocity autocannon (heavy calibers only).
 * The compact refinement of the Improve HVAC: same firepower/reach, but lighter, fewer crits and −1 heat
 * (refined electrothermal). Aerospace-capable (AV = damage, maxRange = extreme); ammo can detonate.
 */
public class OSEnhancedHVAC12 extends HVACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedHVAC12() {
        super();
        name = "Enhanced HVAC/12";
        setInternalName("OSEnhancedHVAC12");
        addLookupName("OS Enhanced HVAC/12");
        addLookupName("Enhanced HVAC/12");
        sortingName = "AC OS 5 HVAC 3 Enh 12";
        heat = 3;
        damage = 12;
        rackSize = 12;
        shortRange = 7;
        mediumRange = 14;
        longRange = 21;
        extremeRange = 28;
        tonnage = 9.0;
        criticalSlots = 5;
        bv = 195;
        cost = 520000;
        shortAV = 12;
        medAV = 12;
        longAV = 12;
        extAV = 12;
        maxRange = RANGE_EXT;
        explosionDamage = damage;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3000, 3025, 3050, DATE_NONE, DATE_NONE)
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
