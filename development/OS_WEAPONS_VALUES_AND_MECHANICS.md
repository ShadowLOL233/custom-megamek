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
> - `tier`：**设计阶梯归属**（STD / IMP / ENH / ADV / EXP）。⚠ = 当前代码 intro-date 与设计意图不符（StaticTechLevel 的 ⚠ 已在 2026-11-07 批次全部解决）。
> - `Intro`：`setISAdvancement(proto, prod, common)`；extinct / reintro 全为 DATE_NONE，省略。
> - `TR·SL`：`TechRating · SimpleTechLevel`。E / F / G = TechRating；STD / ADV / EXP = STANDARD / ADVANCED / EXPERIMENTAL；`INT = INTRO`（2026-11-07 新增，用于 10 件真·基础武器）。
> - `dmg` 单发伤害（`var` = 随射程 / 弹种变化，脚注给具体数）；`heat` 开火热量；`S/M/L/E` 短/中/远/极限射程（格）；`(min N)` 最小射程；`t/c` 吨/临界；`BV` 对战点值。
>
> **五级阶梯**（当前代码 intro 实际排布）：Standard **2805·20·40** · Improve **2900·30·60** · Enhanced **3000·25·50** · Advance **3060·70·80** · Experimental **3045·50·55**。
>
> **⚠ intro-date 长期待办（2026-11-07 立项，低优先级）**：当前所有武器的 `intro-date` 都是按 OS 5-tier 机械式分配固定 3 日期（`setISAdvancement(proto, prod, common)`），**同一 tier 内不同武器共享完全相同的日期**。设计意图是 intro-date 应该跟 **OS 世界观的历史时间线 / 技术复兴事件**挂钩——不同武器对应不同历史时段的技术复兴（而非按 tier 机械分配）。由于 OS 的历史 timeline / 具体事件尚未完整构建，本项将**随武器数值/机制调整 + OS 历史写作同步推进**，每件武器独立拍板。本文档 `Intro` 列当前仅作参考基线，等历史事件明确后按叙事推演驱动调整。

---

## 平衡度量：WSC（Weapon System Cost，武器系统成本）公式

> **2026-11-07 立项,2026-11-08 校准公式与全表应用**。从源码数值反推"武器实际装车代价",作为调参时的参考基准。核心洞察——**能量武器的真实吨位代价不只在武器本身,还在散热**;弹道/导弹武器还有**弹药税**。

### 公式（分析口径,不取整）

**能量武器**（无弹药,假设全 DHS,忽略 engine-free 的 10 个"白嫖"DHS）：

```
WSC_吨   =  T_武器  +  heat / 2
WSC_crit =  C_武器  + (heat / 2) × 3
```

DHS 每个 1t · 3c,处理 2 热。**公式用分数 heat/2 作跨武器比较口径**（不取整）；若要算具体机甲上的实际代价，再把 engine-free 10 DHS 从 heat 里扣掉再取上整。

**弹道 / 导弹武器**（含弹药税,典型 1 场战斗 1.5–3 吨弹药,取 2t · 2c 作中值）：

```
WSC_吨   =  T_武器  +  heat / 2        +  N_ammo
WSC_crit =  C_武器  + (heat / 2) × 3   +  N_ammo
```

弹道武器还有两个**隐藏税**（未计入 WSC 但需要在平衡时意识到）:**爆炸风险**（Gauss/AC 弹药仓）、**弹药枯竭**（后期无子弹即废）。

**特例口径：**
- **火焰器 / 机枪阵列（MGA）/ Narc / Plasma Cannon(0 伤)**：虽然挂 ballistic / missile 家族但有特殊供弹模型 — 火焰器能量供弹无弹药、MGA 自身无弹链主 MG、Narc/Plasma Cannon 的"伤害"不在装甲层 — 视情况用能量公式或 `N_ammo=1`。表中标注。
- **多模 / 变载荷武器（EM Lance 满/降功率、ATM SRTM/APTM/LRTM、LB-X 实心/霰弹）**：按"主用模式"算 WSC，备注里给其它模式的 Δ。
- **Multi-turn（Hyper 系当前隔回合、Rotary PPC 自冷 3 发）**：WSC 不变（吨位/热还是物理上的），**期望伤害 / 回合要按周期折算**（见下 `t/dmg`）。

### 衍生指标

- **`t/dmg`（吨伤比）**：`WSC_吨 / 期望伤害`。衡量"用一吨机甲吨位换多少伤害"。目标带：
  - **Introductory / Standard**：1.1–1.4 t/dmg
  - **Improve / Enhanced**：0.90–1.15 t/dmg
  - **Advance / Experimental**：可以突破 0.85，但必须配机制惩罚（+命中罚、充能、爆炸、min range 等）
- **`c/dmg`（crit 伤比）**：`WSC_crit / 期望伤害`。Mek 躯干总 crit 有限（LT/RT/CT 各 6–12 crit），**crit 效率往往比吨位效率更关键**
- **期望伤害**（Expected Damage）：
  - **单发武器**：`dmg × 命中率`
  - **cluster 导弹**：`rack × 0.6`（纯 cluster）至 `rack × 0.7`（OS 内建 +2 集束）
  - **Streak 系**：`rack × 锁定命中率`
  - **Hyper / Heavy Pulse 等 Multi-turn 武器**：`单发 dmg / 开火回合周期`（例：Hyper Laser 隔回合 → dmg / 2）

### BV vs WSC 的 cross-check

BV 不含 ammo / crit / 爆炸风险。WSC 跟 BV 的排序**不应该脱节**——若 BV 低但 WSC 低（意味着高性价比但 BV 没反映），说明武器**被 BV 低估**，是潜在的 OP 件。反之则 UP 件。

**调参工作流**：每次调武器数值 → 重算 WSC → 跟 BV 比对 → 若差距大则调 BV。

### 参考值（跨家族对照 — 25 件代表武器）

> 下表的 WSC_t / WSC_c 来自公式口径(T + heat/2 [+N_ammo])，与各子族表中的值一致。期望伤害已对 **tier-independent 命中修正**（Heavy +1 → ×0.87、Pulse −2 → ×1.40、Enhanced ER Pulse −3 → ×1.55、OS 核心 LRM 长档 −1 → ×1.20、Ultra 双发 → ×1.30、集束口径 OS 内建 +2 → ×0.70/×0.84、隔回合 Hyper → ×0.50）做调整。

| 武器 | T·c | heat | N_ammo | 期望 dmg | WSC_t | WSC_c | **t/dmg** | BV |
|---|---|---|---|---|---|---|---|---|
| Medium Laser (base) | 1/1 | 3 | — | 5 | 2.5 | 5.5 | **0.50** | 46 |
| Large Laser (base) | 5/2 | 8 | — | 8 | 9 | 14 | **1.13** | 123 |
| Improve ER Large Laser | 4/1 | 12 | — | 10 | 10 | 19 | **1.00** | 230 |
| Heavy Large Laser | 4/3 | 18 | — | 16 ×0.87 = 13.9 | 13 | 30 | **0.93** | 244 |
| Enhanced ER Large Pulse | 6/4 | 18 | — | 14 ×1.55 = 21.7 | 15 | 31 | **0.69** | 350 |
| Hyper Laser Large | 8/6 | 25 | — | 25 / 2 回合 = **12.5** | 20.5 | 43.5 | **1.64** | 620 |
| ER Trinary Laser | 11/6 | 32 | — | 24 | 27 | 54 | **1.13** | 489 |
| Hyper PPC (当前) | 10/7 | 30 | — | 30 / 2 = **15** | 25 | 52 | **1.67** | 824 |
| Enhanced ER PPC | 6/2 | 15 | — | 15 ×1.20 = 18 | 13.5 | 24.5 | **0.75** | 412 |
| AC/10 (OS) | 12/6 | 3 | 2 | 10 | 15.5 | 12.5 | **1.55** | 123 |
| Improve AC/10 | 10/6 | 2 | 2 | 10 | 13 | 11 | **1.30** | 123 |
| Enhanced HVAC/10 | 8/4 | 2 | 2 | 10 ×1.11 = 11.1 | 11 | 9 | **0.99** | 165 |
| Ultra AC/10 (OS) | 13/7 | 4 | 2 | 10 ×1.30 = 13 | 17 | 15 | **1.31** | 210 |
| Enhanced Ultra AC/10 | 10/4 | 3 | 2 | 10 ×1.30 ×1.20 = 15.6 | 13.5 | 10.5 | **0.87** | 270 |
| Gauss Rifle (OS) | 12/6 | 1 | 2 | 15 | 14.5 | 9.5 | **0.97** | 320 |
| Improve Gauss Rifle | 11/6 | 1 | 2 | 15 | 13.5 | 9.5 | **0.90** | 340 |
| Enhanced Gauss Rifle | 10/5 | 1 | 2 | 15 | 12.5 | 8.5 | **0.83** | 370 |
| Enhanced Heavy Gauss | 17/9 | 4 | 2 | 25 | 21 | 17 | **0.84** | 510 |
| Coil Augmented Railgun | 22/13 | 6 | 2 | 30 | 27 | 24 | **0.90** | 700 |
| HAG/30 | 11/8 | 5 | 2 | 30 ×0.60 = 18 | 15.5 | 17.5 | **0.86** | 420 |
| Enhanced LRM 20 | 5/4 | 6 | 2 | 20 ×0.84 = 16.8 | 10 | 15 | **0.60** | 236 |
| Heavy LRM 20 | 10/5 | 6 | 2 | 20 ×0.84 ×1.30 = 21.8 | 15 | 16 | **0.69** | 316 |
| Enhanced SRM 6 | 1.5/1 | 4 | 2 | 12 ×0.70 ×1.10 = 9.2 | 5.5 | 9 | **0.60** | 59 |
| Improve ATM 12 | 7.5/5 | 8 | 2 | 12 ×2 ×0.60 = 14.4 | 13.5 | 19 | **0.94** | 350 |
| Streak LRM 20 | 11.5/5 | 6 | 2 | 20 ×0.90 = 18 | 16.5 | 16 | **0.92** | 363 |

**关键观察（2026-11-08 WSC 全量铺开后更新）**：
1. **Gauss 系仍领先直伤效率**：Enhanced Gauss (0.83) / Improve Gauss (0.90) / Enhanced Heavy Gauss (0.84) — 整个家族 **t/dmg 都在 0.83-1.00 带**。只被 crit 税（6c）和弹药爆炸风险制约
2. **AC 系吨伤最差**（1.55），但 **Enhanced HVAC** 把 AC 家族拉回 **~1.0** → HVAC 是**长距反装甲的 meta 件**；Ultra AC 加精度 (Enhanced Ultra AC/10 = 0.87) 也 Gauss 看齐
3. **Enhanced ER Pulse 系**：-3 命中加成让 effective t/dmg 跌到 **0.69**（Large）— 全家族 **能量武器最 ton-efficient** 件
4. **Hyper 系当前明显偏弱**：Hyper Laser 1.64, Hyper PPC **1.67** — 跟 ER Trinary (1.13) 和 ER Heavy PPC (1.11) 比严重劣势。2026-11-07 重设计后预期 Hyper Laser 降到 ~0.80、Hyper PPC 降到 ~0.90
5. **集束导弹（Enhanced LRM 20 / Enhanced SRM 6）t/dmg 跌到 0.60**：是**全文档最省吨**的武器类别;但有"命中率只有 ~84% / 70%"的方差、弹药税、min range 限制
6. **Streak 系 t/dmg 落在 0.92**：比核心 LRM 贵约 50%,换"保证命中"
7. **Coil Augmented Railgun 0.90**：Advance tier 顶点但 **BV 700 / WSC_t 27 = 25.9** 跟 Enhanced ER PPC (30.5) 几乎并列 → **BV 合理反映了 "30 平伤 + 固有 AP + Shed Barrel" 的溢价**
8. **BV vs WSC 偏差总览**（BV / WSC_t）：
   - **最贵**（BV 密度高）：Enhanced Ultra AC/20 **23.5**、Enhanced ER PPC **30.5**、Enhanced ER Large Pulse **23.3**、Hyper PPC **33.0**（非线性反映 EXP 溢价）
   - **洼地**（BV 密度低 → 候选 BV 上调）：ER Heavy Large Laser **15.3**、Enhanced HVAC/16 **16.5**、Improve ATM 12 (但 BV 已高) 、MRM 40 **14.0**、THML 20 **14.5**、Extended Streak LRM 20 **16.7**

---

## 能量 — 异种集束激光系(Binary / Trinary / Hyper,共 5 件代码态;新架构拆分为两条技术线)

> 术语注:本段"集束"指 Binary/Trinary 的"多镜筒并打";"Hyper"系自 2026-11-08 起独立为**高能激光系**(海军激光 micro-化),跟"集束激光系"分家。
>
> **架构总览(2026-11-08)**:
> - **集束激光系** = Binary + Trinary(base 无 Resonance)+ 3 可选模组(RM / LEM / LEC),Advance tier,中距多功能 modular 平台
> - **高能激光系** = Hyper Laser + 必需支援组件(HSM + C-2500 Combat Computer Core),Experimental tier,超远距狙击件
> - **ER Binary / ER Trinary 删除**(职能并入 Hyper Laser 的长距签名)
> - 下方 "当前代码数值" 表反映**尚未变更的现状**,新架构见"📐 2026-11-08 架构重构决议"子段

集束型激光把多个镜筒并成一次打击;**Resonance Tuning**(当前 base 内建)只对基础标准装甲加成,遇 Ferro-Fibrous 及一切先进/特种装甲失效 — **新架构将 Resonance 剥离为可选模组 RM**。

### 当前代码数值

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| ADV⚠³ | Binary Laser System | 3060/3070/3080 | F·STD | 16 | 16 | 5/10/15/20 | 7/4 | 15 | 28 | 246 | 双筒并一击，无命中修正；Resonance ×1.30 → 21 vs 标装甲 |
| ADV⚠³ | ER Binary Laser System | 3060/3070/3080 | F·STD | 16 | 20 | 8/15/22/30 | 7/4 | 17 | 34 | 326 | −1 命中；×1.30 → 21；换热换射程 |
| ADV⚠³ | Trinary Laser System | 3060/3070/3080 | F·STD | 24 | 24 | 5/10/15/20 | 11/6 | 23 | 42 | 369 | 三筒并一击；×1.50 → 36；近距 alpha、热账重 |
| ADV⚠³ | ER Trinary Laser System | 3060/3070/3080 | F·STD | 24 | 32 | 8/15/22/30 | 11/6 | 27 | 54 | 489 | −1；×1.50 → 36；**每回合可射**（异于充能锁的 Hyper）|
| EXP | Large Hyper Laser | 3045/3050/3055 | G·EXP | 25 | 25 | 12/24/36/50 | 8/6 | 20.5 | 43.5 | 620 | 极距；−1、航空满 AV；**充能一回合 → 隔回合发射**；爆炸物（暴击殉爆 25）|

