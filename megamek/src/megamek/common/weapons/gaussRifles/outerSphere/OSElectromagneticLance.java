/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.gaussRifles.outerSphere;

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
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.units.Entity;
import megamek.common.weapons.gaussRifles.GaussWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.ac.ACAPHandler;
import megamek.common.weapons.handlers.ac.ACFlakHandler;
import megamek.common.weapons.handlers.ac.OSEMLanceAPDSHandler;
import megamek.common.weapons.handlers.ac.OSEMLanceFlechetteHandler;
import megamek.common.weapons.handlers.ac.OSEMLanceIncendiaryHandler;
import megamek.common.weapons.handlers.ac.OSEMLancePrecisionHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Electromagnetic Lance — Advance-tier coil-accelerated ferrous-slug supergun (see
 * OS_TECHBASE_DEVELOPMENT_PLAN §8). The flexible-premium counterpart to the Coil Augmented Railgun: it trades
 * the railgun's raw single-slug apex for munition variety via a power-gate.
 *
 * <p><b>Power-gate:</b> the loaded round selects the coil's output. <b>Full power</b> keeps the long reach and
 * carries inherent armor-piercing (through-armor crit, via {@link ACAPHandler}) — the default kinetic slug plus
 * the APDS discarding-sabot penetrator (~80% damage, stronger penetration). <b>Low power</b> lets fragile
 * payload rounds survive the launch — Flak (anti-air fragments), Flechette (anti-infantry), Precision (cancels
 * up to −2 of the target movement modifier) and Incendiary/HE (sets fires) — but both range and damage drop,
 * and there is no AP. The range profile is switched in {@link #getRanges}; the low-power damage penalty lives in
 * the payload handlers.
 */
public class OSElectromagneticLance extends GaussWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSElectromagneticLance() {
        super();
        name = "Electromagnetic Lance";
        setInternalName("OSElectromagneticLance");
        addLookupName("OS Electromagnetic Lance");
        addLookupName("Electromagnetic Lance");
        addLookupName("EM Lance");
        sortingName = "Gauss OS 5 Advance EMLance";
        heat = 3;
        damage = 20;
        ammoType = AmmoType.AmmoTypeEnum.EM_LANCE_OS;
        minimumRange = 3;
        shortRange = 9;
        mediumRange = 18;
        longRange = 26;
        extremeRange = 34;
        tonnage = 18.0;
        criticalSlots = 11;
        bv = 480;
        cost = 900000;
        shortAV = 20;
        medAV = 20;
        longAV = 20;
        extAV = 20;
        maxRange = RANGE_EXT;
        explosionDamage = 20;
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

    /** @return true for the throttled-down payload rounds (fragile; survive only a low-power launch). */
    private static boolean isLowPower(AmmoType at) {
        return at.getMunitionType().contains(AmmoType.Munitions.M_FLAK)
              || at.getMunitionType().contains(AmmoType.Munitions.M_FLECHETTE)
              || at.getMunitionType().contains(AmmoType.Munitions.M_PRECISION)
              || at.getMunitionType().contains(AmmoType.Munitions.M_INCENDIARY_AC);
    }

    @Override
    public int[] getRanges(Mounted<?> weapon, Mounted<?> ammo) {
        // Power-gate: a low-power launch keeps a fragile payload round intact but collapses the reach; the
        // full-power kinetic slug (the default) keeps the long-barrel profile from the weapon's own fields.
        if ((ammo != null) && (ammo.getType() instanceof AmmoType at) && isLowPower(at)) {
            return new int[] { 0, 5, 10, 15, 20 };
        }
        return super.getRanges(weapon, ammo);
    }

    @Override
    @Nullable
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            Entity entity = game.getEntity(waa.getEntityId());
            if (entity != null) {
                Object item = entity.getEquipment(waa.getWeaponId()).getLinked().getType();
                if (item instanceof AmmoType at) {
                    if (at.getMunitionType().contains(AmmoType.Munitions.M_FLAK)) {
                        return new ACFlakHandler(toHit, waa, game, manager);
                    }
                    if (at.getMunitionType().contains(AmmoType.Munitions.M_FLECHETTE)) {
                        return new OSEMLanceFlechetteHandler(toHit, waa, game, manager);
                    }
                    if (at.getMunitionType().contains(AmmoType.Munitions.M_INCENDIARY_AC)) {
                        return new OSEMLanceIncendiaryHandler(toHit, waa, game, manager);
                    }
                    if (at.getMunitionType().contains(AmmoType.Munitions.M_PRECISION)) {
                        return new OSEMLancePrecisionHandler(toHit, waa, game, manager);
                    }
                    if (at.getMunitionType().contains(AmmoType.Munitions.M_ARMOR_PIERCING)) {
                        return new OSEMLanceAPDSHandler(toHit, waa, game, manager);
                    }
                }
            }
            // Full-power kinetic slug (default): inherent armor-piercing through-armor crit.
            return new ACAPHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
