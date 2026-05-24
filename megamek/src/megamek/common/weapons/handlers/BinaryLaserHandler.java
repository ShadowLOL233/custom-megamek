/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
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

import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.equipment.EquipmentType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.units.Entity;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Handler for Outer Sphere Binary/Trinary Laser System.
 * Implements Resonance Tuning: damage multiplier vs Standard Armor only.
 * Other armor types take normal damage.
 */
public class BinaryLaserHandler extends EnergyWeaponHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    private final double resonanceMultiplier;

    /**
     * @param resonanceMultiplier 1.30 for Binary, 1.50 for Trinary
     */
    public BinaryLaserHandler(ToHitData toHit, WeaponAttackAction waa, Game g, TWGameManager m,
          double resonanceMultiplier) throws EntityLoadingException {
        super(toHit, waa, g, m);
        this.resonanceMultiplier = resonanceMultiplier;
    }

    /**
     * Default constructor uses Binary multiplier (1.30) for backward compatibility.
     */
    public BinaryLaserHandler(ToHitData toHit, WeaponAttackAction waa, Game g, TWGameManager m)
          throws EntityLoadingException {
        this(toHit, waa, g, m, 1.30);
    }

    @Override
    protected int calcDamagePerHit() {
        int baseDamage = super.calcDamagePerHit();

        // Resonance Tuning: bonus damage applies only to Standard Armor
        if (target instanceof Entity entityTarget) {
            int armorType = getRepresentativeArmorType(entityTarget);
            if (isStandardArmor(armorType)) {
                int amplifiedDamage = (int) Math.ceil(baseDamage * resonanceMultiplier);
                int reportId = (resonanceMultiplier >= 1.45) ? 1261 : 1260;
                Report r = new Report(reportId);
                r.subject = subjectId;
                r.indent(2);
                r.add(baseDamage);
                r.add(amplifiedDamage);
                calcDmgPerHitReport.add(r);
                return amplifiedDamage;
            }
        }

        return baseDamage;
    }

    /**
     * Checks the most common armor type on the target. For non-patchwork units,
     * this returns the uniform armor type. For patchwork, returns the CT/torso armor type.
     */
    private static int getRepresentativeArmorType(Entity entity) {
        if (entity.locations() == 0) {
            return EquipmentType.T_ARMOR_UNKNOWN;
        }
        // Use the location of the actual hit if available, otherwise use first location
        return entity.getArmorType(1);
    }

    private static boolean isStandardArmor(int armorType) {
        return armorType == EquipmentType.T_ARMOR_STANDARD
              || armorType == EquipmentType.T_ARMOR_INDUSTRIAL
              || armorType == EquipmentType.T_ARMOR_COMMERCIAL
              || armorType == EquipmentType.T_ARMOR_PRIMITIVE
              || armorType == EquipmentType.T_ARMOR_PRIMITIVE_FIGHTER
              || armorType == EquipmentType.T_ARMOR_HEAVY_INDUSTRIAL
              || armorType == EquipmentType.T_ARMOR_STANDARD_PROTOMEK
              || armorType == EquipmentType.T_ARMOR_AEROSPACE
              || armorType == EquipmentType.T_ARMOR_PRIMITIVE_AERO;
    }
}