**家族 WSC 速评**（期望伤害 = `dmg`，Resonance / −1 命中 修正见"机制"列）：
- **Binary / ER Binary（16 伤）**：t/dmg **0.94 / 1.06**，Resonance 触发 ×1.30 → **0.72 / 0.81**（每 "标装" 开火算一轮折扣）。正常弹性 Improve 线下沿；
- **Trinary / ER Trinary（24 伤）**：t/dmg **0.96 / 1.13**，Resonance ×1.50 → **0.64 / 0.75**。ER Trinary **每回合可射** 是关键 — 把它跟 Hyper Laser 对等看毫无意义。
- **Hyper Laser（当前隔回合 12.5/回合）**：t/dmg **1.64**。**比大部分 Improve 件还差** → 2026-11-07 重设计的核心动因。新机制每回合 28 伤 / HSM 配 2t/2c → t/dmg ≈ **0.58**（WSC_t 20.5 + HSM 2 = 22.5 / 28 = 0.80 单武器口径；跟 ER Trinary 新持平）。

**⚠³** `Binary / Trinary / ER Binary / ER Trinary` 全部挂在 Advance 带（3060），但 Hyper（同族顶点）在 Experimental（3045），中间断档。tier 从没正式拍定过。

### 📐 **2026-11-08 架构重构决议(代码未实施,supersedes 2026-11-07)**

> 2026-11-08 架构重构:原"异种集束激光系"(含 Binary/Trinary/ER 变体/Hyper 5 件)**拆分为两条独立技术线**:
> - **集束激光系**:Binary + Trinary **base 剥离 Resonance**,配 **3 可选模组(RM / LEM / LEC)** 构成模组化 Advance tier 中距多功能平台
> - **高能激光系**:Hyper Laser 独立出来作为 Experimental tier 超远距狙击件,**必需 HSM + C-2500 Combat Computer Core 两件支援才能开火**
>
> **supersedes 2026-11-07 决议**(PLM 删除;ACJ 从未实施,废弃;HSM 保留;Hyper Laser 机制调整)
> **删除**:ER Binary Laser System / ER Trinary Laser System(长距职能并入 Hyper Laser)
> **搁置**:Hyper PPC(未来重定位为 Heavy PPC 的超重机甲恐龙化演进,非 Experimental Hyper 系)

#### 架构对照表

| 维度 | 2026-11-07 决议 | **2026-11-08 新决议** |
|---|---|---|
| Binary/Trinary base | 含 Resonance ×1.3/1.5 内建 | **Resonance 剥离,base 中性平台** |
| ER Binary/ER Trinary | 保留 | **删除,职能并入 Hyper Laser** |
| 集束激光可选模组 | PLM(pulse 精度)+ ACJ(热管理) 互斥 2 件 | **RM(共振)+ LEM(破甲)+ LEC(电容) 3 件** |
| 模组互斥规则 | PLM ⊥ ACJ | **RM ⊥ LEM(光学二选一);LEC 跟 RM/LEM 均兼容** |
| PLM | 2t/2c −2 命中 ×0.8 热 | **删除**(被 LEC 替代) |
| ACJ | 2t/2c 消 Resonance 热 + 缓冲 | **废弃**(被 LEM 替代) |
| Hyper Laser 机制 | 28 伤每回合 + 2d6 失控 + HSM 可选 | **保留** + **必需 HSM + C-2500** + 精度加成(Long −2 / Extreme −3) |
| HSM | Hyper 可选组件 | **保留,升为必需** |
| Hyper PPC | 跟 Hyper Laser 同步重设计 | **搁置**(未来 → Heavy PPC 超重演进,非 Experimental) |

---

### 一、集束激光系 — Base 武器 (2 件)

剥离 Resonance 后,Binary 和 Trinary 是**无偏好的中性平台**,对所有装甲平等输出。玩家通过挂不同模组定义 build 哲学。

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| ADV | Binary Laser System | 3060/3070/3080 | F·STD | 16 | 16 | 5/10/15/20 | 7/4 | 15 | 28 | 246 | 双筒并一击;**无 Resonance**(base 中性);base BV 不含模组加成 |
| ADV | Trinary Laser System | 3060/3070/3080 | F·STD | 24 | 24 | 5/10/15/20 | 11/6 | 23 | 42 | 369 | 三筒并一击;**无 Resonance**(base 中性) |

---

### 二、集束激光系 — 3 可选模组 (全 2t/2c,Advance,TR F,SL STANDARD,3060/3070/3080)

#### 2.1 Resonance Module (RM)

| 属性 | 草案 |
|---|---|
| 内部名 | `OSLaserResonanceModule` |
| 吨位 / Crit | **2 吨 / 2 crit** |
| 兼容 | Binary / Trinary(每武器配 1 个) |
| 跟 LEM | **互斥**(都改武器光学 configuration) |
| 跟 LEC | 兼容 |
| 装填约束 | 跟对应武器**同躯干部位** |
| 效果 ① vs 标装 | Binary **×1.30** / Trinary **×1.50** 伤害 |
| 效果 ② vs 特种装甲 | **无 bonus**(Resonance 对非标装失效) |
| 代价 | **Resonance 触发回合额外热** Binary **+2** / Trinary **+4** |

**叙事**:启用镜筒间的相干共振耦合调节器,对标装硬的"频率共振穿透"bonus。先进装甲的层状复合或散射结构打破了共振条件,bonus 失效。

#### 2.2 Laser Energy Modulator (LEM)

| 属性 | 草案 |
|---|---|
| 内部名 | `OSLaserEnergyModulator` |
| 吨位 / Crit | **2 吨 / 2 crit** |
| 兼容 | Binary / Trinary |
| 跟 RM | **互斥**(光学二选一) |
| 跟 LEC | 兼容 |
| 装填约束 | 跟对应武器**同躯干部位** |
| 效果 (核心) | **Armor Reconfiguration**:攻击时**将目标装甲视为 Standard Armor 做伤害计算**,绕过 Reflective / Reactive(激光部分)/ Hardened / Ferro-Lamellor / Ballistic-Reinforced / OS 变体(HFL / HHFL)/ Energy Absorption 的**伤害削减属性** |
| 不绕过 | Stealth / Null Sig / Void Sig / Chameleon LPS(to-hit 修正不是伤害削减) |
| BV 修正 | 挂武器 BV **×1.15** |

**叙事**:深度介入激光的 photonic 结构,频率调制让光束穿透特种装甲的反射/吸收/吸能机制。tech lineage 参照 canon Re-Engineered Laser,但是以**模组形式挂在集束激光系**上。

**对装甲场景快表**(Binary 为例,16 dmg):

| 目标装甲 | 裸 Binary | Binary + LEM | LEM 收益 |
|---|---|---|---|
| Standard | 16 | 16 | 0% |
| Reflective (激光 ×0.5) | 8 | **16** | **+100%** |
| Hardened (×0.5) | 8 | **16** | **+100%** |
| Ferro-Lamellor (×0.8) | 12.8 | **16** | **+25%** |
| OS HHFL (×0.4) | 6.4 | **16** | **+150%** |
| OS Energy Absorption | ~8 | **16** | **+100%** |

#### 2.3 Laser Energy Capacitor (LEC)

| 属性 | 草案 |
|---|---|
| 内部名 | `OSLaserEnergyCapacitor` |
| 吨位 / Crit | **2 吨 / 2 crit** |
| 兼容 | Binary / Trinary |
| 跟 RM | 兼容(独立电容系统,跟光学无关) |
| 跟 LEM | 兼容 |
| 装填约束 | 跟对应武器**同躯干部位** |
| 充能机制 | 开火前 1 回合充能(占 crit 不允许开火),充能完成后下次开火 **+5 dmg / +5 heat** |
| 殉爆敏感 | 充能状态下被爆头 → **5 伤 + 2d6 crit 投掷**(类 canon PPC Capacitor 殉爆) |

**叙事**:大型电容器组,充能 1 回合储存额外能量,下次开火 pump 额外 5 dmg。canon PPC Capacitor 的激光系对应件。玩家节奏控制 — 预判时机充能,避开爆头回合。

---

### 三、集束激光系 — 模组兼容矩阵

| 配置 | RM | LEM | LEC | Binary 期望输出 vs std | Binary 期望输出 vs 特种(HHFL) | 定位 |
|---|:---:|:---:|:---:|---|---|---|
| 裸装 | ✗ | ✗ | ✗ | 16 (无 bonus) | 6.4 (被 ×0.4 削减) | 中性平台,无 build commitment |
| +RM | ✓ | ✗ | ✗ | **21** (×1.3) | 6.4 (削减照常) | **反标装专家** |
| +LEM | ✗ | ✓ | ✗ | 16 | **16** (绕过削减) | **反精英装甲专家** |
| +LEC | ✗ | ✗ | ✓ | 21 burst (充能后 +5) | 11.4 burst (6.4+5) | **节奏炸点手** |
| +RM +LEC | ✓ | ✗ | ✓ | **26 burst** (×1.3 + 5) | 6.4 burst / 11.4 burst | 反标装 + 爆发 |
| +LEM +LEC | ✗ | ✓ | ✓ | 21 burst (16 + 5) | **21 burst** (16 + 5) | 反精英 + 爆发 |
| +RM +LEM | — | — | — | **非法**(光学互斥) | — | — |

**构型意义**:3 模组的 2 × 3 兼容矩阵给玩家 5 种 build 哲学(含裸装)。每种有独立 meta 场景。

---

### 四、高能激光系 — Hyper Laser 独立出来

#### 4.1 主武器规格

| 属性 | 草案 |
|---|---|
| 内部名 | `OSHyperLaserLarge`(当前已存在) |
| tier | Experimental |
| 技术等级 | Advance+ / TR G / SL EXPERIMENTAL |
| Intro | 3045/3050/3055(当前) |
| dmg / heat | **28 / 32**(前 2 回合固定),**32 + 1d6**(第 3 回合起) |
| range | **12/24/36/50**(S/M/L/E) |
| 精度加成(新) | **Short 0 / Medium 0 / Long −2 / Extreme −3**(自己有,**base 加成**,独立于外接 Energy FCM) |
| 稳定/失控 | 前 2 回合完全稳定;第 3 回合起 **2d6 ≥ 8 失控**(42%)→ 开火失败 + **28 内构 + 殉爆风险**。不开火回合 → 稳定计数器**归零** |
| 失控上限 | 无强制爆炸(Crit 投入本身就是平衡器) |
| 爆炸物殉爆 | 28(跟新伤害同步) |
| t·c | **8t / 6c** |
| BV | **~900**(base,不含必需组件) |

#### 4.2 必需组件(两件缺一不可 → 武器硬 lockout 无法开火)

| 组件 | 职责 | 吨位 / Crit | 备注 |
|---|---|---|---|
| **HSM**(Hyper Stabilization Module)| 稳定延长 2→3 回合 + 失控门槛 ≥8→≥9 + 过热内构 28→15 | 2t / 2c | 跟 Hyper 武器同躯干部位 |
| **C-2500 Combat Computer Core** | 火控核心,见 §9.2 | 1t / 2c | 整机唯一,挂在躯干 |

**系统工程总代价**:Hyper Laser 8t/6c + HSM 2t/2c + C-2500 1t/2c = **11t / 10c**(不含必需的 DHS 散热池)。

#### 4.3 可选组件(非必需,玩家按需装)

| 组件 | 职责 | 吨位 / Crit | 备注 |
|---|---|---|---|
| CCS Energy FCM Module | 额外 −1 命中 + Aimed Shot 能力(§9.2) | ~1t / 1c(按武器吨位 ÷ 10) | 专用模块,挂在 C-2500 核心上 |
| 额外 DHS | 处理 32-38 热的持续开火 | 1t/3c 每 2 热 | 装 10 DHS engine-free 后外接 ~3-4 DHS |

装满可选 → **12t / 11c** 系统工程,BV **~1060-1080**。

#### 4.4 精度加成数学

Hyper Laser 的 range-based 精度加成等效于"把远距的 range 罚压缩到跟近距一样":

| Range Bracket | 标准武器 命中罚 | **Hyper Laser 加成** | **净 to-hit 修正** | 等效 |
|---|:---:|:---:|:---:|---|
| Short (0-12) | 0 | 0 | **0** | 等效短距 |
| Medium (13-24) | +2 | 0 | **+2** | 等效中距 |
| Long (25-36) | +4 | **−2** | **+2** | **等效中距**(命中率 flat) |
| Extreme (37-50) | +6 | **−3** | **+3** | **等效长距**(比普通长距命中率高) |

装 Energy FCM 后再 −1 → Long 净 **+1**(等效短距)/ Extreme 净 **+2**(等效中距),**全文档唯一能在 50 hex 保持短中距命中率的武器**。

#### 4.5 WSC 分析

| 配置 | WSC_t | 期望伤 / turn | t/dmg | 备注 |
|---|---|---|---|---|
| Hyper Laser 本体 | 8 + 16(32 热 / 2)= **24** | 28 | **0.86** | base |
| + HSM | 24 + 2 = **26** | 28(稳定性延长至 3 turn) | **0.93** | 必需 |
| + C-2500 核心 | 26 + 1 = **27** | 28 | **0.96** | 必需 |
| 完整必需系统 | **27 WSC_t** | 28 | **0.96** | 比 ER Trinary 旧的 27/24 = **1.13** 要省 |

跟 2026-11-07 Hyper Laser 对比:旧的 t/dmg 1.64 → 新的 0.96 — **效率翻倍**,但以 11t 系统工程为门槛。

---

### 五、删除/迁移清单(代码落地时执行)

| 件 | 操作 | 存档兼容 |
|---|---|---|
| `OSERBinaryLaserSystem` | **删除** | `addLookupName("ER Binary Laser System")` 保留,load 时转向 null + warn |
| `OSERTrinaryLaserSystem` | **删除** | 同上 |
| 原 PLM 设计(2026-11-07) | **废弃**(未实施,无代码) | — |
| 原 ACJ 设计(2026-11-07) | **废弃**(未实施,无代码) | — |
| Binary/Trinary 的 Resonance 内建 | **剥离**(改为由 RM 模组触发) | handler 改造 |
| HSM(2026-11-07 设计) | **保留**,升为 Hyper Laser 必需组件 | 无变 |
| Hyper Laser `−1` 内建命中 | **删除**,改为 range-based 精度加成(Long −2 / Extreme −3) | handler 改造 |
| Hyper PPC(`OSHyperPPC`) | **本轮不动**(未来 → Heavy PPC 超重演进,非 Experimental) | — |

---

### 六、实施路线(代码批次规划)

**预估复杂度:中-高**。推荐分 5 阶段:

- **Phase 1** — 新 3 模组 MiscType + flag(`F_OS_LASER_RESONANCE` / `F_OS_LASER_EM_MODULATOR` / `F_OS_LASER_ENERGY_CAPACITOR`),装备入库 + mount 约束
- **Phase 2** — Binary/Trinary handler 剥离 Resonance(改为 mount-based 条件触发,检查武器挂 RM 才给 ×1.30/1.50)
- **Phase 3** — LEM handler(新 `OSBinaryLEMHandler` / `OSTrinaryLEMHandler`,绕过装甲减伤)+ LEC handler(充能态,参考 PPC Capacitor 现有 handler)
- **Phase 4** — Hyper Laser 改造:删 `−1` 内建,加 range-based 精度 block 到 `ComputeToHit`,必需组件校验加入 `TestMek`
- **Phase 5** — 删 ER Binary/ER Trinary 源文件 + lookup 兼容 + 编译 + MML 构型验证 + playtest 基准

**预估总工:5-7 个工作会话**,分散在数批次。本次只落**文档**,代码留后批。

---

## 能量 — 激光主阶梯（42 件：Standard / Improve / Enhanced 变体 + 遗留 Advance）

