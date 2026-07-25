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
 * Outer Sphere Enhanced HVAC/18 — Enhanced-tier high-velocity autocannon (heavy calibers only).
 * The compact refinement of the Improve HVAC: same firepower/reach, but lighter, fewer crits and −1 heat
 * (refined electrothermal). Aerospace-capable (AV = damage, maxRange = extreme); electromagnetic-chemical action — no cook-off.
 */
public class OSEnhancedHVAC18 extends OSHVACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedHVAC18() {
        super();
        name = "Enhanced HVAC/18";
        setInternalName("OSEnhancedHVAC18");
        addLookupName("OS Enhanced HVAC/18");
        addLookupName("Enhanced HVAC/18");
        sortingName = "AC OS 5 HVAC 3 Enh 18";
        heat = 6;
        damage = 18;
        rackSize = 18;
        shortRange = 5;
        mediumRange = 11;
        longRange = 18;
        extremeRange = 24;
        tonnage = 12.0;
        criticalSlots = 7;
        bv = 300;
        cost = 760000;
        shortAV = 18;
        medAV = 18;
        longAV = 18;
        extAV = 18;
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
