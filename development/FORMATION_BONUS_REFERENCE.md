# Formation & Formation Bonus 移植参考（AS → TW）

> **用途**：最高优先开发项"把 Alpha Strike 的 Formation Bonus 移植进 TW/classic 引擎"的**源数据转录 + 实现草稿**。
> **来源**：Alpha Strike: Commander's Edition (CAT35680)，编队 pp. 116–123、SPA pp. 92–101、SCA pp. 102–103。
> **状态**：草稿 / 待设计决策（见 §5）。所有数值均为**转录**，非臆测。
> 关联：`OS_TECHBASE_DEVELOPMENT_PLAN.md` §"⭐⭐ TOP PRIORITY"、`开发日志.md`。

---

## 0. 结论速览：编队的正史来源 + 仓库现状

**问：MekBay 上那些多出来的编队（Berserker/Close Combat Lance 等）是 MekBay 自创还是正史？**
**答：正史，非自创。** 它们出自 **Campaign Operations**（CGL 官方 classic BT 规则书）的"Force/Formation Building"。证据在本仓库代码里：

- `megamek/megamek/src/megamek/client/ratgenerator/FormationType.java` 的类注释即 **"Campaign Operations rules for force generation" (@author Neoancient)**，其中定义了 **40 种编队**，明确含 `"Berserker/Close"`（第 1630 行，parent=Battle、idealRole=BRAWLER）。
- MekBay 是基于 MegaMek 数据的第三方工具，它的编队清单基本就是这份 Campaign Operations 列表。

**两个正史来源的区别（关键）：**
| | Campaign Operations（classic BT） | Alpha Strike（Companion / Commander's Ed.） |
|---|---|---|
| 编队数量 | 多（40，含 Berserker/Close、Rifle、Hunter…） | 少（8 家族 + 变体） |
| 内容 | **只有组成要求**（用于建军/随机生成） | 组成要求 **+ Formation Bonus（免费 SPA/SCA）** |
| 仓库现状 | **已实现**于 `FormationType.java`（仅 RAT 用途，无战斗加成） | **未实现**（本文转录其 Bonus） |

**对移植的直接影响：**
1. **组成/资格判定层**：`FormationType.java` 已用 **TW 原生度量**（weight class + WalkMP + `UnitRole` + 匹配对约束 DSL）实现好了——比 AS 的 Size/Move 更贴 TW，**可直接复用/参考**，不必从零写。
2. **加成层**：AS CE 只给下面 §2 这批编队定义了 Bonus。`FormationType.java` 里那些 CampOps 独有编队（Berserker/Close、Rifle、Hunter、Anvil、Order、Light Fire、Security、Hammer、Horde、Ranger、Urban、Anti-Mek）**在 CE 里没有 Bonus**——若要给它们加成，需另找 **AS Companion（2014）**（CE 精简掉了这些）。对照见 §3。

---

## 1. 通用规则（AS CE pp. 116–117）

- **不可混合/叠加**：一支编队即使同时满足多种类型标准，也只能登记为**一种**。
- **标准组成**：IS lance=4 单位；Clan Star=5 'Mech / 10 车 / 5 对战机；ComStar/WoB Level II=6 混编；IS 航空 squadron=6（3 对）；Clan 航空 Star=10（5 对）。
- **Requirements**：百分比按占比、**按需向上取整**（Battle「50% ≥Size 3」：lance→2、Star 5×0.5=2.5→**3**、Level II 6×0.5→**3**）。
- **Ideal Role = 万能豁免**：若**全部**单位皆为该 ideal role，则豁免其它所有要求。
- **Bonus Ability**：每编队 ≥1 个加成，多为免费 SPA。**场上仍有 ≥3 个未毁/未溃成员时持续生效**。带单位类型要求的 SPA 若单位不匹配则无效。与标准 SPA 规则并用时重复能力不叠加（除非该 SPA 自身允许）。
- **Variations**：变体的要求/加成**替换**基础版；变体必须满足自身要求，不能仅靠 ideal role 达成。
- **SPA 点数无关**：AS 里 SPA 有购买点数（PV），但**编队免费授予**，故 §4 的 cost 列**对移植无意义**，仅存作转录完整性。

---

## 2. AS CE 编队清单（要求 + Bonus）

