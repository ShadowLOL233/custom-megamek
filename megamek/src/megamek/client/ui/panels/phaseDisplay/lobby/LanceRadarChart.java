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

import static megamek.client.ui.util.UIUtil.scaleForGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.util.List;
import javax.swing.JPanel;

import megamek.common.equipment.WeaponMounted;
import megamek.common.units.Entity;

/**
 * A MekBay-style six-axis radar (spider) chart summarizing a lance / force's real Total Warfare values: Damage,
 * Armor, Structure, Mobility, Range and Heat. The force polygon (gold) plots the aggregate (Damage/Armor/Structure/
 * Heat summed; Mobility/Range averaged); when a single unit is focused, its own profile is overlaid (blue dashed).
 * Axis labels show the real values. Each polygon is scaled against a fixed reference maximum (force- and per-unit
 * scale respectively) purely so both are visible; the chart is a quick visual comparison, not an exact metric. A
 * light background makes it stand out from the dark lobby. A short header shows the Force and Formation names.
 */
public class LanceRadarChart extends JPanel {

    public static final String[] AXIS_LABELS = { "Damage", "Armor", "Structure", "Mobility", "Range", "Heat" };
    public static final int AXES = AXIS_LABELS.length;

    // Reference maxima used only to scale values to [0,1] for the polygons; labels always show the real value.
    private static final double[] FORCE_MAX = { 160, 1200, 600, 8, 24, 120 };
    private static final double[] UNIT_MAX = { 50, 400, 200, 8, 24, 30 };

    // White grid/text so the chart stands out against MegaMek's dark gray background.
    private static final Color GRID = Color.WHITE;
    private static final Color TEXT = Color.WHITE;
    private static final Color FORCE_COLOR = new Color(224, 170, 60);
    private static final Color UNIT_COLOR = new Color(96, 170, 255);

    private final double[] forceValues = new double[AXES];
    private final double[] forceRaw = new double[AXES];
    private int forceUnitCount = 0;

    private boolean hasUnit = false;
    private final double[] unitValues = new double[AXES];
    private final double[] unitRaw = new double[AXES];

    private String forceName = "";
    private String formationName = "";

    public LanceRadarChart() {
        setPreferredSize(new Dimension(scaleForGUI(380), scaleForGUI(480)));
        setOpaque(false);
    }

    /** Sets the force to display (units may be empty/null to clear) plus the header names. */
    public void setForce(List<Entity> units, String forceName, String formationName) {
        this.forceName = (forceName == null) ? "" : forceName;
        this.formationName = (formationName == null) ? "" : formationName;
        forceUnitCount = (units == null) ? 0 : units.size();

        double[] sum = new double[AXES];
        int count = 0;
        if (units != null) {
            for (Entity entity : units) {
                for (int a = 0; a < AXES; a++) {
                    sum[a] += axisValue(entity, a);
                }
                count++;
            }
        }
        if (count == 0) {
            java.util.Arrays.fill(forceRaw, 0);
            java.util.Arrays.fill(forceValues, 0);
        } else {
            for (int a = 0; a < AXES; a++) {
                // Mobility/Range average (they do not sum meaningfully); the rest sum.
                double raw = ((a == 3) || (a == 4)) ? sum[a] / count : sum[a];
                forceRaw[a] = raw;
                forceValues[a] = clamp(raw / FORCE_MAX[a]);
            }
        }
        repaint();
    }

    /** Overlays a single focused unit's own profile (null to clear the overlay). */
    public void setHighlightedUnit(Entity unit) {
        hasUnit = (unit != null);
        if (hasUnit) {
            for (int a = 0; a < AXES; a++) {
                unitRaw[a] = axisValue(unit, a);
                unitValues[a] = clamp(unitRaw[a] / UNIT_MAX[a]);
            }
        }
        repaint();
    }

