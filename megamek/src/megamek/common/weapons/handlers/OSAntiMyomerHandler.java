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
import megamek.common.equipment.AmmoType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.rolls.PilotingRollData;
import megamek.common.units.Entity;
import megamek.common.units.Mek;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere LB-X Anti-Myomer Flechette: fires on the LB-X cluster table (inherited from
 * {@link LBXHandler}) and, on a hit against a BattleMek, forces the target into a piloting skill roll as
 * the flechettes shred exposed myomer bundles. Each pellet also carries a slight armor-piercing crit
 * bias (the flechettes bite into exposed internals).
 */
public class OSAntiMyomerHandler extends LBXHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAntiMyomerHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
    }

    @Override
    protected boolean usesClusterTable() {
        // Anti-Myomer always fires the pellet spread; the base LBXHandler keys this off the M_CLUSTER
        // munition, which this round does not carry.
        return true;
    }

    @Override
    protected void initHit(Entity entityTarget) {
        super.initHit(entityTarget);
        // Bias every pellet toward the armor-piercing crit table (slight AP crit per the design).
        hit.makeArmorPiercing((AmmoType) weapon.getLinked().getType(), 0);
    }

    @Override
    protected boolean doChecks(Vector<Report> vPhaseReport) {
        boolean missReported = super.doChecks(vPhaseReport);
        // bMissed is finalized before doChecks runs, so only force the PSR when the attack connected.
        if (!bMissed && (target instanceof Mek targetMek) && targetMek.canFall()) {
            game.addPSR(new PilotingRollData(targetMek.getId(), 0, "hit by Anti-Myomer flechette", false));
        }
        return missReported;
    }
}
