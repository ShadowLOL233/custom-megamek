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
import megamek.common.weapons.handlers.lrm.OSDoubleFireLRMHandler;
import megamek.common.weapons.handlers.srm.OSDoubleFireSRMHandler;
import megamek.common.weapons.missiles.MMLWeapon;
import megamek.server.totalWarfare.TWGameManager;

/** Outer Sphere Heavy MML 11 - double-fire MML: two volleys per turn (2x ammo, 2x heat). */
public class OSHeavyMML11 extends MMLWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyMML11() {
        super();
        name = "Heavy MML 11";
        setInternalName("OSHeavyMML11");
        addLookupName("OS Heavy MML-11");
        addLookupName("OS Heavy MML 11");
        sortingName = "Missile OS 4 2 1 11";
        heat = 12;
        rackSize = 11;
        minimumRange = 6;
        shortRange = 7;
        mediumRange = 14;
        longRange = 21;
        extremeRange = 28;
        tonnage = 9.0;
        criticalSlots = 6;
        bv = 200;
        cost = 330000;
        shortAV = 6;
        medAV = 6;
        longAV = 6;
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
                return new OSDoubleFireLRMHandler(toHit, waa, game, manager);
            }
            return new OSDoubleFireSRMHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            return null;
        }
    }
}
