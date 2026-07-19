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
 * OS "Improve C3 Node" — the Improve-tier refinement of the {@link OSC3Node}. Same Boosted C3-master behavior;
 * its wider slave capacity (links 3 Nodes + 6 Points) is a topology tuning applied in the mechanics pass.
 */
public class OSImproveC3Node extends TAGWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveC3Node() {
        super();
        name = "Improve C3 Node";
        shortName = "Improve C3 Node";
        setInternalName("OSImproveC3Node");
        addLookupName("OS Improve C3 Node");
        tonnage = 2;
        criticalSlots = 2;
        svSlots = 2;
        tankSlots = 1;
        hittable = true;
        spreadable = false;
        cost = 2000000;
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
              .setISAdvancement(2900, 2930, 2960, DATE_NONE, DATE_NONE)
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