> Size = 单位体型 1–4（≈TW 轻/中/重/突击）；Move 单位为寸（10"、8"j=跳跃 8 寸）。

### 2.1 地面编队（pp. 117–121）

**BATTLE（战斗）** — ideal: Brawler
- 要求：50% ≥Size 3；≥3 单位为 Brawler/Sniper/Skirmisher。
- **Bonus：全队等效 `Lucky`，等级 =（建队单位数 + 2）**（4 单位→6 级）；任意成员可用。可与自带 Lucky 叠加，但整场每单位**最多 4 次重投**。
  - **Light Battle**：≥75% Size 1，无 Size 4+（车辆版≥2 对匹配 Size 1）；≥1 Scout。Bonus 同上。
  - **Medium Battle**：≥50% Size 2，无 Size 4+（车辆版≥2 对匹配 Size 2）。Bonus 同上。
  - **Heavy Battle**：≥50% ≥Size 3，无 Size 1（车辆版≥2 对匹配 Size 3+）。Bonus 同上。

**ASSAULT（突击）** — ideal: Juggernaut
- 要求：≥3 单位 ≥Size 3，无 Size 1；所有单位装甲 ≥5；≥75% 中程攻击值 ≥3；≥1 Juggernaut 或 ≥2 Sniper。
- **Bonus：开局选 `Demoralizer` 或 `Multi-Tasker`；每回合可指定至多半数（下取整）单位本回合获得所选能力。** 使用者可逐回合换，但能力本身整场不变。
  - **Fast Assault**：加所有单位地面 Move ≥10" 或有跳跃。Bonus：在上之外，每回合至多 2 单位额外获 `Stand Aside`（可与获 Demo/Multi 者不同 → 一单位可能同回合 2 个 SPA）。

**STRIKER/CAVALRY（突袭/骑兵）** — ideal: Striker
- 要求：所有 Move ≥10" 或跳跃 ≥8"j；无 Size 4+；≥50% 为 Striker/Skirmisher。
- **Bonus：75%（正常取整）获 `Speed Demon`。**
  - **Light Striker/Cavalry**：所有 Move ≥10"；无 Size 3+；≥2 单位长程攻击值 >0；≥2 Striker/Skirmisher。Bonus 同上。
  - **Heavy Striker/Cavalry**：所有 Move ≥8"；≥3 单位 ≥Size 3、无 <Size 2；≥1 长程攻击值 >1；≥2 Striker/Skirmisher。Bonus 同上。

**FIRE（火力）** — ideal: Missile Boat
- 要求：≥75% 为 Missile Boat/Sniper。
- **Bonus：每回合开始，至多半数（下取整）本回合获 `Sniper` SPA。**
  - **Fire Support**：≥3 单位有 IF#。Bonus：每回合至多半数（下取整）获 `Oblique Attacker`。
  - **Artillery Fire**：≥2 单位有 ARTX-#。Bonus：每回合至多半数（下取整）获 `Oblique Artilleryman`。
  - **Direct Fire**：≥2 单位 ≥Size 3，所有单位长程攻击值 ≥2。Bonus：每回合至多半数（下取整）获 `Weapon Specialist`。
  - **Anti-Air**：在 Fire 基础上，≥2 单位有 FLK#/AC#/#/#/ARTX-#。Bonus：每回合至多半数（下取整）获 **`Anti-Aircraft Specialists` (SCA)** 效果。

**RECON（侦察）** — ideal: Scout
- 要求：所有 Move ≥10"；≥2 单位 Scout/Striker。
- **Bonus：开局选 `Eagle's Eyes` / `Forward Observer` / `Maneuvering Ace` 之一，全队每单位获之（整场不可换）。**
  - **Light Recon**：全 Size 1、Move ≥12"、**全 Scout**。Bonus：同上，但**每单位可获不同 SPA**。
  - **Heavy Recon**：所有 Move ≥8"（其中≥2 达 ≥10"）；≥1 单位 ≥Size 3；≥2 Scout。Bonus：同上，但**只有至多半数（上取整）**获所选 SPA。

