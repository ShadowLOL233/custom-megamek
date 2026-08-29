/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.missiles.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.srm.OSDoubleFireSRMHandler;
import megamek.common.weapons.srms.SRMWeapon;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Heavy SRM 4 — Single / Ultra fire modes, extended 4/8/12 range. Ultra fires two volleys in
 * one turn (2x ammo, 2x heat) and can jam on a natural-2 attack roll; Single is a plain SRM 4 (one volley).
 */
public class OSHeavySRM4 extends SRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavySRM4() {
        super();
        setModes(new String[] { MODE_AC_SINGLE, MODE_UAC_ULTRA });
        name = "Heavy SRM 4";
        setInternalName("OSHeavySRM4");
        addLookupName("OS Heavy SRM-4");
        addLookupName("OS Heavy SRM 4");
        sortingName = "Missile OS 3 2 1 04";
        heat = 3;
        rackSize = 4;
        shortRange = 6;
        mediumRange = 8;
        longRange = 10;
        extremeRange = 12;
        tonnage = 3.0;
        criticalSlots = 2;
        bv = 65;
        cost = 90000;
        shortAV = 5;
        medAV = 5;
        maxRange = RANGE_MED;
        flags = flags.andNot(F_PROTO_WEAPON).or(F_OS_SRM_SHORT_SPEC);
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
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new OSDoubleFireSRMHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            return null;
        }
    }
}
