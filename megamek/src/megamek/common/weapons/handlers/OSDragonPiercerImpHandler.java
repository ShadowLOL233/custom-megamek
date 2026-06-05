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
 * Improve Dragon Piercer breach handler - lowers the breach crit threshold to 7+ (modifier +1).
 */
public class OSDragonPiercerImpHandler extends OSDragonPiercerHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSDragonPiercerImpHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
    }

    @Override
    protected int getBreachCritModifier() {
        return 1;
    }
}
