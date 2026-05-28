/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.handlers.plasma;

import java.io.Serial;
import java.util.Vector;

import megamek.MMConstants;
import megamek.common.EMPEffectFormatter;
import megamek.common.HitData;
import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.battleArmor.BattleArmor;
import megamek.common.compute.Compute;
import megamek.common.equipment.Engine;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.rolls.Roll;
import megamek.common.units.Aero;
import megamek.common.units.ConvFighter;
import megamek.common.units.Entity;
import megamek.common.units.IBuilding;
import megamek.common.units.Mek;
import megamek.common.units.ProtoMek;
import megamek.common.units.SupportTank;
import megamek.common.units.Tank;
import megamek.common.weapons.handlers.AmmoWeaponHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Handler for the Outer Sphere EMP Plasma Accelerator (School D).
 * <p>
 * An ionized plasma stream carries an EM pulse that disrupts the target's
 * electronics. Unlike the canonical TSEMP it does no armor damage and can only
 * ever cause INTERFERENCE (never a full shutdown) - this is the cost of folding
 * a plasma weapon into a disruptor. In exchange, the OS plasma containment keeps
 * the pulse from backwashing, so the firing unit suffers NO self-interference.
 */
public class OSEMPPlasmaHandler extends AmmoWeaponHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSEMPPlasmaHandler(ToHitData toHit, WeaponAttackAction waa, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(toHit, waa, g, m);
        generalDamageType = HitData.DAMAGE_ENERGY;
    }

    @Override
    protected int calcDamagePerHit() {
        return 0;
    }

    @Override
    protected int calculateNumCluster() {
        bSalvo = false;
        return 1;
    }

    @Override
    protected int calcHits(Vector<Report> vPhaseReport) {
        return 1;
    }

    @Override
    protected void handleEntityDamage(Entity entityTarget, Vector<Report> vPhaseReport,
          IBuilding bldg, int hits, int nCluster, int bldgAbsorbs) {
        super.handleEntityDamage(entityTarget, vPhaseReport, bldg, hits, nCluster, bldgAbsorbs);

        // Count the hit so the standard end-of-turn cleanup keeps the effect active
        // through the target's firing phase (mirrors TSEMP timing).
        entityTarget.addTsempHitThisTurn();

        Report r = new Report(7410);
        r.subject = entityTarget.getId();
        r.addDesc(entityTarget);
        r.add(entityTarget.getTsempHitsThisTurn());
        r.indent(2);
        vPhaseReport.add(r);

        // No effect against conventional infantry
        if (entityTarget.isConventionalInfantry()) {
            r = new Report(7415);
            r.subject = entityTarget.getId();
            r.indent(3);
            vPhaseReport.add(r);
            return;
        }

        int mods = 0;
        if (entityTarget.getWeight() >= 200) {
            // Too massive to be disrupted
            r = new Report(7416);
            r.subject = entityTarget.getId();
            r.indent(3);
            vPhaseReport.add(r);
            return;
        } else if (entityTarget.getWeight() >= 100) {
            mods -= 2;
        }

        if (entityTarget.getEngine() != null
              && entityTarget.getEngine().getEngineType() == Engine.COMBUSTION_ENGINE) {
            mods -= 1;
        } else if (entityTarget.getEngine() != null
              && entityTarget.getEngine().getEngineType() == Engine.STEAM) {
            mods -= 2;
        }

        // Multiple hits add +1 each after the first, up to +4
        mods += Math.min(4, entityTarget.getTsempHitsThisTurn() - 1);

        Roll diceRoll = Compute.rollD6(2);
        int rollValue = Math.max(2, diceRoll.getIntValue() + mods);
        String rollCalc = rollValue + " [" + diceRoll.getIntValue() + " + " + mods + "] max 2";

        // Interference-only: no shutdown branch at all.
        int interferenceTarget = 13;
        if (entityTarget instanceof Mek) {
            interferenceTarget = ((Mek) entityTarget).isIndustrial() ? 6 : 7;
        } else if (entityTarget instanceof SupportTank) {
            interferenceTarget = 5;
        } else if (entityTarget instanceof Tank) {
            interferenceTarget = 6;
        } else if (entityTarget instanceof BattleArmor) {
            interferenceTarget = 6;
        } else if (entityTarget instanceof ProtoMek) {
            interferenceTarget = 6;
        } else if (entityTarget instanceof ConvFighter) {
            interferenceTarget = 6;
        } else if (entityTarget instanceof Aero) {
            interferenceTarget = 7;
        }

        if (mods == 0) {
            r = new Report(7411);
        } else {
            r = new Report(7412);
            r.add((mods >= 0 ? "+" : "") + mods);
        }
        r.indent(3);
        r.addDataWithTooltip(rollCalc, diceRoll.getReport());
        r.subject = entityTarget.getId();

        String tsempEffect;
        if (rollValue >= interferenceTarget) {
            // Never override an existing shutdown from another source.
            if (entityTarget.getTsempEffect() != MMConstants.TSEMP_EFFECT_SHUTDOWN) {
                entityTarget.setTsempEffect(MMConstants.TSEMP_EFFECT_INTERFERENCE);
            }
            tsempEffect = EMPEffectFormatter.formatEffect(MMConstants.TSEMP_EFFECT_INTERFERENCE);
        } else {
            tsempEffect = EMPEffectFormatter.formatEffect(MMConstants.TSEMP_EFFECT_NONE);
        }
        r.add(tsempEffect);
        vPhaseReport.add(r);
    }
}
