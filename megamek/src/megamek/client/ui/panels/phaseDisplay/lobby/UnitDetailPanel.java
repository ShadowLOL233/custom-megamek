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
import java.awt.Dimension;
import java.awt.Image;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import megamek.client.ui.Messages;
import megamek.client.ui.tileset.EntityImage;
import megamek.client.ui.tileset.MMStaticDirectoryManager;
import megamek.common.Configuration;
import megamek.common.equipment.EquipmentType;
import megamek.common.equipment.WeaponType;
import megamek.common.icons.Camouflage;
import megamek.common.units.Entity;
import megamek.common.units.EntityWeightClass;
import megamek.common.units.Mek;
import megamek.common.units.UnitType;
import megamek.common.util.ImageUtil;
import megamek.common.util.fileUtils.MegaMekFile;
import megamek.common.verifier.TestEntity;

/**
 * The lobby "Unit Details" panel: a mek icon + name header over a scrollable stats body (two-column property grid,
 * combat stat bars, colour-coded weapon/ammo tags) for a single focused unit. Extracted from {@link MekCardView} so it
 * can be reused by other lobby views (e.g. the {@link ForceToeView} TO&E chart). It is fed a plain {@link Entity} plus
 * an {@code obscured} flag (blind-drop hiding is decided by the caller), so it carries no game/selection state.
 */
public class UnitDetailPanel extends JPanel {

    private static final String UNKNOWN_UNIT = new MegaMekFile(Configuration.miscImagesDir(),
          "unknown_unit.gif").toString();

    private static final int STAT_TABLE_PX = 288;
    private static final int STAT_BAR_PX = 196;
    private static final String STAT_BAR_COLOR = "#5f6066";
    // Per weapon/ammo category [energy, missile, ballistic, other]: dark body + a brightened left stripe.
    private static final String[] TAG_BODY = { "#283a5a", "#284c2d", "#43315a", "#5e4828" };
    private static final String[] TAG_STRIPE = { "#5a9be0", "#6ac47a", "#a97fd6", "#e0a860" };
    private static final int CAT_ENERGY = 0;
    private static final int CAT_MISSILE = 1;
    private static final int CAT_BALLISTIC = 2;
    private static final int CAT_OTHER = 3;

    private final JLabel iconLabel = new JLabel();
    private final JLabel nameLabel = new JLabel();
    private final JLabel bodyLabel = new JLabel();
    private final JScrollPane bodyScroll;

