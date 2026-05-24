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

package megamek.common.weapons.handlers;

import java.io.Serial;
import java.util.Vector;

import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Handler for Outer Sphere Hyper Laser series.
 *
 * After firing, the weapon enters a 1-turn cooldown (tracked via
 * {@link megamek.common.equipment.Mounted#getHyperLaserCooldown()}). During the
 * cooldown turn the weapon is unusable; the counter is decremented automatically by
 * {@code Mounted.newRound(int)} so the weapon becomes ready again on the round after.
 *
 * Phase report messages are emitted to keep the player informed of the heat spike and
 * cooldown lockout.
 */
public class OSHyperLaserHandler extends EnergyWeaponHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Number of full rounds the weapon is locked out after firing. With the value 2,
     * {@code newRound} decrements once between firing turns leaving the counter at 1,
     * then again leaving it at 0 — yielding exactly one fully unusable round between fires.
     */
    private static final int COOLDOWN_TURNS = 2;

    public OSHyperLaserHandler(ToHitData toHit, WeaponAttackAction waa, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(toHit, waa, g, m);
    }

    @Override
    protected boolean doChecks(Vector<Report> vPhaseReport) {
        if (super.doChecks(vPhaseReport)) {
            return true;
        }

        // Emit fire-with-heat-spike notification
        Report fireReport = new Report(1262);
        fireReport.subject = subjectId;
        fireReport.indent(2);
        fireReport.add(weaponType.getHeat());
        vPhaseReport.add(fireReport);

        if (weapon != null) {
            // Lock the weapon out for one full turn so the player cannot fire again next round.
            weapon.setHyperLaserCooldown(COOLDOWN_TURNS);
            // Reset the charging mode back to "Off" — player must explicitly re-engage charging
            // after the cooldown to fire again. Uses pendingMode so the change takes effect at
            // the start of the next round, keeping this round's accounting clean.
            int offIdx = findModeIndex(weapon, "Off");
            if (offIdx >= 0) {
                weapon.setMode(offIdx);
            }
        }

        return false;
    }

    private static int findModeIndex(megamek.common.equipment.Mounted<?> weapon, String modeName) {
        for (int i = 0; i < weapon.getType().getModesCount(); i++) {
            if (modeName.equals(weapon.getType().getMode(i).getName())) {
                return i;
            }
        }
        return -1;
    }
}
