# Outer Sphere (OS) Weapons — Values & Mechanics

> 用途：把每一件 OS 武器的**具体数值**与**签名机制**并列成一份速查表，便于设计/调参/对局参考。
>
> **数值与机制来源**：机制文字取自 `megamek/resources/megamek/common/equipmentmessages.properties`（`EquipmentInfo.*`，均已对照武器类/handler 代码核实，2026-08-25）；纯数值（吨/临界/射程/伤害/热）取自各武器类。**BV / cost 见 `OS_WEAPONS_REFERENCE.md`**（注意那份表 2026-06-12 生成，命名仍用旧的 "Advance"＝现 "Enhanced"，且缺 HVAC / Enhanced Ultra AC / Electromagnetic Lance / Coil Augmented Railgun，需重新生成）。
>
> **列含义**：dmg = 单发伤害（`cluster` = 按集束、`var` = 随射程/弹种变化）；heat = 开火热量；S/M/L/E = 短/中/远/极限射程（格）；t/c = 吨 / 临界槽。射程档位前的 `(min N)` = 最小射程。
>
> **五级阶梯**：Standard → Improve → Enhanced → Advance → Experimental。纯 IS 复刻（标准 AC/LB-X/Ultra AC/PPC/ER PPC/激光）只列一行并标 “IS 复刻”。

---

## 能量 — 异种激光（Binary / Trinary / Hyper）

集束型激光把多个镜筒并成一次打击；**Resonance Tuning** 只对基础标准装甲加成，遇 Ferro-Fibrous 及一切先进/特种装甲失效。

| 武器 | dmg | heat | S/M/L/E | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Binary Laser System | 16 | 16 | 5/10/15/20 | 7/4 | 双筒并一击，无命中修正；Resonance ×1.30（→21）vs 标准装甲 |
| ER Binary Laser System | 16 | 20 | 8/15/22/30 | 7/4 | −1 命中；Resonance ×1.30（→21）vs 标准装甲；换热效率换射程/精度 |
| Trinary Laser System | 24 | 24 | 5/10/15/20 | 11/6 | 三筒并一击；Resonance ×1.50（→36）vs 标准装甲；近距 alpha、热账重 |
| ER Trinary Laser System | 24 | 32 | 8/15/22/30 | 11/6 | −1 命中；×1.50（→36）vs 标准装甲；**每回合可射**（异于充能锁的 Hyper） |
| Large Hyper Laser | 25 | 30 | 12/24/36/50 | 8/6 | Experimental 极距；−1 命中、航空满 AV；**须先充能一回合→隔回合发射**；爆炸物（暴击殉爆 12） |

## 能量 — 激光阶梯（ER / Heavy / Pulse，大/中/小）

Heavy Laser 自带 **+1 命中惩罚**（Improve 版工程消除之）；Pulse 自带 **−2 命中**；ER Pulse 常以 1 点脉冲精度换 1 档射程（Improve ER Pulse 找回全 −2）。

### 大型
| 武器 | dmg | heat | S/M/L/E | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| ER Large Laser (Improve) | 9 | 12 | 7/14/19/28 | 4.5/3 | 比 IS ER 大激光多 1 伤、更远 |
| Enhanced ER Large Laser | 11 | 14 | 8/15/22/30 | 4.5/4 | 内建 −1 命中；让 1 伤换精度与满远程 AV |
| Heavy Large Laser | 16 | 18 | 5/10/15/20 | 4/3 | 大型最佳吨伤；**+1 命中惩罚**，近战型 |
| Improve Heavy Large Laser | 16 | 18 | 5/10/15/20 | 4/3 | 同上但工程消除 +1 惩罚——满伤正常精度 |
| ER Heavy Large Laser | 16 | 22 | 7/14/19/28 | 5/3 | Heavy 大激光推到 ER 距离；保留 +1 惩罚 |
| Improve Large Pulse Laser | 9 | 10 | 3/7/10 | 6/2 | 全 −2 脉冲精度，近距 brawler |
| ER Large Pulse Laser | 10 | 14 | 6/12/17 | 6.5/3 | −1 命中；比标准大脉冲多 1 档，让 1 点脉冲精度换距离 |
| Improve ER Large Pulse Laser | 11 | 14 | 6/14/22 (ext30) | 6.5/4 | 保全 −2 脉冲且伸到 ER |
| Enhanced ER Large Pulse Laser | 14 | 18 | 6/14/22 (ext30) | 6/4 | Experimental；**−3 命中**（深于常规 −2） |
| Heavy Large Pulse Laser | 12 | 14 | 3/6/9 | 6/2 | Heavy+脉冲；−2 命中但射程压很短，重型近战刀客 |
| Improve Heavy Large Pulse Laser | 13 | 14 | 5/10/15 | 6.5/3 | −2 命中，射程拉回 5/10/15，无缩短惩罚 |

