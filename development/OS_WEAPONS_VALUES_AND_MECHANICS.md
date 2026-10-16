# Outer Sphere (OS) Weapons — Values & Mechanics

> 用途：把每一件 OS 武器的**具体数值**与**签名机制**并列成一份速查表，便于设计/调参/对局参考。
>
> **数值来源**（**2026-10-15 全量从源码重建**，总 **~240 件武器**）：
> - **能量武器**（lasers / PPC / plasma / flamers，共 **71 件**）：dmg / heat / range / t / c / BV / Intro / TR·SL / toHit 直读。
> - **弹道武器**（AC / LB-X / Ultra AC / Rotary / HVAC / Gauss / HAG / MG / Advance 动能，共 **72 件 OS + 8 件 IS BF/RF 变体 = 80 件**）：同样直读。
> - **导弹武器**（LRM / SRM / MRM / MML / ATM / THML / Narc，共 **88 件**）：直读；range 跟随武器子族（核心 LRM 7/10/24/30、Streak LRM 7/14/24/30、SRM 6/8/10 等）。
> - **导弹火控**（Artemis IV/V OS、Diana III/IV、Orion V）在 `MiscType.java` 不在武器文件里，表单沿用 2026-08-29 版，另外单独核对。
> - 机制文字取自 `megamek/resources/megamek/common/equipmentmessages.properties`（`EquipmentInfo.*`），对照武器类 / handler 代码核实。
>
> **BV / cost 全量表** `OS_WEAPONS_REFERENCE.md`：那份 2026-06-12 生成，命名仍用旧的 "Advance"≡现 "Enhanced"，且缺 HVAC / Enhanced Ultra AC / Electromagnetic Lance / Coil Augmented Railgun / Enhanced ER PPC — 需重新生成。
>
> **列含义**：
> - `tier`：**设计阶梯归属**（STD / IMP / ENH / ADV / EXP）。⚠ = 当前代码 intro-date 或 StaticTechLevel 与设计意图不符（表后统一列出）。
> - `Intro`：`setISAdvancement(proto, prod, common)`；extinct / reintro 全为 DATE_NONE，省略。
> - `TR·SL`：`TechRating · SimpleTechLevel`。E / F / G = TechRating；STD / ADV / EXP = STANDARD / ADVANCED / EXPERIMENTAL。
> - `dmg` 单发伤害（`var` = 随射程 / 弹种变化，脚注给具体数）；`heat` 开火热量；`S/M/L/E` 短/中/远/极限射程（格）；`(min N)` 最小射程；`t/c` 吨/临界；`BV` 对战点值。
>
> **五级阶梯**（当前代码 intro 实际排布）：Standard **2805·20·40** · Improve **2900·30·60** · Enhanced **3000·25·50** · Advance **3060·70·80** · Experimental **3045·50·55**。

---

## 能量 — 异种集束激光系（Binary / Trinary / Hyper，共 5 件）

集束型激光把多个镜筒并成一次打击；**Resonance Tuning** 只对基础标准装甲加成，遇 Ferro-Fibrous 及一切先进/特种装甲失效。

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| ADV⚠³ | Binary Laser System | 3060/3070/3080 | F·ADV | 16 | 16 | 5/10/15/20 | 7/4 | 246 | 双筒并一击，无命中修正；Resonance ×1.30 → 21 vs 标装甲 |
| ADV⚠³ | ER Binary Laser System | 3060/3070/3080 | F·ADV | 16 | 20 | 8/15/22/30 | 7/4 | 326 | −1 命中；×1.30 → 21；换热换射程 |
| ADV⚠³ | Trinary Laser System | 3060/3070/3080 | F·ADV | 24 | 24 | 5/10/15/20 | 11/6 | 369 | 三筒并一击；×1.50 → 36；近距 alpha、热账重 |
| ADV⚠³ | ER Trinary Laser System | 3060/3070/3080 | F·ADV | 24 | 32 | 8/15/22/30 | 11/6 | 489 | −1；×1.50 → 36；**每回合可射**（异于充能锁的 Hyper）|
| EXP | Large Hyper Laser | 3045/3050/3055 | G·EXP | 25 | 25 | 12/24/36/50 | 8/6 | 620 | 极距；−1、航空满 AV；**充能一回合 → 隔回合发射**；爆炸物（暴击殉爆 12）|

**⚠³** `Binary / Trinary / ER Binary / ER Trinary` 全部挂在 Advance 带（3060），但 Hyper（同族顶点）在 Experimental（3045），中间断档。tier 从没正式拍定过。两条候选路线：（a）向下拉：Binary=IMP, Trinary=ENH, ER-Binary=ENH, ER-Trinary=ADV → 建立正常阶梯；（b）向上统一：全体 EXP（跟 Hyper 对齐，承认这是"不走量产的实验线"）。

> **数值订正 vs 旧版文档**：Hyper Laser 旧版标 heat 30，代码实际 **heat 25**（dmg 25、heat 25、BV 620）。

## 能量 — 激光主阶梯（42 件：Standard / Improve / Enhanced 变体 + 遗留 Advance）

每个尺寸 14 件：base / ER / Heavy ×（STD + IMP）+ Improve Pulse / ER Pulse / Heavy Pulse + Enhanced ER + Enhanced ER Pulse + 挂在 Advance 日期的"遗留"件（ER Heavy / ER Pulse / Heavy Pulse，⚠²）。

> **机制速记**：Heavy 自带 **+1 命中惩罚**（Improve Heavy 工程消除；ER Heavy 保留）；Pulse 自带 **−2 命中**；Enhanced ER 内建 **−1 命中**；Enhanced ER Pulse 推到 **−3 命中**。
>
> **IS 复刻** = base Laser / ER Laser 的数值与 IS 同名武器完全一致，差别仅 TechBase = OUTER_SPHERE。

### 大型激光（14 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| STD | Large Laser | 2805/2820/2840 | E·STD | 8 | 8 | 5/10/15/20 | 5/2 | 123 | IS 复刻 |
| STD | ER Large Laser | 2805/2820/2840 | E·STD | 9 | 11 | 7/14/19/28 | 5/3 | 170 | 比 IS ER 大激光 +1 伤、更远 |
| STD | Heavy Large Laser | 2805/2820/2840 | E·STD | 16 | 18 | 5/10/15/20 | 4/3 | 244 | 大型最佳吨伤；**+1 命中惩罚**，近战型 |
| IMP | Improve Large Laser | 2900/2930/2960 | E·STD | 9 | 8 | 5/10/15/20 | 4/1 | 135 | +1 伤同热，**−1 吨 −1 crit** |
| IMP | Improve ER Large Laser | 2900/2930/2960 | E·STD | 10 | 12 | 8/15/22/30 | 4/1 | 230 | 推射程至 30；4t / 1crit 奇瘦 |
| IMP | Improve Heavy Large Laser | 2900/2930/2960 | E·STD | 16 | 18 | 5/10/15/20 | 4/3 | 298 | 工程消 +1 惩罚，满伤正常精度 |
| IMP | Improve Large Pulse Laser | 2900/2930/2960 | E·STD | 9 | 10 | 4/8/12/16 | 6/2 | 130 | 全 −2 脉冲 |
| IMP | Improve ER Large Pulse Laser | 2900/2930/2960 | E·STD | 11 | 14 | 6/14/22/30 | 6.5/4 | 240 | 保全 −2 脉冲 + ER 距离 |
| IMP⚠¹ | Improve Heavy Large Pulse Laser | 2900/2930/2960 | **F·ADV** | 13 | 14 | 5/10/15/20 | 6.5/3 | 240 | −2 命中射程拉回；SL 标 ADV（其他 Improve 系均 STD）|
| ENH | Enhanced ER Large Laser | 3000/3025/3050 | F·ADV | 11 | 14 | 8/15/22/30 | 4.5/3 | 260 | 内 −1 命中；1 伤换精度 + 满远程 AV |
| ENH | Enhanced ER Large Pulse Laser | 3000/3025/3050 | **G**·ADV | 14 | 18 | 6/14/22/30 | 6/4 | 350 | **−3 命中**；线内最准大脉冲（TR 独 G）|
| ADV⚠² | ER Heavy Large Laser | 3060/3070/3080 | F·ADV | 16 | 22 | 7/14/19/28 | 5/3 | 245 | Heavy 推 ER；保 +1 惩罚；**设计 ≈ IMP** |
| ADV⚠² | ER Large Pulse Laser | 3060/3070/3080 | F·ADV | 10 | 14 | 6/12/17/24 | 6.5/3 | 220 | −1；1 点精度换 1 档距；**设计 ≈ IMP** |
| ADV⚠² | Heavy Large Pulse Laser | 3060/3070/3080 | F·ADV | 12 | 14 | 3/6/9/12 | 6/2 | 180 | Heavy + Pulse −2，近战刀；**设计 ≈ IMP** |

