/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
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
 *
 * MechWarrior, BattleMech, `Mech and AeroTech are registered trademarks
 * of The Topps Company, Inc. All Rights Reserved.
 *
 * Catalyst Game Labs and the Catalyst Game Labs logo are trademarks of
 * InMediaRes Productions, LLC.
 *
 * MechWarrior Copyright Microsoft Corporation. MegaMek was created under
 * Microsoft's "Game Content Usage Rules"
 * <https://www.xbox.com/en-US/developers/rules> and it is not endorsed by or
 * affiliated with Microsoft.
 */

package megamek.common.weapons.ppc.clan;

import java.io.Serial;

import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.SimpleTechLevel;
import megamek.common.weapons.ppc.PPCWeapon;

/**
 * Clan Re-Engineering PPC — a refined Clan particle cannon trading some range and a short
 * minimum-range deadzone for a heavier, harder-hitting, cooler-running design than the ER PPC.
 */
public class CLReEngineeringPPC extends PPCWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public CLReEngineeringPPC() {
        super();
        this.name = "Re-Engineering PPC";
        this.setInternalName("CLReEngineeringPPC");
        this.addLookupName("Clan Re-Engineering PPC");
        this.addLookupName("CLRePPC");
        sortingName = "PPC Re-Engineering";
        this.heat = 12;
        this.damage = 12;
        this.minimumRange = 2;
        this.shortRange = 7;
        this.mediumRange = 14;
        this.longRange = 20;
        this.extremeRange = 30;
        this.waterShortRange = 5;
        this.waterMediumRange = 9;
        this.waterLongRange = 13;
        this.waterExtremeRange = 20;
        this.tonnage = 8.0;
        this.criticalSlots = 4;
        this.bv = 300.0;
        this.cost = 280000;
        this.shortAV = 12;
        this.medAV = 12;
        this.longAV = 12;
        this.maxRange = RANGE_LONG;
        rulesRefs = "Custom (Outer Sphere AU)";
        techAdvancement.setTechBase(TechBase.CLAN)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.E, AvailabilityValue.D)
              .setClanAdvancement(3065, 3070, 3075, DATE_NONE, DATE_NONE)
              .setClanApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.CSR)
              .setProductionFactions(Faction.CSR)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }
}
