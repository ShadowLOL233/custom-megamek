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
 * OS Shrike Combat Designator handler (dev plan §9.6). Resolves like a normal TAG (the superclass records the
 * standard {@code TagInfo} + spotting), but additionally records a Shrike designation on the target — including
 * whether the designation roll was a natural-9+ critical — so that same-C3-network attackers get -1 (or -3 on a
 * crit) to-hit against that target this turn. The bonus itself is applied in {@code ComputeToHit}.
 */
public class ShrikeTAGHandler extends TAGHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public ShrikeTAGHandler(ToHitData toHit, WeaponAttackAction waa, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(toHit, waa, g, m);
    }

    @Override
    protected void handleEntityDamage(Entity entityTarget, Vector<Report> vPhaseReport, IBuilding bldg,
          int hits, int nCluster, int bldgAbsorbs) {
        super.handleEntityDamage(entityTarget, vPhaseReport, bldg, hits, nCluster, bldgAbsorbs);
        if (entityTarget != null) {
            boolean crit = (roll != null) && (roll.getIntValue() >= 9);
            entityTarget.setShrikeDesignatedBy(attackingEntity.getId(), crit);
        }
    }
}