### 中型激光（14 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| STD | Medium Laser | 2805/2820/2840 | E·STD | 5 | 3 | 3/6/9/12 | 1/1 | 46 | IS 复刻 |
| STD | ER Medium Laser | 2805/2820/2840 | E·STD | 6 | 5 | 4/8/12/16 | 1/1 | 70 | 比 IS ER 中激光 +1 伤 |
| STD | Heavy Medium Laser | 2805/2820/2840 | E·STD | 10 | 7 | 3/6/9/12 | 1/2 | 76 | 最佳中型吨伤；**+1 惩罚** |
| IMP | Improve Medium Laser | 2900/2930/2960 | E·STD | 6 | 3 | 3/6/9/12 | 1/1 | 50 | +1 伤同吨 |
| IMP | Improve ER Medium Laser | 2900/2930/2960 | E·STD | 6 | 5 | 5/10/15/20 | 1/1 | 88 | 推射程 |
| IMP | Improve Heavy Medium Laser | 2900/2930/2960 | E·STD | 10 | 7 | 3/6/9/12 | 1/2 | 103 | 消 +1 惩罚 |
| IMP | Improve Medium Pulse Laser | 2900/2930/2960 | E·STD | 6 | 4 | 3/5/7/10 | 2/1 | 53 | 全 −2 脉冲 |
| IMP | Improve ER Medium Pulse Laser | 2900/2930/2960 | E·STD | 8 | 8 | 4/8/12/16 | 2/2 | 158 | 全 −2 脉冲 + ER |
| IMP⚠¹ | Improve Heavy Medium Pulse Laser | 2900/2930/2960 | **F·ADV** | 8 | 5 | 3/6/9/12 | 1.5/2 | 100 | −2 脉冲射程拉回；SL 标 ADV |
| ENH | Enhanced ER Medium Laser | 3000/3025/3050 | F·ADV | 8 | 6 | 5/10/15/20 | 1/1 | 145 | 内 −1；线内最硬中激光 |
| ENH | Enhanced ER Medium Pulse Laser | 3000/3025/3050 | **G**·ADV | 10 | 12 | 4/8/12/16 | 2/2 | 240 | **−3 命中**；线内最准中激光 |
| ADV⚠² | ER Heavy Medium Laser | 3060/3070/3080 | F·ADV | 10 | 12 | 4/8/12/16 | 2/2 | 105 | Heavy 推 ER 保 +1；**设计 ≈ IMP** |
| ADV⚠² | ER Medium Pulse Laser | 3060/3070/3080 | F·ADV | 7 | 6 | 4/7/11/14 | 2/2 | 130 | −1，1 点精度换距；**设计 ≈ IMP** |
| ADV⚠² | Heavy Medium Pulse Laser | 3060/3070/3080 | F·ADV | 7 | 5 | 2/4/5/8 | 1.5/2 | 75 | −2 脉冲极短距，点射件；**设计 ≈ IMP** |

### 小型激光（14 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| STD | Small Laser | 2805/2820/2840 | E·STD | 3 | 1 | 1/2/3/4 | 0.5/1 | 9 | IS 复刻；**独有 `F_NO_FIRES` 旗**（其他尺寸无，待核）|
| STD | ER Small Laser | 2805/2820/2840 | E·STD | 4 | 2 | 2/4/5/8 | 0.5/1 | 22 | 比 IS ER 小激光更伤更远 |
| STD | Heavy Small Laser | 2805/2820/2840 | E·STD | 6 | 3 | 1/2/3/4 | 0.5/1 | 15 | 最佳小型吨伤；**+1 惩罚** |
| IMP | Improve Small Laser | 2900/2930/2960 | E·STD | 4 | 1 | 1/2/3/4 | 0.5/1 | 10 | +1 伤 |
| IMP | Improve ER Small Laser | 2900/2930/2960 | E·STD | 5 | 2 | 2/4/6/8 | 0.5/1 | 28 | +1 伤 |
| IMP | Improve Heavy Small Laser | 2900/2930/2960 | E·STD | 6 | 3 | 1/2/3/4 | 0.5/1 | 19 | 消 +1 惩罚 |
| IMP | Improve Small Pulse Laser | 2900/2930/2960 | E·STD | 3 | 2 | 2/3/5/7 | 1/1 | 17 | 全 −2 脉冲 |
| IMP | Improve ER Small Pulse Laser | 2900/2930/2960 | E·STD | 4 | 3 | 3/5/7/10 | 1/1 | 62 | 全 −2 脉冲 + ER |
| IMP⚠¹ | Improve Heavy Small Pulse Laser | 2900/2930/2960 | **F·ADV** | 5 | 3 | 1/2/3/4 | 0.5/1 | 30 | −2 脉冲点射；SL 标 ADV |
| ENH | Enhanced ER Small Laser | 3000/3025/3050 | F·ADV | 5 | 3 | 3/5/8/10 | 0.5/1 | 50 | 内 −1；线内最远最准小激光 |
| ENH | Enhanced ER Small Pulse Laser | 3000/3025/3050 | **G**·ADV | 5 | 4 | 3/5/7/10 | 1/1 | 90 | **−3 命中** |
| ADV⚠² | ER Heavy Small Laser | 3060/3070/3080 | F·ADV | 6 | 4 | 2/4/5/8 | 1.5/1 | 26 | Heavy 推 ER 保 +1；**设计 ≈ IMP** |
| ADV⚠² | ER Small Pulse Laser | 3060/3070/3080 | F·ADV | 3 | 3 | 2/4/6/8 | 1/1 | 38 | −1，1 点精度换距；**设计 ≈ IMP** |
| ADV⚠² | Heavy Small Pulse Laser | 3060/3070/3080 | F·ADV | 4 | 3 | 1/2/3/4 | 0.5/1 | 22 | −2 脉冲点射；**设计 ≈ IMP** |

**激光主阶梯小计 42 件**：STD 9 · IMP 15 · IMP⚠ 3 · ENH 6 · ADV⚠ 9。设计意图下**应**全部落在 STD / IMP / ENH 三级；现有 12 件 ⚠ 需要在后续 intro-date / SL 重设时迁回正确档位。

**⚠¹** `Improve Heavy Pulse (S/M/L)`：intro 日期符合 Improve（2900–2960），但 `SimpleTechLevel` 被标成 **ADVANCED**（其他 10 件 Improve-系均 STANDARD）→ 内部不一致，SL 应降为 STANDARD。

**⚠²** `ER Heavy (S/M/L) · ER Pulse 无前缀 (S/M/L) · Heavy Pulse 无前缀 (S/M/L)`：intro 挂在 Advance 带（3060–3080），但设计概念全部是"两件 Standard-tier 工程的组合"（Heavy+ER / Pulse+ER / Heavy+Pulse），应当是 Improve-tier → 建议 intro 迁至 2900–2960、SL 降为 STANDARD、TR 降至 E。

**数值订正 vs 旧版文档**：旧版把 Enhanced ER Large Laser 的 crit 标 4，代码实际 **3**；OSImproveERLaserLarge 的 S/M/L/E 旧版标 7/14/19/28、代码实际 **8/15/22/30**，吨 4t（非 4.5t）、crit 1（非 3）。旧版若干脉冲行缺 ext 列。