每个尺寸 14 件：base / ER / Heavy ×（STD + IMP）+ Improve Pulse / ER Pulse / Heavy Pulse + Enhanced ER + Enhanced ER Pulse + 挂在 Advance 日期的"遗留"件（ER Heavy / ER Pulse / Heavy Pulse，⚠²）。

> **机制速记**：Heavy 自带 **+1 命中惩罚**（Improve Heavy 工程消除；ER Heavy 保留）；Pulse 自带 **−2 命中**；Enhanced ER 内建 **−1 命中**；Enhanced ER Pulse 推到 **−3 命中**。
>
> **IS 复刻** = base Laser / ER Laser 的数值与 IS 同名武器完全一致，差别仅 TechBase = OUTER_SPHERE。

### 大型激光（14 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| STD | Large Laser | 2805/2820/2840 | E·INT | 8 | 8 | 5/10/15/20 | 5/2 | 9 | 14 | 123 | IS 复刻 |
| STD | ER Large Laser | 2805/2820/2840 | E·STD | 9 | 11 | 7/14/19/28 | 5/3 | 10.5 | 19.5 | 170 | 比 IS ER 大激光 +1 伤、更远 |
| STD | Heavy Large Laser | 2805/2820/2840 | E·STD | 16 | 18 | 5/10/15/20 | 4/3 | 13 | 30 | 244 | 大型最佳吨伤；**+1 命中惩罚**，近战型 |
| IMP | Improve Large Laser | 2900/2930/2960 | E·STD | 9 | 8 | 5/10/15/20 | 4/1 | 8 | 13 | 135 | +1 伤同热，**−1 吨 −1 crit** |
| IMP | Improve ER Large Laser | 2900/2930/2960 | E·STD | 10 | 12 | 8/15/22/30 | 4/1 | 10 | 19 | 230 | 推射程至 30；4t / 1crit 奇瘦 |
| IMP | Improve Heavy Large Laser | 2900/2930/2960 | E·STD | 16 | 18 | 5/10/15/20 | 4/3 | 13 | 30 | 298 | 工程消 +1 惩罚，满伤正常精度 |
| IMP | Improve Large Pulse Laser | 2900/2930/2960 | E·STD | 9 | 10 | 4/8/12/16 | 6/2 | 11 | 17 | 130 | 全 −2 脉冲 |
| IMP | Improve ER Large Pulse Laser | 2900/2930/2960 | E·STD | 11 | 14 | 6/14/22/30 | 6.5/4 | 13.5 | 25 | 240 | 保全 −2 脉冲 + ER 距离 |
| IMP | Improve Heavy Large Pulse Laser | 2900/2930/2960 | **F·STD** | 13 | 14 | 5/10/15/20 | 6.5/3 | 13.5 | 24 | 240 | −2 命中射程拉回 |
| ENH | Enhanced ER Large Laser | 3000/3025/3050 | F·STD | 11 | 14 | 8/15/22/30 | 4.5/3 | 11.5 | 24 | 260 | 内 −1 命中；1 伤换精度 + 满远程 AV |
| ENH | Enhanced ER Large Pulse Laser | 3000/3025/3050 | **G**·STD | 14 | 18 | 6/14/22/30 | 6/4 | 15 | 31 | 350 | **−3 命中**；线内最准大脉冲（TR 独 G）|
| ADV | ER Heavy Large Laser | 3060/3070/3080 | F·STD | 16 | 22 | 7/14/19/28 | 5/3 | 16 | 36 | 245 | Heavy 推 ER；保 +1 惩罚；**设计 ≈ IMP** |
| ADV | ER Large Pulse Laser | 3060/3070/3080 | F·STD | 10 | 14 | 6/12/17/24 | 6.5/3 | 13.5 | 24 | 220 | −1；1 点精度换 1 档距；**设计 ≈ IMP** |
| ADV | Heavy Large Pulse Laser | 3060/3070/3080 | F·STD | 12 | 14 | 3/6/9/12 | 6/2 | 13 | 23 | 180 | Heavy + Pulse −2，近战刀；**设计 ≈ IMP** |

**大型激光家族 t/dmg**（期望 dmg 对基础件 = `dmg`；Heavy +1 惩罚 ×0.87；Pulse −2 ×1.40；Enhanced ER −1 ×1.20；Enhanced ER Pulse −3 ×1.55）：
- **最省吨**：Improve ER Large Laser **1.00**、Enhanced ER Large Pulse **0.69**（−3 调整后）、Improve ER Large Pulse **0.88**（−2 调整后）、Improve Large Laser **0.89**
- **偏贵**：Heavy Large Laser **1.44**（原始 0.81 ×1.15 ≈ 0.94；但 WSC_c 30 跟 6 crit 侧的压力对等差）、ER Large Pulse **1.35**（−1 调整后 1.12，仍属"设计 ≈ IMP"的遗留 ADV 列,下调候选）
- **BV vs WSC 偏差警告**：Enhanced ER Large Pulse **BV 350 / WSC_t 15** = 23.3 BV/WSC_t，是家族最高；对标 Improve ER Large Pulse **240 / 13.5 = 17.8** —— 相差 5.5 BV/WSC_t 大致符合 −3 命中加成的 +40% 期望收益（17.8 × 1.4 ≈ 25）。合理。

### 中型激光（14 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| STD | Medium Laser | 2805/2820/2840 | E·INT | 5 | 3 | 3/6/9/12 | 1/1 | 2.5 | 5.5 | 46 | IS 复刻 |
| STD | ER Medium Laser | 2805/2820/2840 | E·STD | 6 | 5 | 4/8/12/16 | 1/1 | 3.5 | 8.5 | 70 | 比 IS ER 中激光 +1 伤 |
| STD | Heavy Medium Laser | 2805/2820/2840 | E·STD | 10 | 7 | 3/6/9/12 | 1/2 | 4.5 | 12.5 | 76 | 最佳中型吨伤；**+1 惩罚** |
| IMP | Improve Medium Laser | 2900/2930/2960 | E·STD | 6 | 3 | 3/6/9/12 | 1/1 | 2.5 | 5.5 | 50 | +1 伤同吨 |
| IMP | Improve ER Medium Laser | 2900/2930/2960 | E·STD | 6 | 5 | 5/10/15/20 | 1/1 | 3.5 | 8.5 | 88 | 推射程 |
| IMP | Improve Heavy Medium Laser | 2900/2930/2960 | E·STD | 10 | 7 | 3/6/9/12 | 1/2 | 4.5 | 12.5 | 103 | 消 +1 惩罚 |
| IMP | Improve Medium Pulse Laser | 2900/2930/2960 | E·STD | 6 | 4 | 3/5/7/10 | 2/1 | 4 | 7 | 53 | 全 −2 脉冲 |
| IMP | Improve ER Medium Pulse Laser | 2900/2930/2960 | E·STD | 8 | 8 | 4/8/12/16 | 2/2 | 6 | 14 | 158 | 全 −2 脉冲 + ER |
| IMP | Improve Heavy Medium Pulse Laser | 2900/2930/2960 | **F·STD** | 8 | 5 | 3/6/9/12 | 1.5/2 | 4 | 9.5 | 100 | −2 脉冲射程拉回 |
| ENH | Enhanced ER Medium Laser | 3000/3025/3050 | F·STD | 8 | 6 | 5/10/15/20 | 1/1 | 4 | 10 | 145 | 内 −1；线内最硬中激光 |
| ENH | Enhanced ER Medium Pulse Laser | 3000/3025/3050 | **G**·STD | 10 | 12 | 4/8/12/16 | 2/2 | 8 | 20 | 240 | **−3 命中**；线内最准中激光 |
| ADV | ER Heavy Medium Laser | 3060/3070/3080 | F·STD | 10 | 12 | 4/8/12/16 | 2/2 | 8 | 20 | 105 | Heavy 推 ER 保 +1；**设计 ≈ IMP** |
| ADV | ER Medium Pulse Laser | 3060/3070/3080 | F·STD | 7 | 6 | 4/7/11/14 | 2/2 | 5 | 11 | 130 | −1，1 点精度换距；**设计 ≈ IMP** |
| ADV | Heavy Medium Pulse Laser | 3060/3070/3080 | F·STD | 7 | 5 | 2/4/5/8 | 1.5/2 | 4 | 9.5 | 75 | −2 脉冲极短距，点射件；**设计 ≈ IMP** |

**中型激光家族 t/dmg**：
- **最省**：Improve Medium Laser **0.42**（Standard tier 的中型一等公民）、Improve Heavy Medium Laser **0.45**（×1.15 惩罚调整后 ≈ 0.52）、Enhanced ER Medium Laser **0.42**（×1.2 调整后 ≈ 0.42）
- **偏贵**：ER Heavy Medium Laser **0.80**（×1.15 惩罚调整后 0.92），"设计 ≈ IMP"遗留；Improve ER Medium Pulse **0.75**（−2 调整 0.54，合理）
- **异常**：ER Heavy Medium Laser **BV 仅 105 对 Improve Heavy 103** — 跟同吨 WSC_t 相同、同 dmg 10，但 ER Heavy 多了 1 档射程，**BV 几乎没加价**。属 BV 低估候选（或"设计 ≈ IMP"本就该降吨）。

### 小型激光（14 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| STD | Small Laser | 2805/2820/2840 | E·INT | 3 | 1 | 1/2/3/4 | 0.5/1 | 1 | 2.5 | 9 | IS 复刻；**独有 `F_NO_FIRES` 旗**（其他尺寸无，待核）|
| STD | ER Small Laser | 2805/2820/2840 | E·STD | 4 | 2 | 2/4/5/8 | 0.5/1 | 1.5 | 4 | 22 | 比 IS ER 小激光更伤更远 |
| STD | Heavy Small Laser | 2805/2820/2840 | E·STD | 6 | 3 | 1/2/3/4 | 0.5/1 | 2 | 5.5 | 15 | 最佳小型吨伤；**+1 惩罚** |
| IMP | Improve Small Laser | 2900/2930/2960 | E·STD | 4 | 1 | 1/2/3/4 | 0.5/1 | 1 | 2.5 | 10 | +1 伤 |
| IMP | Improve ER Small Laser | 2900/2930/2960 | E·STD | 5 | 2 | 2/4/6/8 | 0.5/1 | 1.5 | 4 | 28 | +1 伤 |
| IMP | Improve Heavy Small Laser | 2900/2930/2960 | E·STD | 6 | 3 | 1/2/3/4 | 0.5/1 | 2 | 5.5 | 19 | 消 +1 惩罚 |
| IMP | Improve Small Pulse Laser | 2900/2930/2960 | E·STD | 3 | 2 | 2/3/5/7 | 1/1 | 2 | 4 | 17 | 全 −2 脉冲 |
| IMP | Improve ER Small Pulse Laser | 2900/2930/2960 | E·STD | 4 | 3 | 3/5/7/10 | 1/1 | 2.5 | 5.5 | 62 | 全 −2 脉冲 + ER |
| IMP | Improve Heavy Small Pulse Laser | 2900/2930/2960 | **F·STD** | 5 | 3 | 1/2/3/4 | 0.5/1 | 2 | 5.5 | 30 | −2 脉冲点射 |
| ENH | Enhanced ER Small Laser | 3000/3025/3050 | F·STD | 5 | 3 | 3/5/8/10 | 0.5/1 | 2 | 5.5 | 50 | 内 −1；线内最远最准小激光 |
| ENH | Enhanced ER Small Pulse Laser | 3000/3025/3050 | **G**·STD | 5 | 4 | 3/5/7/10 | 1/1 | 3 | 7 | 90 | **−3 命中** |
| ADV | ER Heavy Small Laser | 3060/3070/3080 | F·STD | 6 | 4 | 2/4/5/8 | 1.5/1 | 3.5 | 7 | 26 | Heavy 推 ER 保 +1；**设计 ≈ IMP** |
| ADV | ER Small Pulse Laser | 3060/3070/3080 | F·STD | 3 | 3 | 2/4/6/8 | 1/1 | 2.5 | 5.5 | 38 | −1，1 点精度换距；**设计 ≈ IMP** |
| ADV | Heavy Small Pulse Laser | 3060/3070/3080 | F·STD | 4 | 3 | 1/2/3/4 | 0.5/1 | 2 | 5.5 | 22 | −2 脉冲点射；**设计 ≈ IMP** |

**小型激光家族 t/dmg**：小型激光 t·c 都已压到底（0.5t/1c 为主），WSC 主要被 heat 推高。t/dmg 已普遍 **0.25–0.50**，是武器中最吨效的一档。主要价值是**点射填空**（critseeker / 反步兵 / 过热耗尽机的末端火力），不作为主输出。**ER Small Pulse Laser BV 38 / dmg 3** 偏贵：WSC_t 2.5 / 3 = 0.83，跟 Improve ER Small Pulse 2.5/4 = 0.63 相比 BV-WSC 比劣势，候选 BV 下调。

**激光主阶梯小计 42 件**：STD 9 · IMP 15 · IMP 3（原 IMP⚠¹） · ENH 6 · ADV 9（原 ADV⚠²）。SL 侧已在 2026-11-07 批次全部落回 STANDARD；intro-date / TR 侧的设计阶梯归属仍见 tier 列。

**⚠¹** `Improve Heavy Pulse (S/M/L)`：intro 日期符合 Improve（2900–2960），但 `SimpleTechLevel` 旧为 **ADVANCED**（其他 10 件 Improve-系均 STANDARD）→ **已落地（2026-11-07）**：SL 降 STANDARD。TR 本轮不动，intro-date 也不动，仅 SL 更新。

**⚠²** `ER Heavy (S/M/L) · ER Pulse 无前缀 (S/M/L) · Heavy Pulse 无前缀 (S/M/L)`：intro 挂在 Advance 带（3060–3080），但设计概念全部是"两件 Standard-tier 工程的组合"（Heavy+ER / Pulse+ER / Heavy+Pulse），应当是 Improve-tier → **SL 已落地（2026-11-07）降 STANDARD**；TR 本轮不动，intro-date 也不动，仅 SL 更新（intro/TR 清理仍待后续批次）。

**数值订正 vs 旧版文档**：旧版把 Enhanced ER Large Laser 的 crit 标 4，代码实际 **3**；OSImproveERLaserLarge 的 S/M/L/E 旧版标 7/14/19/28、代码实际 **8/15/22/30**，吨 4t（非 4.5t）、crit 1（非 3）。旧版若干脉冲行缺 ext 列。

