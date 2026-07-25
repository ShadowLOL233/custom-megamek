/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.gaussRifles.outerSphere;

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
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.gaussRifles.GaussWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.OSCoilAugmentedRailgunHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Coil Augmented Railgun — Advance-tier apex kinetic (see OS_TECHBASE_DEVELOPMENT_PLAN §8).
 *
 * <p>Coilgun pre-accelerator + short rail final stage: the longest-reaching, hardest-hitting single-slug
 * kinetic in the OS line, with inherent armor-piercing. A pure penetrator — one AP munition, no variety.
 *
 * <p><b>Long-barrel</b> (default): 30 flat, inherent AP (through-armor crit); the barrel fires stably for
 * {@link #STABLE_SHOTS} shots per battle, after which it takes +2 to-hit and jams on a natural-2 attack roll,
 * each jam adding +1 (scrapped at {@link #SCRAP_AT}). <b>Barrel-shed</b> (one-way {@value #MODE_SHED} mode):
 * jettison the rail barrel for a fully stable short-range 25-damage weapon with no AP and no wear.
 * The wear / jam / scrap / AP-toggle logic lives in {@link OSCoilAugmentedRailgunHandler}; the +2 to-hit is
 * applied in {@code ComputeToHit}.
 */
public class OSCoilAugmentedRailgun extends GaussWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    /** Shots fired stably per battle before the barrel wears (tunable within 9–12). */
    public static final int STABLE_SHOTS = 12;
    /** Barrel-wear degradation stack at which the weapon is scrapped. */
    public static final int SCRAP_AT = 5;
    /** One-way weapon mode that jettisons the rail barrel. */
    public static final String MODE_SHED = "Shed Barrel";

    public OSCoilAugmentedRailgun() {
        super();
        name = "Coil Augmented Railgun";
        setInternalName("OSCoilAugmentedRailgun");
        addLookupName("OS Coil Augmented Railgun");
        addLookupName("Coil Augmented Railgun");
        sortingName = "Gauss OS 6 Advance Railgun";
        heat = 6;
        damage = 30;
        ammoType = AmmoType.AmmoTypeEnum.RAILGUN_OS;
        minimumRange = 0;
        shortRange = 9;
        mediumRange = 18;
        longRange = 27;
        extremeRange = 36;
        tonnage = 22.0;
        criticalSlots = 13;
        bv = 700;
        cost = 1100000;
        shortAV = 30;
        medAV = 30;
        longAV = 30;
        extAV = 30;
        maxRange = RANGE_EXT;
        explosionDamage = 30;
        setModes(new String[] { "Normal", MODE_SHED });
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3060, 3070, 3080, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(Faction.LEGION)
              .setProductionFactions(Faction.LEGION)
              .setStaticTechLevel(SimpleTechLevel.ADVANCED);
    }

    /**
     * @return {@code true} if this mounted railgun is running without its rail barrel — either committed
     *       (one-way flag set after firing shed) or with the {@value #MODE_SHED} mode currently selected.
     */
    public static boolean isBarrelShed(Mounted<?> mounted) {
        return mounted.isRailgunBarrelShed()
              || ((mounted.curMode() != null) && MODE_SHED.equals(mounted.curMode().getName()));
    }

    @Override
    public int[] getRanges(Mounted<?> weapon, Mounted<?> ammo) {
        if (isBarrelShed(weapon)) {
            // Coil-only, short-barreled: min 0 / S 4 / M 6 / L 9 / E 12.
            return new int[] { 0, 4, 6, 9, 12 };
        }
        return super.getRanges(weapon, ammo);
    }

    @Override
    @Nullable
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new OSCoilAugmentedRailgunHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            return null;
        }
    }
}
