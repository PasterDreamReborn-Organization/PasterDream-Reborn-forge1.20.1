# 工具系列共享行为统一（Mixin 派发）

- 日期：2026-11-12
- 版本变更点：0.9.0beta2~1.0.0alpha
- 范围：融梦水晶 / 炙焰金 / 暗影合金 / 影蚀 / 萦风合金（含萦风雷矛）
- 验证：`.\gradlew compileJava`、`.\gradlew runData` 通过；refmap 正常生成
  （`Item#inventoryTick` → `m_6883_`，`ItemStack#getTooltipLines` → `m_41651_`）
- 后续修正：2026-11-13 将公共词条注入点从 `getTooltipLines` 返回处移至原版属性词条之前，修复属性、ID 和模组名显示在公共词条上方的问题。

---

## 一、背景

各工具系列原本采用「标记接口 + 每个具体工具类手写 `inventoryTick` / `appendHoverText` / `getAttributeModifiers`」的方式复用逻辑：

- 同一行 `super.xxx(...); XxxToolHelper.xxx(...);` 在 4~5 个类里重复；
- 新增一件工具就要复制一遍，容易漏；
- 系列间无法组合（一件物品同时拥有两个系列被动时，要手动改每处 override）。

目标是让「`implements` 对应系列标记接口 = 自动获得该系列通用效果」，并把共享逻辑收敛到一两个注入点。

---

## 二、方案演进（含被否掉的方案）

### 2.1 直接放接口 `default` 方法——不可行

`SwordItem` / `PickaxeItem` / `AxeItem` 等父类从 `Item` 继承了**具体**的 `inventoryTick` / `appendHoverText`。
按 JLS「class wins」，父类具体方法永远压过接口 `default`：子类不写 override 时 `default` 根本不会执行；子类写 `Xxx.super.method()` 也只能调到接口 `default`，调不到父类实现。

### 2.2 新增 `SharedToolBehavior` 接口 + `default`，由 Mixin 显式调用——已放弃

临时实现过「mixin 里 `((Object) this instanceof SharedToolBehavior b)` 后调用 `b.xxx()`」的方案，能让 default 被稳定触发，但存在致命问题：

> 接口 `default` **无法叠加**。附属模组若想让一件工具同时拥有多个系列的被动（同时实现两个标记接口），两个同签名 `default` 会直接冲突（Java 只允许一个实现），要么编译失败，要么只能任选其一。

### 2.3 最终方案：两个 Mixin + 独立 `instanceof` 分支

在固定的注入点上，用**互不干扰的并列 `if`** 逐个系列派发：

```java
// ItemSharedToolBehaviorMixin（Item#inventoryTick TAIL）
if (self instanceof MeltDreamTool)    MeltDreamToolHelper.onInventoryTick(...);
if (self instanceof ShadowAlloyTool)  ShadowAlloyToolHelper.onInventoryTick(...);

// ItemStackSharedToolBehaviorMixin（ItemStack#getTooltipLines 中属性词条前）
if (item instanceof MeltDreamTool)     MeltDreamToolHelper.appendHoverText(...);
if (item instanceof MoltenGoldTool)    MoltenGoldToolHelper.appendHoverText(...);
if (item instanceof ShadowAlloyTool)   ShadowAlloyToolHelper.appendHoverText(...);
if (item instanceof ShadowErosionTool) ShadowErosionToolHelper.appendHoverText(...);
if (item instanceof FluffyWindAlloyTool) FluffyWindAlloyToolHelper.appendHoverText(...);
```

优点：
- 一件工具实现几个接口就执行几个分支，被动**可叠加**，互不覆盖；
- 附属模组只需 `implements` 我们的标记接口即可白嫖被动，不必碰 mixin；
- 新系列 = 在 mixin 里加一个分支，不需要改任何工具类。

---

## 三、最终结构

