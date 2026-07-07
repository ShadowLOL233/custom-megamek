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
import megamek.common.equipment.AmmoType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.StreakHandler;
import megamek.common.weapons.handlers.lrm.StreakLRMHandler;
import megamek.common.weapons.missiles.MMLWeapon;
import megamek.server.totalWarfare.TWGameManager;

/** Outer Sphere Streak MML 11 - dual-mode (LRM/SRM) launcher with lock-on full salvo. */
public class OSStreakMML11 extends MMLWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSStreakMML11() {
        super();
        name = "Streak MML 11";
        setInternalName("OSStreakMML11");
        addLookupName("OS Streak MML-11");
        addLookupName("OS Streak MML 11");
        sortingName = "Missile OS 4 4 1 11";
        flags = flags.andNot(F_ARTEMIS_COMPATIBLE);
        heat = 6;
        rackSize = 11;
        minimumRange = 6;
        shortRange = 7;
        mediumRange = 14;
        longRange = 21;
        extremeRange = 28;
        tonnage = 8.0;
        criticalSlots = 6;
        bv = 165;
        cost = 247500;
        shortAV = 11;
        medAV = 11;
        longAV = 11;
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
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            AmmoType atype = (AmmoType) game.getEntity(waa.getEntityId())
                  .getEquipment(waa.getWeaponId()).getLinked().getType();
            if (atype.hasFlag(AmmoType.F_MML_LRM)) {
                return new StreakLRMHandler(toHit, waa, game, manager);
            }
            return new StreakHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            return null;
        }
    }

    @Override
    public String getSortingName() {
        return sortingName;
    }
}