**PURSUIT（追击）** — ideal: Striker
- 要求：所有 ≤Size 2；75%（正常取整）Move ≥12"；≥1 单位中程攻击值 >1。
- **Bonus：75% 获 `Blood Stalker`。** 特例：可选敌方**一个编队**（非单一单位）为目标；若如此全队须选同一编队，且仅当该编队被摧毁方可改选。
  - **Probe**：所有 ≤Size 3；75% Move ≥10"；所有单位中程可造 ≥2 伤。Bonus 同上。
  - **Sweep**：所有 ≤Size 2、Move ≥10"；所有单位短程可造 ≥2 伤。Bonus 同上。

**COMMAND（指挥）** — ideal: None
- 要求：≥1 单位指定为指挥官/关键副官；50% 为 Sniper/Missile Boat/Skirmisher/Juggernaut；另 1 单位为 Brawler/Striker/Scout。
- **Bonus：开局前半数（上取整）单位各免费获以下之一（可不同）：`Antagonizer`/`Blood Stalker`/`Combat Intuition`/`Eagle's Eyes`/`Marksman`/`Multi-Tasker`。此外指挥官单位获 `Tactical Genius`；若已用完整 SPA 规则且指挥官已有 Tactical Genius，则改为部队先攻掷骰结果 +1。**
  - **Vehicle Command**：同标准（1 指挥官单位），但只需 1 对战车为上述 4 角色之一。Bonus 同上。

**SUPPORT（支援）** — ideal: None ｜ 要求：None
- **Bonus：开局前指定本军另一编队为支援对象。半数（下取整）单位获得与被支援编队相同的 SPA（每种数量不超过对方所得）。** 若对方是"每回合分配"型，须开局一次性分配且不可转移。仍有 ≥3 活跃单位即保留。支援 Command Lance 时获其非指挥官单位的 2 个 SPA，但不获 Tactical Genius。

### 2.2 组合运输/步兵 + 空中连（p. 121）

**MECHANIZED / NOVA** — 建在既有非步兵编队之上；非步兵成员满足并获该基础编队要求与加成；两者各算独立编队。
- 要求：非步兵单位须能同时运输全部步兵（MEC/OMNI、XMEC 载 'Mech、IT# 或组合）。
- **Bonus（二选一）**：
  - **Mechanized**：非空降地面移动时可卸载步兵，卸载后运输单位可续用剩余 Move。
  - **Nova**：搭载步兵可发动武器攻击，用运输单位移动修正，且 **+2 目标数**。

**AIR LANCE** — IS 合成编队 = 1 个地面（非步兵）lance + 1 对**完全相同**的战机。
- **Bonus：无额外加成**；战机不享地面 lance 加成，也不计入任何要求。

### 2.3 航空编队（pp. 122–123）

| 编队 | 要求 | Bonus |
|---|---|---|
| **Interceptor Squadron** | >50% Interceptor | Move(Thrust) ≤9 者获 `Speed Demon`；另至多 2 架获 `Range Master (Long)` |
| **Aerospace Superiority Squadron** | >50% Interceptor 或 Fast Dogfighter | 开局前选至多 50%，分配至多 2 种（任意组合）：`Blood Stalker`/`Ride the Wash`/`Hot Dog` |
| **Fire Support Squadron** | ≥50% Fire Support，其余 Dogfighter | 开局前选 2 对，各配 1 种（两对不同）：`Golden Goose`/`Ground Hugger`/`Hot Dog`/`Shaky Stick` |
| **Strike Squadron** | >50% Attack 或 Dogfighter | 至多 50% 获 `Speed Demon`，其余获 `Golden Goose` |
| **Electronic Warfare Squadron** | 无角色要求，但 >50% 须有 PRB/AECM/BH/ECM/LPRB/LECM/LTAG/TAG/WAT 之一 | 获 **`Communications Disruption` (SCA)**；若部队已有该 SCA，可自选受扰目标而非随机 |
| **Transport Squadron** | 可含支援机/常规/航空战机/Small Craft/DropShip；≥50% Transport | 选 1 种应用于所有 Transport 角色单位：`Dust-Off`/`Ride the Wash`/`Wind Walker` |

---

## 3. Campaign Operations（代码已有）vs AS CE（有 Bonus）对照

