/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.handlers.plasma;

import java.io.Serial;
import java.util.Vector;

import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.compute.Compute;
import megamek.common.compute.ComputeSideTable;
import megamek.common.equipment.ArmorType;
import megamek.common.equipment.EquipmentType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.options.OptionsConstants;
import megamek.common.units.Entity;
import megamek.common.units.IBuilding;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Handler for the Outer Sphere Heavy Plasma Cannon (School B toroidal burst).
 * Like the standard Plasma Cannon it deals no direct armor damage to heat-tracking
 * targets, but the heavier, denser plasma burst floods 3d6 heat (vs 2d6) into the
 * target. Anti-soft cluster behavior is inherited from the standard cannon handler.
 */
public class OSHeavyPlasmaCannonHandler extends PlasmaCannonHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyPlasmaCannonHandler(ToHitData toHit, WeaponAttackAction waa, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(toHit, waa, g, m);
    }

    @Override
    protected void handleEntityDamage(Entity entityTarget, Vector<Report> vPhaseReport,
          IBuilding bldg, int hits, int nCluster, int bldgAbsorbs) {

        if (entityTarget.tracksHeat()) {
            hit = entityTarget.rollHitLocation(toHit.getHitTable(),
                  toHit.getSideTable(), weaponAttackAction.getAimedLocation(),
                  weaponAttackAction.getAimingMode(), toHit.getCover());
            hit.setGeneralDamageType(generalDamageType);
            hit.setAttackerId(getAttackerId());
            if (entityTarget.removePartialCoverHits(hit.getLocation(), toHit.getCover(),
                  ComputeSideTable.sideTable(attackingEntity, entityTarget, weapon.getCalledShot().getCall()))) {
                // Weapon strikes Partial Cover.
                handlePartialCoverHit(entityTarget, vPhaseReport, hit, bldg, hits, nCluster, bldgAbsorbs);
                return;
            }

            if (!bSalvo) {
                Report r = new Report(3405);
                r.subject = subjectId;
                r.add(toHit.getTableDesc());
                r.add(entityTarget.getLocationAbbr(hit));
                vPhaseReport.addElement(r);
            }
            Report r = new Report(3400);
            r.subject = subjectId;
            r.indent(2);
            int extraHeat = Compute.d6(3);
            if (entityTarget.getArmor(hit) > 0
                  && (entityTarget.getArmorType(hit.getLocation()) == EquipmentType.T_ARMOR_REFLECTIVE)
                  && !game.getOptions().booleanOption(OptionsConstants.PLAYTEST_3)) {
                entityTarget.heatFromExternal += Math.max(1, extraHeat / 2);
                r.add(Math.max(1, extraHeat / 2));
                r.choose(true);
                r.messageId = 3406;
                r.add(extraHeat);
                r.add(ArmorType.forEntity(entityTarget, hit.getLocation()).getName());
            } else if (entityTarget.getArmor(hit) > 0 &&
                  (entityTarget.getArmorType(hit.getLocation()) == EquipmentType.T_ARMOR_HEAT_DISSIPATING)) {
                if (game.getOptions().booleanOption(OptionsConstants.PLAYTEST_3)) {
                    extraHeat = 0;
                }
                entityTarget.heatFromExternal += extraHeat / 2;
                r.add(extraHeat / 2);
                r.choose(true);
                r.messageId = 3406;
                r.add(extraHeat);
                r.add(ArmorType.forEntity(entityTarget, hit.getLocation()).getName());
            } else {
                entityTarget.heatFromExternal += extraHeat;
                r.add(extraHeat);
                r.choose(true);
            }
            vPhaseReport.addElement(r);
        } else {
            super.handleEntityDamage(entityTarget, vPhaseReport, bldg, hits, nCluster, bldgAbsorbs);
        }
    }
}
