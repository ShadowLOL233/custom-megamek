/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.handlers;

import java.io.Serial;

import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.equipment.AmmoType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere ATM handler. Adds the LRTM payload's Streak behaviour: when firing the ER (LRTM)
 * munition, a successful attack lands all missiles (full salvo). SRTM (HE) and APTM (standard)
 * resolve as normal ATM rounds. The APTM/LRTM -1 to-hit is carried by the ammo itself.
 */
public class OSATMHandler extends ATMHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSATMHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
    }

    @Override
    protected boolean allShotsHit() {
        AmmoType at = ammo.getType();
        if ((at != null) && at.getMunitionType().contains(AmmoType.Munitions.M_EXTENDED_RANGE)) {
            return true; // LRTM: Streak-style full salvo
        }
        return super.allShotsHit();
    }
}
