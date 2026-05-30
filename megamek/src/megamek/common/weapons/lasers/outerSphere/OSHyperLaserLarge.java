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
 * Outer Sphere Hyper Laser - extreme-range charge-up laser.
 * Damage 25 / Heat 25 / Range 12-24-36 / -1 ToHit / 8t / 6 crit / BV 620.
 */
public class OSHyperLaserLarge extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHyperLaserLarge() {
        super();
        name = "Hyper Laser";
        setInternalName("OSLargeHyperLaser");
        addLookupName("OS Hyper Laser");
        addLookupName("Hyper Laser");
        addLookupName("OS Large Hyper Laser");
        sortingName = "Laser OS 12 Hyper 1 Std D";
        heat = 25;
        damage = 25;
        toHitModifier = -1;
        shortRange = 12;
        mediumRange = 24;
        longRange = 36;
        extremeRange = 50;
        waterShortRange = 8;
        waterMediumRange = 16;
        waterLongRange = 24;
        waterExtremeRange = 33;
        tonnage = 8.0;
        criticalSlots = 6;
        bv = 620;
        cost = 900000;
        shortAV = 25;
        medAV = 25;
        longAV = 25;
        extAV = 25;
        maxRange = RANGE_EXT;
        explosionDamage = 12;
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
