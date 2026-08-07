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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;

import megamek.client.ui.clientGUI.ClientGUI;
import megamek.client.ui.tileset.EntityImage;
import megamek.client.ui.tileset.MMStaticDirectoryManager;
import megamek.client.ui.util.ScalingPopup;
import megamek.common.Player;
import megamek.common.force.Force;
import megamek.common.force.FormationBonusType;
import megamek.common.game.Game;
import megamek.common.game.InGameObject;
import megamek.common.icons.Camouflage;
import megamek.common.options.OptionsConstants;
import megamek.common.units.Entity;

/**
 * A read-only, pan/zoomable TO&amp;E organization chart for the lobby — step ② of the semantic-zoom Force View. It
 * lays out the {@link Force} hierarchy as a top-down tidy tree (top-level forces plus an "Unassigned" bucket), draws
 * each leaf unit with the procedural {@link SldfSymbol} military frame (real unit icon when zoomed in, compact glyph
 * when zoomed out) and each force as a labelled box, and connects parents to children. The chart has its own
 * {@link AffineTransform} for panning (drag) and zooming (wheel around the cursor) and fits the content on refresh; a
 * shared {@link UnitDetailPanel} on the right shows the currently selected unit's stats (as in the Card View).
 *
 * <p>It renders the same units as the sortable {@link MekTableModel} table and drives that table's selection model on
 * click, so every selection-dependent lobby action keeps working. It applies semantic-zoom level-of-detail: zoomed in,
 * units show their real icons; zoomed out, units become compact {@link SldfSymbol} glyphs; zoomed out further, a whole
 * sub-force collapses to a single {@link SldfSymbol#paintFormation echelon formation symbol}. It does not (yet) support
 * drag-and-drop editing — the existing JTree Force View remains the editing surface.
 */
public class ForceToeView extends JPanel implements Scrollable {

    private final ChatLounge lobby;
    private final MekTableModel model;
    private final JTable table;
    private final ClientGUI clientGui;

    private final Canvas canvas;
    private final UnitDetailPanel detailPanel = new UnitDetailPanel();

    public ForceToeView(ChatLounge lobby, MekTableModel model, JTable table) {
        this.lobby = lobby;
        this.model = model;
        this.table = table;
        this.clientGui = lobby.getClientGUI();

        setLayout(new BorderLayout());
        canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);
        add(detailPanel, BorderLayout.EAST);

