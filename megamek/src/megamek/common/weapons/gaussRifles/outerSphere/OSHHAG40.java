/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.gaussRifles.outerSphere;

import static megamek.common.game.IGame.LOGGER;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.annotations.Nullable;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.equipment.AmmoType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.gaussRifles.HAGWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.OSHHAGWeaponHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Heavy Hyper-Assault Gauss/40 - the heaviest cluster Gauss in the line: longer
 * range and a denser cluster (handler adds +2 to the cluster roll), paid for with heavy tonnage,
 * high heat and a large crit footprint. Experimental tier (TechRating G).
 */
public class OSHHAG40 extends HAGWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHHAG40() {
        super();
        name = "Heavy HAG/40";
        setInternalName("OSHHAG40");
        addLookupName("OS Heavy HAG/40");
        addLookupName("Heavy Hyper-Assault Gauss/40");
        addLookupName("OS HHAG/40");
        sortingName = "Gauss OS 7 HHAG 40";
        ammoType = AmmoType.AmmoTypeEnum.HAG_OS;
        heat = 10;
        rackSize = 40;
        minimumRange = 2;
        shortRange = 10;
        mediumRange = 20;
        longRange = 30;
        extremeRange = 40;
        tonnage = 19.0;
        criticalSlots = 13;
        bv = 690;
        cost = 950000;
        shortAV = 28;
        medAV = 28;
        longAV = 28;
        maxRange = RANGE_LONG;
        explosionDamage = 40;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.G)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.F)
              .setISAdvancement(3130, 3135, 3140, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.EXPERIMENTAL);
    }

    @Override
    @Nullable
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new OSHHAGWeaponHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