| 注入点 / Handler | 目标 | 派发的系列 |
|---|---|---|
| `mixin/ItemSharedToolBehaviorMixin` | `Item#inventoryTick`（TAIL） | 融梦水晶（融梦能量修耐久）、暗影合金（理智/生命修耐久） |
| `mixin/ItemStackSharedToolBehaviorMixin` | `ItemStack#getTooltipLines`（读取 `TooltipPart.MODIFIERS` 前，位于物品自身词条之后） | 融梦、炙焰金、暗影合金、影蚀、萦风 |
| `ShadowAlloyAttackHandler` | `LivingHurtEvent` | 暗影合金（低 SAN 攻击加伤） |
| `ShadowAlloyMiningSpeedHandler` | `PlayerEvent.BreakSpeed` | 暗影合金（暗影方块 + 亮度 + 低 SAN 挖掘） |
| `ShadowErosionMiningSpeedHandler` | `PlayerEvent.BreakSpeed` | 影蚀（暗影方块 + 亮度挖掘） |
| `FluffyWindAlloyAttackHandler` | `LivingHurtEvent` | 萦风（附雷 + 移速加伤；雷矛突进自旋前置分流） |
| `FluffyWindAlloyMiningSpeedHandler` | `PlayerEvent.BreakSpeed` | 萦风（移速挖掘） |
| `FluffyWindAlloyDropSweepHandler` | `BreakEvent.BreakEvent` / `LivingDropsEvent` | 萦风（风卷掉落；含投掷中的雷矛虚影） |

> 事件类效果仍放在各系列自己的 Handler 中，用 `instanceof 标记接口` 判定；Mixin 只负责原版方法里没有事件可用的两个点（`inventoryTick` / `getTooltipLines`）。

### 3.1 标记接口现状

| 接口 | `implements` 即获得的自动效果 | 仍需逐类实现的部分 |
|---|---|---|
| `MeltDreamTool` | 背包内融梦能量修耐久、公共词条 | — |
| `MoltenGoldTool` | 公共词条（镶嵌 / 右键急迫 / 冷却） | 右键急迫 `use()`（各工具类调 `MoltenGoldToolHelper.use`） |
| `ShadowAlloyTool` | 理智/生命修耐久、公共词条、低 SAN 攻击加伤、挖掘加成 | 主手 SAN 波动 / 触及属性 `getAttributeModifiers` |
| `ShadowErosionTool` | 公共词条、影蚀挖掘特性 | 主手 SAN 波动 / 触及属性 `getAttributeModifiers` |
| `FluffyWindAlloyTool` | 公共词条、附雷 + 移速加伤、移速挖掘、风卷掉落 | 雷矛激流突进自旋命中（`applyDashOnTouch` 专属分支） |

`getAttributeModifiers` 未自动化的原因：该方法在 `Item` 上是 `IForgeItem` 的接口默认实现（不在 `Item` 字节码里），无法直接 `@Inject`；要统一需另开「往 `Item` 合并方法」的 Mixin（参考 `ItemElytraMixin`），本次未做，避免扩大改动面。

---

## 四、各系列改动

### 4.1 融梦水晶（`world/item/meltdreamtool/`）

- 删除 5 个工具类的 `inventoryTick` / `appendHoverText` 重复 override；剑/弓只保留自身战技词条。
- `MeltDreamToolHelper.onInventoryTick/appendHoverText` 改 `public`，供 Mixin 调用。
- 公共词条由 `ItemStack#getTooltipLines` 统一追加（位于自己词条之后、自动属性之前）。

### 4.2 炙焰金（`world/item/moltengoldtool/`）

- 删除 4 个非剑工具的 `appendHoverText`；公共词条统一派发。
- **`MoltenGoldSwordItem` 新增右键急迫**（`use()` → `MoltenGoldToolHelper.use`），此前只有镐/斧/锹/锄有。
- 剑自身「镶嵌：§7无」词条删除（与公共词条第 1 行重复），语言键 `tooltip.pasterdreammod.molten_gold_sword.1` 一并移除。

### 4.3 暗影合金（`world/item/shadowalloytool/`）

- 删除 4 个类的 `inventoryTick` / `appendHoverText`；`getAttributeModifiers` 保留逐类覆写。
- `ShadowAlloyToolHelper.onInventoryTick/appendHoverText` 改 `public`。

### 4.4 影蚀（`world/item/shadowerosiontool/`）

- 删除 5 个类的 `appendHoverText`；`getAttributeModifiers` 保留逐类覆写。
- `ShadowErosionToolHelper.appendHoverText` 改 `public`。

