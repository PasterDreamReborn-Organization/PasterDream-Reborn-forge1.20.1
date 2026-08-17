---
name: datagen
description: 数据生成器（runData）。覆盖所有 Provider（方块状态、战利品表、标签、物品模型、配方、语言、进度、世界生成）的职责与约束。当用户要跑数据生成、加 Provider、改标签/模型/配方生成逻辑时使用。
---

# 数据生成器（Datagen）

所有 Provider 位于 `datagen/`，入口 `datagen/ModDataGenerator.java`。数据输出到 `src/generated/resources/`（**不可手动编辑**）。完整目录见 `document/rule/program/目录结构组织.md`。

---

## Provider 清单

| Provider | 职责 |
|----------|------|
| `ModBlockStateProvider` | 方块状态与模型 |
| `ModBlockLootTablesProvider` | 方块战利品表 |
| `ModEntityLootTablesProvider` | 实体战利品表（`LootTableSubProvider`，上下文 `ENTITY`，**不用** `ApplyBonusCount`） |
| `ModBlockTagsProvider` | **必须**补全标签（挖掘工具 + 材质类型 + 功能标签 + `ANIMALS_SPAWNABLE_ON`） |
| `ModItemModelsProvider` | 物品模型（工具 `handheldItem()`、普通 `basicItem()`、刷怪蛋 `spawnEggItem()`） |
| `ModItemTagsProvider` | 物品标签（`copy()` 从方块标签同步，工具 `ItemTags.SWORDS` 等） |
| `ModEntityTypeTagsProvider` | 实体类型标签（走 datagen，**不手写 JSON**） |
| `ModBiomeTagsProvider` | 群系标签 |
| `ModFluidTagsProvider` | 流体标签 |
| `ModRecipesProvider` | 配方 |
| `ModAdvancementProvider` | 进度 |
| `ModWorldGenProvider` | 世界生成（`DatapackBuiltinEntriesProvider`） |
| `ModBucketItemProvider` | 流体桶物品模型 |

语言文件：`datagen/lang/ModEnUsLangProvider.java` / `ModZhCnLangProvider.java`。

---

## 关键约束

- 所有数据生成类以 `Provider` 结尾
- 标签常量定义在 `tag/`（`ModBlockTags`、`ModItemTags`、`ModEntityTypeTags`），Provider 引用常量
- 配方工具 `datagen/util/RecipeHelpers.java`（`plankFamilyRecipes` / `storageCompress` / `storageDecompress`）
- 战利品工具 `datagen/util/LootHelpers.java`
- 容器配方平衡见 `.claude/skills/recipe-container/SKILL.md`
- 粒子纹理、音效、饰品槽位标签、Curios 标签**不走 datagen**，手动维护
- 翻译值不用格式化代码控制颜色，用 `.rarity()`

---

## 运行

```
./gradlew runData
```

生成后检查 `src/generated/` 下的 JSON。

---

## 文件速查

| 用途 | 路径 |
|------|------|
| 入口 | `datagen/ModDataGenerator.java` |
| 各 Provider | `datagen/common/` |
| 语言 | `datagen/lang/` |
| 工具 | `datagen/util/` |
| 标签常量 | `tag/` |

---

## 引用文档

- `document/rule/program/目录结构组织.md` — 数据生成器输出目录、Provider 全列表
- `document/rule/program/容器配方平衡系统.md` — 配方容器平衡 datagen 写法
