# Outer Sphere (OS) Weapons — Quick Reference

_Auto-generated from `weapons/**/outerSphere/*.java` on 2026-06-12. Regenerate after weapon edits; do not hand-edit._

Columns: dmg = per-shot damage (`cluster` = damage-by-cluster-size, `var` = variable); rack = rackSize; min = minimum range; S/M/L/E = short/medium/long/extreme ground range (hex); rate = TechRating; lvl = static tech level; base(mechanic) = the Java superclass (encodes firing mechanic).

## Energy — Lasers

| Name | heat | dmg | rack | min | S/M/L/E | tons | crit | BV | cost | ammo | rate | lvl | base(mechanic) |
|---|--:|--:|--:|--:|:--:|--:|--:|--:|--:|---|:--:|:--:|---|
| Binary Laser System | 16 | 16 | - | - | 5/10/15/20 | 7.0 | 4 | 246 | 230k | - | F | ADVANCED | LaserWeapon |
| ER Binary Laser System | 20 | 16 | - | - | 8/15/22/30 | 7.0 | 4 | 326 | 380k | - | F | ADVANCED | LaserWeapon |
| ER Trinary Laser System | 32 | 24 | - | - | 8/15/22/30 | 11.0 | 6 | 489 | 700k | - | F | ADVANCED | LaserWeapon |
| Hyper Laser | 25 | 25 | - | - | 12/24/36/50 | 8.0 | 6 | 620 | 900k | - | G | EXPERIMENTAL | LaserWeapon |
| Trinary Laser System | 24 | 24 | - | - | 5/10/15/20 | 11.0 | 6 | 369 | 480k | - | F | ADVANCED | LaserWeapon |
| Advance ER Large Laser | 14 | 11 | - | - | 8/15/22/30 | 4.5 | 4 | 260 | 360k | - | F | ADVANCED | LaserWeapon |
| Advance ER Large Pulse Laser | 18 | 14 | - | - | 6/14/22/30 | 6.0 | 4 | 350 | 500k | - | G | EXPERIMENTAL | PulseLaserWeapon |
| ER Heavy Large Laser | 22 | 16 | - | - | 7/14/19/28 | 5.0 | 3 | 245 | 500k | - | F | ADVANCED | LaserWeapon |
| ER Large Laser | 12 | 9 | - | - | 7/14/19/28 | 4.5 | 3 | 170 | 200k | - | E | STANDARD | LaserWeapon |
| ER Large Pulse Laser | 14 | 10 | - | - | 6/12/17/24 | 6.5 | 3 | 220 | 260k | - | F | ADVANCED | PulseLaserWeapon |
| Heavy Large Laser | 18 | 16 | - | - | 5/10/15/20 | 4.0 | 3 | 244 | 350k | - | E | STANDARD | LaserWeapon |
| Heavy Large Pulse Laser | 14 | 12 | - | - | 3/6/9/12 | 6.0 | 2 | 180 | 350k | - | F | ADVANCED | PulseLaserWeapon |
| Improve ER Large Laser | 12 | 10 | - | - | 8/15/22/30 | 4.0 | 1 | 230 | 280k | - | E | STANDARD | LaserWeapon |
| Improve ER Large Pulse Laser | 14 | 11 | - | - | 6/14/22/30 | 6.5 | 4 | 240 | 350k | - | E | STANDARD | PulseLaserWeapon |
| Improve Heavy Large Laser | 18 | 16 | - | - | 5/10/15/20 | 4.0 | 3 | 298 | 450k | - | E | STANDARD | LaserWeapon |
| Improve Heavy Large Pulse Laser | 14 | 13 | - | - | 5/10/15/20 | 6.5 | 3 | 240 | 500k | - | F | ADVANCED | PulseLaserWeapon |
| Improve Large Laser | 8 | 9 | - | - | 5/10/15/20 | 4.0 | 1 | 135 | 130k | - | E | STANDARD | LaserWeapon |
| Improve Large Pulse Laser | 10 | 9 | - | - | 3/7/10/15 | 6.0 | 2 | 130 | 200k | - | E | STANDARD | PulseLaserWeapon |
| Large Laser | 8 | 8 | - | - | 5/10/15/20 | 5.0 | 2 | 123 | 100k | - | E | STANDARD | LaserWeapon |
| Advance ER Medium Laser | 6 | 8 | - | - | 5/10/15/20 | 1.0 | 1 | 145 | 200k | - | F | ADVANCED | LaserWeapon |
| Advance ER Medium Pulse Laser | 12 | 10 | - | - | 4/8/12/16 | 2.0 | 2 | 240 | 380k | - | G | EXPERIMENTAL | PulseLaserWeapon |
| ER Heavy Medium Laser | 12 | 10 | - | - | 4/8/12/16 | 2.0 | 2 | 105 | 180k | - | F | ADVANCED | LaserWeapon |
| ER Medium Laser | 5 | 6 | - | - | 4/8/12/16 | 1.0 | 1 | 70 | 75k | - | E | STANDARD | LaserWeapon |
| ER Medium Pulse Laser | 6 | 7 | - | - | 4/7/11/14 | 2.0 | 2 | 130 | 180k | - | F | ADVANCED | PulseLaserWeapon |
| Heavy Medium Laser | 7 | 10 | - | - | 3/6/9/12 | 1.0 | 2 | 76 | 100k | - | E | STANDARD | LaserWeapon |
| Heavy Medium Pulse Laser | 5 | 7 | - | - | 2/4/5/8 | 1.5 | 2 | 75 | 150k | - | F | ADVANCED | PulseLaserWeapon |
| Improve ER Medium Laser | 5 | 6 | - | - | 5/10/15/20 | 1.0 | 1 | 88 | 110k | - | E | STANDARD | LaserWeapon |
| Improve ER Medium Pulse Laser | 8 | 8 | - | - | 4/8/12/16 | 2.0 | 2 | 158 | 250k | - | E | STANDARD | PulseLaserWeapon |
| Improve Heavy Medium Laser | 7 | 10 | - | - | 3/6/9/12 | 1.0 | 2 | 103 | 160k | - | E | STANDARD | LaserWeapon |
| Improve Heavy Medium Pulse Laser | 5 | 8 | - | - | 3/6/9/12 | 1.5 | 2 | 100 | 220k | - | F | ADVANCED | PulseLaserWeapon |
| Improve Medium Laser | 3 | 6 | - | - | 3/6/9/12 | 1.0 | 1 | 50 | 50k | - | E | STANDARD | LaserWeapon |
| Improve Medium Pulse Laser | 4 | 6 | - | - | 3/5/7/10 | 2.0 | 1 | 53 | 80k | - | E | STANDARD | PulseLaserWeapon |
| Medium Laser | 3 | 5 | - | - | 3/6/9/12 | 1.0 | 1 | 46 | 40k | - | E | STANDARD | LaserWeapon |
| Advance ER Small Laser | 3 | 5 | - | - | 3/5/8/10 | 0.5 | 1 | 50 | 60k | - | F | ADVANCED | LaserWeapon |
| Advance ER Small Pulse Laser | 4 | 5 | - | - | 3/5/7/10 | 1.0 | 1 | 90 | 120k | - | G | EXPERIMENTAL | PulseLaserWeapon |
| ER Heavy Small Laser | 4 | 6 | - | - | 2/4/5/8 | 1.5 | 1 | 26 | 40k | - | F | ADVANCED | LaserWeapon |
| ER Small Laser | 2 | 4 | - | - | 2/4/5/8 | 0.5 | 1 | 22 | 20k | - | E | STANDARD | LaserWeapon |
| ER Small Pulse Laser | 3 | 3 | - | - | 2/4/6/8 | 1.0 | 1 | 38 | 50k | - | F | ADVANCED | PulseLaserWeapon |
| Heavy Small Laser | 3 | 6 | - | - | 1/2/3/4 | 0.5 | 1 | 15 | 20k | - | E | STANDARD | LaserWeapon |
| Heavy Small Pulse Laser | 3 | 4 | - | - | 1/2/3/4 | 0.5 | 1 | 22 | 40k | - | F | ADVANCED | PulseLaserWeapon |
| Improve ER Small Laser | 2 | 5 | - | - | 2/4/6/8 | 0.5 | 1 | 28 | 30k | - | E | STANDARD | LaserWeapon |
| Improve ER Small Pulse Laser | 3 | 4 | - | - | 3/5/7/10 | 1.0 | 1 | 62 | 75k | - | E | STANDARD | PulseLaserWeapon |
| Improve Heavy Small Laser | 3 | 6 | - | - | 1/2/3/4 | 0.5 | 1 | 19 | 25k | - | E | STANDARD | LaserWeapon |
| Improve Heavy Small Pulse Laser | 3 | 5 | - | - | 1/2/3/4 | 0.5 | 1 | 30 | 55k | - | F | ADVANCED | PulseLaserWeapon |
| Improve Small Laser | 1 | 4 | - | - | 1/2/3/4 | 0.5 | 1 | 10 | 12k | - | E | STANDARD | LaserWeapon |
| Improve Small Pulse Laser | 2 | 3 | - | - | 2/3/5/7 | 1.0 | 1 | 17 | 20k | - | E | STANDARD | PulseLaserWeapon |
| Small Laser | 1 | 3 | - | - | 1/2/3/4 | 0.5 | 1 | 9 | 11250 | - | E | STANDARD | LaserWeapon |

