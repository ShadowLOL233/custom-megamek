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

/** Outer Sphere Improve MRM 30 - precise mid-range LRM: no min range, -1 to-hit, Diana III FCS. */
public class OSImproveMRM30 extends MRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSImproveMRM30() {
        super();
        name = "Improve MRM 30";
        setInternalName("OSImproveMRM30");
        addLookupName("OS Improve MRM-30");
        addLookupName("OS Improve MRM 30");
        sortingName = "Missile OS 2 1 2 30";
        toHitModifier = -1;
        heat = 10;
        rackSize = 30;
        minimumRange = 0;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 9.5;
        criticalSlots = 5;
        bv = 210;
        cost = 150000;
        shortAV = 18;
        medAV = 18;
        longAV = 18;
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