### 4.5 萦风合金（`world/item/fluffywindalloy/` + 雷矛）

被动统一为「疾风过境」，剑/斧/镐/锹/锄/雷矛全部生效：

1. 攻击附带雷电伤害（`攻击力 × 0.1`，雷电伤害源，保留击杀归属）；
2. 移动速度越快，挖掘速度越快、攻击伤害越高（近战 `(1 + 移速) × 攻击力`，保留暴击/附魔比例）；
3. 破坏方块或击杀生物时，掉落物被风卷至身边。

具体：
- 新增 `FluffyWindAlloyAttackHandler`，替代剑的 `WindAlloyPassiveHandler`（雷随疾风）与斧的 `WindAlloyAxePassiveHandler`。
- `FluffyWindAlloySwordItem` 改为 `implements FluffyWindAlloyTool`，删除独立被动 Handler / 词条 / 常量。
- `WindwreathedThunderSpearItem`（萦风雷矛）改为 `implements FluffyWindAlloyTool`：
  - 删除 `ThunderSpearPassiveHandler` 与 `lightningSource`，普通近战走统一 Handler；
  - 激流突进自旋命中保留专属 `applyDashOnTouch`（`public static`），由统一 Handler 在通用结算前按
    `isAutoSpinAttack() && 主手为雷矛` 前置分流，激流加成 / 横扫溅射不变；
  - `APPLYING_TAG` 统一为 `FluffyWindAlloyToolHelper.APPLYING_TAG`（旧 `pasterdream:thunder_spear_applying` 仅为运行时 `persistentData` 标记，无存档兼容问题）。
- 公共词条统一为 4 行：`被动：疾风过境` + 3 条效果；各工具自身战技词条保留在前。
- `FluffyWindAlloyDropSweepHandler` 简化：只判 `FluffyWindAlloyTool`（雷矛自动命中；投掷中的虚影仍靠 `WindThunderSpearEntity` 分支）。

---

## 五、行为变更清单（玩家可感知）

1. **炙焰金剑**新增右键消耗饱食度获得急迫（10s 冷却），并显示对应公共词条。
2. **萦风剑**：删除「雷随疾风」命名与独立词条，并入「疾风过境」；被动数值不变（`(1+移速)×攻击力` + `攻击力×0.1` 雷伤）。
3. **萦风斧/镐/锹/锄**：新增「攻击附带雷电伤害」（此前只有剑/矛有）；词条统一为 3 条。
4. **萦风雷矛**：被动改名/并入「疾风过境」，效果不变；公共词条与工具一致。
5. **萦风剑与雷矛现在也吃「移速 → 挖掘速度」加成**（因为统一接口 + 统一词条）。若不想要，在 `FluffyWindAlloyMiningSpeedHandler` 里加物品排除即可。
6. 公共词条位于各物品自身词条之后、自动属性及物品 ID 与模组名之前；2026-11-13 修正了最初 `getTooltipLines` RETURN 注入造成的显示顺序问题。

---

## 六、文件变更

### 新增

- `mixin/ItemSharedToolBehaviorMixin.java`
- `mixin/ItemStackSharedToolBehaviorMixin.java`
- `world/item/fluffywindalloy/FluffyWindAlloyAttackHandler.java`
- `changelog/refactor/`（本说明目录）

### 删除（代码内）

- `FluffyWindAlloySwordItem.WindAlloyPassiveHandler`
- `FluffyWindAlloyAxeItem.WindAlloyAxePassiveHandler`
- `WindwreathedThunderSpearItem.ThunderSpearPassiveHandler`
- （方案演进期曾临时新增 `world/item/SharedToolBehavior.java`，最终未采用，已删除）

### 修改（按区域）

- `pasterdream.mixins.json`：登记两个新 Mixin
- `world/item/meltdreamtool/*`、`moltengoldtool/*`、`shadowalloytool/*`、`shadowerosiontool/*`、`fluffywindalloy/*`、`world/item/WindwreathedThunderSpearItem.java`
- `datagen/lang/ModZhCnLangProvider.java`、`ModEnUsLangProvider.java`

---

## 七、语言键变化

