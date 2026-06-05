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
import megamek.common.equipment.Mounted;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.lrm.StreakLRMHandler;
import megamek.common.weapons.missiles.MRMWeapon;
import megamek.server.totalWarfare.TWGameManager;

/** Outer Sphere Streak MRM 10 - MRM with lock-on full salvo. Heavy/bulky to force a real tradeoff. */
public class OSStreakMRM10 extends MRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSStreakMRM10() {
        super();
        name = "Streak MRM 10";
        setInternalName("OSStreakMRM10");
        addLookupName("OS Streak MRM-10");
        addLookupName("OS Streak MRM 10");
        sortingName = "Missile OS 2 4 1 10";
        toHitModifier = 0;
        heat = 4;
        rackSize = 10;
        minimumRange = 0;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 6.0;
        criticalSlots = 3;
        bv = 120;
        cost = 120000;
        shortAV = 10;
        medAV = 10;
        longAV = 10;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }

    @Override
    public int getToHitModifier(Mounted<?> mounted) {
        return 0;
    }

    @Override
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new StreakLRMHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            return null;
        }
    }

    @Override
    public String getSortingName() {
        return sortingName;
    }
}
