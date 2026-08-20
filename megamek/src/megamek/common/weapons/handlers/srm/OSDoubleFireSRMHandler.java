/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.handlers.srm;

import java.io.Serial;
import java.util.Vector;

import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.equipment.AmmoMounted;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.weapons.Weapon;
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Heavy SRM Ultra-fire handler. The launcher has Single / Ultra fire modes. In Ultra it fires
 * two missile volleys in one turn — consuming two rounds and rolling the cluster table independently for
 * each volley (both benefit from a linked Artemis IV FCS or Narc). The weapon's base heat is the single-volley
 * value; the engine doubles it in Ultra via {@code Mounted.getNumShots}. The Ultra double-tap does not jam
 * (aligned with the core-rule Ultra AC change). In Single mode it is a plain SRM launcher — one volley.
 */
public class OSDoubleFireSRMHandler extends SRMHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSDoubleFireSRMHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
    }

    /** @return true when the launcher is set to fire the Ultra double volley this turn. */
    private boolean isUltra() {
        return (weapon.curMode() != null) && weapon.curMode().equals(Weapon.MODE_UAC_ULTRA);
    }

    @Override
    protected void useAmmo() {
        super.useAmmo();
        if (!isUltra() || (ammo == null)) {
            return;
        }
        // Ultra mode: consume the second volley's round (the parent consumed the first).
        if (ammo.getUsableShotsLeft() <= 0) {
            weaponEntity.loadWeaponWithSameAmmo(weapon);
            ammo = (AmmoMounted) weapon.getLinked();
        }
        if ((ammo != null) && (ammo.getUsableShotsLeft() > 0)) {
            ammo.setShotsLeft(ammo.getBaseShotsLeft() - 1);
        }
    }

    @Override
    protected int calcHits(Vector<Report> vPhaseReport) {
        int hits = super.calcHits(vPhaseReport);
        if (isUltra() && !target.isConventionalInfantry()) {
            hits += super.calcHits(vPhaseReport);
        }
        return hits;
    }
}
