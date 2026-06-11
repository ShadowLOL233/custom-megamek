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
package megamek.common.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import megamek.common.equipment.Engine;
import megamek.common.equipment.EquipmentType;
import megamek.common.equipment.EquipmentTypeLookup;
import megamek.common.equipment.MiscType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Verifies the Outer Sphere MASC/Supercharger family: tonnage, critical slots, cost and the
 * per-equipment failure target-number curve. Reference chassis is a 75-ton biped with a 300-rated
 * standard fusion engine.
 */
class OSMovementBoosterTest {

    @BeforeAll
    static void beforeAll() {
        EquipmentType.initializeTypes();
    }

    private static Mek refMek() {
        Mek mek = new BipedMek();
        mek.setWeight(75.0);
        mek.setEngine(new Engine(300, Engine.NORMAL_ENGINE, 0));
        return mek;
    }

    private static MiscType misc(String internalName) {
        MiscType m = (MiscType) EquipmentType.get(internalName);
        assertNotNull(m, "Equipment not registered: " + internalName);
        return m;
    }

    // ---------- MASC family: tonnage / crit / cost ----------

    @Test
    void osMascStats() throws Exception {
        Mek mek = refMek();
        MiscType m = misc(EquipmentTypeLookup.OS_MASC);
        mek.addEquipment(m, Entity.LOC_NONE);
        assertEquals(3.0, m.getTonnage(mek), 0.001);      // 75 * 0.04
        assertEquals(3, m.getNumCriticalSlots(mek, 1.0)); // 75 / 25
        assertEquals(900_000, m.getCost(mek, false, Entity.LOC_NONE), 0.001); // 300 * 3 * 1000
    }

    @Test
    void improveMascStats() throws Exception {
        Mek mek = refMek();
        MiscType m = misc(EquipmentTypeLookup.OS_IMPROVE_MASC);
        mek.addEquipment(m, Entity.LOC_NONE);
        assertEquals(2.0, m.getTonnage(mek), 0.001);      // 75 * 0.03 -> 2.25 -> 2t
        assertEquals(2, m.getNumCriticalSlots(mek, 1.0)); // 75 / 35
        assertEquals(900_000, m.getCost(mek, false, Entity.LOC_NONE), 0.001); // 300 * 2 * 1500
    }

    @Test
    void heavyDutyMascStats() throws Exception {
        Mek mek = refMek();
        MiscType m = misc(EquipmentTypeLookup.OS_HEAVY_DUTY_MASC);
        mek.addEquipment(m, Entity.LOC_NONE);
        assertEquals(4.0, m.getTonnage(mek), 0.001);      // 75 * 0.05 -> 3.75 -> 4t
        assertEquals(3, m.getNumCriticalSlots(mek, 1.0)); // 75 / 22
        assertEquals(1_350_000, m.getCost(mek, false, Entity.LOC_NONE), 0.001); // 300 * 3 * 1500
    }

    // ---------- Supercharger family: tonnage / crit / cost ----------

    @Test
    void superchargerStats() {
        Mek mek = refMek();
        MiscType canon = misc("Supercharger");
        MiscType os = misc(EquipmentTypeLookup.OS_SUPERCHARGER);
        MiscType imp = misc(EquipmentTypeLookup.OS_IMPROVE_SUPERCHARGER);
        MiscType hd = misc(EquipmentTypeLookup.OS_HEAVY_DUTY_SUPERCHARGER);

        // Fixed critical slots: baseline/improve = 1, heavy duty = 2.
        assertEquals(1, os.getBaseCriticalSlots());
        assertEquals(1, imp.getBaseCriticalSlots());
        assertEquals(2, hd.getBaseCriticalSlots());

        // Baseline matches canon (both 10% of engine weight); Improve lighter, Heavy Duty heavier.
        double osT = os.getTonnage(mek);
        assertEquals(canon.getTonnage(mek), osT, 0.001);
        assertTrue(imp.getTonnage(mek) <= osT, "Improve SC should not be heavier than baseline");
        assertTrue(hd.getTonnage(mek) >= osT, "Heavy Duty SC should not be lighter than baseline");

        // Cost: baseline rating*10000, Improve/Heavy Duty rating*12000.
        assertEquals(3_000_000, os.getCost(mek, false, Entity.LOC_NONE), 0.001);
        assertEquals(3_600_000, imp.getCost(mek, false, Entity.LOC_NONE), 0.001);
        assertEquals(3_600_000, hd.getCost(mek, false, Entity.LOC_NONE), 0.001);
    }

    // ---------- Per-equipment failure target-number curve ----------

    private int mascTargetAtLevel(String internalName, int level) throws Exception {
        Mek mek = refMek();
        mek.addEquipment(misc(internalName), Entity.LOC_NONE);
        mek.nMASCLevel = level;
        return mek.getMASCTarget();
    }

    private int scTargetAtLevel(String internalName, int level) throws Exception {
        Mek mek = refMek();
        mek.addEquipment(misc(internalName), Entity.LOC_NONE);
        mek.nSuperchargerLevel = level;
        return mek.getSuperchargerTarget();
    }

    @Test
    void mascFailureCurvesDifferByTier() throws Exception {
        // At level 2: standard=7, alternate(Improve)=5, enhanced(Heavy Duty)=3.
        assertEquals(7, mascTargetAtLevel(EquipmentTypeLookup.OS_MASC, 2));
        assertEquals(5, mascTargetAtLevel(EquipmentTypeLookup.OS_IMPROVE_MASC, 2));
        assertEquals(3, mascTargetAtLevel(EquipmentTypeLookup.OS_HEAVY_DUTY_MASC, 2));
    }

    @Test
    void superchargerFailureCurvesDifferByTier() throws Exception {
        assertEquals(7, scTargetAtLevel(EquipmentTypeLookup.OS_SUPERCHARGER, 2));
        assertEquals(5, scTargetAtLevel(EquipmentTypeLookup.OS_IMPROVE_SUPERCHARGER, 2));
        assertEquals(3, scTargetAtLevel(EquipmentTypeLookup.OS_HEAVY_DUTY_SUPERCHARGER, 2));
    }

    @Test
    void heavyDutyCurveClampsBeyondArrayLength() throws Exception {
        // Enhanced curve caps at 13; an out-of-range level must clamp, not throw.
        assertEquals(13, mascTargetAtLevel(EquipmentTypeLookup.OS_HEAVY_DUTY_MASC, 99));
    }
}
