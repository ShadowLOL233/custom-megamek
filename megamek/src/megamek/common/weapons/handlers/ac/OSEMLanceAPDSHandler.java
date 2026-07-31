/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.handlers.ac;

import java.io.Serial;

import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Electromagnetic Lance APDS handler — the full-power discarding-sabot penetrator. It keeps the
 * long-range profile (a rugged full-power round) and the inherent armor-piercing through-armor crit of
 * {@link ACAPHandler}, but the lighter sabot slug trades about 20% of its damage for penetration (and the ammo
 * carries more shots per ton).
 */
public class OSEMLanceAPDSHandler extends ACAPHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEMLanceAPDSHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
    }

    @Override
    protected int calcDamagePerHit() {
        return Math.max(1, (int) Math.round(super.calcDamagePerHit() * 0.8));
    }
}