## 能量 — PPC（15 件）

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E (min) | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| STD | PPC | 2805/2820/2840 | E·INT | 10 | 10 | (min3) 6/12/18/24 | 7/3 | 12 | 18 | 176 | IS 复刻 |
| STD | ER PPC | 2805/2820/2840 | E·STD | 10 | 15 | 7/14/23/34 | 7/3 | 14.5 | 25.5 | 229 | 比 IS ER PPC 多一档射程（34 ext）|
| STD | Light PPC | 2805/2820/2840 | E·STD | 7 | 7 | (min3) 6/12/18/24 | 3/2 | 6.5 | 12.5 | 125 | 比正史 Light PPC 更伤（canon=5）|
| STD | Heavy PPC | 2805/2820/2840 | E·STD | 18 | 18 | (min3) 6/12/18/24 | 8/4 | 17 | 31 | 380 | 比标准 PPC 更重击 |
| STD | Snub-Nose PPC | 2805/2820/2840 | E·STD | var⁴ | 10 | 9/13/15/22 | 6/2 | 11 | 17 | 180 | 伤随距降；无 min；爆炸物 |
| IMP | Improve PPC | 2900/2930/2960 | E·STD | 10 | 10 | (min3) 6/12/18/24 | 6/2 | 11 | 17 | 195 | **−1 吨 −1 crit** vs base |
| IMP | Improve ER PPC | 2900/2930/2960 | E·STD | 12 | 14 | 7/14/23/30 | 7/3 | 14 | 24 | 290 | +2 伤 −1 热 vs base ER |
| ENH | Enhanced ER PPC | 3000/3025/3050 | F·STD | 15 | 15 | 7/14/23/30 | 6/2 | 13.5 | 24.5 | 412 | 无 min；比 Improve ER +3 伤 +1 热 −1 吨 −1 crit |
| ADV | ER Heavy PPC | 3060/3070/3080 | F·STD | 18 | 22 | 7/14/20/27 | 9/4 | 20 | 37 | 420 | 无 min；重击 + ER |
| ADV | Heavy Snub-Nose PPC | 3060/3070/3080 | F·STD | var⁵ | 15 | 9/12/14/21 | 8/4 | 15.5 | 26.5 | 265 | 重型近距 PPC；无 min；爆炸物 |
| ADV | Rotary Light PPC | 3060/3070/3080 | F·STD | 5×(1–5) | 2/发 | 6/12/18/24 | 7/5 | 12 | 20 | 280 | 前 3 发自冷；4–5 发抽 **RPPC 冷却舱**，无舱 4+ 过载 |
| ADV | Rotary Snub-Nose PPC | 3060/3070/3080 | F·STD | var⁶ ×(1–4) | 3/发 | 8/12/15/22 | 10/6 | 16 | 24 | 360 | 无 min；前 3 发自冷；第 4 发抽冷却舱 |
| ADV | PPC-X | 3060/3070/3080 | F·ADV | 2×6 束（≤12）| 10 | 7/10/13/18 | 6/3 | 11 | 18 | 205 | 分 6 束；命中梯度 **−1 短 / 0 中 / +1 远**；无 min；爆炸物 |
| ADV | Heavy PPC-X | 3060/3070/3080 | F·ADV | 4×6 束（≤24）| 15 | 7/10/13/18 | 8/4 | 15.5 | 26.5 | 350 | 同梯度；爆炸物 |
| EXP⏳ | Hyper PPC | 3045/3050/3055 | G·EXP | 30 | 30 | (min3) 12/24/36/50 | 10/7 | 25 | 52 | 824 | 内 −1；**充能一回合 → 隔回合发射**（隔回合 → **期望 15/回合**）。⏳ **待更新**：Hyper Laser 2026-11-07 重设计落地验证后同步，取消充能 + 每回合可射 + 1d6 额外热 + 2d6 失控 + HSM 兼容 |

**PPC 家族 t/dmg**（Rotary 走"最大 N 发连射 × 命中率"口径，PPC-X 按 6 束期望命中 ≈ 4 束 × 2 伤 = 8 伤算；Hyper 隔回合折半）：
- **最省**：Light PPC **0.93**（7 伤 / 6.5 WSC_t）、Improve PPC **1.10**（10 伤 / 11）、Enhanced ER PPC **0.90**（15 伤 / 13.5）
- **大件**：Heavy PPC **0.94**（18 / 17），ER Heavy PPC **1.11**（18 / 20，为 ER 距离付税）
- **当前 Hyper PPC 极弱**：**1.67**（15 期望 / 25 WSC_t，更差于 Hyper Laser 的 1.64）。2026-11-07 新机制下取消充能 + 每回合可射 → 30 伤 / 25 WSC_t ≈ **0.83** + HSM 配 2t/2c → **0.90** —— 新机制下 Hyper PPC 应比 ER Heavy PPC 略优，差 BV 400+ 的差额可以靠失控风险打平
- **BV vs WSC 偏差警告**：Enhanced ER PPC **BV 412 / WSC_t 13.5 = 30.5**，是 PPC 家族 BV/WSC 比率最高 — 对标无 min + 15 伤 + −1 命中（内建），BV 偏高符合"无 min 的直伤 PPC"溢价；但相比 ER Heavy PPC **BV 420 / WSC_t 20 = 21** 便宜性反而更强，**ER Heavy PPC 是 PPC 家族价格洼地**（一个 ADV tier 还比 ENH tier 价格低 9.5 BV/WSC_t）

**⁴ Snub-Nose PPC**：10 短 / 8 中 / 6 长。
**⁵ Heavy Snub-Nose PPC**：15 短 / 9 中 / 6 长。
**⁶ Rotary Snub-Nose PPC**：8 短 / 6 中 / 4 长（每发；1–4 发模式）。

> PPC 线无 Enhanced-除 ER 一支外的"全阶梯"（无 Enhanced Snub / Enhanced Heavy / Enhanced Light），且无 Pulse PPC。可能是设计留白。

## 能量 — 等离子（5 件，全部弹药供弹）

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|---|--:|:--:|:--:|--:|--:|--:|---|
| STD | Plasma Rifle | 2805/2820/2840 | E·STD | 10 伤 + 2d6 目标热 | 8 | 6/12/18/24 | 5/2 | 11 | 16 | 250 | 聚焦弹；比 IS 等离子步枪更冷/轻/远 |
| STD | Plasma Cannon | 2805/2820/2840 | E·INT | 0 伤 + 2d6 目标热 | 6 | 6/12/18/24 | 4/2 | 8⁺ | 12⁺ | 190 | 面压制；对机甲不破甲只灌热；对软 3d6 集束 |
| EXP | Heavy Plasma Rifle | 3045/3050/3055 | G·EXP | 15 伤 + 2d6 目标热 | 12 | 5/10/15/20 | 7/3 | 15 | 23 | 315 | 破 10 点等离子上限；**+1 命中惩罚**，近距破甲 |
| EXP | Heavy Plasma Cannon | 3045/3050/3055 | G·EXP | 0 伤 + 3d6 目标热 | 10 | 6/12/18/24 | 6/3 | 12⁺ | 17⁺ | 290 | 更密面灌热 + 3d6 反软集束；**+1 惩罚** |
| EXP | EMP Plasma Accelerator | 3045/3050/3055 | G·EXP | 0 | 8 | 5/10/15/20 | 5/3 | 10⁺ | 16⁺ | 190 | 专职电子扰乱：只造 INTERFERENCE（永不完全停机）；开火方无自扰 |

**⁺** 仅灌热 / EMP 的 Plasma Cannon / Heavy Plasma Cannon / EMP Plasma Accelerator 按 `N_ammo=1`（而非 2）算 — 灌热每场耗弹比破甲弹低。

**等离子家族 t/dmg**（灌热件以"灌热 WSC"衡量，不适用常规 t/dmg）：
- **Plasma Rifle 10 伤 + 灌 2d6 热**：破甲侧 t/dmg = **1.10**（11/10），附带 7 期望灌热的次级威胁 → 等效 ≈ **0.85–0.90**。合理
- **Heavy Plasma Rifle 15 伤**：破甲侧 15/15 = **1.00**（×1.15 惩罚调整 1.15）；EXP tier 应更省，BV 315 偏高（WSC_t 15 × 20 = 300 预期 BV）
- **Plasma / Heavy Plasma Cannon** 以"期望灌热 / WSC_t"衡量：Plasma Cannon **7/8 = 0.88 热/t**，Heavy Plasma Cannon **10.5/12 = 0.88 热/t** — Heavy 的 3d6 升级没放大 WSC 代价,平衡合理
- **EMP Plasma Accelerator** 以"效果强度/WSC"衡量：INTERFERENCE 永不完全停机的"软扰"低于 ECM 的"完全断网"，10 WSC_t 的代价 + BV 190 看齐 Plasma Cannon — 代价合理

> **等离子线断档**：base 两件在 STD、Heavy 两件直接跳到 EXP（跳过 IMP 与 ENH 两级）。合理与否取决于设定 — 若 Heavy 等离子被视作"反应堆直流接驳的实验突破"则 EXP 合理；若视作工程演进则应落在 ENH。

## 能量 — 火焰器（4 件，能量供弹无弹药；Damage 烧甲 / Heat 灌热；Xd6 反步兵）

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| STD | ER Flamer | 2805/2820/2840 | E·STD | 2 | 3 | 3/6/9/12 | 1/1 | 2.5 | 5.5 | 18 | 远射型，远超 IS ER 火焰器（3/5/7）；2d6 反步兵 |
| STD | Heavy Flamer | 2805/2820/2840 | E·STD | 5 | 6 | 2/4/6/8 | 1.5/1 | 4.5 | 10 | 30 | 伤害型，全能量供弹（无燃料弹）；6d6 反步兵 |
| STD | ER Heavy Flamer | 2805/2820/2840 | E·STD | 5 | 7 | 3/6/9/12 | 2/1 | 5.5 | 11.5 | 45 | 最重 OS 火焰器；远 + 重伤；6d6 反步兵 |
| IMP | Improve Flamer | 2900/2930/2960 | E·STD | 2 | 2 | 1/2/3/4 | 0.5/1 | 1.5 | 4 | 6 | 效率型：半吨、点火热减半（标准 3）；4d6 反步兵 |

**火焰器家族**：定位反步兵/灌热，不走常规 t/dmg。关键指标是 t/热 — Improve Flamer 1.5 WSC_t 处理 2 灌热 + 4d6 反步兵，是最 ton-efficient 的；Heavy Flamer 5 伤 + 6d6 反步兵对软目标是最强硬件。

> **火焰器线**：所有强化变体（ER / Heavy / ER Heavy）都在 **STD 2805** 开局；只有 Improve Flamer 在 2900。意味着在 OS 时间线早期已经有 2d6/6d6 的大于 IS 火焰器选项 — tier 分布合理，但需核：ER Heavy Flamer dmg 5 / heat 7 在 2805 是不是太强。

---

## 弹道 — 自动炮家族（AC / LB-X / Ultra AC / Rotary AC / HVAC，43 件 + IS BF/RF 8 件）

### Standard AC + Improve AC（8 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| STD | AC/2 | 2805/2820/2840 | E·INT | 2 | 1 | 8/16/24/32 | 6/1 | 8.5 | 4.5 | 37 | IS 复刻；专用弹 `AC_STD_OS` |
| STD | AC/5 | 2805/2820/2840 | E·INT | 5 | 1 | 6/12/18/24 | 8/4 | 10.5 | 7.5 | 70 | IS 复刻 |
| STD | AC/10 | 2805/2820/2840 | E·INT | 10 | 3 | 5/10/15/20 | 12/6 | 15.5 | 12.5 | 123 | IS 复刻 |
| STD | AC/20 | 2805/2820/2840 | E·INT | 20 | 7 | 3/6/9/12 | 14/9 | 19.5 | 21.5 | 178 | IS 复刻 |
| IMP | Improve AC/2 | 2900/2930/2960 | E·STD | **3** | 1 | 8/16/24/32 | 5/1 | 7.5 | 4.5 | 56 | **+1 伤**，专用弹 `AC_IMP_OS` |
| IMP | Improve AC/5 | 2900/2930/2960 | E·STD | **6** | 1 | 6/12/18/24 | 7/3 | 9.5 | 6.5 | 84 | +1 伤 |
| IMP | Improve AC/10 | 2900/2930/2960 | E·STD | 10 | 2 | 5/10/15/20 | 10/6 | 13 | 11 | 123 | 同伤但 −1 热 −2 吨 |
| IMP | Improve AC/20 | 2900/2930/2960 | E·STD | 20 | 6 | 4/8/12/16 | 12/9 | 17 | 20 | 237 | 推 1 档距、−1 热 −2 吨 |

### LB-X（Standard / Improve / Ultra，双模实心弹/集束霰弹，11 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| STD | LB 2-X | 2805/2820/2840 | E·STD | 2 | 1 | 9/18/27/36 | 6/4 | 8.5 | 7.5 | 42 | IS 复刻 LB-X |
| STD | LB 5-X | 2805/2820/2840 | E·STD | 5 | 1 | 7/14/21/28 | 8/5 | 10.5 | 8.5 | 83 | 同上 |
| STD | LB 10-X | 2805/2820/2840 | E·STD | 10 | 2 | 6/12/18/24 | 11/5 | 14 | 10 | 148 | 同上 |
| STD | LB 20-X | 2805/2820/2840 | E·STD | 20 | 5 | 4/8/12/16 | 14/11 | 18.5 | 20.5 | 237 | 同上 |
| IMP | Improve LB 2-X | 2900/2930/2960 | E·STD | 2 | 1 | 9/18/27/36 | 4.5/2 | 7 | 5.5 | 42 | 更轻（4.5t vs 6t），4 crit 降至 2 |
| IMP | Improve LB 5-X | 2900/2930/2960 | E·STD | 5 | 1 | 7/14/21/28 | 6.5/4 | 9 | 7.5 | 83 | 更轻 |
| IMP | Improve LB 10-X | 2900/2930/2960 | E·STD | 10 | 2 | 6/12/18/24 | 10/5 | 13 | 10 | 148 | 更轻 |
| IMP | Improve LB 20-X | 2900/2930/2960 | E·STD | 20 | 5 | **5**/10/15/20 | 12/8 | 16.5 | 17.5 | 296 | 更轻 + 推 1 档短距 |
| EXP | Ultra LB 5-X | 3045/3050/3055 | **G·STD** | 5 | 1 | 8/16/24/32 | 6/4 | 8.5 | 7.5 | 150 | 双模 + Ultra 双发 + **OS LB-X 特种弹**（GAAM 制导 AP、Anti-Myomer 霰弹）|
| EXP | Ultra LB 10-X | 3045/3050/3055 | **G·STD** | 10 | 2 | 7/14/21/28 | 9/5 | 12 | 10 | 275 | 同上 |
| EXP | Ultra LB 20-X | 3045/3050/3055 | **G·STD** | 20 | 5 | 5/10/15/20 | 12/8 | 16.5 | 17.5 | 475 | 同上 |

### Ultra AC（Standard / Improve / Enhanced，OS 版**不卡壳**，12 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| STD | Ultra AC/2 | 2805/2820/2840 | E·STD | 2 | 1 | 8/17/25/33 | 7/3 | 9.5 | 6.5 | 56 | 单/Ultra 双发，无卡壳 |
| STD | Ultra AC/5 | 2805/2820/2840 | E·STD | 5 | 1 | 6/13/20/26 | 9/5 | 11.5 | 8.5 | 112 | 同上 |
| STD | Ultra AC/10 | 2805/2820/2840 | E·STD | 10 | 4 | 6/13/18/24 | 13/7 | 17 | 15 | 210 | 同上 |
| STD | Ultra AC/20 | 2805/2820/2840 | E·STD | 20 | 8 | 3/7/10/13 | 15/9 | 21 | 23 | 281 | 同上 |
| IMP | Improve Ultra AC/2 | 2900/2930/2960 | E·STD | 2 | 1 | 9/18/25/34 | 6/2 | 8.5 | 5.5 | 56 | 更轻更冷 |
| IMP | Improve Ultra AC/5 | 2900/2930/2960 | E·STD | 5 | 1 | 7/14/21/28 | 8/4 | 10.5 | 7.5 | 118 | 同上 |
| IMP | Improve Ultra AC/10 | 2900/2930/2960 | E·STD | 10 | 3 | 6/12/18/24 | 11/6 | 14.5 | 12.5 | 210 | 同上 |
| IMP | Improve Ultra AC/20 | 2900/2930/2960 | E·STD | 20 | 6 | 4/8/12/16 | 12/8 | 17 | 19 | 337 | 推射程 + 更轻更冷 |
| ENH | Enhanced Ultra AC/2 | 3000/3025/3050 | F·STD | 2 | 1 | 9/18/25/34 | 6/2 | 8.5 | 5.5 | 70 | 内建 **−1 命中** + Ultra 无卡壳 |
| ENH | Enhanced Ultra AC/5 | 3000/3025/3050 | F·STD | 5 | 1 | 8/16/24/32 | 7/4 | 9.5 | 7.5 | 150 | 内 −1，更长 |
| ENH | Enhanced Ultra AC/10 | 3000/3025/3050 | F·STD | 10 | 3 | 7/14/20/27 | 10/4 | 13.5 | 10.5 | 270 | 内 −1；精准/长/瘦 |
| ENH | Enhanced Ultra AC/20 | 3000/3025/3050 | F·STD | 20 | 6 | 5/9/13/17 | 12/8 | 17 | 19 | 400 | 内 −1 |

