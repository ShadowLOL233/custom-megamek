/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.autoCannons.outerSphere;

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
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.units.Entity;
import megamek.common.weapons.autoCannons.LBXACWeapon;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.OSUltraLBXHandler;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Ultra LB 20-X - LB-X with an Ultra-style double tap plus future
 * specialized munitions. Heat 5/shot / Range 5-10-15 / 12t / 8 crit.
 */
public class OSUltraLB20XAC extends LBXACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSUltraLB20XAC() {
        super();
        name = "Ultra LB 20-X";
        setInternalName("OSUltraLB20XAC");
        addLookupName("OS Ultra LB 20-X AC");
        addLookupName("OSAdvanceLB20XAC");
        addLookupName("OS Advance LB 20-X AC");
        addLookupName("Advance LB 20-X");
        sortingName = "AC OS 2 LBX 3 Ult 20";
        setModes(new String[] { MODE_AC_SINGLE, MODE_UAC_ULTRA });
        ammoType = AmmoType.AmmoTypeEnum.LBX_OS;
        heat = 5;
        damage = 20;
        rackSize = 20;
        minimumRange = 0;
        shortRange = 5;
        mediumRange = 10;
        longRange = 15;
        extremeRange = 20;
        tonnage = 12.0;
        criticalSlots = 8;
        bv = 475;
        cost = 1000000;
        techAdvancement.setTechBase(TechBase.OUTER_SPHERE)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(TechRating.F)
              .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
              .setISAdvancement(3045, 3050, 3055, DATE_NONE, DATE_NONE)
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
            Entity entity = game.getEntity(waa.getEntityId());
            if (entity != null) {
                Object item = entity.getEquipment(waa.getWeaponId()).getLinked().getType();
                if (item instanceof AmmoType ammoType) {
                    AttackHandler osSpecial = getOSLBSpecialHandler(ammoType, toHit, waa, game, manager);
                    if (osSpecial != null) {
                        return osSpecial;
                    }
                }
            }
            return new OSUltraLBXHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;
    }
}
