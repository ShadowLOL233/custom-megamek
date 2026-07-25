/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.autoCannons.outerSphere;

import static megamek.common.game.IGame.LOGGER;

import java.io.Serial;

import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.annotations.Nullable;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.autoCannons.HVACWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.ac.ACWeaponHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Base class for the Outer Sphere HVAC line — electromagnetic-chemical autocannons.
 *
 * <p>The Inner Sphere HVAC cooks off: its volatile, high-pressure chemical breech detonates on a
 * critical hit and can rupture on a bad firing cycle. The OS pattern is a fundamentally different
 * weapon — a stable chemical charge accelerated by an electromagnetic stage — so it has no such
 * catastrophic-detonation failure mode. Accordingly these weapons are NOT treated as explosive on a
 * critical hit ({@code explosive = false}, {@code explosionDamage = 0}) and use the plain autocannon
 * attack handler instead of {@link HVACWeapon}'s cook-off handler, so a natural-2 attack roll no
 * longer jams-and-explodes the weapon.
 */
public abstract class OSHVACWeapon extends HVACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    protected OSHVACWeapon() {
        super();
        // Electromagnetic-chemical design: no sympathetic detonation / cook-off.
        explosive = false;
        explosionDamage = 0;
    }

    @Override
    @Nullable
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game, TWGameManager manager) {
        try {
            return new ACWeaponHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
