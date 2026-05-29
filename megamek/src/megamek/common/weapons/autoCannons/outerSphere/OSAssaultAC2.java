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
import megamek.common.weapons.AmmoWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.OSAssaultACHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Assault AC/2 - fires a 3-projectile burst (each 2 damage, independent
 * hit), trading range for close-quarters burst output. Heat 6 / Range 5-10-15 / 6t / 2 crit.
 */
public class OSAssaultAC2 extends AmmoWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAssaultAC2() {
        super();
        name = "Assault AC/2";
        setInternalName("OSAssaultAC2");
        addLookupName("OS Assault AC/2");
        sortingName = "AC OS Assault 02";
        flags = flags.or(F_DIRECT_FIRE).or(F_BALLISTIC).or(F_MEK_WEAPON).or(F_AERO_WEAPON).or(F_TANK_WEAPON);
        ammoType = AmmoType.AmmoTypeEnum.AC_ASSAULT_OS;
        atClass = CLASS_AC;
        heat = 6;
        damage = 2;
        rackSize = 2;
        minimumRange = 0;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 6.0;
        criticalSlots = 2;
        bv = 62;
        cost = 130000;
        explosive = true;
        explosionDamage = damage;
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
            return new OSAssaultACHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
