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
 */

package megamek.common.weapons.ppc;

import static megamek.common.game.IGame.LOGGER;

import java.io.Serial;
import java.util.Arrays;

import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.annotations.Nullable;
import megamek.common.equipment.WeaponType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.RotaryPPCHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Abstract base class for the Outer Sphere Rotary PPC family.
 *
 * Adapts the Rotary AC firing-mode pattern to a PPC chassis: subclasses declare
 * a maximum shot count (3-6) and the constructor builds the corresponding 1..N
 * shot mode list. The first three shots are self-cooled and pod-free; only shots
 * beyond the third draw one charge each from the entity-wide RPPC Coolant Pod
 * pool. Suppressed shots cost {@code ceil(baseHeat / 3)} heat instead of the full
 * base heat. Dialing a 4+ shot mode without sufficient coolant triggers a
 * Capacitor Overload — heavier than a RAC jam (weapon destroyed, +15 entity heat,
 * +15 location critical damage).
 *
 * Coolant consumption, heat suppression, overload resolution, and per-bolt
 * variable damage (Snub-Nose variant) are all handled in
 * {@link RotaryPPCHandler}.
 */
public abstract class PPCRotaryWeapon extends PPCWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a Rotary PPC with firing modes 1-shot through {@code maxShots}-shot.
     *
     * @param maxShots the maximum shots-per-turn this weapon allows; must be 2-6.
     */
    protected PPCRotaryWeapon(int maxShots) {
        super();
        if (maxShots < 2 || maxShots > 6) {
            throw new IllegalArgumentException("Rotary PPC maxShots must be 2-6, got " + maxShots);
        }
        // No ammoType set — Rotary PPC is an energy weapon. RPPC Coolant Pods are consumed
        // by the handler from the entity-wide pool whenever the weapon fires in 2+ shot mode.
        String[] allModes = { MODE_AC_SINGLE, MODE_RAC_TWO_SHOT, MODE_RAC_THREE_SHOT,
                              MODE_RAC_FOUR_SHOT, MODE_RAC_FIVE_SHOT, MODE_RAC_SIX_SHOT };
        setModes(Arrays.copyOfRange(allModes, 0, maxShots));
        flags = flags.or(WeaponType.F_PPC_ROTARY);
        explosive = true;
        atClass = CLASS_PPC;
    }

    @Override
    @Nullable
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new RotaryPPCHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
