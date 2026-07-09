/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.handlers;

import java.io.Serial;
import java.util.Vector;

import megamek.common.Hex;
import megamek.common.HitData;
import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.compute.ComputeSideTable;
import megamek.common.equipment.AmmoType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.options.OptionsConstants;
import megamek.common.units.Entity;
import megamek.common.units.IBuilding;
import megamek.common.weapons.DamageType;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Ultra AC APDS (Armor-Piercing Discarding Sabot): keeps the Ultra double-tap from
 * {@link UltraWeaponHandler} while shedding ~20% damage in exchange for the armor-piercing critical
 * behavior (mirrors {@code ACAPHandler}). The AP crit strength scales with caliber via
 * {@code HitData.makeArmorPiercing}.
 */
public class OSUltraAPDSHandler extends UltraWeaponHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSUltraAPDSHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
        generalDamageType = HitData.DAMAGE_ARMOR_PIERCING;
    }

    @Override
    protected int calcDamagePerHit() {
        int base = super.calcDamagePerHit();
        // Discarding sabot: ~-20% damage (floored), traded for the armor-piercing crit chance.
        return (int) Math.floor(base * 0.8);
    }

    @Override
    protected void handleEntityDamage(Entity entityTarget, Vector<Report> vPhaseReport, IBuilding bldg, int hits,
          int nCluster, int bldgAbsorbs) {
        AmmoType ammoType = (AmmoType) weapon.getLinked().getType();
        HitData hit = entityTarget.rollHitLocation(toHit.getHitTable(), toHit.getSideTable(),
              weaponAttackAction.getAimedLocation(), weaponAttackAction.getAimingMode(), toHit.getCover());
        hit.setGeneralDamageType(generalDamageType);
        hit.setAttackerId(getAttackerId());
        if (entityTarget.removePartialCoverHits(hit.getLocation(), toHit.getCover(),
              ComputeSideTable.sideTable(attackingEntity, entityTarget, weapon.getCalledShot().getCall()))) {
            // Weapon strikes Partial Cover.
            handlePartialCoverHit(entityTarget, vPhaseReport, hit, bldg, hits, nCluster, bldgAbsorbs);
            return;
        }

        Report r = new Report(3405);
        r.subject = subjectId;
        r.add(toHit.getTableDesc());
        r.add(entityTarget.getLocationAbbr(hit));
        vPhaseReport.addElement(r);
        if (hit.hitAimedLocation()) {
            r = new Report(3410);
            r.subject = subjectId;
            vPhaseReport.lastElement().newlines = 0;
            vPhaseReport.addElement(r);
        }
        int nDamage = nDamPerHit * Math.min(nCluster, hits);
        if (bDirect && !target.isConventionalInfantry()) {
            hit.makeDirectBlow(toHit.getMoS() / 3);
        }

        if (!calcDmgPerHitReport.isEmpty()) {
            vPhaseReport.addAll(calcDmgPerHitReport);
            calcDmgPerHitReport.clear();
        }

        Hex targetHex = game.getBoard().getHex(target.getPosition());
        boolean targetStickingOutOfBuilding = unitStickingOutOfBuilding(targetHex, entityTarget);

        nDamage = absorbBuildingDamage(nDamage, entityTarget, bldgAbsorbs, vPhaseReport, bldg,
              targetStickingOutOfBuilding);

        nDamage = checkTerrain(nDamage, entityTarget, vPhaseReport);

        if ((null != bldg) && !targetStickingOutOfBuilding) {
            nDamage = (int) Math.floor(bldg.getDamageToScale() * nDamage);
        }

        if (nDamage == 0) {
            r = new Report(3415);
            r.subject = subjectId;
            r.indent(2);
            r.addDesc(entityTarget);
            r.newlines = 0;
            vPhaseReport.addElement(r);
        } else {
            int critModifier = 0;
            if (bGlancing) {
                hit.makeGlancingBlow();
                critModifier -= 2;
            }
            if (bLowProfileGlancing) {
                hit.makeGlancingBlow();
                critModifier -= 2;
            }
            if (bDirect) {
                critModifier += toHit.getMoS() / 3;
            }
            if (!game.getOptions().booleanOption(OptionsConstants.PLAYTEST_3)) {
                hit.makeArmorPiercing(ammoType, critModifier);
            } else {
                hit.makeArmorPiercingPlaytest(ammoType, critModifier);
            }
            vPhaseReport.addAll(gameManager.damageEntity(entityTarget, hit, nDamage, false,
                  attackingEntity.getSwarmTargetId() == entityTarget.getId() ? DamageType.IGNORE_PASSENGER : damageType,
                  false, false, throughFront, underWater));
        }
    }
}
