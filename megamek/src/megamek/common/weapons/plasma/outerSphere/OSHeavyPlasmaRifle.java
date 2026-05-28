/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.plasma.outerSphere;

import static megamek.common.game.IGame.LOGGER;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.alphaStrike.AlphaStrikeElement;
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
import megamek.common.weapons.handlers.plasma.OSHeavyPlasmaRifleHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Heavy Plasma Rifle (School B - magnetic-confinement torus)
 * Damage 15 (+2d6 heat) / Heat 12 / Range 5-10-15 / 7t / 3 crit / +1 to-hit / ammo-fed
 *
 * The self-confining toroidal plasma bolt arrives dense and coherent, carrying
 * kinetic momentum on top of its thermal load, which lets it break the 10-point
 * plasma damage ceiling that the rest of the universe never cracked. A short-ranged,
 * hot, slightly inaccurate armor-cracker.
 */
public class OSHeavyPlasmaRifle extends AmmoWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyPlasmaRifle() {
        name = "Heavy Plasma Rifle";
        setInternalName("OSHeavyPlasmaRifle");
        addLookupName("OS Heavy Plasma Rifle");
        sortingName = "Plasma D Rifle Heavy";
        heat = 12;
        damage = 15;
        rackSize = 1;
        ammoType = AmmoType.AmmoTypeEnum.PLASMA_RIFLE_HEAVY_OS;
        minimumRange = WEAPON_NA;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 7.0;
        criticalSlots = 3;
        toHitModifier = 1;
        flags = flags.or(F_MEK_WEAPON).or(F_TANK_WEAPON).or(F_AERO_WEAPON)
              .or(F_PLASMA).or(F_DIRECT_FIRE).or(F_ENERGY);
        bv = 315;
        cost = 400000;
        shortAV = 15;
        medAV = 15;
        maxRange = RANGE_MED;
        atClass = CLASS_PLASMA;
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
            return new OSHeavyPlasmaRifleHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }

    @Override
    public int getAlphaStrikeHeatDamage(int rangeband) {
        return (rangeband <= AlphaStrikeElement.RANGE_BAND_MEDIUM) ? 3 : 0;
    }
}
