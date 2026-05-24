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
 * Outer Sphere Small Hyper Laser
 * Damage 7 / Heat 9 (charge 3 + fire 9) / Range 2-6-8 / -1 ToHit, 2t / 1 crit
 */
public class OSHyperLaserSmall extends LaserWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHyperLaserSmall() {
        super();
        name = "Small Hyper Laser";
        setInternalName("OSSmallHyperLaser");
        addLookupName("OS Small Hyper Laser");
        sortingName = "Laser Hyper A";
        heat = 9;
        damage = 9;
        toHitModifier = -1;
        shortRange = 4;
        mediumRange = 8;
        longRange = 14;
        extremeRange = 20;
        waterShortRange = 2;
        waterMediumRange = 5;
        waterLongRange = 9;
        waterExtremeRange = 13;
        tonnage = 2.0;
        criticalSlots = 1;
        bv = 60;
        cost = 100000;
        shortAV = 9;
        medAV = 9;
        longAV = 9;
        maxRange = RANGE_LONG;
        explosionDamage = 6;
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