## 能量 — PPC（15 件）

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E (min) | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| STD | PPC | 2805/2820/2840 | E·STD | 10 | 10 | (min3) 6/12/18/24 | 7/3 | 176 | IS 复刻 |
| STD | ER PPC | 2805/2820/2840 | E·STD | 10 | 15 | 7/14/23/34 | 7/3 | 229 | 比 IS ER PPC 多一档射程（34 ext）|
| STD | Light PPC | 2805/2820/2840 | E·STD | 7 | 7 | (min3) 6/12/18/24 | 3/2 | 125 | 比正史 Light PPC 更伤（canon=5）|
| STD | Heavy PPC | 2805/2820/2840 | E·STD | 18 | 18 | (min3) 6/12/18/24 | 8/4 | 380 | 比标准 PPC 更重击 |
| STD | Snub-Nose PPC | 2805/2820/2840 | E·STD | var⁴ | 10 | 9/13/15/22 | 6/2 | 180 | 伤随距降；无 min；爆炸物 |
| IMP | Improve PPC | 2900/2930/2960 | E·STD | 10 | 10 | (min3) 6/12/18/24 | 6/2 | 195 | **−1 吨 −1 crit** vs base |
| IMP | Improve ER PPC | 2900/2930/2960 | E·STD | 12 | 14 | 7/14/23/30 | 7/3 | 290 | +2 伤 −1 热 vs base ER |
| ENH | Enhanced ER PPC | 3000/3025/3050 | F·ADV | 15 | 15 | 7/14/23/30 | 6/2 | 412 | 无 min；比 Improve ER +3 伤 +1 热 −1 吨 −1 crit |
| ADV | ER Heavy PPC | 3060/3070/3080 | F·ADV | 18 | 22 | 7/14/20/27 | 9/4 | 420 | 无 min；重击 + ER |
| ADV | Heavy Snub-Nose PPC | 3060/3070/3080 | F·ADV | var⁵ | 15 | 9/12/14/21 | 8/4 | 265 | 重型近距 PPC；无 min；爆炸物 |
| ADV | Rotary Light PPC | 3060/3070/3080 | F·ADV | 5×(1–5) | 2/发 | 6/12/18/24 | 7/5 | 280 | 前 3 发自冷；4–5 发抽 **RPPC 冷却舱**，无舱 4+ 过载 |
| ADV | Rotary Snub-Nose PPC | 3060/3070/3080 | F·ADV | var⁶ ×(1–4) | 3/发 | 8/12/15/22 | 10/6 | 360 | 无 min；前 3 发自冷；第 4 发抽冷却舱 |
| ADV | PPC-X | 3060/3070/3080 | F·ADV | 2×6 束（≤12）| 10 | 7/10/13/18 | 6/3 | 205 | 分 6 束；命中梯度 **−1 短 / 0 中 / +1 远**；无 min；爆炸物 |
| ADV | Heavy PPC-X | 3060/3070/3080 | F·ADV | 4×6 束（≤24）| 15 | 7/10/13/18 | 8/4 | 350 | 同梯度；爆炸物 |
| EXP | Hyper PPC | 3045/3050/3055 | G·EXP | 30 | 30 | (min3) 12/24/36/50 | 10/7 | 824 | 内 −1；**充能一回合 → 隔回合发射** |

**⁴ Snub-Nose PPC**：10 短 / 8 中 / 6 长。
**⁵ Heavy Snub-Nose PPC**：15 短 / 9 中 / 6 长。
**⁶ Rotary Snub-Nose PPC**：8 短 / 6 中 / 4 长（每发；1–4 发模式）。

> PPC 线无 Enhanced-除 ER 一支外的"全阶梯"（无 Enhanced Snub / Enhanced Heavy / Enhanced Light），且无 Pulse PPC。可能是设计留白。

## 能量 — 等离子（5 件，全部弹药供弹）

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|---|--:|:--:|:--:|--:|---|
| STD | Plasma Rifle | 2805/2820/2840 | E·STD | 10 伤 + 2d6 目标热 | 8 | 6/12/18/24 | 5/2 | 250 | 聚焦弹；比 IS 等离子步枪更冷/轻/远 |
| STD | Plasma Cannon | 2805/2820/2840 | E·STD | 0 伤 + 2d6 目标热 | 6 | 6/12/18/24 | 4/2 | 190 | 面压制；对机甲不破甲只灌热；对软 3d6 集束 |
| EXP | Heavy Plasma Rifle | 3045/3050/3055 | G·EXP | 15 伤 + 2d6 目标热 | 12 | 5/10/15/20 | 7/3 | 315 | 破 10 点等离子上限；**+1 命中惩罚**，近距破甲 |
| EXP | Heavy Plasma Cannon | 3045/3050/3055 | G·EXP | 0 伤 + 3d6 目标热 | 10 | 6/12/18/24 | 6/3 | 290 | 更密面灌热 + 3d6 反软集束；**+1 惩罚** |
| EXP | EMP Plasma Accelerator | 3045/3050/3055 | G·EXP | 0 | 8 | 5/10/15/20 | 5/3 | 190 | 专职电子扰乱：只造 INTERFERENCE（永不完全停机）；开火方无自扰 |

> **等离子线断档**：base 两件在 STD、Heavy 两件直接跳到 EXP（跳过 IMP 与 ENH 两级）。合理与否取决于设定 — 若 Heavy 等离子被视作"反应堆直流接驳的实验突破"则 EXP 合理；若视作工程演进则应落在 ENH。

## 能量 — 火焰器（4 件，能量供弹无弹药；Damage 烧甲 / Heat 灌热；Xd6 反步兵）

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| STD | ER Flamer | 2805/2820/2840 | E·STD | 2 | 3 | 3/6/9/12 | 1/1 | 18 | 远射型，远超 IS ER 火焰器（3/5/7）；2d6 反步兵 |
| STD | Heavy Flamer | 2805/2820/2840 | E·STD | 5 | 6 | 2/4/6/8 | 1.5/1 | 30 | 伤害型，全能量供弹（无燃料弹）；6d6 反步兵 |
| STD | ER Heavy Flamer | 2805/2820/2840 | E·STD | 5 | 7 | 3/6/9/12 | 2/1 | 45 | 最重 OS 火焰器；远 + 重伤；6d6 反步兵 |
| IMP | Improve Flamer | 2900/2930/2960 | E·STD | 2 | 2 | 1/2/3/4 | 0.5/1 | 6 | 效率型：半吨、点火热减半（标准 3）；4d6 反步兵 |

> **火焰器线**：所有强化变体（ER / Heavy / ER Heavy）都在 **STD 2805** 开局；只有 Improve Flamer 在 2900。意味着在 OS 时间线早期已经有 2d6/6d6 的大于 IS 火焰器选项 — tier 分布合理，但需核：ER Heavy Flamer dmg 5 / heat 7 在 2805 是不是太强。

---

## 弹道 — 自动炮家族（AC / LB-X / Ultra AC / Rotary AC / HVAC，43 件 + IS BF/RF 8 件）

### Standard AC + Improve AC（8 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| STD | AC/2 | 2805/2820/2840 | E·STD | 2 | 1 | 8/16/24/32 | 6/1 | 37 | IS 复刻；专用弹 `AC_STD_OS` |
| STD | AC/5 | 2805/2820/2840 | E·STD | 5 | 1 | 6/12/18/24 | 8/4 | 70 | IS 复刻 |
| STD | AC/10 | 2805/2820/2840 | E·STD | 10 | 3 | 5/10/15/20 | 12/6 | 123 | IS 复刻 |
| STD | AC/20 | 2805/2820/2840 | E·STD | 20 | 7 | 3/6/9/12 | 14/9 | 178 | IS 复刻 |
| IMP | Improve AC/2 | 2900/2930/2960 | E·STD | **3** | 1 | 8/16/24/32 | 5/1 | 56 | **+1 伤**，专用弹 `AC_IMP_OS` |
| IMP | Improve AC/5 | 2900/2930/2960 | E·STD | **6** | 1 | 6/12/18/24 | 7/3 | 84 | +1 伤 |
| IMP | Improve AC/10 | 2900/2930/2960 | E·STD | 10 | 2 | 5/10/15/20 | 10/6 | 123 | 同伤但 −1 热 −2 吨 |
| IMP | Improve AC/20 | 2900/2930/2960 | E·STD | 20 | 6 | 4/8/12/16 | 12/9 | 237 | 推 1 档距、−1 热 −2 吨 |

