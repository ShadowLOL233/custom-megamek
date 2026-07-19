/*
 * Copyright (C) 2025 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPL),
 * version 3 or (at your option) any later version,
 * as published by the Free Software Foundation.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A copy of the GPL should have been included with this project;
 * if not, see <https://www.gnu.org/licenses/>.
 *
 * NOTICE: The MegaMek organization is a non-profit group of volunteers
 * creating free software for the BattleTech community.
 */

package megamek.common.weapons.c3;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.weapons.tag.TAGWeapon;

/**
 * OS "C3 Node" — a Boosted (ECM-resistant) C3 master equivalent for the Battle Computer System (dev plan §9).
 * Lighter than the canon C3 Boosted Master. Connection-topology tuning (links up to 3 Nodes/Points) and the
 * B-2500-core prerequisite are wired in a later mechanics pass; for now it behaves as a boosted C3 master.
 */
public class OSC3Node extends TAGWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSC3Node() {
        super();
        name = "C3 Node";
        shortName = "C3 Node";
        setInternalName("OSC3Node");
        addLookupName("OS C3 Node");
        tonnage = 2;
        criticalSlots = 2;
        svSlots = 2;
        tankSlots = 1;
        hittable = true;
        spreadable = false;
        cost = 1500000;
        bv = 0;
        flags = flags.or(F_C3MBS).or(F_MEK_WEAPON).or(F_TANK_WEAPON).andNot(F_AERO_WEAPON);
        heat = 0;
        damage = 0;
        shortRange = 5;
        mediumRange = 9;
        longRange = 15;
        extremeRange = 18;
        rulesRefs = "AU";
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.E, AvailabilityValue.D)
              .setISAdvancement(2805, 2820, 2840, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }

    @Override
    public boolean isC3Equipment() {
        return true;
    }
}