### 中型
| 武器 | dmg | heat | S/M/L/E | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| ER Medium Laser (Improve/Std) | 6 | 5 | 4/8/12/16 | 1/1 | 比基线中激光多 1 伤更远 |
| Enhanced ER Medium Laser | 8 | 6 | 5/10/15 | 1/1 | 内建 −1；线内最硬中激光 |
| Heavy Medium Laser | 10 | 7 | 3/6/9 | 1/2 | 最佳中型吨伤；**+1 惩罚** |
| Improve Heavy Medium Laser | 10 | 7 | 3/6/9 | 1/2 | 消除 +1 惩罚 |
| ER Heavy Medium Laser | 10 | 12 | 4/8/12 (ext16) | 2/2 | Heavy 中激光推到 ER；保留 +1 惩罚 |
| Improve Medium Pulse Laser | 6 | 4 | 3/5/7 | 2/1 | 全 −2 脉冲 |
| ER Medium Pulse Laser | 7 | 6 | 4/7/11 (ext14) | 2/2 | −1；换 1 点脉冲精度得射程 |
| Improve ER Medium Pulse Laser | 8 | 8 | 4/8/12 (ext16) | 2/2 | 全 −2 脉冲 + ER 距离 |
| Enhanced ER Medium Pulse Laser | 10 | 12 | 4/8/12 (ext16) | 2/2 | **−3 命中**，线内最准中激光 |
| Heavy Medium Pulse Laser | 7 | 5 | 2/4/5 | 1.5/2 | −2 脉冲但射程极短，点射精确件 |
| Improve Heavy Medium Pulse Laser | 8 | 5 | 3/6/9 | 1.5/2 | −2 脉冲，射程拉回 |

### 小型
| 武器 | dmg | heat | S/M/L/E | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| ER Small Laser (Improve/Std) | 4 | 2 | 2/4/5 (ext8) | 0.5/1 | 比 IS ER 小激光更伤更远 |
| Enhanced ER Small Laser | 5 | 3 | 3/5/8 (ext10) | 0.5/1 | 内建 −1；线内最远最准小激光 |
| Heavy Small Laser | 6 | 3 | 1/2/3 | 0.5/1 | 最佳小型吨伤；**+1 惩罚** |
| Improve Heavy Small Laser | 6 | 3 | 1/2/3 | 0.5/1 | 消除 +1 惩罚 |
| ER Heavy Small Laser | 6 | 4 | 2/4/5 (ext8) | 1.5/1 | Heavy 小激光推到 ER；保留 +1 |
| Improve Small Pulse Laser | 3 | 2 | 2/3/5 | 1/1 | 全 −2 脉冲 |
| ER Small Pulse Laser | 3 | 3 | 2/4/6 (ext8) | 1/1 | −1；换 1 点脉冲精度得射程 |
| Improve ER Small Pulse Laser | 4 | 3 | 3/5/7 (ext10) | 1/1 | 全 −2 脉冲 + ER |
| Enhanced ER Small Pulse Laser | 5 | 4 | 3/5/7 (ext10) | 1/1 | **−3 命中** |
| Heavy Small Pulse Laser | 4 | 3 | 1/2/3 | 0.5/1 | −2 脉冲，点射 |
| Improve Heavy Small Pulse Laser | 5 | 3 | 1/2/3 | 0.5/1 | 比 Heavy 小脉冲多 1 伤，同点射距 |

