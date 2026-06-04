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
 * Outer Sphere Assault AC/20 - fires a 3-projectile burst (each 14 damage, independent hit).
 * The heaviest-hitting close-range burst weapon. Heat 12 / Range 3-6-9 / 14t / 9 crit.
 */
public class OSAssaultAC20 extends AmmoWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSAssaultAC20() {
        super();
        name = "Assault AC/20";
        setInternalName("OSAssaultAC20");
        addLookupName("OS Assault AC/20");
        sortingName = "AC OS 1 Std 2 ImpAsl 20";
        flags = flags.or(F_DIRECT_FIRE).or(F_BALLISTIC).or(F_MEK_WEAPON).or(F_AERO_WEAPON).or(F_TANK_WEAPON);
        ammoType = AmmoType.AmmoTypeEnum.AC_ASSAULT_OS;
        atClass = CLASS_AC;
        heat = 12;
        damage = 14;
        rackSize = 20;
        minimumRange = 0;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 14.0;
        criticalSlots = 9;
        bv = 336;
        cost = 520000;
        explosive = true;
        explosionDamage = damage;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
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
