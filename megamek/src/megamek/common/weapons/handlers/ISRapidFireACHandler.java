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
import megamek.server.totalWarfare.TWGameManager;

/**
 * Rapid-Fire AC handler — a fixed 3-round Rotary burst with NO jam.
 *
 * <p>Reliability is the RF identity, so the rotary jam roll is removed (only the ammo-feed-problem
 * check is kept). The cluster-hits table (size 3, expected exactly 2.0 hits) keeps RF's effective
 * damage — and therefore its BV — equal to the same-caliber standard AC, given a per-shot damage of
 * (standard damage / 2).</p>
 */
public class ISRapidFireACHandler extends RACHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public ISRapidFireACHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
    }

    @Override
    protected boolean doChecks(Vector<Report> vPhaseReport) {
        // RF never jams. Keep only the ammo-feed-problem check from the RAC chain.
        return doAmmoFeedProblemCheck(vPhaseReport);
    }
}