## 能量 — PPC

| 武器 | dmg | heat | S/M/L/E | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| PPC / ER PPC | — | — | — | — | IS 复刻（见 OS_WEAPONS_REFERENCE） |
| Enhanced ER PPC | 15 | 15 | 7/14/23/30 | 6/2 | 无最小射程；比标准 ER PPC 一倍半伤、更轻 |
| Heavy PPC | 18 | 18 | (min3) 6/12/18/24 | 8/4 | 比标准 PPC 更重击 |
| ER Heavy PPC | 18 | 22 | 7/14/20/27 | 9/4 | 无最小射程，重击 + ER |
| Light PPC | 7 | 7 | (min3) 6/12/18/24 | 3/2 | 轻 PPC，比正史 Light PPC 更伤 |
| Snub-Nose PPC | var 10/8/6 | 10 | 9/13/15/22 | 6/2 | 伤随距降；无最小射程；爆炸物 |
| Heavy Snub-Nose PPC | var 15/9/6 | 15 | 9/12/14/21 | 8/4 | 重型近距 PPC；无最小射程；爆炸物 |
| Hyper PPC | 30 | 30 | (min3) 12/24/36/50 | 10/7 | Experimental；内建 −1；**须先充能→隔回合发射** |
| Rotary Light PPC | 5×(1–5) | 2/发 | 6/12/18/24 | 7/5 | 前 3 发自冷（各 2 热）；4–5 发抽 **RPPC 冷却舱**，无舱射 4+ 过载 |
| Rotary Snub-Nose PPC | var 8/6/4 ×(1–4) | 3/发 | 8/12/15/22 | 10/6 | 无最小射程；前 3 发自冷；第 4 发抽冷却舱，无舱过载 |
| PPC-X | 6×2（集束，最多12） | 10 | 7/10/13/18 | 6/3 | 分 6 束；命中梯度 −1短/0中/+1远；无最小射程；爆炸物 |
| Heavy PPC-X | 6×4（集束，最多24） | 15 | 7/10/13/18 | 8/4 | 同梯度；爆炸物 |

## 能量 — 等离子（全部弹药供弹）

| 武器 | dmg | heat | S/M/L/E | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Plasma Rifle | 10 + 2d6 目标热 | 8 | 6/12/18/24 | 5/2 | 聚焦弹；比正史 IS 等离子步枪更冷/轻/远 |
| Plasma Cannon | 0 装甲伤 + 2d6 目标热 | 6 | 6/12/18/24 | 4/2 | 面压制；对机甲不破甲、只灌热；对软目标 3d6 集束 |
| Heavy Plasma Rifle | 15 + 2d6 目标热 | 12 | 5/10/15/20 | 7/3 | 破 10 点等离子上限；**+1 命中惩罚**，近距破甲 |
| Heavy Plasma Cannon | 0 装甲伤 + 3d6 目标热 | 10 | 6/12/18/24 | 6/3 | 更密面灌热 + 3d6 反软集束；**+1 惩罚** |
| EMP Plasma Accelerator | 0 | 8 | 5/10/15/20 | 5/3 | 专职电子扰乱：只造成 INTERFERENCE（永不完全停机）；开火方无自扰 |

## 能量 — 火焰器（能量供弹无弹药；Damage 烧甲 / Heat 灌热；Xd6 反步兵）

| 武器 | dmg | heat | S/M/L/E | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Improve Flamer | 2 | 2 | 1/2/3 | 0.5/1 | 效率型：半吨、点火热减半（标准 3）；4d6 反步兵 |
| ER Flamer | 2 | 3 | 3/6/9 | 1/1 | 远射型，远超 IS ER 火焰器 3/5/7；2d6 反步兵 |
| Heavy Flamer | 5 | 6 | 2/4/6 | 1.5/1 | 伤害型，全能量供弹（无燃料弹）；6d6 反步兵 |
| ER Heavy Flamer | 5 | 7 | 3/6/9 | 2/1 | 最重 OS 火焰器；远 + 重伤，7 点火热是限制；6d6 反步兵 |

