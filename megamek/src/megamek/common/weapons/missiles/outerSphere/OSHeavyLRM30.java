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

/** Outer Sphere Heavy LRM 30 - double-fire launcher: two volleys per turn (2x ammo, 2x heat). */
public class OSHeavyLRM30 extends LRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyLRM30() {
        super();
        name = "Heavy LRM 30";
        setInternalName("OSHeavyLRM30");
        addLookupName("OS Heavy LRM-30");
        addLookupName("OS Heavy LRM 30");
        sortingName = "Missile OS 1 2 1 30";
        heat = 18;
        rackSize = 30;
        minimumRange = 6;
        tonnage = 14.0;
        criticalSlots = 7;
        bv = 510;
        cost = 562500;
        shortAV = 18;
        medAV = 18;
        longAV = 18;
        maxRange = RANGE_LONG;
        flags = flags.andNot(F_PROTO_WEAPON);
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
