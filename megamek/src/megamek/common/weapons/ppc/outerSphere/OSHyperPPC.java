/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.ppc.outerSphere;

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
import megamek.common.weapons.handlers.OSHyperPPCHandler;
import megamek.common.weapons.ppc.PPCWeapon;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Hyper PPC — the apex energy weapon of the OS arsenal.
 *
 * Damage 30 / Heat 30 (fire) + 10 (charge) / Range 12-24-36 / min 3, -1 ToHit, 10t / 7 crit.
 *
 * Mechanically identical to the Hyper Laser family: 1 turn to enter "Charging" mode,
 * generates charge heat each subsequent turn while charging, fires for full damage when
 * discharged, then enters a 1-turn cooldown before being usable again.
 *
 * Range 12-24-36 matches Large Hyper Laser — both share the "naval pocket version"
 * lore that justifies extreme range relative to ground-scale weapons.
 */
public class OSHyperPPC extends PPCWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSHyperPPC() {
        super();
        name = "Hyper PPC";
        setInternalName("OSHyperPPC");
        addLookupName("OS Hyper PPC");
        sortingName = "PPC OS 6 Hyper 2 Std 1 Std";
        heat = 30;
        damage = 30;
        toHitModifier = -1;
        minimumRange = 3;
        shortRange = 12;
        mediumRange = 24;
        longRange = 36;
        extremeRange = 50;
        waterShortRange = 8;
        waterMediumRange = 16;
        waterLongRange = 24;
        waterExtremeRange = 33;
        tonnage = 10.0;
        criticalSlots = 7;
        bv = 824;
        cost = 1000000;
        shortAV = 30;
        medAV = 30;
        longAV = 30;
        extAV = 30;
        maxRange = RANGE_EXT;
        explosive = true;
        explosionDamage = 15;
        flags = flags.or(F_HYPER);
        // Hyper PPC charge mechanic: weapon must spend at least one turn in "Charging" mode
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
            return new OSHyperPPCHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