---

## 弹道 — 自动炮：LB-X / Ultra / Rotary / HVAC

标准 AC/2·5·10·20、标准 LB-X、标准 Ultra AC 均为 **IS 复刻**（见 OS_WEAPONS_REFERENCE）。以下为 OS 特色项。

### LB-X（双模：实心弹 / 集束霰弹）
| 武器 | dmg | heat | S/M/L/E | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Improve LB 2-X | 2 | 1 | 9/18/27/36 | 4.5/2 | 双模，比标准更轻 |
| Improve LB 5-X | 5 | 1 | 7/14/21/28 | 6.5/4 | 同上 |
| Improve LB 10-X | 10 | 2 | 6/12/18/24 | 10/5 | 主力双模自动炮，更轻 |
| Improve LB 20-X | 20 | 5 | 5/10/15/20 | 12/8 | 更远更轻 |
| Ultra LB 5-X | 5 | 1 | 8/16/24/32 | 6/4 | LB-X 顶级：双模 + Ultra 双发 + **OS LB-X 特种弹**（GAAM 制导 AP、Anti-Myomer 霰弹） |
| Ultra LB 10-X | 10 | 2 | 7/14/21/28 | 9/5 | Ultra 双发 + GAAM/Anti-Myomer |
| Ultra LB 20-X | 20 | 5 | 5/10/15/20 | 12/8 | Ultra 双发 + OS LB-X 特种弹 |

### Ultra AC（单发 / Ultra 双发；OS 版**不卡壳**）
| 武器 | dmg | heat | S/M/L/E | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Improve Ultra AC/2 | 2 | 1 | 9/18/25/34 | 6/2 | 单/双发，无卡壳，更轻 |
| Improve Ultra AC/5 | 5 | 1 | 7/14/21/28 | 8/4 | 同上 |
| Improve Ultra AC/10 | 10 | 3 | 6/12/18/24 | 11/6 | 更轻更冷 |
| Improve Ultra AC/20 | 20 | 6 | 4/8/12/16 | 12/8 | 更远更轻更冷 |
| Enhanced Ultra AC/2 | 2 | 1 | 9/18/25/34 | 6/2 | 内建 −1 命中 + Ultra 无卡壳 |
| Enhanced Ultra AC/5 | 5 | 1 | 8/16/24/32 | 7/4 | 内建 −1，更长 |
| Enhanced Ultra AC/10 | 10 | 3 | 7/14/20/27 | 10/4 | 内建 −1，精准/长/瘦 |
| Enhanced Ultra AC/20 | 20 | 6 | 5/9/13/17 | 12/8 | 内建 −1 |

### Rotary AC（1–N 连发爆发，低骰卡壳）
| 武器 | dmg | heat | S/M/L/E | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Rotary AC/2 | 2×(1–6) | 1/发 | 7/14/21/28 | 7/3 | 持续火力堆集束；低骰卡壳 |
| Rotary AC/5 | 5×(1–6) | 1/发 | 6/12/18/24 | 10/6 | 同上 |
| Rotary AC/10 | 10×(1–5) | 3/发(≤15) | 5/10/15/20 | 14/8 | 同上 |
| Rotary AC/20 | 20×(1–4) | 7/发(≤28) | 4/9/13/17 | 16/12 | 最重持续火力炮 |

