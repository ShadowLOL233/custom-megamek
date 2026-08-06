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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import megamek.common.enums.SkillLevel;
import megamek.common.units.Entity;
import megamek.common.units.EntityMovementMode;
import megamek.common.units.UnitType;

/**
 * Stateless Java2D renderer for SLDF / NATO-style military map symbols — the mid- and far-zoom glyphs of the
 * semantic-zoom Force View. Symbols are drawn procedurally (no PNG assets) into caller-supplied bounds so the
 * zoomable TO&E canvas can scale them freely. The symbology copies the standard SLDF Warfare Symbology map
 * convention rather than inventing glyphs: BattleMeks get a 'Mech silhouette (the one BattleTech-specific glyph);
 * every other unit uses its plain NATO branch symbol (armour pill, wheeled/hover variants, infantry X, aerospace
 * star, anchor, …).
 *
 * <p>A unit symbol is a beveled frame with the branch glyph in the <b>left</b> zone and a large <b>weight-class
 * letter</b> (L/M/H/A) in the <b>right</b> zone — they never overlap — plus a small <b>experience letter</b>
 * (G/R/V/E/…) at the bottom-left. The frame fill is the owning player's colour (affiliation is MekHQ's job) and the
 * frame border is the crew's experience colour. A formation symbol instead centres the dominant-branch glyph and
 * carries <b>echelon marks</b> (dots / bars / crosses for Lance…Army) above the frame.
 *
 * <p>The primitive {@code paintUnit}/{@code paintFormation} overloads take plain enums + a colour and touch no game
 * state, so a standalone legend can exercise every variant; the {@link Entity} overload classifies a live unit and
 * delegates to them.
 */
public final class SldfSymbol {

    /** Branch families, each with a distinct glyph ('Mech is the sole BattleTech-specific one). */
    public enum Branch {
        MEK, VEHICLE_TRACKED, VEHICLE_WHEELED, VEHICLE_HOVER, VTOL, AERO, CONV_FIGHTER, DROPSHIP, INFANTRY,
        BATTLE_ARMOR, PROTOMEK, NAVAL, OTHER
    }

    /** The 8-level command echelon ladder (Lance→Army) and how many marks / what mark style each uses. */
    public enum Echelon {
        LANCE("Lance", MarkStyle.DOT, 3),
        COMPANY("Company", MarkStyle.BAR, 1),
        BATTALION("Battalion", MarkStyle.BAR, 2),
        REGIMENT("Regiment", MarkStyle.BAR, 3),
        BRIGADE("Brigade", MarkStyle.CROSS, 1),
        DIVISION("Division", MarkStyle.CROSS, 2),
        CORPS("Corps", MarkStyle.CROSS, 3),
        ARMY("Army", MarkStyle.CROSS, 4);

        private final String label;
        private final MarkStyle markStyle;
        private final int markCount;

        Echelon(String label, MarkStyle markStyle, int markCount) {
            this.label = label;
            this.markStyle = markStyle;
            this.markCount = markCount;
        }

        public String label() {
            return label;
        }
    }

    private enum MarkStyle { DOT, BAR, CROSS }

    // Experience ring colours (per the user's scheme): Green green, Regular blue, Veteran purple, Elite yellow,
    // Legendary gold; Ultra-Green a muted grey and Heroic an orange bridge between Elite and Legendary.
    private static final Color RING_ULTRA_GREEN = new Color(150, 150, 150);
    private static final Color RING_GREEN = new Color(76, 175, 80);
    private static final Color RING_REGULAR = new Color(33, 150, 243);
    private static final Color RING_VETERAN = new Color(156, 39, 176);
    private static final Color RING_ELITE = new Color(255, 235, 59);
    private static final Color RING_HEROIC = new Color(255, 152, 0);
    private static final Color RING_LEGENDARY = new Color(255, 179, 0);

    // Neutral command-grey border for formation symbols (a formation has no single crew skill).
    private static final Color RING_NEUTRAL = new Color(205, 205, 215);

    private static final Color MARK = new Color(30, 30, 34);
    private static final Color HALO = new Color(255, 255, 255, 210);

    private SldfSymbol() { }

    // ------------------------------------------------------------------------------------------------------------
    // Classification (Entity / count -> symbol attributes)
    // ------------------------------------------------------------------------------------------------------------

