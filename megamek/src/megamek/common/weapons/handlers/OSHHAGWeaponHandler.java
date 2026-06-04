/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.handlers;

import java.io.Serial;

import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Handler for the Outer Sphere Heavy Hyper-Assault Gauss. Behaves like a standard HAG but
 * delivers a denser shot pattern: a flat +2 to the cluster roll on top of the HAG range
 * modifiers (net +4 short / 0 long), so more sub-caliber slugs connect.
 */
public class OSHHAGWeaponHandler extends HAGWeaponHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHHAGWeaponHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
    }

    @Override
    protected int getClusterModifiers(boolean clusterRangePenalty) {
        return super.getClusterModifiers(clusterRangePenalty) + 2;
    }
}