### HVAC（长身管精密反装甲炮；**命中随距递进**、航空满 AV；非殉爆）
命中修正：short 0 / med −1 / long −1 / ext −2。
| 武器 | dmg | heat | S/M/L/E | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Improve HVAC/10 | 10 | 3 | 8/16/24/32 | 10/6 | 距离越远越准 |
| Improve HVAC/12 | 12 | 4 | 7/14/21/28 | 11/7 | 同 |
| Improve HVAC/14 | 14 | 5 | 7/13/20/27 | 12/8 | 同 |
| Improve HVAC/16 | 16 | 6 | 6/12/19/26 | 13/8 | 最重精密 HVAC |
| Enhanced HVAC/10 | 10 | 2 | 8/16/24/32 | 8/4 | 同精度，更轻更冷 |
| Enhanced HVAC/12 | 12 | 3 | 7/14/21/28 | 9/5 | 同 |
| Enhanced HVAC/14 | 14 | 4 | 7/13/20/27 | 10/6 | 同 |
| Enhanced HVAC/16 | 16 | 5 | 6/12/19/26 | 11/6 | Enhanced 减重减热 |

## 弹道 — Gauss / HAG

| 武器 | dmg | heat | S/M/L/E (min) | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Gauss Rifle | 15 | 1 | (min2) 7/15/22/33 | 12/6 | Clan 重量，比 IS Gauss 轻 3 吨 |
| Enhanced Gauss | 15 | 1 | (min2) 9/18/27/40 | 10/5 | 线内最远最轻标准 Gauss |
| Light Gauss | 8 | 1 | (min3) 8/17/25/37 | 10/4 | 轻伤长射，瘦机用 |
| Ultra-Light Gauss | 6 | 1 | (min3) 7/15/22/33 | 7/3 | 最轻 Gauss |
| Heavy Gauss | var 25/20/10 | 2 | (min4) 6/13/20/30 | 16/10 | 攻城炮，伤随距衰减 |
| Improve Heavy Gauss | 22 | 3 | (min3) 7/13/20/30 | 18/10 | 平伤无衰减 |
| Enhanced Heavy Gauss | 25 | 4 | (min3) 7/14/21/32 | 17/9 | 平 25 伤无衰减 |
| Super-Heavy Gauss | var 35/30/25 | 5 | (min4) 6/13/20/30 | 22/14 | 线内最大单发；衰减攻城，超重底盘 |
| Magshot Gauss | 3 | 1 | 4/8/12/16 | 0.5/1 | 比正史 Magshot 更远的轻 Gauss |
| AP Gauss | 3 (2d6 反步兵) | 1 | 3/6/9/12 | 0.5/1 | 专职反人员 Gauss |
| LB-X Gauss | 15（实心/霰弹） | 1 | (min2) 7/15/22/33 | 15/7 | Gauss 射程 + LB-X 双模 |
| Improve LB-X Gauss | 15（实心/霰弹） | 1 | (min2) 8/16/24/36 | 13/6 | 更轻更远 |
| Heavy LB-X Gauss | 20（实心/霰弹） | 2 | (min3) 6/13/20/30 | 19/11 | 重型双模 Gauss |
| HAG/20 | 20 集束 | 3 | (min2) 9/17/25/33 | 7/6 | 集束 Gauss，比 Clan HAG 更冷更远 |
| HAG/30 | 30 集束 | 5 | (min2) 9/17/25/33 | 11/8 | 同 |
| HAG/40 | 40 集束 | 7 | (min2) 9/17/25/33 | 14/10 | 同 |
| Heavy HAG/30 | 30 集束 | 8 | (min2) 10/20/30/40 | 15/10 | **+2 集束骰**（更密） |
| Heavy HAG/40 | 40 集束 | 10 | (min2) 10/20/30/40 | 19/13 | +2 集束骰；最密最远 |

## 弹道 — Advance 动能超级炮

| 武器 | dmg | heat | S/M/L/E (min) | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Electromagnetic Lance | 20 / 5 | 3 | (min3) 9/18/26/34（满功率） | 18/11 | Advance 线圈炮，**功率门控**：满功率 20 点固有 AP 长射；降功率缩到 5/10/15/20 失 AP，改投载荷（Flak / Flechette / Precision −2 / Incendiary） |
| Coil Augmented Railgun | 30 平 | 6 | 9/18/27/36（无最小） | 22/13 | Advance 动能顶点，30 平伤无衰减、固有 AP（穿甲暴击）；前 12 发稳定，之后 +2 命中且 natural-2 卡壳，累积 +5 报废；单向 **Shed Barrel** 模式 → 25 伤稳定近战（4/6/9/12、无 AP、无磨损） |

