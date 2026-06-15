# The Ascended 装备设计纲要（设计交接文档）

> 用途：整理 The Ascended（升华者）装备设计的已确定约定、设计原则、已锁定的内构方案与后续路线，供在 Claude Code 中继续具体设计（MegaMek / MegaMekLab 实装）。本文档为自包含交接件，可独立于原对话阅读。
> 范围：本文档限于装备/工程设计。完整的设定与战略分析（圣战进程、内部分裂、裂出派系、胜利性质等）不在此，留在 `外天体AU设定对话录`。

---

## 0. 工作约定

- 工作语言：中文为主，BattleTech 术语保留英文（派系名、装备名、规则系统名）。
- 技术基：The Ascended 为独立 techbase（不是 Mixed IS / Mixed Clan），自有分层 A-C / A-R / A-S / A-X。
- 内构减重倍数基准：以「标准内构重量」为基准。标准结构 = 机体吨位 × 0.10 = 1.0×。例：0.5× = 标准结构的一半 = 机体吨位 × 0.05。
- 组件讨论顺序：内构(Internal Structure) → 装甲(Armor) → 引擎(Engine) → 能量武器(Energy) → 动能武器(Ballistic) → 导弹武器(Missile)。
- 命名原则：装备名向正史类型对齐，由性能特征与稀有度去暗示其来源/政体，不把文化标签硬塞进名字。
- 编辑纪律：未经确认的内容都是提案，不作为已定 lore；本文档中标「待定」者尚未最终拍板。

---

## 1. 设定与 doctrine 背景（供设计参考，非完整设定）

The Ascended 是由 WoB 残党（含 Manei Domini）与 Clan Society 残党（含 Burrock、被裹挟的 Dark Caste）整合成的后人类主义战争国家，约 3079–82 成形，3140 对 RoOS 发动灭绝圣战。其本质是一个燃烧有限存量、执行一次性存在性豪赌的终末势力——必须速胜，因为打不起持久战。

与装备设计直接相关的几条：

- doctrine：decapitation / strike-force / WMD + Super-Jump 多向量机动；质量与主动权 > 数量与可持续。
- 兵力为双峰结构（决定了装备的双轨哲学）：
  - 精英手术刀（A-S / A-X）：Ascended 全改造精英 + Anointed。极少、不可替代、cyber 改造、受植入期限自噬。承载全军的适应性判断与 Drone 协调经验。bespoke 平台。
  - 主力（Initiates）：改造进行中，是「幸存即晋升、晋升即改造」的经验/晋升管线；本身折旧（植入时钟），补充速率受改造产能锁死。〔注：Initiates 作为主力层是工作假设/提案，尚未入对话录。〕
  - Faithful 铁砧（A-C / A-R）：Iron Womb 生 + 精神编程，廉价、可量产、消耗品；稳（不溃退）但僵（不机动）。
- 物资为 endowment-not-flow：有限、不可再生、靠掠夺与 cannibalization 维持 → 装备设计上 Faithful 端重可量产性与可回收性。

---

## 2. 装备设计原则

1. 双轨哲学：精英用 bespoke、crit 效率优先的 premium 组件（A-S/A-X，稀少昂贵）；Faithful 用廉价、可冲压、可回收的组件（A-C/A-R）。
2. 精英单位 crit 饥渴：Drone 协调栈、cyber 接口、署名削减组件都吃槽位 → 精英结构/组件优先 crit 效率。
3. 无决战兵器，每个优势都付账：不造全面碾压的神装备；每个组件为一个特定角色压榨到极致，坦然接受随之的短板（造价 / 耐久 / 重量 / 脆性）。
4. 针对已知技术的反制：威胁模型基于 IS + Clan 技术（它们懂的），pre-war arsenal 由「针对已知技术的成熟反制 + 泛用技术」主导。针对 RoOS（星盟根、380 年独立演化出的第三分支）的专用技术只能战时反应式开发——这条对后续武器/装甲设计尤其相关（很多反制对 RoOS 是部分死重）。
5. 技术血脉（合成来源）：
   - WoB：Manei Domini / VDNI / cyber、C3i、Caspar II/SDS drone、Super-Jumpdrive、Celestial OmniMech、Bolla/Purifier stealth。
   - Society：Iron Womb 量产、eugenics、Clan 自动化、不带 zellbrigen 的 Clan 级一线技术。

---

## 3. 技术分级（Tier）

- A-C：常见 / 量产层，≈ Clan/WoB grade，不超越 RoOS Advanced。Faithful 主体。
- A-R：进阶可生产层。
- A-S：精英层（cyber 突击、关键平台）。
- A-X：实验天花板，极稀少、不可持续、elite-only。