    /** @return the drawing branch for an entity's unit type (tanks split by movement mode). */
    public static Branch branchOf(Entity entity) {
        return switch (entity.getUnitType()) {
            case UnitType.MEK -> Branch.MEK;
            case UnitType.PROTOMEK -> Branch.PROTOMEK;
            case UnitType.TANK -> vehicleBranch(entity.getMovementMode());
            case UnitType.VTOL -> Branch.VTOL;
            case UnitType.NAVAL -> Branch.NAVAL;
            case UnitType.INFANTRY -> Branch.INFANTRY;
            case UnitType.BATTLE_ARMOR -> Branch.BATTLE_ARMOR;
            case UnitType.CONV_FIGHTER -> Branch.CONV_FIGHTER;
            case UnitType.DROPSHIP -> Branch.DROPSHIP;
            case UnitType.AEROSPACE_FIGHTER, UnitType.SMALL_CRAFT, UnitType.JUMPSHIP, UnitType.WARSHIP,
                 UnitType.SPACE_STATION, UnitType.AERO -> Branch.AERO;
            default -> Branch.OTHER;
        };
    }

    private static Branch vehicleBranch(EntityMovementMode mode) {
        return switch (mode) {
            case WHEELED -> Branch.VEHICLE_WHEELED;
            case HOVER, WIGE -> Branch.VEHICLE_HOVER;
            case NAVAL, HYDROFOIL, SUBMARINE -> Branch.NAVAL;
            case VTOL -> Branch.VTOL;
            default -> Branch.VEHICLE_TRACKED;
        };
    }

    /** @return the entity's weight class as 0..3 (Light/Medium/Heavy/Assault), clamped. */
    public static int weightClassOf(Entity entity) {
        return Math.max(0, Math.min(3, entity.getWeightClass()));
    }

    /** @return the crew's experience tier, derived from average gunnery (lower skill number = more experienced). */
    public static SkillLevel skillOf(Entity entity) {
        int gunnery = (entity.getCrew() != null) ? entity.getCrew().getGunnery() : 4;
        if (gunnery <= 0) {
            return SkillLevel.LEGENDARY;
        } else if (gunnery == 1) {
            return SkillLevel.HEROIC;
        } else if (gunnery == 2) {
            return SkillLevel.ELITE;
        } else if (gunnery == 3) {
            return SkillLevel.VETERAN;
        } else if (gunnery == 4) {
            return SkillLevel.REGULAR;
        } else if (gunnery == 5) {
            return SkillLevel.GREEN;
        }
        return SkillLevel.ULTRA_GREEN;
    }

    /** @return the command echelon for a (recursive) unit count, on a roughly-triangular org (×3 per tier). */
    public static Echelon echelonForCount(int count) {
        if (count <= 6) {
            return Echelon.LANCE;
        } else if (count <= 12) {
            return Echelon.COMPANY;
        } else if (count <= 36) {
            return Echelon.BATTALION;
        } else if (count <= 108) {
            return Echelon.REGIMENT;
        } else if (count <= 324) {
            return Echelon.BRIGADE;
        } else if (count <= 972) {
            return Echelon.DIVISION;
        } else if (count <= 2916) {
            return Echelon.CORPS;
        }
        return Echelon.ARMY;
    }

    /** @return the experience-ring colour for a skill tier. */
    public static Color skillColor(SkillLevel skill) {
        return switch (skill) {
            case GREEN -> RING_GREEN;
            case REGULAR -> RING_REGULAR;
            case VETERAN -> RING_VETERAN;
            case ELITE -> RING_ELITE;
            case HEROIC -> RING_HEROIC;
            case LEGENDARY -> RING_LEGENDARY;
            default -> RING_ULTRA_GREEN;
        };
    }

    private static String skillLetter(SkillLevel skill) {
        return switch (skill) {
            case GREEN -> "G";
            case REGULAR -> "R";
            case VETERAN -> "V";
            case ELITE -> "E";
            case HEROIC -> "H";
            case LEGENDARY -> "L";
            default -> "U";
        };
    }

    // ------------------------------------------------------------------------------------------------------------
    // Public painters
    // ------------------------------------------------------------------------------------------------------------

    /** Paints a single live unit's symbol (no echelon marks) within the bounds. */
    public static void paintUnit(Graphics2D g, int x, int y, int w, int h, Entity entity, Color playerColor) {
        paintUnit(g, x, y, w, h, branchOf(entity), weightClassOf(entity), skillOf(entity), playerColor);
    }

