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
 * Outer Sphere Enhanced HVAC/16 — Enhanced-tier high-velocity autocannon (heavy calibers only).
 * The compact refinement of the Improve HVAC: same firepower/reach, but lighter, fewer crits and −1 heat
 * (refined electrothermal). Aerospace-capable (AV = damage, maxRange = extreme); electromagnetic-chemical action — no cook-off.
 */
public class OSEnhancedHVAC16 extends OSHVACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedHVAC16() {
        super();
        name = "Enhanced HVAC/16";
        setInternalName("OSEnhancedHVAC16");
        addLookupName("OS Enhanced HVAC/16");
        addLookupName("Enhanced HVAC/16");
        sortingName = "AC OS 5 HVAC 3 Enh 16";
        heat = 5;
        damage = 16;
        rackSize = 16;
        shortRange = 6;
        mediumRange = 12;
        longRange = 19;
        extremeRange = 25;
        tonnage = 11.0;
        criticalSlots = 6;
        bv = 265;
        cost = 680000;
        shortAV = 16;
        medAV = 16;
        longAV = 16;
        extAV = 16;
        maxRange = RANGE_EXT;
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
