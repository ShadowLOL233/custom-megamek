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
import megamek.common.weapons.missiles.thunderbolt.Thunderbolt15Weapon;
import megamek.server.totalWarfare.TWGameManager;

/** Outer Sphere Dragon Piercer 15 - concentrated single-missile strike with armor-piercing breach (8+). */
public class OSDragonPiercer15 extends Thunderbolt15Weapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSDragonPiercer15() {
        super();
        name = "Dragon Piercer 15";
        setInternalName("OSDragonPiercer15");
        addLookupName("OS Dragon Piercer 15");
        sortingName = "Dragon Piercer OS 1 Base 15";
        tonnage = 11.0;
        bv = 229;
        cost = 325000;
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
        return "Missile OS 6 1 1 15";
    }
}
