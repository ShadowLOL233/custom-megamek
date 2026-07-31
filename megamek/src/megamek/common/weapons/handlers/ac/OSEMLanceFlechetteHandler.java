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
 * Outer Sphere Electromagnetic Lance flechette handler — the low-power flechette payload round. It behaves
 * like the standard AC flechette (anti-infantry, double damage vs woods), but the throttled-down coil launch
 * that lets the fragile round survive also cuts its damage to about 60% (the power-gate's damage penalty; the
 * matching range collapse is applied in {@code OSElectromagneticLance.getRanges}).
 */
public class OSEMLanceFlechetteHandler extends ACFlechetteHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEMLanceFlechetteHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
    }

    @Override
    protected int calcDamagePerHit() {
        return Math.max(1, (int) Math.round(super.calcDamagePerHit() * 0.6));
    }
}
