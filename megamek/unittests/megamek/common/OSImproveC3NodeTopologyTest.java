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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import megamek.common.equipment.EquipmentType;
import megamek.common.game.Game;
import megamek.common.units.BipedMek;
import megamek.common.units.Crew;
import megamek.common.units.CrewType;
import megamek.common.units.Entity;
import megamek.common.units.Mek;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies the OS Improve C3 Node's enlarged topology and the OS company-scale (~26-unit) C3 network cap (dev plan
 * §9.1): the Improve C3 Node links 6 slave Points (vs the plain node's 3) and, as a company commander, 3 master Nodes
 * (vs 2); OS Boosted C3 networks raise the join cap from the canon 12 to {@link Entity#MAX_OS_C3_NODES}. The canon C3
 * cap is left unchanged (the raise is gated on the OS module marker).
 */
public class OSImproveC3NodeTopologyTest {

    private Game game;
    private int nextEntityId = 1;

    @BeforeAll
    static void initializeEquipment() {
        EquipmentType.initializeTypes();
    }

    @BeforeEach
    void setUp() {
        game = new Game();
        game.addPlayer(0, new Player(0, "Test Player"));
        nextEntityId = 1;
    }

    private Entity newMek(String model) {
        Entity entity = new BipedMek();
        entity.setGame(game);
        entity.setId(nextEntityId++);
        entity.setChassis("OS C3 Topology Test");
        entity.setModel(model);
        entity.setCrew(new Crew(CrewType.SINGLE));
        entity.setOwner(game.getPlayer(0));
        entity.setWeight(75.0);
        entity.setOriginalWalkMP(4);
        return entity;
    }

    private Entity addWithEquipment(String model, String internalName, int... extraSlots) {
        Entity entity = newMek(model);
        try {
            entity.addEquipment(EquipmentType.get(internalName), Mek.LOC_CENTER_TORSO);
            for (int loc : extraSlots) {
                entity.addEquipment(EquipmentType.get(internalName), loc);
            }
        } catch (Exception e) {
            fail("Failed to add " + internalName + ": " + e.getMessage());
        }
        game.addEntity(entity);
        return entity;
    }

    /** A single Improve C3 Node lance master links 6 slave Points (vs the plain node's 3). */
    @Test
    void testImproveNodeSlaveCapacity() {
        Entity improveMaster = addWithEquipment("improve-node", "OSImproveC3Node");
        assertTrue(improveMaster.hasC3M(), "an Improve C3 Node is a C3 master");
        assertTrue(improveMaster.hasOSImproveC3Node(), "should be detected as an OS Improve C3 Node");
        assertEquals(6, improveMaster.calculateFreeC3Nodes(),
              "an Improve C3 Node lance master links 6 slave Points");
    }

    /** The plain OS C3 Node is unchanged: 3 slave links. */
    @Test
    void testPlainNodeUnchanged() {
        Entity plainMaster = addWithEquipment("plain-node", "OSC3Node");
        assertTrue(plainMaster.hasC3M(), "a plain OS C3 Node is a C3 master");
        assertEquals(3, plainMaster.calculateFreeC3Nodes(),
              "the plain OS C3 Node still links 3 slaves (unchanged)");
    }

    /** Two Improve C3 Nodes form a company commander that links 3 master Nodes (vs the canon 2). */
    @Test
    void testImproveCompanyCommanderMasterLinks() {
        Entity commander = addWithEquipment("improve-c3mm", "OSImproveC3Node", Mek.LOC_RIGHT_TORSO);
        assertTrue(commander.hasC3MM(), "two Improve C3 Nodes form a company commander (C3MM)");
        assertEquals(3, commander.calculateFreeC3MNodes(),
              "an Improve C3 Node company commander links 3 master Nodes");
        assertEquals(6, commander.calculateFreeC3Nodes(),
              "the Improve company commander's own lance links 6 slaves");
    }

    /** OS Boosted C3 networks (plain or Improve) raise the join cap to the company scale. */
    @Test
    void testOSNetworkCapRaised() {
        Entity improveMaster = addWithEquipment("improve-node", "OSImproveC3Node");
        Entity plainMaster = addWithEquipment("plain-node", "OSC3Node");
        assertEquals(Entity.MAX_OS_C3_NODES, improveMaster.getC3NetworkNodeCap(),
              "an OS Improve C3 Node network reaches company scale");
        assertEquals(Entity.MAX_OS_C3_NODES, plainMaster.getC3NetworkNodeCap(),
              "an OS Boosted C3 network reaches company scale");
    }

    /** Safety: canon C3 (even the Boosted Master) keeps the canon 12-unit cap — the raise is OS-gated. */
    @Test
    void testCanonCapUnchanged() {
        Entity canonBoostedMaster = addWithEquipment("canon-c3mbs", "ISC3MasterBoostedSystemUnit");
        assertEquals(Entity.MAX_C3_NODES, canonBoostedMaster.getC3NetworkNodeCap(),
              "a canon C3 Boosted Master must keep the canon 12-unit cap (not an OS module)");

        Entity plainMek = newMek("plain");
        game.addEntity(plainMek);
        assertEquals(Entity.MAX_C3_NODES, plainMek.getC3NetworkNodeCap(),
              "a unit with no OS C3 node keeps the canon cap");
    }
}
