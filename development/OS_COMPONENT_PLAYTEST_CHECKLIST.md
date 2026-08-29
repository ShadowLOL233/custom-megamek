# OS 组件对局测试清单（Playtest Checklist）

> 生成 2026-08-10。用途：把所有"已实现但**未经对局(in-game)实测**"的 OS 组件汇总成可逐条勾选的测试表，供跨设备实机验证与调参。
>
> **状态术语**：`编译过` = `:megamek:compileJava` 通过；`装备测试过` = EquipmentType/AmmoType/EquipmentTypeLookup 单元测试通过（装备正确初始化）；`对局测试` = 实机进对局验证效果——**本清单的目标全部停在这一步之前**。
>
> **总体结论**（计划文档 §Playtest priority queue 原话）：目前所有已交付的 OS 组件都只过了"编译 + 装备初始化单元测试"，**没有一件经过对局实测**。已获实机验收通过的都是大厅/UI 功能，非装备。
>
> **进游戏准备**：跨设备导入自定义单位见计划文档 §10（`git pull` → 拷 `development/os-designs/*.mtf` 到 `megamek/userdata/data/mekfiles/` → `megameklab` 重新构建启动）。
>
> 勾选规范：`- [ ]` 待验证 / `- [x]` 已验证通过。每条给出**期望结果**，实测不符时在该行后追加"实测：…"并记入 `开发日志.md`。

---

## 一、导弹系（优先级 P1）

> **状态更新 2026-08-29 — 已进行初步对局测试，正在修改细节中。** 导弹三族做了第二轮重做，当前设计（数值以本更新为准，下方 2026-07-31 首版表保留作对照）：
> - **射程**：LRM min6·7/10/24/30；MRM 3/16/18/20；SRM（含 Heavy）6/8/10/12。
> - **专精档命中**：LRM 远 −1 / MRM 中 −1 / SRM 近 −1（新增旗标 `F_OS_SRM_SHORT_SPEC`）。
> - **集束**：OS 核心导弹内建 **+2 集束**（`WeaponHandler.getClusterModifiers`，按 spec 旗标门控）；FCS 再叠 +2（满配 +4）。约 +21% 命中。
> - **FCS 分级**：Artemis IV / Diana III = 纯 +2 集束；**新增 Artemis V / Diana IV = +2 集束 + −1 命中**，各需专属弹（Artemis IV/V-capable、Diana III/IV-capable）。
> - **改名**：Dragon Piercer → Tactical Heavy Missile Launcher（内部名保留）。
> - ⏳ 待办：BV 未按新强度重算；FCS 吨位/成本/技术年代为初定值；细节按实测手感继续调整。详见 `开发日志.md` 2026-08-29 条。

### 1. OS 导弹射程专精三角 SRM / MRM / LRM（重设计，2026-07-31）
**改动**：把三大集束导弹族重做成短/中/远专精。射程档为纯数据字段；括号内 −1 命中在 `ComputeToHit` 里按射程档给出，旗标 `F_OS_LRM_LONG_SPEC` / `F_OS_MRM_MEDIUM_SPEC`。

| 族 | min | short | med | long | ext | 签名 |
|---|---|---|---|---|---|---|
| SRM（不变） | 0 | 3 | 6 | 9 | 12 | 近战型 |
| MRM（核心） | 0 | 3 | 13 | 16 | 20 | **超大中距档 −1 命中**（取消正史 +1 惩罚） |
| LRM（核心） | 6/0 | 7 | 14 | 24 | 30 | **远距档 −1 命中** + 间接火力 |

