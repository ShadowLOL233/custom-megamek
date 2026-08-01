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

/** Outer Sphere Streak MRM 30 - MRM with lock-on full salvo. Heavy/bulky to force a real tradeoff. */
public class OSStreakMRM30 extends MRMWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSStreakMRM30() {
        super();
        name = "Streak MRM 30";
        setInternalName("OSStreakMRM30");
        addLookupName("OS Streak MRM-30");
        addLookupName("OS Streak MRM 30");
        sortingName = "Missile OS 2 4 1 30";
        toHitModifier = 0;
        heat = 10;
        rackSize = 30;
        minimumRange = 0;
        shortRange = 3;
        mediumRange = 13;
        longRange = 16;
        extremeRange = 20;
        tonnage = 17.0;
        criticalSlots = 7;
        bv = 360;
        cost = 360000;
        shortAV = 30;
        medAV = 30;
        longAV = 30;
        maxRange = RANGE_LONG;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3060, 3070, 3080, DATE_NONE, DATE_NONE)
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
