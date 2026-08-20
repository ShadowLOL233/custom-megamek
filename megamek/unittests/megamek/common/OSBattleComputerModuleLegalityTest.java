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
 *
 * NOTICE: The MegaMek organization is a non-profit group of volunteers
 * creating free software for the BattleTech community.
 */
package megamek.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import megamek.common.equipment.EquipmentType;
import megamek.common.equipment.MiscType;
import megamek.common.equipment.WeaponType;
import megamek.common.equipment.enums.MiscTypeFlag;
import megamek.common.equipment.WeaponTypeFlag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Guards the detection contract that {@code TestMek} keys on for the OS Battle Computer System "modules require a
 * B-2500 core" legality rule (dev plan §9.1). Because the OS BCS modules reuse canon C3/ECM flags, the rule is gated on
 * dedicated OS markers ({@link MiscTypeFlag#F_OS_BCS_MODULE} / {@link WeaponTypeFlag#F_OS_BCS_MODULE}). This test checks
 * every OS module carries the marker, the B-2500 core carries {@link MiscTypeFlag#F_OS_BATTLE_COMPUTER}, and — the key
 * safety property — that <b>canon</b> gear does NOT carry the OS marker (so canon C3/ECM is never wrongly gated).
 */
public class OSBattleComputerModuleLegalityTest {

    @BeforeAll
    static void initializeEquipment() {
        EquipmentType.initializeTypes();
    }

    private static void assertMiscModule(String internalName) {
        EquipmentType et = EquipmentType.get(internalName);
        assertTrue(et instanceof MiscType, internalName + " should resolve to a MiscType");
        assertTrue(((MiscType) et).hasFlag(MiscTypeFlag.F_OS_BCS_MODULE),
              internalName + " should be marked F_OS_BCS_MODULE so the verifier requires a B-2500 core");
    }

    private static void assertWeaponModule(String internalName) {
        EquipmentType et = EquipmentType.get(internalName);
        assertTrue(et instanceof WeaponType, internalName + " should resolve to a WeaponType");
        assertTrue(((WeaponType) et).hasFlag(WeaponTypeFlag.F_OS_BCS_MODULE),
              internalName + " should be marked F_OS_BCS_MODULE so the verifier requires a B-2500 core");
    }

    /** Every OS BCS MiscType module (C3 Point + the three ECM suites) carries the OS-module marker. */
    @Test
    void testOSMiscModulesMarked() {
        assertMiscModule("OSC3Point");
        assertMiscModule("OSImproveC3Point");
        assertMiscModule("OSGuardianECM");
        assertMiscModule("OSImproveGuardianECM");
        assertMiscModule("OSImproveAngleECM");
    }

    /** Every OS BCS WeaponType module (C3 Node, Improve C3 Node, Demon) carries the OS-module marker. */
    @Test
    void testOSWeaponModulesMarked() {
        assertWeaponModule("OSC3Node");
        assertWeaponModule("OSImproveC3Node");
        assertWeaponModule("OSDemonHackingSystem");
    }

    /** The B-2500 core carries the core flag the module rule requires. */
    @Test
    void testBattleComputerCoreFlag() {
        EquipmentType et = EquipmentType.get("OSBattleComputer");
        assertTrue(et instanceof MiscType, "OSBattleComputer should resolve to a MiscType");
        assertTrue(((MiscType) et).hasFlag(MiscTypeFlag.F_OS_BATTLE_COMPUTER),
              "the B-2500 core should carry F_OS_BATTLE_COMPUTER");
    }

    /** Safety: canon Guardian ECM must NOT carry the OS marker, so a canon C3/ECM unit is never gated on a B-2500. */
    @Test
    void testCanonGearNotMarked() {
        EquipmentType canonGuardian = EquipmentType.get("ISGuardianECMSuite");
        assertTrue(canonGuardian instanceof MiscType, "ISGuardianECMSuite should resolve to a MiscType");
        assertFalse(((MiscType) canonGuardian).hasFlag(MiscTypeFlag.F_OS_BCS_MODULE),
              "canon Guardian ECM must NOT be marked as an OS BCS module");
    }
}
