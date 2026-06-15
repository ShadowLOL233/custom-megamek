# Outer Sphere (OS) Tech Base — Development Plan

Branch: `au-0.50.12` (forks: custom-megamek / Custom-megameklab / custom-mekhq).
This document tracks planned and in-progress work for the custom **Outer Sphere (OS)** tech base.
It is a living planning doc — update it as items land.

---

## 0. Design philosophy (applies to all new OS gear)

OS is a **premium** tech base: higher damage / lighter / longer range than IS, at higher BV & cost.

### 0.1 Core engineering value — maintainability & modularity first
OS weapon engineering prizes per-weapon **maintainability (整备性)** and **modularity** ABOVE raw
performance. This is a deliberate cultural choice and the main reason OS tech advances **slower than
the Clans** (the Clans sacrifice serviceability for performance; OS refuses to until the Advance /
Experimental tiers). Because serviceability/modularity is built into every OS weapon, OS **rejects
"do-everything / integrated" weapons**: combining functions (e.g. an Ultra+LB-X+HVAC platform) costs
extra weight + crit slots and forfeits the family's low-weight/low-crit advantage to buy a flexibility
the modular line ALREADY provides — 本末倒置. Specialized, modular, serviceable weapons are preferred
over multi-role monoliths.

### 0.2 Design-tier ladder (a weapon family may exist at several tiers)
1. **Standard** — OS's first / baseline implementation of a technology (an attempt or preliminary
   result). Naming: no prefix ("AC/10", "ER PPC").
2. **Improve** — refines Standard: raises maintainability & modularity AND appropriately **reduces
   weight & crit**, at comparable performance. Naming: "Improve X".
3. **Enhanced** *(NEW tier — not yet in code)* — refines Improve, in ONE of two directions:
   (a) further reduce weight & crit at Improve performance; OR (b) keep Improve's weight/crit + high
   maintainability and push raw **performance** as high as possible. Naming: "Enhanced X".
4. **Advance** — OS's most cutting-edge, most radical, most **Clan-like** design: abandons the
   maintainability + weight/crit discipline to **maximize performance**, but still reaches
   **mass production**. Naming: "Advance X".
5. **Experimental** — **reverse-engineered Ascended tech** (during/after The Long War). More radical
   than Advance; routinely abandons OS's traditional **safety AND maintainability**; ignores weight/crit
   entirely. The ONLY tier connected to the Ascended — **all other tiers are purely indigenous, with
   zero Ascended relation** (RoOS did not foresee the 3140 surprise attack). Naming: TBD.

### 0.3 Implementation mapping (tentative — reconcile before mass edits)
- Rating/level (tentative): Standard E/Standard · Improve E/Standard · Enhanced E–F/Standard–Advanced
  · Advance F/Advanced · Experimental F–G/Experimental.
- **Naming collision warning:** the DESIGN-tier "Experimental" ≠ the code field
  `SimpleTechLevel.EXPERIMENTAL`. Keep the two concepts distinct in code/docs.
- Indigenous-tier timeline (all pre-war, Ascended-independent): Standard ~2805–2840 · Improve
  ~2900–2990 · Enhanced ~3000–3050 *(CONFIRMED — RoOS early-unification era)* · Advance ~3060–3080 ·
  Experimental (Ascended) 3140+ / 3180–3195. (Fragmentation slowed Standard→Improve; OSR unification
  ~3000–3059 accelerated Enhanced→Advance — the "unification dividend".)
- **HVAC's tier — SHELVED** (decision deferred 2026-06-13). HVAC is the electrothermal-chemical AC
  (mag-rail + chemical boost + LB-X-style elongated barrel for maximum muzzle velocity); it is heavy
  (11t/7crit) and performance-pushing, yet its lore is an OSR-unification-era arms-consolidation bridge.
  Tier label (Improve vs Enhanced vs Advance) is parked — see §6.3.
- **Reclassification work** — the existing ~222 OS weapons mix "Improve/Advance" labels with
  overlapping intro years and need reclassification + re-dating onto this 5-tier scheme. See §6.

OS gear lives in `outerSphere` subpackages (e.g. `weapons/autoCannons/outerSphere`) or `OS_*` constants.

**Build/run reminder:** after editing an enum (e.g. `TechBase`) or engine, the running JVM never
hot-reloads jars — do a full clean rebuild + restart: `cd megameklab && .\gradlew.bat clean run`.

---

## 1. Status snapshot (implemented in code; several lines now PENDING tier-revision)

- **Faction:** Republic of the Outer Sphere (`RoOS`). — stable
- **Engines:** full OS lineup (Standard/Improve/Advance × Fusion/Light/XL/XXL/Compact + superheavy)
  is in code, **but ⏳PENDING:** the current "Advance" engines reclassify → **Enhanced**, and the new
  **Advance / Experimental** engine tiers are TBD (see §6.4).
- **Weapons (~224 files under `weapons/**/outerSphere`):** energy (lasers, PPCs incl. rotary/hyper,
  plasma, flamers), kinetics (autocannons, gauss, MGs), full missile line (+3 FCS: Diana III, Orion V)
  are in code — **but DESIGN STATUS is now ⏳TBD/PENDING for energy, kinetic AND missile families:** the
  **Advance** tier must be (re)designed under §0.2, and many "Advance"-named weapons reclassified →
  **Enhanced** (§6.2). Treat these families as under revision, not finished.
- **Armor / structure:** OS structure + damage-reduction armor BV multipliers wired — **but the
  structure "Advance" line is ⏳PENDING → Enhanced reclassification + Advance/Experimental redesign (§6.4).**