## 弹道 — 机枪 / 机炮

| 武器 | dmg | heat | S/M/L/E | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Machine Gun | 2 (2d6 反步兵) | 0 | 2/4/6/8 | 0.5/1 | 射程翻倍于正史 MG，200 发/吨 |
| Light MG | 1 (1d6 反步兵) | 0 | 3/6/9/12 | 0.5/1 | 约长 50%，200 发/吨 |
| Heavy MG | 3 (3d6 反步兵) | 0 | 2/4/6/8 | 1/1 | 三倍正史射程，100 发/吨 |
| MG Array (Light/Std/Heavy) | 链接 2–4 挺 | 0 | 同基座 | — | 链接同型 MG 齐射（Linked/Off），BV 0（价并入 MG） |
| Machine Cannon | 3 (6d6 反步兵) | 1 | 3/6/9/12 | 1.5/2 | 补 MG 与 AC 之间的空档，支持 Flak 弹 |
| Heavy Machine Cannon | 5 (10d6 反步兵) | 2 | 2/5/8/11 | 2.5/3 | 更重版 + Flak 弹 |

---

## 导弹 — LRM

核心 LRM 带 **远距档 −1 命中** + 间接火力；`(min6)` 除非 Enhanced（无最小射程）。ER-LRM 换长档 bonus 得极距。

| 武器 | rack | heat | S/M/L (min, ext) | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Improve LRM 5/10/15/20 | 5–20 | 2/4/5/6 | (min6) 7/14/24 (ext30) | 1.5–7 | OS 轻量核心 LRM，远档 −1；可间接 |
| Enhanced LRM 5/10/15/20 | 5–20 | 2/4/5/6 | 7/14/24 (ext30) | 1–5 | 远档 −1 **且无最小射程**，Clan 重量 |
| Heavy LRM 10/20/30 | 10–30 | 4/6/9 | (min6) 7/14/24 (ext30) | 5/10/14 | 单/Ultra 双齐射（双弹双热，**无卡壳**）；远档 −1 |
| ER LRM 10/15/20 | 10–20 | 6/9/12 | 12/22/38 | 6/9/13 | 极距，Artemis IV 兼容；无长档 bonus |
| Streak LRM 5/10/15/20 | 5–20 | 2/4/6/8 | (min6) 7/14/24 (ext30) | 2.5–11 | 锁定齐射（锁上则全中）；无长档 bonus |
| Extended Streak LRM 10/15/20 | 10–20 | 6/9/12 | (min10) 12/22/38 (ext57) | 8/11/14 | 极距 + 保证命中 |

## 导弹 — SRM

| 武器 | rack | heat | S/M/L (ext) | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Heavy SRM 4/6/8/12 | 4–12 | 3/4/5/7 | 4/8/12 (ext16) | 3–9 | 延伸射程；单/Ultra 双齐射（**无卡壳**） |
| Streak SRM 2/4/6/8 | 2–8 | 2/3/4/6 | 3/6/9 (ext12) | 1.25–5 | 锁定齐射，锁上全中 |
| Improve Streak SRM 2/4/6/8 | 2–8 | 2/3/4/6 | 4/8/12 (ext16) | 1–4 | Clan 级锁定，延伸射程 |

## 导弹 — MRM（超大中距档 −1 命中；配 Diana III 再叠 −1）

| 武器 | rack | heat | S/M/L (ext) | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Improve MRM 10/20/30/40 | 10–40 | 4/6/10/12 | 3/13/16 (ext20) | 3–12 | 巨大中距档全程 −1；配 Diana III FCS 再 −1 |
| Streak MRM 10/20/30 | 10–30 | 4/6/10 | 3/13/16 (ext20) | 6/12/17 | 锁定 MRM，保证命中，但刻意重/占位以作权衡 |