| 分类 | 编队 |
|---|---|
| **两者都有**（`FormationType.java` 有组成 + AS CE 有 Bonus → 移植最省事） | Battle / Light·Medium·Heavy Battle / Assault / Fast Assault / Striker·Cavalry / Light·Heavy Striker·Cavalry / Fire / Fire Support / Artillery Fire / Direct Fire / Anti-Air / Recon / Light·Heavy Recon / Pursuit / Probe / Sweep / Command / Vehicle Command / 全部 6 种航空 squadron |
| **仅 AS CE 有 Bonus**（代码里无对应 FormationType，属 AS 特有构造） | **Support**、**Mechanized/Nova**、**Air Lance** |
| **仅 Campaign Operations 有**（代码已实现组成，但 **AS CE 无 Bonus**；要加成得查 AS Companion） | **Anti-Mek, Anvil, Hunter, Rifle, Berserker/Close, Order, Light Fire, Security, Hammer, Horde, Ranger, Urban** |

> 注意：两系统的组成度量不同——AS CE 用 **Size + Move(寸)**，`FormationType.java` 用 **weight class + WalkMP + `UnitRole`**。移植走 TW，应以 `FormationType.java` 的 TW 原生度量为准，把 AS 文本要求"翻译"到它上面（多数已一致）。

---

## 4. Formation Bonus 引用到的 SPA / SCA —— 效果全表

> 以 AS CE **详细条目**为准（p.94–95 速查表在 PDF 里有排版错位，且部分 cost 与详条不一致——**详条优先**）。移植时应逐条判断：① TW 已有等价机制可直接映射；② 需 TW 原生重表达；③ 暂缓。

### 4.1 SPA（Special Pilot Abilities）

| SPA | 页 | 效果（转录浓缩） |
|---|---|---|
| **Lucky** | 97 | 每购买点：每场可重投 1 次失败的攻击掷骰**或** 1 次失败 Control Roll；第二次结果作数。不用于暴击/破壳/先攻/士气。编队版等级=单位数+2、全队共享。 |
| **Speed Demon** | 99 | 地面（含 VTOL/WiGE）+2" Move/回合、Sprint +4"；**不改 TMM**；航空 +1 有效 Thrust。 |
| **Demoralizer** | 93 | 战斗阶段，选 LOS 内+中程内 1 敌；2D6 vs TN=8+己 Skill−己 SZ；成功→目标 −2"MV、−1 TMM（底 2"/0）、对本单位攻击 +1 TN；本回合 End Phase 起持续到下回合 End Phase；>24"/失 LOS 解除；对航空无效。 |
| **Multi-Tasker** | 98 | 同回合选 2 目标分别结算；每个被拆分攻击伤害减半（下取整、底 1）。 |
| **Stand-Aside** | 99 | 额外花 1" Move 可穿过敌单位（无伤、破 stacking 限制）；且免疫敌 Zone of Control。 |
| **Sniper** | 99 | 中/长/极程修正降为 **+1/+2/+3**；不影响短/地平；对 IF/ART 无效。 |
| **Oblique Attacker** | 98 | IF 间接攻击 −1 TN；**可无友方 spotter 间接开火**（此时把 −1 换成 +2，替换所有 spotter 相关修正）。 |
| **Oblique Artilleryman** | 98 | ART 间接/离场攻击 −1 TN；含反炮兵掷骰宽容。 |
| **Weapon Specialist** | 101 | 标准武器攻击**差 1 未命中**时改为造半伤（下取整、底 1）。 |
| **Eagle's Eyes** | 95 | 探测类（BH/PRB/LPRB）+2" 距离，并赋 RCN；2" 内隐藏单位自动暴露（无视 ECM）；避雷 +2 TN。 |
| **Forward Observer** | 96 | 可为多次同目标炮击当 spotter；若 FO 自身也开火，其所标的间接攻击不吃"spotter 开火"修正。 |
| **Maneuvering Ace** | 97 | 穿林/丛林每寸 −1" 成本；航空大气 Control Roll +2→+1。 |
| **Blood Stalker** | 93 | 开局定"chosen enemy"：对其 −1 TN、对其它敌 +2 TN，直到其被毁；若移动开始时其失 LOS/被毁可改选。编队版可改选敌"编队"。 |
| **Antagonizer** | 92 | 战斗阶段**代替攻击**，选短程内 1 敌；2D6 vs TN=5+Skill；成功激怒（本→下 End Phase）：被激怒者须最短路径逼近本单位、只能攻击本单位；>24"/失 LOS 解除；对航空无效。 |
| **Combat Intuition** | 93 | 若己方赢先攻，本单位可在 Movement Phase 移动并结算全部攻击、立即施加伤害（先于目标行动）；**每 3 回合 1 次**。 |
| **Marksman** | 97 | 若原地不动：命中只造半伤（底 1），但若 MoS≥3 追加 1 次暴击检定（即便目标仍有装甲）。 |
| **Tactical Genius** | 100 | 若为己方指挥单位：先攻被压过时可重投 1 次（结果作数，即便更差）；**每 2 回合 1 次**；开 Battlefield Intelligence 时视作 MHQ4。 |
| **Golden Goose** | 96 | 对地：扫射/打击 −1 TN、投弹 −2 TN。 |
| **Ground Hugger** | 96 | 对地：单次进场可执行"双扫射"或"双打击"。 |
| **Hot Dog** | 97 | 热等级视作低一档：4 点热才自动停机（非 3）；4 点热时 −6" 地面移动、−1 TMM、+3 TN（而非停机）。 |
| **Shaky Stick** | 99 | 空中单位：来自**地面**的对空攻击对其 +1 TN（不影响空对空）。 |
| **Ride the Wash** | 98 | 空中：大气 Control Roll +2→**+0**；AF/CF 在短程尾随时可迫使目标额外 Control Roll +3。 |
| **Range Master (Long)** | 98 | 选一档（非短/地平）专精：该档 −2 TN、短程 +2 TN。长程版档位修正表 S+2/M+2/L+2/E+6。 |
| **Dust-Off** | 95 | 起降：Inappropriate Landing Area 的 Control Roll 修正 +2→+1。 |
| **Wind Walker** | 101 | 空中：免除 +2 大气 Control Roll；所有起降额外 −1 Control Roll。 |

