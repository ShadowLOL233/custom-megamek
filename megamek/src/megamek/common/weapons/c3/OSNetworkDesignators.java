/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.c3;

import java.util.Iterator;

import megamek.common.LosEffects;
import megamek.common.compute.ComputeECM;
import megamek.common.equipment.INarcPod;
import megamek.common.equipment.enums.MiscTypeFlag;
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

    /** Owl Tactical Network (dev plan §9.7): one Aegolius Compiler can sync at most this many Owl TNU feeds. */
    private static final int TNU_PER_COMPILER = 6;

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

    /**
     * @return true if a BCS / Crow Nest core on the attacker's active C3 network rolled 10+ on its coordination
     *       roll this round and is not currently jammed by hostile ECM (dev plan §9.1).
     */
    public static boolean hasBcsCoordination(Game game, Entity attacker) {
        if ((game == null) || (attacker == null)) {
            return false;
        }
        for (Entity core : game.getEntitiesVector()) {
            boolean isBcsCore = core.hasWorkingMisc(MiscTypeFlag.F_OS_BATTLE_COMPUTER)
                  || core.hasWorkingMisc(MiscTypeFlag.F_OS_CROW_NEST);
            if (isBcsCore && (core.getBcsCoordinationRoll() >= 10)
                  && (core.equals(attacker) || attacker.onSameC3NetworkAs(core))
                  && ((core.getPosition() == null)
                        || !ComputeECM.isAffectedByECM(core, core.getPosition(), core.getPosition()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * The single, non-stacking OS "network coordination" to-hit bonus (dev plan §9.3 / §9.6). The Shrike
     * designation (−1, or −3 on a crit), the Kestrel network paint (−1, direct-fire only) and the B-2500
     * coordination roll (−1, direct-fire only) are all "network coordination" — they do <b>not</b> stack with each
     * other; only the deepest applies. A CCS module −1 (fire control, handled elsewhere) may still stack on top,
     * so a direct-fire weapon can still reach −2 total (§9.3).
     *
     * @return the (≤ 0) to-hit modifier to apply, or 0 if none applies.
     */
    public static int networkCoordinationToHit(Game game, Entity attacker, Entity target, boolean directFire) {
        if ((attacker == null) || (target == null)) {
            return 0;
        }
        int best = Math.min(0, shrikeToHitBonus(game, attacker, target));
        if (directFire) {
            if (hasNetworkKestrelPaint(game, attacker, target)) {
                best = Math.min(best, -1);
            }
            if (hasBcsCoordination(game, attacker)) {
                best = Math.min(best, -1);
            }
        }
        return best;
    }

    /**
     * @return true if the target carries a friendly Lodestar beacon and a unit on the attacker's active C3 network
     *       (incl. the attacker) mounts an operational Kestrel NTD with the target in range — i.e. the network may
     *       fire LRMs indirectly at the beaconed target without a normal spotter (dev plan §9.6).
     */
    public static boolean enablesLodestarIndirectFire(Game game, Entity attacker, Entity target) {
        if ((attacker == null) || (target == null) || (target.getPosition() == null)
              || !hasFriendlyLodestar(target, attacker.getOwner().getTeam())) {
            return false;
        }
        for (Entity e : game.getEntitiesVector()) {
            if (e.isDestroyed() || (e.getPosition() == null) || !networkedWith(game, attacker, e)) {
                continue;
            }
            int kestrelRange = kestrelRange(e);
            if ((kestrelRange > 0) && (e.getPosition().distance(target.getPosition()) <= kestrelRange)) {
                return true;
            }
        }
        return false;
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
        if (attacker.onSameC3NetworkAs(other)) {
            return true;
        }
        // Owl bridge (dev plan §9.7): 'other' carries an Owl TNU whose data is compiled onto a C3 network the
        // attacker shares, via a live Aegolius Compiler mounted on a C3-Node. Lets a designator on a non-C3
        // platform reach the network.
        return isTnuBridged(game, attacker, other);
    }

    /** @return true if {@code e} mounts an operational Owl TNU uplink. */
    private static boolean hasWorkingTnu(Entity e) {
        return (e != null) && e.hasWorkingMisc(MiscTypeFlag.F_OS_TNU);
    }

    /**
     * @return true if {@code core} mounts an operational Aegolius Compiler on a valid C3-Node host — the compiler
     *       only functions co-mounted with a C3 Node (master). ECM suppression of the compiler is a later pass.
     */
    private static boolean hasWorkingAegoliusHost(Entity core) {
        return (core != null) && core.hasWorkingMisc(MiscTypeFlag.F_OS_TNU_COMPILER) && core.hasC3M();
    }

    /**
     * @return true if the Owl-TNU carrier {@code tnuCarrier} is bridged onto the attacker's active C3 network by a
     *       live Aegolius Compiler. Capacity is pooled at {@link #TNU_PER_COMPILER} feeds per compiler on the
     *       attacker's network; same-team TNU carriers fill it lowest-id first (deterministic). mechanics-remaining:
     *       exact per-compiler assignment; ECM suppression of the compiler.
     */
    private static boolean isTnuBridged(Game game, Entity attacker, Entity tnuCarrier) {
        if (!hasWorkingTnu(tnuCarrier) || (tnuCarrier.getOwner() == null)) {
            return false;
        }
        int team = tnuCarrier.getOwner().getTeam();
        int capacity = 0;
        for (Entity core : game.getEntitiesVector()) {
            if (!hasWorkingAegoliusHost(core) || (core.getOwner() == null)
                  || (core.getOwner().getTeam() != team)) {
                continue;
            }
            if (core.equals(attacker) || attacker.onSameC3NetworkAs(core)) {
                capacity += TNU_PER_COMPILER;
            }
        }
        if (capacity <= 0) {
            return false;
        }
        int rank = 0;
        for (Entity e : game.getEntitiesVector()) {
            if (e.equals(tnuCarrier) || !hasWorkingTnu(e) || (e.getOwner() == null)
                  || (e.getOwner().getTeam() != team)) {
                continue;
            }
            if (e.getId() < tnuCarrier.getId()) {
                rank++;
            }
        }
        return rank < capacity;
    }
}
