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
import megamek.common.weapons.handlers.OSUltraLBXHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Ultra LB 5-X - an LB-X with an Ultra-style double tap (Single/Ultra modes)
 * plus future specialized munitions. Heat 1/shot / Range 8-16-24 / 6t / 4 crit.
 */
public class OSUltraLB5XAC extends LBXACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSUltraLB5XAC() {
        super();
        name = "Ultra LB 5-X";
        setInternalName("OSUltraLB5XAC");
        addLookupName("OS Ultra LB 5-X AC");
        addLookupName("OSAdvanceLB5XAC");
        addLookupName("OS Advance LB 5-X AC");
        addLookupName("Advance LB 5-X");
        sortingName = "AC OS 2 LBX 3 Ult 05";
        setModes(new String[] { MODE_AC_SINGLE, MODE_UAC_ULTRA });
        ammoType = AmmoType.AmmoTypeEnum.LBX_OS;
        heat = 1;
        damage = 5;
        rackSize = 5;
        minimumRange = 0;
        shortRange = 8;
        mediumRange = 16;
        longRange = 24;
        extremeRange = 32;
        tonnage = 6.0;
        criticalSlots = 4;
        bv = 150;
        cost = 420000;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3045, 3050, 3055, DATE_NONE, DATE_NONE)
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
            return new OSUltraLBXHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
