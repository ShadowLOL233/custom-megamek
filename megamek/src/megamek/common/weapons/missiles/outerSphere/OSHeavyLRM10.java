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
import megamek.common.weapons.handlers.lrm.OSDoubleFireLRMHandler;
import megamek.common.weapons.lrms.LRMWeapon;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Heavy LRM 10 — Single / Ultra fire modes. Ultra fires two volleys in one turn (2x ammo,
 * 2x heat) and can jam on a natural-2 attack roll; Single is a plain LRM 10 (one volley, no jam).
 */
public class OSHeavyLRM10 extends LRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyLRM10() {
        super();
        setModes(new String[] { MODE_AC_SINGLE, MODE_UAC_ULTRA });
        name = "Heavy LRM 10";
        setInternalName("OSHeavyLRM10");
        addLookupName("OS Heavy LRM-10");
        addLookupName("OS Heavy LRM 10");
        sortingName = "Missile OS 1 2 1 10";
        heat = 4;
        rackSize = 10;
        minimumRange = 6;
        mediumRange = 10;
        tonnage = 5.0;
        criticalSlots = 2;
        bv = 155;
        longRange = 24;
        extremeRange = 30;
        cost = 150000;
        shortAV = 6;
        medAV = 6;
        longAV = 6;
        maxRange = RANGE_LONG;
        flags = flags.andNot(F_PROTO_WEAPON).or(F_OS_LRM_LONG_SPEC);
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
            return new OSDoubleFireLRMHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            return null;
        }
    }
}
