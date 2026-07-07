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
 * Outer Sphere Heavy Flamer
 * Damage 5 / Heat 6 / Range 2-4-6 / 1.5t / 1 crit / no ammo (energy)
 *
 * Damage-focused variant. Unlike the canonical IS/Clan Heavy Flamer, which burns
 * a plastic-fuel ammo feed, the OS version is fully energy-fed from the fusion
 * plant - no ammo, in keeping with the OS "energy, no ammo" weapon doctrine.
 */
public class OSHeavyFlamer extends FlamerWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyFlamer() {
        super();
        name = "Heavy Flamer";
        setInternalName("OSHeavyFlamer");
        addLookupName("OS Heavy Flamer");
        sortingName = "Flamer OS 2 Heavy 1 Std";
        heat = 6;
        damage = 5;
        infDamageClass = WeaponType.WEAPON_BURST_6D6;
        shortRange = 2;
        mediumRange = 4;
        longRange = 6;
        extremeRange = 8;
        tonnage = 1.5;
        criticalSlots = 1;
        bv = 30;
        cost = 16000;
        shortAV = 5;
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
        return (rangeBand == AlphaStrikeElement.RANGE_BAND_SHORT) ? 5 : 0;
    }
}
