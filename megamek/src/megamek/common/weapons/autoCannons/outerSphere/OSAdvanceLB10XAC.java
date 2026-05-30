/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.autoCannons.outerSphere;

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
import megamek.common.weapons.autoCannons.LBXACWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.OSAdvanceLBXHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Advance LB 10-X - LB-X with an Ultra-style double tap plus future
 * specialized munitions. Heat 2/shot / Range 7-14-21 / 9t / 5 crit.
 */
public class OSAdvanceLB10XAC extends LBXACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAdvanceLB10XAC() {
        super();
        name = "Advance LB 10-X";
        setInternalName("OSAdvanceLB10XAC");
        addLookupName("OS Advance LB 10-X AC");
        sortingName = "AC OS 2 LBX 3 Adv 10";
        setModes(new String[] { MODE_AC_SINGLE, MODE_UAC_ULTRA });
        ammoType = AmmoType.AmmoTypeEnum.LBX_OS;
        heat = 2;
        damage = 10;
        rackSize = 10;
        minimumRange = 0;
        shortRange = 7;
        mediumRange = 14;
        longRange = 21;
        extremeRange = 28;
        tonnage = 9.0;
        criticalSlots = 5;
        bv = 275;
        cost = 680000;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
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
            return new OSAdvanceLBXHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
