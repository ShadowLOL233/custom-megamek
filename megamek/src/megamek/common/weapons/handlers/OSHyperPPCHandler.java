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
import megamek.common.equipment.Mounted;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Handler for Outer Sphere Hyper PPC.
 *
 * Reuses the same charge/fire/cooldown cycle as {@link OSHyperLaserHandler} but
 * extends {@link PPCHandler} so PPC-specific behavior (field inhibitor checks,
 * capacitor interactions) is preserved.
 *
 * Lockout after firing is enforced via {@link Mounted#setHyperLaserCooldown(int)}
 * (the field is named after the laser but is mechanically generic).
 */
public class OSHyperPPCHandler extends PPCHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Number of full rounds the weapon is locked out after firing. With the value 2,
     * Mounted.newRound() decrements once leaving the counter at 1 (cooldown round),
     * then again leaving it at 0 — yielding exactly one fully unusable round between fires.
     */
    private static final int COOLDOWN_TURNS = 2;

    public OSHyperPPCHandler(ToHitData toHit, WeaponAttackAction waa, Game g, TWGameManager m)
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
            weapon.setHyperLaserCooldown(COOLDOWN_TURNS);
            // Reset the charging mode back to "Off" — player must explicitly re-engage charging
            // after the cooldown to fire again.
            int offIdx = findModeIndex(weapon, "Off");
            if (offIdx >= 0) {
                weapon.setMode(offIdx);
            }
        }

        return false;
    }

    private static int findModeIndex(Mounted<?> weapon, String modeName) {
        for (int i = 0; i < weapon.getType().getModesCount(); i++) {
            if (modeName.equals(weapon.getType().getMode(i).getName())) {
                return i;
            }
        }
        return -1;
    }
}
