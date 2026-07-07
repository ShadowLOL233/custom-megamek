/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.missiles.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.Mounted;
import megamek.common.weapons.missiles.MRMWeapon;

/**
 * Outer Sphere Improve MRM 10 - OS reinterprets the MRM as a precise mid-range LRM: no minimum
 * range and a -1 to-hit bonus (instead of the canon +1 penalty). Pairs with the Diana III FCS.
 * There is no base OS MRM (plain and Improve MRM were only months apart, so RoOS fielded Improve).
 */
public class OSImproveMRM10 extends MRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveMRM10() {
        super();
        name = "Improve MRM 10";
        setInternalName("OSImproveMRM10");
        addLookupName("OS Improve MRM-10");
        addLookupName("OS Improve MRM 10");
        sortingName = "Missile OS 2 1 2 10";
        toHitModifier = -1;
        heat = 4;
        rackSize = 10;
        minimumRange = 0;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 3.0;
        criticalSlots = 2;
        bv = 70;
        cost = 50000;
        shortAV = 6;
        medAV = 6;
        longAV = 6;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2900, 2930, 2960, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }

    @Override
    public int getToHitModifier(Mounted<?> mounted) {
        int mod = -1;
        if ((mounted != null) && (mounted.getLinkedBy() != null)
              && (mounted.getLinkedBy().getType() instanceof megamek.common.equipment.MiscType fcs)
              && fcs.hasFlag(megamek.common.equipment.MiscType.F_DIANA_III)
              && !mounted.getLinkedBy().isDestroyed() && !mounted.getLinkedBy().isMissing()
              && !mounted.getLinkedBy().isBreached()) {
            mod -= 1; // Diana III FCS adds another -1
        }
        return mod;
    }

    @Override
    public String getSortingName() {
        return sortingName;
    }
}
