/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.ppc.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.weapons.ppc.PPCRotaryWeapon;

/**
 * Outer Sphere Rotary Snub-Nose PPC (RSPPC) — short-range cluster rotary.
 *
 * Combines the Snub-Nose's range-graded damage falloff (8/6/4) with the Rotary mode-dial
 * (1-4 shots). Per-bolt damage drops with range, so 4-shot at short can produce 32 max
 * damage while 4-shot at long produces 16 max. 8 heat per shot / Range 8-12-15 no min /
 * 10t / 6 crit / BV ~360.
 *
 * The first three shots are self-cooled and pod-free, each costing ⌈8/3⌉ = 3 suppressed heat;
 * only the 4th shot draws one RPPC Coolant Pod charge. Dialing 4-shot without sufficient
 * coolant triggers Capacitor Overload.
 */
public class OSSnubNoseRotaryPPC extends PPCRotaryWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSSnubNoseRotaryPPC() {
        super(4);  // 1-shot through 4-shot modes
        name = "Rotary Snub-Nose PPC";
        setInternalName("OSRotarySnubNosePPC");
        addLookupName("OS Rotary Snub-Nose PPC");
        addLookupName("Legion Rotary Snub-Nose PPC");
        addLookupName("RSPPC");
        sortingName = "PPC OS 4 Rotary 4 Snb 1 Std";
        heat = 8;
        damage = DAMAGE_VARIABLE;
        damageShort = 8;
        damageMedium = 6;
        damageLong = 4;
        minimumRange = 0;
        shortRange = 8;
        mediumRange = 12;
        longRange = 15;
        extremeRange = 22;
        waterShortRange = 5;
        waterMediumRange = 8;
        waterLongRange = 10;
        waterExtremeRange = 14;
        tonnage = 10.0;
        criticalSlots = 6;
        bv = 360;
        cost = 480000;
        shortAV = 8;
        medAV = 6;
        maxRange = RANGE_MED;
        // Gauss-style catastrophic explosion: critical hit triggers simultaneous discharge
        // across all four short-barrel capacitor stages. RSPPC's larger Snub-Nose geometry
        // stores more potential energy per stage than RLPPC, producing a larger blast (18 vs
        // 15). Gated through EquipmentType.isExplosive()'s F_PPC_ROTARY bypass — fires
        // unconditionally on weapon-crit without needing a PPC Capacitor module.
        explosionDamage = 18;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F)
              .setISAdvancement(3060, 3070, 3080, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }

    @Override
    public int getDamage(int range) {
        if (range <= shortRange) {
            return damageShort;
        }
        if (range <= mediumRange) {
            return damageMedium;
        }
        return damageLong;
    }
}
