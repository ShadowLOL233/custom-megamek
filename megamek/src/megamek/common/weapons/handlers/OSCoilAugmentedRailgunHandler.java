/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.handlers;

import java.io.Serial;
import java.util.Vector;

import megamek.common.HitData;
import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.units.Entity;
import megamek.common.units.IBuilding;
import megamek.common.weapons.gaussRifles.outerSphere.OSCoilAugmentedRailgun;
import megamek.common.weapons.handlers.ac.ACAPHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Attack handler for the OS Coil Augmented Railgun (see OS_TECHBASE_DEVELOPMENT_PLAN §8).
 *
 * <p>Extends {@link ACAPHandler} to inherit the inherent through-armor-crit armor-piercing effect
 * ({@code generalDamageType = DAMAGE_ARMOR_PIERCING}). On top of that it implements:
 * <ul>
 *   <li><b>Barrel-shed:</b> once running without the rail barrel, AP is dropped and damage scales 30 → 25.</li>
 *   <li><b>Barrel wear:</b> after {@link OSCoilAugmentedRailgun#STABLE_SHOTS} shots this battle, a natural-2
 *       attack roll jams the weapon; each jam adds +1 degradation and at {@link OSCoilAugmentedRailgun#SCRAP_AT}
 *       the weapon is scrapped (destroyed for the rest of the battle). The flat +2 to-hit and the +degradation
 *       are added in {@code ComputeToHit}.</li>
 * </ul>
 */
public class OSCoilAugmentedRailgunHandler extends ACAPHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSCoilAugmentedRailgunHandler(ToHitData toHit, WeaponAttackAction waa, Game game, TWGameManager manager)
          throws EntityLoadingException {
        super(toHit, waa, game, manager);
    }

    @Override
    protected int calcDamagePerHit() {
        int dmg = super.calcDamagePerHit();
        if (OSCoilAugmentedRailgun.isBarrelShed(weapon)) {
            // Coil-only launch after shedding the rail barrel: 30 → 25.
            dmg = dmg * 25 / 30;
        }
        return dmg;
    }

    @Override
    protected boolean doChecks(Vector<Report> vPhaseReport) {
        // Commit the one-way barrel jettison the first time the weapon fires in Shed Barrel mode.
        if (!weapon.isRailgunBarrelShed() && OSCoilAugmentedRailgun.isBarrelShed(weapon)) {
            weapon.setRailgunBarrelShed(true);
        }
        boolean shed = weapon.isRailgunBarrelShed();

        // AP is velocity-driven: present with the rail barrel, gone once shed.
        generalDamageType = shed ? HitData.DAMAGE_NONE : HitData.DAMAGE_ARMOR_PIERCING;

        if (super.doChecks(vPhaseReport)) {
            weapon.incrementRailgunShotsFired();
            return true;
        }

        boolean worn = !shed && (weapon.getRailgunShotsFired() >= OSCoilAugmentedRailgun.STABLE_SHOTS);
        if (worn) {
            // Firing on a worn barrel: surface the accumulated to-hit penalty (the modifier itself is added
            // in ComputeToHit) in the firing-phase report.
            Report wear = new Report(1273);
            wear.subject = subjectId;
            wear.indent();
            wear.add(2 + weapon.getRailgunDegradation());
            vPhaseReport.addElement(wear);
        }

        boolean jammed = false;
        if (worn && (roll.getIntValue() == 2)) {
            // Barrel worn past its stable life: this shot jams and degrades the rails.
            weapon.setJammed(true);
            weapon.incrementRailgunDegradation();

            Report jam = new Report(1270);
            jam.subject = subjectId;
            jam.indent();
            vPhaseReport.addElement(jam);

            Report degr = new Report(1271);
            degr.subject = subjectId;
            degr.indent(2);
            degr.add(weapon.getRailgunDegradation());
            degr.add(OSCoilAugmentedRailgun.SCRAP_AT);
            vPhaseReport.addElement(degr);

            if (weapon.getRailgunDegradation() >= OSCoilAugmentedRailgun.SCRAP_AT) {
                // Barrel scrapped — permanently inoperable for the rest of the battle.
                weapon.setDestroyed(true);
                Report scrap = new Report(1272);
                scrap.subject = subjectId;
                scrap.indent(2);
                vPhaseReport.addElement(scrap);
            }
            jammed = true;
        }

        weapon.incrementRailgunShotsFired();
        return jammed;
    }

    @Override
    protected void handleEntityDamage(Entity entityTarget, Vector<Report> vPhaseReport, IBuilding bldg, int hits,
          int nCluster, int bldgAbsorbs) {
        // Long-barrel AP slug: flavor the through-armor penetration (the actual crit is rolled inside
        // gameManager.damageEntity via the inherited ACAPHandler AP handling). No flavor once the barrel is shed.
        if (!OSCoilAugmentedRailgun.isBarrelShed(weapon)) {
            Report ap = new Report(1274);
            ap.subject = subjectId;
            ap.indent(2);
            vPhaseReport.addElement(ap);
        }
        super.handleEntityDamage(entityTarget, vPhaseReport, bldg, hits, nCluster, bldgAbsorbs);
    }
}
