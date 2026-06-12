# Outer Sphere (OS) Tech Base — Development Plan

Branch: `au-0.50.12` (forks: custom-megamek / Custom-megameklab / custom-mekhq).
This document tracks planned and in-progress work for the custom **Outer Sphere (OS)** tech base.
It is a living planning doc — update it as items land.

---

## 0. Design philosophy (applies to all new OS gear)

OS is a **premium** tech base: higher damage / lighter / longer range than IS, at higher BV & cost.
Naming tiers mirror the engines: **base / "Improve" / "Advance"** (+ superheavy).

- base & Improve  → tech rating **E**
- Advance         → tech rating **F**, top OS gear may use the new rating **G**
- Static level: **code is authoritative.** Actual OS Improve AC/LB-X weapons use
  `SimpleTechLevel.STANDARD`; Advance-tier → `EXPERIMENTAL`.

OS gear lives in `outerSphere` subpackages (e.g. `weapons/autoCannons/outerSphere`) or `OS_*` constants.

**Build/run reminder:** after editing an enum (e.g. `TechBase`) or engine, the running JVM never
hot-reloads jars — do a full clean rebuild + restart: `cd megameklab && .\gradlew.bat clean run`.

---

## 1. Status snapshot (done & pushed)

- **Faction:** Republic of the Outer Sphere (`RoOS`).
- **Engines:** full OS lineup (Standard/Improve/Advance × Fusion/Light/XL/XXL/Compact + superheavy).
- **Weapons (~224 files under `weapons/**/outerSphere`):** energy (lasers, PPCs incl. rotary/hyper,
  plasma, flamers), kinetics (autocannons, gauss, MGs), full missile line (+3 FCS: Diana III, Orion V).
- **Armor / BV:** OS structure + damage-reduction armor BV multipliers wired.
- **Movement boosters:** OS MASC / Supercharger line (per-equipment failure mechanics).
- **Tech rating G** added to `enums/TechRating.java`, wired into MekHQ maintenance & parts cost.

---

## 2. Planned: OS HVAC (High-Velocity Autocannon) — Improve tier, heavy calibers only

### Design decision
- OS HVAC is rooted in **OS Improve AC + Improve LB-X** (NOT IS HVAC).
- **Only heavy calibers 12 / 14 / 16 / 18.** OS will **NOT** make a base-tier HVAC 2/5/10. Reasons:
  1. **Tier inversion** — "base" sits below "Improve"; deriving a base weapon from Improve LB-X is
     backwards. A base HVAC would root in base AC/LB-X instead.
  2. **Redundancy** — 2/5/10 is already saturated in OS (base AC, Improve AC, Assault AC, Ultra ×3
     tiers, LB-X ×3 tiers, Rotary). A base HVAC there adds no unique value.
  3. **HVAC's value only pays off at heavy calibers** — long reach + aero AV + ammo-explosion gamble
     matter when throwing a big single slug far. OS introduces HVAC purely to fill the heavy
     long-range niche, not to mirror IS's catalog.

### Concept
Improve-AC mechanism (single slug, `explosionDamage = damage`) + LB-X-class range pushed further,
traded against **high heat**. Aerospace-capable (`AV = damage` all brackets, `maxRange = EXT`).
Premium = light + high BV/cost.

### DRAFT stats (NOT final — pending fine-tuning)

| Model            | heat | dmg | short/med/long/ext | tons | crits | BV  | cost  | AV  | maxRange | explDmg |
|------------------|:----:|:---:|:------------------:|:----:|:-----:|:---:|:-----:|:---:|:--------:|:-------:|
| Improve HVAC/12  |  5   | 12  |   7/14/21/28       |  11  |   7   | 195 | 420k  | 12  |   EXT    |   12    |
| Improve HVAC/14  |  6   | 14  |   6/13/20/26       |  12  |   8   | 230 | 480k  | 14  |   EXT    |   14    |
| Improve HVAC/16  |  8   | 16  |   6/12/19/25       |  13  |   8   | 265 | 540k  | 16  |   EXT    |   16    |
| Improve HVAC/18  |  9   | 18  |   5/11/18/24       |  14  |   9   | 300 | 600k  | 18  |   EXT    |   18    |