    /** Paints a single unit's symbol from primitive attributes (used by both the live path and the legend). */
    public static void paintUnit(Graphics2D g, int x, int y, int w, int h,
          Branch branch, int weightClass, SkillLevel skill, Color playerColor) {
        paint(g, x, y, w, h, branch, weightClass, playerColor, skillColor(skill), skillLetter(skill), null);
    }

    /**
     * Paints a whole formation collapsed to one echelon symbol: the centred dominant-branch glyph plus the echelon
     * marks above it. The border is a neutral command grey and no weight/experience letters are shown.
     */
    public static void paintFormation(Graphics2D g, int x, int y, int w, int h,
          Echelon echelon, Branch dominantBranch, int avgWeightClass, Color playerColor) {
        paint(g, x, y, w, h, dominantBranch, avgWeightClass, playerColor, RING_NEUTRAL, null, echelon);
    }

    // ------------------------------------------------------------------------------------------------------------
    // Core painter
    // ------------------------------------------------------------------------------------------------------------

    private static void paint(Graphics2D g, int x, int y, int w, int h,
          Branch branch, int weightClass, Color playerColor, Color ringColor, String expLetter, Echelon echelon) {
        Object oldAA = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        Stroke oldStroke = g.getStroke();
        Font oldFont = g.getFont();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Layout: reserve a top band for echelon marks; the frame is a landscape rectangle below it.
        double margin = Math.max(2.0, Math.min(w, h) * 0.08);
        double topBand = h * 0.20;
        double fx = x + margin;
        double fy = y + topBand;
        double fw = w - 2 * margin;
        double fh = h - topBand - margin;
        double corner = Math.min(fw, fh) * 0.16;
        double cut = Math.min(fw, fh) * 0.34;

        Color fill = (playerColor != null) ? playerColor : new Color(90, 90, 100);
        Color ink = contrastInk(fill);
        float border = (float) Math.max(1.5, fh * 0.055);

        // Frame: rounded rectangle with a beveled top-right corner (the SLDF unit-frame look).
        Shape frame = frameShape(fx, fy, fw, fh, corner);
        g.setColor(fill);
        g.fill(frame);
        g.setColor(ringColor);
        g.setStroke(new BasicStroke(border, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(frame);

        boolean unit = (echelon == null);
        // Tall glyphs (the 'Mech silhouette) get Sven's mek-tile layout (glyph left, big weight letter right); every
        // other glyph gets Sven's armour-tile layout (glyph fills the interior, small weight letter tucked top-right).
        boolean tall = (branch == Branch.MEK) || (branch == Branch.PROTOMEK);
        g.setColor(ink);

        if (!unit) {
            double side = Math.min(fw * 0.60, fh * 0.84);
            double gx = fx + (fw - side) / 2.0;
            double gy = fy + (fh - side) / 2.0;
            g.setStroke(glyphStroke(side));
            drawBranch(g, branch, gx, gy, side, side);
            drawEchelon(g, echelon, x + w / 2.0, y + topBand * 0.5, w * 0.7, topBand * 0.7);
        } else if (tall) {
            double side = Math.min(fw * 0.50, fh * 0.86);
            double gx = fx + fw * 0.06;
            double gy = fy + (fh - side) / 2.0;
            g.setStroke(glyphStroke(side));
            drawBranch(g, branch, gx, gy, side, side);
            double wzX = gx + side;
            double wzW = fx + fw - wzX - cut * 0.35;
            drawWeightLetter(g, weightClass, wzX, fy, wzW, fh, ink);
            drawExpLetter(g, expLetter, fx + fw * 0.05, fy + fh - fh * 0.05, fh * 0.22, ink);
        } else {
            double gx = fx + fw * 0.04;
            double gy = fy + fh * 0.06;
            double gw = fw * 0.92;
            double gh = fh * 0.88;
            g.setStroke(glyphStroke(gh));
            drawBranch(g, branch, gx, gy, gw, gh);
            drawWeightCorner(g, weightClass, fx, fy, fw, fh, cut, ink);
            drawExpLetter(g, expLetter, fx + fw * 0.05, fy + fh - fh * 0.05, fh * 0.22, ink);
        }

        g.setStroke(oldStroke);
        g.setFont(oldFont);
        if (oldAA != null) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAA);
        }
    }