- [ ] Core LRM 在 **long 档(15–24)** 命中 −1 触发、其它档不触发 → 期望：仅 long 档吃 −1
- [ ] Core MRM 在 **medium 档(4–13)** 命中 −1 触发；正史 +1 惩罚已消失 → 期望：中距 −1、无 +1
- [ ] **MML（LRM mode）无 long −1**、**Streak LRM 无 long −1**（其优势是锁定不是精度）→ 期望：两者均无远距 −1
- [ ] ER-LRM / Extended Streak / Dragon Piercer 射程与命中**不受影响** → 期望：保持原样
- [ ] BV 数值手感：Core LRM +15%（Improve 5/10/15/20 = 52/104/156/208；Enhanced 59/117/177/236；Heavy 10/20/30 = 155/316/472）；Streak LRM +5%（91/182/273/363）；MRM 持平

**代码位置**：`WeaponType`（`F_OS_LRM_LONG_SPEC`/`F_OS_MRM_MEDIUM_SPEC` 别名）、`actions/compute/ComputeToHit.java`（括号块）、新 `OSMRMWeapon` 基类。

### 2. OS 导弹特殊弹药 SRM / LRM / MRM（2026-07-31）
**改动**：以 `OS_*_MUNITION_MUTATOR` 复用正史 `Munitions.M_*` + 通用 handler（tech-base 无关），零 handler 改动；MRM 因 `MRMHandler` 不分弹种，效果派发经新 `OSMRMWeapon`（复用 LRM 集束 handler）。

- [ ] **SRM 全套**（Inferno / Fragmentation / Smoke / Tandem-Charge / Narc-capable / Heat-Seeking / Listen-Kill / Mine-Clearance / Anti-TSM / Dead-Fire / ARAD / Acid）逐一在 SRM 2/4/6 + MML-SRM 上结算 → 期望：各弹种效果与正史一致
- [ ] **LRM 全套**（Follow-the-Leader / Semi-Guided / Swarm / Swarm-I / Thunder·-Active·-Augmented·-Vibrabomb·-Inferno + Frag/Smoke/Narc/Heat-Seeking/Listen-Kill/Mine-Clearance/Anti-TSM/Dead-Fire/ARAD）在 LRM 5/10/15/20/30 + MML-LRM 上结算
- [ ] **MRM 子集**（Frag/Smoke/Narc/Heat-Seeking/Listen-Kill/Anti-TSM/Mine-Clearance/Dead-Fire）在 Improve MRM 10/20/30/40 上**经 `OSMRMWeapon` 正确派发** → 期望：效果生效（若无效多半是 handler 没走 OSMRM 基类）
- [ ] Thunder/FASCAM 仍**仅限 LRM**；Tandem-Charge/Acid 仍**仅限 SRM** → 期望：MRM 选不到这些
- [ ] Artemis 仍由独立 OS Artemis 块施加（不重复叠加）

**备注**：Incendiary LRM 延后（正史走独立 ammo 非 mutator）；Streak/Heavy-SRM 未纳入；Torpedo 延后。

---

## 二、OS 网络目标指示器 Kestrel / Shrike / Lodestar（优先级 P2，§9.6）
**改动**：C3 网络专用目标指示子族；**脱离激活的 OS C3/BCS 网络即为死重**。已实现于 `weapons/c3/OSNetworkDesignators` + `ComputeToHit` + `ShrikeTAGHandler` + `Munitions.M_LODESTAR`/`INarcPod.LODESTAR`。

| 件 | Tier | t/crit | 作用 |
|---|---|---|---|
| Kestrel Network Target Designator | Improve | 4/3 | 对指定目标网络 −1 直射 + Lodestar 使能 LRM 间接 |
| Shrike Combat Designator | Enhanced | 3/2 | 可暴击 TAG：网络 −1（暴击 −3） |
| Lodestar Guidance Beacon | Improve | Munin(iNarc) pod ×4 | 植入远程锁，供网络内 Kestrel 读取 |