## 导弹 — MML（双模 LRM / SRM）

| 武器 | rack | heat | LRM 档 | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| MML 3/5/7/9 | 3–9 | 2–5 | (min6) 7/14/21 | 1.25–5 | 双模发射器，比 IS MML 轻 |
| Heavy MML 5/8/11/14 | 5–14 | 6/8/12/14 | (min6) 7/14/21 | 4–12 | 双模 + 单/Ultra 双发（**无卡壳**） |
| Streak MML 5/7/9/11 | 5–11 | 3/4/5/6 | (min6) 7/14/21 | 3.5–6.5 | 双模 + Streak 锁定（不兼容 Artemis） |

## 导弹 — ATM（可变载荷：Extended / Standard / High-Explosive）

| 武器 | rack | heat | S/M/L/E (min) | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| ATM 3/6/9/12 | 3–12 | 2/4/6/8 | (min4) 5/10/15/20 | 1.5–7 | 装 LRTM / SRTM / APTM 改射程与单发伤 |
| Improve ATM 3/6/9/12 | 3–12 | 2/4/6/8 | (min4) 5/10/15/20 | 1.5–7.5 | iATM 级，更丰富载荷、更高性能 |

## 导弹 — Dragon Piercer（单枚大穿甲弹头，非集束；穿甲暴击）

| 武器 | dmg | heat | S/M/L | t/c | 机制 |
|---|--:|--:|:--:|:--:|---|
| Dragon Piercer 5/10/15/20 | 5–20 | 3/5/7/10 | 5/10/15 … 20/25/30 | 3–11 | 单枚 AP 弹头，穿甲暴击 **8+** |
| Improve Dragon Piercer 5/10/15/20 | 5–20 | 3/5/7/10 | 同上 | 2.5–9.5 | 更轻，穿甲暴击加深到 **7+** |

## 导弹 — Narc / 信标（0 伤，挂导引 pod）

| 武器 | heat | S/M/L/E | t/c | 机制 |
|---|--:|:--:|:--:|---|
| Narc Missile Beacon | 3 | 3/6/9/12 | 3/2 | 挂导引 pod，友方 Narc 导弹保证集束命中 |
| Seeker Missile Beacon | — | 3/6/9/12 | 2.5/2 | 轻量 Narc 信标，同 pod |
| Munin Missile Beacon | — | 4/9/15/18 | 5/3 | iNarc 级多 pod：Homing / ECM / Haywire / Nemesis |

## 导弹火控（装在发射器旁增益）

| 件 | t/c | 机制 |
|---|:--:|---|
| Artemis IV FCS (OS) | 1/1 | 与正史 Artemis IV 相同（+2 集束）——IS 复刻，仅 OS 技术基座 |
| Diana III FCS | 1/1 | MRM 专用（对标 Apollo）：给 OS MRM **集束 bonus + 额外 −1 命中** |
| Orion V FCS | 1.5/2 | 高端整合：集束 **+3**（vs Artemis +2）；对 Streak 改助锁（锁定骰 −1 + 免标准 ECM 断锁） |

---

## 备注

- **未列的标准武器**（AC/2·5·10·20、标准 LB-X 2/5/10/20、标准 Ultra AC、PPC、ER PPC、标准大/中/小激光、Improve Gauss、无机制的 Improve/Enhanced 变体）均为 IS 复刻或纯数值版，机制与正史一致，具体 BV/吨位见 `OS_WEAPONS_REFERENCE.md`。
- **测试状态**：本表所有武器均**仅过编译 + 装备初始化单元测试，未经对局实测**（见 `OS_COMPONENT_PLAYTEST_CHECKLIST.md`）。
- **相关文档**：`OS_TECHBASE_DEVELOPMENT_PLAN.md`（设计/实现，§7 AC munitions、§8 Advance 动能、§2 HVAC）；`CANON_AMMO_REFERENCE.md`（弹药）；`equipmentmessages.properties`（游戏内 ⓘ info 原文）。
