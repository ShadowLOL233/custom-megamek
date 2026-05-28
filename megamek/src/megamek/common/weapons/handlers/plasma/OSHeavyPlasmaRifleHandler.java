/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.handlers.plasma;

import java.io.Serial;
import java.util.Vector;

import megamek.common.RangeType;
import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.battleArmor.BattleArmor;
import megamek.common.compute.Compute;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.options.OptionsConstants;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Handler for the Outer Sphere Heavy Plasma Rifle (School B magnetic-confinement
 * torus). Identical to the standard Plasma Rifle handler except the densified,
 * coherent plasma toroid carries kinetic momentum and so breaks the canonical
 * 10-point thermal damage ceiling: 15 armor damage (plus the inherited 2d6 heat).
 */
public class OSHeavyPlasmaRifleHandler extends PlasmaRifleHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyPlasmaRifleHandler(ToHitData toHitData, WeaponAttackAction weaponAttackAction, Game game,
          TWGameManager twGameManager) throws EntityLoadingException {
        super(toHitData, weaponAttackAction, game, twGameManager);
    }

    @Override
    protected int calcDamagePerHit() {
        if (target.tracksHeat()) {
            int toReturn = 15;
            toReturn = applyGlancingBlowModifier(toReturn, false);
            if (game.getOptions().booleanOption(OptionsConstants.ADVANCED_COMBAT_TAC_OPS_RANGE) &&
                  (nRange > weaponType.getRanges(weapon)[RangeType.RANGE_LONG])) {
                toReturn -= 1;
            }
            if (game.getOptions().booleanOption(OptionsConstants.ADVANCED_COMBAT_TAC_OPS_LOS_RANGE) &&
                  (nRange > weaponType.getRanges(weapon)[RangeType.RANGE_EXTREME])) {
                toReturn = (int) Math.floor(toReturn / 2.0);
            }
            return toReturn;
        }
        return 1;
    }

    @Override
    protected int calcHits(Vector<Report> vPhaseReport) {
        // against meks, 1 hit with 15 damage, plus heat
        if (target.tracksHeat()) {
            return 1;
        }
        // otherwise, 15+2d6 damage, fire-resistant BA gets only half
        if ((target instanceof BattleArmor) && ((BattleArmor) target).isFireResistant()) {
            return 7;
        }
        int toReturn = 15 + Compute.d6(2);
        return applyGlancingBlowModifier(toReturn, false);
    }
}
