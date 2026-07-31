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
 * Outer Sphere Electromagnetic Lance incendiary (HE) handler — a low-power payload round. The fragile
 * incendiary filler survives only a throttled-down launch, so it collapses to the short-range profile and hits
 * for about 60% damage with no armor-piercing, while still setting fires like the standard AC incendiary round.
 */
public class OSEMLanceIncendiaryHandler extends ACIncendiaryHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEMLanceIncendiaryHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
    }

    @Override
    protected int calcDamagePerHit() {
        return Math.max(1, (int) Math.round(super.calcDamagePerHit() * 0.6));
    }
}