- 新增：`tooltip.pasterdream.fluffy_wind_alloy_tool.passive3`
- 修改：`tooltip.pasterdream.fluffy_wind_alloy_tool.passive1/2`（改为统一 3 条效果）
- 删除：
  - `tooltip.pasterdreammod.molten_gold_sword.1`
  - `tooltip.pasterdream.fluffy_wind_alloy_sword.passive_name/passive1/passive2`
  - `tooltip.pasterdream.fluffy_wind_alloy_axe.passive1/passive2`
  - `tooltip.pasterdream.windwreathed_thunder_spear.passive_name/passive1/passive2`

> `src/generated/` 为 datagen 产物（已 gitignore），改语言后必须重跑 `.\gradlew runData`，不要手改。

---

## 八、配套文档更新

- `document/rule/program/架构规范.md`：Mixin 数量与用途补充「工具系列共享行为派发」。
- `document/rule/program/程序规范文档.md`「易犯错误记录」：新增「接口 default 方法无法覆盖父类具体方法」，记录 class wins 与 default 无法叠加的结论。
- `document/design/item/ToolTips文档.md`、`document/design/item/萦风雷矛.md`：词条与被动描述同步。

---

## 九、增量指南

### 新增一个「自动生效」的工具系列

1. 建标记接口（不要放 default 方法）与 `XxxToolHelper`。
2. 需要背包 tick → 在 `ItemSharedToolBehaviorMixin` 加一个 `if (self instanceof XxxTool)` 分支。
3. 需要公共词条 → 在 `ItemStackSharedToolBehaviorMixin` 加一个 `if (item instanceof XxxTool)` 分支（词条会追加到自身词条之后）。
4. 事件型效果（攻击/挖掘/掉落）→ 新建 `@Mod.EventBusSubscriber` 的 Handler，内用 `instanceof XxxTool` 判定；需要跳过自算伤害时复用/新增 `APPLYING_TAG`。
5. 特例（某件物品不适用某效果）→ 在分支条件里显式写排除并注释原因，不要用接口 override 隐藏。

### 不要做的事

- 不要用「标记接口 + `default` 方法」自动派发（class wins，且 default 无法叠加）。
- 不要在 `appendHoverText` 里再手写系列公共词条（会与 Mixin 追加重复）。
- 不要 `@Overwrite` 原版方法；本项目已因接口注入冲突踩过坑（见 `document/rule/program/程序规范文档.md`）。

---

# Tool Series Shared Behavior Unification (Mixin Dispatch)

- Date: 2026-11-12
- Version transition: 0.9.0beta2 ~ 1.0.0alpha
- Scope: Melt Dream Crystal / Molten Gold / Shadow Alloy / Shadow Erosion / Fluffy Wind Alloy (including the Windwreathed Thunder Spear)
- Follow-up fix (2026-11-13): moved shared tooltip insertion before vanilla attribute lines, so attributes, item ID, and mod name appear below it.
- Verified with: `.\gradlew compileJava`, `.\gradlew runData`; refmap generated correctly
  (`Item#inventoryTick` → `m_6883_`, `ItemStack#getTooltipLines` → `m_41651_`)

## 1. Background

Every tool series used to reuse logic via a marker interface plus hand-written `inventoryTick` / `appendHoverText` / `getAttributeModifiers` overrides in each concrete tool class:

- The same `super.xxx(...); XxxToolHelper.xxx(...);` lines were duplicated across 4–5 classes.
- Adding a new tool meant copying them again, easy to miss one.
- Series could not be combined: an item wanting two series' passives required editing every override by hand.

The goal: `implements` the series marker interface = automatically gain that series' shared effects, with the shared logic centralized in one or two injection points.

## 2. Design Evolution (including rejected approaches)

### 2.1 Interface `default` methods — not viable

`SwordItem` / `PickaxeItem` / `AxeItem` inherit **concrete** `inventoryTick` / `appendHoverText` from `Item`. Per JLS "class wins", the superclass concrete method always beats an interface `default`: a subclass that does not override never runs the `default`, and `Xxx.super.method()` only reaches the interface `default`, not the superclass implementation.

### 2.2 A `SharedToolBehavior` interface with `default` methods, explicitly called from a Mixin — abandoned