    public UnitDetailPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(Messages.getString("ChatLounge.cardView.unitDetails")));

        iconLabel.setVerticalAlignment(SwingConstants.TOP);
        iconLabel.setBorder(new EmptyBorder(scaleForGUI(6), scaleForGUI(6), scaleForGUI(6), scaleForGUI(4)));
        nameLabel.setVerticalAlignment(SwingConstants.TOP);
        nameLabel.setBorder(new EmptyBorder(scaleForGUI(6), 0, scaleForGUI(4), scaleForGUI(6)));
        nameLabel.setFont(nameLabel.getFont().deriveFont(nameLabel.getFont().getSize2D() * 1.15f));
        JPanel header = new JPanel(new BorderLayout(scaleForGUI(4), 0));
        header.add(iconLabel, BorderLayout.WEST);
        header.add(nameLabel, BorderLayout.CENTER);

        bodyLabel.setVerticalAlignment(SwingConstants.TOP);
        bodyLabel.setBorder(new EmptyBorder(scaleForGUI(2), scaleForGUI(8), scaleForGUI(6), scaleForGUI(8)));
        bodyLabel.setFont(bodyLabel.getFont().deriveFont(bodyLabel.getFont().getSize2D() * 1.15f));
        bodyScroll = new JScrollPane(bodyLabel);
        bodyScroll.setBorder(null);
        bodyScroll.getVerticalScrollBar().setUnitIncrement(scaleForGUI(16));

        add(header, BorderLayout.NORTH);
        add(bodyScroll, BorderLayout.CENTER);
        setPreferredSize(new Dimension(scaleForGUI(370), scaleForGUI(100)));
        setUnit(null, false);
    }

    /** Shows the given unit's details; a null unit shows the placeholder, an obscured unit shows "?". */
    public void setUnit(Entity unit, boolean obscured) {
        if ((unit == null) || obscured) {
            iconLabel.setIcon(null);
            nameLabel.setText("");
            bodyLabel.setText((unit == null)
                  ? "<HTML><I>" + Messages.getString("ChatLounge.cardView.selectUnit") + "</I>"
                  : "<HTML><B>?</B>");
        } else {
            iconLabel.setIcon(buildIcon(unit, false, scaleForGUI(96)));
            nameLabel.setText(buildDetailHeaderHtml(unit));
            bodyLabel.setText(buildDetailBodyHtml(unit));
        }
        bodyScroll.getVerticalScrollBar().setValue(0);
    }

    /**
     * @return the header HTML: left column = model then chassis; right column = weight class, role, super-category
     *       (Mek/Vehicle/Aerospace/…) then sub-category (BattleMek/IndustrialMek/ProtoMek/…).
     */
    private String buildDetailHeaderHtml(Entity entity) {
        String role = (entity.getRole() != null) && entity.getRole().hasRole() ? entity.getRole().toString() : "";
        return "<HTML><TABLE WIDTH=\"100%\"><TR>"
              + "<TD VALIGN=\"TOP\"><FONT SIZE=\"-2\">" + entity.getModel() + "</FONT><BR><B><FONT SIZE=\"+1\">"
              + entity.getChassis() + "</FONT></B></TD>"
              + "<TD VALIGN=\"TOP\" ALIGN=\"RIGHT\">" + EntityWeightClass.getClassName(entity.getWeightClass())
              + "<BR><I>" + role + "</I><BR>" + superCategory(entity) + "<BR><FONT SIZE=\"-2\">"
              + subCategory(entity) + "</FONT></TD>"
              + "</TR></TABLE></HTML>";
    }

    /** @return the broad unit category: Mek / Vehicle / Aerospace / Infantry, else the readable unit-type name. */
    private static String superCategory(Entity entity) {
        int type = entity.getUnitType();
        if ((type == UnitType.MEK) || (type == UnitType.PROTOMEK)) {
            return "Mek";
        } else if ((type == UnitType.TANK) || (type == UnitType.VTOL) || (type == UnitType.NAVAL)) {
            return "Vehicle";
        } else if ((type == UnitType.AEROSPACE_FIGHTER) || (type == UnitType.CONV_FIGHTER)
              || (type == UnitType.SMALL_CRAFT) || (type == UnitType.DROPSHIP)) {
            return "Aerospace";
        } else if ((type == UnitType.INFANTRY) || (type == UnitType.BATTLE_ARMOR)) {
            return "Infantry";
        }
        return UnitType.getTypeDisplayableName(type);
    }

    /** @return the specific unit sub-category, e.g. BattleMek / IndustrialMek / ProtoMek / Tank / Aerospace Fighter. */
    private static String subCategory(Entity entity) {
        if ((entity.getUnitType() == UnitType.MEK) && (entity instanceof Mek)) {
            return ((Mek) entity).isIndustrial() ? "IndustrialMek" : "BattleMek";
        }
        return UnitType.getTypeDisplayableName(entity.getUnitType());
    }

    private String buildDetailBodyHtml(Entity entity) {
        NumberFormat integers = NumberFormat.getIntegerInstance();

        StringBuilder sb = new StringBuilder("<HTML>");

        // Two-column property grid.
        List<String[]> left = new ArrayList<>();
        left.add(new String[] { "BV", integers.format(entity.getStrength()) });
        left.add(new String[] { "Tons", NumberFormat.getInstance().format(entity.getWeight()) });
        left.add(new String[] { "Intro", String.valueOf(entity.getIntroductionDate()) });
        left.add(new String[] { "C-Bill", integers.format(entity.getCost(false)) });
        left.add(new String[] { "Structure", EquipmentType.getStructureTypeName(entity.getStructureType()) });
        left.add(new String[] { "Engine", (entity.getEngine() != null) ? entity.getEngine().getEngineName() : "—" });
        left.add(new String[] { "Move", entity.getWalkMP() + "/" + entity.getRunMP() + "/" + entity.getJumpMP() });

        List<String[]> right = new ArrayList<>();
        right.add(new String[] { "Pilot", entity.getCrew().getSkillsAsString(false) });
        right.add(new String[] { "Rules", titleCase(entity.getStaticTechLevel().toString()) });
        right.add(new String[] { "Tech", techBaseName(entity) });
        right.add(new String[] { "Armor", EquipmentType.getArmorTypeName(entity.getArmorType(0)) });
        right.add(new String[] { "Motive", entity.getMovementModeAsString() });
        right.add(new String[] { "Network", networkName(entity) });

        sb.append("<TABLE>");
        int gridRows = Math.max(left.size(), right.size());
        for (int i = 0; i < gridRows; i++) {
            sb.append("<TR>");
            appendPair(sb, (i < left.size()) ? left.get(i) : null);
            sb.append("<TD>&nbsp;&nbsp;</TD>");
            appendPair(sb, (i < right.size()) ? right.get(i) : null);
            sb.append("</TR>");
        }
        sb.append("</TABLE><HR>");

        // Combat stat block with proportional bars.
        double firepower = LanceRadarChart.axisValue(entity, 0);
        int dissipation = (int) LanceRadarChart.axisValue(entity, 5);
        int weaponHeat = totalWeaponHeat(entity);
        double damagePerTurn = (weaponHeat <= dissipation) || (weaponHeat == 0) ? firepower
              : firepower * dissipation / weaponHeat;
        int armor = entity.getTotalOArmor();
        int maxArmor = Math.max(armor, TestEntity.getMaximumArmorPoints(entity));
        int armorPct = (maxArmor > 0) ? (int) Math.round(100.0 * armor / maxArmor) : 100;

        statBar(sb, "Armor", armor + " (" + armorPct + "%)", (maxArmor > 0) ? (double) armor / maxArmor : 0);
        statBar(sb, "Structure", String.valueOf(entity.getTotalOInternal()), entity.getTotalOInternal() / 200.0);
        statBar(sb, "Firepower", String.valueOf(Math.round(firepower)), firepower / 60.0);
        statBar(sb, "Damage/Turn", String.format("%.1f", damagePerTurn), damagePerTurn / 40.0);
        double range = LanceRadarChart.axisValue(entity, 4);
        statBar(sb, "Range", String.valueOf(Math.round(range)), range / 30.0);
        statBar(sb, "Heat", String.valueOf(weaponHeat), weaponHeat / 40.0);
        statBar(sb, "Dissipation", String.valueOf(dissipation), dissipation / 30.0);
        statBar(sb, "Top Speed", String.valueOf(entity.getRunMP()), entity.getRunMP() / 12.0);
        statBar(sb, "Jump", String.valueOf(entity.getJumpMP()), entity.getJumpMP() / 8.0);

        // Equipment (weapons) as color-coded bars by type.
        Map<String, Integer> weaponCounts = new LinkedHashMap<>();
        Map<String, Integer> weaponCategories = new LinkedHashMap<>();
        for (var mounted : entity.getWeaponList()) {
            String name = mounted.getType().getName();
            weaponCounts.merge(name, 1, Integer::sum);
            weaponCategories.putIfAbsent(name, weaponCategory(mounted.getType()));
        }
        if (!weaponCounts.isEmpty()) {
            sb.append("<BR><B>Equipment</B>");
            for (Map.Entry<String, Integer> weapon : weaponCounts.entrySet()) {
                colorTag(sb, weaponCategories.get(weapon.getKey()), weapon.getValue() + "× " + weapon.getKey());
            }
        }

        // Ammo as color-coded bars with shots per ton.
        Map<String, Integer> ammoShots = new LinkedHashMap<>();
        for (var mounted : entity.getAmmo()) {
            ammoShots.putIfAbsent(mounted.getType().getName(), mounted.getType().getShots());
        }
        if (!ammoShots.isEmpty()) {
            sb.append("<BR><B>Ammo</B>");
            for (Map.Entry<String, Integer> bin : ammoShots.entrySet()) {
                colorTag(sb, ammoCategory(bin.getKey()), bin.getKey() + " (" + bin.getValue() + "/ton)");
            }
        }
        return sb.append("</HTML>").toString();
    }

    private static void appendPair(StringBuilder sb, String[] pair) {
        if (pair == null) {
            sb.append("<TD></TD><TD></TD>");
        } else {
            sb.append("<TD>").append(pair[0]).append("</TD><TD><B>").append(pair[1]).append("</B></TD>");
        }
    }

    /** Renders one stat as its own fixed-width mini-table: a gray bar (label on it) sized to {@code proportion}. */
    private static void statBar(StringBuilder sb, String label, String value, double proportion) {
        int fill = (int) Math.round(Math.max(0, Math.min(1, proportion)) * STAT_BAR_PX);
        sb.append("<TABLE WIDTH=\"").append(STAT_TABLE_PX).append("\" CELLPADDING=\"1\"><TR>")
              .append("<TD BGCOLOR=\"").append(STAT_BAR_COLOR).append("\" WIDTH=\"").append(fill)
              .append("\"><FONT COLOR=\"#ffffff\"><B>&nbsp;").append(label).append("&nbsp;</B></FONT></TD>")
              .append("<TD></TD><TD ALIGN=\"RIGHT\"><B>").append(value).append("</B></TD></TR></TABLE>");
    }

    /**
     * Renders a color-coded tag: a brightened left stripe + a thin bright outline (both the stripe color) around a
     * dark tinted body with white text. The outline is faked with a 1px stripe-colored outer cell (Swing HTML does
     * not honor table BORDERCOLOR).
     */
    private static void colorTag(StringBuilder sb, int category, String text) {
        String stripe = TAG_STRIPE[category];
        sb.append("<TABLE CELLSPACING=\"0\" CELLPADDING=\"1\" BGCOLOR=\"").append(stripe).append("\"><TR><TD>")
              .append("<TABLE CELLSPACING=\"0\" CELLPADDING=\"0\"><TR>")
              .append("<TD BGCOLOR=\"").append(stripe).append("\" WIDTH=\"5\">&nbsp;</TD>")
              .append("<TD BGCOLOR=\"").append(TAG_BODY[category]).append("\"><FONT COLOR=\"#ffffff\">&nbsp;")
              .append(text).append("&nbsp;</FONT></TD>")
              .append("</TR></TABLE></TD></TR></TABLE>");
    }

    /** @return the category index for a weapon: energy / missile / ballistic / other. */
    private static int weaponCategory(WeaponType weaponType) {
        if (weaponType.hasFlag(WeaponType.F_ENERGY)) {
            return CAT_ENERGY;
        } else if (weaponType.hasFlag(WeaponType.F_MISSILE)) {
            return CAT_MISSILE;
        } else if (weaponType.hasFlag(WeaponType.F_BALLISTIC)) {
            return CAT_BALLISTIC;
        }
        return CAT_OTHER;
    }

    /** @return the category index for an ammo bin, classified by name (missile / ballistic / other). */
    private static int ammoCategory(String name) {
        String upper = name.toUpperCase();
        if (upper.contains("LRM") || upper.contains("SRM") || upper.contains("MRM") || upper.contains("MML")
              || upper.contains("ATM") || upper.contains("ROCKET") || upper.contains("THUNDERBOLT")
              || upper.contains("NARC") || upper.contains("STREAK") || upper.contains("ARROW")) {
            return CAT_MISSILE;
        }
        if (upper.contains("AC/") || upper.contains("AUTOCANNON") || upper.contains("GAUSS") || upper.contains("MG")
              || upper.contains("MACHINE GUN") || upper.contains("LB ") || upper.contains("ULTRA")
              || upper.contains("ROTARY") || upper.contains("HAG") || upper.contains("RIFLE")
              || upper.contains("LONG TOM") || upper.contains("SNIPER") || upper.contains("THUMPER")) {
            return CAT_BALLISTIC;
        }
        return CAT_OTHER;
    }

    private static String titleCase(String text) {
        return text.isEmpty() ? text : text.charAt(0) + text.substring(1).toLowerCase();
    }

    private static String techBaseName(Entity entity) {
        return entity.isMixedTech() ? "Mixed" : entity.isClan() ? "Clan" : "Inner Sphere";
    }

    private static String networkName(Entity entity) {
        return (entity.hasC3() || entity.hasC3i()) ? "C3" : "None";
    }

    private static int totalWeaponHeat(Entity entity) {
        int heat = 0;
        for (var mounted : entity.getWeaponList()) {
            heat += Math.max(0, mounted.getType().getHeat());
        }
        return heat;
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