## Energy — PPCs

| Name | heat | dmg | rack | min | S/M/L/E | tons | crit | BV | cost | ammo | rate | lvl | base(mechanic) |
|---|--:|--:|--:|--:|:--:|--:|--:|--:|--:|---|:--:|:--:|---|
| Advance ER PPC | 15 | 15 | - | - | 7/14/23/30 | 6.0 | 2 | 412 | 450k | - | F | ADVANCED | PPCWeapon |
| ER Heavy PPC | 22 | 18 | - | - | 7/14/20/27 | 9.0 | 4 | 420 | 700k | - | F | ADVANCED | PPCWeapon |
| ER PPC | 15 | 10 | - | - | 7/14/23/34 | 7.0 | 3 | 229 | 300k | - | E | STANDARD | PPCWeapon |
| Heavy PPC | 18 | 18 | - | 3 | 6/12/18/24 | 8.0 | 4 | 380 | 500k | - | E | STANDARD | PPCWeapon |
| Heavy PPC-X | 15 | 4 | 6 | - | 7/10/13/18 | 8.0 | 4 | 350 | 600k | - | F | ADVANCED | PPCXWeapon |
| Heavy Snub-Nose PPC | 15 | var | - | 0 | 9/12/14/21 | 8.0 | 4 | 265 | 400k | - | F | ADVANCED | PPCWeapon |
| Hyper PPC | 30 | 30 | - | 3 | 12/24/36/50 | 10.0 | 7 | 824 | 1M | - | G | EXPERIMENTAL | PPCWeapon |
| Improve ER PPC | 14 | 12 | - | - | 7/14/23/30 | 7.0 | 3 | 290 | 400k | - | E | STANDARD | PPCWeapon |
| Improve PPC | 10 | 10 | - | 3 | 6/12/18/24 | 6.0 | 2 | 195 | 250k | - | E | STANDARD | PPCWeapon |
| Light PPC | 7 | 7 | - | 3 | 6/12/18/24 | 3.0 | 2 | 125 | 180k | - | E | STANDARD | PPCWeapon |
| Rotary Light PPC | 5 | 5 | - | - | 6/12/18/24 | 7.0 | 5 | 280 | 350k | - | F | ADVANCED | PPCRotaryWeapon |
| PPC | 10 | 10 | - | 3 | 6/12/18/24 | 7.0 | 3 | 176 | 200k | - | E | STANDARD | PPCWeapon |
| PPC-X | 10 | 2 | 6 | - | 7/10/13/18 | 6.0 | 3 | 205 | 350k | - | F | ADVANCED | PPCXWeapon |
| Snub-Nose PPC | 10 | var | - | 0 | 9/13/15/22 | 6.0 | 2 | 180 | 250k | - | E | STANDARD | PPCWeapon |
| Rotary Snub-Nose PPC | 8 | var | - | 0 | 8/12/15/22 | 10.0 | 6 | 360 | 480k | - | F | ADVANCED | PPCRotaryWeapon |

