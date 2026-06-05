/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.missiles.outerSphere;

import java.io.Serial;

import megamek.common.SimpleTechLevel;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.enums.AvailabilityValue;
import megamek.common.enums.Faction;
import megamek.common.enums.TechBase;
import megamek.common.enums.TechRating;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.handlers.AttackHandler;
import megamek.common.weapons.handlers.OSDragonPiercerHandler;
import megamek.common.weapons.missiles.thunderbolt.Thunderbolt5Weapon;
import megamek.server.totalWarfare.TWGameManager;

/** Outer Sphere Dragon Piercer 5 - concentrated single-missile strike with armor-piercing breach (8+). */
public class OSDragonPiercer5 extends Thunderbolt5Weapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSDragonPiercer5() {
        super();
        name = "Dragon Piercer 5";
        setInternalName("OSDragonPiercer5");
        addLookupName("OS Dragon Piercer 5");
        sortingName = "Dragon Piercer OS 1 Base 05";
        tonnage = 3.0;
        bv = 64;
        cost = 50000;
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
    public AttackHandler getCorrectHandler(ToHitData toHit, WeaponAttackAction waa, Game game,
          TWGameManager manager) {
        try {
            return new OSDragonPiercerHandler(toHit, waa, game, manager);
        } catch (EntityLoadingException ignored) {
            return null;
        }
    }

    @Override
    public String getSortingName() {
        return "Missile OS 6 1 1 05";
    }
}
