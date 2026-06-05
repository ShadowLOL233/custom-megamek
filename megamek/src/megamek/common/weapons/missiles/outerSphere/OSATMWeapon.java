/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.missiles.outerSphere;

import java.io.Serial;

import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.OSATMHandler;
import megamek.common.weapons.missiles.ATMWeapon;
import megamek.server.totalWarfare.TWGameManager;

/** Outer Sphere ATM base - routes to {@link OSATMHandler} for LRTM Streak behaviour. */
public abstract class OSATMWeapon extends ATMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new OSATMHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            return null;
        }
    }
}
