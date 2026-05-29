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
 * Outer Sphere Assault AC/10 - fires a 3-projectile burst (each 9 damage, independent hit).
 * Heat 12 / Range 4-8-12 / 12t / 6 crit.
 */
public class OSAssaultAC10 extends AmmoWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAssaultAC10() {
        super();
        name = "Assault AC/10";
        setInternalName("OSAssaultAC10");
        addLookupName("OS Assault AC/10");
        sortingName = "AC OS Assault 10";
        flags = flags.or(F_DIRECT_FIRE).or(F_BALLISTIC).or(F_MEK_WEAPON).or(F_AERO_WEAPON).or(F_TANK_WEAPON);
        ammoType = AmmoType.AmmoTypeEnum.AC_ASSAULT_OS;
        atClass = CLASS_AC;
        heat = 12;
        damage = 9;
        rackSize = 10;
        minimumRange = 0;
        shortRange = 4;
        mediumRange = 8;
        longRange = 12;
        extremeRange = 16;
        tonnage = 12.0;
        criticalSlots = 6;
        bv = 239;
        cost = 360000;
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
