/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.mgs.outerSphere;

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
import megamek.common.equipment.Mounted;
import megamek.common.equipment.WeaponType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.AmmoWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.MGAWeaponHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Light Machine Gun Array.
 * 0.25t / 1 crit / Links 2-4 OS Light MGs. BV calculated in calculateBV().
 */
public class OSLightMGA extends AmmoWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSLightMGA() {
        super();
        name = "Light Machine Gun Array";
        setInternalName("OSLightMGA");
        addLookupName("OS Light Machine Gun Array");
        addLookupName("OS LMGA");
        sortingName = "MGA OS 1 Light";
        heat = 0;
        damage = 1;
        infDamageClass = WeaponType.WEAPON_BURST_1D6;
        rackSize = 1;
        ammoType = AmmoType.AmmoTypeEnum.MG_LIGHT_OS;
        minimumRange = WEAPON_NA;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 0.25;
        criticalSlots = 1;
        bv = 0;
        flags = flags.or(F_MEK_WEAPON).or(F_TANK_WEAPON).or(F_AERO_WEAPON)
              .or(F_PROTO_WEAPON).or(F_BALLISTIC).or(F_BURST_FIRE).or(F_MGA);
        cost = 8000;
        String[] modeStrings = { "Linked", "Off" };
        setModes(modeStrings);
        instantModeSwitch = false;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.E)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3128, 3133, 3138, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.STANDARD);
    }

    @Override
    @Nullable
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new MGAWeaponHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }

    @Override
    public double getBattleForceDamage(int range, Mounted<?> fcs) {
        return 0;
    }
}
