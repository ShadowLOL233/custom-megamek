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
import megamek.common.weapons.handlers.plasma.PlasmaCannonHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Plasma Cannon
 * Damage 0 to meks (+2d6 heat), 3d6 cluster vs soft / Heat 6 / Range 6-12-18 / 4t / 2 crit / ammo-fed
 *
 * Diffuse-burst plasma. Cannot penetrate mek armor, but floods the target with
 * heat and shreds infantry/vehicles. Area-suppression counterpart to the focused
 * Plasma Rifle.
 */
public class OSPlasmaCannon extends AmmoWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSPlasmaCannon() {
        name = "Plasma Cannon";
        setInternalName("OSPlasmaCannon");
        addLookupName("OS Plasma Cannon");
        sortingName = "Plasma OS 2 Cannon 1 Std";
        heat = 6;
        damage = DAMAGE_VARIABLE;
        rackSize = 2;
        ammoType = AmmoType.AmmoTypeEnum.PLASMA_CANNON_OS;
        minimumRange = WEAPON_NA;
        shortRange = 6;
        mediumRange = 12;
        longRange = 18;
        extremeRange = 24;
        tonnage = 4.0;
        criticalSlots = 2;
        flags = flags.or(F_MEK_WEAPON).or(F_TANK_WEAPON).or(F_AERO_WEAPON)
              .or(F_PLASMA).or(F_DIRECT_FIRE).or(F_ENERGY);
        bv = 190;
        cost = 340000;
        shortAV = 10;
        medAV = 10;
        maxRange = RANGE_MED;
        atClass = CLASS_PLASMA;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(2805, 2820, 2840, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }

    @Override
    public @Nullable AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new PlasmaCannonHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }

    @Override
    public int getAlphaStrikeHeatDamage(int rangeband) {
        return (rangeband <= AlphaStrikeElement.RANGE_BAND_LONG) ? 6 : 0;
    }

    @Override
    public double getBattleForceDamage(int range, Mounted<?> linked) {
        return 0;
    }
}
