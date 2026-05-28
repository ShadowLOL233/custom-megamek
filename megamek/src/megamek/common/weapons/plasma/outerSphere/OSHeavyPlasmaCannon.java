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
import megamek.common.equipment.Mounted;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.AmmoWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.plasma.OSHeavyPlasmaCannonHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Heavy Plasma Cannon (School B - toroidal burst)
 * Damage 0 to meks (+3d6 heat), 3d6 cluster vs soft / Heat 10 / Range 6-12-18 / 6t / 3 crit / +1 to-hit / ammo-fed
 *
 * The denser toroidal burst floods 3d6 heat (vs the standard cannon's 2d6) into
 * a heat-tracking target. Still no direct armor penetration - this is a heavy
 * area heat-flood weapon.
 */
public class OSHeavyPlasmaCannon extends AmmoWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHeavyPlasmaCannon() {
        name = "Heavy Plasma Cannon";
        setInternalName("OSHeavyPlasmaCannon");
        addLookupName("OS Heavy Plasma Cannon");
        sortingName = "Plasma F Cannon Heavy";
        heat = 10;
        damage = DAMAGE_VARIABLE;
        rackSize = 2;
        ammoType = AmmoType.AmmoTypeEnum.PLASMA_CANNON_HEAVY_OS;
        minimumRange = WEAPON_NA;
        shortRange = 6;
        mediumRange = 12;
        longRange = 18;
        extremeRange = 24;
        tonnage = 6.0;
        criticalSlots = 3;
        toHitModifier = 1;
        flags = flags.or(F_MEK_WEAPON).or(F_TANK_WEAPON).or(F_AERO_WEAPON)
              .or(F_PLASMA).or(F_DIRECT_FIRE).or(F_ENERGY);
        bv = 290;
        cost = 450000;
        shortAV = 10;
        medAV = 10;
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
    public @Nullable AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new OSHeavyPlasmaCannonHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }

    @Override
    public int getAlphaStrikeHeatDamage(int rangeband) {
        return (rangeband <= AlphaStrikeElement.RANGE_BAND_LONG) ? 10 : 0;
    }

    @Override
    public double getBattleForceDamage(int range, Mounted<?> linked) {
        return 0;
    }
}
