# Outer Sphere (OS) Tech-Base Equipment Reference

Comprehensive technical inventory of all Outer Sphere (OS) tech-base **engines, internal structures, and armor types** currently implemented in this fork's custom development branch. Values verified against source code at the time of writing.

**Scope:** This document covers only the Engine / Structure / Armor categories. Weapons (OS lasers, PPCs, Rotary PPC family, etc.) and miscellaneous equipment (OS CASE, EARS, DDS, PFD, etc.) are documented separately.

---

## Table of Contents

1. [Engines (7 types)](#1-engines)
2. [Internal Structures (19 types)](#2-internal-structures)
3. [Armor Types (20 types)](#3-armor-types)
4. [Cross-Reference & Design Guidance](#4-cross-reference--design-guidance)
5. [Source File Locations](#5-source-file-locations)

---

## 1. Engines

**Source:** `megamek/src/megamek/common/equipment/Engine.java`

All OS engines integrate 10 heat sinks (matching standard fusion convention). Unlike the standard Clan XXL engine, the **OS XXL has no additional heat penalty** — a major design advantage.

| Internal Constant | Display Name | Weight Multiplier | Critical Slot Layout | TechRating | Availability | Intro / Proto / Common | Tech Level |
|---|---|---|---|---|---|---|---|
| `OS_STANDARD_ENGINE` (14) | Advance Fusion | **1.0×** | CT: 6 (concentrated) | E | F/C/B/B | 2840 / 2850 / 2865 | STANDARD |
| `OS_LIGHT_ENGINE` (15) | Advance Light | **0.65×** | CT: 6, LT/RT: 2/2 | E | F/C/B/B | 2855 / 2865 / 2870 | STANDARD |
| `OS_XL_ENGINE` (16) | Advance XL | **0.45×** | CT: 6, LT/RT: 3/3 | E | F/C/B/B | 2860 / 2865 / 2875 | STANDARD |
| `OS_XXL_ENGINE` (17) | Advance XXL | **0.30×** | CT: 6, LT/RT: 4/4 | F | X/F/D/C | 2895 / 2905 / 2930 | **EXPERIMENTAL** |
| `OS_COMPACT_ENGINE` (18) | Advance Light Compact | **1.35×** | CT: 3 (compact) | E | F/C/B/B | 2860 / 2865 / 2870 | STANDARD |
| `OS_SH_XL_ENGINE` (19) | Experimental Superheavy XL | **0.45×** (formula: rating² / 400² × multiplier) | SH-specific layout | F | X/X/F/D | 3000 / 3020 / 3045 | **EXPERIMENTAL** |
| `OS_SH_XXL_ENGINE` (20) | Experimental Superheavy XXL | **0.30×** (same SH formula) | SH-specific layout | F | X/X/X/F | 3050 / 3065 / 3075 | **EXPERIMENTAL** |

**Engine Selection Quick Reference:**

| Need | Recommendation | Weight Savings | Trade-off |
|---|---|---|---|
| Cheapest available | Advance Fusion | 0% (baseline) | None |
| Moderate reduction, Standard tier | Advance Light | 35% | Side-torso slots (LT/RT 2 each) |
| Heavy reduction, Standard tier | Advance XL | 55% | Side-torso vulnerability (3 each) |
| Maximum reduction (Experimental) | Advance XXL | 70% | Very fragile (4 each), no heat penalty |
| Compact center-torso layout | Advance Light Compact | -35% (heavier!) | Frees side-torso, +35% weight |
| Superheavy chassis | SH XL / SH XXL | 55% / 70% | Superheavy-only, EXPERIMENTAL |

---

## 2. Internal Structures

**Source:**
- Equipment definitions: `megamek/src/megamek/common/equipment/MiscType.java` (lines ~9569-9980)
- HP multipliers: `megamek/src/megamek/common/units/Mek.java` `getOSStructureHpMultiplier()` (line 784)
- Armor cap bonus: `megamek/src/megamek/common/units/Mek.java` `getOSArmorBonus()` (line 810)

### 2A. Endo Series (Weight Reduction Only, No HP Boost)

| Internal Type | Display Name | HP Multiplier | Weight (× unit weight) | Crit Slots | Armor Cap Bonus | Tech Level | Years |
|---|---|---|---|---|---|---|---|
| `OS_IMP_ENDO_STEEL` | Imp. Endo Steel | 1.0× | 0.05× | 8-14 (size-dependent) | 0% | STANDARD | 2855 / 2865 / 2875 |
| `OS_ADV_ENDO_STEEL` | Adv. Endo Steel | 1.0× | 0.035× | 6-10 | 0% | STANDARD | 2870 / 2880 / 2895 |
| `OS_IMP_ENDO_COMPOSITE` | Imp. Endo Composite | 1.0× | 0.04× | 4-8 | 0% | STANDARD | 2870 / 2880 / 2895 |
| `OS_ADV_ENDO_COMPOSITE` | Adv. Endo Composite | 1.0× | 0.025× | 3-6 | 0% | ADVANCED | 2885 / 2895 / 2910 |

### 2B. Reinforce Series (HP Boost, No Armor Cap Bonus)

| Internal Type | Display Name | HP Multiplier | Weight (× unit weight) | Crit Slots | Armor Cap Bonus | Tech Level | Years |
|---|---|---|---|---|---|---|---|
| `OS_IMP_REINFORCE` | Imp. Reinforce | **2.0×** | 0.20× | 0 | 0% | ADVANCED | 2870 / 2885 / 2900 |
| `OS_REINFORCE_COMPOSITE` | Reinforce Composite | **1.5×** | 0.135× | 0 | 0% | ADVANCED | 2875 / 2890 / 2905 |
| `OS_REINFORCE_ENDO_STEEL` | Reinforce Endo Steel | **1.5×** | 0.135× | 8-14 | 0% | ADVANCED | 2880 / 2895 / 2910 |
| `OS_IMP_REINFORCE_ENDO_STEEL` | Imp. Reinforce Endo Steel | **1.5×** | 0.135× | 6-12 | 0% | ADVANCED | 2895 / 2910 / 2930 |
| `OS_ADV_REINFORCE_ENDO_STEEL` | Adv. Reinforce Endo Steel | **1.5×** | 0.135× | 6-12 | 0% | EXPERIMENTAL | 2910 / 2930 / 2950 |

### 2C. Heavy Duty Series (HP + Armor Cap Dual Bonus)

| Internal Type | Display Name | HP Multiplier | Weight (× unit weight) | Crit Slots | Armor Cap Bonus | Tech Level | Years |
|---|---|---|---|---|---|---|---|
| `OS_HEAVY_DUTY` | Heavy Duty | **1.2×** | 0.135× | 5-10 | **+20%** | ADVANCED | 2880 / 2895 / 2910 |
| `OS_HEAVY_DUTY_ENDO_STEEL` | Heavy Duty Endo Steel | **1.2×** | 0.135× | 5-10 | **+20%** | ADVANCED | 2895 / 2910 / 2930 |
| `OS_SH_DUTY_ENDO_STEEL` | SH Duty Endo Steel | **1.25×** | 0.16× | 6-12 | **+25%** | EXPERIMENTAL | 2990 / 3010 / 3030 |

### 2D. Superheavy Reinforce Series

| Internal Type | Display Name | HP Multiplier | Weight (× unit weight) | Crit Slots | Armor Cap Bonus | Tech Level | Years |
|---|---|---|---|---|---|---|---|
| `OS_SH_REINFORCE` | SH Reinforce | **2.0×** | 0.20× | 0 | 0% | EXPERIMENTAL | 2995 / 3015 / 3035 |
| `OS_SH_REINFORCE_ENDO_STEEL` | SH Reinforce Endo Steel | **1.75×** | 0.17× | 6-12 | 0% | EXPERIMENTAL | 3000 / 3025 / 3050 |
| `OS_SH_REINFORCE_ENDO_COMPOSITE` | SH Reinforce Endo Composite | **1.75×** | 0.15× | 4-8 | 0% | EXPERIMENTAL | 3020 / 3045 / 3070 |

### 2E. Reinforce Heavy Duty Series (Triple Bonus: HP × Armor Cap × Optional Endo Weight)

| Internal Type | Display Name | HP Multiplier | Weight (× unit weight) | Crit Slots | Armor Cap Bonus | Tech Level | Years |
|---|---|---|---|---|---|---|---|
| `OS_REINFORCE_HEAVY_DUTY` | Reinforce Heavy Duty | **1.75×** | 0.18× | 5-10 | **+15%** | ADVANCED | 2895 / 2910 / 2930 |
| `OS_REINFORCE_HEAVY_DUTY_ENDO_STEEL` | Reinforce HD Endo Steel | **1.75×** | 0.18× | 5-10 | **+15%** | ADVANCED | 2910 / 2930 / 2950 |
| `OS_SH_REINFORCE_HEAVY_DUTY` | SH Reinforce Heavy Duty | **1.75×** | 0.20× | 5-10 | **+20%** | EXPERIMENTAL | 3025 / 3050 / 3075 |
| `OS_SH_REINFORCE_HEAVY_DUTY_ENDO_STEEL` | SH Reinforce HD Endo Steel | **1.75×** | 0.20× | 5-10 | **+20%** | EXPERIMENTAL | 3035 / 3060 / 3085 |

### Structure Rules Summary

- **HP Multiplier** affects each location's internal structure HP — the resistance after armor is breached.
- **Armor Cap Bonus** increases the maximum armor points the mech can equip (Heavy Duty series only).
- **Zero-slot structures** (`OS_IMP_REINFORCE`, `OS_REINFORCE_COMPOSITE`, `OS_SH_REINFORCE`) are integrated into the chassis and consume no critical slots.
- **Endo variants** save weight via distributed crit slots but provide no HP bonus on their own.
- **Stacking rule:** Reinforce-style HP bonus + Heavy Duty armor cap bonus stack (see 2E for triple-bonus combos).

---

## 3. Armor Types

**Source:** `megamek/src/megamek/common/equipment/ArmorType.java` (lines ~2029-2385)

The `pptMultiplier` field represents **points-per-ton relative to standard armor (16 pts/ton)**. So a multiplier of `1.25` produces 20 pts/ton; `0.5` produces 8 pts/ton.

### 3A. Ferro-Fibrous Series (Weight Efficiency)

| `T_ARMOR_OS_*` | Display Name | PPT Multiplier | Crits (Mek) | Cost (c-bills/ton) | Tech Level | Years |
|---|---|---|---|---|---|---|
| `IMP_FF` | Imp. Ferro-Fibrous | **1.12×** | 9 | 20,000 | STANDARD | 2820 / 2825 / 2832 |
| `ADV_FF` | Adv. Ferro-Fibrous | **1.208×** | 7 | 28,000 | ADVANCED | 2840 / 2848 / 2856 |
| `IMP_LIGHT_FF` | Imp. Light FF | 1.0× | 6 | 18,000 | STANDARD | 2828 / 2834 / 2842 |
| `HEAVY_FF` | Heavy FF | **1.1875×** | 15 | 25,000 | ADVANCED | 2824 / 2830 / 2838 |
| `IMP_HEAVY_FF` | Imp. Heavy FF | **1.25×** | 13 | 32,000 | STANDARD | 2848 / 2856 / 2866 |

### 3B. Hardened Series (Halves AC / Ballistic / PPC Damage)

| `T_ARMOR_OS_*` | Display Name | PPT Multiplier | Damage Rule | Crits (Mek) | Cost | Tech Level | Years |
|---|---|---|---|---|---|---|---|
| `IMP_HARDENED` | Imp. Hardened | **0.625×** | AC/Ballistic/PPC ×0.5 | 5 | 22,000 | STANDARD | 2850 / 2860 / 2872 |
| `HARDENED_FF` | Hardened FF | **0.5625×** | Same | 14 | 30,000 | ADVANCED | 2860 / 2870 / 2882 |
| `HARDENED_HEAVY_FF` | Hardened Heavy FF | **0.9375×** | Same | 16 | 45,000 | EXPERIMENTAL | 2875 / 2888 / 2900 |
| `ADV_HARDENED_FF` | Adv. Hardened FF | **0.8125×** | Same | 14 | 38,000 | EXPERIMENTAL | 2870 / 2882 / 2896 |

### 3C. Ferro-Lamellor Series (Universal Damage Reduction ×0.75 or ×0.875)

| `T_ARMOR_OS_*` | Display Name | PPT Multiplier | Damage Rule | Crits (Mek) | Cost | Tech Level | Years |
|---|---|---|---|---|---|---|---|
| `FERRO_LAMELLOR` | Ferro-Lamellor | **0.75×** | All incoming damage ×0.75 | Variable | 35,000 | ADVANCED | 2878 / 2892 / 2910 |
| `HEAVY_FERRO_LAMELLOR` | Heavy Ferro-Lamellor | **0.875×** | All incoming damage ×0.875 | 16 | 50,000 | ADVANCED | 2890 / 2905 / 2922 |
| `HARDENED_HEAVY_FERRO_LAMELLOR` | Hardened Heavy FL | **0.75×** | FL ×0.75 stacked with Hardened ×0.5 for AC/PPC | 18 | 80,000 | EXPERIMENTAL | 2905 / 2920 / 2940 |

### 3D. Reactive / Reflective / Specialized Series

| `T_ARMOR_OS_*` | Display Name | PPT Multiplier | Damage Rule | Crits (Mek) | Cost | Tech Level | Years |
|---|---|---|---|---|---|---|---|
| `REACTIVE` | Reactive | 1.0× | Reflects ballistic/explosive damage | 12 | 30,000 | ADVANCED | 2840 / 2850 / 2862 |
| `IMP_REACTIVE` | Imp. Reactive | 1.0× | Same, fewer crit slots | 5 | 40,000 | STANDARD | 2870 / 2882 / 2896 |
| `ENERGY_ABSORPTION` | Energy Absorption | 1.0× | Laser/PPC damage reduced | 9 | 35,000 | ADVANCED | 2855 / 2865 / 2878 |
| `LASER_REFLECTIVE` | Laser Reflective | 1.0× | Laser damage halved | 7 | 30,000 | ADVANCED | 2848 / 2858 / 2870 |
| `BALLISTIC_REINFORCED` | Ballistic Reinforced | **0.875×** | Ballistic damage reduced | 7 | 25,000 | ADVANCED | 2845 / 2855 / 2868 |
| `APA` | Anti-Penetrative Ablation | **0.9375×** | Critical-hit resistant | 6 | 20,000 | ADVANCED | 2830 / 2840 / 2852 |

### 3E. Stealth Series (ECM + Reduced Targeting)

| `T_ARMOR_OS_*` | Display Name | PPT Multiplier | Stealth Cost | Crits (Mek) | Cost | Tech Level | Years |
|---|---|---|---|---|---|---|---|
| `STEALTH` | Stealth | 1.0× | +8 heat/turn when active | 12 | 50,000 | ADVANCED | 2852 / 2862 / 2875 |
| `IMP_STEALTH` | Imp. Stealth | 1.0× | +7 heat/turn when active | 10 | 75,000 | STANDARD | 2878 / 2892 / 2910 |

### Damage-Reduction Stacking Rules

| Armor Flag | Triggers On | Multiplier | Stacks With |
|---|---|---|---|
| `F_HARDENED_ARMOR` | AC / Ballistic / PPC | ×0.5 | Ferro-Lamellor |
| `F_FERRO_LAMELLOR` | Any incoming damage | ×0.75 or ×0.875 | Hardened |
| `F_REFLECTIVE` | Laser / PPC (per subclass) | ×0.5 | Does NOT stack with Hardened |
| `F_BALLISTIC_REINFORCED` | Ballistic class | ×0.5 (partial) | Does NOT stack with Hardened |

**Hardened Heavy Ferro-Lamellor is the OS damage-reduction ceiling**: FL ×0.75 × Hardened ×0.5 = **incoming damage ×0.375** against AC/PPC. Cost: 80,000 c-bills/ton, 18 crit slots, EXPERIMENTAL tech level.

---

## 4. Cross-Reference & Design Guidance

### Maximum Survivability Build (Pure OS Heavy)

For a superheavy assault with maximum durability:

- **Structure:** `OS_SH_REINFORCE_HEAVY_DUTY_ENDO_STEEL`
  - HP × **1.75**, armor cap × **+20%**, weight 0.20× chassis
- **Armor:** `IMP_HEAVY_FF` (1.25× pts/ton) **+** Hardened layer (×0.5 vs AC/PPC) via `HARDENED_HEAVY_FERRO_LAMELLOR` if you want max reduction
- **Engine:** `OS_SH_XXL_ENGINE` for maximum weight savings

**Net effect:** Roughly **2.6-3.0× effective HP** of a standard same-tonnage mech, but at the cost of:
- All EXPERIMENTAL tech (restricted in many ruleset configurations)
- High c-bill cost
- 18 crit slots for armor + 6-12 for structure = significant slot occupation

### Build Cost Comparison (Per Ton of Armor)

| Armor Type | Cost/Ton | Cost per Effective HP* |
|---|---|---|
| Standard IS Armor (baseline) | 10,000 | 625 |
| Imp. FF | 20,000 | 1,116 |
| Hardened (vs AC/PPC) | 22,000 | 1,760 (but ×2 effective HP) |
| Imp. Heavy FF | 32,000 | 1,600 |
| Ferro-Lamellor | 35,000 | 2,917 |
| Heavy FL | 50,000 | 3,571 |
| Stealth | 50,000 | 3,125 |
| Imp. Stealth | 75,000 | 4,688 |
| Hardened Heavy FL | 80,000 | 3,200 (×2.67 effective HP) |

*"Effective HP" approximated as `pts/ton × damage_reduction_factor`. Crude metric but useful for comparison.

### Tech Base Compatibility

- Pure OS units **can only use** `TechBase.ALL` or `TechBase.OUTER_SPHERE` equipment.
- Cannot mount IS / Clan / Ascended exclusive equipment.
- **Mixed (OS Chassis)** allows IS/Clan equipment on an OS chassis (uncommon but supported).
- The recently added `TechBase.ASCENDED` is strictly isolated — Ascended and OS units cannot share equipment.

---

## 5. Source File Locations

| Category | Primary File | Key Function / Region |
|---|---|---|
| Engines (definitions) | `megamek/src/megamek/common/equipment/Engine.java` | `OS_*_TA` static fields (lines ~1083-1125) |
| Engines (tech level) | Same | `getTechLevel()` switch (line ~1418) |
| Structures (definitions) | `megamek/src/megamek/common/equipment/MiscType.java` | `createOS*Structure()` factories (lines ~9569-9980) |
| Structures (HP rules) | `megamek/src/megamek/common/units/Mek.java` | `getOSStructureHpMultiplier()` (line 784) |
| Structures (armor cap) | `megamek/src/megamek/common/units/Mek.java` | `getOSArmorBonus()` (line 810) |
| Armor (definitions) | `megamek/src/megamek/common/equipment/ArmorType.java` | `createOS*()` factories (lines ~2029-2385) |
| Lookup keys | `megamek/src/megamek/common/equipment/EquipmentTypeLookup.java` | `OS_*` constants |
| Type constants | `megamek/src/megamek/common/equipment/EquipmentType.java` | `T_STRUCTURE_OS_*` / `T_ARMOR_OS_*` |

---

## Maintenance Notes

- This reference reflects the codebase state as of the document creation date.
- **HP multipliers are source-verified** from `Mek.java` line 786-802.
- **Armor pptMultipliers are source-verified** from `ArmorType.java`.
- **Engine weight multipliers** are derived from the constructor's intent and TA declarations.
- When adding new OS equipment, please update this document or note divergence.

For future expansion topics (Rotary PPC family, Ascended TechBase, OS weapons inventory), see sibling documents in this folder.