### LB-X（Standard / Improve / Ultra，双模实心弹/集束霰弹，11 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| STD | LB 2-X | 2805/2820/2840 | E·STD | 2 | 1 | 9/18/27/36 | 6/4 | 42 | IS 复刻 LB-X |
| STD | LB 5-X | 2805/2820/2840 | E·STD | 5 | 1 | 7/14/21/28 | 8/5 | 83 | 同上 |
| STD | LB 10-X | 2805/2820/2840 | E·STD | 10 | 2 | 6/12/18/24 | 11/5 | 148 | 同上 |
| STD | LB 20-X | 2805/2820/2840 | E·STD | 20 | 5 | 4/8/12/16 | 14/11 | 237 | 同上 |
| IMP | Improve LB 2-X | 2900/2930/2960 | E·STD | 2 | 1 | 9/18/27/36 | 4.5/2 | 42 | 更轻（4.5t vs 6t），4 crit 降至 2 |
| IMP | Improve LB 5-X | 2900/2930/2960 | E·STD | 5 | 1 | 7/14/21/28 | 6.5/4 | 83 | 更轻 |
| IMP | Improve LB 10-X | 2900/2930/2960 | E·STD | 10 | 2 | 6/12/18/24 | 10/5 | 148 | 更轻 |
| IMP | Improve LB 20-X | 2900/2930/2960 | E·STD | 20 | 5 | **5**/10/15/20 | 12/8 | 296 | 更轻 + 推 1 档短距 |
| ⚠EXP¹ | Ultra LB 5-X | 3045/3050/3055 | **G·EXP** | 5 | 1 | 8/16/24/32 | 6/4 | 150 | 双模 + Ultra 双发 + **OS LB-X 特种弹**（GAAM 制导 AP、Anti-Myomer 霰弹）|
| ⚠EXP¹ | Ultra LB 10-X | 3045/3050/3055 | **G·EXP** | 10 | 2 | 7/14/21/28 | 9/5 | 275 | 同上 |
| ⚠EXP¹ | Ultra LB 20-X | 3045/3050/3055 | **G·EXP** | 20 | 5 | 5/10/15/20 | 12/8 | 475 | 同上 |

### Ultra AC（Standard / Improve / Enhanced，OS 版**不卡壳**，12 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| STD | Ultra AC/2 | 2805/2820/2840 | E·STD | 2 | 1 | 8/17/25/33 | 7/3 | 56 | 单/Ultra 双发，无卡壳 |
| STD | Ultra AC/5 | 2805/2820/2840 | E·STD | 5 | 1 | 6/13/20/26 | 9/5 | 112 | 同上 |
| STD | Ultra AC/10 | 2805/2820/2840 | E·STD | 10 | 4 | 6/13/18/24 | 13/7 | 210 | 同上 |
| STD | Ultra AC/20 | 2805/2820/2840 | E·STD | 20 | 8 | 3/7/10/13 | 15/9 | 281 | 同上 |
| IMP | Improve Ultra AC/2 | 2900/2930/2960 | E·STD | 2 | 1 | 9/18/25/34 | 6/2 | 56 | 更轻更冷 |
| IMP | Improve Ultra AC/5 | 2900/2930/2960 | E·STD | 5 | 1 | 7/14/21/28 | 8/4 | 118 | 同上 |
| IMP | Improve Ultra AC/10 | 2900/2930/2960 | E·STD | 10 | 3 | 6/12/18/24 | 11/6 | 210 | 同上 |
| IMP | Improve Ultra AC/20 | 2900/2930/2960 | E·STD | 20 | 6 | 4/8/12/16 | 12/8 | 337 | 推射程 + 更轻更冷 |
| ENH | Enhanced Ultra AC/2 | 3000/3025/3050 | F·ADV | 2 | 1 | 9/18/25/34 | 6/2 | 70 | 内建 **−1 命中** + Ultra 无卡壳 |
| ENH | Enhanced Ultra AC/5 | 3000/3025/3050 | F·ADV | 5 | 1 | 8/16/24/32 | 7/4 | 150 | 内 −1，更长 |
| ENH | Enhanced Ultra AC/10 | 3000/3025/3050 | F·ADV | 10 | 3 | 7/14/20/27 | 10/4 | 270 | 内 −1；精准/长/瘦 |
| ENH | Enhanced Ultra AC/20 | 3000/3025/3050 | F·ADV | 20 | 6 | 5/9/13/17 | 12/8 | 400 | 内 −1 |

### Rotary AC（1–N 连发爆发，低骰卡壳，4 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| ⚠STD² | Rotary AC/2 | 2805/2820/2840 | F·STD | 2×(1–6) | 1/发 | 7/14/21/28 | 7/3 | 185 | 持续火力堆集束；低骰卡壳；**TR F 却 SL STD、intro 2805 — 怪**|
| ⚠STD² | Rotary AC/5 | 2805/2820/2840 | F·STD | 5×(1–6) | 1/发 | 6/12/18/24 | 10/6 | 295 | 同上 |
| ADV | Rotary AC/10 | 3060/3070/3080 | F·ADV | 10×(1–5) | 3/发 | 5/10/15/20 | 14/8 | 410 | 同上；heat 封顶 15 |
| ADV | Rotary AC/20 | 3060/3070/3080 | F·ADV | 20×(1–4) | 7/发 | 4/9/13/17 | 16/12 | 620 | 最重持续火力；heat 封顶 28 |

### HVAC（长身管精密反装甲炮；**命中随距递进** 0/−1/−1/−2；航空满 AV；非殉爆；8 件）
专用弹 `HVAC_OS`（idx 139），非 `AC_STD_OS`（独立 enum）。

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| IMP | Improve HVAC/10 | 2900/2930/2960 | E·STD | 10 | 3 | 8/16/24/32 | 10/6 | 165 | 距离越远越准 |
| IMP | Improve HVAC/12 | 2900/2930/2960 | E·STD | 12 | 4 | 7/14/21/28 | 11/7 | 195 | 同 |
| IMP | Improve HVAC/14 | 2900/2930/2960 | E·STD | 14 | 5 | 7/13/20/27 | 12/8 | 225 | 同 |
| IMP | Improve HVAC/16 | 2900/2930/2960 | E·STD | 16 | 6 | 6/12/19/26 | 13/8 | 255 | 最重精密 HVAC |
| ENH | Enhanced HVAC/10 | 3000/3025/3050 | F·ADV | 10 | 2 | 8/16/24/32 | 8/4 | 165 | 同精度，更轻更冷 |
| ENH | Enhanced HVAC/12 | 3000/3025/3050 | F·ADV | 12 | 3 | 7/14/21/28 | 9/5 | 195 | 同 |
| ENH | Enhanced HVAC/14 | 3000/3025/3050 | F·ADV | 14 | 4 | 7/13/20/27 | 10/6 | 225 | 同 |
| ENH | Enhanced HVAC/16 | 3000/3025/3050 | F·ADV | 16 | 5 | 6/12/19/26 | 11/6 | 255 | Enhanced 减重减热 |

### IS-fork AC 变体 — BF / RF（8 件，`autoCannons/innerSphere/`，IS tech base 不是 OS）
BF = Burst Fire（**长档 −1 命中**，软化 long bracket 的 −4 → −3，专用弹 `AC_BF`）；RF = Rapid Fire（**3 发爆发，cluster size 3，期望 2.0 命中**，**无 +1 卡壳**，专用弹 `AC_RF`）。有效伤 = canon AC 对应口径；BV / 吨 / crit 跟 canon AC 完全对齐。

| 类型 | 武器 | Intro | TR | dmg | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| BF | AC/2 BF | 2290/2300/2305 | C | 2 | 1 | 8/16/24/32 | 6/1 | 37 | long −1 命中 |
| BF | AC/5 BF | 2240/2250/2255 | C | 5 | 1 | 6/12/18/24 | 8/4 | 70 | 同上 |
| BF | AC/10 BF | 2443/2460/2465 | C | 10 | 3 | 5/10/15/20 | 12/7 | 123 | 同上 |
| BF | AC/20 BF | 2488/2500/2502 | C | 20 | 7 | 3/6/9/12 | 14/10 | 178 | 同上 |
| RF | AC/2 RF | 2290/2300/2305 | C | 1 ×3 | 0 | 8/16/24/32 | 6/1 | 37 | 3 发爆发（cluster-3，期望 2 中，有效伤 ≈ 2） |
| RF | AC/5 RF | 2240/2250/2255 | C | 3 ×3 | 0 | 6/12/18/24 | 8/4 | 70 | 同上 |
| RF | AC/10 RF | 2443/2460/2465 | C | 5 ×3 | 1 | 5/10/15/20 | 12/7 | 123 | 同上 |
| RF | AC/20 RF | 2488/2500/2502 | C | 10 ×3 | 2 | 3/6/9/12 | 14/10 | 178 | 同上 |

**AC 段小计 43 OS + 8 IS 变体 = 51 件**：STD 15 · IMP 20 · ENH 4 · ADV 2 · EXP⚠ 3 · ⚠STD 2 + IS BF 4 · IS RF 4。