### Rotary AC（1–N 连发爆发，低骰卡壳，4 件）
> **Rotary WSC 口径**：heat 按**最大连发总热**算（而非 per-shot），因为回合内 N 发是设计意图使用场景。期望伤害按 **max dmg × 0.65**（cluster 表 size-N 期望 ≈ 65%）。

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| ⚠STD² | Rotary AC/2 | 2805/2820/2840 | F·STD | 2×(1–6) | 1/发 (max 6) | 7/14/21/28 | 7/3 | 12 | 14 | 185 | 持续火力堆集束；低骰卡壳；**TR F 与 intro 2805 Standard 带矛盾（TR 本轮不动，仍遗留）**|
| ⚠STD² | Rotary AC/5 | 2805/2820/2840 | F·STD | 5×(1–6) | 1/发 (max 6) | 6/12/18/24 | 10/6 | 15 | 17 | 295 | 同上 |
| ADV | Rotary AC/10 | 3060/3070/3080 | F·STD | 10×(1–5) | 3/发 (cap 15) | 5/10/15/20 | 14/8 | 23.5 | 32.5 | 410 | 同上；heat 封顶 15 |
| ADV | Rotary AC/20 | 3060/3070/3080 | F·STD | 20×(1–4) | 7/发 (cap 28) | 4/9/13/17 | 16/12 | 32 | 56 | 620 | 最重持续火力；heat 封顶 28 |

**Rotary 家族 t/dmg**（期望 max × 0.65）：
- **Rotary AC/2**：12 吨 WSC 买 7.8 期望伤 → **1.54 t/dmg**；跟 AC/5(IMP) 的 9.5/6 = **1.58** 并列、合理
- **Rotary AC/5**：15/19.5 = **0.77 t/dmg** — 最吨效的 Rotary；但**低骰卡壳风险 + 热账 6 不能全开**让"实战期望"低于纸面
- **Rotary AC/10**：23.5/32.5 = 32.5 WSC_t 买 32.5 期望... 实际 **0.72**，BV 410 / WSC_t 23.5 = 17.4 BV/t，合理
- **Rotary AC/20**：32/52 WSC = 买 52 期望伤 → **0.62 t/dmg**，看似最省；但 **32 吨 + 56 crit** 的 crit 账是**全文档最可怕**，几乎挤占一整个躯干部位（LT/RT 只有 12 crit）→ **只能上超重机**。建议注意 BV 620 是否反映 crit 税

### HVAC（长身管精密反装甲炮；**命中随距递进** 0/−1/−1/−2；航空满 AV；非殉爆；8 件）
专用弹 `HVAC_OS`（idx 139），非 `AC_STD_OS`（独立 enum）。

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| IMP | Improve HVAC/10 | 2900/2930/2960 | E·STD | 10 | 3 | 8/16/24/32 | 10/6 | 13.5 | 12.5 | 165 | 距离越远越准 |
| IMP | Improve HVAC/12 | 2900/2930/2960 | E·STD | 12 | 4 | 7/14/21/28 | 11/7 | 15 | 15 | 195 | 同 |
| IMP | Improve HVAC/14 | 2900/2930/2960 | E·STD | 14 | 5 | 7/13/20/27 | 12/8 | 16.5 | 17.5 | 225 | 同 |
| IMP | Improve HVAC/16 | 2900/2930/2960 | E·STD | 16 | 6 | 6/12/19/26 | 13/8 | 18 | 19 | 255 | 最重精密 HVAC |
| ENH | Enhanced HVAC/10 | 3000/3025/3050 | F·STD | 10 | 2 | 8/16/24/32 | 8/4 | 11 | 9 | 165 | 同精度，更轻更冷 |
| ENH | Enhanced HVAC/12 | 3000/3025/3050 | F·STD | 12 | 3 | 7/14/21/28 | 9/5 | 12.5 | 11.5 | 195 | 同 |
| ENH | Enhanced HVAC/14 | 3000/3025/3050 | F·STD | 14 | 4 | 7/13/20/27 | 10/6 | 14 | 14 | 225 | 同 |
| ENH | Enhanced HVAC/16 | 3000/3025/3050 | F·STD | 16 | 5 | 6/12/19/26 | 11/6 | 15.5 | 15.5 | 255 | Enhanced 减重减热 |

**HVAC 家族 t/dmg**（期望 dmg = `dmg`；长档 −1 命中加成 ×1.20；精度加成在远档 2 个 bracket 命中 +11% / +22% 分别）：
- **Enhanced HVAC/10**：11 WSC_t / 10 dmg = **1.10** → 距离加成后 **0.92**；最 ton-efficient HVAC 件
- **Enhanced HVAC/16**：15.5 / 16 = **0.97** → 距离加成 **0.81**；性价比最好的大口径 HVAC
- **Improve HVAC 系**：全家族加成后约 0.95–1.15，不如 Enhanced 省，符合 tier 差
- **AC 全家族对比**（WSC_t/dmg）：Improve AC/10 **1.30** vs Enhanced HVAC/10 **1.10** → 同伤 Enhanced HVAC 便宜 **1.8t**，还额外送精度递进 — HVAC 是**长距离反装甲的真·王者**
- **BV vs WSC**：Enhanced HVAC/10-16 **BV 跟 Improve 完全相同**但 WSC_t 平均低 2.5t。Enhanced HVAC 系 **BV 被低估**候选 — 建议每级升 20-25 BV 反映减重 + 精度溢价

### IS-fork AC 变体 — BF / RF（8 件，`autoCannons/innerSphere/`，IS tech base 不是 OS）
BF = Burst Fire（**长档 −1 命中**，软化 long bracket 的 −4 → −3，专用弹 `AC_BF`）；RF = Rapid Fire（**3 发爆发，cluster size 3，期望 2.0 命中**，**无 +1 卡壳**，专用弹 `AC_RF`）。有效伤 = canon AC 对应口径；BV / 吨 / crit 跟 canon AC 完全对齐。

| 类型 | 武器 | Intro | TR | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| BF | AC/2 BF | 2290/2300/2305 | C | 2 | 1 | 8/16/24/32 | 6/1 | 8.5 | 4.5 | 37 | long −1 命中 |
| BF | AC/5 BF | 2240/2250/2255 | C | 5 | 1 | 6/12/18/24 | 8/4 | 10.5 | 7.5 | 70 | 同上 |
| BF | AC/10 BF | 2443/2460/2465 | C | 10 | 3 | 5/10/15/20 | 12/7 | 15.5 | 13.5 | 123 | 同上 |
| BF | AC/20 BF | 2488/2500/2502 | C | 20 | 7 | 3/6/9/12 | 14/10 | 19.5 | 22.5 | 178 | 同上 |
| RF | AC/2 RF | 2290/2300/2305 | C | 1 ×3 | 0 | 8/16/24/32 | 6/1 | 8 | 3 | 37 | 3 发爆发（cluster-3，期望 2 中，有效伤 ≈ 2） |
| RF | AC/5 RF | 2240/2250/2255 | C | 3 ×3 | 0 | 6/12/18/24 | 8/4 | 10 | 6 | 70 | 同上 |
| RF | AC/10 RF | 2443/2460/2465 | C | 5 ×3 | 1 | 5/10/15/20 | 12/7 | 14.5 | 10.5 | 123 | 同上 |
| RF | AC/20 RF | 2488/2500/2502 | C | 10 ×3 | 2 | 3/6/9/12 | 14/10 | 17 | 15 | 178 | 同上 |

**BF / RF 家族 t/dmg**（设计 = **BV·吨·crit 完全等同 canon AC**，玩法差异化）：
- **BF 系**：WSC 跟 canon AC 完全一样（+0），长档 −1 让 long bracket 实际命中 **+11%** → 等效**便宜 10% 的 canon AC**。BV 跟 canon 一致是设计选择（§7 用户决议）
- **RF 系**：WSC 比 canon AC **略低**（AC/2 RF 8 vs AC/2 8.5 — 省 0.5t 热账；AC/10 RF 14.5 vs AC/10 15.5 — 省 1t），因为 RF 的"3 发 × dmg/2"把 heat 砍半
- **RF 实战陷阱**：BV 等于 canon AC 但 cluster-3 期望 **恰好 = 2 发 = canon 伤**；**没有卡壳** 意味着稳定输出高于 Rotary AC 的"低骰卡壳"。相比 Ultra AC 的双发 Ultra 模式（无卡壳），RF 的 **cluster-3 分散伤害**在打表面单位目标（不穿装甲）时实际收益更低 — 玩家味道件,不是 meta

**AC 段小计 43 OS + 8 IS 变体 = 51 件**：STD 15 · IMP 20 · ENH 4 · ADV 2 · EXP 3（原 ⚠EXP¹）· ⚠STD 2 + IS BF 4 · IS RF 4。

**⚠¹ Ultra LB-X 系** 3 件：intro 挂 3045 EXPERIMENTAL，TR G，等同 Hyper 级。概念"LB-X + Ultra 双发 + OS 特种弹"虽然叠两层机制，整体仍是产品化 Advance-tier 可量产的件；旧标 EXP + G 过头 → **SL 已落地（2026-11-07）降 STANDARD**。TR 本轮不动，intro-date 也不动，仅 SL 更新；intro / TR 层面的迁 3060 ADV + TR F 仍待后续批次。

**⚠² Rotary AC/2 和 /5** 2 件：intro 挂 2805（Standard 带），但 TR 却是 **F**（Standard 其他件都是 E），SL=STANDARD 跟 TR F 不匹配。概念"Rotary 机制"在 canon BT 是 Advance 级。本轮 TR 不动、SL 本就 STD → **TR 矛盾仍遗留**；仍建议后续 intro 迁 3060 ADV + 把 Rotary AC/2/5/10/20 四件统一在 Advance 带，或下调 TR 到 E 让 2805 自洽。

## 弹道 — Gauss 家族（14 件）

### Gauss 核心阶梯（Light / Standard / Heavy / Super-Heavy，9 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E (min) | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|---|--:|:--:|:--:|--:|--:|--:|---|
| ADV | Ultra-Light Gauss Rifle | 3060/3070/3080 | E·STD | 6 | 1 | (min3) 7/15/22/33 | 7/3 | 9.5 | 6.5 | 120 | 最轻 Gauss |
| STD | Light Gauss Rifle | 2805/2820/2840 | E·STD | 8 | 1 | (min3) 8/17/25/37 | 10/4 | 12.5 | 7.5 | 165 | 轻伤长射，瘦机用 |
| STD | Gauss Rifle | 2805/2820/2840 | E·STD | 15 | 1 | (min2) 7/15/22/33 | 12/6 | 14.5 | 9.5 | 320 | Clan 重量，比 IS Gauss 轻 3 吨 |
| IMP | Improve Gauss Rifle | 2900/2930/2960 | E·STD | 15 | 1 | (min2) 8/16/24/36 | 11/6 | 13.5 | 9.5 | 340 | 推射程 + −1 吨 |
| ENH | Enhanced Gauss Rifle | 3000/3025/3050 | F·STD | 15 | 1 | (min2) 9/18/27/40 | 10/5 | 12.5 | 8.5 | 370 | 线内最远最轻标准 Gauss |
| STD | Heavy Gauss Rifle | 2805/2820/2840 | E·STD | var⁴ | 2 | (min4) 6/13/20/30 | 16/10 | 19 | 15 | 360 | 攻城炮，伤随距衰减 25/20/10 |
| IMP | Improve Heavy Gauss Rifle | 2900/2930/2960 | E·STD | 22 | 3 | (min3) 7/13/20/30 | 18/10 | 21.5 | 16.5 | 430 | 平 22 伤**无衰减** |
| ENH | Enhanced Heavy Gauss Rifle | 3000/3025/3050 | F·STD | 25 | 4 | (min3) 7/14/21/32 | 17/9 | 21 | 17 | 510 | 平 25 伤无衰减 |
| EXP | Super-Heavy Gauss Rifle | 3045/3050/3055 | G·EXP | var⁵ | 5 | (min4) 6/13/20/30 | 22/14 | 26.5 | 23.5 | 582 | 线内最大单发 35/30/25；衰减攻城；超重底盘 |

**⁴ Heavy Gauss**：25 短 / 20 中 / 10 长。 **⁵ Super-Heavy Gauss**：35 短 / 30 中 / 25 长。

### Magshot / AP / LB-X Gauss（5 件）
| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E (min) | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| STD | Anti-Personnel Gauss | 2805/2820/2840 | E·STD | 3 (2d6 反步) | 1 | 3/6/9/12 | 0.5/1 | 3 | 4.5 | 21 | 专职反人员 Gauss；`F_BURST_FIRE` |
| STD | Magshot Gauss | 2805/2820/2840 | E·STD | 3 | 1 | 4/8/12/16 | 0.5/1 | 3 | 4.5 | 27 | 比正史 Magshot 更远 |
| STD | LB-X Gauss | 2805/2820/2840 | E·STD | 15 实心/霰弹 | 1 | (min2) 7/15/22/33 | 15/7 | 17.5 | 10.5 | 300 | Gauss 射程 + LB-X 双模 |
| IMP | Improve LB-X Gauss | 2900/2930/2960 | E·STD | 15 实心/霰弹 | 1 | (min2) 8/16/24/36 | 13/6 | 15.5 | 9.5 | 330 | 更轻更远 |
| ADV | Heavy LB-X Gauss | 3060/3070/3080 | F·STD | 20 实心/霰弹 | 2 | (min3) 6/13/20/30 | 19/11 | 22 | 16 | 420 | 重型双模 Gauss |

**Gauss 家族 t/dmg**（Heavy Gauss 用衰减平均：标准 Heavy 25/20/10 ≈ 18.3 平均；Super-Heavy 35/30/25 ≈ 30 平均）：
- **最吨效**：Ultra-Light Gauss **1.58**（9.5/6），性能垃圾但 crit 省；Improve Gauss **0.90**（13.5/15），**家族最优 BV/WSC**；Enhanced Gauss **0.83**（12.5/15），**全文档最吨效的直伤件**
- **大件**：Heavy Gauss **1.04**（19/18.3），Improve Heavy **0.98**，Enhanced Heavy **0.84**（21/25）— 越高 tier 越便宜,符合 §0.2 ladder 哲学
- **Super-Heavy**：**0.88**（26.5/30），Experimental tier 但不比 Enhanced Heavy 便宜太多 — 意味着**超重底盘约束**本身就是平衡机制
- **LB-X Gauss**：实心模式 WSC_t/dmg **1.17**（17.5/15）；比 Gauss Rifle 贵 3t 换"实心/霰弹双模"换得不值,BV 300 偏高 — **候选下调**（15 vs 320 跟 LB-X Gauss 15 vs 300 几乎同 BV,但 LB-X Gauss 多 3 吨/1 crit，没理由 BV 低）

