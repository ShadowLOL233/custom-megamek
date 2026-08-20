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
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.units.Entity;
import megamek.common.units.IBuilding;
import megamek.server.totalWarfare.TWGameManager;

/**
 * OS Demon Aggressive Hacking System handler (dev plan §9.1). Resolves like a normal TAG (the superclass records the
 * standard {@code TagInfo} + spotting), but additionally records a Demon hack on the target — including whether the
 * designation roll was a natural-9+ critical — so that when that target shoots at the hacker's team this turn it takes
 * a +1 (or +2 on a crit) to-hit penalty, doubled inside a hostile Guardian/Angel ECM bubble (max +4). The penalty
 * itself is applied in {@code ComputeToHit}.
 */
public class OSDemonHackingHandler extends TAGHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSDemonHackingHandler(ToHitData toHit, WeaponAttackAction waa, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(toHit, waa, g, m);
    }

    @Override
    protected void handleEntityDamage(Entity entityTarget, Vector<Report> vPhaseReport, IBuilding bldg,
          int hits, int nCluster, int bldgAbsorbs) {
        super.handleEntityDamage(entityTarget, vPhaseReport, bldg, hits, nCluster, bldgAbsorbs);
        if (entityTarget != null) {
            boolean crit = (roll != null) && (roll.getIntValue() >= 9);
            entityTarget.setDemonHackedBy(attackingEntity.getId(), crit);
        }
    }
}
