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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

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
 * Verifies that the OS "C3 Node" (dev plan §9.1, a Boosted C3-master equivalent, {@code F_C3MBS}) behaves like the
 * canon C3 network hierarchy: one node is a lance master, and mounting two nodes on a single mech makes it a company
 * commander (C3MM) able to anchor a company-scale (12-unit) C3 network. The OS "C3 Point" ({@code F_C3SBS}) is the
 * matching Boosted slave.
 *
 * <p>Mirrors {@code C3MasterBVTest} but swaps the canon C3 computers for the OS equipment.</p>
 */
public class OSC3NodeCompanyCommandTest {

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

    private Entity newMek(String model, double weight) {
        Entity entity = new BipedMek();
        entity.setGame(game);
        entity.setId(nextEntityId++);
        entity.setChassis("OS Test Mek");
        entity.setModel(model);
        entity.setCrew(new Crew(CrewType.SINGLE));
        entity.setOwner(game.getPlayer(0));
        entity.setWeight(weight);
        entity.setOriginalWalkMP(4);
        return entity;
    }

    /** Company commander: two OS C3 Nodes -> C3MM. */
    private Entity createCompanyCommander() {
        Entity entity = newMek("OS C3MM", 100.0);
        try {
            EquipmentType node = EquipmentType.get("OSC3Node");
            entity.addEquipment(node, Mek.LOC_CENTER_TORSO);
            entity.addEquipment(node, Mek.LOC_RIGHT_TORSO);
        } catch (Exception e) {
            fail("Failed to add OS C3 Node equipment: " + e.getMessage());
        }
        return entity;
    }

    /** Lance master: one OS C3 Node. */
    private Entity createLanceMaster(String model) {
        Entity entity = newMek(model, 75.0);
        try {
            entity.addEquipment(EquipmentType.get("OSC3Node"), Mek.LOC_CENTER_TORSO);
        } catch (Exception e) {
            fail("Failed to add OS C3 Node equipment: " + e.getMessage());
        }
        return entity;
    }

    /** Slave: one OS C3 Point. */
    private Entity createPoint(String model) {
        Entity entity = newMek(model, 50.0);
        try {
            entity.addEquipment(EquipmentType.get("OSC3Point"), Mek.LOC_CENTER_TORSO);
        } catch (Exception e) {
            fail("Failed to add OS C3 Point equipment: " + e.getMessage());
        }
        return entity;
    }

    /** One OS C3 Node -> lance master (C3M), not a company commander. Two -> company commander (C3MM). */
    @Test
    void testNodeCountDeterminesMasterTier() {
        Entity oneNode = createLanceMaster("single-node");
        Entity twoNodes = createCompanyCommander();
        Entity point = createPoint("point");
        game.addEntity(oneNode);
        game.addEntity(twoNodes);
        game.addEntity(point);

        assertTrue(oneNode.hasC3M(), "one OS C3 Node should be a C3 lance master");
        assertFalse(oneNode.hasC3MM(), "one OS C3 Node should NOT be a company commander");

        assertTrue(twoNodes.hasC3MM(), "two OS C3 Nodes should form a company commander (C3MM)");
        assertTrue(twoNodes.hasC3M(), "a company commander also reports hasC3M() (its lance-master half)");

        assertTrue(point.hasC3S(), "an OS C3 Point should be a C3 slave");
        assertFalse(point.hasC3M(), "an OS C3 Point is not a master");
    }

    /** A fresh company commander can anchor a company: 2 master-to-master links + 3 slave links. */
    @Test
    void testCompanyCommanderHubCapacity() {
        Entity commander = createCompanyCommander();
        game.addEntity(commander);

        assertTrue(commander.hasC3MM(), "two OS C3 Nodes -> C3MM");
        assertEquals(2, commander.calculateFreeC3MNodes(),
              "company hub links 2 other lance masters (M level)");
        assertEquals(3, commander.calculateFreeC3Nodes(),
              "company hub's own lance links 3 slaves (S level)");
    }

    /**
     * End-to-end: the OS company commander wires two separate lances plus its own lance into a single company-scale
     * (12-unit) C3 network.
     */
    @Test
    void testAnchorsCompanyScaleNetwork() {
        Entity commander = createCompanyCommander();
        Entity lanceA = createLanceMaster("lance-A-master");
        Entity lanceB = createLanceMaster("lance-B-master");
        game.addEntity(commander);
        game.addEntity(lanceA);
        game.addEntity(lanceB);

        List<Entity> commanderLance = addPoints("cmd-slave", 3);
        List<Entity> lanceAPoints = addPoints("A-slave", 3);
        List<Entity> lanceBPoints = addPoints("B-slave", 3);

        // Company level: both lance masters connect up to the commander.
        lanceA.setC3Master(commander.getId(), true);
        lanceB.setC3Master(commander.getId(), true);
        assertSame(commander, lanceA.getC3Master(), "lance A master reports the commander as its master");
        assertSame(commander, lanceB.getC3Master(), "lance B master reports the commander as its master");
        assertEquals(0, commander.calculateFreeC3MNodes(), "both company master links are now consumed");

        // Lance level: slaves connect to their lance masters (the commander runs its own lance).
        commanderLance.forEach(s -> s.setC3Master(commander.getId(), true));
        lanceAPoints.forEach(s -> s.setC3Master(lanceA.getId(), true));
        lanceBPoints.forEach(s -> s.setC3Master(lanceB.getId(), true));

        // The whole company shares one network anchored on the commander.
        List<Entity> company = new ArrayList<>(List.of(commander, lanceA, lanceB));
        company.addAll(commanderLance);
        company.addAll(lanceAPoints);
        company.addAll(lanceBPoints);
        assertEquals(12, company.size(), "company scale = 12 units");

        for (Entity member : company) {
            if (member != commander) {
                assertTrue(commander.onSameC3NetworkAs(member),
                      member.getModel() + " should share the commander's C3 network");
            }
        }
        // A depth-2 member (slave under a lance master) also sees a peer in another lance.
        assertTrue(lanceAPoints.get(0).onSameC3NetworkAs(lanceBPoints.get(0)),
              "slaves in different lances of the same company share one network");
    }

    private List<Entity> addPoints(String prefix, int count) {
        List<Entity> points = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Entity point = createPoint(prefix + "-" + i);
            game.addEntity(point);
            points.add(point);
        }
        return points;
    }
}