---

## 弹道 — HAG 家族（5 件，Hyper-Assault Gauss，集束伤害）

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E (min) | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| STD | HAG/20 | 2805/2820/2840 | E·STD | 20 集束 | 3 | (min2) 9/17/25/33 | 7/6 | 10.5 | 12.5 | 280 | 集束 Gauss，比 Clan HAG 更冷更远 |
| STD | HAG/30 | 2805/2820/2840 | E·STD | 30 集束 | 5 | (min2) 9/17/25/33 | 11/8 | 15.5 | 17.5 | 420 | 同 |
| STD | HAG/40 | 2805/2820/2840 | E·STD | 40 集束 | 7 | (min2) 9/17/25/33 | 14/10 | 19.5 | 22.5 | 560 | 同；OS 新口径 |
| EXP | Heavy HAG/30 | 3045/3050/3055 | G·EXP | 30 集束 | 8 | (min2) 10/20/30/40 | 15/10 | 21 | 24 | 520 | **+2 集束骰**（更密）；30 格 ext |
| EXP | Heavy HAG/40 | 3045/3050/3055 | G·EXP | 40 集束 | 10 | (min2) 10/20/30/40 | 19/13 | 26 | 30 | 690 | +2 集束骰；最密最远 |

**HAG 家族 t/dmg**（期望 = rack × 0.6 集束；Heavy HAG +2 骰 → ×0.7）：
- **HAG/20**：10.5 WSC / 12 期望 = **0.88**，最吨效集束炮之一
- **HAG/30**：15.5/18 = **0.86**，HAG/40 **0.81** — 口径越大越省,家族内递增良好
- **Heavy HAG/30**：21/21 = **1.00**；Heavy HAG/40 **0.93** — Experimental tier 价格溢价合理（极距 40 格 + 更密集束）
- **vs Enhanced Gauss（WSC 12.5 买 15 伤 → 0.83）**：HAG/20 的 10.5 买 12 期望,**几乎平手**但 HAG 打集束伤 → 装甲吸收更轻。**Gauss 仍是穿甲王**，HAG 是**反软/反群** — 两线并存合理

---

## 弹道 — Advance 动能超级炮（2 件）

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E (min) | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| ADV | Electromagnetic Lance | 3060/3070/3080 | F·ADV | 20 / 5⁶ | 3 | (min3) 9/18/26/34 | 18/11 | 21.5 | 17.5 | 480 | Advance 线圈炮，**功率门控**：满功率 20 点固有 AP 长射；降功率缩到 0/5/10/15/20 失 AP，改投载荷（Flak / Flechette / Precision −2 / Incendiary）|
| ADV | Coil Augmented Railgun | 3060/3070/3080 | F·ADV | 30 平 | 6 | 9/18/27/36 | 22/13 | 27 | 24 | 700 | Advance 动能顶点，30 平伤**无衰减**、固有 AP（穿甲暴击）；前 12 发稳定，之后 +2 命中且 natural-2 卡壳，累积 +5 报废；单向 **Shed Barrel** 模式 → 25 伤稳定近战（4/6/9/12、无 AP、无磨损）|

**Advance 动能 t/dmg**（满功率口径；EM Lance 的"降功率特种弹"是另一维度）：
- **EM Lance**：21.5/20 = **1.08**（满功率），BV 480 / 20 = **24 BV/dmg** — Advance tier 的"固有 AP + 极距"溢价合理
- **Coil Augmented Railgun**：27/30 = **0.90**，BV 700 / 30 = **23 BV/dmg** — **30 平伤无衰减 + AP + Shed Barrel 25 伤近战模式**，是**全文档单武器 BV 最高件**。磨损机制（前 12 发稳定）本身是平衡器,实战应低于 0.90
- **关键发现**：Coil Railgun 的 BV/WSC_t 比 ER Heavy PPC（21.0）略高 2 → 换的是 "单发 30 平伤" + "固有 AP" 的 ROS tier 优势,合理

**⁶ EM Lance**：满功率 20 伤 9/18/26/34、固有 AP；降功率 5 伤 0/5/10/15/20、失 AP、改投特种弹（4 枚 handler：APDS / Flak / Flechette / Incendiary / Precision）。

## 弹道 — 机枪 / 机炮（8 件）

> **MG WSC 口径**：MG 本身 100-200 发/吨 → **N_ammo=1**（而非 2）更贴实战，一吨弹药够用；MGA 没自己的弹链主，`N_ammo=0`。

| tier | 武器 | Intro | TR·SL | dmg | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| STD | Light Machine Gun | 2805/2820/2840 | E·STD | 1 (1d6 反步) | 0 | 3/6/9/12 | 0.5/1 | 1.5⁺ | 2⁺ | 6 | 约长 50% vs 正史 LMG |
| STD | Machine Gun | 2805/2820/2840 | E·INT | 2 (2d6 反步) | 0 | 2/4/6/8 | 0.5/1 | 1.5⁺ | 2⁺ | 6 | 射程翻倍于正史 MG，200 发/吨 |
| STD | Heavy Machine Gun | 2805/2820/2840 | E·STD | 3 (3d6 反步) | 0 | 2/4/6/8 | 1/1 | 2⁺ | 2⁺ | 8 | 三倍正史射程，100 发/吨 |
| STD | Light Machine Gun Array | 2805/2820/2840 | E·STD | 2–4×LMG | 0 | 3/6/9/12 | 0.25/1 | 0.25⁺⁺ | 1⁺⁺ | 0 | 链接 2–4 挺 LMG 齐射（Linked/Off）；BV 0（并入 MG）；`F_MGA` |
| STD | Machine Gun Array | 2805/2820/2840 | E·STD | 2–4×MG | 0 | 2/4/6/8 | 0.5/1 | 0.5⁺⁺ | 1⁺⁺ | 0 | 同上，MG 基座 |
| STD | Heavy Machine Gun Array | 2805/2820/2840 | E·STD | 2–4×HMG | 0 | 2/4/6/8 | 1/1 | 1⁺⁺ | 1⁺⁺ | 0 | 同上，HMG 基座 |
| ADV | Machine Cannon | 3060/3070/3080 | F·STD | 3 (6d6 反步) | 1 | 3/6/9/12 | 1.5/2 | 3⁺ | 3.5⁺ | 14 | 补 MG 与 AC 之间的空档；支持 Flak 弹 |
| EXP | Heavy Machine Cannon | 3045/3050/3055 | G·EXP | 5 (10d6 反步) | 2 | 2/5/8/11 | 2.5/3 | 4.5⁺ | 6⁺ | 26 | 更重版 + Flak 弹 |

**⁺** `N_ammo=1`（MG 实战用弹远少于 AC）。**⁺⁺** MGA 无自身弹链主,`N_ammo=0`（从挂的 MG 吃弹）。

**MG 家族 t/dmg**（本家族更关键是"BV 效费" + 反步兵倍率）：
- **MG 系**：BV 6-8 极便宜,WSC_t 1.5-2 也极便宜 → BV/WSC_t = **4.0**,**全文档最低**；MG 不用 t/dmg 衡量,用 Xd6 反步战术价值
- **Machine Cannon**：14 BV / 3 WSC_t = **4.7**；跟 MG 比贵得起（有 6d6 反步兵 + Flak）
- **Heavy Machine Cannon**：26 BV / 4.5 WSC_t = **5.8**；EXP tier 溢价合理
- **MGA 关键机制**：**BV 0** + **WSC 几乎为 0** → 挂主武器 MGA 是"**白嫖反步**"。合理因为伤害走挂的 MG 条，MGA 只是集束触发器

**弹道段小计 72 OS + 8 IS = 80 件**：STD 32（含 MG 6）· IMP 20 · ENH 5 · ADV 10 · EXP 5 · ⚠STD 2（Rotary AC/2/5，TR 矛盾未解）· EXP 3（原 ⚠EXP¹ Ultra LB-X，SL 已降 STD）· IS BF 4 · IS RF 4。

---

## 导弹 — LRM 家族（25 件）

OS 核心 LRM（Improve / Enhanced / Heavy）带 **远距档 −1 命中**（`F_OS_LRM_LONG_SPEC`）+ **内建 +2 集束**（发射器固有，不可被 ECM 压制，与 FCS 叠加）+ 间接火力。Streak 系锁定必中无命中修正；ER-LRM 换长档 bonus 得极距。链接 Artemis IV FCS 给 +2 集束、Artemis V FCS 再给任意距离 −1 命中（均需对应 capable 弹）。

### LRM 核心（Improve / Enhanced / Heavy，11 件）
| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L/E (min) | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| IMP | Improve LRM 5 | 2900/2930/2960 | E·STD | 5 | 2 | (min6) 7/10/24/30 | 1.5/1 | 4.5 | 6 | 52 | OS 轻量核心 LRM；远档 −1、内建 +2 集束；可间接 |
| IMP | Improve LRM 10 | 2900/2930/2960 | E·STD | 10 | 4 | (min6) 7/10/24/30 | 3/2 | 7 | 10 | 104 | 同上 |
| IMP | Improve LRM 15 | 2900/2930/2960 | E·STD | 15 | 5 | (min6) 7/10/24/30 | 5/3 | 9.5 | 12.5 | 156 | 同上 |
| IMP | Improve LRM 20 | 2900/2930/2960 | E·STD | 20 | 6 | (min6) 7/10/24/30 | 7/5 | 12 | 16 | 208 | 同上 |
| ENH | Enhanced LRM 5 | 3000/3025/3050 | F·STD | 5 | 2 | (min6) 7/10/24/30 | 1/1 | 4 | 6 | 59 | Clan 重量；旧 doc 误标"无 min"，**代码仍是 min 6** |
| ENH | Enhanced LRM 10 | 3000/3025/3050 | F·STD | 10 | 4 | (min6) 7/10/24/30 | 2.5/1 | 6.5 | 9 | 117 | 同上 |
| ENH | Enhanced LRM 15 | 3000/3025/3050 | F·STD | 15 | 5 | (min6) 7/10/24/30 | 3.5/2 | 8 | 11.5 | 177 | 同上 |
| ENH | Enhanced LRM 20 | 3000/3025/3050 | F·STD | 20 | 6 | (min6) 7/10/24/30 | 5/4 | 10 | 15 | 236 | 同上 |
| STD | Heavy LRM 10 | 2805/2820/2840 | E·STD | 10 | 4 | (min6) 7/10/24/30 | 5/2 | 9 | 10 | 155 | 单/Ultra 双齐射（**无卡壳**，`OSDoubleFireLRMHandler`）；远档 −1、内建 +2 集束 |
| STD | Heavy LRM 20 | 2805/2820/2840 | E·STD | 20 | 6 | (min6) 7/10/24/30 | 10/5 | 15 | 16 | 316 | 同上 |
| STD | Heavy LRM 30 | 2805/2820/2840 | E·STD | 30 | 9 | (min6) 7/10/24/30 | 14/7 | 20.5 | 22.5 | 472 | 同上；30 rack 极端重齐射 |

### ER-LRM（3 件）
| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L (ext) | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| ADV | ER LRM 10 | 3060/3070/3080 | F·STD | 10 | 6 | 12/22/38 | 6/3 | 11 | 14 | 104 | 极距 LRM；Artemis 兼容；**无长档 −1 与内建集束**（走 ER 配方） |
| ADV | ER LRM 15 | 3060/3070/3080 | F·STD | 15 | 9 | 12/22/38 | 9/5 | 15.5 | 20.5 | 156 | 同上 |
| ADV | ER LRM 20 | 3060/3070/3080 | F·STD | 20 | 12 | 12/22/38 | 13/6 | 21 | 26 | 210 | 同上 |

### Streak LRM / Improve Streak LRM / Extended Streak LRM（11 件）
Streak 系：锁定齐射，锁上则全中；**无长档 −1 bonus**；重于非 Streak 同口径。range 走 canon LRM 7/14/21+（短档 7，不是核心 OS 的 10）。

| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L/E (min) | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| IMP | Improve Streak LRM 5 | 2900/2930/2960 | **F·STD** | 5 | 2 | (min6) 7/14/24/30 | 2/1 | 5 | 6 | 91 | Clan 级锁定 |
| IMP | Improve Streak LRM 10 | 2900/2930/2960 | **F·STD** | 10 | 4 | (min6) 7/14/24/30 | 5/2 | 9 | 10 | 182 | 同上 |
| IMP | Improve Streak LRM 15 | 2900/2930/2960 | **F·STD** | 15 | 5 | (min6) 7/14/24/30 | 7/3 | 11.5 | 12.5 | 273 | 同上 |
| IMP | Improve Streak LRM 20 | 2900/2930/2960 | **F·STD** | 20 | 6 | (min6) 7/14/24/30 | 10/5 | 15 | 16 | 363 | 同上 |
| ADV | Streak LRM 5 | 3060/3070/3080 | F·STD | 5 | 2 | (min6) 7/14/24/30 | 2.5/1 | 5.5 | 6 | 91 | 锁定齐射全中 |
| ADV | Streak LRM 10 | 3060/3070/3080 | F·STD | 10 | 4 | (min6) 7/14/24/30 | 6/2 | 10 | 10 | 182 | 同上 |
| ADV | Streak LRM 15 | 3060/3070/3080 | F·STD | 15 | 5 | (min6) 7/14/24/30 | 8.5/4 | 13 | 13.5 | 273 | 同上 |
| ADV | Streak LRM 20 | 3060/3070/3080 | F·STD | 20 | 6 | (min6) 7/14/24/30 | 11.5/5 | 16.5 | 16 | 363 | 同上 |
| EXP | Extended Streak LRM 10 | 3045/3050/3055 | G·STD | 10 | 6 | (min10) 12/22/38 (ext57) | 8/4 | 13 | 15 | 200 | 极距 + 保证命中 |
| EXP | Extended Streak LRM 15 | 3045/3050/3055 | G·STD | 15 | 9 | (min10) 12/22/38 (ext57) | 11/6 | 17.5 | 21.5 | 300 | 同上 |
| EXP | Extended Streak LRM 20 | 3045/3050/3055 | G·STD | 20 | 12 | (min10) 12/22/38 (ext57) | 16/7 | 24 | 27 | 400 | 同上 |

