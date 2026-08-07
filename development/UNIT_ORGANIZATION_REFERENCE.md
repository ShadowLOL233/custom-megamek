# BattleTech 部队编组标准（TO&E 识别依据）

> 来源：Sarna.net "Unit Organization"，由用户于 2026-08-06 提供，作为 TO&E 图**编组识别与布局**的开发标准。
> 本文件是权威依据：`ForceToeView` 的 echelon 分类、权重排序、父/子布局都应对齐这里的结构定义。

组织原则：每支部队实践中各异，但多数遵循一套基础结构以便与其它部队整合。分类主要有两套 moniker——**权重级**（light/medium/heavy/assault，按平均吨位；越大编队越宽松、越随意）与**角色**（如 Scout，最偏战术、多用于最小编队）。

---

## 一、BattleMech 地面阶梯（主线）

| 梯队 | 组成 | 规模（战斗单位） | 备注 |
|---|---|---|---|
| **Lance（枪队）** | 4 台 BattleMech | 4 机（+4–6 支援 ≈ 10 人） | 最小编队，等同步兵 platoon；按**平均吨位**分 light/medium/heavy/assault。继承战期一版标准为 1 轻 1 中 2 重。 |
| **Demi-Company / Reinforced Lance（加强枪队）** | 6 台单位 | 6 机 | 临时/常设的 6 单位编队；2 个加强枪队 = 1 个连。 |
| **Company（连）** | **3 个 Lance**（+ 可选辅助 lance / 侦察 / 装甲 / 火炮） | ~12 机（+18 支援 ≈ 60 人） | 比 lance 更自足；连长常为其**Command Lance** 之长（少校/上尉）。3 连 = 1 营。 |
| **Battalion（营）** | **3 个 Company + 1 个营级 Command Lance** | 36–40 机（+航空/火炮/侦察等，总 200–300 人） | 高度自足；营长通常不亲自参战。 |
| **Regiment（团）** | **3–5 个 Battalion** | 108–180 机/车 | 大公国常规单位最大常用编队；含专属 DropShip、可能 JumpShip。4–5 营者称 **Reinforced Regiment**。 |
| **Brigade（旅）** | **3+ 个 Regiment**（同权重级为主，≤5） | — | SLDF 常用，现今 IS 少用；有时指共享传承的大编队。 |
| **Division（师）** | **3 个 Brigade** + 预备/支援 + 航空联队 | — | SLDF 核心；IS 现无师级。Com Guard "Level IV" 借用此词（216 单位）。 |
| **Corps（军）** | 1–3 个 Mech Division + 2–7 步兵师 + 独立团 + WarShip 等（无严格标准） | 守 30–100 星球 | 仅 SLDF。后世也指 5–10 个师。 |
| **Army（集团军）** | **3–5 个 Corps** | — | Reunification War 起取代 corps；Com Guard "Level V" 借用。 |

**变体（识别时可归入基础梯队，标注变体名）**：
- **Augmented Lance**（CCAF）：4 Mech + 2 车（standard）/ 4 Mech + 2 BA 班（secondary）；装甲版 4 车 + 2 Mech 等。
- **Company Task Force**：18–24 单位的合成连。**Augmented Company**（CCAF）：12 单位编成 2 个 augmented lance。
- **Augmented / Reinforced / SL Reinforced Battalion**：4 连（±command lance），后者 48 机；SL 版 = 4 连 × 4 lance。
- **Augmented Regiment**（CCAF 3081 起，含 ≥1 augmented battalion）、**Forward ARC**（DC，Mech 团配装甲营+步兵团+航空联队）。
- **Regimental Combat Team (RCT)**：SLDF 起源=4 战斗团+支援；AFFS 版=1 Mech 团+3 车团+5 步兵团+2 航空联队+1 火炮营（多regiment）。**Light Combat Team (LCT)**：加强营 Mech + 2–3 装甲营 + 骑兵营 + 火炮连 + BA 编队。

---

## 二、Aerospace 阶梯（约为 Mech 的一半规模）

- **Flight**：2 架（航空/常规战机）。CCAF：2–3 架为 element / triple，2 squadron 为 flight。
- **Command Element**（CCAF 航空）：3 架指挥+防御。
- **Squadron**：6 单位，分 2–3 个 flight。
- **Wing**：营级，18 架 + 2 架指挥 flight。
- **Regiment**：2–3 个 wing（含指挥 flight）。
- 注：DropShip/JumpShip 通常不编入，作独立支援。历史 "air lance" = 标准 lance + 一个战机 flight（现已少用）。

---

## 三、Conventional Infantry 阶梯

- **Fire team**：3–5 人。
- **Squad**：2 个 fire team + 班长（7–12 人）。
- **Platoon**：2+ squad + 支援（25–50 人；3025 标准 = 3 线班 + 1 支援武器班）。
- **Company**：2+ platoon（70–200+ 人）。
- **Battalion**：2–6 company（300–1000 人）。
- 31–32 世纪步兵地位下降，少见大于连的步兵编队；班/排常配属支援 Mech 作战、巡逻、侦察。

---

## 四、Battle Armor 阶梯（类 Mech 结构）

- **Squad**：4 名装甲步兵。
- **Platoon**：3–5 squad + 支援。
- **Company**：3 platoon + 支援。
- **Battalion**：3 company + 支援。

---

## 五、分类口径小结（供 echelon 识别用）

1. **结构优先**：连=3 lance、营=3 连+营 command lance、团=3–5 营……即一支部队的 echelon ≈ 其直属子编队 echelon 的**上一级**（子系为 Lance→本级 Company；子系为 Company→本级 Battalion；以此类推）。
2. **叶子编队按单位数+类型**：Mech 4=Lance / 6=Reinforced Lance；Aerospace lance=2 架；BA squad=4；步兵按上表。
3. **权重级**（light/medium/heavy/assault）对 lance 由**平均吨位**得出；编队越大权重级越宽松/近似。
4. **Command Lance**：连/营的指挥元（由用户显式指定，见 `Force.commandLance`），排序权重视为**高于**任何普通 lance。
5. **角色 moniker**（Scout 等）多用于最小编队，作战术标注。