- **Movement boosters:** OS MASC / Supercharger line (per-equipment failure mechanics). — stable
- **Tech rating G** added to `enums/TechRating.java`, wired into MekHQ maintenance & parts cost. — stable

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
  per the §0.2 tier ladder.
- Earlier memory note said "base/Improve → static Advanced"; corrected to **STANDARD** per code — keep
  this in mind when copying tech fields.

---

## 6. Naming & tier-reclassification work items (added 2026-06-13)

### 6.1 Rename OS weapons that collide with canon names BUT diverge in design concept
Where an OS weapon reuses a canon **Clan/IS weapon NAME** but its **design concept differs** from that
canon weapon, it MUST be **renamed** — so the name doesn't imply it behaves like the canon namesake.
- **Known cases to rename:** **ATM**, **HAG / HHAG** (the OS designs diverge from the canon Clan ATM /
  Hyper-Assault Gauss concepts).
- **Do NOT rename** weapons whose function/design is genuinely close to canon — Laser, PPC, Gauss,
  LRM/SRM, etc. No pointless renaming.
- TODO: audit all OS weapon names for canon collisions; flag the conceptually-divergent ones; propose
  new OS-specific names for those.

### 6.2 Reclassify "Advance"-named weapons that are really Enhanced
Under the §0.2 ladder, **Advance** = the most radical / "Clan-like" tier (abandons maintainability +
weight/crit discipline for performance, yet mass-produced). **Many weapons currently named "Advance X"
are actually just refined, still-serviceable performance bumps** — those belong to the new **Enhanced**
tier, not Advance.
- TODO: audit every `Advance*` OS weapon; KEEP as Advance only the truly radical "Clan-like" designs;
  rename the rest to **Enhanced X** and re-date them into the Enhanced ~3000–3050 band.
- Current `Advance*` to review: Advance Ultra AC (2/5/10/20), Advance ER PPC, Advance ER Large Laser,
  Advance ER Large Pulse Laser, Advance Gauss Rifle, Advance Heavy Gauss Rifle.

### 6.3 HVAC tier classification — SHELVED
Per decision 2026-06-13, HVAC's tier label (Improve vs Enhanced vs Advance) is **deferred**. Recorded
here so it is not forgotten; resolve alongside §6.2 and the HVAC stat finalization (§2). HVAC is the
electrothermal-chemical AC; its heavy "range-over-weight" profile leans Advance, but its arms-
consolidation lore leans earlier — undecided.

### 6.4 Engines & internal structure — rename, reclassify, and redesign (added 2026-06-13)
- **Reclassify (decided):** the current **"Advance"-tier ENGINES and INTERNAL STRUCTURE** are
  refined-but-serviceable, not radical — reclassify them to the new **Enhanced** tier (rename
  "Advance X" → "Enhanced X", re-date to ~3000–3050), mirroring §6.2 for weapons.
- **Revisit naming (decided):** review the engine & structure naming overall for consistency with the
  §0.2 ladder (Standard/Improve/Enhanced/Advance/Experimental) — current code uses "base/Improve/Advance".
- **Redesign the now-vacant Advance tier** for engines & structure under §0.2 (genuinely radical /
  "Clan-like", performance-max, mass-produced).
- **Design a new Experimental tier** (Ascended-derived) for engines & structure.

**Engine-physics design question (OPEN — pending user decision).** Does OS break BattleTech's fusion
ceiling? Proposed working stance (NOT final):
- **Advance engines stay WITHIN fusion** — the indigenous pinnacle of fusion engineering (max power
  density; sacrifices the conservative safety/maintainability margins for output; still mass-produced).
  No new physics — this keeps OS believable and "slower than the Clans" (who never broke the fusion
  ceiling). "Cold-fusion"-style flavor (cleaner/denser fusion) fits HERE, if a flavor name is wanted.
- **Only EXPERIMENTAL engines exceed fusion** — reverse-engineered from captured **Ascended** power
  tech (candidate principles: **antimatter / matter-antimatter annihilation**, and/or a
  **Lithium-Fusion-burst core** derived from the Ascended Super-Jumpdrive's energy-handling). The
  literal **"借妖之力以败妖"**: monstrous power-to-weight, catastrophic instability (abandons safety),
  barely producible. This is the ONLY OS power plant that breaks the ceiling — and it came from the
  ENEMY, not OS genius, which fits the AU theme (OS humility vs Ascended hubristic transcendence).

### 6.5 Mobility-enhancement line — myomer / AES / equivalents (SHELVED, but on the roadmap)
Discussion shelved 2026-06-13, recorded here as a planned design avenue. Background established:
in BT, ground SPEED is engine-rating-bound (not myomer-bound), so the OS mobility levers are NOT
"faster baseline myomer" but: actuator-enhancement (precision/agility), myomer overclock (burst speed
at risk), and strength/heat myomer variants.
- **Canon reference points:** Myomer (electroactive artificial muscle, engine-powered), TSM
  (heat≥9 → ~2× physical strength), MASC (overclocks myomer for burst run MP, lock-up risk),
  RISC Super-Cooled Myomer, and AES (Actuator Enhancement System — per-limb actuator boost; arm AES
  +physical-attack accuracy, leg AES needs ALL legs; incompatible with MASC / Targeting Computer /
  advanced myomers; `MiscType.createISAES`, TO:AUE p.91).
- **OS design avenue (TBD):** OS-flavored AES / safer-MASC / next-gen myomer (improving strength,
  cooling, reliability or agility — NOT breaking the engine-bound speed cap). Tier per §0.2.
- Note: OS already has the MASC/Supercharger line (movement boosters) — extend from there.
