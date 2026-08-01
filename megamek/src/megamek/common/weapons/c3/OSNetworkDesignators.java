/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.c3;

import java.util.Iterator;

import megamek.common.LosEffects;
import megamek.common.equipment.INarcPod;
import megamek.common.equipment.WeaponMounted;
import megamek.common.equipment.WeaponType;
import megamek.common.equipment.WeaponTypeFlag;
import megamek.common.game.Game;
import megamek.common.units.Entity;

/**
 * Shared C3-network read logic for the OS network target designators (dev plan §9.6): Kestrel NTD (passive
 * network -1 direct-fire vs a painted target) and Shrike Combat Designator (network -1/-3 vs a Shrike-designated
 * target). Called from {@code ComputeToHit}.
 */
public final class OSNetworkDesignators {

    private OSNetworkDesignators() {}

    /**
     * @return true if some unit on the attacker's active C3 network (including the attacker itself) mounts an
     *       operational Kestrel NTD that paints the target — i.e. the target is within that Kestrel's range and
     *       either the Kestrel carrier has LOS to it, or a friendly Lodestar beacon is attached to it.
     */
    public static boolean hasNetworkKestrelPaint(Game game, Entity attacker, Entity target) {
        if ((attacker == null) || (target == null) || (target.getPosition() == null)) {
            return false;
        }
        for (Entity e : game.getEntitiesVector()) {
            if (e.isDestroyed() || (e.getPosition() == null) || !networkedWith(game, attacker, e)) {
                continue;
            }
            int kestrelRange = kestrelRange(e);
            if (kestrelRange <= 0) {
                continue;
            }
            if (e.getPosition().distance(target.getPosition()) > kestrelRange) {
                continue;
            }
            // Lodestar-beacon path: a friendly beacon on the target, in range → no LOS needed.
            if (hasFriendlyLodestar(target, e.getOwner().getTeam())) {
                return true;
            }
            // LOS path.
            if (LosEffects.calculateLOS(game, e, target).canSee()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the Shrike to-hit bonus for this attacker vs this target: -3 if the target was designated this turn
     *       by a Shrike on the attacker's C3 network with a critical (natural 9+) designation roll, -1 for a
     *       normal Shrike designation, or 0 if none applies.
     */
    public static int shrikeToHitBonus(Game game, Entity attacker, Entity target) {
        if ((attacker == null) || (target == null)) {
            return 0;
        }
        int designatorId = target.getShrikeDesignatedBy();
        if (designatorId < 0) {
            return 0;
        }
        Entity designator = game.getEntity(designatorId);
        if ((designator == null) || designator.isDestroyed() || !networkedWith(game, attacker, designator)) {
            return 0;
        }
        return target.isShrikeDesignationCrit() ? -3 : -1;
    }

    /** @return the long range of an operational Kestrel NTD mounted on {@code e}, or 0 if it has none. */
    private static int kestrelRange(Entity e) {
        for (WeaponMounted m : e.getWeaponList()) {
            if (m.isDestroyed() || m.isMissing() || m.isBreached()) {
                continue;
            }
            WeaponType wt = m.getType();
            if (wt.hasFlag(WeaponTypeFlag.F_OS_KESTREL_DESIGNATOR)) {
                return wt.getLongRange();
            }
        }
        return 0;
    }

    private static boolean hasFriendlyLodestar(Entity target, int team) {
        for (Iterator<INarcPod> it = target.getINarcPodsAttached(); it.hasNext(); ) {
            INarcPod pod = it.next();
            if ((pod.type() == INarcPod.LODESTAR) && (pod.team() == team)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if {@code other} is on the attacker's active C3 network. The attacker itself counts only when
     *       it is actually networked with at least one other unit (so a lone designator is dead weight).
     */
    private static boolean networkedWith(Game game, Entity attacker, Entity other) {
        if ((attacker == null) || (other == null)) {
            return false;
        }
        if (attacker.equals(other)) {
            for (Entity f : game.getEntitiesVector()) {
                if (!f.equals(attacker) && attacker.onSameC3NetworkAs(f)) {
                    return true;
                }
            }
            return false;
        }
        return attacker.onSameC3NetworkAs(other);
    }
}
