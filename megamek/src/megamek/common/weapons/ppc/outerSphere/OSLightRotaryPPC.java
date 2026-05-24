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
 * Outer Sphere Rotary Light PPC (RLPPC) — the standard entry in the Rotary PPC family.
 *
 * Mode-dial cluster PPC: 5 damage per sub-bolt across 1-5 firing modes / 5 heat per shot /
 * Range 6-12-18 no min / 7t / 5 crit / BV ~280.
 *
 * Every shot beyond the first consumes one charge from an installed RPPC Coolant Pod and
 * converts that shot's heat to ⌈5/3⌉ = 2 heat. Firing 2+ shot without sufficient coolant
 * triggers Capacitor Overload: weapon destroyed, entity heat +15, and 15 points of
 * internal damage to the mounting location.
 */
public class OSLightRotaryPPC extends PPCRotaryWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSLightRotaryPPC() {
        super(5);  // 1-shot through 5-shot modes
        name = "Rotary Light PPC";
        setInternalName("OSRotaryLightPPC");
        addLookupName("OS Rotary Light PPC");
        addLookupName("Legion Rotary Light PPC");
        addLookupName("RLPPC");
        sortingName = "PPC Rotary Light B";
        heat = 5;
        damage = 5;
        shortRange = 6;
        mediumRange = 12;
        longRange = 18;
        extremeRange = 24;
        waterShortRange = 4;
        waterMediumRange = 8;
        waterLongRange = 12;
        waterExtremeRange = 16;
        tonnage = 7.0;
        criticalSlots = 5;
        bv = 280;
        cost = 350000;
        shortAV = 5;
        medAV = 5;
        maxRange = RANGE_MED;
        // Gauss-style catastrophic explosion: when a critical hit destroys the weapon, the
        // five-stage capacitor bank discharges across the rotating drum simultaneously. The
        // F_PPC_ROTARY exception in EquipmentType.isExplosive() bypasses the standard PPC
        // capacitor requirement, so this damage is applied unconditionally on weapon-crit.
        explosionDamage = 15;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F)
              .setISAdvancement(3130, 3135, 3140, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