## Energy — Plasma

| Name | heat | dmg | rack | min | S/M/L/E | tons | crit | BV | cost | ammo | rate | lvl | base(mechanic) |
|---|--:|--:|--:|--:|:--:|--:|--:|--:|--:|---|:--:|:--:|---|
| EMP Plasma Accelerator | 8 | 0 | 1 | WEAPON_NA | 5/10/15/20 | 5.0 | 3 | 190 | 500k | PLASMA_EMP_OS | G | EXPERIMENTAL | AmmoWeapon |
| Heavy Plasma Cannon | 10 | var | 2 | WEAPON_NA | 6/12/18/24 | 6.0 | 3 | 290 | 450k | PLASMA_CANNON_HEAVY_OS | G | EXPERIMENTAL | AmmoWeapon |
| Heavy Plasma Rifle | 12 | 15 | 1 | WEAPON_NA | 5/10/15/20 | 7.0 | 3 | 315 | 400k | PLASMA_RIFLE_HEAVY_OS | G | EXPERIMENTAL | AmmoWeapon |
| Plasma Cannon | 6 | var | 2 | WEAPON_NA | 6/12/18/24 | 4.0 | 2 | 190 | 340k | PLASMA_CANNON_OS | E | STANDARD | AmmoWeapon |
| Plasma Rifle | 8 | 10 | 1 | WEAPON_NA | 6/12/18/24 | 5.0 | 2 | 250 | 280k | PLASMA_RIFLE_OS | E | STANDARD | AmmoWeapon |

## Energy — Flamers

| Name | heat | dmg | rack | min | S/M/L/E | tons | crit | BV | cost | ammo | rate | lvl | base(mechanic) |
|---|--:|--:|--:|--:|:--:|--:|--:|--:|--:|---|:--:|:--:|---|
| ER Flamer | 3 | 2 | - | - | 3/6/9/12 | 1 | 1 | 18 | 11k | - | E | STANDARD | FlamerWeapon |
| ER Heavy Flamer | 7 | 5 | - | - | 3/6/9/12 | 2 | 1 | 45 | 27k | - | E | STANDARD | FlamerWeapon |
| Heavy Flamer | 6 | 5 | - | - | 2/4/6/8 | 1.5 | 1 | 30 | 16k | - | E | STANDARD | FlamerWeapon |
| Improve Flamer | 2 | 2 | - | - | 1/2/3/4 | 0.5 | 1 | 6 | 9k | - | E | STANDARD | FlamerWeapon |

## Ballistic — Autocannons

