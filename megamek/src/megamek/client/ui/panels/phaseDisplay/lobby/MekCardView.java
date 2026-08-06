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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import megamek.client.ui.WrapLayout;
import megamek.client.ui.clientGUI.ClientGUI;
import megamek.client.ui.tileset.EntityImage;
import megamek.client.ui.tileset.MMStaticDirectoryManager;
import megamek.client.ui.util.ScalingPopup;
import megamek.common.Configuration;
import megamek.common.Player;
import megamek.common.force.Force;
import megamek.common.force.FormationBonusType;
import megamek.common.game.InGameObject;
import megamek.common.icons.Camouflage;
import megamek.common.options.OptionsConstants;
import megamek.common.units.Entity;
import megamek.common.util.ImageUtil;
import megamek.common.util.fileUtils.MegaMekFile;

/**
 * A MekBay-style "Card View" for the lobby: unit cards (icon, name, tonnage, BV, pilot skill) grouped by their
 * {@link Force}, each group headed by the force name and its detected {@link FormationBonusType} classification, plus
 * a detail panel that shows the full stats of the currently focused unit. It renders the same units as the sortable
 * {@link MekTableModel} table; card clicks drive the shared table's selection model, so all selection-dependent lobby
 * actions (configure, right-click menu, …) keep working unchanged.
 */
public class MekCardView extends JPanel implements Scrollable {

    private static final int CARD_WIDTH = 250;
    private static final int CARD_HEIGHT = 84;
    private static final int CARD_ICON_HEIGHT = 68;
    private static final String DOT_SPACER = " ⬝ ";

    private static final String UNKNOWN_UNIT = new MegaMekFile(Configuration.miscImagesDir(),
          "unknown_unit.gif").toString();

    private final ChatLounge lobby;
    private final MekTableModel model;
    /** The sortable table; the single source of truth for the current unit selection. */
    private final JTable table;
    private final ClientGUI clientGui;

    private final JPanel cardsContainer = new JPanel();
    private final UnitDetailPanel detailPanel = new UnitDetailPanel();
    private final List<UnitCard> allCards = new ArrayList<>();

