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
 * Handler for the Outer Sphere Assault Autocannon. One trigger pull fires a burst
 * of three projectiles from a single round; how many of the three connect is rolled
 * on the cluster table (reusing the Ultra multi-shot machinery with a fixed count of
 * three), and each connecting projectile deals the weapon's full per-shot damage to
 * its own hit location. Firing heat is the weapon's flat heat value (no per-shot
 * multiplier, since the burst consumes only one round).
 */
public class OSAssaultACHandler extends UltraWeaponHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAssaultACHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
    }

    @Override
    protected void useAmmo() {
        setDone();
        checkAmmo();
        howManyShots = 3;
        int total = weaponEntity.getTotalAmmoOfType(ammo.getType());
        if (total < 1) {
            howManyShots = 0;
        }
        attemptToReloadWeapon();
        // One burst consumes a single round regardless of how many projectiles it splits into.
        reduceShotsLeft(howManyShots > 0 ? 1 : 0);
    }
}
