# Outer Sphere (OS) Tech Base — Development Plan

Branch: `au-0.50.12` (forks: custom-megamek / Custom-megameklab / custom-mekhq).
This document tracks planned and in-progress work for the custom **Outer Sphere (OS)** tech base.
It is a living planning doc — update it as items land.

---

## Contents

Quick index — jump to a section instead of scanning the whole file.

- **⭐⭐ TOP PRIORITY (2026-08-01) — Alpha Strike Formation Bonus → Total Warfare:** see the dedicated section directly below the Contents. **#2 priority = playtesting the new OS components** (see the Playtest priority queue). Improve C3 Point focus-fire (§9.6) is queued behind those.
- **Section 0 — Design philosophy**: tier ladder, BV methodology, TechRating, implementation mapping (durable reference).
- **Section 1 — Status snapshot**: what is implemented in code (some lines pending tier-revision).
- **Section 2 — OS HVAC**: **✅ REPURPOSED 2026-07-31 → long-barrel precision anti-armor AC** (calibers 10/12/14/16, dedicated `HVAC_OS` ammo, range-scaling to-hit bonus, BV 165/195/225/255).
- **Section 3 — Planned: superheavy unit-cap extensions** [active]: >100t vehicles / VTOL / fighters.
- **Section 4 — Dev focus: OS versions of canon special equipment** [active]: MiscType catalogue (priority A/B/C/E/G/I).
- **Section 5 — Open tuning / review items** [active].
- **Section 6 — Naming & tier-reclassification work items** [active]: §6.4 engine/structure/armor Advance→Enhanced rename **✅ DONE 2026-07-06**; subsections 6.3 & 6.5 shelved (see below).
- **Section 7 — Planned: BF & RF AC variants** [active]: being reclassified to **Inner Sphere** (not OS).
- **Section 8 — Electromagnetic Lance & kinetic apex**: Coil Augmented Railgun **✅ IMPLEMENTED**; Electromagnetic Lance (power-gated coilgun supergun) **✅ IMPLEMENTED 2026-07-31 — 6 munitions**; also holds the (now ✅ done) HVAC-repurpose & Heavy-missile-Ultra decisions.
- **Section 9 — OS Modular Electronics: BCS & CCS** [active/DESIGN]: two systems — **BCS** (Battle Computer System: info/command/EW, Tacticon B-2500 lineage, modular track + Advance specialized cores C-X280/Raven/G-X100) and **CCS** (Combat Computer System: single-mech fire control, C-2500 core + weapon-type modules + Composite). Dissolves canon AES⊥TC / AES⊥MASC / standalone-C3 into a modular ecosystem balanced by a two-core tonnage tax. **§9.6** adds a C3-network target-designator sub-family (Kestrel / Shrike / Lodestar).
- **OS missile munitions + range redesign (2026-07-31):** SRM/LRM/MRM special munitions **✅ done**; the SRM/MRM/LRM range-specialization triad (MRM medium -1 / LRM long -1) **✅ done**. Detail under "⭐ Development priority" below.
- **Section 11 — OS Actuator & Motive Systems (AES-I & AMS-I)** [design, not coded]: Improve-tier AMS-I (vehicle, −2 motive-crit, lighter) + a reconceived Mek-only AES-I (mobility/handling: −1 to-hit when run/jump, offsets Hardened/Modular Armor MP penalties; no TC/BCS/CCS/MASC exclusion).

Shelved ideas are parked in **[OS_SHELVED_IDEAS.md](OS_SHELVED_IDEAS.md)**. Splitting this plan into
per-topic files (weapons / units / equipment) is **deferred** until it grows further.

## ⭐⭐ TOP PRIORITY — Alpha Strike Formation Bonus → Total Warfare (added 2026-08-01)

**Goal:** bring Alpha Strike's *Formation Bonus* into the TW (classic) engine — a **force** whose composition
matches a formation type (Battle / Assault / Striker-Cavalry / Fire / Recon / Pursuit / Command / …) grants its
member units a standing bonus for the battle.

**How AS does it (for reference):** in the AS Companion you organize units into a formation (lance/star), and if
its composition (unit count + **unit roles**, sometimes size/movement) matches a formation type, the whole
formation gains a **Formation Bonus** — mostly delivered as free **SPAs** granted to members, plus a few flat
effects (e.g. Recon/Command → initiative; Fire → long-range/indirect help). *(Exact AS Companion values must be
transcribed when we spec the table — do not guess them.)*

**Feasibility in this engine: HIGH — most infrastructure already exists; only the formation ruleset is missing.**
- **Unit roles ✓:** `UnitRole` (`common/units/UnitRole.java`) already carries the AS role set — AMBUSHER, BRAWLER,
  JUGGERNAUT, MISSILE_BOAT, SCOUT, SKIRMISHER, SNIPER, STRIKER (+ aero). `Entity.getRole()` exposes it. AS
  formation qualification keys off exactly these roles.
- **Force grouping ✓:** `common/force/Force` + `Forces`; `Entity.getForceId()`. A "formation" = a **Force**
  (lance level), NOT a Team. Enumerate members via `Force.getEntities()` → `game.getInGameObjects(...)`.
- **Bonus hooks ✓:** initiative via `InitiativeBonusBreakdown` (add a `formation` component, mirroring the `bcs`
  one); to-hit via `ComputeAttackerToHitMods` / `ComputeToHit`; per-round / per-team effects via the phase-prep
  managers.
- **Gap ✗:** there is **no** Formation-type / Formation-Bonus logic yet (the repo's Alpha Strike code is
  stat-conversion + card tooling, not a TW ruleset) — must be built from scratch.

**Conceptual challenge (AS abstract → TW granular):** AS bonuses are balanced for AS's coarse scale; some port
1:1 (initiative, free SPAs — TW already has the SPA system), but others (range/damage-band effects) need a
TW-native re-expression. Expect a *curated* subset + re-tuning, not a blind copy.

**Design questions to settle before coding:**
1. **Which formation types + exact bonuses** to include (transcribe AS Companion; decide SPA-grant vs flat-mod per bonus).
2. **Detection granularity/timing:** per-Force, evaluated at game start (re-check on unit loss?); composition
   rules (count, role mix, weight/movement) per type.
3. **Bonus scope + interaction:** initiative only, or also to-hit / movement / morale? How it stacks with the OS
   BCS/C3 network bonuses and the §9.3/§9.6 network-coordination cap.
4. **Canon vs AU:** gate behind a **game option** so vanilla TW is unchanged by default (recommended).

**Proposed phasing:** (P1) `Formation`/`FormationType` model + per-Force detection from `UnitRole`; (P2)
initiative-bonus formations first (simplest hook — `InitiativeBonusBreakdown`); (P3) to-hit / movement formations;
(P4) lobby UI showing a Force's detected formation + bonus; (P5) game-option gate + docs.

**Status:** ⏳ **ANALYSIS DONE (2026-08-01); SOURCE DATA TRANSCRIBED (2026-08-01)** — the full AS Commander's
Edition formation list + Formation Bonuses (pp. 116–123) and every referenced SPA/SCA effect (pp. 92–103) are now
transcribed in `development/FORMATION_BONUS_REFERENCE.md`. That doc also records: (a) the extra MekBay formations
(Berserker/Close, Rifle, Hunter, …) are canon from **Campaign Operations**, already implemented (composition only,
no bonus) in `megamek/client/ratgenerator/FormationType.java`, reusable for the eligibility layer; (b) which
formations have CE bonuses vs which need AS Companion. **Scope decided (2026-08-01, two tiers):** Tier 1 (coding
started) = the non-CamOps AS CE Formation Bonuses; Tier 2 (next) = the CamOps-only formations (Berserker/Close, …),
whose bonuses need AS Companion or custom design. **Implementation approach (reference doc §6):** grant the
equivalent *existing* TW pilot option (`Crew.getOptions()`) to a qualifying Force's members, gated by a new game
option — most AS SPAs (Sniper, Oblique Attacker, Weapon Specialist, Blood Stalker, Multi-Tasker, Tactical Genius,
Forward Observer, Maneuvering Ace, Edge) already exist and are honored by the TW rules engine.

---

## ⭐ Playtest priority queue — #2 DEV PRIORITY (2026-07-31, reprioritized 2026-08-01)

**This entire queue is the #2 development priority, immediately below the Formation Bonus port above** — every OS
component shipped so far compiles and passes the equipment-init unit tests **only**; none has had an in-game
playtest. Testing priority order (newest OS components first, per the 2026-08-01 reprioritization):

1. **P1 — OS missile redesign + special munitions (this session) — UNTESTED.** The SRM/MRM/LRM range-
   specialization triad (MRM medium −1 / LRM long −1) and the full SRM/LRM/MRM special-munition catalogue.
   Check: the bracket −1s fire in the right range brackets; MRM special-munition effects resolve; the new BVs feel right.
2. **P2 — Kestrel / Shrike / Lodestar network designators (§9.6) — UNTESTED.** Kestrel network −1 (LOS + Lodestar-
   beacon paths), Shrike −1/−3 crit, Lodestar beacon attach + LRM indirect-fire enable, and the non-stacking
   network-coordination cap (only the deepest of Kestrel/Shrike/B-2500 applies; a CCS module −1 still stacks to −2).
   Verify the effects vanish off-network / off-beacon / under ECM.
3. **P3 — BCS/CCS modular electronics (§9).** The larger prior initiative; §9.5 lists its still-UNTESTED
   mechanics (Demon debuff, the network-cap raise, the Advance cores, …).
4. **P4 — Earlier weapon equipment — UNTESTED.**
   - **HVAC precision repurpose (§2)** — the range-scaling to-hit bonus (0 / −1 / −1 / −2) and the new
     10/12/14/16 calibers / `HVAC_OS` ammo.
   - **Ultra Heavy LRM/SRM (§8)** — Single/Ultra mode switching, the 2-volley resolution + heat doubling,
     and the natural-2 double-tap jam.
   - **Electromagnetic Lance (§8)** — the per-munition power-gate (range + damage switching), inherent AP
     on the full-power rounds, and Precision's −2 vs target movement.

---

## ⭐ Development priority (added 2026-07-31)

### OS missile special munitions — SRM ✅ / LRM ✅ / MRM ✅
The OS missile line (LRM / SRM / MML / Streak / ER-LRM / Heavy families — ammo in `AmmoType.java`,
`createOS*Ammo`) shipped **STANDARD ammo only**; this initiative brings it up to the canon missile-munition
catalogue with OS-native variants. Method: define `OS_*_MUNITION_MUTATOR`s (canon `Munitions.M_*` + weight
ratio, OS Standard tier 2805/2820/2840, rating E, `Faction.LEGION`) and apply them via `createMunitions`.
Because MegaMek's munition mechanics are driven by the `Munitions` enum + generic handlers (which only check
`munitionType.contains(M_*)`, tech-base-agnostic), the OS variants reuse canon mechanics with **no handler
work**.
- **SRM — ✅ DONE (2026-07-31):** full canon set (Inferno, Fragmentation, Smoke, Tandem-Charge, Narc-capable,
  Heat-Seeking, Listen-Kill, Mine-Clearance, Anti-TSM, Dead-Fire, ARAD, Acid) on SRM 2/4/6 + MML-SRM. Artemis
  is applied by the separate OS Artemis block. Streak / Heavy-SRM excluded; Torpedo (naval) deferred.