提醒：Tier 是稀有度/角色层级；BT 的 Tech Rating 是 A–F 独立轴。A-X 物件通常 Tech Rating F。

---

## 4. 内构（Internal Structure）— 已设计方案

基准提醒：吨位按「× 标准内构重量」，标准 = 机体吨位 × 0.10。Composite / Reinforced 的确切脆化-加成机制与 BV2 倍率以 TO:AUE / IO 为权威，实装前需核对（见 §6）。

| 类型 | Tier | × 标准（× 机体吨位） | Crits | Tech Rating | 特殊机制 | BV | 状态 |
|---|---|---|---|---|---|---|---|
| Compact Endo-Composite | A-X | 0.5×（0.05） | 3 | F | 无 | 中性 | 锁定 |
| Reinforce Heavy Duty | A-S | 待定（见下） | 待定 | E–F | 内构点 ×1.25；最大装甲上限 +25% | 结构侧 ×1.25 + 额外装甲 | 待定 |
| Standard〔Ascended 主力〕 | A-C | 0.75×（0.075） | 3 | D | 无 | 中性 | 锁定 |
| Drone Standard Composite | A-R | 0.5×（0.05） | 5 | D | 内构受伤 +25%（向上取整） | 略降 | 锁定 |

各结构设计意图：

- Compact Endo-Composite（A-X，锁定）：Endo Steel 级重量（0.5×）却只占 3 crit（Clan Endo Steel 要 7）。精英 premium，用最好的材料学把 crit 省给特殊装备；代价压在 Tech F + 高造价 + A-X 稀有度（只能小批量）。BV 中性（不抬战斗力，只抬构造密度）。

- Reinforce Heavy Duty（A-S，待定）：重甲砧 / 保护 cyber 驾驶员。机制已定：内构点 ×1.25（+25% 耐久）+ 最大装甲上限 +25%。吨位/Crit 待最终拍板，两候选：
  - 推荐：1.5×（0.15）/ 4 crit —— weight-led，保住 reinforced 的重身份。
  - 备选：1.25×（0.125）/ 7 crit —— crit-led，重量更轻。
  - 推导锚：正史 Reinforced 2.0× 买 +100% 耐久；按线性，+25% 耐久 ≈ 1.25×，+25% 装甲上限 ≈ 再 +0.25×。
  - 调参注意：真正强的杠杆是「+25% 装甲上限」；若实测偏强，加重（→更高 ×）或加 crit。
  - BV：内构点 ×1.25 → 结构防御 BV 贡献 ×1.25；额外搭载装甲按正常装甲防御 BV 计入。

- Standard〔Ascended 主力〕(A-C，锁定)：比正史标准更轻、但吃 crit 的量产主力结构，作为 Ascended 的技术特色（Clan 自动化 + 材料学的产物）。相对正史 Endo-Composite（0.75× / 4 crit / Tech E）有「少 1 crit、低 1 Tech」的小幅 edge，作为量产效率签名保留。BV 中性。

- Drone Standard Composite（A-R，锁定）：廉价、轻、微脆的无人机结构。对比 Clan Endo Steel（0.5× / 7 crit / 不脆）：同重、少 2 crit、换 +25% 脆性，是合理小权衡。注：5 crit 严格说已不算 “Composite”，名字可后续斟酌。

---

## 5. 路线图 / 下一步

1. 最终确认：Reinforce Heavy Duty 的吨位/Crit（1.5×/4crit 推荐，或 1.25×/7crit）。
2. 四种内构锁定后，可补：C-bill 单价（TechManual 公式：结构类型 × 吨位）、确切 BV2 数值。
3. 按组件顺序继续：装甲(Armor) → 引擎(Engine) → 能量(Energy) → 动能(Ballistic) → 导弹(Missile)。

---

## 6. 待核验 / pending（需源书）

- Composite / Reinforced 的确切机制（半数点 vs 双倍伤害 / 加成方式）与其 BV2 倍率：权威源为 TO:AUE 与 Interstellar Operations。
- 实装结构造价（C-bill）：TechManual 结构造价公式。
- 本表内自定义机制（「最大装甲上限 +X%」「内构受伤 +X%」）为 house rule，MML 实装时需建自定义条目。

---

## 7. 已识别、尚未展开的其他技术族（六大类之外，供日后）

- 神经化 Drone 协调栈（C3i + Caspar/SDS + VDNI + Machina Domini Interface 合成）。
- 署名削减 / 隐身组件（Bolla + Purifier + Clan 材料）。
- 角色定向表型、CBRN 硬化 + 短寿命优化的消耗型生物、战斗化学（Society eugenics + Manei Domini Effuser）。
- 针对已知 IS/Clan 技术的反制族（counter-ECM、anti-BA、counter-battery、针对已知伤害 profile 优化的装甲与损伤分配）。
