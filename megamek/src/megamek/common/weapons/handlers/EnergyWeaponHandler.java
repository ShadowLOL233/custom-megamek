/*
 * Copyright (c) 2005 - Ben Mazur (bmazur@sev.org)
 * Copyright (C) 2007-2025 The MegaMek Team. All Rights Reserved.
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
 *
 * NOTICE: The MegaMek organization is a non-profit group of volunteers
 * creating free software for the BattleTech community.
 *
 * MechWarrior, BattleMech, `Mech and AeroTech are registered trademarks
 * of The Topps Company, Inc. All Rights Reserved.
 *
 * Catalyst Game Labs and the Catalyst Game Labs logo are trademarks of
 * InMediaRes Productions, LLC.
 *
 * MechWarrior Copyright Microsoft Corporation. MegaMek was created under
 * Microsoft's "Game Content Usage Rules"
 * <https://www.xbox.com/en-US/developers/rules> and it is not endorsed by or
 * affiliated with Microsoft.
 */

package megamek.common.weapons.handlers;

import java.io.Serial;

import megamek.common.HitData;
import megamek.common.RangeType;
import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.battleArmor.BattleArmor;
import megamek.common.compute.Compute;
import megamek.common.equipment.WeaponType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.options.OptionsConstants;
import megamek.common.units.Entity;
import megamek.common.units.Infantry;
import megamek.server.totalWarfare.TWGameManager;

public class EnergyWeaponHandler extends WeaponHandler {
    @Serial
    private static final long serialVersionUID = 2452514543790235562L;

    /**
     *
     */
    public EnergyWeaponHandler(ToHitData toHit, WeaponAttackAction waa, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(toHit, waa, g, m);
        generalDamageType = HitData.DAMAGE_ENERGY;
    }

    /*
     * (non-Javadoc)
     *
     * @see megamek.common.weapons.handlers.WeaponHandler#calcDamagePerHit()
     */
    @Override
    protected int calcDamagePerHit() {
        double toReturn = weaponType.getDamage(nRange);

        if ((game.getOptions().booleanOption(OptionsConstants.ADVANCED_COMBAT_TAC_OPS_ENERGY_WEAPONS)
              && weapon.hasModes()) || weaponType.hasFlag(WeaponType.F_BOMBAST_LASER)) {
            toReturn = Compute.dialDownDamage(weapon, weaponType, nRange);
        }
        // during a swarm, all damage gets applied as one block to one location
        if ((attackingEntity instanceof BattleArmor)
              && (weapon.getLocation() == BattleArmor.LOC_SQUAD)
              && !(weapon.isSquadSupportWeapon())
              && (attackingEntity.getSwarmTargetId() == target.getId())) {
            toReturn *= ((BattleArmor) attackingEntity).getShootingStrength();
        }
        // Check for Altered Damage from Energy Weapons (TacOp, pg.83)
        if (game.getOptions().booleanOption(OptionsConstants.ADVANCED_COMBAT_TAC_OPS_ALTERNATIVE_DAMAGE)) {
            if (nRange <= 1) {
                toReturn++;
            } else if (nRange > weaponType.getMediumRange() && nRange <= weaponType.getLongRange()) {
                toReturn--;
            }
        }

        if (game.getOptions().booleanOption(OptionsConstants.ADVANCED_COMBAT_TAC_OPS_RANGE)
              && (nRange > weaponType.getRanges(weapon)[RangeType.RANGE_LONG])) {
            toReturn -= 1;
        }
        if (game.getOptions().booleanOption(OptionsConstants.ADVANCED_COMBAT_TAC_OPS_LOS_RANGE)
              && (nRange > weaponType.getRanges(weapon)[RangeType.RANGE_EXTREME])) {
            toReturn = (int) Math.floor(toReturn * .75);
        }


        if (target.isConventionalInfantry()) {
            toReturn = Compute.directBlowInfantryDamage(
                  toReturn, bDirect ? toHit.getMoS() / 3 : 0,
                  weaponType.getInfantryDamageClass(),
                  ((Infantry) target).isMechanized(),
                  toHit.getThruBldg() != null, attackingEntity.getId(), calcDmgPerHitReport);
        } else if (bDirect) {
            toReturn = Math.min(toReturn + (toHit.getMoS() / 3.0), toReturn * 2);
        }

        toReturn = applyGlancingBlowModifier(toReturn, target.isConventionalInfantry());

        // OS PFD: -10% to all energy weapons (PPCs handled separately in PPCHandler)
        if ((target instanceof Entity) && ((Entity) target).hasActiveOSPFD()) {
            toReturn = Math.max(toReturn * 0.9, 1.0);
            addPFDActivationReport((Entity) target, ((Entity) target).getOSPFDRounds(), 1251, 1252);
        }

        // OS Adv. PFD: -20% to all energy weapons (PPCs handled separately in PPCHandler)
        if ((target instanceof Entity) && ((Entity) target).hasActiveOSAdvPFD()) {
            toReturn = Math.max(toReturn * 0.8, 1.0);
            addPFDActivationReport((Entity) target, ((Entity) target).getOSAdvPFDRounds(), 1255, 1256);
        }

        return (int) Math.ceil(toReturn);
    }

    /**
     * Adds a PFD/Adv. PFD activation report to {@code calcDmgPerHitReport}.
     * Shows "overload check active" if {@code currentRounds >= 8}, otherwise
     * shows how many rounds remain before the overload check begins.
     *
     * @param targetEntity    the entity whose PFD is active
     * @param currentRounds   how many consecutive rounds the PFD has been on
     * @param reportIdActive  report number to use when overload check is already running
     * @param reportIdCountdown report number to use when countdown is still ongoing
     */
    protected void addPFDActivationReport(Entity targetEntity, int currentRounds,
                                          int reportIdActive, int reportIdCountdown) {
        int roundsRemaining = 8 - currentRounds;
        Report r;
        if (roundsRemaining <= 0) {
            r = new Report(reportIdActive);
            r.subject = subjectId;
            r.indent(2);
            r.addDesc(targetEntity);
        } else {
            r = new Report(reportIdCountdown);
            r.subject = subjectId;
            r.indent(2);
            r.addDesc(targetEntity);
            r.add(roundsRemaining);
        }
        calcDmgPerHitReport.add(r);
    }

}
