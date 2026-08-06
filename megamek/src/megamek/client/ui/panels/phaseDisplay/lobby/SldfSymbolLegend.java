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
package megamek.client.ui.panels.phaseDisplay.lobby;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import megamek.client.ui.panels.phaseDisplay.lobby.SldfSymbol.Branch;
import megamek.client.ui.panels.phaseDisplay.lobby.SldfSymbol.Echelon;
import megamek.common.enums.SkillLevel;

/**
 * Standalone visual key for {@link SldfSymbol}: opens a window showing every branch glyph, weight class, experience
 * ring and echelon symbol plus a few player-colour samples, so the symbology can be eyeballed and iterated on before
 * it is wired into the Force View canvas. Throwaway dev tooling (run {@link #main} from an IDE or
 * {@code ./gradlew.bat :megamek:symbolLegend}); not part of the shipped UI.
 */
public class SldfSymbolLegend extends JPanel {

    private static final Color BG = new Color(52, 52, 56);
    private static final Color LABEL = new Color(225, 225, 230);
    private static final Color TITLE = new Color(255, 210, 120);
    private static final int SYM_W = 104;
    private static final int SYM_H = 76;
    private static final int CELL_W = SYM_W + 16;
    private static final int ROW_H = SYM_H + 46;

    // A couple of representative player colours (the live view uses Player.getColour()).
    private static final Color[] SAMPLE_COLORS = {
          new Color(58, 110, 200), new Color(190, 55, 55), new Color(70, 150, 80),
          new Color(210, 170, 60), new Color(140, 90, 175) };
    private static final String[] SAMPLE_COLOR_NAMES = { "Blue", "Red", "Green", "Gold", "Purple" };

    @Override
    public Dimension getPreferredSize() {
        int cols = Math.max(Branch.values().length, Echelon.values().length);
        return new Dimension(cols * CELL_W + 40, 5 * ROW_H + 30);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(BG);
        g.fillRect(0, 0, getWidth(), getHeight());

        Color sample = SAMPLE_COLORS[0];
        int y = 20;

        y = section(g, "Branches (Medium, Regular)", y);
        int x = 20;
        for (Branch branch : Branch.values()) {
            SldfSymbol.paintUnit(g, x, y, SYM_W, SYM_H, branch, 1, SkillLevel.REGULAR, sample);
            label(g, branch.name(), x, y);
            x += CELL_W;
        }
        y += ROW_H;

        y = section(g, "Weight class (BattleMek)", y);
        x = 20;
        String[] weights = { "Light", "Medium", "Heavy", "Assault" };
        for (int w = 0; w < 4; w++) {
            SldfSymbol.paintUnit(g, x, y, SYM_W, SYM_H, Branch.MEK, w, SkillLevel.REGULAR, sample);
            label(g, weights[w], x, y);
            x += CELL_W;
        }
        y += ROW_H;

        y = section(g, "Experience (Heavy Vehicle) — border colour + letter", y);
        x = 20;
        SkillLevel[] skills = { SkillLevel.ULTRA_GREEN, SkillLevel.GREEN, SkillLevel.REGULAR,
              SkillLevel.VETERAN, SkillLevel.ELITE, SkillLevel.HEROIC, SkillLevel.LEGENDARY };
        for (SkillLevel skill : skills) {
            SldfSymbol.paintUnit(g, x, y, SYM_W, SYM_H, Branch.VEHICLE_TRACKED, 2, skill, sample);
            label(g, skill.name(), x, y);
            x += CELL_W;
        }
        y += ROW_H;

        y = section(g, "Echelon (Mek formation)", y);
        x = 20;
        for (Echelon echelon : Echelon.values()) {
            SldfSymbol.paintFormation(g, x, y, SYM_W, SYM_H, echelon, Branch.MEK, 2, sample);
            label(g, echelon.label(), x, y);
            x += CELL_W;
        }
        y += ROW_H;

        y = section(g, "Player colour (Heavy Mek, Veteran)", y);
        x = 20;
        for (int i = 0; i < SAMPLE_COLORS.length; i++) {
            SldfSymbol.paintUnit(g, x, y, SYM_W, SYM_H, Branch.MEK, 2, SkillLevel.VETERAN, SAMPLE_COLORS[i]);
            label(g, SAMPLE_COLOR_NAMES[i], x, y);
            x += CELL_W;
        }
    }

    private int section(Graphics2D g, String title, int y) {
        g.setColor(TITLE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 13f));
        g.drawString(title, 20, y + 14);
        return y + 24;
    }

    private void label(Graphics2D g, String text, int x, int y) {
        g.setColor(LABEL);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 11f));
        int tw = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x + (SYM_W - tw) / 2, y + SYM_H + 16);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("SLDF Symbol Legend");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new SldfSymbolLegend());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