- [ ] **Kestrel −1 直射**：本机在激活 C3/BCS 网络且（a）对目标有 LOS 或（b）目标带友方 Lodestar beacon 且在 Kestrel 射程内 → 全网络对该目标所有直射 −1；脱网/无 LOS 且无 beacon 时消失
- [ ] **Kestrel + Lodestar 使能 LRM 间接**：目标带友方 Lodestar、网络内有 Kestrel 在射程 → LRM/LRM-IMP/MML/NLRM **无常规 spotter 也能间接开火**（self-spot）
- [ ] **Shrike −1 / −3**：成功指定后，同网络单位本回合攻击该目标 −1；指定骰 **natural 9+** 暴击时加深到 −3；`newRound` 重置
- [ ] **Lodestar pod**：从 OS Munin/iNarc 发射，命中挂 `INarcPod.LODESTAR`（队属、持续到移除）；4 发/12,000 C-bill
- [ ] **反叠加上限**：Kestrel −1 / Shrike −1(−3) / B-2500 协同 −1 三者**只取最深的一个**（`networkCoordinationToHit`，全程只应用一次）；CCS 模块 −1 可另叠 → 直射最多 −2
- [ ] **ECM 可压制**：目标受 ECM 影响时以上网络效果被跳过

**代码位置**：`ComputeToHit`（Kestrel 被动 + Shrike 读取 + Lodestar 间接 self-spot）、`ShrikeTAGHandler`、`NarcHandler`（挂 pod）、`AmmoType.createOSLodestarAmmo`、`OSNetworkDesignators`。
**待议**：Improve C3 Point focus-fire −1 尚无类，暂不进 cap（见第三节"尚未实现"）。

---

## 三、模块化电子 BCS / CCS（优先级 P3，§9）

### 3A. 已实现、待对局测试
- [ ] **B-2500 协同掷骰**（2026-07-23 done）：每回合 2d6，**10+（16.7%）** 全网络对**直射**武器 −1；**12（2.8%）** 该回合先攻 +2；ECM 可压制；回合报告有初始化行（双盲感知）→ 期望：概率与报告正确
- [ ] **CCS 命中模块 −1**：C-2500 核心 + CC 弹道/能量/导弹模块各给对应武器类 −1；**Composite TC** 给全武器 −1（与专用模块互斥、需核心）→ 期望：`ComputeAttackerToHitMods` 正确加成
- [ ] **CCS 变量吨位/临界**：弹道 ÷6、导弹 ÷7、能量 ÷8，下限 **1t/1crit**（下限刚从远程下调，见第七节）；Composite = 总武器吨 ÷6 → 期望：建造重量正确
- [ ] **CCS 全机型**：C-2500 + 三模块 + Composite 可建于 Mek / 战车 / 支援车 / 战机（带 TC 同款机型 flag）→ **车辆/战机上的 −1 与变量吨位/aimed-shot 未测**，重点验
- [ ] **C3 Node/Point、Guardian ECM**（映射正史 C3/ECM）基本联网/ECM 泡工作正常

**代码位置**：`MiscType.java`（CCS 五件 + BCS 模块）、`ComputeAttackerToHitMods.java`、B-2500 协同掷骰 + 报告。

### 3B. 尚未实现（**非测试项，属开发待办，勿测**，§9.5）
- Demon Aggressive Hacking System 攻击性 to-hit debuff（+1/+2，友方 ECM 内翻倍 +4）
- Improve C3 Point **focus-fire −1**（尚无类，未进反叠加 cap）
- 网络容量 12 → **26**（连级）+ Improve C3 Node 6-Point 拓扑
- Advance 核心行为：Crow Nest 网络锁 + 满 +2 command；Raven CEWS 6-unit 平权网 + 内置 Light TAG；Ghost Core 隐身共存
- BCS 模块 → B-2500 核心**前置依赖**（目前 C3/ECM 无核心也能装）
- B-2500 常驻 **+1 先攻**目前只走 TCP-implant 路径，未独立生效
- Enhanced Combat Computer（暂搁置）

> 注：Raven CEWS / Ghost Core 的 MiscType 旗标（`F_OS_RAVEN_CEWS` / `F_OS_GHOST_CORE`）已存在、装备可建，但**特殊行为是桩**，别指望效果生效。

