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

import megamek.common.RangeType;
import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.compute.Compute;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.options.OptionsConstants;
import megamek.common.planetaryConditions.PlanetaryConditions;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Handler for Outer Sphere PPC-X family weapons.
 *
 * The PPC-X fires 6 sub-beams in a clustered pattern (modeled on LB-X autocannon mechanics).
 * Number of hits is determined by the missile cluster hit table; each hit deals the weapon's
 * per-sub-beam damage (2 for PPC-X, 4 for Heavy PPC-X).
 *
 * The range-bracket ToHit modifier (-1 / +0 / +1) is applied separately in
 * {@code ComputeToHit} via {@code instanceof OSPPCXWeapon}.
 */
public class OSPPCXHandler extends PPCHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSPPCXHandler(ToHitData toHit, WeaponAttackAction waa, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(toHit, waa, g, m);
        sSalvoType = " beam(s) ";
    }

    @Override
    protected int calcDamagePerHit() {
        // Each sub-beam deals the weapon's base damage; total damage = damage × rackSize
        return weaponType.getDamage();
    }

    @Override
    protected int calcHits(Vector<Report> vPhaseReport) {
        int shotsHit;
        int nHitsModifier = getClusterModifiers(true);

        if (allShotsHit()) {
            shotsHit = weaponType.getRackSize();
            if (game.getOptions().booleanOption(OptionsConstants.ADVANCED_COMBAT_TAC_OPS_RANGE)
                  && (nRange > weaponType.getRanges(weapon)[RangeType.RANGE_LONG])) {
                shotsHit = (int) Math.ceil(shotsHit * .75);
            }
            if (game.getOptions().booleanOption(OptionsConstants.ADVANCED_COMBAT_TAC_OPS_LOS_RANGE)
                  && (nRange > weaponType.getRanges(weapon)[RangeType.RANGE_EXTREME])) {
                shotsHit = (int) Math.ceil(shotsHit * .5);
            }
        } else {
            PlanetaryConditions conditions = game.getPlanetaryConditions();
            shotsHit = Compute.missilesHit(weaponType.getRackSize(), nHitsModifier, conditions.getEMI().isEMI());
        }

        Report report = new Report(3325);
        report.subject = subjectId;
        report.add(shotsHit);
        report.add(sSalvoType);
        report.add(toHit.getTableDesc());
        report.newlines = 0;
        vPhaseReport.addElement(report);
        if (nHitsModifier != 0) {
            report = (nHitsModifier > 0) ? new Report(3340) : new Report(3341);
            report.subject = subjectId;
            report.add(nHitsModifier);
            report.newlines = 0;
            vPhaseReport.addElement(report);
        }
        report = new Report(3345);
        report.subject = subjectId;
        vPhaseReport.addElement(report);
        bSalvo = true;
        return shotsHit;
    }

    @Override
    protected boolean usesClusterTable() {
        return true;
    }

    @Override
    protected int calculateNumCluster() {
        return 1;
    }
}