| Name | heat | dmg | rack | min | S/M/L/E | tons | crit | BV | cost | ammo | rate | lvl | base(mechanic) |
|---|--:|--:|--:|--:|:--:|--:|--:|--:|--:|---|:--:|:--:|---|
| AC/10 | 3 | 10 | 10 | - | 5/10/15/20 | 12.0 | 6 | 123 | 200k | - | E | STANDARD | ACWeapon |
| AC/2 | 1 | 2 | 2 | 0 | 8/16/24/32 | 6.0 | 1 | 37 | 75k | - | E | STANDARD | ACWeapon |
| AC/20 | 7 | 20 | 20 | - | 3/6/9/12 | 14.0 | 9 | 178 | 300k | - | E | STANDARD | ACWeapon |
| AC/5 | 1 | 5 | 5 | 0 | 6/12/18/24 | 8.0 | 4 | 70 | 125k | - | E | STANDARD | ACWeapon |
| Advance Ultra AC/10 | 3 | 10 | 10 | 0 | 7/14/20/27 | 10.0 | 4 | 270 | 510k | - | F | EXPERIMENTAL | UACWeapon |
| Advance Ultra AC/2 | 1 | 2 | 2 | 0 | 9/18/25/34 | 6.0 | 2 | 70 | 190k | - | F | EXPERIMENTAL | UACWeapon |
| Advance Ultra AC/20 | 6 | 20 | 20 | 0 | 5/9/13/17 | 12.0 | 8 | 400 | 770k | - | F | EXPERIMENTAL | UACWeapon |
| Advance Ultra AC/5 | 1 | 5 | 5 | 0 | 8/16/24/32 | 7.0 | 4 | 150 | 320k | - | F | EXPERIMENTAL | UACWeapon |
| Assault AC/10 | 12 | 9 | 10 | 0 | 4/8/12/16 | 12.0 | 6 | 239 | 360k | AC_ASSAULT_OS | E | EXPERIMENTAL | AmmoWeapon |
| Assault AC/2 | 6 | 2 | 2 | 0 | 5/10/15/20 | 6.0 | 2 | 62 | 130k | AC_ASSAULT_OS | E | EXPERIMENTAL | AmmoWeapon |
| Assault AC/20 | 12 | 14 | 20 | 0 | 3/6/9/12 | 14.0 | 9 | 336 | 520k | AC_ASSAULT_OS | E | EXPERIMENTAL | AmmoWeapon |
| Assault AC/5 | 6 | 5 | 5 | 0 | 5/10/15/20 | 8.0 | 4 | 157 | 220k | AC_ASSAULT_OS | E | EXPERIMENTAL | AmmoWeapon |
| Improve AC/10 | 2 | 10 | 10 | 0 | 5/10/15/20 | 10.0 | 6 | 123 | 240k | AC_IMP_OS | E | STANDARD | ACWeapon |
| Improve AC/2 | 1 | 3 | 2 | 0 | 8/16/24/32 | 5.0 | 1 | 56 | 95k | AC_IMP_OS | E | STANDARD | ACWeapon |
| Improve AC/20 | 6 | 20 | 20 | 0 | 4/8/12/16 | 12.0 | 9 | 237 | 360k | AC_IMP_OS | E | STANDARD | ACWeapon |
| Improve AC/5 | 1 | 6 | 5 | 0 | 6/12/18/24 | 7.0 | 3 | 84 | 150k | AC_IMP_OS | E | STANDARD | ACWeapon |
| Improve LB 10-X | 2 | 10 | 10 | 0 | 6/12/18/24 | 10.0 | 5 | 148 | 520k | LBX_OS | E | STANDARD | LBXACWeapon |
| Improve LB 20-X | 5 | 20 | 20 | 0 | 5/10/15/20 | 12.0 | 8 | 296 | 780k | LBX_OS | E | STANDARD | LBXACWeapon |
| Improve LB 2-X | 1 | 2 | 2 | 0 | 9/18/27/36 | 4.5 | 2 | 42 | 200k | LBX_OS | E | STANDARD | LBXACWeapon |
| Improve LB 5-X | 1 | 5 | 5 | 0 | 7/14/21/28 | 6.5 | 4 | 83 | 320k | LBX_OS | E | STANDARD | LBXACWeapon |
| Improve Ultra AC/10 | 3 | 10 | 10 | 0 | 6/12/18/24 | 11.0 | 6 | 210 | 420k | AC_ULTRA | E | STANDARD | UACWeapon |
| Improve Ultra AC/2 | 1 | 2 | 2 | 0 | 9/18/25/34 | 6.0 | 2 | 56 | 160k | AC_ULTRA | E | STANDARD | UACWeapon |
| Improve Ultra AC/20 | 6 | 20 | 20 | 0 | 4/8/12/16 | 12.0 | 8 | 337 | 625k | AC_ULTRA | E | STANDARD | UACWeapon |
| Improve Ultra AC/5 | 1 | 5 | 5 | 0 | 7/14/21/28 | 8.0 | 4 | 118 | 260k | AC_ULTRA | E | STANDARD | UACWeapon |
| LB 10-X | 2 | 10 | 10 | 0 | 6/12/18/24 | 11.0 | 5 | 148 | 400k | LBX_OS | E | STANDARD | LBXACWeapon |
| LB 20-X | 5 | 20 | 20 | 0 | 4/8/12/16 | 14.0 | 11 | 237 | 600k | LBX_OS | E | STANDARD | LBXACWeapon |
| LB 2-X | 1 | 2 | 2 | 0 | 9/18/27/36 | 6.0 | 4 | 42 | 150k | LBX_OS | E | STANDARD | LBXACWeapon |
| LB 5-X | 1 | 5 | 5 | 0 | 7/14/21/28 | 8.0 | 5 | 83 | 250k | LBX_OS | E | STANDARD | LBXACWeapon |
| Rotary AC/10 | 3 | 10 | 10 | 0 | 5/10/15/20 | 14.0 | 8 | 410 | 560k | - | F | ADVANCED | RACWeapon |
| Rotary AC/2 | 1 | 2 | 2 | 0 | 7/14/21/28 | 7.0 | 3 | 185 | 200k | - | F | STANDARD | RACWeapon |
| Rotary AC/20 | 7 | 20 | 20 | 0 | 4/9/13/17 | 16.0 | 12 | 620 | 860k | - | F | ADVANCED | RACWeapon |
| Rotary AC/5 | 1 | 5 | 5 | 0 | 6/12/18/24 | 10.0 | 6 | 295 | 320k | - | F | STANDARD | RACWeapon |
| Ultra AC/10 | 4 | 10 | 10 | 0 | 6/13/18/24 | 13.0 | 7 | 210 | 320k | AC_ULTRA | E | STANDARD | UACWeapon |
| Ultra AC/2 | 1 | 2 | 2 | 0 | 8/17/25/33 | 7.0 | 3 | 56 | 120k | AC_ULTRA | E | STANDARD | UACWeapon |
| Ultra AC/20 | 8 | 20 | 20 | 0 | 3/7/10/13 | 15.0 | 9 | 281 | 480k | AC_ULTRA | E | STANDARD | UACWeapon |
| Ultra AC/5 | 1 | 5 | 5 | 0 | 6/13/20/26 | 9.0 | 5 | 112 | 200k | AC_ULTRA | E | STANDARD | UACWeapon |
| Ultra LB 10-X | 2 | 10 | 10 | 0 | 7/14/21/28 | 9.0 | 5 | 275 | 680k | LBX_OS | F | EXPERIMENTAL | LBXACWeapon |
| Ultra LB 20-X | 5 | 20 | 20 | 0 | 5/10/15/20 | 12.0 | 8 | 475 | 1M | LBX_OS | F | EXPERIMENTAL | LBXACWeapon |
| Ultra LB 5-X | 1 | 5 | 5 | 0 | 8/16/24/32 | 6.0 | 4 | 150 | 420k | LBX_OS | F | EXPERIMENTAL | LBXACWeapon |