Reference baselines (OS Improve, from code):
Improve AC/10 heat2 dmg10 5/10/15/20 10t 6c BV123 240k · Improve AC/20 heat6 dmg20 4/8/12/16 12t 9c BV237 360k ·
Improve LB10-X heat2 dmg10 6/12/18/24 10t 5c BV148 520k · Improve LB20-X heat5 dmg20 5/10/15/20 12t 8c BV296 780k.

### Tech fields (mirror OS Improve siblings)
`TechBase.OUTER_SPHERE`, rating **E**, availability `X/X/F/E`, `ISAdvancement(3128, 3133, 3138)`,
`Faction.LEGION`, `SimpleTechLevel.STANDARD`.

### Implementation notes
- Extend `weapons/autoCannons/HVACWeapon` (inherits HVAC jam/rapid-fire handler + BattleForce override).
- New ammo `AmmoType.AmmoTypeEnum.AC_HVAC_OS` (explodes) recommended over reusing `AC_IMP_OS`
  (avoids semantically sharing magazines with Improve AC).
- Package: `weapons/autoCannons/outerSphere/OSImproveHVAC{12,14,16,18}.java`.
- Naming: `name = "Improve HVAC/12"`, sortingName pattern `"AC OS 3 HVAC 2 Imp 12"`.

---

## 3. Planned: superheavy unit-cap extensions (>100t units)

These all mirror the existing OS BattleMek approach
(`megameklab` `BMChassisView.refreshTonnage` + `Mek.getOSSuperheavyChassisTechAdvancement()`),
which OS-gates the cap and adds an OS superheavy TechAdvancement so a pure-OS unit isn't flagged mixed.

| Target | Current cap & where | Plan |
|---|---|---|
| **OS superheavy ground vehicles** | `verifier/TestTank.maxTonnage(mode, superheavy)` — tracked 100/200, hover 50/100, VTOL 30/60, wheeled 80/160; reads ONLY mode + superheavy flag, no tech-base check. UI: `megameklab` `combatVehicle/CVChassisView.refreshTonnage`. | Add an OS superheavy vehicle TechAdvancement on `Tank`; OS-gate the cap in `CVChassisView`; possibly raise the `maxTonnage` ceiling for OS. |
| **Igor superheavy VTOL** (apocryphal MW5, ~51t cargo VTOL) | VTOL cap 30/60t — 51t already fits the 60t superheavy-VTOL bracket. | Build on the OS superheavy-vehicle work above; prefer an OS-flavored superheavy VTOL for tech-base consistency. |
| **OS superheavy aerospace fighters (>100t)** | `verifier/TestAero.getMaxTonnage(aero, faction)` returns 50 (conv) / 100 (aero), no tech-base logic. UI: `megameklab` `fighterAero/ASChassisView.refreshTonnage` (`max = conventional ? 50 : 100`), spinner hard-init `(20,5,100,5)`. Fighters have NO vanilla "superheavy" doubling — 100t is a hard ceiling. | OS-gate the cap in `ASChassisView` + raise the ceiling in `TestAero.getMaxTonnage` for OS, with an OS fighter TechAdvancement to avoid mixed-tech flagging. Note BT lore: >100t flying craft are Small Craft / DropShips (`largeAero`), not fighters — this is a deliberate OS rules-breaking extension (same nature as the OS 200t Mek). |

---

## 4. Development focus: OS versions of canon special equipment (MiscType)

Source of truth: `megamek/src/megamek/common/equipment/MiscType.java` (394 misc items) +
`equipment/enums/MiscTypeFlag.java`. The full Mek/combat-vehicle special-equipment catalogue below is
the reference pool for building **OS-flavored versions** (apply the base/Improve/Advance tiering + the
premium philosophy in §0).

> Note: AMS, TAG, Narc, M-Pod/B-Pod are rules-"weapons" (WeaponType), and Coolant Pod is ammo — not in
> MiscType. Listed here for completeness but tracked separately if OS versions are wanted.

