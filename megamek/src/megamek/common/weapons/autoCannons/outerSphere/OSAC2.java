/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.autoCannons.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.alphaStrike.AlphaStrikeElement;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.Mounted;
import megamek.common.weapons.autoCannons.ACWeapon;

/**
 * Outer Sphere Standard AC/2 — RoOS-manufactured replica of the upstream IS AC/2.
 * Stats identical to the IS reference; differentiated only by tech base.
 */
public class OSAC2 extends ACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAC2() {
        super();
        name = "AC/2";
        setInternalName("OSAC2");
        addLookupName("OS AC/2");
        addLookupName("OS Autocannon/2");
        addLookupName("Legion AC/2");
        sortingName = "AC OS 1 Std 1 Std 02";
        heat = 1;
        damage = 2;
        rackSize = 2;
        minimumRange = 4;
        shortRange = 8;
        mediumRange = 16;
        longRange = 24;
        extremeRange = 32;
        tonnage = 6.0;
        criticalSlots = 1;
        bv = 37;
        cost = 75000;
        explosive = true;
        shortAV = 2;
        medAV = 2;
        longAV = 2;
        maxRange = RANGE_LONG;
        explosionDamage = damage;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.C)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.D, AvailabilityValue.C)
              .setISAdvancement(3115, 3120, 3125, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }

    @Override
    public double getBattleForceDamage(int range, Mounted<?> ignore) {
        return range == AlphaStrikeElement.SHORT_RANGE ? 0.132 : 0.2;
    }
}