### 4.2 SCA（Special Command Abilities）

| SCA | 页 | 效果（转录浓缩） |
|---|---|---|
| **Anti-Aircraft Specialists** | 102 | 对**空中**目标（VTOL/WiGE/航空/常规战机/Small Craft/DropShip）所有攻击 **−2 TN**；对地面单位（含跳跃）或着陆的可飞单位改为 **+1 TN**。航空单位不能使用本 SCA。 |
| **Communications Disruption** | 103 | 需敌方已分组（无则默认 4 单位 lance）。每回合开始掷 1D6，逢 **6** 使敌方**随机 1 个 lance/Star/Level II** 本回合 Movement Phase −4" Move（底 1"）／航空 −1 base Thrust；回合末解除。（编队变体：若部队已有该 SCA 则可**自选**目标。）（Battlefield Intelligence 规则下需 BI 比 ≥2:1。） |

---

## 5. 开发分层与范围（✅ 2026-08-01 用户拍板）

**两层开发：**
- **Tier 1（第一优先，立即开发）= 非 CamOps 的 AS CE Formation Bonus**：§2 全部带 CE Bonus 的编队——Battle/Assault/Striker·Cavalry/Fire/Recon/Pursuit/Command + 变体、6 种航空 squadron，及 Support/Nova/Air Lance。数据齐全（§2/§4）。
- **Tier 2（第二优先，其后开发）= CamOps 独有编队**：Berserker/Close、Rifle、Hunter、Anvil、Order、Ranger、Urban、Hammer、Horde、Security、Anti-Mek、Light Fire（§3 第三行）。组成已在 `ratgenerator/FormationType.java`，但 **CE 无 Bonus** → 需从 AS Companion 转录或自设计加成（TBD）。列为本移植的第二阶段。

## 6. 实现架构（2026-08-01，据代码勘查确定）

**核心策略：AS 的"授予免费 SPA"→ 直接授予 TW 引擎已有的同名飞行员选项（`Crew.getOptions()`）。** TW 已实现并结算多数对应能力，"授予"即生效，无需重写机制；这也天然是 TW 原生表达（decision 2 就此定案）。