**LRM 家族 t/dmg**（期望：核心 LRM = rack × 0.7（内建 +2 集束）+ 长档 −1 加成 ×1.20 → 有效 **rack × 0.84**；ER-LRM = rack × 0.6（无内建集束 + 无长档 −1）；Streak = rack × 0.9（锁定高命中率）；Heavy LRM 平均开两次 ×1.3）：
- **核心 LRM 最省**：Improve LRM 5 **1.07**（4.5/4.2），Improve LRM 20 **0.71**（12/16.8），Enhanced LRM 20 **0.60**（10/16.8）— Enhanced LRM 20 是**全家族最 ton-efficient 的 LRM**
- **Heavy LRM 开双发**：Heavy LRM 20 WSC 15 / 期望 21.8（rack 20 × 0.84 × 1.3）= **0.69**，跟 Enhanced LRM 20 并列,但多一档稳定性
- **ER-LRM 的代价**：ER LRM 20 WSC 21 / 期望 12（rack 20 × 0.6，无长档 −1）= **1.75** — **最贵的 LRM**；换的是 38 格极距 + Artemis 兼容
- **Streak LRM**：Streak LRM 20 WSC 16.5 / 期望 18（rack 20 × 0.9）= **0.92**；Improve Streak LRM 20 **0.83**（15/18）— 比核心 LRM 贵但**保证命中**,**打弱甲目标反而更稳**
- **BV vs WSC 偏差警告**：Enhanced LRM 20 的 **BV 236 / WSC_t 10 = 23.6** — 全家族 BV/WSC_t 最高；跟 Improve LRM 20 的 **BV 208 / WSC_t 12 = 17.3** 比，Enhanced LRM 的 1t 效率溢价被反映在 BV 中。合理
- **Extended Streak LRM 20 400 BV**：WSC_t 24 / 期望 18 = **1.33**；BV 比 Streak LRM 20 (363) 高但 WSC 也多 7.5t → BV/WSC = 400/24 = 16.7 实际比 Streak LRM 的 22.0 更便宜。考虑到它极距 38 + 锁定 + G·STD tier，**BV 400 偏低**候选 → 建议 440-460

## 导弹 — SRM 家族（18 件）

OS 核心 SRM（Improve / Enhanced / Heavy）带 **短档 −1 命中**（`F_OS_SRM_SHORT_SPEC`）+ **内建 +2 集束**。Streak 系锁定必中、无命中修正；Streak SRM 用 canon 3/6/9 短距，Improve Streak SRM 用延伸 4/8/12/16。

### SRM 核心（Improve / Enhanced / Heavy，10 件）
| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L (ext) | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| IMP | Improve SRM 2 | 2900/2930/2960 | E·STD | 2 | 2 | 6/8/10 (ext12) | 0.75/1 | 3.75 | 6 | 21 | OS 核心 SRM：短档 −1、内建 +2 集束 |
| IMP | Improve SRM 4 | 2900/2930/2960 | E·STD | 4 | 3 | 6/8/10 (ext12) | 1.5/1 | 5 | 7.5 | 39 | 同上 |
| IMP | Improve SRM 6 | 2900/2930/2960 | E·STD | 6 | 4 | 6/8/10 (ext12) | 2/2 | 6 | 10 | 59 | 同上 |
| ENH | Enhanced SRM 2 | 3000/3025/3050 | F·STD | 2 | 2 | 6/8/10 (ext12) | 0.5/1 | 3.5 | 6 | 21 | Clan 效率版，战斗档同 Improve |
| ENH | Enhanced SRM 4 | 3000/3025/3050 | F·STD | 4 | 3 | 6/8/10 (ext12) | 1/1 | 4.5 | 7.5 | 39 | 同上 |
| ENH | Enhanced SRM 6 | 3000/3025/3050 | F·STD | 6 | 4 | 6/8/10 (ext12) | 1.5/1 | 5.5 | 9 | 59 | 同上 |
| STD | Heavy SRM 4 | 2805/2820/2840 | E·STD | 4 | 3 | 6/8/10 (ext12) | 3/2 | 6.5 | 8.5 | 65 | 单/Ultra 双齐射（**无卡壳**，`OSDoubleFireSRMHandler`）；短档 −1、内建 +2 集束 |
| STD | Heavy SRM 6 | 2805/2820/2840 | E·STD | 6 | 4 | 6/8/10 (ext12) | 4.5/3 | 8.5 | 11 | 95 | 同上 |
| STD | Heavy SRM 8 | 2805/2820/2840 | E·STD | 8 | 5 | 6/8/10 (ext12) | 6/4 | 10.5 | 13.5 | 130 | 同上；OS 新口径 |
| STD | Heavy SRM 12 | 2805/2820/2840 | E·STD | 12 | 7 | 6/8/10 (ext12) | 9/5 | 14.5 | 17.5 | 190 | 同上；OS 新口径 |

### Streak SRM / Improve Streak SRM（8 件）
| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| IMP | Improve Streak SRM 2 | 2900/2930/2960 | **F·STD** | 2 | 2 | 4/8/12/16 | 1/1 | 4 | 6 | 40 | Clan 级锁定；**延伸射程 4/8/12/16** |
| IMP | Improve Streak SRM 4 | 2900/2930/2960 | **F·STD** | 4 | 3 | 4/8/12/16 | 2/1 | 5.5 | 7.5 | 79 | 同上 |
| IMP | Improve Streak SRM 6 | 2900/2930/2960 | **F·STD** | 6 | 4 | 4/8/12/16 | 3/2 | 7 | 10 | 118 | 同上 |
| IMP | Improve Streak SRM 8 | 2900/2930/2960 | **F·STD** | 8 | 5 | 4/8/12/16 | 4/3 | 8.5 | 12.5 | 158 | 同上；OS 新口径 |
| ADV | Streak SRM 2 | 3060/3070/3080 | F·STD | 2 | 2 | 3/6/9 (ext12) | 1.25/1 | 4.25 | 6 | 30 | canon 短距锁定齐射 |
| ADV | Streak SRM 4 | 3060/3070/3080 | F·STD | 4 | 3 | 3/6/9 (ext12) | 2.5/1 | 6 | 7.5 | 59 | 同上 |
| ADV | Streak SRM 6 | 3060/3070/3080 | F·STD | 6 | 4 | 3/6/9 (ext12) | 3.75/2 | 7.75 | 10 | 89 | 同上 |
| ADV | Streak SRM 8 | 3060/3070/3080 | F·STD | 8 | 5 | 3/6/9 (ext12) | 5/3 | 9.5 | 12.5 | 120 | 同上；OS 新口径 |

**SRM 家族 t/dmg**（SRM 伤 = rack × 2；核心 SRM 期望 = rack × 2 × 0.7（+2 集束）× ~1.1（短档 −1 加成） = **rack × 1.54**；Streak 近乎全中 = rack × 2 × 0.9；Heavy 开双发 ×1.3）：
- **核心 SRM 最省**：Enhanced SRM 6 WSC 5.5 / 期望 9.24 = **0.60** — 全 SRM 家族最吨效（每吨买 1.68 伤）；Improve SRM 6 **0.65** 几乎并列
- **Heavy SRM**：Heavy SRM 12 WSC 14.5 / 期望 24 × 0.7 × 1.1 × 1.3 ≈ 24 = **0.60**。跟 Enhanced SRM 6 **完全并列** → Heavy SRM 的"双发无卡壳"溢价没买到 ton-efficiency 进步；BV 190 偏高（跟 Enhanced SRM 6 × 2 = 118 BV 比,多 72 BV 买的只是"打两次 + 无卡壳",轻微 OP 候选）
- **Streak SRM**：Streak SRM 6 WSC 7.75 / 期望 10.8（rack 6 × 2 × 0.9）= **0.72**；比核心 SRM 贵 ~15% 换**保证命中**
- **Improve Streak SRM 8**：延伸射程 16 格意味着在 SRM 价位拿到 LRM 短档射程；WSC 8.5 / 期望 14.4 = **0.59**，**全文档最便宜的 Streak** → 可能需要上调 BV（当前 158 比 Streak SRM 8 的 120 多 38 BV 合理）

## 导弹 — MRM 家族（7 件）

OS MRM 核心带 **超大中距档 −1 命中**（`F_OS_MRM_MEDIUM_SPEC`）+ **内建 +2 集束**；Diana III/IV FCS 加 +2 集束，Diana IV 再加任意距离 −1（需 capable 弹）。

| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L (ext) | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| IMP | Improve MRM 10 | 2900/2930/2960 | E·STD | 10 | 4 | 3/16/18 (ext20) | 3/2 | 7 | 10 | 70 | 中距档 −1、内建 +2 集束 |
| IMP | Improve MRM 20 | 2900/2930/2960 | E·STD | 20 | 6 | 3/16/18 (ext20) | 7/3 | 12 | 14 | 140 | 同上 |
| IMP | Improve MRM 30 | 2900/2930/2960 | E·STD | 30 | 10 | 3/16/18 (ext20) | 9.5/5 | 16.5 | 22 | 210 | 同上 |
| IMP | Improve MRM 40 | 2900/2930/2960 | E·STD | 40 | 12 | 3/16/18 (ext20) | 12/7 | 20 | 27 | 280 | 同上；OS 新口径 |
| ADV | Streak MRM 10 | 3060/3070/3080 | F·STD | 10 | 4 | 3/13/16/20 | 6/3 | 10 | 11 | 120 | 锁定 MRM 保证命中；range 比 Improve MRM 更短（13/16/20），刻意重/占位 |
| ADV | Streak MRM 20 | 3060/3070/3080 | F·STD | 20 | 6 | 3/13/16/20 | 12/5 | 17 | 16 | 240 | 同上 |
| ADV | Streak MRM 30 | 3060/3070/3080 | F·STD | 30 | 10 | 3/13/16/20 | 17/7 | 24 | 24 | 360 | 同上 |

**MRM 家族 t/dmg**（期望：Improve MRM = rack × 0.7（内建 +2）× 1.11（中档 −1）≈ **rack × 0.78**；Streak MRM = rack × 0.9）：
- **Improve MRM 10**：WSC 7 / 期望 7.8 = **0.90**；Improve MRM 40 = 20/31.2 = **0.64** — 口径越大越省,家族内递增良好
- **Streak MRM 20**：17/18 = **0.94**；比 Improve MRM 20 **（12/15.6=0.77）**贵 22% 换**锁定全中**。溢价合理
- **MRM 40 (BV 280 / WSC_t 20 = 14.0)**：全家族 BV/WSC_t 最低 → **BV 偏低**候选（跟 Heavy LRM 30 的 472/20.5 = 23 对比，MRM 40 中距独占 +1 应有溢价）
- **Streak MRM 30 (BV 360 / WSC_t 24 = 15.0)**：WSC 极重（24t ≈ 一个中型机的 1/2）→ 是**专机使用武器**,性价比不如 Heavy LRM 20（316 / 15 = 21.1）

## 导弹 — MML 家族（16 件，双模 LRM / SRM）

所有 MML 的 LRM-模式 range 都是 canon LRM 的 7/14/21/28 min 6（SRM 模式自动切换到 SRM range）。Heavy MML 加 Single/Ultra 双齐射（无卡壳），Streak MML 全锁定（**不兼容 Artemis**）。

### MML 核心 / Improve（8 件）
| tier | 武器 | Intro | TR·SL | rack | heat | LRM 档 (min) | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| STD | MML 3 | 2805/2820/2840 | E·STD | 3 | 2 | (min6) 7/14/21/28 | 1.25/2 | 4.25 | 7 | 29 | 双模发射器，比 IS MML 轻 |
| STD | MML 5 | 2805/2820/2840 | E·STD | 5 | 3 | (min6) 7/14/21/28 | 2.5/3 | 6 | 9.5 | 45 | 同上 |
| STD | MML 7 | 2805/2820/2840 | E·STD | 7 | 4 | (min6) 7/14/21/28 | 4/3 | 8 | 11 | 67 | 同上 |
| STD | MML 9 | 2805/2820/2840 | E·STD | 9 | 5 | (min6) 7/14/21/28 | 5/4 | 9.5 | 13.5 | 86 | 同上 |
| IMP | Improve MML 5 | 2900/2930/2960 | E·STD | 5 | 3 | (min6) 7/14/21/28 | 2/2 | 5.5 | 8.5 | 45 | 更轻的双模 |
| IMP | Improve MML 7 | 2900/2930/2960 | E·STD | 7 | 4 | (min6) 7/14/21/28 | 3/3 | 7 | 11 | 67 | 同上 |
| IMP | Improve MML 9 | 2900/2930/2960 | E·STD | 9 | 5 | (min6) 7/14/21/28 | 4/4 | 8.5 | 13.5 | 86 | 同上 |
| IMP | Improve MML 11 | 2900/2930/2960 | E·STD | 11 | 6 | (min6) 7/14/21/28 | 5.5/4 | 10.5 | 15 | 105 | 同上；OS 新口径 |

### Heavy MML / Streak MML（8 件）
| tier | 武器 | Intro | TR·SL | rack | heat | LRM 档 (min) | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| ADV | Heavy MML 5 | 3060/3070/3080 | F·STD | 5 | 6 | (min6) 7/14/21/28 | 4/3 | 9 | 14 | 85 | 双模 + 单/Ultra 双齐射（**无卡壳**，`OSDoubleFireLRM/SRMHandler`）|
| ADV | Heavy MML 8 | 3060/3070/3080 | F·STD | 8 | 8 | (min6) 7/14/21/28 | 6.5/4 | 12.5 | 18 | 145 | 同上 |
| ADV | Heavy MML 11 | 3060/3070/3080 | F·STD | 11 | 12 | (min6) 7/14/21/28 | 9/6 | 17 | 26 | 200 | 同上 |
| ADV | Heavy MML 14 | 3060/3070/3080 | F·STD | 14 | 14 | (min6) 7/14/21/28 | 12/7 | 21 | 30 | 250 | 同上；OS 新口径 |
| ADV | Streak MML 5 | 3060/3070/3080 | F·STD | 5 | 3 | (min6) 7/14/21/28 | 3.5/3 | 7 | 9.5 | 75 | 双模 + Streak 锁定（**不兼容 Artemis**）|
| ADV | Streak MML 7 | 3060/3070/3080 | F·STD | 7 | 4 | (min6) 7/14/21/28 | 5/4 | 9 | 12 | 105 | 同上 |
| ADV | Streak MML 9 | 3060/3070/3080 | F·STD | 9 | 5 | (min6) 7/14/21/28 | 6.5/5 | 11 | 14.5 | 135 | 同上 |
| ADV | Streak MML 11 | 3060/3070/3080 | F·STD | 11 | 6 | (min6) 7/14/21/28 | 8/6 | 13 | 17 | 165 | 同上；OS 新口径 |

**MML 家族 t/dmg**（LRM 模式口径；MML 无内建 +2 集束（通用性税） → 期望 = rack × 0.6；Heavy MML ×1.3 开双发；Streak MML ×0.9）：
- **核心 MML 9**：9.5 / 5.4 = **1.76**；比 Improve LRM 20 (12/16.8=0.71) 贵 2.5 倍 — MML 的"双模切换"溢价**极高**；但换的是 1 件填 2 用（省 crit）
- **Improve MML 11**：10.5 / 6.6 = **1.59**，比核心 MML 9 便宜 10% — tier 升级合理
- **Heavy MML 14**：21 / 10.9（rack 14 × 0.6 × 1.3）= **1.93**；全文档最贵的 LRM/SRM 系武器之一；**Heavy MML 系的"双模 + 双发"溢价很重**，只适合"少数大件 vs 多件专武器"的构建哲学
- **Streak MML 11**：13 / 9.9 = **1.31**，比 Streak LRM 20 (16.5/18=0.92) 贵 42%；MML 的双模税在 Streak 系仍存在

## 导弹 — ATM 家族（8 件，可变载荷：Extended / Standard / High-Explosive）

ATM 核心机制：装 **SRTM / APTM / LRTM** 改射程与单发伤（canon ATM 行为）。