    /** @return the raw Total Warfare contribution of one unit to the given axis (see {@link #AXIS_LABELS}). */
    public static double axisValue(Entity entity, int axis) {
        return switch (axis) {
            case 0 -> unitDamage(entity);
            case 1 -> Math.max(0, entity.getTotalOArmor());
            case 2 -> Math.max(0, entity.getTotalOInternal());
            case 3 -> Math.max(0, entity.getWalkMP());
            case 4 -> unitLongestRange(entity);
            case 5 -> unitHeatCapacity(entity);
            default -> 0;
        };
    }

    private static double unitDamage(Entity entity) {
        double damage = 0;
        for (WeaponMounted mounted : entity.getWeaponList()) {
            double weaponDamage = mounted.getType().getDamage();
            if (weaponDamage < 0) {
                weaponDamage = mounted.getType().getRackSize();
            }
            if (weaponDamage > 0) {
                damage += weaponDamage;
            }
        }
        return damage;
    }

    private static double unitLongestRange(Entity entity) {
        int longest = 0;
        for (WeaponMounted mounted : entity.getWeaponList()) {
            longest = Math.max(longest, mounted.getType().getLongRange());
        }
        return longest;
    }

    private static double unitHeatCapacity(Entity entity) {
        int heatCapacity = entity.getHeatCapacity();
        return ((heatCapacity > 0) && (heatCapacity != Entity.DOES_NOT_TRACK_HEAT)) ? heatCapacity : 0;
    }

    private static double clamp(double v) {
        return Math.max(0, Math.min(1, v));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // No background fill: let MegaMek's standard dark gray show through; the grid/text are white.
        Font baseFont = getFont();
        g2.setColor(TEXT);

        // Header: Force name (bold) + Formation name — enlarged, with a clear gap before the chart.
        int headerBottom = scaleForGUI(8);
        Font nameFont = baseFont.deriveFont(Font.BOLD, (float) scaleForGUI(16));
        Font formFont = baseFont.deriveFont(Font.ITALIC, (float) scaleForGUI(13));
        headerBottom = drawCentered(g2, forceName.isEmpty() ? "" : forceName, nameFont, w, headerBottom);
        headerBottom = drawCentered(g2, formationName, formFont, w, headerBottom);
        headerBottom += scaleForGUI(14);

        Font labelFont = baseFont.deriveFont((float) scaleForGUI(11));
        g2.setFont(labelFont);
        FontMetrics fm = g2.getFontMetrics();

        int chartTop = headerBottom;
        int cx = w / 2;
        int cy = chartTop + (h - chartTop) / 2;
        // Shrunk ~25% from before so the enlarged header no longer overlaps the hexagon / axis labels.
        int radius = (int) (Math.min(w, h - chartTop) * 0.28);

        // Grid rings + spokes.
        g2.setStroke(new BasicStroke(1f));
        for (int ring = 1; ring <= 4; ring++) {
            g2.setColor(GRID);
            g2.draw(polygon(cx, cy, radius * (ring / 4.0)));
        }
        for (int i = 0; i < AXES; i++) {
            double angle = angleFor(i);
            g2.setColor(GRID);
            g2.drawLine(cx, cy, cx + (int) (Math.cos(angle) * radius), cy + (int) (Math.sin(angle) * radius));
        }

        if (forceUnitCount == 0) {
            g2.setColor(TEXT);
            String msg = "Select a force";
            g2.drawString(msg, cx - fm.stringWidth(msg) / 2, cy);
            g2.dispose();
            return;
        }

        // Force polygon (gold).
        Path2D forcePath = dataPolygon(cx, cy, radius, forceValues);
        g2.setColor(alpha(FORCE_COLOR, 70));
        g2.fill(forcePath);
        g2.setColor(FORCE_COLOR);
        g2.setStroke(new BasicStroke((float) scaleForGUI(2)));
        g2.draw(forcePath);

        // Focused unit overlay (blue dashed).
        if (hasUnit) {
            Path2D unitPath = dataPolygon(cx, cy, radius, unitValues);
            g2.setColor(UNIT_COLOR);
            g2.setStroke(new BasicStroke((float) scaleForGUI(2), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f,
                  new float[] { scaleForGUI(4), scaleForGUI(4) }, 0f));
            g2.draw(unitPath);
        }

        drawAxisLabels(g2, cx, cy, radius, fm);
        g2.dispose();
    }