## Ballistic — Gauss

| Name | heat | dmg | rack | min | S/M/L/E | tons | crit | BV | cost | ammo | rate | lvl | base(mechanic) |
|---|--:|--:|--:|--:|:--:|--:|--:|--:|--:|---|:--:|:--:|---|
| Anti-Personnel Gauss Rifle | 1 | 3 | - | 0 | 3/6/9/12 | 0.5 | 1 | 21 | 9k | GAUSS_AP_OS | E | STANDARD | GaussWeapon |
| Advance Gauss Rifle | 1 | 15 | - | 2 | 9/18/27/40 | 10.0 | 5 | 370 | 420k | GAUSS_OS | F | EXPERIMENTAL | GaussWeapon |
| Advance Heavy Gauss Rifle | 4 | 25 | 25 | 3 | 7/14/21/32 | 17.0 | 9 | 510 | 850k | GAUSS_HEAVY_OS | F | ADVANCED | GaussWeapon |
| Gauss Rifle | 1 | 15 | - | 2 | 7/15/22/33 | 12.0 | 6 | 320 | 300k | GAUSS_OS | E | STANDARD | GaussWeapon |
| HAG/20 | 3 | - | 20 | 2 | 9/17/25/33 | 7.0 | 6 | 280 | 400k | HAG_OS | E | STANDARD | HAGWeapon |
| HAG/30 | 5 | - | 30 | 2 | 9/17/25/33 | 11.0 | 8 | 420 | 500k | HAG_OS | E | STANDARD | HAGWeapon |
| HAG/40 | 7 | - | 40 | 2 | 9/17/25/33 | 14.0 | 10 | 560 | 600k | HAG_OS | E | STANDARD | HAGWeapon |
| Heavy HAG/30 | 8 | - | 30 | 2 | 10/20/30/40 | 15.0 | 10 | 520 | 720k | HAG_OS | G | EXPERIMENTAL | HAGWeapon |
| Heavy HAG/40 | 10 | - | 40 | 2 | 10/20/30/40 | 19.0 | 13 | 690 | 950k | HAG_OS | G | EXPERIMENTAL | HAGWeapon |
| Heavy Gauss Rifle | 2 | var | 25 | 4 | 6/13/20/30 | 16.0 | 10 | 360 | 520k | GAUSS_HEAVY_OS | E | STANDARD | GaussWeapon |
| Heavy LB-X Gauss Rifle | 2 | 20 | 20 | 3 | 6/13/20/30 | 19.0 | 11 | 420 | 700k | - | F | ADVANCED | OSLBXGaussWeapon |
| Improve Gauss Rifle | 1 | 15 | - | 2 | 8/16/24/36 | 11.0 | 6 | 340 | 340k | GAUSS_OS | E | STANDARD | GaussWeapon |
| Improve Heavy Gauss Rifle | 3 | 22 | 22 | 3 | 7/13/20/30 | 18.0 | 10 | 430 | 720k | GAUSS_HEAVY_OS | E | STANDARD | GaussWeapon |
| Improve LB-X Gauss Rifle | 1 | 15 | 15 | 2 | 8/16/24/36 | 13.0 | 6 | 330 | 480k | - | E | STANDARD | OSLBXGaussWeapon |
| LB-X Gauss Rifle | 1 | 15 | 15 | 2 | 7/15/22/33 | 15.0 | 7 | 300 | 420k | - | E | STANDARD | OSLBXGaussWeapon |
| Light Gauss Rifle | 1 | 8 | 8 | 3 | 8/17/25/37 | 10.0 | 4 | 165 | 300k | GAUSS_LIGHT_OS | E | STANDARD | GaussWeapon |
| Magshot Gauss Rifle | 1 | 3 | - | 0 | 4/8/12/16 | 0.5 | 1 | 27 | 9k | GAUSS_MAGSHOT_OS | E | STANDARD | GaussWeapon |
| Super-Heavy Gauss Rifle | 5 | var | 35 | 4 | 6/13/20/30 | 22.0 | 14 | 582 | 1100k | GAUSS_HEAVY_OS | G | EXPERIMENTAL | GaussWeapon |
| Ultra-Light Gauss Rifle | 1 | 6 | 6 | 3 | 7/15/22/33 | 7.0 | 3 | 120 | 260k | GAUSS_LIGHT_OS | E | ADVANCED | GaussWeapon |

## Ballistic — Machine Guns

