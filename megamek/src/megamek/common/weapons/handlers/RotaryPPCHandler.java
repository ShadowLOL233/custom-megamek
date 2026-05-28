/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPL),
 * version 3 or (at your option) any later version,
 * as published by the Free Software Foundation.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A copy of the GPL should have been included with this project;
 * if not, see <https://www.gnu.org/licenses/>.
 */

package megamek.common.weapons.handlers;

import java.io.Serial;
import java.util.Vector;

import megamek.common.HitData;
import megamek.common.RangeType;
import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.compute.Compute;
import megamek.common.equipment.AmmoMounted;
import megamek.common.equipment.AmmoType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.options.OptionsConstants;
import megamek.common.planetaryConditions.PlanetaryConditions;
import megamek.common.rolls.TargetRoll;
import megamek.common.weapons.Weapon;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Handler for the Outer Sphere Rotary PPC family (RLPPC + RSPPC).
 *
 * Each variant fires up to N sub-bolts per turn (N = its mode cap, 5 for RLPPC and 4
 * for RSPPC). The number of sub-bolts that hit is rolled on the missile cluster hit
 * table. The number of shots is set by the weapon's current mode (1-shot through
 * N-shot).
 *
 * Coolant + Heat resolution:
 *   - 1-shot: no coolant consumed, heat = baseHeat
 *   - up to FREE_SHOTS (3) shots: self-cooled and pod-free; each shot beyond the first
 *     costs the suppressed ⌈baseHeat / 3⌉ heat
 *   - beyond FREE_SHOTS: each further shot draws one charge from the entity-wide RPPC
 *     Coolant Pod pool and stays suppressed; an uncovered shot runs at full baseHeat
 *   - If insufficient coolant for a shot beyond FREE_SHOTS: Capacitor Overload triggers
 *     (weapon destroyed, entity heat +15, location takes 15 internal damage)
 *
 * Per-sub-bolt damage uses {@code weaponType.getDamage(nRange)} so that variants with
 * range-graded damage (Snub-Nose) compute each hit against the current range bracket.
 */
