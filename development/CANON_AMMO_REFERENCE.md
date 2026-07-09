# Canon Kinetic & Missile Ammo Reference

_用途 / Purpose:_ 一份正史（canon）动能武器与导弹武器的「武器 → 可用弹种/弹药变体」速查表，供接下来的 OS 弹药与武器开发参考。数据取自 `megamek/common/equipment/AmmoType.java`（枚举声明 + `createMunitions()` 变体映射，见 2026-07-08 核对）。IS 视角为主（OS 由 IS 演化），Clan 变体另注。

## How ammo compatibility works

- 一件武器由 `ammoType`（`AmmoType.AmmoTypeEnum`）+ `rackSize` 决定它能装哪一族弹药。任何 `ammoType`+`rackSize` 相同且 tech-legal 的弹药都可装填。
- 一族弹药内可以有多个 **munition 变体**（同 bin、同枚举，只是弹头不同），由 `createMunitions(baseAmmoList, mutators)` 批量生成。**只有 AC/LAC、SRM、LRM 三族是「变体丰富」的**；其余多为「固定单弹」或少量模式。
- `TechBase.OUTER_SPHERE` 的弹药只对 OS/Mixed 单位可见，不进 IS/Clan 单位列表。
- OS 侧枚举取舍（复用 canon vs 专属 `*_OS`）见 `OS_TECHBASE_DEVELOPMENT_PLAN.md`。

---

## Part 1 — Kinetic (Ballistic) Weapons

| 武器族 Weapon family | ammoType 枚举 | rackSize | 可用弹种 / munition 变体 | 备注 |
|---|---|---|---|---|
| **Autocannon (AC)** | `AC` | 2/5/10/20 | Standard, **Armor-Piercing (AP)**, **Caseless**, **Flak**, **Flechette**, **Precision**, **Tracer** | 变体丰富 |
| **Light AC (LAC)** | `LAC` | 2/5 | 同 AC（AP / Caseless / Flak / Flechette / Precision / Tracer） | 与 AC 共享变体（同在 `acAmmos`） |
| **Ultra AC (UAC)** | `AC_ULTRA` | 2/5/10/20 | Standard only | double-tap；发数/热量硬编码在枚举上 |
| **LB-X AC** | `AC_LBX` | 2/5/10/20 | **Slug（实心）+ Cluster（霰弹）** | 双弹种 |
| **Rotary AC (RAC)** | `AC_ROTARY` | 2/5/10/20 | Standard only（Caseless 仅 PLAYTEST） | 至多 6 发/回合 |
| **Hyper-Velocity AC (HVAC)** | `HYPER_VELOCITY` | 2/5/10 | Standard only | |
| **Gauss Rifle** | `GAUSS` | 15 | Standard slug | 无变体 |
| **Light Gauss** | `GAUSS_LIGHT` | 8 | Standard | 无变体 |
| **Heavy Gauss** | `GAUSS_HEAVY` | 25 | Standard | 无变体 |
| **Improved (Heavy) Gauss** | `GAUSS_IMP` / `IGAUSS_HEAVY` | 15 / 25 | Standard | 无变体 |
| **Silver Bullet Gauss** | `SBGAUSS` | 15 | **Cluster（霰弹）** | LB-X 式霰弹 |
| **AP Gauss** | `APGAUSS` | 3 | Standard | 无变体 |
| **Magshot Gauss** | `MAGSHOT` | 2 | Standard | 无变体 |
| **Hyper-Assault Gauss (HAG)** | `HAG` | 20/30/40 | Standard（按距离滚 cluster） | 单弹种 |
| **Machine Gun** | `MG` | 2 | Standard（整吨 / 半吨） | 无变体 |
| **Light MG** | `MG_LIGHT` | 1 | Standard | 无变体 |
| **Heavy MG** | `MG_HEAVY` | 3 | Standard | 无变体 |
| **Artillery Cannon** (Long Tom / Sniper / Thumper Cannon) | `*_CANNON` | - | Standard, Cluster, FASCAM, Flechette, Illumination, Smoke（等火炮弹） | 走火炮弹体系 |

**AC/LAC munition 速查（IS）：** Standard｜Armor-Piercing｜Caseless｜Flak（防空）｜Flechette（反步兵）｜Precision（-命中修正减免）｜Tracer。Clan Improved/Proto AC 另有对应 Clan 版本。

