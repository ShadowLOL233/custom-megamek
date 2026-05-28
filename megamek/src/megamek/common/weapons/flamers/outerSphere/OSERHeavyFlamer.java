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
 * Outer Sphere ER Heavy Flamer
 * Damage 5 / Heat 7 / Range 3-6-9 / 2t / 1 crit / no ammo (energy)
 *
 * Combined range-extended, damage-focused variant - the heaviest OS flamer.
 * Energy-fed (no ammo). Heat-to-fire (7) is the real limiter on this weapon.
 */
public class OSERHeavyFlamer extends FlamerWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSERHeavyFlamer() {
        super();
        name = "ER Heavy Flamer";
        setInternalName("OSERHeavyFlamer");
        addLookupName("OS ER Heavy Flamer");
        sortingName = "Flamer OS D ER Heavy";
        flags = flags.or(WeaponType.F_ER_FLAMER);
        heat = 7;
        damage = 5;
        infDamageClass = WeaponType.WEAPON_BURST_6D6;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 2;
        criticalSlots = 1;
        bv = 45;
        cost = 27000;
        shortAV = 5;
        maxRange = RANGE_SHORT;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }

    @Override
    public int getAlphaStrikeHeatDamage(int rangeBand) {
        return (rangeBand <= AlphaStrikeElement.RANGE_BAND_MEDIUM) ? 5 : 0;
    }
}
