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
 * Outer Sphere Electromagnetic Lance precision handler — a low-power payload round. Its delicate guidance
 * package survives only a throttled-down launch, so it collapses to the short-range profile (see
 * {@code OSElectromagneticLance.getRanges}) and hits for about 60% damage with no armor-piercing, in exchange
 * for cancelling up to −2 of the target's movement to-hit modifier (wired in {@code ComputeTargetToHitMods}
 * for the M_PRECISION munition on the EM_LANCE_OS enum).
 */
public class OSEMLancePrecisionHandler extends ACWeaponHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEMLancePrecisionHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
    }

    @Override
    protected int calcDamagePerHit() {
        return Math.max(1, (int) Math.round(super.calcDamagePerHit() * 0.6));
    }
}
