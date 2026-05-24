/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.lasers.outerSphere;

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
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.OSHyperLaserHandler;
import megamek.common.weapons.lasers.LaserWeapon;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Medium Hyper Laser
 * Damage 12 / Heat 15 (charge 5 + fire 15) / Range 4-9-14 / -1 ToHit, 4t / 3 crit
 */
public class OSHyperLaserMedium extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHyperLaserMedium() {
        super();
        name = "Medium Hyper Laser";
        setInternalName("OSMediumHyperLaser");
        addLookupName("OS Medium Hyper Laser");
        sortingName = "Laser Hyper B";
        heat = 15;
        damage = 15;
        toHitModifier = -1;
        shortRange = 7;
        mediumRange = 14;
        longRange = 22;
        extremeRange = 30;
        waterShortRange = 5;
        waterMediumRange = 9;
        waterLongRange = 15;
        waterExtremeRange = 20;
        tonnage = 4.0;
        criticalSlots = 3;
        bv = 180;
        cost = 350000;
        shortAV = 15;
        medAV = 15;
        longAV = 15;
        maxRange = RANGE_LONG;
        explosionDamage = 10;
        explosive = true;
        flags = flags.or(F_LASER).or(F_DIRECT_FIRE).or(F_HYPER);
        // Hyper Laser charge mechanic: weapon must spend at least one turn in "Charging" mode
        // generating charge heat before it can discharge in the firing turn.
        setModes("Off", "Charging");
        setInstantModeSwitch(false);
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F)
              .setISAdvancement(3140, 3145, 3150, DATE_NONE, DATE_NONE)
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
            return new OSHyperLaserHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
