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
import megamek.server.totalWarfare.TWGameManager;

/**
 * Outer Sphere Heavy SRM double-fire handler. The launcher fires two missile volleys in a single
 * turn, consuming two rounds of ammunition (the weapon's heat value already reflects the doubled
 * cost). Each volley rolls the cluster table independently, so total hits = sum of two cluster
 * rolls, both benefiting from any linked Artemis IV FCS or Narc beacon.
 */
public class OSDoubleFireSRMHandler extends SRMHandler {
    @Serial
    private static final long serialVersionUID = 1L;

    public OSDoubleFireSRMHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m)
          throws EntityLoadingException {
        super(t, w, g, m);
    }

    @Override
    protected void useAmmo() {
        super.useAmmo();
        // Consume the second volley's round, mirroring the parent's single-round consumption.
        if (ammo == null) {
            return;
        }
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
        if (!target.isConventionalInfantry()) {
            hits += super.calcHits(vPhaseReport);
        }
        return hits;
    }
}