| Name | heat | dmg | rack | min | S/M/L/E | tons | crit | BV | cost | ammo | rate | lvl | base(mechanic) |
|---|--:|--:|--:|--:|:--:|--:|--:|--:|--:|---|:--:|:--:|---|
| Heavy Machine Gun | 0 | 3 | 3 | - | 2/4/6/8 | 1.0 | 1 | 8 | 9k | MG_HEAVY_OS | E | STANDARD | MGWeapon |
| Heavy Machine Gun Array | 0 | 3 | 3 | WEAPON_NA | 2/4/6/8 | 1.0 | 1 | 0 | 12k | MG_HEAVY_OS | E | STANDARD | AmmoWeapon |
| Heavy Machine Cannon | 2 | 5 | 5 | - | 2/5/8/11 | 2.5 | 3 | 26 | 60k | HEAVY_MACHINE_CANNON_OS | G | EXPERIMENTAL | MGWeapon |
| Light Machine Gun | 0 | 1 | 1 | - | 3/6/9/12 | 0.5 | 1 | 6 | 6k | MG_LIGHT_OS | E | STANDARD | MGWeapon |
| Light Machine Gun Array | 0 | 1 | 1 | WEAPON_NA | 3/6/9/12 | 0.25 | 1 | 0 | 8k | MG_LIGHT_OS | E | STANDARD | AmmoWeapon |
| Machine Gun Array | 0 | 2 | 2 | WEAPON_NA | 2/4/6/8 | 0.5 | 1 | 0 | 8k | MG_OS | E | STANDARD | AmmoWeapon |
| Machine Cannon | 1 | 3 | 3 | - | 3/6/9/12 | 1.5 | 2 | 14 | 30k | MACHINE_CANNON_OS | F | ADVANCED | MGWeapon |
| Machine Gun | 0 | 2 | 2 | - | 2/4/6/8 | 0.5 | 1 | 6 | 6k | MG_OS | E | STANDARD | MGWeapon |

## Missiles