    public MekCardView(ChatLounge lobby, MekTableModel model, JTable table) {
        this.lobby = lobby;
        this.model = model;
        this.table = table;
        this.clientGui = lobby.getClientGUI();

        setLayout(new BorderLayout());
        cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.PAGE_AXIS));
        JScrollPane cardsScroll = new JScrollPane(cardsContainer);
        cardsScroll.setBorder(null);
        cardsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        cardsScroll.getVerticalScrollBar().setUnitIncrement(scaleForGUI(16));
        add(cardsScroll, BorderLayout.CENTER);

        add(detailPanel, BorderLayout.EAST);

        // Keep card highlighting in sync when the selection is changed from either view.
        table.getSelectionModel().addListSelectionListener(e -> syncSelectionVisuals());
        setFocusedUnit(null);
    }

    /** Rebuilds the grouped cards from the shared table model. Call whenever the unit list changes. */
    public void refresh() {
        allCards.clear();
        cardsContainer.removeAll();

        // Bucket the model rows by force id, preserving the model's (sorted) order.
        Map<Integer, List<Integer>> groups = new LinkedHashMap<>();
        for (int row = 0; row < model.getRowCount(); row++) {
            InGameObject unit = model.getEntityAt(row);
            if (unit instanceof Entity entity) {
                groups.computeIfAbsent(entity.getForceId(), k -> new ArrayList<>()).add(row);
            }
        }
        for (Map.Entry<Integer, List<Integer>> entry : groups.entrySet()) {
            cardsContainer.add(buildForceSection(entry.getKey(), entry.getValue()));
        }
        cardsContainer.add(Box.createVerticalGlue());
        cardsContainer.revalidate();
        cardsContainer.repaint();
    }

    private JPanel buildForceSection(int forceId, List<Integer> rows) {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);
        section.setAlignmentX(LEFT_ALIGNMENT);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, scaleForGUI(4), 0));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(scaleForGUI(8), scaleForGUI(6), scaleForGUI(2), scaleForGUI(6)));
        headerPanel.add(new JLabel(buildSectionHeader(forceId)));
        FormationBonusType formation = detectForceFormation(forceId);
        if (formation != null) {
            JButton infoButton = new JButton("ⓘ");
            infoButton.setMargin(new Insets(0, scaleForGUI(4), 0, scaleForGUI(4)));
            infoButton.setFocusable(false);
            infoButton.setToolTipText(megamek.client.ui.Messages.getString("ChatLounge.cardView.formationInfo"));
            infoButton.addActionListener(e -> showFormationInfo(formation, forceId));
            headerPanel.add(infoButton);
        }
        section.add(headerPanel, BorderLayout.NORTH);

        JPanel cards = new JPanel(new WrapLayout(FlowLayout.LEFT, scaleForGUI(8), scaleForGUI(8)));
        cards.setOpaque(false);
        for (int row : rows) {
            UnitCard card = new UnitCard(row, (Entity) model.getEntityAt(row));
            allCards.add(card);
            cards.add(card);
        }
        section.add(cards, BorderLayout.CENTER);
        return section;
    }

    /** @return an HTML header for a force group: its name and detected formation classification. */
    private String buildSectionHeader(int forceId) {
        if (forceId == Force.NO_FORCE) {
            return "<HTML><B>" + megamek.client.ui.Messages.getString("ChatLounge.cardView.unassigned") + "</B>";
        }
        Force force = clientGui.getClient().getGame().getForces().getForce(forceId);
        if (force == null) {
            return "<HTML><B>" + megamek.client.ui.Messages.getString("ChatLounge.cardView.unassigned") + "</B>";
        }
        FormationBonusType formation = detectForceFormation(forceId);
        String classification = (formation != null)
              ? " &mdash; " + formation.getDisplayName() + " " + echelonForForce(forceId) : "";
        return "<HTML><B>" + force.getName() + "</B>" + classification;
    }

    /** @return the formation detected from a force's direct units, or null (Unassigned / none). */
    private FormationBonusType detectForceFormation(int forceId) {
        if (forceId == Force.NO_FORCE) {
            return null;
        }
        Force force = clientGui.getClient().getGame().getForces().getForce(forceId);
        if (force == null) {
            return null;
        }
        List<Entity> directUnits = new ArrayList<>();
        for (int memberId : force.getEntities()) {
            Entity member = clientGui.getClient().getGame().getEntity(memberId);
            if (member != null) {
                directUnits.add(member);
            }
        }
        return FormationBonusType.detect(directUnits);
    }

    private static final Color SECTION_REQ_COLOR = new Color(74, 111, 165);
    private static final Color SECTION_BONUS_COLOR = new Color(184, 134, 47);
    private static final Color SECTION_ABIL_COLOR = new Color(63, 125, 74);

    /** Shows a MekBay-style dialog describing a formation: requirements, bonus (with citation), granted abilities. */
    private void showFormationInfo(FormationBonusType formation, int forceId) {
        String designation = formation.getDisplayName() + " " + echelonForForce(forceId);
        Window owner = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(owner, designation, Dialog.ModalityType.APPLICATION_MODAL);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        content.setBorder(new EmptyBorder(scaleForGUI(14), scaleForGUI(18), scaleForGUI(12), scaleForGUI(18)));

        JLabel title = new JLabel(designation);
        title.setFont(title.getFont().deriveFont(Font.BOLD, scaleForGUI(18)));
        // Left-align (matching the sections) so the content lines up under the formation name.
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(title);
        content.add(Box.createVerticalStrut(scaleForGUI(12)));

        content.add(formationSection("REQUIREMENTS",
              "<HTML><DIV WIDTH=\"400\">" + formation.getRequirements() + "<BR><I>Active while the formation keeps "
                    + "at least " + FormationBonusType.MIN_ACTIVE_UNITS + " undestroyed units on the board.</I></DIV>",
              null, SECTION_REQ_COLOR));
        content.add(Box.createVerticalStrut(scaleForGUI(10)));
        content.add(formationSection("FORMATION BONUS",
              "<HTML><DIV WIDTH=\"400\">" + formation.getBonusDescription() + "</DIV>",
              "Alpha Strike: Commander's Edition", SECTION_BONUS_COLOR));
        content.add(Box.createVerticalStrut(scaleForGUI(10)));

        JPanel abilities = new JPanel();
        abilities.setLayout(new BoxLayout(abilities, BoxLayout.PAGE_AXIS));
        abilities.setAlignmentX(Component.LEFT_ALIGNMENT);
        abilities.add(formationSectionHeader("GRANTED ABILITIES", SECTION_ABIL_COLOR));
        abilities.add(Box.createVerticalStrut(scaleForGUI(3)));
        List<String> abilityNames = formation.getGrantedAbilityNames();
        if (abilityNames.isEmpty()) {
            abilities.add(formationAccentBox("<HTML><I>None in Total Warfare yet (display only).</I>", null,
                  SECTION_ABIL_COLOR));
        } else {
            for (String ability : abilityNames) {
                abilities.add(formationAccentBox("<HTML><B>" + ability + "</B>", null, SECTION_ABIL_COLOR));
                abilities.add(Box.createVerticalStrut(scaleForGUI(3)));
            }
        }
        content.add(abilities);

        JButton dismiss = new JButton("DISMISS");
        dismiss.addActionListener(e -> dialog.dispose());
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttons.add(dismiss);

        JPanel root = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(scaleForGUI(16));
        root.add(scroll, BorderLayout.CENTER);
        root.add(buttons, BorderLayout.SOUTH);

        dialog.setContentPane(root);
        dialog.pack();
        dialog.setSize(scaleForGUI(480), Math.min(dialog.getHeight() + scaleForGUI(4), scaleForGUI(580)));
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }

    /** @return the echelon designation for a (recursive) unit count: Lance / Reinforced Lance / Company / … */
    static String echelonName(int count) {
        if (count <= 4) {
            return "Lance";
        } else if (count <= 6) {
            return "Reinforced Lance";
        } else if (count <= 12) {
            return "Company";
        } else if (count <= 36) {
            return "Battalion";
        }
        return "Regiment";
    }

    /** @return the echelon designation of a force (by id), from its recursive unit count. */
    private String echelonForForce(int forceId) {
        Force force = clientGui.getClient().getGame().getForces().getForce(forceId);
        int count = (force != null) ? clientGui.getClient().getGame().getForces().getFullEntities(force).size() : 0;
        return echelonName(count);
    }

    private JComponent formationSection(String title, String html, String citation, Color color) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.PAGE_AXIS));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.add(formationSectionHeader(title, color));
        section.add(Box.createVerticalStrut(scaleForGUI(2)));
        section.add(formationAccentBox(html, citation, color));
        return section;
    }

    private JComponent formationSectionHeader(String text, Color color) {
        JLabel header = new JLabel(text);
        header.setOpaque(true);
        header.setBackground(color);
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD, (float) scaleForGUI(11)));
        header.setBorder(new EmptyBorder(scaleForGUI(2), scaleForGUI(6), scaleForGUI(2), scaleForGUI(6)));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, header.getPreferredSize().height + scaleForGUI(6)));
        return header;
    }

    private JComponent formationAccentBox(String html, String citation, Color color) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.setBorder(BorderFactory.createCompoundBorder(
              new MatteBorder(0, scaleForGUI(3), 0, 0, color),
              new EmptyBorder(scaleForGUI(4), scaleForGUI(8), scaleForGUI(4), scaleForGUI(8))));
        JLabel body = new JLabel(html);
        body.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(body);
        if (citation != null) {
            JLabel cite = new JLabel(citation);
            cite.setFont(cite.getFont().deriveFont(Font.ITALIC, (float) scaleForGUI(10)));
            cite.setForeground(Color.GRAY);
            cite.setAlignmentX(Component.LEFT_ALIGNMENT);
            box.add(cite);
        }
        return box;
    }

    /** Updates the detail panel to show the given focused unit (null clears it). */
    public void setFocusedUnit(Entity unit) {
        detailPanel.setUnit(unit, (unit != null) && isObscured(unit));
    }

    private void syncSelectionVisuals() {
        for (UnitCard card : allCards) {
            card.syncSelection();
        }
    }

    private Player ownerOf(Entity entity) {
        return clientGui.getClient().getGame().getPlayer(entity.getOwnerId());
    }

    /** Mirrors {@link MekTableModel}'s blind-drop obscuring: enemy units are hidden unless the viewer is a GM. */
    private boolean isObscured(Entity entity) {
        Player local = clientGui.getClient().getLocalPlayer();
        boolean localGM = local.isGameMaster();
        return !localGM && local.isEnemyOf(ownerOf(entity))
              && clientGui.getClient().getGame().getOptions().booleanOption(OptionsConstants.BASE_BLIND_DROP);
    }

    private List<Entity> selectedEntities() {
        List<Entity> result = new ArrayList<>();
        for (int row : table.getSelectedRows()) {
            InGameObject unit = model.getEntityAt(row);
            if (unit instanceof Entity) {
                result.add((Entity) unit);
            }
        }
        return result;
    }

    // Scrollable: fill the enclosing viewport (so the outer scroll pane never scrolls us) and let the internal
    // cards scroll pane handle overflow while the detail panel stays pinned at the bottom.
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

    /** A single unit card. Its row index matches the model/table row (the lobby table uses no row sorter). */
    private class UnitCard extends JPanel {

        private final int row;

        UnitCard(int row, Entity entity) {
            this.row = row;
            boolean obscured = isObscured(entity);

            setLayout(new BorderLayout(scaleForGUI(6), 0));
            setPreferredSize(new Dimension(scaleForGUI(CARD_WIDTH), scaleForGUI(CARD_HEIGHT)));
            setOpaque(true);

            JLabel iconLabel = new JLabel();
            iconLabel.setBorder(new EmptyBorder(scaleForGUI(4), scaleForGUI(4), scaleForGUI(4), 0));
            Icon icon = buildIcon(entity, obscured, scaleForGUI(CARD_ICON_HEIGHT));
            if (icon != null) {
                iconLabel.setIcon(icon);
            }
            add(iconLabel, BorderLayout.WEST);

            JPanel info = new JPanel();
            info.setOpaque(false);
            info.setLayout(new BoxLayout(info, BoxLayout.PAGE_AXIS));
            info.setBorder(new EmptyBorder(scaleForGUI(6), scaleForGUI(2), scaleForGUI(4), scaleForGUI(4)));

            Player owner = ownerOf(entity);
            String name = obscured ? "?" : entity.getShortName();
            JLabel nameLabel = new JLabel("<HTML><B>" + name + "</B></HTML>");
            info.add(nameLabel);

            if (!obscured) {
                String tons = NumberFormat.getInstance().format(entity.getWeight());
                String bv = NumberFormat.getIntegerInstance().format(entity.getStrength());
                info.add(new JLabel(tons + " t" + DOT_SPACER + "BV " + bv));
                info.add(new JLabel(entity.getCrew().getSkillsAsString(false)));
            }

            JLabel playerLabel = new JLabel(owner.getName());
            playerLabel.setForeground(owner.getColour().getColour());
            info.add(playerLabel);
            add(info, BorderLayout.CENTER);

            CardMouseAdapter mouse = new CardMouseAdapter(entity);
            addMouseListener(mouse);
            iconLabel.addMouseListener(mouse);
            info.addMouseListener(mouse);

            syncSelection();
        }

        /** Updates the card background/border to reflect whether its row is currently selected in the table. */
        void syncSelection() {
            boolean selected = table.isRowSelected(row);
            setBackground(selected ? table.getSelectionBackground() : table.getBackground());
            Color line = selected ? table.getSelectionBackground().darker() : Color.GRAY;
            setBorder(BorderFactory.createLineBorder(line, scaleForGUI(selected ? 2 : 1)));
        }
    }

    private class CardMouseAdapter extends MouseAdapter {

        private final Entity entity;

        CardMouseAdapter(Entity entity) {
            this.entity = entity;
        }

        private int rowFor(MouseEvent e) {
            Component c = (Component) e.getSource();
            UnitCard card = (c instanceof UnitCard) ? (UnitCard) c
                  : (UnitCard) SwingUtilities.getAncestorOfClass(UnitCard.class, c);
            return (card != null) ? card.row : -1;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup(e);
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                select(rowFor(e), e);
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
            if ((e.getClickCount() == 2) && SwingUtilities.isLeftMouseButton(e) && lobby.isEditable(entity)) {
                lobby.lobbyActions.customizeMek(entity);
            }
        }

        private void select(int row, MouseEvent e) {
            if (row < 0) {
                return;
            }
            if (e.isControlDown()) {
                if (table.isRowSelected(row)) {
                    table.removeRowSelectionInterval(row, row);
                } else {
                    table.addRowSelectionInterval(row, row);
                }
            } else if (e.isShiftDown()) {
                int anchor = table.getSelectionModel().getAnchorSelectionIndex();
                table.addRowSelectionInterval((anchor < 0) ? row : anchor, row);
            } else {
                table.setRowSelectionInterval(row, row);
            }
        }

        private void popup(MouseEvent e) {
            int row = rowFor(e);
            if ((row >= 0) && !table.isRowSelected(row)) {
                table.setRowSelectionInterval(row, row);
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

    private Icon buildIcon(Entity entity, boolean obscured, int height) {
        Image image;
        if (obscured) {
            image = new ImageIcon(UNKNOWN_UNIT).getImage();
        } else {
            Camouflage camouflage = entity.getCamouflageOrElseOwners();
            Image base = MMStaticDirectoryManager.getMekTileset().imageFor(entity);
            image = new EntityImage(base, camouflage, this, entity).loadPreviewImage(true);
        }
        if ((image == null) || (image.getWidth(null) <= 0) || (image.getHeight(null) <= 0)) {
            return null;
        }
        int width = height * image.getWidth(null) / image.getHeight(null);
        return new ImageIcon(ImageUtil.getScaledImage(image, width, height));
    }
}