---

## Part 2 — Missile Weapons

| 武器族 Weapon family | ammoType 枚举 | rackSize | 可用弹种 / munition 变体 | 备注 |
|---|---|---|---|---|
| **LRM** | `LRM` | 5/10/15/20 | 变体丰富，见下表 | 变体丰富 |
| **Enhanced LRM (NLRM)** | `NLRM` | 10/15/20 | 共享 LRM 变体集 | |
| **SRM** | `SRM` | 2/4/6 | 变体丰富，见下表 | 变体丰富 |
| **MML** | `MML` | 3/5/7/9 | LRM 模式用 LRM 变体 + SRM 模式用 SRM 变体 | 双模 |
| **MRM** | `MRM` | 10/20/30/40 | Standard only | |
| **Streak SRM** | `SRM_STREAK` | 2/4/6 | Standard only | 全中/全失 |
| **Streak LRM** | `LRM_STREAK` | 5/10/15/20 | Standard only | |
| **Extended LRM (ELRM)** | `EXLRM` | 10/15/20 | Standard only | 超远程 |
| **ATM** | `ATM` | 3/6/9/12 | **Standard / Extended-Range (ER) / High-Explosive (HE)** | Clan；3 模 |
| **iATM** | `IATM` | 3/6/9/12 | 可用 ATM 三弹 + Improved (IMP) / IIW | Clan |
| **Thunderbolt** | `TBOLT_5/10/15/20` | (单弹) | Standard（单弹头） | 无变体 |
| **Narc** | `NARC` | (荚舱) | Homing pod, Explosive pod | |
| **iNarc** | `INARC` | (荚舱) | Homing(Explosive), **ECM**, **Haywire**, **Nemesis** pods | |
| **Rocket Launcher (RL)** | `ROCKET_LAUNCHER` | 10/15/20 | Standard（一次性、不可再装） | |
| **LRT / SRT（鱼雷）** | `LRM_TORPEDO` / `SRM_TORPEDO` | LRT 5/10/15/20, SRT 2/4/6 | Standard + Torpedo/部分 LRM/SRM 变体（限水战） | |
| **Arrow IV（火炮导弹）** | `ARROW_IV` | (火炮) | Standard, Homing, Air-Defense (ADA), Cluster, FASCAM, Illumination, Smoke, Thunder(-Vibrabomb) | 火炮 |

### LRM munition 集（IS）
Standard｜Artemis-capable｜Narc-capable｜Semi-Guided｜Swarm｜Swarm-I｜Thunder｜Thunder-Active｜Thunder-Augmented｜Thunder-Vibrabomb｜Thunder-Inferno｜Fragmentation｜Incendiary｜Heat-Seeking｜Smoke｜Follow-The-Leader｜Anti-TSM｜Dead-Fire｜Listen-Kill｜Mine Clearance｜Anti-Radiation (ARAD)

### SRM munition 集（IS）
Standard｜Artemis-capable｜Narc-capable｜Inferno｜Tandem-Charge｜Fragmentation｜Acid｜Heat-Seeking｜Smoke｜Anti-TSM｜Dead-Fire｜Listen-Kill｜Mine Clearance｜Anti-Radiation (ARAD)

> Clan LRM/SRM 另有平行的 `(Clan) …` 变体集（含 Artemis-V），基本涵盖同类弹种。

---

## Part 3 — Notes for OS development

- **变体丰富族（AC/LAC、SRM、LRM、MML）**：造 OS 版时需决定是否复刻整棵 munition 树，还是只出 Standard bin。当前 OS 实装（`AC_STD_OS`、OS LRM/SRM）都只出 **Standard** bin。
- **固定单弹族（Gauss 全系、MG、Ultra/Rotary/HVAC、Streak、MRM、ELRM、Thunderbolt、RL）**：一个 bin、无 munition 树，克隆成本最低。
- **双模/多模族（LB-X = slug+cluster，ATM = Std/ER/HE，MML = LRM+SRM，Narc/iNarc = 多荚舱）**：需保留其模式结构。
- **枚举取舍**：动能 AC 系（单发）可用专属 `*_OS` 枚举；Ultra/Rotary（多发，热量硬编码）与全部导弹（Artemis/Narc/FCS 耦合）**必须复用** canon 枚举。详见 `OS_TECHBASE_DEVELOPMENT_PLAN.md`。
