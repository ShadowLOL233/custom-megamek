/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 */

package megamek.common.weapons.autoCannons.innerSphere;

import java.io.Serial;

import megamek.common.equipment.AmmoType;
import megamek.common.weapons.autoCannons.ACWeapon;

/**
 * Base marker class for Inner Sphere Burst-Fire ACs — same-BV flavor sidegrades of the standard AC
 * (MW5-style "AC/N BF").
 *
 * <p>Mechanically identical to the standard AC in every stat; the only difference is a range-bracket
 * to-hit gradient (+0 short/med, -1 at long range) applied in {@code ComputeToHit} via {@code instanceof}
 * this class (the same approach as PPC-X). No custom handler is needed — the modifier lives in the
 * to-hit computation, which runs before any weapon handler.</p>
 */
public abstract class ISBurstFireACWeapon extends ACWeapon {
    @Serial
    private static final long serialVersionUID = 1L;

    public ISBurstFireACWeapon() {
        super();
        ammoType = AmmoType.AmmoTypeEnum.AC_BF;
    }
}