    /** @return the SLDF unit-frame outline: a rounded rectangle with four equal rounded corners. */
    private static Shape frameShape(double x, double y, double w, double h, double r) {
        return new RoundRectangle2D.Double(x, y, w, h, r * 2, r * 2);
    }

    // ------------------------------------------------------------------------------------------------------------
    // Branch glyphs (drawn within a square icon box x,y,w,h; the caller has set colour + stroke)
    // ------------------------------------------------------------------------------------------------------------

    private static void drawBranch(Graphics2D g, Branch branch, double x, double y, double w, double h) {
        // 'Mech and the wide armour glyphs use the whole (possibly landscape) box; the rest centre in a square so
        // they never stretch when the box is wide.
        switch (branch) {
            case MEK -> drawMek(g, x, y, w, h);
            case PROTOMEK -> drawMek(g, x + w * 0.12, y, w * 0.76, h);
            case VEHICLE_TRACKED -> drawTracked(g, x, y, w, h);
            case VEHICLE_WHEELED -> drawWheeled(g, x, y, w, h);
            case VEHICLE_HOVER -> drawHover(g, x, y, w, h);
            default -> {
                double s = Math.min(w, h);
                double sx = x + (w - s) / 2.0;
                double sy = y + (h - s) / 2.0;
                switch (branch) {
                    case VTOL -> drawVtol(g, sx, sy, s, s);
                    case AERO -> drawAero(g, sx, sy, s, s);
                    case CONV_FIGHTER -> drawConvFighter(g, sx, sy, s, s);
                    case DROPSHIP -> drawDropship(g, sx, sy, s, s);
                    case INFANTRY -> {
                        g.draw(new Line2D.Double(sx, sy + s * 0.08, sx + s, sy + s * 0.92));
                        g.draw(new Line2D.Double(sx + s, sy + s * 0.08, sx, sy + s * 0.92));
                    }
                    case BATTLE_ARMOR -> drawBattleArmor(g, sx, sy, s, s);
                    case NAVAL -> drawAnchor(g, sx, sy, s, s);
                    default -> g.draw(new Rectangle2D.Double(sx + s * 0.3, sy + s * 0.3, s * 0.4, s * 0.4));
                }
            }
        }
    }

    /** The MegaMekLab record-sheet BattleMech silhouette, filled solid and fitted to the box. */
    private static void drawMek(Graphics2D g, double x, double y, double w, double h) {
        fillSilhouette(g, x, y, w, h, MekSilhouetteData.WIDTH, MekSilhouetteData.HEIGHT, MekSilhouetteData.SUBPATHS);
    }

    /** The MegaMekLab record-sheet aerospace-fighter silhouette, filled solid and fitted to the box. */
    private static void drawAero(Graphics2D g, double x, double y, double w, double h) {
        fillSilhouette(g, x, y, w, h, AeroSilhouetteData.WIDTH, AeroSilhouetteData.HEIGHT, AeroSilhouetteData.SUBPATHS);
    }

    /** The MegaMekLab record-sheet conventional-fighter silhouette, filled solid and fitted to the box. */
    private static void drawConvFighter(Graphics2D g, double x, double y, double w, double h) {
        fillSilhouette(g, x, y, w, h, ConvFighterSilhouetteData.WIDTH, ConvFighterSilhouetteData.HEIGHT,
              ConvFighterSilhouetteData.SUBPATHS);
    }

    /** The MegaMekLab record-sheet aerodyne-dropship silhouette, filled solid and fitted to the box. */
    private static void drawDropship(Graphics2D g, double x, double y, double w, double h) {
        fillSilhouette(g, x, y, w, h, DropshipSilhouetteData.WIDTH, DropshipSilhouetteData.HEIGHT,
              DropshipSilhouetteData.SUBPATHS);
    }

    /** The Battle Armor trooper silhouette (traced from the Shrapnel illustration), filled solid, fitted to box. */
    private static void drawBattleArmor(Graphics2D g, double x, double y, double w, double h) {
        fillSilhouette(g, x, y, w, h, BattleArmorSilhouetteData.WIDTH, BattleArmorSilhouetteData.HEIGHT,
              BattleArmorSilhouetteData.SUBPATHS);
    }