**⚠¹ Ultra LB-X 系** 3 件：intro 挂 3045 EXPERIMENTAL，TR G，等同 Hyper 级。概念"LB-X + Ultra 双发 + OS 特种弹"虽然叠两层机制，整体仍是产品化 Advance-tier 可量产的件；标 EXP + G 过头。**建议：迁 3060/3070/3080 ADV + TR F**。

**⚠² Rotary AC/2 和 /5** 2 件：intro 挂 2805（Standard 带），但 TR 却是 **F**（Standard 其他件都是 E），SL=STANDARD 跟 TR F 不匹配。概念"Rotary 机制"在 canon BT 是 Advance 级。**建议：intro 迁 3060 ADV + 把 Rotary AC/2/5/10/20 四件统一在 Advance 带**，或下调 TR 到 E 让 2805 自洽。

## 弹道 — Gauss 家族（14 件）

### Gauss 核心阶梯（Light / Standard / Heavy / Super-Heavy，9 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E (min) | t/c | BV | 机制 |
|---|---|:--:|:--:|---|--:|:--:|:--:|--:|---|
| ADV | Ultra-Light Gauss Rifle | 3060/3070/3080 | E·ADV | 6 | 1 | (min3) 7/15/22/33 | 7/3 | 120 | 最轻 Gauss |
| STD | Light Gauss Rifle | 2805/2820/2840 | E·STD | 8 | 1 | (min3) 8/17/25/37 | 10/4 | 165 | 轻伤长射，瘦机用 |
| STD | Gauss Rifle | 2805/2820/2840 | E·STD | 15 | 1 | (min2) 7/15/22/33 | 12/6 | 320 | Clan 重量，比 IS Gauss 轻 3 吨 |
| IMP | Improve Gauss Rifle | 2900/2930/2960 | E·STD | 15 | 1 | (min2) 8/16/24/36 | 11/6 | 340 | 推射程 + −1 吨 |
| ENH | Enhanced Gauss Rifle | 3000/3025/3050 | F·ADV | 15 | 1 | (min2) 9/18/27/40 | 10/5 | 370 | 线内最远最轻标准 Gauss |
| STD | Heavy Gauss Rifle | 2805/2820/2840 | E·STD | var⁴ | 2 | (min4) 6/13/20/30 | 16/10 | 360 | 攻城炮，伤随距衰减 25/20/10 |
| IMP | Improve Heavy Gauss Rifle | 2900/2930/2960 | E·STD | 22 | 3 | (min3) 7/13/20/30 | 18/10 | 430 | 平 22 伤**无衰减** |
| ENH | Enhanced Heavy Gauss Rifle | 3000/3025/3050 | F·ADV | 25 | 4 | (min3) 7/14/21/32 | 17/9 | 510 | 平 25 伤无衰减 |
| EXP | Super-Heavy Gauss Rifle | 3045/3050/3055 | G·EXP | var⁵ | 5 | (min4) 6/13/20/30 | 22/14 | 582 | 线内最大单发 35/30/25；衰减攻城；超重底盘 |

**⁴ Heavy Gauss**：25 短 / 20 中 / 10 长。 **⁵ Super-Heavy Gauss**：35 短 / 30 中 / 25 长。

### Magshot / AP / LB-X Gauss（5 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E (min) | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| STD | Anti-Personnel Gauss | 2805/2820/2840 | E·STD | 3 (2d6 反步) | 1 | 3/6/9/12 | 0.5/1 | 21 | 专职反人员 Gauss；`F_BURST_FIRE` |
| STD | Magshot Gauss | 2805/2820/2840 | E·STD | 3 | 1 | 4/8/12/16 | 0.5/1 | 27 | 比正史 Magshot 更远 |
| STD | LB-X Gauss | 2805/2820/2840 | E·STD | 15 实心/霰弹 | 1 | (min2) 7/15/22/33 | 15/7 | 300 | Gauss 射程 + LB-X 双模 |
| IMP | Improve LB-X Gauss | 2900/2930/2960 | E·STD | 15 实心/霰弹 | 1 | (min2) 8/16/24/36 | 13/6 | 330 | 更轻更远 |
| ADV | Heavy LB-X Gauss | 3060/3070/3080 | F·ADV | 20 实心/霰弹 | 2 | (min3) 6/13/20/30 | 19/11 | 420 | 重型双模 Gauss |

---

## 弹道 — HAG 家族（5 件，Hyper-Assault Gauss，集束伤害）

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E (min) | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| STD | HAG/20 | 2805/2820/2840 | E·STD | 20 集束 | 3 | (min2) 9/17/25/33 | 7/6 | 280 | 集束 Gauss，比 Clan HAG 更冷更远 |
| STD | HAG/30 | 2805/2820/2840 | E·STD | 30 集束 | 5 | (min2) 9/17/25/33 | 11/8 | 420 | 同 |
| STD | HAG/40 | 2805/2820/2840 | E·STD | 40 集束 | 7 | (min2) 9/17/25/33 | 14/10 | 560 | 同；OS 新口径 |
| EXP | Heavy HAG/30 | 3045/3050/3055 | G·EXP | 30 集束 | 8 | (min2) 10/20/30/40 | 15/10 | 520 | **+2 集束骰**（更密）；30 格 ext |
| EXP | Heavy HAG/40 | 3045/3050/3055 | G·EXP | 40 集束 | 10 | (min2) 10/20/30/40 | 19/13 | 690 | +2 集束骰；最密最远 |

---

## 弹道 — Advance 动能超级炮（2 件）

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E (min) | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| ADV | Electromagnetic Lance | 3060/3070/3080 | F·ADV | 20 / 5⁶ | 3 | (min3) 9/18/26/34 | 18/11 | 480 | Advance 线圈炮，**功率门控**：满功率 20 点固有 AP 长射；降功率缩到 0/5/10/15/20 失 AP，改投载荷（Flak / Flechette / Precision −2 / Incendiary）|
| ADV | Coil Augmented Railgun | 3060/3070/3080 | F·ADV | 30 平 | 6 | 9/18/27/36 | 22/13 | 700 | Advance 动能顶点，30 平伤**无衰减**、固有 AP（穿甲暴击）；前 12 发稳定，之后 +2 命中且 natural-2 卡壳，累积 +5 报废；单向 **Shed Barrel** 模式 → 25 伤稳定近战（4/6/9/12、无 AP、无磨损）|

**⁶ EM Lance**：满功率 20 伤 9/18/26/34、固有 AP；降功率 5 伤 0/5/10/15/20、失 AP、改投特种弹（4 枚 handler：APDS / Flak / Flechette / Incendiary / Precision）。

## 弹道 — 机枪 / 机炮（8 件）

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| STD | Light Machine Gun | 2805/2820/2840 | E·STD | 1 (1d6 反步) | 0 | 3/6/9/12 | 0.5/1 | 6 | 约长 50% vs 正史 LMG |
| STD | Machine Gun | 2805/2820/2840 | E·STD | 2 (2d6 反步) | 0 | 2/4/6/8 | 0.5/1 | 6 | 射程翻倍于正史 MG，200 发/吨 |
| STD | Heavy Machine Gun | 2805/2820/2840 | E·STD | 3 (3d6 反步) | 0 | 2/4/6/8 | 1/1 | 8 | 三倍正史射程，100 发/吨 |
| STD | Light Machine Gun Array | 2805/2820/2840 | E·STD | 2–4×LMG | 0 | 3/6/9/12 | 0.25/1 | 0 | 链接 2–4 挺 LMG 齐射（Linked/Off）；BV 0（并入 MG）；`F_MGA` |
| STD | Machine Gun Array | 2805/2820/2840 | E·STD | 2–4×MG | 0 | 2/4/6/8 | 0.5/1 | 0 | 同上，MG 基座 |
| STD | Heavy Machine Gun Array | 2805/2820/2840 | E·STD | 2–4×HMG | 0 | 2/4/6/8 | 1/1 | 0 | 同上，HMG 基座 |
| ADV | Machine Cannon | 3060/3070/3080 | F·ADV | 3 (6d6 反步) | 1 | 3/6/9/12 | 1.5/2 | 14 | 补 MG 与 AC 之间的空档；支持 Flak 弹 |
| EXP | Heavy Machine Cannon | 3045/3050/3055 | G·EXP | 5 (10d6 反步) | 2 | 2/5/8/11 | 2.5/3 | 26 | 更重版 + Flak 弹 |