---

## 四、能量 / 动能武器（优先级 P4 及更早）

### 4. HVAC 精密化重构（§2，2026-07-31）
**改动**：从殉爆式航空 AC 改为长身管精密反装甲 AC。口径 `10/12/14/16`；单发、非殉爆（`explosive=false`，普通 `ACWeaponHandler`）；专用 `HVAC_OS` 弹药（idx 139，7/6/5/4 发/吨）；Improve/Enhanced 共享 BV。
- [ ] **射程递进 −1 命中**（`ComputeToHit`，gated on `OSHVACWeapon`）：short **0** / med **−1** / long **−1** / ext **−2** → 期望：各档正确
- [ ] `HVAC_OS` 弹药**不与正史 HVAC/10 交叉装填**（专用 enum 的目的）
- [ ] 航空：AV = 伤害、maxRange = EXT 生效
- [ ] BV **165/195/225/255**（Improve/Enhanced 同 BV）手感

### 5. Electromagnetic Lance（§8，Advance，2026-07-31，6 弹种）
**改动**：Advance 档 Gauss 变体（extends `GaussWeapon`），`EM_LANCE_OS` 弹药（idx 140）。**功率门控**由 weapon 的 `getRanges` override 实现（无核心方法改动）。底盘：dmg 20 / heat 3 / 18t / 11crit / BV 480 / 3060·3070·3080 / F / LEGION。
- [ ] **满功率**（min3/9/18/26/34，带 AP）：**EM Slug**（`ACAPHandler`，固有 AP、满伤）、**APDS**（`OSEMLanceAPDSHandler`，固有 AP + ×0.8、更多发/吨）
- [ ] **低功率**（0/5/10/15/20，**失 AP + 掉伤**）：**Flak**（`ACFlakHandler`，防空）、**Flechette**（×0.6，反步兵）、**Precision**（×0.6，抵消目标移动 −2，`M_PRECISION`+`EM_LANCE_OS` 于 `ComputeTargetToHitMods`）、**Incendiary/HE**（×0.6，纵火）
- [ ] 换弹即换射程/AP：装满功率弹走长档带 AP、装低功率弹缩短档失 AP → 期望：`getRanges` 按弹种切换
**待议/延后**：Flak 低功率掉伤（集束性）、手动节流模式、EM-Lance 命中递进、最终 BV 调参。

### 6. Coil Augmented Railgun（§8 后继，Advance，已实现 = `b64ff86193` + 报告/shed 后续）
**改动**：OS 最"招牌"的武器。dmg **30 平**（无衰减）；range **min0 / 9/18/27/36**；heat 6；22t；13crit；专用惰性 AP 弹 3 发/吨（不殉爆）；weapon 暴击殉爆 30。
- [ ] **固有 AP**（长炮管才有）：每次命中掷穿甲暴击（`ACAPHandler` 模式）→ 报告 `1274`"slug punches clean through"
- [ ] **炮管磨损**：前 **9–12 发**稳定；超阈值起 **+2 命中（不叠）** + 每发 **natural-2 卡壳**；每次卡壳 +1，卡壳栈到 **+5 报废**（本场永久失效）→ 报告 `1270`(卡壳) / `1271`(退化 N/5) / `1272`(报废) / `1273`(磨损射击 +N)
- [ ] **Shed Barrel 模式（单向不可逆）**：range 缩 min0/4/6/9/12、dmg 降 25、**无磨损/无 +2/无卡壳/无退化**、**失 AP**；`FiringDisplay` 切换前弹 Yes/No 确认窗
- [ ] 两态均满伤无衰减（30 长管 / 25 shed）
**待议**：稳定阈值 9–12 具体值；+2 是否计入 +5 报废（默认不计）；natural-2 卡壳概率偏低（~2.78%/发），报废是长尾事件，若要报废成真实风险需放宽触发。