        // Repaint highlighting and refresh the detail whenever the shared selection changes from any view.
        table.getSelectionModel().addListSelectionListener(e -> {
            canvas.repaint();
            updateDetail();
        });
        updateDetail();
    }

    /** Rebuilds the chart from the current forces and unit table; refits the view and refreshes the detail. */
    public void refresh() {
        canvas.refresh();
        updateDetail();
    }

    /** Shows the single selected unit in the detail panel (Card-View style); clears it unless exactly one is selected. */
    private void updateDetail() {
        int[] rows = table.getSelectedRows();
        Entity single = null;
        if (rows.length == 1) {
            InGameObject unit = model.getEntityAt(rows[0]);
            if (unit instanceof Entity entity) {
                single = entity;
            }
        }
        detailPanel.setUnit(single, (single != null) && isObscured(single));
    }

    private Game game() {
        return clientGui.getClient().getGame();
    }

    private Player ownerOf(Entity entity) {
        return game().getPlayer(entity.getOwnerId());
    }

    /** Mirrors {@link MekTableModel}'s blind-drop obscuring: enemy units are hidden unless the viewer is a GM. */
    private boolean isObscured(Entity entity) {
        Player local = clientGui.getClient().getLocalPlayer();
        boolean localGM = (local != null) && local.isGameMaster();
        return !localGM && (local != null) && local.isEnemyOf(ownerOf(entity))
              && game().getOptions().booleanOption(OptionsConstants.BASE_BLIND_DROP);
    }

    // Scrollable: fill the viewport so the enclosing JScrollPane never scrolls us; the canvas pans/zooms internally.
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return scaleForGUI(16);
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return scaleForGUI(64);
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return true;
    }

    /** The pan/zoom drawing surface. */
    private class Canvas extends JPanel {

        private static final int UNIT_W = 50;
        private static final int UNIT_H = 50;
        private static final int FORCE_W = 140;
        private static final int FORCE_H = 34;
        private static final int H_GAP = 12;
        private static final int V_GAP = 30;
        private static final int FOREST_GAP = 40;
        private static final int MARGIN = 40;
        private static final int COMMAND_SORT = 1000;  // command lances sort above any detected formation

        private static final double MIN_SCALE = 0.15;
        private static final double MAX_SCALE = 3.0;

        // Below this on-screen unit width (px) draw the compact SLDF glyph; at or above it draw the real unit icon.
        private static final int LOD_ICON_MIN_PX = 44;

        // Below this on-screen subtree width (px) a whole sub-force collapses to one echelon formation symbol — but
        // only once its units are already compact glyphs (never collapse while unit icons are still legible).
        private static final int COLLAPSE_MAX_PX = 140;
        private static final int FORMATION_SYM = 60;

        private final Color bg = new Color(40, 42, 48);
        private final Color edge = new Color(150, 155, 165);
        private final Color forceFill = new Color(58, 62, 72);
        private final Color forceBorder = new Color(120, 126, 140);
        private final Color commandBorder = new Color(214, 176, 74);
        private final Color forceText = new Color(232, 234, 240);
        private final Color forceSubText = new Color(176, 182, 196);
        private final Color obscuredFill = new Color(58, 60, 68);
        // Dark gray unit fill, deliberately lighter than the chart background so units stand out against it.
        private final Color unitBg = new Color(78, 82, 92);
        private final Color hint = new Color(150, 155, 165);

        private final List<Node> roots = new ArrayList<>();
        private final List<Node> drawNodes = new ArrayList<>();
        private final List<Edge> edges = new ArrayList<>();
        private final Map<Integer, Image> iconCache = new HashMap<>();

        private double scale = 1.0;
        private double offsetX = 0;
        private double offsetY = 0;
        private boolean needsFit = true;
        // The zoom at which the collapse partition was last computed; NaN forces a recompute after a rebuild.
        private double lastVisibilityScale = Double.NaN;

        private int lastDragX;
        private int lastDragY;

        Canvas() {
            setBackground(bg);
            setOpaque(true);
            CanvasMouse mouse = new CanvasMouse();
            addMouseListener(mouse);
            addMouseMotionListener(mouse);
            addMouseWheelListener(mouse);
            ToolTipManager.sharedInstance().registerComponent(this);
        }

        void refresh() {
            roots.clear();
            drawNodes.clear();
            edges.clear();
            iconCache.clear();
            lastVisibilityScale = Double.NaN;

            // Map entity id -> table/model row (the lobby table uses no row sorter, so row == model row).
            Map<Integer, Integer> rowByEntityId = new LinkedHashMap<>();
            for (int row = 0; row < model.getRowCount(); row++) {
                InGameObject unit = model.getEntityAt(row);
                if (unit instanceof Entity entity) {
                    rowByEntityId.put(entity.getId(), row);
                }
            }

            for (Force force : game().getForces().getTopLevelForces()) {
                roots.add(buildForceNode(force, rowByEntityId));
            }

            // Units not assigned to any force are grouped under a synthetic "Unassigned" node.
            List<Node> orphans = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : rowByEntityId.entrySet()) {
                Entity entity = game().getEntity(entry.getKey());
                if ((entity != null) && (entity.getForceId() == Force.NO_FORCE)) {
                    orphans.add(new UnitNode(entity, entry.getValue(), isObscured(entity)));
                }
            }
            if (!orphans.isEmpty()) {
                ForceNode unassigned = new ForceNode(
                      megamek.client.ui.Messages.getString("ChatLounge.cardView.unassigned"));
                unassigned.children.addAll(orphans);
                unassigned.echelon = SldfSymbol.echelonForCount(orphans.size());
                unassigned.symbolColor = null;
                computeFormationGlyph(unassigned);
                roots.add(unassigned);
            }

            // Lay out each root tree as a forest, packed left to right.
            double cursor = MARGIN;
            for (Node root : roots) {
                measure(root);
                place(root, cursor, MARGIN);
                cursor += root.subtreeWidth + FOREST_GAP;
            }

            needsFit = true;
            repaint();
        }

        private ForceNode buildForceNode(Force force, Map<Integer, Integer> rowByEntityId) {
            int count = game().getForces().getFullEntities(force).size();
            ForceNode node = new ForceNode(force.getName() + SPACER + MekCardView.echelonName(count));
            for (int subId : force.getSubForces()) {
                Force sub = game().getForces().getForce(subId);
                if (sub != null) {
                    node.children.add(buildForceNode(sub, rowByEntityId));
                }
            }
            for (int entityId : force.getEntities()) {
                Entity entity = game().getEntity(entityId);
                Integer row = rowByEntityId.get(entityId);
                if ((entity != null) && (row != null)) {
                    node.children.add(new UnitNode(entity, row, isObscured(entity)));
                }
            }
            node.echelon = SldfSymbol.echelonForCount(count);
            node.symbolColor = forceColor(force);
            node.isCommand = force.isCommandLance();
            computeFormationGlyph(node);
            node.sortWeight = sortWeightFor(force, node);
            reparentCommandLance(node);
            return node;
        }

        /** Ordering priority: a user-set command lance ranks top, else its detected AS formation, else its tonnage. */
        private int sortWeightFor(Force force, ForceNode node) {
            if (force.isCommandLance()) {
                return COMMAND_SORT;
            }
            FormationBonusType formation = detectFormation(force);
            if (formation != null) {
                return formation.sortWeight();
            }
            return 20 + node.avgWeightClass * 12;   // no detected formation: fall back to average tonnage class
        }

        private FormationBonusType detectFormation(Force force) {
            List<Entity> units = new ArrayList<>();
            for (int id : force.getEntities()) {
                Entity entity = game().getEntity(id);
                if (entity != null) {
                    units.add(entity);
                }
            }
            return FormationBonusType.detect(units);
        }

        /**
         * Display-only: if one of this force's sub-forces is a designated command lance, make it the "mother root" of
         * this formation — the command lance keeps its own units and the force's OTHER sub-forces are re-hung beneath
         * it, while the force's loose direct units stay put. Mirrors the SLDF/IS convention (a battalion's or company's
         * command element leads its subordinate formations). Actual force data is untouched.
         */
        private void reparentCommandLance(ForceNode node) {
            ForceNode command = null;
            for (Node child : node.children) {
                if ((child instanceof ForceNode fc) && fc.isCommand) {
                    command = fc;
                    break;
                }
            }
            if (command == null) {
                return;
            }
            List<Node> siblingForces = new ArrayList<>();
            for (Node child : node.children) {
                if ((child != command) && (child instanceof ForceNode)) {
                    siblingForces.add(child);
                }
            }
            node.children.removeAll(siblingForces);
            command.children.addAll(siblingForces);
        }

        private List<Node> unitChildren(ForceNode f) {
            List<Node> units = new ArrayList<>();
            for (Node c : f.children) {
                if (c instanceof UnitNode) {
                    units.add(c);
                }
            }
            return units;
        }

        /** @return this force's sub-force children ordered by descending sort weight (highest = leftmost). */
        private List<Node> forceChildren(ForceNode f) {
            List<Node> forceKids = new ArrayList<>();
            for (Node c : f.children) {
                if (c instanceof ForceNode) {
                    forceKids.add(c);
                }
            }
            forceKids.sort((a, b) -> Integer.compare(((ForceNode) b).sortWeight, ((ForceNode) a).sortWeight));
            return forceKids;
        }

        private double rowWidth(List<Node> kids) {
            double w = 0;
            for (Node c : kids) {
                w += c.subtreeWidth;
            }
            return w + Math.max(0, kids.size() - 1) * scaleForGUI(H_GAP);
        }

        /**
         * Computes each node's footprint width and box x-offset (boxDX), bottom-up. A force's own units are a
         * horizontal row centred under its box; its sub-forces are a second horizontal row below that. Sets no
         * absolute position.
         */
        private void measure(Node node) {
            if (node instanceof UnitNode) {
                node.w = scaleForGUI(UNIT_W);
                node.h = scaleForGUI(UNIT_H);
                node.subtreeWidth = node.w;
                node.boxDX = 0;
                return;
            }
            ForceNode force = (ForceNode) node;
            node.w = scaleForGUI(FORCE_W);
            node.h = scaleForGUI(FORCE_H);
            List<Node> units = unitChildren(force);
            List<Node> forceKids = forceChildren(force);
            for (Node child : force.children) {
                measure(child);
            }

            double unitsRowW = units.isEmpty() ? 0 : rowWidth(units);
            double forcesRowW = forceKids.isEmpty() ? 0 : rowWidth(forceKids);

            // Both rows are centred under the box; extents relative to the box's left edge = 0.
            double minX = 0;
            double maxX = node.w;
            if (unitsRowW > 0) {
                minX = Math.min(minX, node.w / 2 - unitsRowW / 2);
                maxX = Math.max(maxX, node.w / 2 + unitsRowW / 2);
            }
            if (forcesRowW > 0) {
                minX = Math.min(minX, node.w / 2 - forcesRowW / 2);
                maxX = Math.max(maxX, node.w / 2 + forcesRowW / 2);
            }
            node.boxDX = -minX;
            node.subtreeWidth = maxX - minX;
        }

        /** Places a subtree at absolute coordinates given its footprint top-left, recording connectors. */
        private void place(Node node, double footprintX, double footprintY) {
            node.x = footprintX + node.boxDX;
            node.y = footprintY;
            drawNodes.add(node);
            if (!(node instanceof ForceNode force)) {
                return;
            }

            List<Node> units = unitChildren(force);
            List<Node> forceKids = forceChildren(force);
            double boxCenterX = node.x + node.w / 2;
            double parentBottomY = node.y + node.h;
            double y = parentBottomY + scaleForGUI(V_GAP);

            if (!units.isEmpty()) {
                double cursor = boxCenterX - rowWidth(units) / 2;
                for (Node u : units) {
                    place(u, cursor, y);
                    Rectangle2D fb = SldfSymbol.unitFrameBounds((int) Math.round(u.x), (int) Math.round(u.y),
                          (int) Math.round(u.w), (int) Math.round(u.h));
                    edges.add(new Edge(boxCenterX, parentBottomY, fb.getCenterX(), fb.getMinY(), u));
                    cursor += u.subtreeWidth + scaleForGUI(H_GAP);
                }
                y += scaleForGUI(UNIT_H) + scaleForGUI(V_GAP);
            }

            if (!forceKids.isEmpty()) {
                double cursor = boxCenterX - rowWidth(forceKids) / 2;
                for (Node c : forceKids) {
                    place(c, cursor, y);
                    edges.add(new Edge(boxCenterX, parentBottomY, c.x + c.w / 2, c.y, c));
                    cursor += c.subtreeWidth + scaleForGUI(H_GAP);
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            if (drawNodes.isEmpty()) {
                g2.setColor(hint);
                g2.drawString(megamek.client.ui.Messages.getString("ChatLounge.toeView.empty"),
                      scaleForGUI(20), scaleForGUI(30));
                g2.dispose();
                return;
            }

            if (needsFit) {
                fitToView();
            }
            ensureVisibility();

            AffineTransform view = new AffineTransform();
            view.translate(offsetX, offsetY);
            view.scale(scale, scale);
            AffineTransform old = g2.getTransform();
            g2.transform(view);

            g2.setColor(edge);
            g2.setStroke(new BasicStroke((float) Math.max(1.0, scaleForGUI(1)), BasicStroke.CAP_ROUND,
                  BasicStroke.JOIN_ROUND));
            for (Edge e : edges) {
                if (e.child.hidden) {
                    continue;
                }
                drawElbow(g2, e.px, e.py, e.cx, e.cy);
            }
            for (Node node : drawNodes) {
                if (node.hidden) {
                    continue;
                }
                if (node instanceof UnitNode unit) {
                    paintUnitNode(g2, unit);
                } else if (((ForceNode) node).collapsed) {
                    paintCollapsedForce(g2, (ForceNode) node);
                } else {
                    paintForceNode(g2, (ForceNode) node);
                }
            }

            g2.setTransform(old);
            g2.setColor(hint);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, (float) scaleForGUI(11)));
            g2.drawString(megamek.client.ui.Messages.getString("ChatLounge.toeView.hint"),
                  scaleForGUI(8), getHeight() - scaleForGUI(8));

            paintZoomReadout(g2);
            g2.dispose();
        }

        /**
         * Draws a live zoom indicator in the top-right (screen space): the zoom %, the on-screen unit width in px (the
         * icon⇄glyph LOD flips at {@link #LOD_ICON_MIN_PX}) and the current unit LOD band. A tuning aid so the zoom
         * thresholds can be adjusted and described precisely.
         */
        private void paintZoomReadout(Graphics2D g2) {
            double unitPx = scaleForGUI(UNIT_W) * scale;
            String lod = (unitPx >= LOD_ICON_MIN_PX) ? "icons" : "symbols";
            String text = "Zoom " + Math.round(scale * 100) + "%    " + Math.round(unitPx) + "px/unit    " + lod;

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, (float) scaleForGUI(11)));
            FontMetrics fm = g2.getFontMetrics();
            int padX = scaleForGUI(8);
            int padY = scaleForGUI(4);
            int boxW = fm.stringWidth(text) + 2 * padX;
            int boxH = fm.getAscent() + fm.getDescent() + 2 * padY;
            int bx = getWidth() - boxW - scaleForGUI(8);
            int by = scaleForGUI(8);

            g2.setColor(new Color(28, 30, 36, 215));
            g2.fillRoundRect(bx, by, boxW, boxH, scaleForGUI(8), scaleForGUI(8));
            g2.setColor(forceBorder);
            g2.drawRoundRect(bx, by, boxW, boxH, scaleForGUI(8), scaleForGUI(8));
            g2.setColor(forceText);
            g2.drawString(text, bx + padX, by + padY + fm.getAscent());
        }

        /** Draws a parent→child connector as an orthogonal elbow (down, across, down). */
        private void drawElbow(Graphics2D g2, double x1, double y1, double x2, double y2) {
            double midY = (y1 + y2) / 2.0;
            g2.draw(new Line2D.Double(x1, y1, x1, midY));
            g2.draw(new Line2D.Double(x1, midY, x2, midY));
            g2.draw(new Line2D.Double(x2, midY, x2, y2));
        }

        private void paintForceNode(Graphics2D g2, ForceNode node) {
            RoundRectangle2D box = new RoundRectangle2D.Double(node.x, node.y, node.w, node.h,
                  scaleForGUI(10), scaleForGUI(10));
            g2.setColor(forceFill);
            g2.fill(box);
            g2.setColor(node.isCommand ? commandBorder : forceBorder);
            g2.setStroke(new BasicStroke((float) Math.max(1.0, scaleForGUI(node.isCommand ? 2 : 1))));
            g2.draw(box);

            String[] parts = node.label.split(SPACER, 2);
            Font base = g2.getFont();
            g2.setFont(base.deriveFont(Font.BOLD, (float) scaleForGUI(10)));
            drawCentered(g2, parts[0], node.x + node.w / 2.0, node.y + node.h * 0.44, forceText, node.w - scaleForGUI(6));
            if (parts.length > 1) {
                g2.setFont(base.deriveFont(Font.PLAIN, (float) scaleForGUI(8)));
                drawCentered(g2, parts[1], node.x + node.w / 2.0, node.y + node.h * 0.80, forceSubText,
                      node.w - scaleForGUI(6));
            }
        }

        /** Draws a collapsed sub-force as a single centred SLDF echelon symbol with its name beneath it. */
        private void paintCollapsedForce(Graphics2D g2, ForceNode node) {
            double symW = scaleForGUI(FORMATION_SYM);
            double symH = scaleForGUI(FORMATION_SYM);
            double centerX = node.x + node.w / 2.0;
            double sx = centerX - symW / 2.0;
            double sy = node.y;
            SldfSymbol.paintFormation(g2, (int) Math.round(sx), (int) Math.round(sy),
                  (int) Math.round(symW), (int) Math.round(symH),
                  node.echelon, node.dominantBranch, node.avgWeightClass, node.symbolColor);

            String name = node.label.split(SPACER, 2)[0];
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, (float) scaleForGUI(10)));
            drawCentered(g2, name, centerX, sy + symH + scaleForGUI(11), forceText,
                  Math.min(node.subtreeWidth, scaleForGUI(170)));

            // Selection ring when any member unit of the collapsed formation is selected in the shared table.
            if (anyMemberSelected(node)) {
                double pad = scaleForGUI(3);
                g2.setColor(table.getSelectionBackground());
                g2.setStroke(new BasicStroke((float) Math.max(2.0, scaleForGUI(2))));
                g2.draw(new RoundRectangle2D.Double(sx - pad, sy - pad, symW + 2 * pad, symH + 2 * pad,
                      scaleForGUI(10), scaleForGUI(10)));
            }
        }

        private void paintUnitNode(Graphics2D g2, UnitNode node) {
            Rectangle2D fb = SldfSymbol.unitFrameBounds((int) Math.round(node.x), (int) Math.round(node.y),
                  (int) Math.round(node.w), (int) Math.round(node.h));

            if (node.obscured) {
                RoundRectangle2D box = new RoundRectangle2D.Double(fb.getX(), fb.getY(), fb.getWidth(), fb.getHeight(),
                      scaleForGUI(8), scaleForGUI(8));
                g2.setColor(obscuredFill);
                g2.fill(box);
                g2.setColor(forceBorder);
                g2.setStroke(new BasicStroke((float) Math.max(1.0, scaleForGUI(1))));
                g2.draw(box);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, (float) (fb.getHeight() * 0.5)));
                drawCentered(g2, "?", fb.getCenterX(), fb.getCenterY() + fb.getHeight() * 0.16, forceText, fb.getWidth());
            } else if ((node.w * scale) >= LOD_ICON_MIN_PX) {
                // Close ("Lance") zoom: the SLDF unit frame carrying the real Card-View unit icon in the centre.
                Rectangle2D area = SldfSymbol.paintUnitFrame(g2, (int) Math.round(node.x), (int) Math.round(node.y),
                      (int) Math.round(node.w), (int) Math.round(node.h), node.entity, unitBg);
                Image icon = iconFor(node.entity);
                if ((icon != null) && (icon.getWidth(this) > 0) && (icon.getHeight(this) > 0)) {
                    double fit = Math.min(area.getWidth() / icon.getWidth(this), area.getHeight() / icon.getHeight(this));
                    double dw = icon.getWidth(this) * fit;
                    double dh = icon.getHeight(this) * fit;
                    g2.drawImage(icon, (int) Math.round(area.getCenterX() - dw / 2.0),
                          (int) Math.round(area.getCenterY() - dh / 2.0),
                          (int) Math.round(dw), (int) Math.round(dh), this);
                }
            } else {
                // Zoomed out ("Battalion+"): the compact SLDF glyph on a dark-gray frame.
                SldfSymbol.paintUnit(g2, (int) Math.round(node.x), (int) Math.round(node.y),
                      (int) Math.round(node.w), (int) Math.round(node.h), node.entity, unitBg);
            }

            // Selection highlight: a ring hugging the unit frame boundary.
            if (table.isRowSelected(node.row)) {
                double pad = scaleForGUI(2);
                g2.setColor(table.getSelectionBackground());
                g2.setStroke(new BasicStroke((float) Math.max(2.0, scaleForGUI(2))));
                g2.draw(new RoundRectangle2D.Double(fb.getX() - pad, fb.getY() - pad,
                      fb.getWidth() + 2 * pad, fb.getHeight() + 2 * pad, scaleForGUI(10), scaleForGUI(10)));
            }
        }

        /** @return the cached Card-View preview image (unit tile + camo) for an entity, or null if unavailable. */
        private Image iconFor(Entity entity) {
            return iconCache.computeIfAbsent(entity.getId(), id -> {
                try {
                    Camouflage camouflage = entity.getCamouflageOrElseOwners();
                    Image base = MMStaticDirectoryManager.getMekTileset().imageFor(entity);
                    return new EntityImage(base, camouflage, this, entity).loadPreviewImage(true);
                } catch (Exception ex) {
                    return null;
                }
            });
        }

        private void drawCentered(Graphics2D g2, String text, double cx, double baselineY, Color color,
              double maxWidth) {
            FontMetrics fm = g2.getFontMetrics();
            String shown = clip(text, fm, maxWidth);
            g2.setColor(color);
            g2.drawString(shown, (float) (cx - fm.stringWidth(shown) / 2.0), (float) baselineY);
        }

        private String clip(String text, FontMetrics fm, double maxWidth) {
            if (fm.stringWidth(text) <= maxWidth) {
                return text;
            }
            String ellipsis = "…";
            StringBuilder sb = new StringBuilder(text);
            while ((sb.length() > 1) && (fm.stringWidth(sb + ellipsis) > maxWidth)) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb + ellipsis;
        }

        private void fitToView() {
            needsFit = false;
            if (drawNodes.isEmpty() || (getWidth() <= 0) || (getHeight() <= 0)) {
                return;
            }
            double minX = Double.MAX_VALUE;
            double minY = Double.MAX_VALUE;
            double maxX = -Double.MAX_VALUE;
            double maxY = -Double.MAX_VALUE;
            for (Node node : drawNodes) {
                minX = Math.min(minX, node.x);
                minY = Math.min(minY, node.y);
                maxX = Math.max(maxX, node.x + node.w);
                maxY = Math.max(maxY, node.y + node.h);
            }
            double contentW = (maxX - minX) + 2 * MARGIN;
            double contentH = (maxY - minY) + 2 * MARGIN;
            double fit = Math.min(getWidth() / contentW, getHeight() / contentH);
            scale = Math.max(MIN_SCALE, Math.min(1.2, fit));
            offsetX = (getWidth() - (maxX - minX) * scale) / 2.0 - minX * scale;
            offsetY = (getHeight() - (maxY - minY) * scale) / 2.0 - minY * scale;
        }

        private Node nodeAt(int screenX, int screenY) {
            Point2D world;
            try {
                AffineTransform view = new AffineTransform();
                view.translate(offsetX, offsetY);
                view.scale(scale, scale);
                world = view.inverseTransform(new Point2D.Double(screenX, screenY), null);
            } catch (NoninvertibleTransformException ex) {
                return null;
            }
            ensureVisibility();
            // Prefer unit nodes (drawn on top) then force nodes; never hit nodes hidden inside a collapsed force.
            for (int pass = 0; pass < 2; pass++) {
                for (Node node : drawNodes) {
                    if (node.hidden) {
                        continue;
                    }
                    boolean isUnit = node instanceof UnitNode;
                    if ((pass == 0) != isUnit) {
                        continue;
                    }
                    // A collapsed force's symbol (+ its name label) is taller than the force box; widen its hit area.
                    double bottom = node.collapsed ? node.y + scaleForGUI(FORMATION_SYM + 14) : node.y + node.h;
                    if ((world.getX() >= node.x) && (world.getX() <= node.x + node.w)
                          && (world.getY() >= node.y) && (world.getY() <= bottom)) {
                        return node;
                    }
                }
            }
            return null;
        }

        /** Recomputes the collapse partition (which forces render as symbols) whenever the zoom changed. */
        private void ensureVisibility() {
            if (scale == lastVisibilityScale) {
                return;
            }
            for (Node node : drawNodes) {
                node.hidden = false;
                node.collapsed = false;
            }
            for (Node root : roots) {
                walkVisibility(root, false);
            }
            lastVisibilityScale = scale;
        }

        private void walkVisibility(Node node, boolean ancestorCollapsed) {
            node.hidden = ancestorCollapsed;
            boolean collapseHere = false;
            if (!ancestorCollapsed && (node instanceof ForceNode force) && shouldCollapse(force)) {
                node.collapsed = true;
                collapseHere = true;
            }
            if (node instanceof ForceNode force) {
                for (Node child : force.children) {
                    walkVisibility(child, ancestorCollapsed || collapseHere);
                }
            }
        }

        /**
         * @return whether a force should render as one collapsed echelon symbol: only once its units are already
         *       compact glyphs (never while unit icons are legible), and its whole subtree is small on screen.
         */
        private boolean shouldCollapse(ForceNode force) {
            if (force.children.isEmpty()) {
                return false;
            }
            if (scaleForGUI(UNIT_W) * scale >= LOD_ICON_MIN_PX) {
                return false;
            }
            return force.subtreeWidth * scale < COLLAPSE_MAX_PX;
        }

        /** Tallies the subtree's dominant branch and average weight class for the collapsed formation symbol. */
        private void computeFormationGlyph(ForceNode force) {
            List<Entity> units = new ArrayList<>();
            collectUnitEntities(force, units);
            if (units.isEmpty()) {
                return;
            }
            Map<SldfSymbol.Branch, Integer> branchTally = new EnumMap<>(SldfSymbol.Branch.class);
            int weightSum = 0;
            for (Entity entity : units) {
                branchTally.merge(SldfSymbol.branchOf(entity), 1, Integer::sum);
                weightSum += SldfSymbol.weightClassOf(entity);
            }
            force.dominantBranch = branchTally.entrySet().stream()
                  .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(SldfSymbol.Branch.OTHER);
            force.avgWeightClass = Math.round((float) weightSum / units.size());
        }

        private void collectUnitEntities(Node node, List<Entity> out) {
            if (node instanceof UnitNode unit) {
                out.add(unit.entity);
            } else {
                for (Node child : ((ForceNode) node).children) {
                    collectUnitEntities(child, out);
                }
            }
        }

        /** @return the owning player's colour for a force's formation symbol, or null (neutral) if unavailable. */
        private Color forceColor(Force force) {
            Player owner = game().getForces().getOwner(force);
            return (owner != null) ? owner.getColour().getColour() : null;
        }

        private boolean anyMemberSelected(Node node) {
            List<Integer> rows = new ArrayList<>();
            collectUnitRows(node, rows);
            for (int row : rows) {
                if (table.isRowSelected(row)) {
                    return true;
                }
            }
            return false;
        }

        private void collectUnitRows(Node node, List<Integer> rows) {
            if (node instanceof UnitNode unit) {
                rows.add(unit.row);
            } else {
                for (Node child : ((ForceNode) node).children) {
                    collectUnitRows(child, rows);
                }
            }
        }

        private void selectNode(Node node, MouseEvent e) {
            List<Integer> rows = new ArrayList<>();
            collectUnitRows(node, rows);
            if (rows.isEmpty()) {
                return;
            }
            boolean additive = e.isControlDown() || e.isShiftDown();
            if (!additive) {
                table.clearSelection();
            }
            for (int row : rows) {
                table.addRowSelectionInterval(row, row);
            }
        }

        private List<Entity> selectedEntities() {
            List<Entity> result = new ArrayList<>();
            for (int row : table.getSelectedRows()) {
                InGameObject unit = model.getEntityAt(row);
                if (unit instanceof Entity entity) {
                    result.add(entity);
                }
            }
            return result;
        }

        @Override
        public String getToolTipText(MouseEvent e) {
            Node node = nodeAt(e.getX(), e.getY());
            if (node instanceof UnitNode unit) {
                return unit.obscured ? "?" : unit.entity.getShortName();
            } else if (node instanceof ForceNode force) {
                return force.label;
            }
            return null;
        }

        private class CanvasMouse extends MouseAdapter {

            @Override
            public void mousePressed(MouseEvent e) {
                lastDragX = e.getX();
                lastDragY = e.getY();
                if (e.isPopupTrigger()) {
                    popup(e);
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    Node node = nodeAt(e.getX(), e.getY());
                    if (node != null) {
                        selectNode(node, e);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup(e);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if ((e.getClickCount() == 2) && SwingUtilities.isLeftMouseButton(e)) {
                    Node node = nodeAt(e.getX(), e.getY());
                    if ((node instanceof UnitNode unit) && !unit.obscured && lobby.isEditable(unit.entity)) {
                        lobby.lobbyActions.customizeMek(unit.entity);
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) || SwingUtilities.isMiddleMouseButton(e)) {
                    offsetX += e.getX() - lastDragX;
                    offsetY += e.getY() - lastDragY;
                    lastDragX = e.getX();
                    lastDragY = e.getY();
                    repaint();
                }
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double factor = (e.getWheelRotation() < 0) ? 1.1 : 1 / 1.1;
                double newScale = Math.max(MIN_SCALE, Math.min(MAX_SCALE, scale * factor));
                double wx = (e.getX() - offsetX) / scale;
                double wy = (e.getY() - offsetY) / scale;
                scale = newScale;
                offsetX = e.getX() - wx * scale;
                offsetY = e.getY() - wy * scale;
                repaint();
            }

            private void popup(MouseEvent e) {
                Node node = nodeAt(e.getX(), e.getY());
                if (node != null) {
                    List<Integer> rows = new ArrayList<>();
                    collectUnitRows(node, rows);
                    boolean anySelected = rows.stream().anyMatch(table::isRowSelected);
                    if (!anySelected) {
                        selectNode(node, e);
                    }
                }
                List<Entity> entities = selectedEntities();
                if (entities.isEmpty()) {
                    return;
                }
                ScalingPopup menu = LobbyMekPopup.getPopup(entities, new ArrayList<Force>(),
                      new LobbyMekPopupActions(lobby), lobby);
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private static final String SPACER = "  · ";

    // ----------------------------------------------------------------------------------------------------------------
    // Node model
    // ----------------------------------------------------------------------------------------------------------------

    private abstract static class Node {
        double x;
        double y;
        double w;
        double h;
        double subtreeWidth;
        double boxDX;       // x offset of this node's box within its subtree footprint (footprint left = 0)
        boolean hidden;     // inside a collapsed ancestor this frame -> not drawn or hit-tested
        boolean collapsed;  // (ForceNode only) drawn as a single echelon formation symbol
    }

    private static final class ForceNode extends Node {
        final String label;
        final List<Node> children = new ArrayList<>();
        // Far-zoom collapse attributes: the whole subtree drawn as one SldfSymbol echelon formation symbol.
        SldfSymbol.Echelon echelon = SldfSymbol.Echelon.LANCE;
        SldfSymbol.Branch dominantBranch = SldfSymbol.Branch.OTHER;
        int avgWeightClass;
        Color symbolColor;
        boolean isCommand;  // designated command lance: re-parented as its formation's "mother root"
        int sortWeight;     // ordering priority among siblings (command > detected formation > tonnage)

        ForceNode(String label) {
            this.label = label;
        }
    }

    /** A parent→child connector; keeps the child node so connectors into collapsed-away nodes are skipped. */
    private static final class Edge {
        final double px;
        final double py;
        final double cx;
        final double cy;
        final Node child;

        Edge(double px, double py, double cx, double cy, Node child) {
            this.px = px;
            this.py = py;
            this.cx = cx;
            this.cy = cy;
            this.child = child;
        }
    }

    private static final class UnitNode extends Node {
        final Entity entity;
        final int row;
        final boolean obscured;

        UnitNode(Entity entity, int row, boolean obscured) {
            this.entity = entity;
            this.row = row;
            this.obscured = obscured;
        }
    }
}
