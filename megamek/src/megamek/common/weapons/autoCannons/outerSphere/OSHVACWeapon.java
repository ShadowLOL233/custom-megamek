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
import megamek.common.equipment.AmmoType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.autoCannons.HVACWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.ac.ACWeaponHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Base class for the Outer Sphere HVAC line — a long-barrel precision anti-armor autocannon.
 *
 * <p>Unlike the Inner Sphere HVAC (a volatile chemical breech that cooks off on a critical hit), the OS
 * pattern is a stable chemical charge accelerated by an electromagnetic long barrel. It has no
 * catastrophic-detonation failure mode ({@code explosive = false}, {@code explosionDamage = 0}) and uses
 * the plain autocannon attack handler instead of {@link HVACWeapon}'s cook-off handler.
 *
 * <p>The OS line is repurposed as a precision piece: the long, high-velocity barrel walks concentrated
 * single slugs onto distant targets, giving a to-hit bonus that grows with range (medium/long −1, extreme −2,
 * applied in {@code ComputeToHit}). It is distinct from LB-X (single-slug, not cluster) and pays a
 * mech-level tax in heavy tonnage, high crit count, low ammo/ton and a high BV. Aerospace-capable
 * (AV = damage, maxRange = extreme). Ammo lives in the dedicated {@link AmmoType.AmmoTypeEnum#HVAC_OS} bin.
 */
public abstract class OSHVACWeapon extends HVACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    protected OSHVACWeapon() {
        super();
        // Electromagnetic-chemical design: no sympathetic detonation / cook-off.
        explosive = false;
        explosionDamage = 0;
        // Dedicated OS precision-HVAC ammo bin (isolates caliber 10 from the canon HYPER_VELOCITY HVAC/10).
        ammoType = AmmoType.AmmoTypeEnum.HVAC_OS;
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