    private void drawAxisLabels(Graphics2D g2, int cx, int cy, int radius, FontMetrics fm) {
        int labelRadius = radius + scaleForGUI(30);
        for (int i = 0; i < AXES; i++) {
            double angle = angleFor(i);
            int x = cx + (int) (Math.cos(angle) * labelRadius);
            int y = cy + (int) (Math.sin(angle) * labelRadius);
            String forceText = AXIS_LABELS[i] + " " + formatValue(i, forceRaw[i]);
            String unitText = hasUnit ? formatValue(i, unitRaw[i]) : null;
            drawLabelBox(g2, x, y, forceText, unitText, fm);
        }
    }

    /** Draws an axis label centered at (x,y) inside a bordered, semi-transparent box for readability. */
    private void drawLabelBox(Graphics2D g2, int x, int y, String line1, String line2, FontMetrics fm) {
        int lineHeight = fm.getHeight();
        int width = Math.max(fm.stringWidth(line1), (line2 != null) ? fm.stringWidth(line2) : 0) + scaleForGUI(8);
        int lines = (line2 != null) ? 2 : 1;
        int height = lineHeight * lines + scaleForGUI(4);
        int boxX = x - width / 2;
        int boxY = y - height / 2;
        int arc = scaleForGUI(6);

        g2.setColor(new Color(0, 0, 0, 155));
        g2.fillRoundRect(boxX, boxY, width, height, arc, arc);
        g2.setColor(GRID);
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(boxX, boxY, width, height, arc, arc);

        int textY = boxY + fm.getAscent() + scaleForGUI(2);
        g2.setColor(TEXT);
        g2.drawString(line1, x - fm.stringWidth(line1) / 2, textY);
        if (line2 != null) {
            g2.setColor(UNIT_COLOR);
            g2.drawString(line2, x - fm.stringWidth(line2) / 2, textY + lineHeight);
        }
    }

    private static String formatValue(int axis, double raw) {
        // Mobility and Range are averages -> one decimal; totals -> integer.
        return ((axis == 3) || (axis == 4)) ? String.format("%.1f", raw) : String.valueOf(Math.round(raw));
    }

    /** Draws a string centered horizontally at the given top y; returns the y below it. */
    private int drawCentered(Graphics2D g2, String text, Font font, int width, int top) {
        if (text.isEmpty()) {
            return top;
        }
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(TEXT);
        g2.drawString(text, (width - fm.stringWidth(text)) / 2, top + fm.getAscent());
        return top + fm.getHeight();
    }

    private static Path2D dataPolygon(int cx, int cy, int radius, double[] values) {
        Path2D path = new Path2D.Double();
        for (int i = 0; i < AXES; i++) {
            double angle = angleFor(i);
            double r = radius * values[i];
            double x = cx + Math.cos(angle) * r;
            double y = cy + Math.sin(angle) * r;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.closePath();
        return path;
    }

    /** @return the angle (radians) of axis i, starting at the top and going clockwise. */
    private static double angleFor(int i) {
        return -Math.PI / 2 + (2 * Math.PI * i / AXES);
    }

    private static Path2D polygon(int cx, int cy, double r) {
        Path2D path = new Path2D.Double();
        for (int i = 0; i < AXES; i++) {
            double angle = angleFor(i);
            double x = cx + Math.cos(angle) * r;
            double y = cy + Math.sin(angle) * r;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.closePath();
        return path;
    }

    private static Color alpha(Color color, int a) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), a);
    }
}
