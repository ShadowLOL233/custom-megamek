/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.handlers;

import java.io.Serial;
import java.util.Vector;

import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.compute.Compute;
import megamek.common.equipment.AmmoType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Handler for the Outer Sphere Ultra LB-X Autocannon: an LB-X that can fire an
 * Ultra-style double tap. Reuses the Ultra multi-shot machinery (shot count from
 * the Single/Ultra mode, ammo consumption, jam-on-snake-eyes) and layers the LB-X
 * slug/cluster behavior on top: with cluster ammo each of the (1 or 2) shots bursts
 * into a full pellet spread; with slug ammo each shot is a single full-damage hit.
 */
public class OSUltraLBXHandler extends UltraWeaponHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSUltraLBXHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
        sSalvoType = " pellet(s) ";
    }

    private boolean isClusterAmmo() {
        return (ammo != null)
              && ammo.getType().getMunitionType().contains(AmmoType.Munitions.M_CLUSTER);
    }

    @Override
    protected boolean usesClusterTable() {
        return isClusterAmmo() || super.usesClusterTable();
    }

    @Override
    protected int calcDamagePerHit() {
        if (isClusterAmmo() && !target.isConventionalInfantry()) {
            return 1;
        }
        return super.calcDamagePerHit();
    }

    @Override
    protected int calcHits(Vector<Report> vPhaseReport) {
        if (!isClusterAmmo()) {
            return super.calcHits(vPhaseReport);
        }
        if (target.isConventionalInfantry()) {
            return 1;
        }
        bSalvo = true;
        int shots = Math.max(1, howManyShots);
        int nMod = getClusterModifiers(true);
        int total = 0;
        for (int i = 0; i < shots; i++) {
            total += allShotsHit() ? weaponType.getRackSize()
                  : Compute.missilesHit(weaponType.getRackSize(), nMod);
        }

        Report r = new Report(3325);
        r.subject = subjectId;
        r.add(total);
        r.add(sSalvoType);
        r.add(toHit.getTableDesc());
        r.newlines = 0;
        vPhaseReport.addElement(r);
        if (nMod != 0) {
            r = new Report(nMod > 0 ? 3340 : 3341);
            r.subject = subjectId;
            r.add(nMod);
            r.newlines = 0;
            vPhaseReport.addElement(r);
        }
        r = new Report(3345);
        r.subject = subjectId;
        vPhaseReport.addElement(r);
        return total;
    }
}