- **LRM — ✅ DONE (2026-07-31):** full canon set — Follow-the-Leader, Semi-Guided, Swarm, Swarm-I, the
  Thunder/FASCAM family (Thunder / -Active / -Augmented / -Vibrabomb / -Inferno), plus the shared Fragmentation,
  Smoke, Narc, Heat-Seeking, Listen-Kill, Mine-Clearance, Anti-TSM, Dead-Fire, ARAD — on LRM 5/10/15/20/30 +
  MML-LRM. Artemis applied separately. **Incendiary LRM deferred** (canon builds it as separate ammo via
  `createIncendiaryVariants`, not a mutator); Tandem-Charge / Acid are SRM-only in canon; ER-LRM / MRM excluded.
- **MRM — ✅ DONE (2026-07-31):** OS-original subset (canon MRM has none): Fragmentation, Smoke, Narc-capable,
  Heat-Seeking, Listen-Kill, Anti-TSM, Mine-Clearance, Dead-Fire on Improve MRM 10/20/30/40 (Streak MRM
  excluded). Because the plain `MRMHandler` doesn't branch on munition, effect dispatch is wired via the new
  `OSMRMWeapon` base (reuses the LRM cluster-missile handlers). Thunder/FASCAM stays LRM-only.

Tiering: all at OS Standard for now; the user may later promote select munitions to Advance. **UNTESTED
in-game** — compiles + equipment-init / AmmoType tests pass.

