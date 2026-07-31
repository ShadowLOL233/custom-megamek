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
 * Outer Sphere Enhanced HVAC/10 — Enhanced-tier refinement of the Improve precision HVAC: same firepower,
 * reach and range-scaling accuracy (medium/long −1, extreme −2), but −2 t, −2 crit and −1 heat. Shares the Improve
 * BV (damage/range/accuracy are identical; the lighter/cooler build is the mech-level advantage).
 * Aerospace-capable (AV = damage, maxRange = extreme); electromagnetic-chemical action — no cook-off.
 */
public class OSEnhancedHVAC10 extends OSHVACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEnhancedHVAC10() {
        super();
        name = "Enhanced HVAC/10";
        setInternalName("OSEnhancedHVAC10");
        addLookupName("OS Enhanced HVAC/10");
        addLookupName("Enhanced HVAC/10");
        sortingName = "AC OS 5 HVAC 3 Enh 10";
        heat = 2;
        damage = 10;
        rackSize = 10;
        shortRange = 8;
        mediumRange = 16;
        longRange = 24;
        extremeRange = 32;
        tonnage = 8.0;
        criticalSlots = 4;
        bv = 165;
        cost = 480000;
        shortAV = 10;
        medAV = 10;
        longAV = 10;
        extAV = 10;
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
