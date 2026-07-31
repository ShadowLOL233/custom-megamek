/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.c3;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.WeaponTypeFlag;
import megamek.common.weapons.tag.TAGWeapon;

/**
 * OS Shrike Combat Designator (dev plan §9.6) — an Enhanced-tier crit-capable TAG. On a successful designation it
 * grants every same-C3-network unit attacking that target a to-hit bonus this turn: −1 on a normal hit, and a
 * further −2 (total −3) when the designation roll is a critical (natural 9+). Dead weight if the mounting unit
 * is not in an active C3 network.
 *
 * <p><b>Mechanics-remaining (TODO, mechanics pass):</b> the crit detection + per-turn network-bonus state on the
 * target, and its injection into {@code ComputeTargetToHitMods} gated on {@code onSameC3NetworkAs} the
 * designator. For now the weapon exists and designates like a TAG.
 */
public class OSShrikeDesignator extends TAGWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSShrikeDesignator() {
        super();
        name = "Shrike Combat Designator";
        shortName = "Shrike";
        setInternalName("OSShrikeDesignator");
        addLookupName("OS Shrike Combat Designator");
        addLookupName("Shrike Combat Designator");
        tonnage = 3.0;
        criticalSlots = 2;
        svSlots = 2;
        tankSlots = 2;
        hittable = true;
        spreadable = false;
        cost = 320000;
        bv = 110;
        flags = flags.or(WeaponTypeFlag.F_OS_SHRIKE_DESIGNATOR).or(F_MEK_WEAPON).or(F_TANK_WEAPON).andNot(F_AERO_WEAPON);
        heat = 0;
        damage = 0;
        shortRange = 9;
        mediumRange = 12;
        longRange = 15;
        extremeRange = 17;
        rulesRefs = "AU";
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3000, 3025, 3050, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
