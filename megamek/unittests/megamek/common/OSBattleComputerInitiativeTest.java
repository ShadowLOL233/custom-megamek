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
import megamek.common.game.InitiativeBonusBreakdown;
import megamek.common.units.BipedMek;
import megamek.common.units.Crew;
import megamek.common.units.CrewType;
import megamek.common.units.Entity;
import megamek.common.units.Mek;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies the OS Battle Computer System "Tactical Coordination" initiative effect (dev plan §9.1): a B-2500 core that
 * rolls a 12 on its per-round 2d6 coordination die grants the force +2 initiative. Also checks that the new {@code bcs}
 * component threads correctly through {@link InitiativeBonusBreakdown} (positive, non-stacking with other positives).
 */
public class OSBattleComputerInitiativeTest {

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

    private Entity newDeployedMek(boolean withBattleComputer) {
        Entity entity = new BipedMek();
        entity.setGame(game);
        entity.setId(nextEntityId++);
        entity.setChassis("BCS Test");
        entity.setModel(withBattleComputer ? "B-2500" : "plain");
        entity.setCrew(new Crew(CrewType.SINGLE));
        entity.setOwner(game.getPlayer(0));
        entity.setWeight(100.0);
        entity.setOriginalWalkMP(3);
        entity.setDeployed(true); // required by Player.isActiveForCommandBonus
        if (withBattleComputer) {
            try {
                entity.addEquipment(EquipmentType.get("OSBattleComputer"), Mek.LOC_CENTER_TORSO);
            } catch (Exception e) {
                fail("Failed to add OS Battle Computer core: " + e.getMessage());
            }
        }
        return entity;
    }

    /** A coordination roll below 12 grants no initiative bonus. */
    @Test
    void testNoInitiativeBonusBelow12() {
        Entity core = newDeployedMek(true);
        game.addEntity(core);
        Player player = game.getPlayer(0);

        for (int roll : new int[] { 2, 7, 9, 10, 11 }) {
            core.setBcsCoordinationRoll(roll);
            assertEquals(0, player.getBcsCoordinationInitBonus(),
                  "coordination roll " + roll + " should grant no initiative bonus");
        }
    }

    /** A coordination roll of 12 grants +2 initiative. */
    @Test
    void testPlus2InitiativeOn12() {
        Entity core = newDeployedMek(true);
        game.addEntity(core);
        core.setBcsCoordinationRoll(12);

        assertEquals(2, game.getPlayer(0).getBcsCoordinationInitBonus(),
              "a coordination roll of 12 should grant +2 initiative to the force");
    }

    /** Without a BCS core, no bonus is granted even if a roll value is somehow present. */
    @Test
    void testNoBonusWithoutCore() {
        Entity plain = newDeployedMek(false);
        game.addEntity(plain);
        plain.setBcsCoordinationRoll(12);

        assertEquals(0, game.getPlayer(0).getBcsCoordinationInitBonus(),
              "a unit without a B-2500/Crow Nest core should never grant the coordination bonus");
    }

    /** A core that is neither on-board nor deploying next round does not contribute (fails isActiveForCommandBonus). */
    @Test
    void testNoBonusWhenInactive() {
        Entity core = newDeployedMek(true);
        core.setDeployed(false);
        core.setDeployRound(99); // far future: not on-board and not deploying next round
        game.addEntity(core);
        core.setBcsCoordinationRoll(12);

        assertEquals(0, game.getPlayer(0).getBcsCoordinationInitBonus(),
              "an inactive (not-yet-deploying) core should not contribute an initiative bonus");
    }

    /**
     * The initiative-report line (message 1269) must render all of its parts — including the effect text — for every
     * outcome. Guards against a tag/{@code add} count mismatch (addDesc contributes the unit AND owner tokens).
     */
    @Test
    void testCoordinationReportRendersRollAndEffect() {
        Entity core = newDeployedMek(true);
        game.addEntity(core);

        for (int[] rollAndKeyword : new int[][] { { 7, 0 }, { 11, 1 }, { 12, 2 } }) {
            int roll = rollAndKeyword[0];
            String effect = switch (rollAndKeyword[1]) {
                case 2 -> "network -1 to-hit vs direct-fire and force initiative +2 this round (negated by hostile ECM)";
                case 1 -> "network -1 to-hit vs direct-fire this round (negated by hostile ECM)";
                default -> "no coordination effect this round";
            };
            Report report = new Report(1269);
            report.subject = core.getId();
            report.addDesc(core);
            report.add(roll);
            report.add(effect);

            String rendered = report.text();
            assertTrue(rendered.contains(String.valueOf(roll)),
                  "report should show the 2d6 roll " + roll + "; was: " + rendered);
            assertTrue(rendered.contains(effect),
                  "report should render the effect text for roll " + roll + "; was: " + rendered);
        }
    }

    /** The bcs component participates in the breakdown total and display, and does not stack with other positives. */
    @Test
    void testBreakdownIntegration() {
        // bcs alone contributes +2 to the total
        InitiativeBonusBreakdown bcsOnly =
              new InitiativeBonusBreakdown(0, 0, null, 0, 0, 0, 2, 0, 0, 0);
        assertEquals(2, bcsOnly.total(), "a lone +2 BCS bonus should total +2");
        assertTrue(bcsOnly.toBreakdownString().contains("BCS")
                    || bcsOnly.toBreakdownString().contains("Coordination"),
              "the breakdown display should mention the BCS/Coordination bonus");

        // Positives do not stack: +2 BCS alongside +2 console still totals +2
        InitiativeBonusBreakdown bcsAndConsole =
              new InitiativeBonusBreakdown(0, 0, null, 2, 0, 0, 2, 0, 0, 0);
        assertEquals(2, bcsAndConsole.total(), "two +2 positives should not stack (highest applies)");

        // Negatives stack cumulatively with the highest positive
        InitiativeBonusBreakdown bcsWithPenalty =
              new InitiativeBonusBreakdown(0, 0, null, 0, 0, 0, 2, -1, 0, 0);
        assertEquals(1, bcsWithPenalty.total(), "+2 BCS with a -1 constant should total +1");
    }
}
