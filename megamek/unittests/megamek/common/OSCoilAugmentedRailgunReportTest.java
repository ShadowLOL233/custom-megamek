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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Renders the Coil Augmented Railgun Round Report lines (message IDs 1270–1274) to guard against the tag/{@code add}
 * count mismatch that silently drops text (the same class of bug that hit the B-2500 coordination line). Templates with
 * {@code <data>} placeholders must be fed exactly as many {@code add()} calls as the handler provides.
 */
public class OSCoilAugmentedRailgunReportTest {

    @Test
    void plainReportsRenderTheirText() {
        assertTrue(new Report(1270).text().contains("JAMS"), "jam line");
        assertTrue(new Report(1272).text().contains("SCRAPPED"), "scrap line");
        assertTrue(new Report(1274).text().contains("punches clean through"), "AP flavor line");
    }

    @Test
    void degradationReportRendersBothNumbers() {
        Report degradation = new Report(1271);
        degradation.add(3); // current degradation
        degradation.add(5); // SCRAP_AT
        String rendered = degradation.text();
        assertTrue(rendered.contains("3") && rendered.contains("5"),
              "degradation line should show '3 of 5'; was: " + rendered);
    }

    @Test
    void wornBarrelReportRendersPenalty() {
        Report wear = new Report(1273);
        wear.add(4); // +N to-hit
        String rendered = wear.text();
        assertTrue(rendered.contains("4"), "worn-barrel line should show the +N penalty; was: " + rendered);
    }
}
