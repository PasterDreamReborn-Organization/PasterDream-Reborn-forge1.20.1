---
name: recipe-container
description: 新增/修改配方或容器类工艺方块（陶盆、梦之釜、研钵、研究台、梦蓄器、酿造、金狐交易）。覆盖通用配方框架、容器配方平衡、NBT 保留配方、JEI 兼容。当用户要加配方、做容器方块、处理带 craftRemainder 容器的合成配方时使用。
---

# 配方与容器系统

核心规范，详细设计见 `document/rule/program/容器配方平衡系统.md` 与 `document/rule/program/架构规范.md`。

---

## 关键约束

- 所有配方类型集中在 `recipe/` 包，序列化器统一在 `init/ModRecipes.java` 注册
- 复杂容器方块独占子包，固定含 Block + BlockEntity + Menu + Screen + Renderer + Recipe
- 带 `craftRemainder` 的填充容器配方**必须**走容器平衡（见下）
- JEI 兼容放 `compat/jei/`，在 `ModJEIPlugin.java` 注册类别
- 通用配方框架（`recipe/genericrecipe/`）提供接口、抽象基类、Serializer 基类、MatchResult、匹配与处理工具，自定义配方优先复用

---

## 容器配方平衡

填充容器物品（如 `GLASS_JAR_OF_WATER` → 空容器 `GLASS_JAR`）作为原料时会返还空容器，产物也是容器时导致数量失衡。

- **E** = 原料中空容器数，**F** = 原料中填充容器数，**O** = 产物中填充容器数
- 平衡公式：`cancelCount = min(F, max(0, O - E))`
- `O > E + F` → 无法配平，datagen 报错；`O = E` → 天然平衡；`O < E` → 正常返还

**新建填充容器物品必须做：**
1. 设 `craftRemainder`（`PasterDreamDrinkAndFoodProperties` 已协变返回，普通 Item 用 `new Item.Properties().craftRemainder(...)`）
2. 在 `ModFluidContainerRelation.java` 用 `FluidContainerRegistry.register(空容器, 流体, 量, 填充容器)` 注册关系

**datagen 写法：** 产物是填充容器的配方用 `saveContainerBalancedShapeless` / `saveContainerBalancedShaped`，校验参数 `(产物, 产物数量, 原料1, 数量1, ...)`，无需手动补空罐子。

---

## 容器工艺方块清单

| 方块 | 包 | 模式 |
|------|-----|------|
| 陶盆 | `world/block/claypan/` | 独立工艺方块 |
| 梦之釜 | `world/block/dreamcauldron/` | GeckoLib 渲染 |
| 梦蓄器 | `world/block/dreamaccumulator/` | 按群系条件产出 |
| 研究台 | `world/block/researchtable/` | Copy + Research 双模式 |
| 研钵（物品容器） | `world/item/mortar/` | 物品型容器，含 NBT 同步 |
| 金狐交易 | `recipe/GoldenFoxTradeRecipe.java` | 交易配方 |

---

## 实现步骤

1. 定义配方类（无特殊逻辑复用 `genericrecipe` 基类，有特殊逻辑新建）
2. 序列化器在 `init/ModRecipes.java` 注册
3. datagen 在 `datagen/common/ModRecipesProvider.java` 生成
4. 容器配方若需保留 NBT，用 `saveNbtPreserving()` / `saveNbtPreservingShaped()` 包装
5. JEI 兼容：新建 `compat/jei/<recipe>/`，在 `ModJEIPlugin.java` 注册
6. `runData` 生成 JSON，检查 `src/generated/`

---

## 文件速查

| 用途 | 路径 |
|------|------|
| 通用配方框架 | `recipe/genericrecipe/` |
| 配方序列化器注册 | `init/ModRecipes.java` |
| 容器平衡核心 | `helper/ContainerBalanceHelper.java` |
| NBT 保留配方 | `recipe/NbtPreservingShapelessRecipe.java` / `NbtPreservingShapedRecipe.java` |
| 研钵配方 | `world/item/mortar/MortarCraftingRecipe.java` |
| 自定义原料 | `helper/pasterdreamingredient/` |
| 带流体槽容器基类 | `helper/abstractcontainermenuwithfluidslot/` |
| 流体容器能力 | `helper/fluidcontainercapability/` |
| 配方 datagen | `datagen/common/ModRecipesProvider.java` |
| 配方工具 | `datagen/util/RecipeHelpers.java` |
| JEI 插件 | `compat/jei/ModJEIPlugin.java` |

---

## 引用文档

- `document/rule/program/容器配方平衡系统.md` — 平衡公式、使用、常见问题
- `document/rule/program/架构规范.md` — 各容器方块包职责、JEI 集成规范