### PRIORITY categories for upcoming development: **A, B, C, E, G, I**

**A. Electronics / EW / Sensors  ⭐PRIORITY**
Guardian ECM (IS/CL), Angel ECM, Beagle Active Probe (+proto), Clan/Light Active Probe, Bloodhound
Probe, Watchdog CEWS, Nova CEWS, IS EW Equipment, Improved Sensors.

**B. Fire Control / Targeting / C3  ⭐PRIORITY**
Targeting Computer (IS/CL), Artemis IV/V (+proto, OS), Apollo MRM FCS, PPC Capacitor, C3 Slave /
Emergency Master / Boosted (C3SBS), C3i, Naval C3, Diana III / Orion V (OS), Basic/Advanced Fire
Control, Command Console / Tank Command Console / Collapsible Command Module.

**C. Movement / Mobility  ⭐PRIORITY**
MASC, Supercharger (OS line exists), Jump Jet / Improved / Extended (+proto), Jump Booster /
Mechanical Jump Booster, Partial Wing (IS/CL), UMU, Tracks, Vehicular Jump Jet, VTOL Jet Booster,
TSM / Industrial TSM / proto, RISC Super-Cooled Myomer.

**D. Heat Management** (OS heat sinks already done; lower priority)
Single / Double (IS/CL +proto) / Compact / Laser / Radical Heat Sink, Laser Insulator,
RISC Emergency Coolant System.

**E. Defensive / Survivability  ⭐PRIORITY**
CASE / CASE II / proto / OS CASE, Modular Armor, Heavy Armor, Blue Shield Particle Field Damper,
Null / Void Signature System, Chameleon Light Polarization Shield, HarJel / II / III, Armored Motive
System, AP Pod, Chaff Pod, Spikes, RamPlate, Booby Trap, Ejection Seat / Escape Pod,
Neural Interface / DNI Cockpit.

**F. Internal Structure** (OS structure line largely done; lower priority)
Endo Steel / Endo-Composite / Composite / Reinforced / Industrial (+ OS structure line).

**G. Physical Weapons / Melee / Manipulators  ⭐PRIORITY**
Hatchet, Sword, Mace, Lance, Retractable Blade, Vibroblade S/M/L, Chain Whip, Flail, Claw, Talons,
Clubs; industrial-as-melee tools (Chainsaw, Backhoe, Dual Saw, Rock Cutter, Spot Welder, Mining Drill,
Buzzsaw, Pile Driver, Wrecking Ball, Salvage Arm, Lift Hoist); Shields S/M/L, AES, Manipulator.

**H. Industrial / Utility** (lowest priority)
Cargo/containers, lift/hitch/trailer/dumpers, bulldozer, bridge layers, sprayers, MASH, field kitchen,
mobile field base, mobile/ground HPG, searchlights, imagers, comms gear, mine/sensor dispensers,
batteries, fuel tanks, drone/SDS systems.

**I. Vehicle Chassis Mods  ⭐PRIORITY**
Amphibious (full/limited), Environmental Sealing, Off-Road, Dune Buggy, Flotation Hull, Hydrofoil,
Submersible, STOL/VSTOL, Convertible, Monocycle/Bicycle, Snowmobile, Ultra-Light, Prop, Omni Chassis,
Tractor/Trailer, Armored/Heavy Chassis, Turrets (Head/Quad/Shoulder/Sponson/Pintle), Mast Mount.

---

## 5. Open tuning / review items

- **HVAC stat tables (§2) are a tuning starting point — confirm final numbers before implementation.**
- Decide OS HVAC ammo: dedicated `AC_HVAC_OS` vs reuse `AC_IMP_OS` (recommended: dedicated).
- For each PRIORITY equipment category (A/B/C/E/G/I): decide which items get OS versions and design
  base/Improve/Advance stat tables per the §0 philosophy.
- Earlier memory note said "base/Improve → static Advanced"; corrected to **STANDARD** per code — keep
  this in mind when copying tech fields.
