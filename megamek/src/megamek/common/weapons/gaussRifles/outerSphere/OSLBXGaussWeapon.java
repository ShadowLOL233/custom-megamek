/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.gaussRifles.outerSphere;

import static megamek.common.game.IGame.LOGGER;

import java.io.Serial;

import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.annotations.Nullable;
import megamek.common.equipment.AmmoType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.units.Entity;
import megamek.common.weapons.gaussRifles.GaussWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.GRHandler;
import megamek.common.weapons.handlers.LBXHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Base class for Outer Sphere LB-X Gauss Rifles: switchable slug / cluster (M_CLUSTER) by
 * loaded ammo, same routing pattern as canon LB-X autocannon. With slug ammo the weapon
 * does {@code damage} to one location via {@code GRHandler}; with cluster ammo it bursts
 * into {@code rackSize} pellets resolved by {@code LBXHandler}.
 */
public abstract class OSLBXGaussWeapon extends GaussWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSLBXGaussWeapon() {
        super();
        ammoType = AmmoType.AmmoTypeEnum.GAUSS_LBX_OS;
        atClass = CLASS_LBX_AC;
    }

    @Override
    @Nullable
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            Entity entity = game.getEntity(waa.getEntityId());
            if (entity != null) {
                Object item = entity.getEquipment(waa.getWeaponId()).getLinked().getType();
                if (item instanceof AmmoType ammoType
                      && ammoType.getMunitionType().contains(AmmoType.Munitions.M_CLUSTER)) {
                    return new LBXHandler(toHit, waa, game, manager);
                }
            }
            return new GRHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
