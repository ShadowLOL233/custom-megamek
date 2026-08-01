/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.missiles.outerSphere;

import static megamek.common.game.IGame.LOGGER;

import java.io.Serial;

import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.annotations.Nullable;
import megamek.common.equipment.AmmoType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.units.Entity;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.MRMHandler;
import megamek.common.weapons.handlers.MissileMineClearanceHandler;
import megamek.common.weapons.handlers.lrm.LRMAntiTSMHandler;
import megamek.common.weapons.handlers.lrm.LRMDeadFireHandler;
import megamek.common.weapons.handlers.lrm.LRMFragHandler;
import megamek.common.weapons.handlers.lrm.LRMSmokeWarheadHandler;
import megamek.common.weapons.missiles.MRMWeapon;
import megamek.server.totalWarfare.TWGameManager;

/**
 * OS MRM base — the Outer Sphere medium-range specialist launcher.
 *
 * <p>Two things distinguish it from the canon {@link MRMWeapon}:
 * <ul>
 *   <li><b>Accuracy:</b> it drops the canon MRM +1 inaccuracy (toHitModifier 0). OS MRMs are instead accurate
 *       in their huge medium bracket via a -1 to-hit wired in {@code ComputeToHit} on
 *       {@code F_OS_MRM_MEDIUM_SPEC} (a linked Diana III FCS adds a further -1 there).</li>
 *   <li><b>Special munitions:</b> unlike the plain {@link MRMHandler}, it dispatches OS MRM special munitions to
 *       the matching cluster-missile effect handlers (reusing the LRM handlers, since MRM racks are LRM-scale
 *       cluster salvos). Munitions with no dedicated handler (Narc-capable / Heat-Seeking / Listen-Kill) fall
 *       through to {@link MRMHandler}, where their cluster/to-hit bonuses are applied as usual.</li>
 * </ul>
 */
public abstract class OSMRMWeapon extends MRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSMRMWeapon() {
        super();
        toHitModifier = 0;
    }

    @Override
    @Nullable
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            Entity entity = game.getEntity(waa.getEntityId());
            if (entity != null) {
                AmmoType atype = (AmmoType) entity.getEquipment(waa.getWeaponId()).getLinked().getType();
                if (atype.getMunitionType().contains(AmmoType.Munitions.M_FRAGMENTATION)) {
                    return new LRMFragHandler(toHit, waa, game, manager);
                }
                if (atype.getMunitionType().contains(AmmoType.Munitions.M_ANTI_TSM)) {
                    return new LRMAntiTSMHandler(toHit, waa, game, manager);
                }
                if (atype.getMunitionType().contains(AmmoType.Munitions.M_DEAD_FIRE)) {
                    return new LRMDeadFireHandler(toHit, waa, game, manager);
                }
                if (atype.getMunitionType().contains(AmmoType.Munitions.M_SMOKE_WARHEAD)) {
                    return new LRMSmokeWarheadHandler(toHit, waa, game, manager);
                }
                if (atype.getMunitionType().contains(AmmoType.Munitions.M_MINE_CLEARANCE)) {
                    return new MissileMineClearanceHandler(toHit, waa, game, manager);
                }
            }
            return new MRMHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("OSMRMWeapon.getCorrectHandler - received null entity.");
        }
        return null;
    }
}