    private static void fillSilhouette(Graphics2D g, double x, double y, double w, double h,
          int srcW, int srcH, int[][] subpaths) {
        double scale = Math.min(w / srcW, h / srcH);
        double ox = x + (w - srcW * scale) / 2.0;
        double oy = y + (h - srcH * scale) / 2.0;
        for (int[] sp : subpaths) {
            Path2D path = new Path2D.Double();
            path.moveTo(ox + sp[0] * scale, oy + sp[1] * scale);
            for (int i = 2; i < sp.length; i += 2) {
                path.lineTo(ox + sp[i] * scale, oy + sp[i + 1] * scale);
            }
            path.closePath();
            g.fill(path);
        }
    }

    /** Tracked armour: a horizontal stadium (rounded-rectangle capsule) outline, per Sven's reference tile. */
    private static void drawTracked(Graphics2D g, double x, double y, double w, double h) {
        double ph = h * 0.60;
        g.draw(new RoundRectangle2D.Double(x + w * 0.09, y + h * 0.20, w * 0.82, ph, ph, ph));
    }

    /**
     * Wheeled armour: a horizontal bar with three evenly-spaced filled wheels hanging just below it. Proportions are
     * measured from Sven's reference tile (line ~34% height spanning ~10-84% width; wheels centred at ~26/50/74%
     * width, diameter ~15% width, touching the bar).
     */
    private static void drawWheeled(Graphics2D g, double x, double y, double w, double h) {
        double ly = y + h * 0.34;
        g.draw(new Line2D.Double(x + w * 0.10, ly, x + w * 0.84, ly));
        double r = w * 0.075;
        double cy = ly + r;
        double[] positions = { 0.26, 0.50, 0.74 };
        for (double f : positions) {
            double cx = x + w * f;
            g.fill(new Ellipse2D.Double(cx - r, cy - r, 2 * r, 2 * r));
        }
    }

    /** Hover armour: a horizontal bar with seven evenly-spaced vertical skirt teeth below it (Sven proportions). */
    private static void drawHover(Graphics2D g, double x, double y, double w, double h) {
        double ly = y + h * 0.34;
        g.draw(new Line2D.Double(x + w * 0.09, ly, x + w * 0.91, ly));
        int teeth = 7;
        double bottom = y + h * 0.67;
        for (int i = 0; i < teeth; i++) {
            double tx = x + w * (0.10 + i * (0.795 / (teeth - 1)));
            g.draw(new Line2D.Double(tx, ly, tx, bottom));
        }
    }

    /** Rotary-wing: a wide rotor disc over a short fuselage. */
    private static void drawVtol(Graphics2D g, double x, double y, double w, double h) {
        g.draw(new Line2D.Double(x, y + h * 0.18, x + w, y + h * 0.18));
        g.draw(new Line2D.Double(x + w / 2.0, y + h * 0.18, x + w / 2.0, y + h * 0.36));
        g.draw(new Ellipse2D.Double(x + w * 0.22, y + h * 0.36, w * 0.56, h * 0.48));
    }

    /** NATO naval: an anchor (ring, shaft, stock crossbar and curved flukes). */
    private static void drawAnchor(Graphics2D g, double x, double y, double w, double h) {
        double cx = x + w / 2.0;
        double ringR = Math.min(w, h) * 0.11;
        double top = y + h * 0.06;
        g.draw(new Ellipse2D.Double(cx - ringR, top, ringR * 2, ringR * 2));
        g.draw(new Line2D.Double(cx, top + ringR * 2, cx, y + h * 0.88));
        g.draw(new Line2D.Double(cx - w * 0.22, y + h * 0.34, cx + w * 0.22, y + h * 0.34));
        g.draw(new Arc2D.Double(cx - w * 0.32, y + h * 0.48, w * 0.64, h * 0.48, 205, 130, Arc2D.OPEN));
        g.draw(new Line2D.Double(cx - w * 0.31, y + h * 0.60, cx - w * 0.31, y + h * 0.74));
        g.draw(new Line2D.Double(cx + w * 0.31, y + h * 0.60, cx + w * 0.31, y + h * 0.74));
    }

    // ------------------------------------------------------------------------------------------------------------
    // Weight / experience letters + echelon marks
    // ------------------------------------------------------------------------------------------------------------

    private static final String[] WEIGHT_LETTERS = { "L", "M", "H", "A" };

