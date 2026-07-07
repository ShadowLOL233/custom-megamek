/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.flamers.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.alphaStrike.AlphaStrikeElement;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.WeaponType;
import megamek.common.weapons.flamers.FlamerWeapon;

/**
 * Outer Sphere ER Flamer
 * Damage 2 / Heat 3 / Range 3-6-9 / 1t / 1 crit / no ammo (energy)
 *
 * Range-extended variant. Reaches noticeably further than the canonical IS ER
 * Flamer (3-5-7) at comparable mass, reflecting the OS edge in field projection.
 */
public class OSERFlamer extends FlamerWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERFlamer() {
        super();
        name = "ER Flamer";
        setInternalName("OSERFlamer");
        addLookupName("OS ER Flamer");
        sortingName = "Flamer OS 3 ER 1 Std";
        flags = flags.or(WeaponType.F_ER_FLAMER);
        heat = 3;
        damage = 2;
        infDamageClass = WeaponType.WEAPON_BURST_2D6;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 1;
        criticalSlots = 1;
        bv = 18;
        cost = 11000;
        shortAV = 2;
        maxRange = RANGE_SHORT;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2805, 2820, 2840, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }

    @Override
    public int getAlphaStrikeHeatDamage(int rangeBand) {
        return (rangeBand <= AlphaStrikeElement.RANGE_BAND_MEDIUM) ? 2 : 0;
    }
}
