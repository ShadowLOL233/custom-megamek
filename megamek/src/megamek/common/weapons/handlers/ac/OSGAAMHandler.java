/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.handlers.ac;

import java.io.Serial;

import megamek.common.RangeType;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere LB-X GAAM (Guided Anti-Armor Missile): a single guided projectile fired at full rack
 * damage. It inherits the armor-piercing critical behavior from {@link ACAPHandler} and layers on the
 * guided-round range profile: no damage inside minimum range (the round cannot arm) and a +25% damage
 * bonus in the medium-range guided sweet spot. Range bands come from the M_GAAM ammo override in
 * {@code WeaponType.getRanges}.
 */
public class OSGAAMHandler extends ACAPHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSGAAMHandler(ToHitData toHitData, WeaponAttackAction weaponAttackAction, Game game,
          TWGameManager twGameManager) throws EntityLoadingException {
        super(toHitData, weaponAttackAction, game, twGameManager);
    }

    @Override
    protected int calcDamagePerHit() {
        int[] ranges = weaponType.getRanges(weapon);
        // Guided round cannot arm inside minimum range -> no damage.
        if (nRange <= ranges[RangeType.RANGE_MINIMUM]) {
            return 0;
        }
        int damage = super.calcDamagePerHit();
        // Medium-range guided sweet spot: +25% damage.
        if ((nRange > ranges[RangeType.RANGE_SHORT]) && (nRange <= ranges[RangeType.RANGE_MEDIUM])) {
            damage = (int) Math.round(damage * 1.25);
        }
        return damage;
    }
}