**弹道段小计 72 OS + 8 IS = 80 件**：STD 32（含 MG 6）· IMP 20 · ENH 5 · ADV 10 · EXP 5 · ⚠STD 2（Rotary AC/2/5）· ⚠EXP 3（Ultra LB-X）· IS BF 4 · IS RF 4。

---

## 导弹 — LRM 家族（25 件）

OS 核心 LRM（Improve / Enhanced / Heavy）带 **远距档 −1 命中**（`F_OS_LRM_LONG_SPEC`）+ **内建 +2 集束**（发射器固有，不可被 ECM 压制，与 FCS 叠加）+ 间接火力。Streak 系锁定必中无命中修正；ER-LRM 换长档 bonus 得极距。链接 Artemis IV FCS 给 +2 集束、Artemis V FCS 再给任意距离 −1 命中（均需对应 capable 弹）。

### LRM 核心（Improve / Enhanced / Heavy，11 件）
| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L/E (min) | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| IMP | Improve LRM 5 | 2900/2930/2960 | E·STD | 5 | 2 | (min6) 7/10/24/30 | 1.5/1 | 52 | OS 轻量核心 LRM；远档 −1、内建 +2 集束；可间接 |
| IMP | Improve LRM 10 | 2900/2930/2960 | E·STD | 10 | 4 | (min6) 7/10/24/30 | 3/2 | 104 | 同上 |
| IMP | Improve LRM 15 | 2900/2930/2960 | E·STD | 15 | 5 | (min6) 7/10/24/30 | 5/3 | 156 | 同上 |
| IMP | Improve LRM 20 | 2900/2930/2960 | E·STD | 20 | 6 | (min6) 7/10/24/30 | 7/5 | 208 | 同上 |
| ENH | Enhanced LRM 5 | 3000/3025/3050 | F·ADV | 5 | 2 | (min6) 7/10/24/30 | 1/1 | 59 | Clan 重量；旧 doc 误标"无 min"，**代码仍是 min 6** |
| ENH | Enhanced LRM 10 | 3000/3025/3050 | F·ADV | 10 | 4 | (min6) 7/10/24/30 | 2.5/1 | 117 | 同上 |
| ENH | Enhanced LRM 15 | 3000/3025/3050 | F·ADV | 15 | 5 | (min6) 7/10/24/30 | 3.5/2 | 177 | 同上 |
| ENH | Enhanced LRM 20 | 3000/3025/3050 | F·ADV | 20 | 6 | (min6) 7/10/24/30 | 5/4 | 236 | 同上 |
| STD | Heavy LRM 10 | 2805/2820/2840 | E·STD | 10 | 4 | (min6) 7/10/24/30 | 5/2 | 155 | 单/Ultra 双齐射（**无卡壳**，`OSDoubleFireLRMHandler`）；远档 −1、内建 +2 集束 |
| STD | Heavy LRM 20 | 2805/2820/2840 | E·STD | 20 | 6 | (min6) 7/10/24/30 | 10/5 | 316 | 同上 |
| STD | Heavy LRM 30 | 2805/2820/2840 | E·STD | 30 | 9 | (min6) 7/10/24/30 | 14/7 | 472 | 同上；30 rack 极端重齐射 |

### ER-LRM（3 件）
| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L (ext) | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| ADV | ER LRM 10 | 3060/3070/3080 | F·ADV | 10 | 6 | 12/22/38 | 6/3 | 104 | 极距 LRM；Artemis 兼容；**无长档 −1 与内建集束**（走 ER 配方） |
| ADV | ER LRM 15 | 3060/3070/3080 | F·ADV | 15 | 9 | 12/22/38 | 9/5 | 156 | 同上 |
| ADV | ER LRM 20 | 3060/3070/3080 | F·ADV | 20 | 12 | 12/22/38 | 13/6 | 210 | 同上 |

### Streak LRM / Improve Streak LRM / Extended Streak LRM（11 件）
Streak 系：锁定齐射，锁上则全中；**无长档 −1 bonus**；重于非 Streak 同口径。range 走 canon LRM 7/14/21+（短档 7，不是核心 OS 的 10）。

| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L/E (min) | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| ⚠IMP | Improve Streak LRM 5 | 2900/2930/2960 | **F·ADV**⚠¹ | 5 | 2 | (min6) 7/14/24/30 | 2/1 | 91 | Clan 级锁定；SL 标 ADV（应 STD） |
| ⚠IMP | Improve Streak LRM 10 | 2900/2930/2960 | **F·ADV**⚠¹ | 10 | 4 | (min6) 7/14/24/30 | 5/2 | 182 | 同上 |
| ⚠IMP | Improve Streak LRM 15 | 2900/2930/2960 | **F·ADV**⚠¹ | 15 | 5 | (min6) 7/14/24/30 | 7/3 | 273 | 同上 |
| ⚠IMP | Improve Streak LRM 20 | 2900/2930/2960 | **F·ADV**⚠¹ | 20 | 6 | (min6) 7/14/24/30 | 10/5 | 363 | 同上 |
| ADV | Streak LRM 5 | 3060/3070/3080 | F·ADV | 5 | 2 | (min6) 7/14/24/30 | 2.5/1 | 91 | 锁定齐射全中 |
| ADV | Streak LRM 10 | 3060/3070/3080 | F·ADV | 10 | 4 | (min6) 7/14/24/30 | 6/2 | 182 | 同上 |
| ADV | Streak LRM 15 | 3060/3070/3080 | F·ADV | 15 | 5 | (min6) 7/14/24/30 | 8.5/4 | 273 | 同上 |
| ADV | Streak LRM 20 | 3060/3070/3080 | F·ADV | 20 | 6 | (min6) 7/14/24/30 | 11.5/5 | 363 | 同上 |
| EXP | Extended Streak LRM 10 | 3045/3050/3055 | G·EXP | 10 | 6 | (min10) 12/22/38 (ext57) | 8/4 | 200 | 极距 + 保证命中 |
| EXP | Extended Streak LRM 15 | 3045/3050/3055 | G·EXP | 15 | 9 | (min10) 12/22/38 (ext57) | 11/6 | 300 | 同上 |
| EXP | Extended Streak LRM 20 | 3045/3050/3055 | G·EXP | 20 | 12 | (min10) 12/22/38 (ext57) | 16/7 | 400 | 同上 |

## 导弹 — SRM 家族（18 件）

OS 核心 SRM（Improve / Enhanced / Heavy）带 **短档 −1 命中**（`F_OS_SRM_SHORT_SPEC`）+ **内建 +2 集束**。Streak 系锁定必中、无命中修正；Streak SRM 用 canon 3/6/9 短距，Improve Streak SRM 用延伸 4/8/12/16。

### SRM 核心（Improve / Enhanced / Heavy，10 件）
| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L (ext) | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| IMP | Improve SRM 2 | 2900/2930/2960 | E·STD | 2 | 2 | 6/8/10 (ext12) | 0.75/1 | 21 | OS 核心 SRM：短档 −1、内建 +2 集束 |
| IMP | Improve SRM 4 | 2900/2930/2960 | E·STD | 4 | 3 | 6/8/10 (ext12) | 1.5/1 | 39 | 同上 |
| IMP | Improve SRM 6 | 2900/2930/2960 | E·STD | 6 | 4 | 6/8/10 (ext12) | 2/2 | 59 | 同上 |
| ENH | Enhanced SRM 2 | 3000/3025/3050 | F·ADV | 2 | 2 | 6/8/10 (ext12) | 0.5/1 | 21 | Clan 效率版，战斗档同 Improve |
| ENH | Enhanced SRM 4 | 3000/3025/3050 | F·ADV | 4 | 3 | 6/8/10 (ext12) | 1/1 | 39 | 同上 |
| ENH | Enhanced SRM 6 | 3000/3025/3050 | F·ADV | 6 | 4 | 6/8/10 (ext12) | 1.5/1 | 59 | 同上 |
| STD | Heavy SRM 4 | 2805/2820/2840 | E·STD | 4 | 3 | 6/8/10 (ext12) | 3/2 | 65 | 单/Ultra 双齐射（**无卡壳**，`OSDoubleFireSRMHandler`）；短档 −1、内建 +2 集束 |
| STD | Heavy SRM 6 | 2805/2820/2840 | E·STD | 6 | 4 | 6/8/10 (ext12) | 4.5/3 | 95 | 同上 |
| STD | Heavy SRM 8 | 2805/2820/2840 | E·STD | 8 | 5 | 6/8/10 (ext12) | 6/4 | 130 | 同上；OS 新口径 |
| STD | Heavy SRM 12 | 2805/2820/2840 | E·STD | 12 | 7 | 6/8/10 (ext12) | 9/5 | 190 | 同上；OS 新口径 |