**关键代码事实（勘查所得）：**
- **Force→活单位**：`Force.getEntities()` 得 id → `game.getInGameObjects(ids)` 得活 `Entity`；`Forces.getFullEntities(force)` 递归含子编队；`Entity.getForceId()`。
- **UnitRole**：`Entity.getRole()`；枚举 = 地面 8（AMBUSHER/BRAWLER/JUGGERNAUT/MISSILE_BOAT/SCOUT/SKIRMISHER/SNIPER/STRIKER）+ 航空 6 + `UNDETERMINED`/`NONE`。**⚠️ TW 场上 role 常为 UNDETERMINED（除非单位文件指定）**——检测须优雅降级（不合格即不给加成；将来 UI 提示补 role）。
- **`ratgenerator/FormationType.java` 只吃 `MekSummary`，不吃活 Entity**——其约束模型（Percent/Count/Grouping、idealRole、weight class、WalkMp）可参照，但**需另写吃 `Entity` 的评估器**（`Entity.getWeightClass()`/`getRole()`/`getWalkMP()`/`getJumpMP()`）。
- **SPA 授予/查询**：`entity.hasAbility(OptionsConstants.X)` 查；`crew.getOptions().setOption(name, true)` 授予。
- **AS SPA → TW 选项映射（EXISTS 者直接授予）：**
  - Battle → Lucky ⇒ **EDGE**
  - Fire → Sniper ⇒ **GUNNERY_SNIPER**
  - Fire Support → Oblique Attacker ⇒ **GUNNERY_OBLIQUE_ATTACKER**
  - Direct Fire → Weapon Specialist ⇒ **GUNNERY_WEAPON_SPECIALIST**
  - Pursuit → Blood Stalker ⇒ **GUNNERY_BLOOD_STALKER**
  - Assault → Multi-Tasker ⇒ **GUNNERY_MULTI_TASKER**（Demoralizer 无 TW 选项）
  - Recon → Forward Observer ⇒ **MISC_FORWARD_OBSERVER** / Maneuvering Ace ⇒ **PILOT_MANEUVERING_ACE**（Eagle's Eyes 无）
  - Command → Tactical Genius ⇒ **MISC_TACTICAL_GENIUS** + Blood Stalker/Multi-Tasker（Antagonizer/Combat Intuition/Marksman/Eagle's Eyes 无）
  - **无 TW 选项、需原生实现或暂缓**：Speed Demon（Striker 招牌，+2" 移动）、Demoralizer、Marksman、Eagle's Eyes、Anti-Aircraft Specialists(SCA)、航空对地/大气类（Golden Goose/Ground Hugger/Shaky Stick/Ride the Wash/Wind Walker/Dust-Off）、Communications Disruption(SCA)。
- **生命周期**：加成"仅当编队 ≥3 活跃成员时有效"→ 每回合刷新（授予/收回选项）。参照 BCS：`InitiativeBonusBreakdown`（record）+ `Player.getBcsCoordinationInitBonus()`（每轮逐单位算）+ `Team.getInitBonusBreakdown()`（聚合）。
- **门控**：新增布尔游戏选项，`OptionsConstants` 常量 + `GameOptions.initialize()` 注册 + `game.getOptions().booleanOption(...)` 读。默认关，原版 TW 不变。
- **to-hit 注入点（若需原生 to-hit 加成）**：`ComputeToHit.compileWeaponToHitMods()`（OS 网络 −1 模板在 ~1645-1656，参 `OSNetworkDesignators.networkCoordinationToHit`）。

**Tier 1 分期：** P1 游戏选项；P2 编队检测器（吃 Entity）；P3 授予-已存在-TW-选项 的编队（Battle/Fire/FireSupport/DirectFire/Pursuit/Assault/Command/Recon）+ 每回合刷新 + 生命周期挂点；P4 原生补缺（Speed Demon 等）；P5 航空 squadron + Support/Nova/Air Lance；P6 大厅 UI 显示编队 + 加成。（其后 → Tier 2 CamOps 编队。）

**仍待决（编码时逐条定）：**
- 叠加/上限：编队授予的 SPA 与 OS BCS/C3 网络 −1、§9.3/§9.6 上限的交互。
- "每回合分配至多半数"（Assault/Fire/…）：自动给前半 vs 玩家选。

---

*本文为转录 + 草稿；SPA/SCA 效果以英文规则原文为准，实现前请核对 TW 侧是否已有等价机制。*