### OS missile range specialization — SRM / MRM / LRM triad (redesign, 2026-07-31)
Reworks the three cluster-missile families into a short / medium / long specialization triad (OS §0 "reject
do-everything"): SRM = short brawler, MRM = medium specialist, LRM = long-range bombardment. Range brackets are
data-only field changes; the bracket-conditional to-hit bonuses are wired in `ComputeToHit` (mirroring the HVAC
range-scaling block), gated on new flags `F_OS_LRM_LONG_SPEC` / `F_OS_MRM_MEDIUM_SPEC`.

| Family | min | short | medium | long | ext | signature |
|---|---|---|---|---|---|---|
| SRM (unchanged) | 0 | 3 | 6 | 9 | 12 | dmg 2/missile, short brawler |
| MRM (core) | 0 | 3 | 13 | 16 | 20 | **-1 to-hit in the huge medium bracket** (Diana III FCS stacks a further -1); canon +1 penalty removed |
| LRM (core) | 6 / 0 | 7 | 14 | 24 | 30 | **-1 to-hit in the long bracket**, indirect-fire bombardment |

**Variant positioning (where the LRM sub-families sit in the new triad):**
- **Core LRM (Heavy / Improve / Enhanced):** the only family with 7/14/24 + long -1 (`F_OS_LRM_LONG_SPEC`). **BV +15%.**
- **Streak LRM (+ Improve Streak):** precision no-waste long — adopts the new 7/14/24 range, keeps lock-on, **no
  long -1** (its edge is guaranteed hits, not the accuracy bonus). **BV +5%.**
- **ER-LRM:** the extreme-reach axis (12/22/38, Artemis) — unchanged; trades the long -1 for raw distance.
- **Extended Streak LRM:** apex reach + lock-on (12/22/38) — unchanged.
- **Dragon Piercer:** a Thunderbolt single-warhead AP strike — orthogonal to the cluster triad, unchanged (no
  bracket -1, no range change).
- **MML (LRM mode):** the generalist — stays baseline canon 7/14/21, **no** long -1 (flexibility is its tradeoff;
  specialization is not rewarded). This also sidesteps the dual-mode gating complexity.
- **Core MRM (Improve 10/20/30/40):** the medium specialist (`F_OS_MRM_MEDIUM_SPEC`); **BV held**. **Streak MRM**
  adopts the new 3/13/16 ranges, no medium -1, BV held.

**BV scheme applied:** value = damage + range + accuracy (OS methodology). Core LRM +15% (long +3 reach + long
-1): Improve 5/10/15/20 = 52/104/156/208; Enhanced = 59/117/177/236; Heavy 10/20/30 = 155/316/472. Streak LRM
+5% (range only): 5/10/15/20 = 91/182/273/363. MRM held (medium -1 + huge bracket ≈ the old flat -1).

**Implementation:** `WeaponTypeFlag.F_OS_LRM_LONG_SPEC` / `F_OS_MRM_MEDIUM_SPEC` (+ WeaponType bare aliases);
`ComputeToHit` bracket blocks; new `OSMRMWeapon` base (medium-spec accuracy baseline + special-munition
dispatch). Compiles; equipment-init / AmmoType tests pass. **UNTESTED in-game** (to playtest: verify the long/
medium -1 fire in the right brackets, MRM special-munition effects resolve, and the new BVs feel right).

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
5. **Experimental** — OS's most radical tier: more radical than Advance; routinely abandons OS's
   traditional **safety AND maintainability**; ignores weight/crit entirely. **Dating rule (revised
   2026-07-06):** Experimental gear is usable from **~3050** (`3045/3050/3055`) by default — it is NOT
   auto-locked to the post-war era. Only items **explicitly designated as reverse-engineered Ascended
   tech** are locked to **3140+**; everything else in this tier is indigenous. Naming: TBD.

### 0.3 Implementation mapping (tentative — reconcile before mass edits)
- Rating/level (tentative): Standard E/Standard · Improve E/Standard · Enhanced E–F/Standard–Advanced
  · Advance F/Advanced · Experimental F–G/Experimental.
- **Naming collision warning:** the DESIGN-tier "Experimental" ≠ the code field
  `SimpleTechLevel.EXPERIMENTAL`. Keep the two concepts distinct in code/docs.
- Tier timeline — concrete `ISAdvancement(prototype/production/common)` triplets (APPLIED to all OS
  weapons 2026-07-06): Standard `2805/2820/2840` · Improve `2900/2930/2960` · Enhanced `3000/3025/3050`
  *(CONFIRMED — RoOS early-unification era; no weapons tagged Enhanced yet)* · Advance `3060/3070/3080` ·
  Experimental `3045/3050/3055` (indigenous, usable from ~3050). **Ascended reverse-engineered** gear is
  a separately-flagged exception dated **3140+ / 3180–3195**. (Fragmentation slowed Standard→Improve;
  OSR unification ~3000–3059 accelerated Enhanced→Advance — the "unification dividend".)
- **HVAC's tier — SHELVED** (decision deferred 2026-06-13). HVAC is the electrothermal-chemical AC
  (mag-rail + chemical boost + LB-X-style elongated barrel for maximum muzzle velocity); it is heavy
  (11t/7crit) and performance-pushing, yet its lore is an OSR-unification-era arms-consolidation bridge.
  Tier label (Improve vs Enhanced vs Advance) is parked — see §6.3.
- **Re-dating — DONE (2026-07-06):** all 218 dated OS weapons were re-dated onto the triplets above,
  classified by name token (Improve/Advance/Hyper) with a `SimpleTechLevel` fallback for untokened
  weapons. Counts at re-dating: Standard 65 · Improve 67 · Advance 71 · Experimental 15.
- **Advance→Enhanced weapon reclassification — DONE (2026-07-06):** all 20 `OSAdvance*`-named weapons
  renamed `OSEnhanced*` ("Enhanced X"), re-dated to Enhanced `3000/3025/3050`, EXPERIMENTAL-static ones
  dropped to ADVANCED; old internal/display names kept as `addLookupName` for save-compat. Engine &
  structure Advance→Enhanced is still pending (see §6.4).

OS gear lives in `outerSphere` subpackages (e.g. `weapons/autoCannons/outerSphere`) or `OS_*` constants.

**Build/run reminder:** after editing an enum (e.g. `TechBase`) or engine, the running JVM never
hot-reloads jars — do a full clean rebuild + restart: `cd megameklab && .\gradlew.bat clean run`.

---

## 1. Status snapshot (implemented in code; several lines now PENDING tier-revision)

- **Faction:** Republic of the Outer Sphere (`RoOS`). — stable
- **Engines:** full OS lineup (Standard/Improve/Advance × Fusion/Light/XL/XXL/Compact + superheavy)
  is in code; the old "Advance" engines were **renamed → Enhanced (✅ 2026-07-06, §6.4)**. Still ⏳PENDING:
  designing the new **Advance / Experimental** engine tiers (see §6.4).
- **Weapons (~224 files under `weapons/**/outerSphere`):** energy (lasers, PPCs incl. rotary/hyper,
  plasma, flamers), kinetics (autocannons, gauss, MGs), full missile line (+3 FCS: Diana III, Orion V)
  are in code — **but DESIGN STATUS is now ⏳TBD/PENDING for energy, kinetic AND missile families:** the
  **Advance** tier must be (re)designed under §0.2, and many "Advance"-named weapons reclassified →
  **Enhanced** (§6.2). Treat these families as under revision, not finished.
- **Armor / structure:** OS structure + damage-reduction armor BV multipliers wired — **but the
  structure "Advance" line was **renamed → Enhanced (✅ 2026-07-06, §6.4)**; new Advance/Experimental structure redesign still ⏳PENDING (§6.4).**
- **Movement boosters:** OS MASC / Supercharger line (per-equipment failure mechanics). — stable
- **Tech rating G** added to `enums/TechRating.java`, wired into MekHQ maintenance & parts cost. — stable

---

## 2. OS HVAC (High-Velocity Autocannon) — REPURPOSED to a precision anti-armor AC (2026-07-31)

> **REPURPOSED 2026-07-31 — was an explosive-gamble aero AC, now a long-barrel precision anti-armor AC.**
> The single-slug OS HVAC no longer competes with LB-X on an explosive/aero-gamble axis; it is now a high-BV
> **precision** piece whose long, high-velocity barrel walks concentrated slugs onto distant targets.
> - **Calibers re-based `12/14/16/18` → `{10,12,14,16}`** (dropped 18, added 10 — cuts family tonnage/crit).
>   Old `OSImprove/EnhancedHVAC18` deleted; old display names kept as `addLookupName` on the survivors.
> - **Signature — range-scaling to-hit bonus** (in `ComputeToHit`, gated on `OSHVACWeapon`): short **0**,
>   medium **−1**, long **−1**, extreme **−2**. A milder, longer-reaching cousin of the Burst-Fire gradient;
>   priced into a high BV (unlike BF's BV-neutral −1). Tuned down from a first draft of med −2 / long −3.
> - **Stats (Improve / Enhanced share BV per the ratified policy):** dmg = caliber; ranges /10 8/16/24/32 ·
>   /12 7/14/21/28 · /14 7/13/20/27 · /16 6/12/19/26; **Improve** t/crit/heat 10/6/3 · 11/7/4 · 12/8/5 · 13/8/6;
>   **Enhanced** −2t/−2crit/−1heat (8/4/2 · 9/5/3 · 10/6/4 · 11/6/5); **BV 165/195/225/255**; aero AV = dmg,
>   maxRange = EXT; single-slug, non-explosive (`explosive=false`, plain `ACWeaponHandler`).
> - **Ammo — dedicated `HVAC_OS` enum (idx 139)**, NOT canon `HYPER_VELOCITY` (avoids the caliber-10 cross-load
>   with canon HVAC/10). Low ammo/ton **7/6/5/4** (mech-level tax); intro 2900 covers both tiers.
> - Migrated the tracked design `os-designs/Atlas AS7-OS_T HVAC.mtf` off the deleted HVAC/18 → HVAC/16
>   (tonnage/crit-neutral swap). Compiles; equipment tests pass; **not yet playtested**. BV is the main tuning knob.

> **NOTE:** the *Design decision / Concept / DRAFT stats / Tech fields / Implementation notes* below are the
> **pre-repurpose historical record** (the explosive-gamble era) — kept for rationale, superseded by the above.

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

- ~~HVAC stat tables (§2)~~ — **DONE (2026-07-06):** user-approved draft shipped as Enhanced HVAC (§2).
- ~~Decide OS HVAC ammo~~ — **DONE:** reused canon `HYPER_VELOCITY` (HVAC base/handler keyed to it), not a
  dedicated enum.
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

### 6.2 Reclassify "Advance"-named weapons that are really Enhanced — DONE (2026-07-06)
Under the §0.2 ladder, **Advance** = the most radical / "Clan-like" tier. Per user decision, **ALL 20
`OSAdvance*`-named weapons were reclassified to Enhanced** (none kept as Advance): renamed `OSEnhanced*`
/ "Enhanced X", re-dated to Enhanced `3000/3025/3050`, EXPERIMENTAL-static → ADVANCED, old names kept as
`addLookupName` for save-compat. Compiles.
- Weapons reclassified: Enhanced Ultra AC (2/5/10/20), Enhanced ER PPC, Enhanced ER (Large/Medium/Small)
  Laser, Enhanced ER (Large/Medium/Small) Pulse Laser, Enhanced Gauss Rifle, Enhanced Heavy Gauss Rifle,
  Enhanced LRM (5/10/15/20), Enhanced SRM (2/4/6).
- Note: the "Advance" tier now holds only untokened `ADVANCED`-static weapons (PPC-X, Rotary Light PPC,
  etc.); a *new*, genuinely-radical Advance line can be designed later under §0.2.

### 6.3 HVAC tier classification — DECIDED: Improve + Enhanced (re-tiered 2026-07-08)
Originally shipped as a single **Enhanced** tier (2026-07-06). On review the draft stats read as too heavy
for Enhanced, so they were rebased as **Improve HVAC** and a lighter/cooler **Enhanced HVAC** (−2 t / −2 crit
/ −1 heat, BV +10) was added above it. Two tiers now: `OSImproveHVAC*` + `OSEnhancedHVAC*` — see §2.

### 6.4 Engines & internal structure — rename, reclassify, and redesign (added 2026-06-13)
- **✅ DONE (2026-07-06) — renamed ENGINES, INTERNAL STRUCTURE & armor "Advance" → "Enhanced".** Per user
  decision, also re-dated to Enhanced `3000/3025/3050` and included the 2 OS Advance armors. Details:
  - **Engines (5):** `OS_ADVANCE_*_ENGINE` → `OS_ENHANCED_*_ENGINE` (constant **values 14/16/17/27/30 kept**);
    `TYPE_KEYS` + `messages.properties` display → "Enhanced X (OS)"; `getEngineTypeByString` parser now
    matches `enhanced X` **and keeps** `advance X`/`adv. X` for save-compat; 5 TAs re-dated to 3000/3025/3050.
  - **Internal structure (3):** `T_STRUCTURE_OS_ADV_*` → `T_STRUCTURE_OS_ENH_*` (**IDs 8/13/15 kept**);
    `structureNames` → "Enhanced …(OS)"; old "Advance …(OS)" (+IS/Clan) added as `addLookupName`; TAs re-dated.
  - **Armor (2):** "Advance Ferro-Fibrous (OS)" / "Advance Hardened Ferro-Fibrous (OS)" → "Enhanced …";
    old internal/display names kept as `addLookupName`; re-dated.
  - Both `:megamek` and `:megameklab` compile. Internal `F_OS_ADV_*` MiscType flags + `createOSAdv*` factory
    method names were left as-is (deep-internal, not user-visible / not save-critical). Static tech levels
    left unchanged (structure Reinforced ES + Hardened FF armor remain EXPERIMENTAL — flag for review vs the
    Enhanced-tier = Standard–Advanced convention). Genuinely-radical Advance engine/structure lines can be
    (re)designed later under §0.2.
- **Revisit naming (decided):** review the engine & structure naming overall for consistency with the
  §0.2 ladder (Standard/Improve/Enhanced/Advance/Experimental) — current code uses "base/Improve/Advance".
- **Redesign the now-vacant Advance tier** for engines & structure under §0.2 (genuinely radical /
  "Clan-like", performance-max, mass-produced).
- **Design a new Experimental tier** (Ascended-derived) for engines & structure.

**Engine-physics design question — DECIDED (2026-07-06).** OS *may* break the fusion ceiling: Advance
engines stay within fusion; only Experimental (Ascended reverse-engineered) engines exceed it. The
Ascended black-tech engine details are deferred (to finalize later). Accepted direction:
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

### 6.5 Mobility-enhancement line — myomer / AES / equivalents — SHELVED
Moved to [OS_SHELVED_IDEAS.md](OS_SHELVED_IDEAS.md).

---

## 7. Planned: BF (Burst-Fire) & RF (Rapid-Fire) AC variants — same-BV flavor sidegrades (added 2026-07-05)

> **Reclassification & status (updated 2026-07-05):** BF & RF are **Inner Sphere** tech, not OS. Resolved:
> names use the MW5 form **"AC/N BF" / "AC/N RF"**; classes/handlers are `IS*` in the `innerSphere` package
> (`ISRapidFireACHandler`, `ISBurstFireACHandler`); **every tech field mirrors the same-caliber standard IS
> AC** (TechBase IS, TechRating C, IntroLevel, canon intro dates — a Succession-Wars field modification);
> **faction unrestricted**; no special OS gating (Mixed-OS access is handled by the mixed-tech system).
> **RF is implemented & compiles** — `ISRapidFireAC{2,5,10,20}` + `ISRapidFireACWeapon` + `ISRapidFireACHandler`,
> registered right after `ISAC20`. **BF is implemented & compiles too** — `ISBurstFireAC{2,5,10,20}` + marker `ISBurstFireACWeapon`, plus the
> range-gradient block in `ComputeToHit` right after the PPC-X block; **no handler needed** (the to-hit
> modifier runs before any weapon handler).

### Design intent
BF and RF are **not** new power tiers or niches. They are **flavor / playstyle sidegrades of the standard
IS AC**, carrying **identical BV, tonnage and ammo** (heat ≈ standard) to the same-caliber standard AC. Players pick
them for feel, not power. Calibers 2 / 5 / 10 / 20 for both. This scheme **removes the existing Assault AC
line** (superseded — see below).

### BF (Burst-Fire) — accuracy-shaped AC
Identical to the standard IS AC in every stat. The only difference is a **range-bracket to-hit gradient**
stacked on top of the normal range modifiers:
- short **0**, medium **0**, long **−1** (a sustained burst that walks onto distant targets).
- **DECIDED (2026-07-06): BV stays = standard AC.** The long-range -1 only softens the -4 long bracket to
  -3 — a minor, situational bonus not worth repricing. BF ships at BV = standard.

Implementation (Option A — mirror the existing PPC-X):
- Add a block in `actions/compute/ComputeToHit.java` mirroring the `PPCXWeapon` range-dispersion block
  (~L1526-1543), gated on `weaponType instanceof ISBurstFireACWeapon`. Only the FIRST `getToHitModifier`
  site (main compile) — the second (~L1814) is the artillery path, not relevant.
- Marker base class `ISBurstFireACWeapon extends ACWeapon`; weapons `ISBurstFireAC{2,5,10,20}` (names "AC/N BF").
- Shares standard AC ammo — no new ammo enum.
- Princess bot NOT patched (it will slightly misjudge, same as PPC-X — accepted).

### RF (Rapid-Fire) — 3-round burst via cluster
Fires a **3-round burst**: one to-hit roll (**no penalty**), then the **Cluster Hits Table (size 3,
expected exactly 2.0 hits)** decides how many rounds land, each rolling its own hit location.

Per-shot damage = **standard AC damage ÷ 2**. Because cluster-3 averages exactly 2.0,
`2.0 × (std ÷ 2) = std` → effective damage equals the standard AC **at every range**, so BV = standard,
exactly and range-independently. **No +1 penalty, no jam** (either would push effective below standard and
break BV = standard).

| Model    | shots × dmg | nominal | effective (×2.0) | vs std | BV          |
|----------|:-----------:|:-------:|:----------------:|:------:|:-----------:|
| RF AC/2  |    3 × 1    |    3    |        2         |  = std | = std AC/2  |
| RF AC/5  |    3 × 3    |    9    |        6         | ~120%* | = std AC/5  |
| RF AC/10 |    3 × 5    |   15    |       10         |  = std | = std AC/10 |
| RF AC/20 |   3 × 10    |   30    |       20         |  = std | = std AC/20 |

\* AC/5: std ÷ 2 = 2.5 has no integer split; **3 × 3** chosen (effective 6 vs std 5, ~120%). Accepted as a
deliberate minor over-tune; BV kept at standard AC/5.

Implementation:
- Build on the **Rotary engine** (`AC_ROTARY` ammo). Required: shot-count / heat are hardwired to the
  `AC_ULTRA` / `AC_ROTARY` enums, and the cluster-count mechanic is what yields the 2.0 average. Do **NOT**
  use the old Assault "independent-hit" mechanic — 3 independent to-hits = 1.5× standard, which breaks
  BV = standard.
- Handler `ISRapidFireACHandler extends RACHandler`, overridden to fix **3 shots**, use cluster
  hits, and **skip the jam roll**.
- Per-shot damage on the weapon `damage` field; weapons `ISRapidFireAC{2,5,10,20}` (names "AC/N RF").
- RF shares `AC_ROTARY` ammo with the Rotary line (engine coupling).
- Naming: MML "AC/10 RF"; on the record sheet show the burst (e.g. "5×3" = 5 dmg × 3 shots) so
  players don't misread the nominal (15) as concentrated damage.

### Dedicated ammo for BF & RF (implemented — 2026-07-05)
**Shipped & compiles:** enums `AC_BF`(135) / `AC_RF`(136); 8 ammo entries ("AC/N BF Ammo" / "AC/N RF Ammo")
via the `createDedicatedACAmmo` helper (not added to `acAmmos`, so no munition variants); `getNumShots`
patched to include `AC_RF` (path A) so RF's per-shot heat still scales x3; weapons wired
(`ISBurstFireACWeapon` -> `AC_BF`, `ISRapidFireACWeapon` -> `AC_RF`).

Background: BF fed standard `AC` ammo and RF fed `AC_ROTARY` ("Rotary AC/N Ammo") — the latter mislabels an
RF weapon and lets both cross-load with unrelated ACs. Plan: give each its own ammo type so bins are
labelled correctly and not shared with standard / Rotary AC.
- **New enums** `AC_BF`, `AC_RF` (IS, no OS suffix). New entries "AC/N BF Ammo" / "AC/N RF Ammo";
  shots/ton = standard AC (45 / 20 / 10 / 5); tech fields mirror standard AC ammo.
- **Wire**: set `ammoType` on the marker bases (`ISBurstFireACWeapon` → `AC_BF`, `ISRapidFireACWeapon` → `AC_RF`).
- **RF heat fix (engine, 1 line):** `Mounted.getNumShots` (~L892) keys the multi-shot count off the ammo enum
  (AC_ULTRA / AC_ROTARY); a dedicated `AC_RF` would fall through → `getCurrentShots() = 1` → per-shot heat
  stops scaling. Fix = add `AC_RF` to the `AC_ROTARY` branch (mirrors the MG_OS precedent), keeping per-shot
  heat 0/0/1/2. *Alternative:* no patch, set RF base heat to the total 0/0/3/6 and accept `getCurrentShots()=1`.
- Firing / cluster / ammo-consumption are handler + mode driven — unaffected by the enum change.
- AmmoTypeEnum uses explicit indices — pick free ones (the freed `AC_ASSAULT_OS` slot is a candidate once
  Assault is removed).

### OS AC-family native ammo — Standard / Ultra / Rotary (implemented — 2026-07-08)
**Audit:** most OS ammo-using families already had OS-native bins (Improve AC `AC_IMP_OS`, LB-X `LBX_OS`,
all Gauss/HAG `*_OS`, HVAC `HYPER_VELOCITY` OS entries, MML/Streak/MRM/ER-LRM/ATM reuse-enum bins). The only
families still falling back on canon (IS/Clan) ammo were **Standard AC**, **Ultra AC** (Std/Improve/Enhanced),
and **Rotary AC**. Ultra's canon IS ammo also goes *extinct 2915-3035*, straddling the Improve/Enhanced tiers.

**Shipped & compiles:**
- **Standard AC** → dedicated enum `AC_STD_OS`(137) (single-shot ⇒ no engine coupling, mirrors `AC_IMP_OS`).
  4 entries via `makeOSACAmmo`, dates 2805, values mirror canon AC ammo. Weapons `OSAC{2,5,10,20}` wired to it.
- **Ultra AC** → **reuses canon `AC_ULTRA`** (double-tap heat is hard-keyed to that enum — see the enum-decl
  note; a dedicated enum would need patching `getNumShots`, `HeatTrackingBVCalculator`, `Compute`,
  `ASDamageConverter`, UAC/RAC handlers, unjam in `TWGameManager` — ~8 files). 4 OS entries, TechBase OS,
  intro **2805** (covers all three tiers continuously, no extinction). Weapons unchanged.
- **Rotary AC** → **reuses canon `AC_ROTARY`** (same rationale). 4 OS entries, intro **3060** (matches the
  OS Rotary weapon). Weapons unchanged.
- Display names for all 12 share canon calibers, so tagged `(OS)` via a `tagOS` helper to disambiguate in MML.
- **Rule confirmed (house convention):** behaviour-coupled AC mechanisms (Ultra/Rotary, like OS missiles)
  *reuse* the canon ammoType enum; only behaviour-simple ones (standard AC, class-driven HAG) get a dedicated
  `*_OS` enum. Documented inline at the `AmmoTypeEnum` declaration.
- Save-compat: `OSAC{2,5,10,20}` now use `AC_STD_OS`; existing custom units may need standard-AC ammo re-picked.

### OS missile-family native ammo — LRM / SRM standard racks (implemented — 2026-07-08)
**Audit (all ~85 OS missile weapons):** every family whose *canon* ammo is introduced late already has an
OS-native bin, so **no intro-date gaps remain** — ER-LRM (`EXLRM`), Streak LRM/SRM (`LRM_STREAK`/`SRM_STREAK`),
MRM, MML, Dragon Piercer (`TBOLT_*`), ATM (SRTM/APTM/LRTM), Narc/Munin all covered. The only OS missile
weapons still on canon ammo were **plain LRM at rack 5/10/15/20** (OSHeavy/Improve/Enhanced LRM) and **plain
SRM at rack 2/4/6** (OSHeavy/Improve/Enhanced SRM); the OS designers had only built OS ammo for the *non-canon*
racks (LRM30, SRM8/12). Canon LRM/SRM ammo is ancient (~2400/2370, never extinct) ⇒ **no functional gap**;
adding OS ammo here is purely self-containment (same rationale as standard AC).

**Shipped & compiles:** 7 entries via `makeOSClusterMissileAmmo` — `createOSLRM{5,10,15,20}Ammo` (reuse `LRM`)
+ `createOSSRM{2,4,6}Ammo` (reuse `SRM`), TechBase OS, intro **2805** (covers Heavy 2805 / Improve 2900 /
Enhanced 3000), values mirror canon, `(OS)`-tagged names. **Weapons unchanged.**
- **Reuse is mandatory for missiles** (unlike the dedicated `AC_STD_OS` used for standard AC): a dedicated
  `LRM_OS`/`SRM_OS` enum would break Artemis IV / Narc / FCS bonuses, which are keyed to the `LRM`/`SRM` enum.
  Consequence: OS LRM/SRM weapons load *both* canon and OS ammo (they keep their canon ammoType).

### OS AC special munitions — Phase 1: Standard AC (implemented — 2026-07-08)
**Scope decided:** munitions are AU-tiered. Standard AC (`AC_STD_OS`) gets the canon AC munitions **minus
Precision** (Precision is reserved for the Enhanced tier), plus one OS-original round. LB-X / Ultra specials
are **Phase 2** (need handler plumbing — see below).

**Shipped & compiles:** 6 munitions on `OSAC{2,5,10,20}` via `createMunitions(osStdAcAmmos, …)` with new OS
mutators (`OS_AC_*_MUTATOR`, TechBase OUTER_SPHERE, all intro 2805 so no gap):
- **Armor-Piercing** (½ shots, +1 to-hit, `makeArmorPiercing` crit boost), **Caseless** (2× shots, jam/
  destroy on natural-2 roll), **Flak** (flak table + 5-fragment cluster), **Flechette** (anti-infantry +
  2× vs woods), **Tracer** (−1 dmg, −1 night to-hit), **Rocket-Propelled** (OS-original, see below).
- Effects work because `ACWeapon.getCorrectHandler` dispatches on munition (independent of the `AC_STD_OS`
  enum). Enum-gated to-hit effects were threaded: `AC_STD_OS` added to the AP `+1` check
  (`ComputeToHit`), the Tracer/incendiary night check (`AbstractAttackAction`), and the munition
  cost/BV + name-switch blocks in `AmmoType.createMunitionType`.

**Rocket-Propelled (`M_ROCKET_PROPELLED`, custom):** +1 to-hit at short range, −1 at long/extreme
(`ComputeToHit`, mirrors the BF gradient); all range bands ×1.2 when loaded (`WeaponType.getRanges`,
mirrors the ATM ammo-range mechanism). Plain slug damage (no special handler). Weight ratio 1.

### OS AC special munitions — Phase 2: LB-X / Ultra — IMPLEMENTED (2026-07-09)
Decision (2026-07-08): LB-X and Ultra get **bespoke, family-specific** munitions (NOT the generic Phase 1
set). Numbers below are the shipped values (per the OS BV/cost methodology — still tunable).

> **Shipped & compiles (2026-07-09).** All 5 munitions (18 ammo entries) live under the reused `LBX_OS` /
> `AC_ULTRA` enums via explicit creation (`makeOSLBXSpecialAmmo` / `makeOSUltraSpecialAmmo`); 4 new `Munitions`
> values `M_GAAM` / `M_ANTI_MYOMER` / `M_IMP_CASELESS` / `M_APDS` (Precision reuses canon `M_PRECISION`).
> **Handler dispatch (the core blocker) resolved:** `LBXACWeapon.getCorrectHandler` now calls a shared
> `getOSLBSpecialHandler` (routes `M_GAAM`→`OSGAAMHandler`, `M_ANTI_MYOMER`→`OSAntiMyomerHandler`) — reused by
> the 3 `OSUltraLB{5,10,20}XAC` overrides so both plain and double-tap LB-X honor the munition;
> `UACWeapon.getCorrectHandler` routes `M_APDS`→`OSUltraAPDSHandler` (keeps the Ultra double-tap).
> - **GAAM** → `OSGAAMHandler extends ACAPHandler` (single guided round, AP crit); min-range no-arm + medium
>   +25% dmg in the handler; range profile in `WeaponType.getRanges` (LBX_OS + M_GAAM); to-hit −1 med / +1 long
>   in `ComputeToHit`.
> - **Anti-Myomer** → `OSAntiMyomerHandler extends LBXHandler`: `usesClusterTable()` forced true (base keys it
>   to M_CLUSTER, which this round lacks), per-pellet AP crit via an `initHit` override (`makeArmorPiercing`),
>   forced PSR vs falling Meks in `doChecks`; ammo carries the −1 to-hit.
> - **Precision** → no handler: `−2` vs target movement mod wired by threading `AC_ULTRA` into
>   `ComputeTargetToHitMods`; half shots come from the ammo.
> - **Improved Caseless** → no handler: the default (non-PLAYTEST3) Ultra jam path already only jams (never
>   destroys); being `M_IMP_CASELESS` (not `M_CASELESS`) it can't enter the PLAYTEST3 destroy branch. Value =
>   the ~1.5× shots-per-ton in the ammo.
> - **APDS** → `OSUltraAPDSHandler extends UltraWeaponHandler`: `calcDamagePerHit ×0.8` (floored) + AP crit via
>   an overridden `handleEntityDamage`, double-tap preserved.
> **Validated:** `:megamek:compileJava` + the AmmoType/EquipmentType/EquipmentTypeLookup tests pass (the 18 new
> ammos initialize with no duplicate-name/tech-field errors). In-game behavior not yet playtested.
> **Open tuning:** the per-pellet Anti-Myomer AP crit may read stronger than "slight" (≈7 crit-biased pellets on
> an LB10-X spread) — revisit after a playtest. APDS/2 (1 dmg) shipped; confirm it earns its slot.

**Base reference (existing OS slug/cluster ammo):** LB-X 5/10/20 → shots 22/10/6, ammoBV 11/16/24, cost
9k/15k/24k · Ultra 2/5/10/20 → shots 45/20/10/5, ammoBV 7/14/26/35, cost 1k/9k/12k/20k.

**LB-X specials (calibers 5/10/20-X; reuse `LBX_OS` enum):**
- **GAAM** (Guided Anti-Armor Missile, `M_GAAM`, intro 2900): single guided projectile at rack damage;
  ranges **min 6 · 7/10/25/30**; to-hit **medium −1 / long +1**; **medium hit +25% dmg**; **≤6 hex hit = 0
  dmg** (can't arm); **AP crit**. Data /5·/10·/20: dmg 5·10·20 (med 6·12·25), shots 11·5·3, ammoBV
  14·20·32, cost 30k·50k·90k. → new `OSGAAMHandler` + getRanges + ComputeToHit gradient.
- **Anti-Myomer Flechette** (`M_ANTI_MYOMER`, intro 2900): **Cluster** (cluster table) + **forces a PSR on
  the target on hit** + slight AP crit. Data /5·/10·/20: cluster 5·10·20, shots 20·9·5, ammoBV 13·19·28,
  cost 18k·30k·48k. → handler extends `LBXHandler` (add PSR + AP).

**Ultra specials (calibers 2/5/10/20; reuse `AC_ULTRA` enum):**
- **Precision** (`M_PRECISION`, intro **3000** = Enhanced era): cancels target movement mod **−2**; **half
  shots** (canon). shots 22/10/5/2, ammoBV = base, cost ×6 (6k/54k/72k/120k).
- **Improve Caseless** (`M_IMP_CASELESS`, intro 2900): jam **never destroys the weapon** (only jams); shots
  **≈1.5× slug** (between slug and canon caseless's 2×): 67/30/15/7; ammoBV = base; cost ×1.5.
- **APDS** (`M_APDS`, intro 2900): **damage ≈−20% (floored) + AP crit + shots ≈1.35×** (discarding sabot).
  dmg 1/4/8/16, shots 61/27/14/7; ammoBV ≈ base; cost ×3.

**Tiering = date-soft-limit (hard per-weapon restriction is NOT possible):** Improve & the top tier of each
family share one ammo enum + rackSize, so ammo can't hard-gate by weapon. Specials intro 2900 (Precision
3000) so they surface at the Improve/Enhanced era; standard-tier weapons *can* technically load them once
that era arrives (accepted). LB-X tiers are Standard/Improve/**Ultra-LB** (no Enhanced LB); Ultra AC tiers
are Standard/Improve/**Enhanced**.

**Implementation surface (all DONE 2026-07-09 — kept as a map of the touched code):**
- **Handler dispatch (the core blocker) — DONE:** `LBXACWeapon.getOSLBSpecialHandler` (shared with the 3
  `OSUltraLB*` overrides) + `UACWeapon` APDS route; the Ultra path keeps the double-tap.
- **3 new handlers — DONE:** `OSGAAMHandler` (extends `ACAPHandler`), `OSAntiMyomerHandler` (extends
  `LBXHandler`), `OSUltraAPDSHandler` (extends `UltraWeaponHandler`).
- **Ammo — DONE:** explicit creation (not the canon mutator system) + 4 new `Munitions` values
  (`M_GAAM`, `M_ANTI_MYOMER`, `M_IMP_CASELESS`, `M_APDS`; Precision reuses `M_PRECISION`).
- **To-hit/range — DONE:** GAAM `getRanges` + `ComputeToHit` gradient; `AC_ULTRA` threaded into the
  Precision −2 gate in `ComputeTargetToHitMods`.
- **Remaining:** in-game playtest; retune Anti-Myomer AP crit if it over-performs; confirm APDS/2 slot.

### Assault AC removal
- Delete weapons `OSAssaultAC{2,5,10,20}` + their `addWeapon` registration.
- Delete handler `OSAssaultACHandler` (its independent-hit logic is not reused by RF).
- Ammo enum `AC_ASSAULT_OS` (idx 121): **deprecate / leave as placeholder** — do NOT remove the enum value
  (ordinal shift would break old saves).
- Save-compat: custom units referencing `OSAssaultAC*` will fail to load — confirm they are cleaned up.

### Reference math (for tuning)
- 2d6 to-hit P(≥TN): 7 → 58.3%, 8 → 41.7%, 9 → 27.8% (a ±1 modifier shifts one row).
- `Compute.clusterHitsTable`: size 3 → rolls 2-4 = 1, 5-9 = 2, 10-12 = 3; P(1/2/3) = 16.7 / 66.7 / 16.7%;
  **E = 2.00**. Size 2 (Ultra) → rolls 2-7 = 1, 8-12 = 2; E = 1.42.

---

## 8. Planned: Electromagnetic Lance — Advance-tier Gauss variant (added 2026-07-10)

**Status: IMPLEMENTED — 6 munitions (2026-07-31, passes 1 + 2).** One weapon `OSElectromagneticLance` (Advance,
extends `GaussWeapon`) + dedicated `EM_LANCE_OS` ammo enum (idx 140). The power-gate is a `getRanges` override on
the weapon (no core-method change): full-power rounds keep the long profile (min3 / 9 / 18 / 26 / 34); low-power
rounds collapse to 0 / 5 / 10 / 15 / 20 and lose AP.
- **Full power:** **EM Slug** (default → `ACAPHandler`, inherent AP, full dmg) · **APDS**
  (`OSEMLanceAPDSHandler`, inherent AP + ×0.8 dmg, more shots/ton — the rugged sabot penetrator).
- **Low power (no AP, dmg drop):** **Flak** (`ACFlakHandler`, anti-air fragments — range-only penalty so far) ·
  **Flechette** (`OSEMLanceFlechetteHandler`, ×0.6, anti-infantry) · **Precision** (`OSEMLancePrecisionHandler`,
  ×0.6, cancels up to −2 of the target movement mod — wired via `M_PRECISION` + `EM_LANCE_OS` in
  `ComputeTargetToHitMods`) · **Incendiary/HE** (`OSEMLanceIncendiaryHandler`, ×0.6, sets fires).
- Chassis: dmg 20 · heat 3 · 18 t · 11 crit · BV 480 · 3060/3070/3080 · TechRating F · LEGION. Compiles;
  equipment tests pass; **not yet playtested**.
- **Still deferred:** Flak's low-power damage drop (cluster nature), a manual throttle mode, an EM-Lance to-hit
  gradient, and final stat/BV tuning after playtest.

### Concept
An **Advance-tier variant of the OS Gauss family**: a coil-accelerated ferrous-slug supergun that fuses the
Gauss coilgun mechanism with AC-style munition variety. Signature = **range exceeding the OS Gauss line**,
**energy-adjustable output**, **inherent armor-piercing**, and — at throttled power — access to **AC munitions**.

### Tier & tech
**Advance, NOT Experimental.** It stays within known electromagnetic-accelerator physics — no fusion-ceiling
break, no Ascended reverse-engineering — so it is OS's *indigenous* electromagnetic-weapon pinnacle, not stolen
tech (fits "OS advances slower than the Clans, but on its own"). TechRating **F** (candidate), Faction LEGION.
Heavy tonnage / high crit / high BV & cost — the Advance "abandons serviceability & safety discipline for raw
performance" tax. Contrast: the **Railgun/Railcannon** idea (see below) would be the *Experimental* step beyond.

### Core mechanic — power-gated munitions (the engineering problem turned into a feature)
- **Physical basis:** launch acceleration ∝ muzzle energy. A full-power coil launch imposes thousands of g —
  fine for rugged inert penetrators, **fatal to fuzed / payload / electronic rounds**.
- **FULL power = rugged kinetic rounds only** (standard slug, AP/APDS): max range, max damage, inherent AP —
  the "Gauss-killer" profile. **LOW power = fragile/payload rounds survive** (Flak, Flechette, HE/incendiary,
  Precision) but **both range AND damage drop**.
- This **converts an intractable materials problem** (harden every exotic round against hypersonic launch)
  **into a tractable control-system + doctrine problem** (throttle down so the round survives). It sidesteps
  the problem honestly rather than hand-waving it.
- **Gate is by round RUGGEDNESS, not special-vs-standard:** AP is a "special" round but stays full-power (it is
  a hardened penetrator), preserving the full-range AP signature.
- **Edge cases to settle:** Caseless (casing, not fragility → likely full-power), Rocket-Propelled (own rocket
  motor → maybe disallowed on EM Lance), Tracer (TBD).

### Implementation path (when coding)
Map onto the **existing ATM variable-profile mechanism** — the loaded ammo type selects the range/damage
profile, exactly like ATM ER/Standard/HE in `WeaponType.getRanges` (`M_EXTENDED_RANGE` / `M_HIGH_EXPLOSIVE`
blocks). Kinetic rounds → "ER/full-power profile"; payload rounds → "low-power/short profile + effect". The
player's "output selection" = which ammo bin is fired; an optional manual throttle mode can be layered later.
AP via `makeArmorPiercing` (ACAPHandler pattern, as used in Phase 2).

### Lore anchors
**Silver Bullet Gauss** (proves Gauss can fire sabot-delivered non-standard projectiles), **HAG**, **Magshot** —
the design space is canon-explored; EM Lance pushes the Silver-Bullet sabot/payload idea to its limit plus
variable coil output. Single hardest in-universe problem = munition survival under coil launch (solved by the
power-gate above).

### Philosophy (§0.1) resolution
EM Lance *looks* like the "do-everything" weapon OS rejects — but the power-gate means it can **never** have
versatility AND performance in the same shot. It **pays the "no do-everything" tax continuously, shot by shot**,
rather than escaping it. That makes the Advance-tier exception self-consistent (internalizes the OS creed into
the mechanism, not just into a high BV).

### Open (before coding)
Confirm Advance tier; exact ranges vs the OS Gauss line; how hard the low-power range/damage penalty is; final
munition→power-tier assignment; heat model (coil discharge → more heat than a standard Gauss's ~1?); shared AC
ammo enum vs dedicated EM Lance ammo.

### Successor concept — Coil Augmented Railgun (hybrid) — SPECCED (design pinned 2026-07-24)
**Status: IMPLEMENTED — committed as `b64ff86193` + round-report / shed-barrel follow-ups (the doc had simply lagged the code; corrected in the 2026-07-31 sync).** The hybrid that fuses coilgun + railgun and is
arguably **the single most "OS" weapon in the tech base** — the real-engineering hybrid *is* the
maintainability-first way to build a railgun. **Base version name = Coil Augmented Railgun.**

**Real engineering basis (both routes borrow the coil to cure the rail's flaw):** the railgun's death is
**rail self-erosion** (megaamp current + hypervelocity sliding contact). Two real mitigations, both coil-based:
1. **Field augmentation** — external coils boost field B; since F = J×B, the same force needs far **less current
   J** → less arcing/ablation → dramatically longer rail life.
2. **Coil pre-acceleration + short rail final stage** — the coilgun launches the slug contactlessly to high
   speed, so it enters rail contact only for the **last brief stage** → minimal contact time/erosion.
Both leverage OS's coilgun mastery to tame the railgun's erosion while keeping its higher muzzle-velocity ceiling.

**Why it's "most OS" (§0.1):** OS engineers a flaw away rather than accepting it (Clan) or avoiding it (raw) — it
reclaims most of the railgun's violence *within* the maintainability creed, grown from the EM Lance (§8) as its
successor.

**Tier & placement (CONFIRMED):** **Advance — the crown of OS's *serviceable* electromagnetic line, ABOVE EM
Lance.** Raw **Railgun / Railcannon = SHELVED** for now (the hybrid is OS's accepted answer; the raw rail may
return later as the Experimental creed-abandoning extreme).

**Identity vs EM Lance — TRADES flexibility for pure kinetic supremacy (NOT a strict upgrade):**
- **EM Lance = flexible premium:** power-gated variable output + multi-munition, lighter, cheaper, zero erosion.
- **Coil Augmented Railgun = pure single-slug AP penetrator apex:** longest range + highest single-shot damage +
  inherent AP, but fires **ONE munition only** (a rugged AP kinetic slug) — **NO munition variety, NO variable
  output**. It gives up the EM Lance's entire flexibility to buy raw performance; both stay relevant.
  *(Corrects the earlier draft that had the hybrid "inherit" the power-gated munitions.)*

**Pinned stats:**
| field | value |
|---|---|
| Damage | **30**, flat (no range falloff) |
| Range | **min 0 — no minimum-range penalty** / S 9 / M 18 / L 27 / E 36 |
| Heat | **6** (hottest Gauss-family — residual rail ohmic/contact heat) |
| Tonnage | **22** |
| Crit slots | **13** |
| Ammo | **dedicated NEW Railgun ammo, 3 shots/ton** — inert AP slug, does NOT explode |
| Weapon crit-explosion | **30** (= damage); bigger than EM Lance, milder than raw rail |
| To-hit modifier | **none** at baseline (accuracy lane belongs to the repurposed precision HVAC) |
| BV / cost | ≈ 700+ / ≈ 1.1M (compute via formula) |
| Tier / rating / faction | **Advance** · TechRating **F** · LEGION |

**Special mechanics:**
- **Inherent AP (long-barrel only):** every hit rolls a **through-armor critical** (reuse the `OSGAAMHandler` /
  `ACAPHandler` pattern). AP is velocity-driven, so it is **LOST in barrel-shed mode** (see below).
- **Barrel wear (the erosion identity):** the barrel fires **stably for the first 9–12 shots per battle** (≈ 3–4
  tons of ammo at 3/ton). **Past that threshold**, the weapon takes a flat **+2 to-hit penalty (does NOT stack)** and
  each further shot **jams on a natural-2 attack roll**. **Every jam then adds a cumulative +1 penalty; when that jam
  stack reaches +5 the weapon is *scrapped* (报废) — permanently inoperable for the rest of the battle.** Reliable in
  a burst; degrades and can self-destruct under sustained spam.
- **Barrel-shed mode (player escape hatch — ONE-WAY):** at any point the player may **jettison the rail barrel**.
  Irreversible for the battle: range collapses to **min 0 / S 4 / M 6 / L 9 / E 12** and damage drops to **25**, in
  exchange for a **fully stable weapon — no barrel-wear, no +2, no jam, no degradation stack**. Turns the long-range
  apex into a reliable short-range 25-dmg brawler — the way to keep fighting once the barrel is spent, or to pre-empt
  the wear spiral. **Inherent AP is LOST** (velocity-driven — the coil-only shot no longer reaches the AP threshold;
  it deals ordinary 25 damage). The weapon can still crit-explode.
- **Flat damage / no falloff:** full damage at every bracket in both configs (30 long-barrel / 25 shed).
- **Single munition:** only the AP kinetic slug.

**Open (minor, before coding):** exact stable threshold within **9–12**; whether the flat +2 counts toward the +5
scrap total or is separate (**default: separate — +5 is the jam stack alone**); **jam-trigger math** — a natural-2 is
only ~2.78%/shot, so reaching +5 jams is a long-tail event; if scrapping should be a real risk, widen the trigger
(e.g. natural 2–3, or a wear-scaling jam chance). Barrel-shed ranges (4/6/9 base) still tunable; BV formula; ammo-enum name.

### Related decisions this session (their own write-ups still pending)
- **HVAC repurpose (§2 redesign) — ✅ DONE 2026-07-31.** Now a long-barrel precision anti-armor AC (see §2 for
  the full write-up): calibers 10/12/14/16, dedicated `HVAC_OS` ammo, and a range-scaling to-hit bonus
  (short 0 / med −1 / long −1 / extreme −2 — tuned down from the med −2 / long −3 first draft), priced into a
  high BV (165/195/225/255).
- **Heavy LRM/SRM → Ultra-missile — ✅ DONE 2026-07-31.** Heavy LRM (10/20/30) and Heavy SRM (4/6/8/12) gained
  **Single / Ultra** fire modes: Ultra fires 2 volleys (each its own cluster roll), consumes 2 ammo, doubles heat
  (via a new `Mounted.getNumShots` clause gated on LRM/SRM ammo + Ultra mode), and **jams on a natural-2 attack
  roll**; Single is a plain launcher. Weapon `heat` re-based to the single-volley value; BV re-tiered to ≈1.6×
  single (the Ultra-AC convention). The 4-volley escalation remains deferred pending playtest.
- **Railgun / Railcannon:** a *more violent* future direction (real-rail Lorentz accelerator, not a coilgun).
  Because its defining flaw is **self-erosion of the rails** — the antithesis of OS's maintainability creed —
  it is **Experimental**, or a deliberate rupture of the OS identity. **OS's preferred answer is the
  Coil Augmented Railgun hybrid (see §8 successor note), which engineers the erosion away at Advance tier**; the
  raw Railgun/Railcannon may end up narrative-only. Under analysis; not yet specced.

---

## 9. OS Modular Electronics — Battle Computer System (BCS) & Combat Computer System (CCS) (added 2026-07-15, fully redesigned 2026-07-18)

**Status: IMPLEMENTED — core equipment + most mechanics (as of 2026-07-19; ~17 commits on `au-0.50.12`, unpushed,
not yet playtested).** Supersedes the original single-"backbone" sketch (channel-arbitration model dropped); the
electronics counterpart to the §8 weapon apex.

**Implementation log (2026-07-19):**
- **All §9 equipment defined, registered, buildable, compiling** (equipment-init tests pass) — CCS: C-2500 core, CC
  Ballistic/Energy/Missile modules, Composite TC; BCS: B-2500 core, OS Guardian / Improve-Guardian / Improve-Angle
  ECM, C3 Node + Improve-C3-Node (weapons), C3 Point (misc), Advance cores C-X280 Crow Nest / Raven CEWS / G-X100
  Ghost Core, Demon Hacking System (weapon).
- **Mechanics wired:** CCS weapon-type −1 to-hit (needs C-2500 + matching module / Composite); C-2500 aimed-shot
  (grants no TC −1); B-2500 / Crow-Nest +1 initiative (ECM-suppressible); B-2500 per-turn 2d6 coordination roll →
  network −1 direct-fire on 10+ (**UNTESTED**); OS ECM jams; OS C3 networks (Boosted).
- **Construction legality:** CC modules / Composite require the C-2500 core; Composite ⊥ specialized; one BCS core per mek.
- **Final stats:** both cores **2 t / 2 crit**. CC modules scale by weapon family — **max(2, ⌈family tonnage ÷ ratio⌉)**,
  ratios ballistic **÷6**, missile **÷7**, energy **÷8**; Composite = total weapon tonnage **÷6**. Record-sheet short
  names "C-2500 … FCM".
- **Also landed this session (missile FCS):** re-dated the OS missile FCS to their tiers (Artemis IV → Standard
  2805/2820/2840; Diana III → Standard E/2805; Orion V → Advance 3060/3070/3080); added **OS Artemis-capable missile
  ammo** for all Artemis-compatible OS families (LRM 5–30, SRM 2–6, MML 3–14 LRM+SRM) via an OS-tech Artemis mutator.

**Bugfix — CC modules duplicated on MTF load (2026-07-19):** the CC Ballistic/Energy/Missile + Composite modules
are *loadout-derived variable-crit* equipment, but the MTF loader was allocating all their slots up front from a
not-yet-fully-parsed weapon list. The allocated slot count didn't match the slot lines written in the file, so the
"slot already full → skip" dedup missed and the loader spawned duplicate CC Energy/Ballistic mounts (worse after any
change to the tonnage/crit formula, since old saves then carried a stale slot count). Fix: route these modules
through the same slot-by-slot path the Targeting Computer uses — new predicate `MiscType.isOSCombatComputerModule()`,
and `MtfFile` now builds one shared mount without pre-allocating slots (`addTargCompWithoutSlots`) and adds a
`CriticalSlot` per file line. `:megamek:compileJava`, `MtfFileTest`, and `BulkRoundTripTest` all pass. **Known gap
(separate):** MML in-editor weapon edits still don't auto-resize these modules (no `updateTC`-equivalent hook) — any
loadout-derived variable-crit OS component added in future must be registered in `isOSCombatComputerModule()` and
given a MML resize hook.

**Verified — C3 Node = canon C3MM company hub (2026-07-19):** confirmed (design intent) that the C3 Node's `F_C3MBS`
mapping gives the canon C3 hierarchy: **one** node = a C3 lance master (shows `(3S free)`), **two** nodes on one mech
= a **company commander (C3MM)** that anchors a company-scale 12-unit network (2 master-to-master links + a 3-slave
lance). New regression test `OSC3NodeCompanyCommandTest` (mirrors `C3MasterBVTest`, using `OSC3Node`/`OSC3Point`)
asserts `hasC3MM()`, `calculateFreeC3MNodes()==2`, `calculateFreeC3Nodes()==3`, and that a full 12-unit company all
reports `onSameC3NetworkAs` the commander — all pass. NOTE: the `(3S free)` a player sees on an *un-networked* two-node
mech is just the lobby's unlinked-master rendering (`LobbyMekCellFormatter` `getC3Master()==null` branch never checks
`hasC3MM()`); the `(2M, 3S free)` company-commander line appears once the unit is set as its own master in a C3 network.
No code change needed — this is inherited canon behavior and matches the design.

**Implemented — B-2500 Tactical Coordination: report line + 12→initiative+2 (2026-07-23):** the per-round 2d6
coordination roll (`Entity.newRound` → `getBcsCoordinationRoll`) is now surfaced and its +2 branch wired.
- **Report (before Movement):** `TWGameManager.writeInitiativeReport()` emits one line per active B-2500 / Crow Nest
  core in the INITIATIVE_REPORT phase (which renders before the Movement phase, per the requested timing). Shows the
  2d6 roll + effect (≥12 → network −1 vs direct-fire **and** force initiative +2; 10–11 → network −1; <10 → no effect),
  all "negated by hostile ECM". **Double-blind aware:** `Report.PUBLIC` in normal games, owner/subject-visible under
  `doBlind()` (no shared header when blind). Report templates 1268/1269 (OS 1260s block).
- **12 → initiative +2:** new `bcs` component on `InitiativeBonusBreakdown` (positive, non-stacking per Xotl) fed by
  `Player.getBcsCoordinationInitBonus()` (returns 2 when an active, on-board, non-ECM-jammed BCS core rolled 12).
  Threaded through `Team` (team init) **and** `TurnOrdered` (individual init) **and** `SerializationHelper` (save
  compat, old saves default 0). Displays as "+2 B-2500 Coordination" in the init breakdown. Added
  `Entity.setBcsCoordinationRoll` (test hook).
- **Bug caught during test:** `Report.addDesc()` emits **two** `<data>` tokens (unit + owner), so template 1269 needed
  the canon `<data> (<data>) …` prefix; the first draft dropped the effect text (only the roll showed). Fixed + guarded
  by a `Report.text()` rendering assertion.
- **Tests:** new `OSBattleComputerInitiativeTest` (6) — no bonus <12, +2 on 12, no core / inactive → none, breakdown
  stacking, and report rendering; updated the existing `InitiativeBonusBreakdownTest` (19) for the new field. All pass.
- **Still open (pre-existing):** the B-2500's *constant* +1 initiative only applies via the TCP-implant path
  (`getTCPInitBonus` is gated on `MD_TRIPLE_CORE_PROCESSOR` + VDNI/BVDNI), so a normal-pilot B-2500 gives +0 on non-12
  turns and only the new +2 on a 12. Making the constant +1 grant standalone is a separate follow-up.

**Added — Network Target Designators (Kestrel / Shrike / Lodestar) — DESIGN + stubs (2026-07-31):** a new
BCS-adjacent C3-network designator sub-family (full design in §9.6). **Kestrel NTD** (Improve) and **Shrike
Combat Designator** (Enhanced) are defined as TAG-subclass stubs, flagged (`F_OS_KESTREL_DESIGNATOR` /
`F_OS_SHRIKE_DESIGNATOR`), and registered — compile-clean, same pattern as Demon. **Lodestar Guidance Beacon**
(a Narc-beacon munition on the OS Narc / Munin launcher) is designed but not yet coded. **All mechanics TODO** (§9.5).

### 9.0 Architecture & philosophy
Two systems, each needing its **own core**; a mech may mount both, but two cores is a heavy tonnage/crit tax —
that tax (NOT a per-attack arbitration rule) is the balancer.
- **BCS — Battle Computer System** (*lateral*: battlefield info / command / coordination / EW). Lineage: the SLDF
  Cyclops **Tacticon B-2000 Battle Computer**, which OS democratizes into a serviceable modular family.
- **CCS — Combat Computer System** (*vertical*: one mech putting rounds precisely on target). Lineage: **TC + AES**.
- BCS/CCS resolve the canon incompatibilities (AES⊥TC, AES⊥MASC, standalone C3/TAG) not by picking one, but by
  making them a modular ecosystem on shared cores. Modular tracks = §0.1 serviceability ideal; the integrated
  Advance cores = §0.2 "abandon modularity for integrated performance."

Common fields: TechBase **OUTER_SPHERE**, Faction **LEGION**. BV follows the TC pattern (**bv=0 on the item,
modifies served weapons' BV**). Tier dating per §0.3: Standard `2805/2820/2840`, Improve `2900/2930/2960`, Advance
`3060/3070/3080`.

### 9.1 BCS — two tracks

**Track 1 — Modular: B-2500 core + plug-in modules.**

**Tacticon B-2500 Battle Computer Core — 2 t / 2 crit — Standard tier — prerequisite for all BCS modules**
(designation *B-3000* is reserved for a future evolved core). Native "Tactical
Coordination" (**ECM-suppressible, NOT immune** — if the B-2500 mech sits under hostile ECM the whole effect is
jammed, preserving Guardian/Angle's value):
- Constant: **+1 initiative** to the force.
- Each turn roll **2d6**: on **10+ (16.7%)** all networked units get **−1 to-hit vs DIRECT-FIRE weapons** that turn.
- On **12 (2.8%)**: initiative bumps to **+2** that turn.
- Anti-stack: this network −1 does **not** stack with the Improve C3 Point focus-fire −1 (BCS network-coordination
  cap = −1 total).

Modules (weights shared-chassis-discounted — the core owns the backbone; "**Boosted for free**" = every OS C3 link
is ECM-resistant, a signature priced into BV; ⏳ Improve-line weights are draft):

| Module | Tier | t / crit | Effect | Maps to |
|---|---|---|---|---|
| C3 Node | Standard | 2 / 2 | Boosted C3-Master equiv; links 3 Nodes/Points | F_C3M / F_C3SBS |
| C3 Point | Standard | 1 / 1 | Boosted C3-Slave equiv | F_C3S / F_C3SBS |
| Guardian ECM | Standard | 1 / 1 | ECM bubble | F_ECM |
| Improve C3 Node | Improve | 2 / 2 | links 3 Nodes + **6 Points** | F_C3M + code |
| Improve C3 Point | Improve | 1.5 / 1 | built-in TAG + **focus-fire**: network units attacking a target this network TAG'd get **−1** | F_C3S + F_TAG + new |
| Improve Guardian ECM | Improve | 0.5 / 1 | lighter ECM | F_ECM |
| Improve Angle ECM | Improve | 1 / 1 | lighter Angel-class ECM (display name "Angle" per design) | F_ANGEL_ECM |
| **Demon Aggressive Hacking System** | **Advance** | 1.5 / 1 | offensive EW — see below | new (crit-capable TAG) |

**Demon Aggressive Hacking System (Advance):** a TAG-like designator that rolls to-hit **and can score a critical
hit** (the only TAG-type system that can). Against the hacked target this turn, when it shoots at your units: hit →
**+1** to-hit penalty; crit → **+2**; and if the target is also inside a same-network Guardian/Angle ECM bubble the
penalty **doubles → max +4** (crit+ECM). +4 kept as the Advance signature (needs the full hit-crit-in-your-ECM combo).

**Track 2 — Specialized Advance cores** (self-contained cores developed *from* BCS tech; **mutually exclusive with
the B-2500 and with each other** — one core per mech; do NOT host modules, no shared-chassis discount):

| Core | Effect |
|---|---|
| **C-X280 Crow Nest EW Core** | integrates a battle-computer + C3-Node; network **locked pre-battle to EITHER the C3 net OR the Raven net** (no mid-battle switch). Command: **+2 initiative + the B-2500 coordination roll** (ECM-suppressible). |
| **Raven CEWS** | Watchdog-class combined EW + a C3i-style **6-unit egalitarian "Raven net"** + built-in Light TAG + Improve Guardian ECM. **No** initiative bonus. |
| **G-X100 Ghost Core** | derived from Raven CEWS; Nova-CEWS-style stealth compatibility (coexists with Null-Sig + Chameleon LPS; since NSS⊥Stealth-Armor you effectively pick one). Drops TAG, keeps Guardian ECM. **No** initiative bonus. |

**Network size cap:** normal OS C3 reaches **company scale (~26 units)**; battalion scale reserved for future
superheavy units. ⏳ code: canon assumes a 12-unit cap.

### 9.2 CCS — core + modules

**C-2500 Combat Computer Core — 2 t / 2 crit — prerequisite for all CC modules. Baseline effect = Aimed Shot**
(the "aiming brain" lives on the core; modules only supply the −1, so aimed-shot is not bound to any one weapon-type
module).

| Component | Tier | t / crit | Effect |
|---|---|---|---|
| CC Ballistic Module | Standard | max(2, ⌈ballistic wpn t ÷6⌉) | **−1 to-hit for ballistic** direct-fire weapons |
| CC Energy Module | Standard | max(2, ⌈energy wpn t ÷8⌉) | **−1 to-hit for energy** weapons |
| CC Missile Module | Standard | max(2, ⌈missile wpn t ÷7⌉) | **−1 to-hit for missile** weapons (canon TC can't); no aimed-shot; no cluster bonus (leave to Artemis) |
| Composite Targeting Computer | Standard | total weapon tonnage **÷6** | **−1 to ALL** weapon types; **requires the core; mutually exclusive with the specialized modules** |

- Specialized module weight/crit = **max(2, ⌈that weapon family's tonnage ÷ ratio⌉)** — ratios ballistic **÷6**
  (heaviest; its −1 is the most valuable since ballistics have the fewest native accuracy options), missile
  **÷7**, energy **÷8** (lightest). The 2 t/2c floor is the per-module overhead that preserves "one specialized ▸
  Composite, several specialized ◂ Composite" (holds for realistic mixed loadouts up to ~30 t total; Composite ÷6
  widens that a little). **BV also scales with served weapons** (prices the premium).
- Module names keep "**& Recoil-Compensation / & Tracking**" as engineering *flavor only* — no extra mechanics (BT
  does not model mech-scale recoil; a forced movement-penalty proxy was dropped as counter-intuitive).
- **Efficiency relationship:** one specialized module (focused loadout) beats the Composite; several specialized
  modules (mixed loadout) lose to the Composite. Holds for realistic loadouts (focused >15 t → specialized; 2-type
  <30 t / 3-type <45 t → Composite).
- **Enhanced Combat Computer** — SHELVED for now (was to be the Advance do-everything apex; the name also collided
  with the §0.2 "Enhanced" tier).
- **Mountable on all unit types (decided 2026-07-31):** the whole CCS — C-2500 core, all three CC modules, and the
  Composite TC — carries the same entity flags as the canon Targeting Computer (`F_MEK_EQUIPMENT + F_TANK_EQUIPMENT +
  F_SUPPORT_TANK_EQUIPMENT + F_FIGHTER_EQUIPMENT`), so it is buildable on Meks, combat & support vehicles, and
  aerospace fighters. OS rejects Mek-exclusive systems; the "*one mech*" framing in §9.1 is thematic, not a
  construction restriction. `F_HEAVY_EQUIPMENT` (canon TC's support-vehicle weight-class marker) is the only TC flag
  not copied — add it if full parity is wanted. ⏳ playtest: the module/Composite −1 to-hit
  (`ComputeAttackerToHitMods`) and the variable tonnage/crit on non-Mek chassis are UNTESTED on vehicles/fighters.

### 9.3 Cross-system rules & tiers
- **Direct-fire stacking:** a direct-fire weapon may take **CCS module −1 + BCS network −1 = −2** total (allowed).
  The BCS-internal network-coordination cap (−1) still holds; the −2 is the BCS×CCS cross-system sum, reachable only
  on a B-2500 10+ turn with both cores mounted.
- **Tiers:** **Standard** = B-2500 Core, C-2500 Core, C3 Node, C3 Point, Guardian ECM, all three CC Modules,
  Composite Targeting Computer. **Improve** = the four "Improve X" modules. **Advance** = C-X280 Crow Nest, Raven
  CEWS, G-X100 Ghost Core, Demon. *(Cores/CC-modules at Standard extends the "C3 Node = Standard" ruling; ⏳ adjust
  if the cores should sit at Improve.)*

### 9.4 Implementation hooks
- **Reuse:** C3 (F_C3S/M/SBS/I @ MiscType 4589–4767), ECM (F_ECM/F_ANGEL_ECM @ 5992/6106), TAG (F_TAG), combined-EW
  pattern (createWatchdogECM/createNovaCEWS @ 6229/6271), TC accuracy (ComputeAttackerToHitMods:259) + Aimed-Shot
  mode, initiative/command (Player.getCommandConsoleBonus:740 / hasTCPCommandEquipment:915 / isEntityECMAffected:939),
  stealth-exemption precedent (TestMek:1381 Nova-CEWS null-sig exemption).
- **New wiring:** B-2500 per-turn coordination roll + ECM-suppression gate; Improve C3 Point focus-fire −1; Demon
  offensive to-hit debuff (crit-capable TAG); C-2500/module "requires core" gate; Composite⊥specialized rule;
  network cap raise (12→26); C-X280 pre-battle network lock.

### 9.5 Open (⏳)
Mechanics-remaining (best implemented test-driven; all UNTESTED until the unified playtest):
- ~~B-2500 coordination roll — the **+2-initiative-on-12** branch~~ ✅ DONE 2026-07-23 (network −1 was already wired; +2 now wired + reported).
- **Demon** per-turn to-hit debuff (+1/+2, doubled to +4 under friendly ECM, escalated on a Demon crit).
- **Improve C3 Point** focus-fire −1 (needs Improve C3 Point defined as a TAG-capable component first).
- **Network cap → 26** (company scale) + Improve C3 Node's 6-Point topology.
- **Advance-core behaviors:** Crow Nest C3/Raven network lock + full +2 command; Raven 6-unit egalitarian net +
  built-in Light TAG; Ghost stealth-system coexistence.
- **BCS module → B-2500 core prerequisite** (currently C3/ECM work without a BCS core — inconsistent with the CCS
  core-prereq).
- Exact per-module BV factors; Enhanced Combat Computer revival + rename. ~~optional Round-Report line for the
  coordination roll~~ ✅ DONE 2026-07-23 (initiative-report line, double-blind aware).
- **B-2500 constant +1 initiative** currently only applies through the TCP-implant path — make it grant standalone.
- ~~**Network Target Designators (§9.6):** Kestrel −1, Shrike −1/−3 crit, Lodestar beacon pod, Lodestar LRM
  indirect-fire enable, and the non-stacking network-coordination cap (Kestrel/Shrike/B-2500)~~ ✅ DONE 2026-08-01
  (see §9.6). **Remaining:** Improve C3 Point focus-fire −1 has no class yet — when built it should route through
  `OSNetworkDesignators.networkCoordinationToHit` to join the cap. All §9.6 mechanics are UNTESTED in-game.

### 9.6 OS Network Target Designators — Kestrel / Shrike / Lodestar (added 2026-07-31)

A BCS-adjacent sub-family of C3-network target designators. Each is **dead weight unless the mounting / firing
unit is in an active OS C3/BCS network** — their entire value is network-shared targeting data. Two are
TAG-derived designator devices carried on a unit; the third is a Narc-beacon munition.

**Status (2026-08-01): MECHANICS IMPLEMENTED — UNTESTED in-game.** Kestrel −1 direct-fire and Shrike −1/−3 are
wired in `ComputeToHit` (via `weapons/c3/OSNetworkDesignators`); Shrike records its designation + natural-9+ crit
on the target through a custom `ShrikeTAGHandler`; Lodestar is coded as an OS **Munin (iNarc) beacon munition**
(`M_LODESTAR` → `INarcPod.LODESTAR`) whose beacon-in-range path feeds the Kestrel −1. The Lodestar **LRM
indirect-fire enable** and the non-stacking **network-coordination cap** (Kestrel/Shrike/B-2500 share one −1)
are also wired (2026-08-01). All mechanics landed; **UNTESTED in-game.**

| Item | Tier | t / crit | BV | Cost | Role |
|---|---|---|---|---|---|
| Kestrel Network Target Designator | Improve | 4 / 3 | 130 | 200,000 | network −1 direct-fire vs a designated target + Lodestar-enabled LRM indirect fire |
| Shrike Combat Designator | Enhanced | 3 / 2 | 110 | 320,000 | crit-capable TAG: network −1 (−3 on crit) vs a designated target |
| Lodestar Guidance Beacon | Improve | Munin (iNarc) pod, 4 shots | — | 12,000 | `M_LODESTAR` beacon that plants the remote lock a networked Kestrel reads |

**Kestrel Network Target Designator (Improve).** A TAG-mode designator. While its unit is in an active C3/BCS
network **and** either (a) it holds LOS to the designated target, or (b) a **Lodestar beacon** is attached to the
target and the target is within the Kestrel's own range, the **whole network gains −1 to-hit against that target
with all direct-fire weapons**. The Lodestar path additionally grants the network **LRM indirect-fire
capability** against the beaconed target (the beacon is the remote lock; no spotter LOS needed). Kestrel carries
no self to-hit modifier — the value is purely the shared network reading.
> *Design note:* this **supersedes** the earlier "network reads the target's range brackets at close range"
> sketch. Finalized 2026-07-31 as a flat **−1 direct-fire + Lodestar LRM-indirect enable** (the OSKestrelDesignator
> Javadoc has been updated to match).

**Shrike Combat Designator (Enhanced).** A crit-capable TAG. On a successful designation, every same-C3-network
unit attacking that target **this turn** gets **−1 to-hit**, deepened to **−3** when the designation roll is a
critical (natural 9+). Independent of Kestrel/Lodestar (no beacon needed). Dead weight outside a network.

**Lodestar Guidance Beacon (Narc munition).** A **special beacon pod fired from the OS Narc / Munin launcher**
— it reuses the existing OS beacon-ammo family (see `AmmoType.makeOSBeaconAmmo`, the `OSNarcPods` / `OSMunin*`
line, `AmmoType.java`). On a hit it attaches to the target like any Narc pod. It does nothing on its own; it is
the **remote lock a networked Kestrel consumes**: units in a C3 network **containing a Kestrel NTD** gain LRM
indirect fire against the beaconed target, and while the beacon is within that Kestrel's range the network gets
the −1 direct-fire reading even without Kestrel LOS. **Resolved params (2026-08-01):** a new `Munitions.M_LODESTAR`
fired from the OS **Munin** (iNarc) launcher; on hit it attaches an `INarcPod.LODESTAR` (team-owned, persists until
removed, like any Narc pod); 4 shots / 12,000 C-bills. The Kestrel beacon-in-range −1 reads it via
`OSNetworkDesignators.hasNetworkKestrelPaint`. **Still TODO:** the LRM indirect-fire enable (see §9.5).

**Relationships & anti-stack.**
- Kestrel + Lodestar = the *information / indirect* combo (reach + −1 direct-fire without LOS). Shrike = the
  *burst direct-fire* combo (−1/−3 this turn, driven by the Shrike unit's own designation). Separate paths; a
  network could field both.
- ⏳ **Stacking rule to confirm:** the Kestrel/Lodestar network −1 and the Shrike network −1 are both "network
  to-hit vs a designated target." Proposed: both fall under the §9.1 **BCS network-coordination −1 cap** (do not
  stack with each other, nor with the B-2500 coordination −1 / Improve C3 Point focus-fire −1). A direct-fire
  weapon may still add a CCS module −1 on top (§9.3 cross-system −2 ceiling).
- **ECM:** as network-coordination effects, all of these are **ECM-suppressible** like the rest of the BCS
  (§9.1), pending the mechanics pass.

**Implementation (landed 2026-08-01 — compiles + AmmoType/EquipmentType tests pass; UNTESTED in-game).**
- **Kestrel −1 direct-fire ✅:** passive — `ComputeToHit` calls `OSNetworkDesignators.hasNetworkKestrelPaint`
  (some unit on the attacker's active C3 net, incl. the attacker, mounts an operational Kestrel that is in range
  of the target and either has LOS or reads a friendly Lodestar beacon). Gated off indirect + artillery.
- **Shrike −1/−3 ✅:** `ShrikeTAGHandler` (a `TAGHandler` subclass) records the designation + `roll ≥ 9` crit on
  the target (`Entity.shrikeDesignatedBy` / `shrikeDesignationCrit`, reset in `newRound`); `ComputeToHit` reads it
  via `OSNetworkDesignators.shrikeToHitBonus` (−1, or −3 on crit) for same-network attackers.
- **Lodestar pod ✅:** `Munitions.M_LODESTAR` (appended to keep ordinals stable) → `INarcPod.LODESTAR`, attached
  by `NarcHandler`; ammo `createOSLodestarAmmo` (OS Munin/iNarc, 4 shots, 12,000). Detected by the Kestrel
  beacon-in-range path.
- **Kestrel/Lodestar LRM indirect enable ✅ (2026-08-01):** in the `ComputeToHit` indirect spotter block, an LRM /
  LRM-IMP / MML / NLRM attack whose target carries a friendly Lodestar beacon (with a Kestrel on the attacker's
  network in range) **self-spots** — `spotter = te; narcSpotter = true` (mirrors the Narc self-spot fallback), so
  indirect fire is legal without a normal spotter. ECM-suppressible (skipped when the target is ECM-affected).
- **Anti-stack network-coordination cap ✅ (2026-08-01):** the Kestrel −1, Shrike −1/−3 and B-2500 coordination −1
  are now a single non-stacking bonus — `OSNetworkDesignators.networkCoordinationToHit` returns only the **deepest**
  of them (applied once in `ComputeToHit`). The B-2500 −1 was moved out of `ComputeAttackerToHitMods` into this
  consolidated computation. A CCS module −1 (fire control) still stacks on top (§9.3: direct-fire may reach −2).
  *(Improve C3 Point focus-fire −1 has no class yet, so it is not part of the cap until it is built; when added it
  should route through `networkCoordinationToHit`.)*

## 10. OS design saves — cross-device import (added 2026-07-19)

My custom OS unit designs are version-controlled in **`development/os-designs/`** (27 `.mtf` files as of
2026-07-19). MegaMek/MegaMekLab actually *read* them from **`megamek/userdata/data/mekfiles/`**, which is
git-ignored (`.gitignore` → `megamek/userdata/**`), so the tracked copy under `development/` is the source of truth
that travels with this repo. Import = copy them from `development/os-designs/` into the userdata mekfiles folder.

**Import on another device (Windows / PowerShell), run from the megamek repo root:**

```powershell
# 1. Get the latest branch + design files
git checkout au-0.50.12
git pull origin au-0.50.12

# 2. Copy the tracked designs into the (git-ignored) userdata folder MML/MM loads from
New-Item -ItemType Directory -Force "megamek\userdata\data\mekfiles" | Out-Null
Copy-Item "development\os-designs\*.mtf" "megamek\userdata\data\mekfiles\" -Force

# 3. Rebuild + launch MegaMekLab so it picks up this fork's equipment, then open the designs
cd ..\megameklab
.\gradlew.bat clean run
```

macOS/Linux equivalent for step 2: `mkdir -p megamek/userdata/data/mekfiles && cp development/os-designs/*.mtf
megamek/userdata/data/mekfiles/`.

**Sync new/edited designs back:** copy them the other direction and commit —
`Copy-Item "megamek\userdata\data\mekfiles\*.mtf" "development\os-designs\" -Force`, then
`git add development/os-designs && git commit`. (The tracked `development/os-designs/` copy is what's shared; the
`userdata/` copy stays local and git-ignored.)

## 11. OS Actuator & Motive Systems — AES-I & AMS-I (design 2026-08-06; IMPLEMENTED 2026-08-09 — compiles + equipment tests pass, NOT playtested; see dev log)

OS Improve-tier takes on two canon "system enhancement" devices. Both are **Improve tier**: `TechBase.OUTER_SPHERE`,
rating **E**, `ISAdvancement 2900/2930/2960`, static **STANDARD**, `omniFixedOnly`. Neither is a plain stat-reskin —
the AES is **reconceived as a mobility/handling system** (see below). **Design only; no code yet.**

### 11.1 Improved Armored Motive System (AMS-I) — combat/support VEHICLE, defensive

> ⚠️ This is the **Armored Motive System** (anti-motive-crit), NOT the Anti-Missile System.

| Field | Value | vs canon |
|---|---|---|
| Internal name | `OSImprovedArmoredMotiveSystem` (+ alias `Legion Improved Armored Motive System`) | — |
| Flags | `F_ARMORED_MOTIVE_SYSTEM` + `F_TANK_EQUIPMENT` + `F_SUPPORT_TANK_EQUIPMENT` | same (not Mek-ised, per OS-not-Mek-exclusive) |
| Weight | vehicle tonnage × **0.10** | canon IS 0.15 / Clan 0.10 → Improve −weight (matches Clan) |
| Crit / tank slots | **0** (chassis mod) | same |
| Effect | **−2 to the motive-system damage roll** (`TWGameManager` `hasWorkingMisc(F_ARMORED_MOTIVE_SYSTEM)`) | same (Improve keeps performance) |
| Cost / BV | AMS-tonnage × **120,000** C-bill / BV **0** | canon ×100k (slight OS premium) |
| Tech | OUTER_SPHERE / E / 2900·2930·2960 / STANDARD / avail F·E·D·D | canon E / ADVANCED / 3071 |

### 11.2 Improved Actuator Enhancement System (AES-I) — MEK ONLY, mobility/handling

**Reconception:** canon AES gives a static per-limb −1 to weapon + physical attacks. The OS AES-I is instead a
**whole-Mek actuator/handling system** — it steadies gunnery at speed and muscles through the movement penalties of
heavy gear. **Mek only** (no vehicles/aero — decision 1).

| Field | Value | Note |
|---|---|---|
| Internal name | `OSImprovedAES` (+ alias `Legion Improved Actuator Enhancement System`), short **AES-I** | — |
| Flag | **NEW `F_OS_AES`** + `F_MEK_EQUIPMENT` | distinct from canon `F_ACTUATOR_ENHANCEMENT_SYSTEM` so it triggers NONE of the canon per-limb/physical logic, the AES⊥TC/⊥MASC validator, or `hasFunctionalArmAES` |
| **Install model** | **ONE chassis-wide install per Mek**, no arm/leg requirement | ⚠️ CHANGED from canon per-limb — a consequence of the whole-unit effect |
| Weight | mek tonnage × **0.05** (round half-ton); 50t → 2.5t | proposal |
| Crit | weight-class total: Light 1 / Med 2 / Heavy 3 / Assault 4, placed freely | much cheaper than canon (canon was per-limb) |
| Cost / BV | mek tonnage × **4,000** C-bill / BV **TBD** (modest) | tunable |
| Tech | OUTER_SPHERE / E / 2900·2930·2960 / STANDARD / avail F·E·D·D | Improve tier |

**Effects:**
- **(A) To-hit:** **−1 to the attacker's movement-based to-hit modifier when the Mek RAN or JUMPED** (offsets the
  high-speed firing penalty; deliberately small). Walking unaffected. Hook: `ComputeAttackerToHitMods` (add near the
  canon-AES block), gated on `F_OS_AES` + movement type RUN/JUMP.
- **(B) MP offset:** **negates the MP penalty from bulky armour/equipment** — **Hardened Armor** (−1 walk/run) and
  **Modular Armor** (−1 jump). Hooks: suppress `Mek.hardenedArmorMPReduction()` and the modular-armor jump penalty
  when `F_OS_AES` is present. *(Optional future extension: Medium Shield −1 jump — NOT committed.)*
- **Rationale:** enhanced actuators = "a heavy/fast Mek still runs and shoots well." Only offsets **physical-load**
  penalties from equipment — deliberately NOT heat, combat damage (leg crits), or environmental penalties.

**Exclusions: NONE** — compatible with Targeting Computer, BCS (B-series), CCS (C-series), MASC, TSM. Rationale: as a
mobility/handling system (not fire control), the canon "AES⊥TC / AES⊥MASC" aiming-conflict rationale no longer
applies; decision 3 also requires no BCS/CCS conflict. *(MASC compatibility = confirm.)*

**Balance note:** AES-I −1 (run/jump) + a Targeting Computer −1 (always) = −2 to-hit when running — conditional, accepted.

**Coding TODO (when we build it):** new `F_OS_AES` flag in `MiscTypeFlag`; `createOSImprovedAES()` in `MiscType`;
`EquipmentTypeLookup.OS_IMPROVED_AES`; to-hit hook in `ComputeAttackerToHitMods`; MP-offset hooks in the Mek MP path;
`createOSImprovedArmoredMotiveSystem()` for AMS-I (pure stat item, reuses the existing `F_ARMORED_MOTIVE_SYSTEM`
mechanic — no engine work).