| Name | heat | dmg | rack | min | S/M/L/E | tons | crit | BV | cost | ammo | rate | lvl | base(mechanic) |
|---|--:|--:|--:|--:|:--:|--:|--:|--:|--:|---|:--:|:--:|---|
| ATM 12 | 8 | - | 12 | 4 | 5/10/15/20 | 7.0 | 5 | 212 | 350k | - | F | ADVANCED | OSATMWeapon |
| ATM 3 | 2 | - | 3 | 4 | 5/10/15/20 | 1.5 | 2 | 53 | 50k | - | F | ADVANCED | OSATMWeapon |
| ATM 6 | 4 | - | 6 | 4 | 5/10/15/20 | 3.5 | 3 | 105 | 125k | - | F | ADVANCED | OSATMWeapon |
| ATM 9 | 6 | - | 9 | 4 | 5/10/15/20 | 5.0 | 4 | 147 | 225k | - | F | ADVANCED | OSATMWeapon |
| Advance LRM 10 | 4 | - | 10 | 0 | -/-/-/- | 2.5 | 1 | 102 | 150k | - | F | ADVANCED | LRMWeapon |
| Advance LRM 15 | 5 | - | 15 | 0 | -/-/-/- | 3.5 | 2 | 154 | 262500 | - | F | ADVANCED | LRMWeapon |
| Advance LRM 20 | 6 | - | 20 | 0 | -/-/-/- | 5.0 | 4 | 205 | 375k | - | F | ADVANCED | LRMWeapon |
| Advance LRM 5 | 2 | - | 5 | 0 | -/-/-/- | 1.0 | 1 | 51 | 45k | - | F | ADVANCED | LRMWeapon |
| Advance SRM 2 | 2 | - | 2 | - | 3/6/9/12 | 0.5 | 1 | 21 | 13k | - | F | ADVANCED | SRMWeapon |
| Advance SRM 4 | 3 | - | 4 | - | 3/6/9/12 | 1.0 | 1 | 39 | 78k | - | F | ADVANCED | SRMWeapon |
| Advance SRM 6 | 4 | - | 6 | - | 3/6/9/12 | 1.5 | 1 | 59 | 104k | - | F | ADVANCED | SRMWeapon |
| Dragon Piercer 10 | - | - | - | - | -/-/-/- | 7.0 | - | 127 | 175k | - | E | STANDARD | Thunderbolt10Weapon |
| Dragon Piercer 15 | - | - | - | - | -/-/-/- | 11.0 | - | 229 | 325k | - | E | STANDARD | Thunderbolt15Weapon |
| Dragon Piercer 20 | - | - | - | - | -/-/-/- | 15.0 | - | 305 | 450k | - | E | STANDARD | Thunderbolt20Weapon |
| Dragon Piercer 5 | - | - | - | - | -/-/-/- | 3.0 | - | 64 | 50k | - | E | STANDARD | Thunderbolt5Weapon |
| ER LRM 10 | 6 | - | 10 | - | -/-/-/- | 6.0 | 3 | 104 | 200k | - | F | ADVANCED | ExtendedLRMWeapon |
| ER LRM 15 | 9 | - | 15 | - | -/-/-/- | 9.0 | 5 | 156 | 350k | - | F | ADVANCED | ExtendedLRMWeapon |
| ER LRM 20 | 12 | - | 20 | - | -/-/-/- | 13.0 | 6 | 210 | 500k | - | F | ADVANCED | ExtendedLRMWeapon |
| Extended Streak LRM 10 | 6 | - | 10 | 10 | 12/22/38/57 | 8.0 | 4 | 200 | 200k | - | G | EXPERIMENTAL | StreakLRMWeapon |
| Extended Streak LRM 15 | 9 | - | 15 | 10 | 12/22/38/57 | 11.0 | 6 | 300 | 300k | - | G | EXPERIMENTAL | StreakLRMWeapon |
| Extended Streak LRM 20 | 12 | - | 20 | 10 | 12/22/38/57 | 16.0 | 7 | 400 | 400k | - | G | EXPERIMENTAL | StreakLRMWeapon |
| Heavy LRM 10 | 8 | - | 10 | 6 | -/-/-/- | 5.0 | 2 | 170 | 150k | - | E | STANDARD | LRMWeapon |
| Heavy LRM 20 | 12 | - | 20 | 6 | -/-/-/- | 10.0 | 5 | 345 | 375k | - | E | STANDARD | LRMWeapon |
| Heavy LRM 30 | 18 | - | 30 | 6 | -/-/-/- | 14.0 | 7 | 510 | 562500 | - | E | STANDARD | LRMWeapon |
| Heavy MML 11 | 12 | - | 11 | 6 | 7/14/21/28 | 9.0 | 6 | 200 | 330k | - | F | ADVANCED | MMLWeapon |
| Heavy MML 14 | 14 | - | 14 | 6 | 7/14/21/28 | 12.0 | 7 | 250 | 420k | - | F | ADVANCED | MMLWeapon |
| Heavy MML 5 | 6 | - | 5 | 6 | 7/14/21/28 | 4.0 | 3 | 85 | 150k | - | F | ADVANCED | MMLWeapon |
| Heavy MML 8 | 8 | - | 8 | 6 | 7/14/21/28 | 6.5 | 4 | 145 | 240k | - | F | ADVANCED | MMLWeapon |
| Heavy SRM 12 | 14 | - | 12 | - | 4/8/12/16 | 9.0 | 5 | 240 | 300k | - | E | STANDARD | SRMWeapon |
| Heavy SRM 4 | 6 | - | 4 | - | 4/8/12/16 | 3.0 | 2 | 80 | 90k | - | E | STANDARD | SRMWeapon |
| Heavy SRM 6 | 8 | - | 6 | - | 4/8/12/16 | 4.5 | 3 | 120 | 140k | - | E | STANDARD | SRMWeapon |
| Heavy SRM 8 | 10 | - | 8 | - | 4/8/12/16 | 6.0 | 4 | 160 | 200k | - | E | STANDARD | SRMWeapon |
| Improve ATM 12 | 8 | - | 12 | 4 | 5/10/15/20 | 7.5 | 5 | 350 | 550k | - | F | ADVANCED | OSATMWeapon |
| Improve ATM 3 | 2 | - | 3 | 4 | 5/10/15/20 | 1.5 | 2 | 83 | 83k | - | F | ADVANCED | OSATMWeapon |
| Improve ATM 6 | 4 | - | 6 | 4 | 5/10/15/20 | 3.5 | 3 | 165 | 250k | - | F | ADVANCED | OSATMWeapon |
| Improve ATM 9 | 6 | - | 9 | 4 | 5/10/15/20 | 5.5 | 4 | 247 | 375k | - | F | ADVANCED | OSATMWeapon |
| Improve Dragon Piercer 10 | - | - | - | - | -/-/-/- | 5.5 | - | 127 | 227500 | - | E | STANDARD | Thunderbolt10Weapon |
| Improve Dragon Piercer 15 | - | - | - | - | -/-/-/- | 8.5 | - | 229 | 422500 | - | E | STANDARD | Thunderbolt15Weapon |
| Improve Dragon Piercer 20 | - | - | - | - | -/-/-/- | 12.0 | 4 | 305 | 585k | - | E | STANDARD | Thunderbolt20Weapon |
| Improve Dragon Piercer 5 | - | - | - | - | -/-/-/- | 2.5 | - | 64 | 65k | - | E | STANDARD | Thunderbolt5Weapon |
| Improve LRM 10 | 4 | - | 10 | 6 | -/-/-/- | 3.0 | 2 | 90 | 100k | - | E | STANDARD | LRMWeapon |
| Improve LRM 15 | 5 | - | 15 | 6 | -/-/-/- | 5.0 | 3 | 136 | 175k | - | E | STANDARD | LRMWeapon |
| Improve LRM 20 | 6 | - | 20 | 6 | -/-/-/- | 7.0 | 5 | 181 | 250k | - | E | STANDARD | LRMWeapon |
| Improve LRM 5 | 2 | - | 5 | 6 | -/-/-/- | 1.5 | 1 | 45 | 30k | - | E | STANDARD | LRMWeapon |
| Improve MML 11 | 6 | - | 11 | 6 | 7/14/21/28 | 5.5 | 4 | 105 | 200k | - | E | STANDARD | MMLWeapon |
| Improve MML 5 | 3 | - | 5 | 6 | 7/14/21/28 | 2.0 | 2 | 45 | 97500 | - | E | STANDARD | MMLWeapon |
| Improve MML 7 | 4 | - | 7 | 6 | 7/14/21/28 | 3.0 | 3 | 67 | 136500 | - | E | STANDARD | MMLWeapon |
| Improve MML 9 | 5 | - | 9 | 6 | 7/14/21/28 | 4.0 | 4 | 86 | 162500 | - | E | STANDARD | MMLWeapon |
| Improve MRM 10 | 4 | - | 10 | 0 | 5/10/15/20 | 3.0 | 2 | 70 | 50k | - | E | STANDARD | MRMWeapon |
| Improve MRM 20 | 6 | - | 20 | 0 | 5/10/15/20 | 7.0 | 3 | 140 | 100k | - | E | STANDARD | MRMWeapon |
| Improve MRM 30 | 10 | - | 30 | 0 | 5/10/15/20 | 9.5 | 5 | 210 | 150k | - | E | STANDARD | MRMWeapon |
| Improve MRM 40 | 12 | - | 40 | 0 | 5/10/15/20 | 12.0 | 7 | 280 | 200k | - | E | STANDARD | MRMWeapon |
| Improve SRM 2 | 2 | - | 2 | - | 3/6/9/12 | 0.75 | 1 | 21 | 10k | - | E | STANDARD | SRMWeapon |
| Improve SRM 4 | 3 | - | 4 | - | 3/6/9/12 | 1.5 | 1 | 39 | 60k | - | E | STANDARD | SRMWeapon |
| Improve SRM 6 | 4 | - | 6 | - | 3/6/9/12 | 2.0 | 2 | 59 | 80k | - | E | STANDARD | SRMWeapon |
| Improve Streak LRM 10 | 4 | - | 10 | 6 | 7/14/21/28 | 5.0 | 2 | 173 | 156k | - | E | ADVANCED | StreakLRMWeapon |
| Improve Streak LRM 15 | 5 | - | 15 | 6 | 7/14/21/28 | 7.0 | 3 | 260 | 234k | - | E | ADVANCED | StreakLRMWeapon |
| Improve Streak LRM 20 | 6 | - | 20 | 6 | 7/14/21/28 | 10.0 | 5 | 346 | 312k | - | E | ADVANCED | StreakLRMWeapon |
| Improve Streak LRM 5 | 2 | - | 5 | 6 | 7/14/21/28 | 2.0 | 1 | 87 | 78k | - | E | ADVANCED | StreakLRMWeapon |
| Improve Streak SRM 2 | 2 | - | 2 | - | 4/8/12/16 | 1.0 | 1 | 40 | 30k | - | E | ADVANCED | StreakSRMWeapon |
| Improve Streak SRM 4 | 3 | - | 4 | - | 4/8/12/16 | 2.0 | 1 | 79 | 60k | - | E | ADVANCED | StreakSRMWeapon |
| Improve Streak SRM 6 | 4 | - | 6 | - | 4/8/12/16 | 3.0 | 2 | 118 | 90k | - | E | ADVANCED | StreakSRMWeapon |
| Improve Streak SRM 8 | 5 | - | 8 | - | 4/8/12/16 | 4.0 | 3 | 158 | 120k | - | E | ADVANCED | StreakSRMWeapon |
| MML 3 | 2 | - | 3 | 6 | 7/14/21/28 | 1.25 | 2 | 29 | 45k | - | E | STANDARD | MMLWeapon |
| MML 5 | 3 | - | 5 | 6 | 7/14/21/28 | 2.5 | 3 | 45 | 75k | - | E | STANDARD | MMLWeapon |
| MML 7 | 4 | - | 7 | 6 | 7/14/21/28 | 4.0 | 3 | 67 | 105k | - | E | STANDARD | MMLWeapon |
| MML 9 | 5 | - | 9 | 6 | 7/14/21/28 | 5.0 | 4 | 86 | 125k | - | E | STANDARD | MMLWeapon |
| Munin Missile Beacon | 0 | - | 1 | - | 4/9/15/18 | 5.0 | 3 | 75 | 250k | INARC | F | ADVANCED | NarcWeapon |
| Narc | 0 | - | 1 | - | 3/6/9/12 | 3.0 | 2 | 30 | 100k | - | E | STANDARD | NarcWeapon |
| Seeker Missile Beacon | 0 | - | 1 | - | 3/6/9/12 | 2.5 | 2 | 30 | 120k | - | E | STANDARD | NarcWeapon |
| Streak LRM 10 | 4 | - | 10 | 6 | 7/14/21/28 | 6.0 | 2 | 173 | 120k | - | E | ADVANCED | StreakLRMWeapon |
| Streak LRM 15 | 5 | - | 15 | 6 | 7/14/21/28 | 8.5 | 4 | 260 | 180k | - | E | ADVANCED | StreakLRMWeapon |
| Streak LRM 20 | 6 | - | 20 | 6 | 7/14/21/28 | 11.5 | 5 | 346 | 240k | - | E | ADVANCED | StreakLRMWeapon |
| Streak LRM 5 | 2 | - | 5 | 6 | 7/14/21/28 | 2.5 | 1 | 87 | 60k | - | E | ADVANCED | StreakLRMWeapon |
| Streak MML 11 | 6 | - | 11 | 6 | 7/14/21/28 | 8.0 | 6 | 165 | 247500 | - | F | ADVANCED | MMLWeapon |
| Streak MML 5 | 3 | - | 5 | 6 | 7/14/21/28 | 3.5 | 3 | 75 | 112500 | - | F | ADVANCED | MMLWeapon |
| Streak MML 7 | 4 | - | 7 | 6 | 7/14/21/28 | 5.0 | 4 | 105 | 157500 | - | F | ADVANCED | MMLWeapon |
| Streak MML 9 | 5 | - | 9 | 6 | 7/14/21/28 | 6.5 | 5 | 135 | 202500 | - | F | ADVANCED | MMLWeapon |
| Streak MRM 10 | 4 | - | 10 | 0 | 5/10/15/20 | 6.0 | 3 | 120 | 120k | - | F | ADVANCED | MRMWeapon |
| Streak MRM 20 | 6 | - | 20 | 0 | 5/10/15/20 | 12.0 | 5 | 240 | 240k | - | F | ADVANCED | MRMWeapon |
| Streak MRM 30 | 10 | - | 30 | 0 | 5/10/15/20 | 17.0 | 7 | 360 | 360k | - | F | ADVANCED | MRMWeapon |
| Streak SRM 2 | 2 | - | 2 | - | 3/6/9/12 | 1.25 | 1 | 30 | 27k | - | E | ADVANCED | StreakSRMWeapon |
| Streak SRM 4 | 3 | - | 4 | - | 3/6/9/12 | 2.5 | 1 | 59 | 54k | - | E | ADVANCED | StreakSRMWeapon |
| Streak SRM 6 | 4 | - | 6 | - | 3/6/9/12 | 3.75 | 2 | 89 | 81k | - | E | ADVANCED | StreakSRMWeapon |
| Streak SRM 8 | 5 | - | 8 | - | 3/6/9/12 | 5.0 | 3 | 120 | 108k | - | E | ADVANCED | StreakSRMWeapon |
