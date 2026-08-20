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
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.WeaponTypeFlag;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.OSDemonHackingHandler;
import megamek.common.weapons.tag.TAGWeapon;
import megamek.server.totalWarfare.TWGameManager;

/**
 * OS "Demon Aggressive Hacking System" (dev plan §9) — an Advance-tier offensive-EW designator that borrows TAG's
 * targeting mode. It is the only TAG-type system that can score a critical hit.
 * <p>
 * Against a target hacked this turn, when that target shoots at the hacker's team it takes +1 to-hit (+2 if the Demon
 * designation roll was a natural-9+ critical), doubled if the target is also inside a hostile Guardian/Angel ECM bubble
 * (max +4). The crit detection + per-turn target state live in {@link OSDemonHackingHandler}; the penalty injection is
 * in {@code ComputeToHit} (via {@link OSNetworkDesignators#demonHackPenalty}).
 */
public class OSDemonHackingSystem extends TAGWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSDemonHackingSystem() {
        super();
        name = "B-2500 Demon Aggressive Hacking System";
        shortName = "Demon Hacking System";
        setInternalName("OSDemonHackingSystem");
        addLookupName("OS Demon Hacking System");
        tonnage = 1.5;
        criticalSlots = 1;
        svSlots = 1;
        tankSlots = 1;
        hittable = true;
        spreadable = false;
        cost = 1000000;
        bv = 0;
        flags = flags.or(WeaponTypeFlag.F_OS_DEMON_HACKING).or(WeaponTypeFlag.F_OS_BCS_MODULE).or(F_MEK_WEAPON)
              .or(F_TANK_WEAPON).andNot(F_AERO_WEAPON);
        heat = 0;
        damage = 0;
        shortRange = 5;
        mediumRange = 9;
        longRange = 15;
        extremeRange = 18;
        rulesRefs = "AU";
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3060, 3070, 3080, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }

    @Override
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new OSDemonHackingHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            return null;
        }
    }
}