A temporary implementation did `((Object) this instanceof SharedToolBehavior b)` inside a mixin and then called `b.xxx()`. This reliably triggers the `default`, but has a fatal flaw:

> Interface `default` methods **cannot be composed**. If an addon mod wants one tool to carry several series' passives (implementing two marker interfaces), the two same-signature `default` methods conflict outright (Java allows only one implementation) — either a compile error, or only one can be kept.

### 2.3 Final approach: two mixins + independent `instanceof` branches

At fixed injection points, dispatch per series with **independent, non-interfering `if` blocks**:

```java
// ItemSharedToolBehaviorMixin (Item#inventoryTick TAIL)
if (self instanceof MeltDreamTool)    MeltDreamToolHelper.onInventoryTick(...);
if (self instanceof ShadowAlloyTool)  ShadowAlloyToolHelper.onInventoryTick(...);

// ItemStackSharedToolBehaviorMixin (before attribute lines in ItemStack#getTooltipLines)
if (item instanceof MeltDreamTool)     MeltDreamToolHelper.appendHoverText(...);
if (item instanceof MoltenGoldTool)    MoltenGoldToolHelper.appendHoverText(...);
if (item instanceof ShadowAlloyTool)   ShadowAlloyToolHelper.appendHoverText(...);
if (item instanceof ShadowErosionTool) ShadowErosionToolHelper.appendHoverText(...);
if (item instanceof FluffyWindAlloyTool) FluffyWindAlloyToolHelper.appendHoverText(...);
```

Benefits:
- Implementing several interfaces runs several branches: passives **stack** and never overwrite each other.
- Addon mods just `implements` our marker interface to inherit the passive; no mixin changes required.
- New series = one extra branch in the mixin, no tool classes touched.

## 3. Final Structure