| tier | 武器 | Intro | TR·SL | rack | heat | S/M/L/E (min) | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|:--:|--:|--:|--:|---|
| IMP | Improve ATM 3 | 2900/2930/2960 | **F·STD** | 3 | 2 | (min4) 5/10/15/20 | 1.5/2 | 4.5 | 7 | 83 | iATM 级，更丰富载荷 |
| IMP | Improve ATM 6 | 2900/2930/2960 | **F·STD** | 6 | 4 | (min4) 5/10/15/20 | 3.5/3 | 7.5 | 11 | 165 | 同上 |
| IMP | Improve ATM 9 | 2900/2930/2960 | **F·STD** | 9 | 6 | (min4) 5/10/15/20 | 5.5/4 | 10.5 | 15 | 247 | 同上 |
| IMP | Improve ATM 12 | 2900/2930/2960 | **F·STD** | 12 | 8 | (min4) 5/10/15/20 | 7.5/5 | 13.5 | 19 | 350 | 同上 |
| ADV | ATM 3 | 3060/3070/3080 | F·STD | 3 | 2 | (min4) 5/10/15/20 | 1.5/2 | 4.5 | 7 | 53 | canon ATM：LRTM/SRTM/APTM 可变载荷 |
| ADV | ATM 6 | 3060/3070/3080 | F·STD | 6 | 4 | (min4) 5/10/15/20 | 3.5/3 | 7.5 | 11 | 105 | 同上 |
| ADV | ATM 9 | 3060/3070/3080 | F·STD | 9 | 6 | (min4) 5/10/15/20 | 5/4 | 10 | 15 | 147 | 同上 |
| ADV | ATM 12 | 3060/3070/3080 | F·STD | 12 | 8 | (min4) 5/10/15/20 | 7/5 | 13 | 19 | 212 | 同上 |

**ATM 家族 t/dmg**（期望：标准弹 ATM = rack × 2 × 0.6 = **rack × 1.2**；SRTM 近距 = rack × 3 × 0.6；LRTM 远距 = rack × 1 × 0.6）：
- **标准弹口径**：Improve ATM 12 WSC 13.5 / 14.4 期望 = **0.94**；ATM 12 (canon ADV) WSC 13 / 14.4 = **0.90**。两者 WSC 几乎相同
- **BV 差对比**：Improve ATM 12 **BV 350 / WSC_t 13.5 = 25.9**；ATM 12 **BV 212 / WSC_t 13 = 16.3** — **Improve ATM 的 BV 溢价 60%** 对应"iATM 级载荷丰富度"。假如实战两者都用标准弹,Improve ATM 的 BV 溢价无法反映；但 Improve ATM 可以用 iATM 专有弹药（更丰富） → BV 溢价有理
- **ATM 作为短距打击**：SRTM 装载时近距 3 × rack 的伤量让 ATM 6 短距实际期望 3×6×0.6=10.8 伤 → WSC 7.5 买 10.8 = **0.69** — 短距刺客
- **BV vs WSC**：Improve ATM 系 BV/WSC_t ≈ 25-26,canon ATM 系 BV/WSC_t ≈ 14-16，差距大致 1.6×。合理反映 tier 差

## 导弹 — Tactical Heavy Missile Launcher（8 件，原 Dragon Piercer 改名）

> 内部名 / lookup 仍保留 `OSDragonPiercer*`（存档兼容）。单枚大穿甲弹头，非集束；专属 handler `OSDragonPiercerHandler`（Improve 版用 `OSDragonPiercerImpHandler`）；**继承 Thunderbolt 基类的 dmg / heat**（Tbolt-5 5/3、-10 10/5、-15 15/7、-20 20/8）。

| tier | 武器 | Intro | TR·SL | dmg | heat | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|--:|:--:|--:|--:|--:|---|
| STD | Tactical Heavy Missile Launcher 5 | 2805/2820/2840 | E·STD | 5 | 3 | 3/– | 6.5 | 5.5 | 64 | 单枚 AP 弹头，穿甲暴击 **8+** |
| STD | Tactical Heavy Missile Launcher 10 | 2805/2820/2840 | E·STD | 10 | 5 | 7/– | 11.5 | 9.5 | 127 | 同上 |
| STD | Tactical Heavy Missile Launcher 15 | 2805/2820/2840 | E·STD | 15 | 7 | 11/– | 16.5 | 12.5 | 229 | 同上 |
| STD | Tactical Heavy Missile Launcher 20 | 2805/2820/2840 | E·STD | 20 | 8 | 15/– | 21 | 14 | 305 | 同上 |
| IMP | Improve THML 5 | 2900/2930/2960 | E·STD | 5 | 3 | 2.5/– | 6 | 5.5 | 64 | 更轻；穿甲暴击加深到 **7+** |
| IMP | Improve THML 10 | 2900/2930/2960 | E·STD | 10 | 5 | 5.5/– | 10 | 9.5 | 127 | 同上 |
| IMP | Improve THML 15 | 2900/2930/2960 | E·STD | 15 | 7 | 8.5/– | 14 | 12.5 | 229 | 同上 |
| IMP | Improve THML 20 | 2900/2930/2960 | E·STD | 20 | 8 | 12/4 | 18 | 18 | 305 | 同上 |

> **crit 字段**："–" 表示代码未显式设 criticalSlots（由基类处理）；只有 Improve THML 20 显式设 crit=4。

**THML 家族 t/dmg**（期望 = dmg × 0.6；AP 暴击 8+/7+ 加 ~10% 有效伤）：
- **THML 20**：21 WSC / 13.2 期望 = **1.59**；最贵的 OS 导弹件（跟 Heavy LRM 20 的 15/16.8 = 0.89 比 **贵 ~80%**）
- **Improve THML 20**：18 / 13.2 = **1.36**；Improve 版省 3t,**穿甲暴击门槛更低 7+（原 8+） → AP crit 概率 ×1.8** → 有效 t/dmg 调整到 **~0.95** — 跟 ER Heavy PPC 对等
- **定位**：THML 是**专机 AP 弹头**,不走 ton/dmg 效率线,走"保证穿甲暴击"路线。BV 305 / WSC_t 21 = **14.5 BV/WSC_t** 偏低 (ER Heavy PPC 420/20=21.0 对比) → 建议上调 BV ≈ 340-360

## 导弹 — Narc / 信标（3 件，0 伤，挂导引 pod）

> Narc 本身无伤;WSC 以 `N_ammo=1` 算（pod 消耗远低于 AC/LRM 弹药）。t/dmg 不适用 — Narc 的价值在"集束 +2 友方导弹命中"。

| tier | 武器 | Intro | TR·SL | heat | S/M/L/E | t/c | WSC_t | WSC_c | BV | 机制 |
|---|---|:--:|:--:|--:|:--:|:--:|--:|--:|--:|---|
| STD | Narc Missile Beacon | 2805/2820/2840 | E·STD | 0 | 3/6/9/12 | 3/2 | 4 | 3 | 30 | 挂导引 pod，友方 Narc 导弹保证集束命中 |
| STD | Seeker Missile Beacon | 2805/2820/2840 | E·STD | 0 | 3/6/9/12 | 2.5/2 | 3.5 | 3 | 30 | 轻量 Narc 信标，同 pod |
| ADV | Munin Missile Beacon | 3060/3070/3080 | F·STD | 0 | 4/9/15/18 | 5/3 | 6 | 4 | 75 | iNarc 级多 pod：Homing / ECM / Haywire / Nemesis / **Lodestar**（§9.6）|

**Narc 家族**：Narc Missile Beacon BV 30 / WSC_t 4 = **7.5 BV/WSC_t** 看似偏贵,但 Narc 的价值是"全网络导弹 +2 集束"的**场外增益** — 跟 Artemis IV FCS 的 +2 集束等效但覆盖全网络而非单发射器；Munin 的 Lodestar pod 更是 Kestrel NTD 远程解锁件（见 §9.6）,BV 75 很合理

**导弹段小计 88 件**：STD 23（Heavy LRM/SRM、MML、THML、Narc）· IMP 15 · IMP 12（原 ⚠IMP¹：Improve Streak LRM/SRM、Improve ATM — SL 已降 STD）· ENH 7 · ADV 28（ER-LRM、Streak LRM/SRM/MRM/MML、Heavy MML、ATM、Munin）· EXP 3（Extended Streak LRM）。

**⚠¹** `Improve Streak LRM / Improve Streak SRM / Improve ATM`（共 12 件）：intro 对齐 Improve（2900–2960），但 `SimpleTechLevel` 旧标 **ADVANCED**（其他 Improve-系导弹均 STANDARD）→ **已落地（2026-11-07）**：SL 降 STANDARD。TR 本轮不动，intro-date 也不动，仅 SL 更新。模式跟激光 ⚠¹（Improve Heavy Pulse 系）完全一样。

**旧版 doc 订正**：
- Enhanced LRM 系 4 件：旧版标"**无最小射程**"是错的 — 代码所有 Enhanced LRM 都是 **min 6**。
- Streak LRM 系：旧版把 range 写成 "(min6) 7/10/24 (ext30)" 跟核心 LRM 一致，但代码里 Streak LRM 用的是 **canon 7/14/24/30**（med 档是 14 不是 10，因为 Streak 系不走核心 LRM 的"远档 −1 换 med 收紧"配方）。
- Streak MRM 系：旧版标 "3/16/18 (ext20)" 跟 Improve MRM 一致，代码里是 **3/13/16/20**（med/long 都更短，刻意弱化为"锁定但射程不如 Improve"）。
- Improve Streak SRM 系：旧版标 "6/8/10 (ext12)" 跟 Improve SRM 一致，代码里是 **4/8/12/16**（延伸射程，Clan 风）。

## 导弹火控（装在发射器旁增益；均需装对应"capable"弹才生效）

> FCS 是 MiscType（非武器），无 heat / 无弹药税；WSC_t = t、WSC_c = c。其"价值密度"看**命中/集束加成 / WSC_t** 比。

| 件 | t/c | WSC_t | WSC_c | 机制 |
|---|:--:|--:|--:|---|
| Artemis IV FCS (OS) | 1/1 | 1 | 1 | +2 集束（仅 Artemis IV-capable 弹）；**无命中修正** |
| Artemis V FCS (OS) | 1.5/1 | 1.5 | 1 | 高级 LRM/SRM 火控：+2 集束 **且任意距离 −1 命中**（仅 Artemis V-capable 弹；ECM/隐身压制集束半） |
| Diana III FCS | 1/1 | 1 | 1 | MRM 专用（对标 Apollo）：+2 集束（仅 Diana III-capable MRM 弹）；**无命中修正** |
| Diana IV FCS | 1.5/1 | 1.5 | 1 | 高级 MRM 火控：+2 集束 **且任意距离 −1 命中**（仅 Diana IV-capable MRM 弹） |
| Orion V FCS | 1.5/2 | 1.5 | 2 | 高端整合：集束 **+3**（vs Artemis +2）；对 Streak 改助锁（锁定骰 −1 + 免标准 ECM 断锁） |

**FCS 价值密度**（以挂一台 LRM 20 为参照：rack 20 × 0.1 期望增量/集束骰 = **+2 期望伤/+2 集束**）：
- **Artemis IV OS / Diana III**：1 WSC_t 买 **+2 期望伤 + −2 BV×0.25 系数** ≈ 价值密度 **2.0**；Artemis V OS / Diana IV 1.5 WSC_t 买 +2 集束 + 任意距离 −1 命中（≈ ×1.2 期望） = 期望增量 **2.0 + 4** = 6 → 价值密度 **4.0** — 最强的 FCS
- **Orion V FCS**：1.5 WSC_t 买 +3 集束（+3 期望伤）+ Streak 助锁 → 价值密度 **2.0 vs 单发射器**;但如果挂多发射器则是 **2.0 × N** → 超线性收益,对**集束导弹堆**最划算

---

## 备注

- **2026-11-08 — WSC 全量铺开**：所有武器表均新增 **WSC_t / WSC_c 两列**（分析口径 `T + heat/2 [+N_ammo]`），每族表后附 **t/dmg + BV cross-check** 评语。参考表（§平衡度量）扩至 25 件跨家族代表。定位出若干 **BV 偏差候选**（跟 WSC_t 比较）：Enhanced HVAC 系 BV 偏低、ER Heavy Large Laser BV 偏低、MRM 40 BV 偏低、Heavy SRM 12 BV 偏高、Extended Streak LRM 20 BV 偏低、Hyper PPC 现数值下严重偏弱（1.67 t/dmg）等待 2026-11-07 重设计落地验证。**数值不变,仅加列 + 评语;下一步按 WSC/BV 偏差批次调 BV。**
- **导弹系已按 2026-08-29 重做同步**（提交 `70ea7cf99c`）：LRM/MRM/SRM 重定射程、各专精档 −1（含新增 SRM 短档 `F_OS_SRM_SHORT_SPEC`）、核心发射器**内建 +2 集束**（不可被 ECM 压制、与 FCS 叠加）；Artemis IV / Diana III 退回**只给 +2 集束**，新增 **Artemis V / Diana IV**（1.5t）才另给任意距离 **−1 命中**（均需装对应 capable 弹）；Dragon Piercer 改名 **Tactical Heavy Missile Launcher**（内部名保留）。**BV 尚未按新强度重调。**
- **测试状态**：本表所有武器均**仅过编译 + 装备初始化单元测试，未经对局实测**（见 `OS_COMPONENT_PLAYTEST_CHECKLIST.md`）。
- **相关文档**：`OS_TECHBASE_DEVELOPMENT_PLAN.md`（设计/实现，§7 AC munitions、§8 Advance 动能、§2 HVAC）；`CANON_AMMO_REFERENCE.md`（弹药）；`equipmentmessages.properties`（游戏内 ⓘ info 原文）。

- **全文档 ⚠ 分类问题汇总**（2026-11-07 SL 批次已清，仅剩 1 件 TR 遗留）：
  - **能量**：Improve Heavy Pulse 系 3 件 SL=ADV 应 STD → **✅ 2026-11-07 已降 STANDARD**；ER Heavy / ER Pulse 无前缀 / Heavy Pulse 无前缀 共 9 件挂 Advance 带 → **✅ SL 已降 STANDARD**（intro-date / TR 迁 Improve 仍遗留）；Binary/Trinary 系 4 件 tier 未拍定仍待（Hyper 在 EXP 中间断档）。
  - **弹道**：Rotary AC/2 和 /5 挂 2805 Standard 却带 TR F，**TR 矛盾仍遗留**（SL 本就 STD、本轮未动）；Ultra LB-X 系 3 件挂 EXP+G 过头 → **✅ SL 已降 STANDARD**（intro / TR 迁 ADV+F 仍遗留）。
  - **导弹**：Improve Streak LRM / Improve Streak SRM / Improve ATM 共 12 件 SL=ADV 应 STD（跟 Improve Heavy Pulse 同 pattern）→ **✅ 2026-11-07 已降 STANDARD**；旧版 doc 对 Enhanced LRM 系误标"无 min"、对 Streak LRM/MRM 系 range 误抄 — 本次重建已订正。
  - **本次 SL 批次小计**：100 件（10 件 STANDARD→INTRO 真·基础武器 + 90 件 ADV/EXP→STANDARD 工程落地）。TR / intro-date 本轮不动。剩遗留：Rotary AC/2/5 TR 矛盾 2 件（intro-date 层）。