    private static BasicStroke glyphStroke(double dim) {
        return new BasicStroke((float) Math.max(1.4, dim * 0.08), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    }

    /** Large weight-class letter centred in a dedicated right-hand zone (tall-glyph / Mek layout). */
    private static void drawWeightLetter(Graphics2D g, int weightClass, double zx, double fy, double zw, double fh,
          Color ink) {
        String s = WEIGHT_LETTERS[Math.max(0, Math.min(3, weightClass))];
        g.setFont(g.getFont().deriveFont(Font.BOLD, (float) (fh * 0.52)));
        FontMetrics fm = g.getFontMetrics();
        double bx = zx + (zw - fm.stringWidth(s)) / 2.0;
        double by = fy + (fh + fm.getAscent() - fm.getDescent()) / 2.0;
        g.setColor(ink);
        g.drawString(s, (float) bx, (float) by);
    }

    /** Small weight-class letter tucked into the frame's top-right corner (wide-glyph / armour layout). */
    private static void drawWeightCorner(Graphics2D g, int weightClass, double fx, double fy, double fw, double fh,
          double cut, Color ink) {
        String s = WEIGHT_LETTERS[Math.max(0, Math.min(3, weightClass))];
        g.setFont(g.getFont().deriveFont(Font.BOLD, (float) (fh * 0.22)));
        FontMetrics fm = g.getFontMetrics();
        double bx = fx + fw - fm.stringWidth(s) - Math.max(fw * 0.03, cut * 0.35);
        double by = fy + fm.getAscent() + fh * 0.05;
        g.setColor(ink);
        g.drawString(s, (float) bx, (float) by);
    }

    private static void drawExpLetter(Graphics2D g, String letter, double bx, double baseline, double size, Color ink) {
        if (letter == null) {
            return;
        }
        g.setFont(g.getFont().deriveFont(Font.BOLD, (float) size));
        g.setColor(ink);
        g.drawString(letter, (float) bx, (float) baseline);
    }

    private static void drawEchelon(Graphics2D g, Echelon echelon, double cx, double cy, double maxW, double maxH) {
        int n = echelon.markCount;
        switch (echelon.markStyle) {
            case DOT -> {
                double d = Math.min(maxH * 0.5, maxW / (n * 2.0));
                double gap = d * 0.9;
                double sx = cx - (n * d + (n - 1) * gap) / 2.0;
                for (int i = 0; i < n; i++) {
                    haloFill(g, new Ellipse2D.Double(sx, cy - d / 2.0, d, d));
                    sx += d + gap;
                }
            }
            case BAR -> {
                double bh = maxH;
                double bw = Math.max(2.0, bh * 0.16);
                double gap = bw * 1.4;
                double sx = cx - (n * bw + (n - 1) * gap) / 2.0;
                for (int i = 0; i < n; i++) {
                    haloFill(g, new RoundRectangle2D.Double(sx, cy - bh / 2.0, bw, bh, bw, bw));
                    sx += bw + gap;
                }
            }
            case CROSS -> {
                double s = Math.min(maxH, maxW / (n * 1.6));
                double gap = s * 0.5;
                double sx = cx - (n * s + (n - 1) * gap) / 2.0;
                for (int i = 0; i < n; i++) {
                    haloStroke(g, new Line2D.Double(sx, cy - s / 2.0, sx + s, cy + s / 2.0),
                          new Line2D.Double(sx + s, cy - s / 2.0, sx, cy + s / 2.0), (float) Math.max(1.5, s * 0.16));
                    sx += s + gap;
                }
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // Small helpers
    // ------------------------------------------------------------------------------------------------------------

    private static void haloFill(Graphics2D g, Shape shape) {
        Stroke old = g.getStroke();
        g.setColor(HALO);
        g.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(shape);
        g.setColor(MARK);
        g.fill(shape);
        g.setStroke(old);
    }

    private static void haloStroke(Graphics2D g, Line2D a, Line2D b, float width) {
        g.setColor(HALO);
        g.setStroke(new BasicStroke(width + 3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(a);
        g.draw(b);
        g.setColor(MARK);
        g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(a);
        g.draw(b);
    }

    /** @return black or white, whichever contrasts better with the given fill (perceptual luminance). */
    private static Color contrastInk(Color c) {
        double lum = (0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue()) / 255.0;
        return (lum > 0.55) ? new Color(20, 20, 24) : Color.WHITE;
    }
}