public class RotaryPPCHandler extends PPCHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    // NOTE: No explicit initializers here. The parent WeaponHandler constructor calls
    // useAmmo() (which sets these fields) via polymorphic dispatch *before* this subclass's
    // field initializers would run. Explicit initializers like `= 1` would overwrite the
    // values useAmmo() just computed — silently forcing the weapon back into 1-shot mode.
    // Default int = 0 / boolean = false suffice since useAmmo() unconditionally assigns.
    private int howManyShots;
    private int coolantUsed;
    private boolean overloaded;

    /**
     * Built-in cluster-roll bonus applied to every multi-shot rotary burst. The synchronized
     * rotary drum and advanced fire control shift the missile cluster table upward, raising the
     * average number of sub-bolts that connect so a dialed-up burst is less of a dice gamble.
     */
    private static final int ROTARY_CLUSTER_BONUS = 1;

    /**
     * Shots a Rotary PPC fires on its own internal cooling without drawing RPPC Coolant Pod
     * charges. Only shots beyond this count consume one pod charge each, so dialing at or below it
     * is always pod-free and can never overload - two RPPCs sharing the entity-wide pool therefore
     * no longer starve each other at low/mid dial.
     */
    private static final int FREE_SHOTS = 3;

    public RotaryPPCHandler(ToHitData toHit, WeaponAttackAction waa, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(toHit, waa, g, m);
        sSalvoType = " sub-bolt(s) ";
    }

    @Override
    protected void useAmmo() {
        setDone();
        howManyShots = shotsForMode(weapon.curMode().toString());

        // Only shots beyond FREE_SHOTS draw from the entity-wide RPPC Coolant Pod pool (1 charge
        // each). The first FREE_SHOTS shots are self-cooled and pod-free, so low/mid dialing never
        // overloads and multiple RPPCs no longer starve each other.
        int coolantNeeded = Math.max(0, howManyShots - FREE_SHOTS);
        if (coolantNeeded > 0) {
            int available = getAvailableCoolant();
            int draw = Math.min(coolantNeeded, available);
            if (draw > 0) {
                consumeCoolant(draw);
            }
            coolantUsed = draw;
            overloaded = (draw < coolantNeeded);
        }
    }

    private static int shotsForMode(String mode) {
        return switch (mode) {
            case Weapon.MODE_RAC_SIX_SHOT -> 6;
            case Weapon.MODE_RAC_FIVE_SHOT -> 5;
            case Weapon.MODE_RAC_FOUR_SHOT -> 4;
            case Weapon.MODE_RAC_THREE_SHOT -> 3;
            case Weapon.MODE_RAC_TWO_SHOT -> 2;
            default -> 1;
        };
    }

    private int getAvailableCoolant() {
        int total = 0;
        for (AmmoMounted m : attackingEntity.getAmmo()) {
            if (m.isDestroyed() || m.isMissing()) {
                continue;
            }
            if (m.getType().getAmmoType() == AmmoType.AmmoTypeEnum.PPC_COOLANT) {
                total += m.getUsableShotsLeft();
            }
        }
        return total;
    }

    private void consumeCoolant(int count) {
        for (AmmoMounted m : attackingEntity.getAmmo()) {
            if (count <= 0) {
                break;
            }
            if (m.isDestroyed() || m.isMissing()) {
                continue;
            }
            if (m.getType().getAmmoType() != AmmoType.AmmoTypeEnum.PPC_COOLANT) {
                continue;
            }
            int available = m.getUsableShotsLeft();
            int taken = Math.min(count, available);
            m.setShotsLeft(available - taken);
            count -= taken;
        }
    }

    @Override
    protected void addHeat() {
        if (isStrafing && !isStrafingFirstShot()) {
            return;
        }
        if (toHit.getValue() == TargetRoll.IMPOSSIBLE) {
            return;
        }
        int baseHeat = weaponType.getHeat();
        int totalHeat;
        if (howManyShots <= 1) {
            totalHeat = baseHeat;
        } else {
            int extra = howManyShots - 1;
            // Shots 2..FREE_SHOTS are self-cooled (no pod needed); shots beyond FREE_SHOTS are
            // suppressed only when a coolant charge covers them, otherwise they run at full heat.
            int freeExtras = Math.min(extra, FREE_SHOTS - 1);
            int suppressed = freeExtras + coolantUsed;
            int unsuppressed = extra - suppressed;
            int extraHeatSuppressed = (int) Math.ceil(baseHeat / 3.0) * suppressed;
            int extraHeatNormal = baseHeat * unsuppressed;
            totalHeat = baseHeat + extraHeatSuppressed + extraHeatNormal;
        }
        if (overloaded) {
            totalHeat += 15;
        }
        attackingEntity.heatBuildup += totalHeat;
    }

    @Override
    protected boolean doChecks(Vector<Report> vPhaseReport) {
        if (super.doChecks(vPhaseReport)) {
            return true;
        }

        // Coolant consumption report: shown when 2+ shot mode succeeded with full coolant
        // supply, so the player can see exactly how many charges were drawn and the per-shot
        // suppressed heat that resulted.
        if (howManyShots >= 2 && coolantUsed > 0 && !overloaded) {
            int suppressedPerShotHeat = (int) Math.ceil(weaponType.getHeat() / 3.0);
            Report r = new Report(1266);
            r.subject = subjectId;
            r.indent();
            r.add(howManyShots);
            r.add(coolantUsed);
            r.add(suppressedPerShotHeat);
            vPhaseReport.addElement(r);
        }

        if (overloaded) {
            // Partial-coolant report: show how many charges were drawn before depletion.
            if (coolantUsed > 0) {
                int needed = howManyShots - 1;
                Report rPartial = new Report(1267);
                rPartial.subject = subjectId;
                rPartial.indent();
                rPartial.add(coolantUsed);
                rPartial.add(needed);
                vPhaseReport.addElement(rPartial);
            }
            // Capacitor Overload: weapon destroyed, location takes internal damage.
            Report r = new Report(1265);
            r.subject = subjectId;
            r.indent();
            r.add(weapon.getName());
            r.newlines = 1;
            vPhaseReport.addElement(r);

            weapon.setHit(true);
            weapon.setDestroyed(true);

            HitData hit = new HitData(weapon.getLocation());
            vPhaseReport.addAll(gameManager.damageEntity(attackingEntity, hit, 15));
        }
        return false;
    }

    @Override
    protected int calcHits(Vector<Report> vPhaseReport) {
        if (target.isConventionalInfantry()) {
            return 1;
        }
        if (howManyShots == 1) {
            return 1;
        }
        bSalvo = true;
        // Built-in cluster bonus: the synchronized rotary drum + advanced fire control shift the
        // cluster table upward so more sub-bolts land on average, taming the "5-shot, 1 hit" swing.
        int nMod = getClusterModifiers(true) + ROTARY_CLUSTER_BONUS;
        int shotsHit;
        if (allShotsHit()) {
            shotsHit = howManyShots;
            if (game.getOptions().booleanOption(OptionsConstants.ADVANCED_COMBAT_TAC_OPS_RANGE)
                  && (nRange > weaponType.getRanges(weapon)[RangeType.RANGE_LONG])) {
                shotsHit = (int) Math.ceil(shotsHit * .75);
            }
            if (game.getOptions().booleanOption(OptionsConstants.ADVANCED_COMBAT_TAC_OPS_LOS_RANGE)
                  && (nRange > weaponType.getRanges(weapon)[RangeType.RANGE_EXTREME])) {
                shotsHit = (int) Math.ceil(shotsHit * .5);
            }
        } else {
            PlanetaryConditions conditions = game.getPlanetaryConditions();
            shotsHit = Compute.missilesHit(howManyShots, nMod, conditions.getEMI().isEMI());
            // Reliability floor: on a burst that hits, at least half the dialed sub-bolts always
            // connect, cutting off the worst-case cluster rolls while keeping rotary randomness.
            int minHits = (int) Math.ceil(howManyShots / 2.0);
            shotsHit = Math.max(shotsHit, minHits);
        }

        Report r = new Report(3325);
        r.subject = subjectId;
        r.add(shotsHit);
        r.add(sSalvoType);
        r.add(toHit.getTableDesc());
        r.newlines = 0;
        vPhaseReport.addElement(r);
        if (nMod != 0) {
            r = (nMod > 0) ? new Report(3340) : new Report(3341);
            r.subject = subjectId;
            r.add(nMod);
            r.newlines = 0;
            vPhaseReport.addElement(r);
        }
        r = new Report(3345);
        r.subject = subjectId;
        vPhaseReport.addElement(r);
        return shotsHit;
    }

    @Override
    protected int calcDamagePerHit() {
        // Use range-aware damage so RSPPC (Snub-Nose Rotary) gets its 8/6/4 falloff per
        // sub-bolt. For constant-damage variants (RLPPC) WeaponType.getDamage(range)
        // simply returns the base damage field.
        return weaponType.getDamage(nRange);
    }

    @Override
    protected boolean usesClusterTable() {
        return true;
    }

    @Override
    protected int calculateNumCluster() {
        return 1;
    }
}