### Streak SRM / Improve Streak SRM（8 件）
| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| ⚠IMP | Improve Streak SRM 2 | 2900/2930/2960 | **F·ADV**⚠¹ | 2 | 2 | 4/8/12/16 | 1/1 | 40 | Clan 级锁定；**延伸射程 4/8/12/16**；SL 标 ADV（应 STD）|
| ⚠IMP | Improve Streak SRM 4 | 2900/2930/2960 | **F·ADV**⚠¹ | 4 | 3 | 4/8/12/16 | 2/1 | 79 | 同上 |
| ⚠IMP | Improve Streak SRM 6 | 2900/2930/2960 | **F·ADV**⚠¹ | 6 | 4 | 4/8/12/16 | 3/2 | 118 | 同上 |
| ⚠IMP | Improve Streak SRM 8 | 2900/2930/2960 | **F·ADV**⚠¹ | 8 | 5 | 4/8/12/16 | 4/3 | 158 | 同上；OS 新口径 |
| ADV | Streak SRM 2 | 3060/3070/3080 | F·ADV | 2 | 2 | 3/6/9 (ext12) | 1.25/1 | 30 | canon 短距锁定齐射 |
| ADV | Streak SRM 4 | 3060/3070/3080 | F·ADV | 4 | 3 | 3/6/9 (ext12) | 2.5/1 | 59 | 同上 |
| ADV | Streak SRM 6 | 3060/3070/3080 | F·ADV | 6 | 4 | 3/6/9 (ext12) | 3.75/2 | 89 | 同上 |
| ADV | Streak SRM 8 | 3060/3070/3080 | F·ADV | 8 | 5 | 3/6/9 (ext12) | 5/3 | 120 | 同上；OS 新口径 |

## 导弹 — MRM 家族（7 件）

OS MRM 核心带 **超大中距档 −1 命中**（`F_OS_MRM_MEDIUM_SPEC`）+ **内建 +2 集束**；Diana III/IV FCS 加 +2 集束，Diana IV 再加任意距离 −1（需 capable 弹）。

| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L (ext) | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| IMP | Improve MRM 10 | 2900/2930/2960 | E·STD | 10 | 4 | 3/16/18 (ext20) | 3/2 | 70 | 中距档 −1、内建 +2 集束 |
| IMP | Improve MRM 20 | 2900/2930/2960 | E·STD | 20 | 6 | 3/16/18 (ext20) | 7/3 | 140 | 同上 |
| IMP | Improve MRM 30 | 2900/2930/2960 | E·STD | 30 | 10 | 3/16/18 (ext20) | 9.5/5 | 210 | 同上 |
| IMP | Improve MRM 40 | 2900/2930/2960 | E·STD | 40 | 12 | 3/16/18 (ext20) | 12/7 | 280 | 同上；OS 新口径 |
| ADV | Streak MRM 10 | 3060/3070/3080 | F·ADV | 10 | 4 | 3/13/16/20 | 6/3 | 120 | 锁定 MRM 保证命中；range 比 Improve MRM 更短（13/16/20），刻意重/占位 |
| ADV | Streak MRM 20 | 3060/3070/3080 | F·ADV | 20 | 6 | 3/13/16/20 | 12/5 | 240 | 同上 |
| ADV | Streak MRM 30 | 3060/3070/3080 | F·ADV | 30 | 10 | 3/13/16/20 | 17/7 | 360 | 同上 |

## 导弹 — MML 家族（16 件，双模 LRM / SRM）

所有 MML 的 LRM-模式 range 都是 canon LRM 的 7/14/21/28 min 6（SRM 模式自动切换到 SRM range）。Heavy MML 加 Single/Ultra 双齐射（无卡壳），Streak MML 全锁定（**不兼容 Artemis**）。

### MML 核心 / Improve（8 件）
| tier | 武器 | Intro | TR·SL | rack | heat | LRM 档 (min) | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| STD | MML 3 | 2805/2820/2840 | E·STD | 3 | 2 | (min6) 7/14/21/28 | 1.25/2 | 29 | 双模发射器，比 IS MML 轻 |
| STD | MML 5 | 2805/2820/2840 | E·STD | 5 | 3 | (min6) 7/14/21/28 | 2.5/3 | 45 | 同上 |
| STD | MML 7 | 2805/2820/2840 | E·STD | 7 | 4 | (min6) 7/14/21/28 | 4/3 | 67 | 同上 |
| STD | MML 9 | 2805/2820/2840 | E·STD | 9 | 5 | (min6) 7/14/21/28 | 5/4 | 86 | 同上 |
| IMP | Improve MML 5 | 2900/2930/2960 | E·STD | 5 | 3 | (min6) 7/14/21/28 | 2/2 | 45 | 更轻的双模 |
| IMP | Improve MML 7 | 2900/2930/2960 | E·STD | 7 | 4 | (min6) 7/14/21/28 | 3/3 | 67 | 同上 |
| IMP | Improve MML 9 | 2900/2930/2960 | E·STD | 9 | 5 | (min6) 7/14/21/28 | 4/4 | 86 | 同上 |
| IMP | Improve MML 11 | 2900/2930/2960 | E·STD | 11 | 6 | (min6) 7/14/21/28 | 5.5/4 | 105 | 同上；OS 新口径 |

### Heavy MML / Streak MML（8 件）
| tier | 武器 | Intro | TR·SL | rack | heat | LRM 档 (min) | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| ADV | Heavy MML 5 | 3060/3070/3080 | F·ADV | 5 | 6 | (min6) 7/14/21/28 | 4/3 | 85 | 双模 + 单/Ultra 双齐射（**无卡壳**，`OSDoubleFireLRM/SRMHandler`）|
| ADV | Heavy MML 8 | 3060/3070/3080 | F·ADV | 8 | 8 | (min6) 7/14/21/28 | 6.5/4 | 145 | 同上 |
| ADV | Heavy MML 11 | 3060/3070/3080 | F·ADV | 11 | 12 | (min6) 7/14/21/28 | 9/6 | 200 | 同上 |
| ADV | Heavy MML 14 | 3060/3070/3080 | F·ADV | 14 | 14 | (min6) 7/14/21/28 | 12/7 | 250 | 同上；OS 新口径 |
| ADV | Streak MML 5 | 3060/3070/3080 | F·ADV | 5 | 3 | (min6) 7/14/21/28 | 3.5/3 | 75 | 双模 + Streak 锁定（**不兼容 Artemis**）|
| ADV | Streak MML 7 | 3060/3070/3080 | F·ADV | 7 | 4 | (min6) 7/14/21/28 | 5/4 | 105 | 同上 |
| ADV | Streak MML 9 | 3060/3070/3080 | F·ADV | 9 | 5 | (min6) 7/14/21/28 | 6.5/5 | 135 | 同上 |
| ADV | Streak MML 11 | 3060/3070/3080 | F·ADV | 11 | 6 | (min6) 7/14/21/28 | 8/6 | 165 | 同上；OS 新口径 |

## 导弹 — ATM 家族（8 件，可变载荷：Extended / Standard / High-Explosive）

ATM 核心机制：装 **SRTM / APTM / LRTM** 改射程与单发伤（canon ATM 行为）。

| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L/E (min) | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|---|
| ⚠IMP | Improve ATM 3 | 2900/2930/2960 | **F·ADV**⚠¹ | 3 | 2 | (min4) 5/10/15/20 | 1.5/2 | 83 | iATM 级，更丰富载荷；SL 标 ADV（应 STD）|
| ⚠IMP | Improve ATM 6 | 2900/2930/2960 | **F·ADV**⚠¹ | 6 | 4 | (min4) 5/10/15/20 | 3.5/3 | 165 | 同上 |
| ⚠IMP | Improve ATM 9 | 2900/2930/2960 | **F·ADV**⚠¹ | 9 | 6 | (min4) 5/10/15/20 | 5.5/4 | 247 | 同上 |
| ⚠IMP | Improve ATM 12 | 2900/2930/2960 | **F·ADV**⚠¹ | 12 | 8 | (min4) 5/10/15/20 | 7.5/5 | 350 | 同上 |
| ADV | ATM 3 | 3060/3070/3080 | F·ADV | 3 | 2 | (min4) 5/10/15/20 | 1.5/2 | 53 | canon ATM：LRTM/SRTM/APTM 可变载荷 |
| ADV | ATM 6 | 3060/3070/3080 | F·ADV | 6 | 4 | (min4) 5/10/15/20 | 3.5/3 | 105 | 同上 |
| ADV | ATM 9 | 3060/3070/3080 | F·ADV | 9 | 6 | (min4) 5/10/15/20 | 5/4 | 147 | 同上 |
| ADV | ATM 12 | 3060/3070/3080 | F·ADV | 12 | 8 | (min4) 5/10/15/20 | 7/5 | 212 | 同上 |

## 导弹 — Tactical Heavy Missile Launcher（8 件，原 Dragon Piercer 改名）

> 内部名 / lookup 仍保留 `OSDragonPiercer*`（存档兼容）。单枚大穿甲弹头，非集束；专属 handler `OSDragonPiercerHandler`（Improve 版用 `OSDragonPiercerImpHandler`）。

| tier | 武器 | Intro | TR·SL | rack | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|:--:|--:|---|
| STD | Tactical Heavy Missile Launcher 5 | 2805/2820/2840 | E·STD | 1 | 3/– | 64 | 单枚 AP 弹头，穿甲暴击 **8+** |
| STD | Tactical Heavy Missile Launcher 10 | 2805/2820/2840 | E·STD | 1 | 7/– | 127 | 同上 |
| STD | Tactical Heavy Missile Launcher 15 | 2805/2820/2840 | E·STD | 1 | 11/– | 229 | 同上 |
| STD | Tactical Heavy Missile Launcher 20 | 2805/2820/2840 | E·STD | 1 | 15/– | 305 | 同上 |
| IMP | Improve THML 5 | 2900/2930/2960 | E·STD | 1 | 2.5/– | 64 | 更轻；穿甲暴击加深到 **7+** |
| IMP | Improve THML 10 | 2900/2930/2960 | E·STD | 1 | 5.5/– | 127 | 同上 |
| IMP | Improve THML 15 | 2900/2930/2960 | E·STD | 1 | 8.5/– | 229 | 同上 |
| IMP | Improve THML 20 | 2900/2930/2960 | E·STD | 1 | 12/4 | 305 | 同上 |

> **crit 字段**："–" 表示代码未显式设 criticalSlots（由基类处理）；只有 Improve THML 20 显式设 crit=4。

## 导弹 — Narc / 信标（3 件，0 伤，挂导引 pod）

| tier | 武器 | Intro | TR·SL | heat | S/M/L/E | t/c | BV | 机制 |
|---|---|:--:|:--:|--:|:--:|:--:|--:|---|
| STD | Narc Missile Beacon | 2805/2820/2840 | E·STD | 0 | 3/6/9/12 | 3/2 | 30 | 挂导引 pod，友方 Narc 导弹保证集束命中 |
| STD | Seeker Missile Beacon | 2805/2820/2840 | E·STD | 0 | 3/6/9/12 | 2.5/2 | 30 | 轻量 Narc 信标，同 pod |
| ADV | Munin Missile Beacon | 3060/3070/3080 | F·ADV | 0 | 4/9/15/18 | 5/3 | 75 | iNarc 级多 pod：Homing / ECM / Haywire / Nemesis / **Lodestar**（§9.6）|

**导弹段小计 88 件**：STD 23（Heavy LRM/SRM、MML、THML、Narc）· IMP 15 · ⚠IMP 12（Improve Streak LRM/SRM、Improve ATM — SL 应 STD）· ENH 7 · ADV 28（ER-LRM、Streak LRM/SRM/MRM/MML、Heavy MML、ATM、Munin）· EXP 3（Extended Streak LRM）。

**⚠¹** `Improve Streak LRM / Improve Streak SRM / Improve ATM`（共 12 件）：intro 对齐 Improve（2900–2960），但 `SimpleTechLevel` 标 **ADVANCED**（其他 Improve-系导弹均 STANDARD）→ **内部不一致，SL 应降 STANDARD**。模式跟激光 ⚠¹（Improve Heavy Pulse 系）完全一样。

**旧版 doc 订正**：
- Enhanced LRM 系 4 件：旧版标"**无最小射程**"是错的 — 代码所有 Enhanced LRM 都是 **min 6**。
- Streak LRM 系：旧版把 range 写成 "(min6) 7/10/24 (ext30)" 跟核心 LRM 一致，但代码里 Streak LRM 用的是 **canon 7/14/24/30**（med 档是 14 不是 10，因为 Streak 系不走核心 LRM 的"远档 −1 换 med 收紧"配方）。
- Streak MRM 系：旧版标 "3/16/18 (ext20)" 跟 Improve MRM 一致，代码里是 **3/13/16/20**（med/long 都更短，刻意弱化为"锁定但射程不如 Improve"）。
- Improve Streak SRM 系：旧版标 "6/8/10 (ext12)" 跟 Improve SRM 一致，代码里是 **4/8/12/16**（延伸射程，Clan 风）。

## 导弹火控（装在发射器旁增益；均需装对应"capable"弹才生效）

| 件 | t/c | 机制 |
|---|:--:|---|
| Artemis IV FCS (OS) | 1/1 | +2 集束（仅 Artemis IV-capable 弹）；**无命中修正** |
| Artemis V FCS (OS) | 1.5/1 | 高级 LRM/SRM 火控：+2 集束 **且任意距离 −1 命中**（仅 Artemis V-capable 弹；ECM/隐身压制集束半） |
| Diana III FCS | 1/1 | MRM 专用（对标 Apollo）：+2 集束（仅 Diana III-capable MRM 弹）；**无命中修正** |
| Diana IV FCS | 1.5/1 | 高级 MRM 火控：+2 集束 **且任意距离 −1 命中**（仅 Diana IV-capable MRM 弹） |
| Orion V FCS | 1.5/2 | 高端整合：集束 **+3**（vs Artemis +2）；对 Streak 改助锁（锁定骰 −1 + 免标准 ECM 断锁） |

---

## 备注

- **导弹系已按 2026-08-29 重做同步**（提交 `70ea7cf99c`）：LRM/MRM/SRM 重定射程、各专精档 −1（含新增 SRM 短档 `F_OS_SRM_SHORT_SPEC`）、核心发射器**内建 +2 集束**（不可被 ECM 压制、与 FCS 叠加）；Artemis IV / Diana III 退回**只给 +2 集束**，新增 **Artemis V / Diana IV**（1.5t）才另给任意距离 **−1 命中**（均需装对应 capable 弹）；Dragon Piercer 改名 **Tactical Heavy Missile Launcher**（内部名保留）。**BV 尚未按新强度重调。**
- **测试状态**：本表所有武器均**仅过编译 + 装备初始化单元测试，未经对局实测**（见 `OS_COMPONENT_PLAYTEST_CHECKLIST.md`）。
- **相关文档**：`OS_TECHBASE_DEVELOPMENT_PLAN.md`（设计/实现，§7 AC munitions、§8 Advance 动能、§2 HVAC）；`CANON_AMMO_REFERENCE.md`（弹药）；`equipmentmessages.properties`（游戏内 ⓘ info 原文）。

- **全文档 ⚠ 分类问题汇总**（本次重建发现，供后续 intro-date / SL 清查用）：
  - **能量**：Improve Heavy Pulse 系 3 件 SL=ADV 应 STD；ER Heavy / ER Pulse 无前缀 / Heavy Pulse 无前缀 共 9 件挂 Advance 带应 Improve；Binary/Trinary 系 4 件 tier 未拍定（Hyper 在 EXP 中间断档）。
  - **弹道**：Rotary AC/2 和 /5 挂 2805 Standard 却带 TR F，设计矛盾（建议统一到 ADV 或 TR 降 E）；Ultra LB-X 系 3 件挂 EXP+G 过头（建议 ADV+F）。
  - **导弹**：Improve Streak LRM / Improve Streak SRM / Improve ATM 共 12 件 SL=ADV 应 STD（跟 Improve Heavy Pulse 同 pattern）；旧版 doc 对 Enhanced LRM 系误标"无 min"、对 Streak LRM/MRM 系 range 误抄 — 本次重建已订正。
