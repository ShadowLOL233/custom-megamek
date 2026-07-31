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
 * Outer Sphere Enhanced HVAC/16 — Enhanced-tier refinement of the Improve precision HVAC: same firepower,
 * reach and range-scaling accuracy (medium/long −1, extreme −2), but −2 t, −2 crit and −1 heat. Shares the Improve
 * BV (damage/range/accuracy are identical; the lighter/cooler build is the mech-level advantage).
 * Aerospace-capable (AV = damage, maxRange = extreme); electromagnetic-chemical action — no cook-off.
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
        extremeRange = 26;
        tonnage = 11.0;
        criticalSlots = 6;
        bv = 255;
        cost = 660000;
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
