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

/** Outer Sphere Heavy SRM 12 - double-fire launcher (2 volleys, 2x ammo/heat), extended 4/8/12. */
public class OSHeavySRM12 extends SRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavySRM12() {
        super();
        name = "Heavy SRM 12";
        setInternalName("OSHeavySRM12");
        addLookupName("OS Heavy SRM-12");
        addLookupName("OS Heavy SRM 12");
        sortingName = "Missile OS 3 2 1 12";
        heat = 14;
        rackSize = 12;
        shortRange = 4;
        mediumRange = 8;
        longRange = 12;
        extremeRange = 16;
        tonnage = 9.0;
        criticalSlots = 5;
        bv = 240;
        cost = 300000;
        shortAV = 16;
        medAV = 16;
        maxRange = RANGE_MED;
        flags = flags.andNot(F_PROTO_WEAPON);
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
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
