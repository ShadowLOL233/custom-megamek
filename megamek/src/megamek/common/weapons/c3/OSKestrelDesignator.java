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
 * OS Kestrel Network Target Designator (dev plan §9.6) — an Improve-tier C3-network target designator. It borrows
 * TAG's targeting mode to designate a target; while its mounting unit is in an active C3/BCS network and either
 * holds LOS to the designated target or a Lodestar beacon is attached to it (and the target is within the
 * Kestrel's own range), the whole network gains −1 to-hit against that target with all direct-fire weapons. The
 * Lodestar-beacon path additionally grants the network LRM indirect-fire capability against the beaconed target
 * (the beacon is the remote lock; no spotter LOS needed). Kestrel carries no self to-hit modifier. Dead weight if
 * the mounting unit is not in an active C3 network.
 *
 * <p><b>Mechanics-remaining (TODO, mechanics pass):</b> the network −1 direct-fire injection into
 * {@code ComputeTargetToHitMods} (gated on {@code onSameC3NetworkAs} plus LOS-or-Lodestar-beacon), the
 * Lodestar LRM-indirect enable, and the per-turn designation state. The range/spotter reading side lives near
 * {@code ComputeC3Spotter.findC3Spotter}. For now the weapon exists and designates like a TAG.
 */
public class OSKestrelDesignator extends TAGWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSKestrelDesignator() {
        super();
        name = "Kestrel Network Target Designator";
        shortName = "Kestrel NTD";
        setInternalName("OSKestrelDesignator");
        addLookupName("OS Kestrel NTD");
        addLookupName("Kestrel Network Target Designator");
        tonnage = 4.0;
        criticalSlots = 3;
        svSlots = 3;
        tankSlots = 3;
        hittable = true;
        spreadable = false;
        cost = 200000;
        bv = 130;
        flags = flags.or(WeaponTypeFlag.F_OS_KESTREL_DESIGNATOR).or(F_MEK_WEAPON).or(F_TANK_WEAPON).andNot(F_AERO_WEAPON);
        heat = 0;
        damage = 0;
        shortRange = 15;
        mediumRange = 16;
        longRange = 17;
        extremeRange = 18;
        rulesRefs = "AU";
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2900, 2930, 2960, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
