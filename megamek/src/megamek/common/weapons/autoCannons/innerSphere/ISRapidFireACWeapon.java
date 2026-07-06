/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.autoCannons.innerSphere;

import static megamek.common.game.IGame.LOGGER;

import java.io.Serial;

import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.annotations.Nullable;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.equipment.AmmoType;
import megamek.common.weapons.autoCannons.RACWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.ISRapidFireACHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Base class for Inner Sphere Rapid-Fire ACs — same-BV flavor sidegrades of the standard AC (MW5-style
 * "AC/N RF").
 *
 * <p>Each fires a fixed 3-round burst (per-shot damage = standard damage / 2) on the Rotary engine: one
 * to-hit roll, cluster-hits table (size 3, expected 2.0 hits), and — unlike a real Rotary AC — NO jam.
 * Because 2.0 &times; (std / 2) = std, effective damage equals the standard AC at every range, so BV is
 * kept identical to the same-caliber standard AC.</p>
 */
public abstract class ISRapidFireACWeapon extends RACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public ISRapidFireACWeapon() {
        super();
        // Fixed 3-round burst; a single-shot fallback is left selectable but is strictly weaker.
        setModes(new String[] { MODE_RAC_THREE_SHOT, MODE_AC_SINGLE });
        // RF never jams, so it is not "explosive when jammed" like a real Rotary AC.
        explosive = false;
        ammoType = AmmoType.AmmoTypeEnum.AC_RF;
    }

    @Override
    @Nullable
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new ISRapidFireACHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
