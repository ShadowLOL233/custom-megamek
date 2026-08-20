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

import megamek.common.board.Coords;
import megamek.common.equipment.EquipmentType;
import megamek.common.game.Game;
import megamek.common.units.BipedMek;
import megamek.common.units.Crew;
import megamek.common.units.CrewType;
import megamek.common.units.Entity;
import megamek.common.weapons.c3.OSNetworkDesignators;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies the OS Demon Aggressive Hacking System offensive debuff (dev plan §9.1): a unit Demon-hacked this turn
 * takes a to-hit penalty when it shoots at the hacker's own team — +1 on a normal designation, +2 when the Demon
 * designation roll was a critical (natural 9+). Exercises {@link OSNetworkDesignators#demonHackPenalty} directly (the
 * ECM-doubling path is left to in-game playtest, as it needs an ECM emitter + board). The hacked unit sits on team 1;
 * the hacker + its allies sit on team 2.
 */
public class OSDemonHackingTest {

    private Game game;
    private int nextEntityId = 1;

    @BeforeAll
    static void initializeEquipment() {
        EquipmentType.initializeTypes();
    }

    @BeforeEach
    void setUp() {
        game = new Game();
        Player hackedSide = new Player(0, "Hacked Side");
        hackedSide.setTeam(1);
        Player hackerSide = new Player(1, "Hacker Side");
        hackerSide.setTeam(2);
        game.addPlayer(0, hackedSide);
        game.addPlayer(1, hackerSide);
        nextEntityId = 1;
    }

    private Entity newMek(int playerId, int x, int y) {
        Entity entity = new BipedMek();
        entity.setGame(game);
        entity.setId(nextEntityId++);
        entity.setChassis("Demon Test");
        entity.setModel("m" + entity.getId());
        entity.setCrew(new Crew(CrewType.SINGLE));
        entity.setOwner(game.getPlayer(playerId));
        entity.setWeight(50.0);
        entity.setOriginalWalkMP(4);
        entity.setPosition(new Coords(x, y));
        game.addEntity(entity);
        return entity;
    }

    /** A hacked unit firing at the hacker's team takes +1 to-hit on a normal (non-crit) designation. */
    @Test
    void testPlus1OnNormalHack() {
        Entity hacked = newMek(0, 0, 0);   // team 1
        Entity hacker = newMek(1, 1, 0);   // team 2
        Entity ally = newMek(1, 2, 0);     // team 2 (the hacker's teammate = the victim)
        hacked.setDemonHackedBy(hacker.getId(), false);

        assertEquals(1, OSNetworkDesignators.demonHackPenalty(game, hacked, ally),
              "a Demon-hacked unit shooting the hacker's team should take +1 to-hit");
    }

    /** A critical Demon designation deepens the penalty to +2. */
    @Test
    void testPlus2OnCritHack() {
        Entity hacked = newMek(0, 0, 0);
        Entity hacker = newMek(1, 1, 0);
        Entity ally = newMek(1, 2, 0);
        hacked.setDemonHackedBy(hacker.getId(), true);

        assertEquals(2, OSNetworkDesignators.demonHackPenalty(game, hacked, ally),
              "a critical Demon hack should impose +2 to-hit");
    }

    /** The penalty applies only when shooting the hacker's team, not other targets (e.g. a third party). */
    @Test
    void testNoPenaltyAgainstNonHackerTeam() {
        Entity hacked = newMek(0, 0, 0);          // team 1
        Entity hacker = newMek(1, 1, 0);          // team 2
        Entity friendly = newMek(0, 3, 0);        // team 1 (a teammate of the hacked unit)
        hacked.setDemonHackedBy(hacker.getId(), false);

        assertEquals(0, OSNetworkDesignators.demonHackPenalty(game, hacked, friendly),
              "the Demon debuff should not bite when the hacked unit shoots a non-hacker-team target");
    }

    /** An un-hacked unit is never penalized. */
    @Test
    void testNoPenaltyWithoutHack() {
        Entity shooter = newMek(0, 0, 0);
        Entity ally = newMek(1, 1, 0);

        assertEquals(0, OSNetworkDesignators.demonHackPenalty(game, shooter, ally),
              "a unit that was not Demon-hacked should take no penalty");
    }

    /** If the hacker is destroyed, the hack lapses. */
    @Test
    void testNoPenaltyWhenHackerDestroyed() {
        Entity hacked = newMek(0, 0, 0);
        Entity hacker = newMek(1, 1, 0);
        Entity ally = newMek(1, 2, 0);
        hacked.setDemonHackedBy(hacker.getId(), true);
        hacker.setDestroyed(true);

        assertEquals(0, OSNetworkDesignators.demonHackPenalty(game, hacked, ally),
              "a destroyed Demon carrier should no longer impose the debuff");
    }
}