| Injection point / Handler | Target | Dispatched series |
|---|---|---|
| `mixin/ItemSharedToolBehaviorMixin` | `Item#inventoryTick` (TAIL) | Melt Dream (energy repair), Shadow Alloy (SAN/health repair) |
| `mixin/ItemStackSharedToolBehaviorMixin` | `ItemStack#getTooltipLines` (before `TooltipPart.MODIFIERS`, after the item's own lines) | Melt Dream, Molten Gold, Shadow Alloy, Shadow Erosion, Fluffy Wind |
| `ShadowAlloyAttackHandler` | `LivingHurtEvent` | Shadow Alloy (low-SAN attack bonus) |
| `ShadowAlloyMiningSpeedHandler` | `PlayerEvent.BreakSpeed` | Shadow Alloy (shadow blocks + light + low SAN mining) |
| `ShadowErosionMiningSpeedHandler` | `PlayerEvent.BreakSpeed` | Shadow Erosion (shadow blocks + light mining) |
| `FluffyWindAlloyAttackHandler` | `LivingHurtEvent` | Fluffy Wind (lightning + move-speed damage; spear dash pre-branch) |
| `FluffyWindAlloyMiningSpeedHandler` | `PlayerEvent.BreakSpeed` | Fluffy Wind (move-speed mining) |
| `FluffyWindAlloyDropSweepHandler` | `BreakEvent.BreakEvent` / `LivingDropsEvent` | Fluffy Wind (drop sweep; includes thrown spear phantom) |

> Event-driven effects stay in each series' own handler guarded by `instanceof 标记接口`; the mixins only cover the two vanilla methods that have no event equivalent (`inventoryTick` / `getTooltipLines`).

### 3.1 Marker interface status

| Interface | Automatic once implemented | Still per-class |
|---|---|---|
| `MeltDreamTool` | Inventory energy repair, shared tooltip | — |
| `MoltenGoldTool` | Shared tooltip (inlay / right-click Haste / cooldown) | Right-click Haste `use()` (each class calls `MoltenGoldToolHelper.use`) |
| `ShadowAlloyTool` | SAN/health repair, shared tooltip, low-SAN attack bonus, mining bonuses | Main-hand SAN variability / reach `getAttributeModifiers` |
| `ShadowErosionTool` | Shared tooltip, shadow erosion mining traits | Main-hand SAN variability / reach `getAttributeModifiers` |
| `FluffyWindAlloyTool` | Shared tooltip, lightning + move-speed damage, move-speed mining, drop sweep | Spear riptide dash spin hit (`applyDashOnTouch` dedicated branch) |

Why `getAttributeModifiers` is not automated: it is an `IForgeItem` interface default method on `Item` (not present in `Item` bytecode), so it cannot be `@Inject`ed directly. Automating it needs a separate mixin that merges a method into `Item` (see `ItemElytraMixin`); out of scope here to keep the change contained.

## 4. Changes per Series

### 4.1 Melt Dream Crystal (`world/item/meltdreamtool/`)

- Removed duplicate `inventoryTick` / `appendHoverText` overrides from 5 tools; sword/bow keep only their own combat-art tooltips.
- `MeltDreamToolHelper.onInventoryTick/appendHoverText` made `public` for the mixin.
- Shared tooltip appended via `ItemStack#getTooltipLines` (after the item's own lines and before attributes).

### 4.2 Molten Gold (`world/item/moltengoldtool/`)

- Removed `appendHoverText` from the 4 non-sword tools; shared tooltip dispatched centrally.
- **`MoltenGoldSwordItem` gained right-click Haste** (`use()` → `MoltenGoldToolHelper.use`); previously only pickaxe/axe/shovel/hoe had it.
- Removed the sword's own "Inlaid: §7None" line (duplicated the shared tooltip's first line); lang key `tooltip.pasterdreammod.molten_gold_sword.1` removed too.

### 4.3 Shadow Alloy (`world/item/shadowalloytool/`)

- Removed `inventoryTick` / `appendHoverText` from 4 classes; `getAttributeModifiers` kept per class.
- `ShadowAlloyToolHelper.onInventoryTick/appendHoverText` made `public`.

### 4.4 Shadow Erosion (`world/item/shadowerosiontool/`)

- Removed `appendHoverText` from 5 classes; `getAttributeModifiers` kept per class.
- `ShadowErosionToolHelper.appendHoverText` made `public`.

### 4.5 Fluffy Wind Alloy (`world/item/fluffywindalloy/` + spear)

The passive is unified as "Gale Passage" and applies to sword/axe/pickaxe/shovel/hoe/spear:

1. Attacks deal additional lightning damage (`attack damage × 0.1`, lightning damage source, kill credit preserved);
2. The faster you move, the faster you mine and the higher your attack damage (melee `(1 + move speed) × attack damage`, crit/enchant ratio preserved);
3. Drops from broken blocks or killed mobs are swept to you.

Details:
- Added `FluffyWindAlloyAttackHandler`, replacing the sword's `WindAlloyPassiveHandler` ("Thunder Follows the Wind") and the axe's `WindAlloyAxePassiveHandler`.
- `FluffyWindAlloySwordItem` now `implements FluffyWindAlloyTool`; its dedicated passive handler / tooltip / constants were removed.
- `WindwreathedThunderSpearItem` now `implements FluffyWindAlloyTool`:
  - `ThunderSpearPassiveHandler` and `lightningSource` removed; normal melee uses the shared handler;
  - The riptide dash spin hit keeps its dedicated `applyDashOnTouch` (`public static`), pre-branched by the shared handler on `isAutoSpinAttack() && main hand is spear`, so riptide bonus / sweep are unchanged;
  - `APPLYING_TAG` unified to `FluffyWindAlloyToolHelper.APPLYING_TAG` (the old `pasterdream:thunder_spear_applying` was only a runtime `persistentData` marker, no save compatibility impact).
- Shared tooltip unified to 4 lines: `Passive: Gale Passage` + 3 effect lines; each tool's own combat-art lines stay first.
- `FluffyWindAlloyDropSweepHandler` simplified: only checks `FluffyWindAlloyTool` (spear matches automatically; the thrown phantom still uses the `WindThunderSpearEntity` branch).

## 5. Behavior Changes (player-visible)

1. **Molten Gold Sword** gains right-click Haste (consume hunger, 10s cooldown) and shows the corresponding shared tooltip.
2. **Fluffy Wind Sword**: the "Thunder Follows the Wind" naming and standalone tooltip are gone, merged into "Gale Passage"; numbers unchanged (`(1+speed)×attack` + `attack×0.1` lightning).
3. **Fluffy Wind Axe/Pickaxe/Shovel/Hoe**: now also "attacks deal additional lightning damage" (previously only sword/spear); tooltip unified to 3 lines.
4. **Windwreathed Thunder Spear**: passive renamed/merged into "Gale Passage", effects unchanged; shared tooltip identical to the tools.
5. **Fluffy Wind Sword and Spear now also get "move speed → mining speed"** (unified interface + unified tooltip). To exclude, add an item check in `FluffyWindAlloyMiningSpeedHandler`.
6. Shared tooltip lines appear after the item's own lines and before attributes, item ID, and mod name. The 2026-11-13 fix corrected the original RETURN injection's ordering bug.

## 6. File Changes

### Added

- `mixin/ItemSharedToolBehaviorMixin.java`
- `mixin/ItemStackSharedToolBehaviorMixin.java`
- `world/item/fluffywindalloy/FluffyWindAlloyAttackHandler.java`
- `changelog/refactor/` (this documentation directory)

### Removed (in code)

- `FluffyWindAlloySwordItem.WindAlloyPassiveHandler`
- `FluffyWindAlloyAxeItem.WindAlloyAxePassiveHandler`
- `WindwreathedThunderSpearItem.ThunderSpearPassiveHandler`
- (A temporary `world/item/SharedToolBehavior.java` was created during design exploration, rejected, and deleted.)

### Modified (by area)

- `pasterdream.mixins.json`: registered the two new mixins
- `world/item/meltdreamtool/*`, `moltengoldtool/*`, `shadowalloytool/*`, `shadowerosiontool/*`, `fluffywindalloy/*`, `world/item/WindwreathedThunderSpearItem.java`
- `datagen/lang/ModZhCnLangProvider.java`, `ModEnUsLangProvider.java`

## 7. Language Key Changes

- Added: `tooltip.pasterdream.fluffy_wind_alloy_tool.passive3`
- Changed: `tooltip.pasterdream.fluffy_wind_alloy_tool.passive1/2` (unified 3 effect lines)
- Removed:
  - `tooltip.pasterdreammod.molten_gold_sword.1`
  - `tooltip.pasterdream.fluffy_wind_alloy_sword.passive_name/passive1/passive2`
  - `tooltip.pasterdream.fluffy_wind_alloy_axe.passive1/passive2`
  - `tooltip.pasterdream.windwreathed_thunder_spear.passive_name/passive1/passive2`

> `src/generated/` is datagen output (gitignored). After editing language keys, rerun `.\gradlew runData`; never hand-edit it.

## 8. Related Documentation Updates

- `document/rule/program/架构规范.md`: mixin count/usage now mentions tool-series shared behavior dispatch.
- `document/rule/program/程序规范文档.md` "Common Mistakes": added "interface default methods cannot override concrete superclass methods" (class wins + cannot compose).
- `document/design/item/ToolTips文档.md`, `document/design/item/萦风雷矛.md`: tooltips and passive descriptions synced.

## 9. Incremental Guide

### Adding a new "auto-applied" tool series

1. Create the marker interface (do not put `default` methods on it) and an `XxxToolHelper`.
2. Need inventory tick → add an `if (self instanceof XxxTool)` branch in `ItemSharedToolBehaviorMixin`.
3. Need shared tooltip → add an `if (item instanceof XxxTool)` branch in `ItemStackSharedToolBehaviorMixin` (appended after the item's own lines).
4. Event-driven effects (attack/mining/drops) → new `@Mod.EventBusSubscriber` handler with an `instanceof XxxTool` guard; reuse/add an `APPLYING_TAG` to skip self-computed damage.
5. Exceptions (an item that should not get a certain effect) → write the exclusion explicitly in the branch condition with a comment; do not hide it behind an interface override.

### Don'ts

- Do not dispatch via "marker interface + `default` method" (class wins, and defaults cannot compose).
- Do not hand-write series-shared tooltip lines inside `appendHoverText` (it will duplicate what the mixin appends).
- Do not `@Overwrite` vanilla methods; this project already got burned by interface-injection conflicts (see `document/rule/program/程序规范文档.md`).

