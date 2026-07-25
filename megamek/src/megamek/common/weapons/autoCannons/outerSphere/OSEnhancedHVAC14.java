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
 * Outer Sphere Enhanced HVAC/14 — Enhanced-tier high-velocity autocannon (heavy calibers only).
 * The compact refinement of the Improve HVAC: same firepower/reach, but lighter, fewer crits and −1 heat
 * (refined electrothermal). Aerospace-capable (AV = damage, maxRange = extreme); electromagnetic-chemical action — no cook-off.
 */
public class OSEnhancedHVAC14 extends OSHVACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedHVAC14() {
        super();
        name = "Enhanced HVAC/14";
        setInternalName("OSEnhancedHVAC14");
        addLookupName("OS Enhanced HVAC/14");
        addLookupName("Enhanced HVAC/14");
        sortingName = "AC OS 5 HVAC 3 Enh 14";
        heat = 4;
        damage = 14;
        rackSize = 14;
        shortRange = 6;
        mediumRange = 13;
        longRange = 20;
        extremeRange = 26;
        tonnage = 10.0;
        criticalSlots = 6;
        bv = 230;
        cost = 600000;
        shortAV = 14;
        medAV = 14;
        longAV = 14;
        extAV = 14;
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