### 7. Ultra 重型 LRM / SRM（§8，2026-07-31）
**改动**：Heavy LRM(10/20/30) 与 Heavy SRM(4/6/8/12) 加 **Single / Ultra** 模式。
- [ ] **Ultra 模式**：射 2 齐射（各自集束掷骰）、耗 2 发弹、**热量翻倍**（`Mounted.getNumShots` 新分支，gated on LRM/SRM 弹 + Ultra 模式）、**natural-2 卡壳**
- [ ] **Single 模式**：普通发射器
- [ ] BV ≈ 1.6× 单齐（Ultra-AC 惯例）；weapon `heat` 已按单齐值重定
**待议**：4-齐射升级延后。

### 8. OS 标准 AC 特殊弹药 Phase 1（§7，2026-07-08）
**改动**：`AC_STD_OS` 上 6 弹种（正史 AC 弹去掉 Precision + 一枚 OS 原创），dispatch 靠 `ACWeapon.getCorrectHandler` 按弹种。
- [ ] **Armor-Piercing**（½发、+1 命中、crit 增强）、**Caseless**（2×发、natural-2 卡/毁）、**Flak**（flak 表 + 5 碎片集束）、**Flechette**（反步兵 + 对林 2×）、**Tracer**（−1 伤、夜战 −1 命中）
- [ ] **Rocket-Propelled**（OS 原创）：short +1 / long·ext −1 命中；装填时全射程档 ×1.2（仿 ATM）；普通实弹伤

### 9. OS LB-X / Ultra 特殊弹药 Phase 2（§7，2026-07-09，5 弹种 18 条弹药）
**改动**：复用 `LBX_OS` / `AC_ULTRA` enum；4 个新 `Munitions`（`M_GAAM`/`M_ANTI_MYOMER`/`M_IMP_CASELESS`/`M_APDS`，Precision 复用 `M_PRECISION`）；共享 handler dispatch `getOSLBSpecialHandler`。
- [ ] **GAAM**（`OSGAAMHandler`）：单发制导 + AP crit；min6·7/10/25/30；med −1/long +1 命中；**med 命中 +25% 伤**；**≤6 格命中 = 0 伤（未武装）**
- [ ] **Anti-Myomer Flechette**（`OSAntiMyomerHandler`）：集束表 + **命中强制目标 PSR** + 逐弹丸 AP crit → **实测重点**：逐弹丸 AP 是否过强（LB10-X ≈7 偏 crit 弹丸），过强则回调
- [ ] **Precision**（无 handler）：抵消目标移动 **−2**、半发（`AC_ULTRA` 线入 `ComputeTargetToHitMods`）
- [ ] **Improved Caseless**（无 handler）：卡壳**只卡不毁**、发数 ≈1.5×
- [ ] **APDS**（`OSUltraAPDSHandler`）：伤 ≈−20%（floored）+ AP crit + 发数 ≈1.35×、保留 double-tap → **确认 APDS/2（1 伤）是否值一个弹位**

