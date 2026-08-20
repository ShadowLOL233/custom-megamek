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
import static org.junit.jupiter.api.Assertions.fail;

import megamek.common.equipment.EquipmentType;
import megamek.common.game.Game;
import megamek.common.units.BipedMek;
import megamek.common.units.Crew;
import megamek.common.units.CrewType;
import megamek.common.units.Entity;
import megamek.common.units.Mek;
import megamek.common.weapons.c3.OSNetworkDesignators;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies the OS Improve C3 Point focus-fire bonus (dev plan §9.1): while a working Improve C3 Point is on the
 * attacker's C3 network, network units attacking a target the network has TAG'd get -1 to-hit. Exercises
 * {@link OSNetworkDesignators#improveC3PointFocusFire} over a minimal OS C3 network (a C3 Node master linked to an
 * Improve C3 Point slave), mirroring the network-build pattern in {@code OSC3NodeCompanyCommandTest}.
 */
public class OSImproveC3PointFocusFireTest {

    private Game game;
    private int nextEntityId = 1;

    @BeforeAll
    static void initializeEquipment() {
        EquipmentType.initializeTypes();
    }

    @BeforeEach
    void setUp() {
        game = new Game();
        game.addPlayer(0, new Player(0, "Team A"));
        game.addPlayer(1, new Player(1, "Team B"));
        nextEntityId = 1;
    }

    private Entity newMek(int playerId, String model) {
        Entity entity = new BipedMek();
        entity.setGame(game);
        entity.setId(nextEntityId++);
        entity.setChassis("FocusFire Test");
        entity.setModel(model);
        entity.setCrew(new Crew(CrewType.SINGLE));
        entity.setOwner(game.getPlayer(playerId));
        entity.setWeight(50.0);
        entity.setOriginalWalkMP(4);
        return entity;
    }

    private Entity addWithEquipment(int playerId, String model, String internalName) {
        Entity entity = newMek(playerId, model);
        try {
            entity.addEquipment(EquipmentType.get(internalName), Mek.LOC_CENTER_TORSO);
        } catch (Exception e) {
            fail("Failed to add " + internalName + ": " + e.getMessage());
        }
        game.addEntity(entity);
        return entity;
    }

    /** A network with a working Improve C3 Point grants -1 vs a target the network has TAG'd. */
    @Test
    void testFocusFireMinusOne() {
        Entity master = addWithEquipment(0, "c3-node-master", "OSC3Node");
        Entity focusPoint = addWithEquipment(0, "improve-c3-point", "OSImproveC3Point");
        Entity enemy = newMek(1, "enemy");
        game.addEntity(enemy);
        focusPoint.setC3Master(master.getId(), true);
        enemy.setTaggedBy(master.getId()); // a networked TAG source designates the enemy

        assertEquals(-1, OSNetworkDesignators.improveC3PointFocusFire(game, master, enemy),
              "a networked Improve C3 Point should grant -1 focus-fire vs a network-TAG'd target");
    }

    /** No focus-fire without a network TAG on the target. */
    @Test
    void testNoBonusWithoutTag() {
        Entity master = addWithEquipment(0, "c3-node-master", "OSC3Node");
        Entity focusPoint = addWithEquipment(0, "improve-c3-point", "OSImproveC3Point");
        Entity enemy = newMek(1, "enemy");
        game.addEntity(enemy);
        focusPoint.setC3Master(master.getId(), true);

        assertEquals(0, OSNetworkDesignators.improveC3PointFocusFire(game, master, enemy),
              "without a TAG on the target the focus-fire bonus should not apply");
    }

    /** A plain (non-Improve) C3 Point network gives no focus-fire even with a TAG. */
    @Test
    void testNoBonusWithoutImproveC3Point() {
        Entity master = addWithEquipment(0, "c3-node-master", "OSC3Node");
        Entity plainPoint = addWithEquipment(0, "plain-c3-point", "OSC3Point");
        Entity enemy = newMek(1, "enemy");
        game.addEntity(enemy);
        plainPoint.setC3Master(master.getId(), true);
        enemy.setTaggedBy(master.getId());

        assertEquals(0, OSNetworkDesignators.improveC3PointFocusFire(game, master, enemy),
              "a network without an Improve C3 Point should grant no focus-fire bonus");
    }

    /** A TAG from an off-network unit does not trigger the network focus-fire. */
    @Test
    void testNoBonusWhenTaggerNotNetworked() {
        Entity master = addWithEquipment(0, "c3-node-master", "OSC3Node");
        Entity focusPoint = addWithEquipment(0, "improve-c3-point", "OSImproveC3Point");
        focusPoint.setC3Master(master.getId(), true);
        Entity offNet = newMek(0, "off-network-tagger"); // no C3, not on the network
        game.addEntity(offNet);
        Entity enemy = newMek(1, "enemy");
        game.addEntity(enemy);
        enemy.setTaggedBy(offNet.getId());

        assertEquals(0, OSNetworkDesignators.improveC3PointFocusFire(game, master, enemy),
              "a TAG from a non-networked unit should not trigger network focus-fire");
    }
}