### 10. BF / RF AC 变种（§7，IS 技术、本 fork 内）
**改动**：同 BV/吨位/弹药的手感侧改；`innerSphere` 包，`AC/N BF`、`AC/N RF`；专用弹 `AC_BF`(135)/`AC_RF`(136)。
- [ ] **BF**：命中递进 short 0/med 0/**long −1**（把 long −4 软化到 −3）；BV = 标准 AC
- [ ] **RF**：3 发爆发、单次命中骰无惩罚，用 **cluster size-3（期望 2.0 命中）**，每发伤 = 标准 ÷2 → 有效伤 = 标准 AC；**无 +1、无卡壳**；per-shot 热量 ×3 正常（`getNumShots` 已含 `AC_RF`）
- [ ] 记录表显示爆发（如 "5×3"）不误读

### 11. OS AC/导弹 原生弹药 Standard/Ultra/Rotary + LRM/SRM（§7，2026-07-08）
**改动**：Standard AC → 专用 `AC_STD_OS`(137)；Ultra/Rotary **复用**正史 enum（双发/机制硬键）；LRM/SRM 加 OS 原生 bin（**必须复用** `LRM`/`SRM` enum，否则破坏 Artemis/Narc/FCS）。
- [ ] MML 里 12 条 AC 弹以 `(OS)` 标签区分、可选中→保存→回读
- [ ] **存档兼容**：既有自定义单位（`OSAC{2,5,10,20}` 现用 `AC_STD_OS`）可能需**重挑标准 AC 弹**——逐一确认不报错
- [ ] OS LRM/SRM 武器可同时装正史与 OS 弹（保留正史 ammoType）

---

## 五、机动 / 防御系统

### 12. AES-I / AMS-I（§11，2026-08-09）
**AMS-I（Improved Armored Motive System，战/支援**载具**，防御）**
- [ ] `F_ARMORED_MOTIVE_SYSTEM`+`F_TANK`+`F_SUPPORT_TANK`；重量 = 车吨 **×0.10**；crit/tankSlots **0**
- [ ] 效果 **−2 机动损伤掷骰**（`TWGameManager` `hasWorkingMisc`）→ 期望：复用正史 motive-crit 机制
- [ ] cost = 吨 ×120,000；BV 0；tech OS/E/2900·2930·2960/STANDARD/F·E·D·D

**AES-I（Improved Actuator Enhancement System，**仅机甲**，机动/操控）**——重构为整机机动系统，**非火控**
- [ ] **新旗标 `F_OS_AES`**（+`F_MEK_EQUIPMENT`）：**不触发**正史逐肢/物理/AES⊥TC/⊥MASC 校验/`hasFunctionalArmAES` → 重点验：装上后正史 AES 逻辑一律不误触
- [ ] **整机一次安装**（无逐肢要求）；重量 = 机甲吨 **×0.05**（半吨取整）；crit 按体重 **Light1/Med2/Heavy3/Assault4**，自由摆放
- [ ] **(A) 命中**：机甲 **RAN 或 JUMPED** 时对移动命中修正 **−1**（走路不吃）→ `ComputeAttackerToHitMods`
- [ ] **(B) MP 抵消**：抵消 **Hardened Armor（−1 走/跑）** 与 **Modular Armor（−1 跳）** 的 MP 惩罚（`Mek.hardenedArmorMPReduction` 返回 0 + 两处跳跃 MP 加 `!hasWorkingMisc(F_OS_AES)`）；被打坏则失效
- [ ] **互斥：无**——与 TC / BCS / CCS / MASC / TSM 共存（**MASC 兼容待确认**）
- [ ] MML 侧自动进机甲装备库、记录表按普通装备行打印（无需 MML 特判）
**待议**：AES-I **BV = 0 待定**（−1 命中 + MP 抵消有实际价值需补 BV）；可用性首档 F 与 intro 2900（SW 期）略矛盾，是否改 X·E·D·D；AES-I 是否禁用于 superheavy（正史禁，OS 未定）。

### 13. OS MASC / 增压器 tier 重定 + 基础增压器差异化（2026-07-25）
- [ ] **MML Enhancement 下拉**能看到 OS MASC 三兄弟（`BMChassisView.ENHANCEMENT_NAMES` 白名单已补 `OS_MASC/OS_IMPROVE_MASC/OS_HEAVY_DUTY_MASC`）→ 选中→保存→回读
- [ ] **tier 年份**：基础版 Standard 2805/2820/2840、Improve/Heavy Duty Improve 2900/2930/2960——游戏内按新 STANDARD 等级出现
- [ ] **基础 OS 增压器差异化**：失效时**引擎伤害封顶 2 击**（canon 仍 3）；基础版失效曲线维持标准（免检回合留给 Improve）→ **实测**：真在场上让基础 OS 增压器失效验证封顶 2（`Entity.java` `cappedScDamage`；canon IS 增压器不受影响）

---

## 六、平台开放（车辆 / 战机）

### 14. OS 装甲线开放给车辆/战机（2026-07-31，20 款）
- [ ] 20 款 `createOS*` 装甲补齐机型 flag；MML 里给车辆建单时**装甲下拉能出现**（此前 FF 家族只有 `F_MEK_EQUIPMENT` 故不出现）
- [ ] 8 款对战机开放（Reflective×2 / Ferro-Lamellor×2 / BRA / APA / Stealth×2），12 款不开放——逐类对齐正史
- [ ] **已知缺口**：OS 版**未设 `fighterSlots`/`svSlots`**（正史 Reflective=1、BRA=2 等）→ 战机上护甲占位/成本可能不对，**补齐后再 playtest**
- [ ] 车辆/战机建单：装甲选中→保存→回读正常

### 15. CCS 全机型开放 + Hybrid 引擎降 Standard（2026-07-31）
- [ ] CCS 五件在 MML 里对**车辆/战机可选**（已加 `F_TANK/F_SUPPORT_TANK/F_FIGHTER` flag）→ 选中→保存→回读
- [ ] （效果层与 3A 的"CCS 变量吨位/−1 在非机甲上"重叠，一并验）
- [ ] **OS Hybrid 引擎 Advance→Standard**：静态等级 + 遗留 `getTechType(year)` 两处均改；游戏内年份可用性正确（两套等级表示别再不一致）

### 16. 载具可选热沉 + Compact Double Heat Sink（2026-08-06，两仓库）
**改动**：给战斗载具开"可选热沉类型"；照抄 Aero 的类型字段路线（非机甲挂装备路线），单沉默认路径字节级不变。
- [ ] `CVStructureTab` 左栏"Heat Sinks · Type"下拉：Single + Compact Double；改动触发 `refreshStructure/refreshStatus`
- [ ] **Compact DHS**：1.5t / `F_DOUBLE_HEAT_SINK`+`F_COMPACT_HEAT_SINK` / 散热 2 / OS Enhanced（3000·3025·3050，F，X/X/E/D，ADVANCED）→ 建车重量/散热正确
- [ ] **BLK 存读**：只有非单沉才写 `sink_type_name`；老车 BLK 不变、能回读
- [ ] **单沉默认与旧值完全一致**（散热 1、重量公式不变）——回归验证
- [ ] 机甲/航空下拉**选不到** Compact DHS（只给载具，`HeatSinkView.LOOKUP_NAMES` 未动）
**已知局限**：①对局层"载具热量"选装规则散热仍按旧算；②引擎免费热沉随类型升级对载具可能偏强；③下拉未按技术年代过滤（非法组合靠校验器标红）；④非 OS 技术基座载具选它会被判非法。

---

## 七、刚从远程拉取（本会话合并，建议一并验）
> 这两个提交在 `2026-08-10` 拉取合并进来（`835077bccb`、`07c3a64564`），同样标注未对局实测。

- [ ] **CCS 弹道/能量/导弹模块下限降到 1t/1crit**（`835077bccb`）→ 期望：轻武器载荷下模块吨位/临界按新下限、命中 −1 仍生效
- [ ] **OS/Ascended BLK tech base 修复 + 车辆/战机 OS 引擎持久化**（`07c3a64564`）→ 期望：带 OS 引擎的载具/战机 BLK **保存→回读** tech base 与引擎正确、不漂移

---

## 八、设计仍在流变（不在本测试清单，供参考）
- **基础能量/动能/导弹武器族**（`weapons/**/outerSphere` ~224 文件）已在代码中、可对战，但 **Advance 档设计与部分"Advance"→Enhanced 重分类仍 ⏳PENDING**（§0.2/§6.2）；其 tier/BV 未定稿，暂不作为测试项。
- **§4 各优先类（A/B/C/E/G/I）的 OS 版特种装备**为开发路线池，多数尚未实现。
- **新 Advance/Experimental 引擎与结构**重设计 ⏳PENDING（§6.4）。
