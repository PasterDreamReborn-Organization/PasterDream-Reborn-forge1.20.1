---
name: entity
description: 新增/修改实体（生物、弹射物、投影物）。覆盖实体类型注册、GeckoLib/非 GeckoLib 渲染器、属性注册、战利品表、自然生成、刷怪蛋。当用户要加实体、改实体 AI/渲染/掉落时使用。
---

# 实体系统

核心规范见 `document/rule/program/架构规范.md` 与 `document/rule/program/目录结构组织.md`。

---

## 关键约束

- 实体类放 `world/entity/`，模型放 `client/model/`，渲染器放 `client/renderer/`
- 实体类型在 `init/ModEntities.java` 注册（`DeferredRegister<EntityType<?>>`）
- **渲染器统一在 `init/ModEntityRenderer.java` 注册**（参照 `ModBlockEntityRenderer` 模式），不要在主类零散注册
- 实体属性在 `ModEntities.registerAttributes()` 注册
- GeckoLib 实体：模型 `GeoModel`、渲染器 `GeoEntityRenderer`；非 GeckoLib 可用原版模型（如 `ChickenModel`）
- 实体纹理：非 GeckoLib → `textures/entities/`，GeckoLib → `textures/entity/`
- `init()` 方法中注册 `SpawnPlacements`

---

## 刷怪蛋

- `init/ModItems.java` 注册 `ForgeSpawnEggItem`
- 模型用 `spawnEggItem()` 辅助方法（父模型 `minecraft:item/template_spawn_egg`），在 `ModItemModelsProvider` 调用
- 放入 `PASTERDREAM_DREAM_DEBUG_TAB`（调试物品栏）
- 颜色：第一参底色，第二参斑点色

---

## 战利品表

- `datagen/common/ModEntityLootTablesProvider.java`（`LootTableSubProvider`，上下文 `ENTITY`）
- 在 `ModDataGenerator` 的 `LootTableProvider` 加 `SubProviderEntry`
- **不要**用 `ApplyBonusCount` / 时运函数——`ENTITY` 上下文缺 `tool` 参数会导致 datagen 失败

---

## 自然生成（群系修饰符）

- `worldgen/biome/ModBiomeModifierProvider.java` 用 `addSpawns()`，标签 `forge:add_spawns`
- 生成条件方块**必须**在 `BlockTags.ANIMALS_SPAWNABLE_ON` 标签（`ModBlockTagsProvider` 维护）

---

## 暗影生物

- 实现 `IShadowMob` 标记接口
- 加入 `#pasterdream:shadow_mob` 实体标签（`ModEntityTypeTags`）
- 难度相关逻辑见 `.claude/skills/shadow-difficulty/SKILL.md`

---

## 文件速查

| 用途 | 路径 |
|------|------|
| 实体实现 | `world/entity/` |
| 实体类型注册 | `init/ModEntities.java` |
| 渲染器注册 | `init/ModEntityRenderer.java` |
| 模型 | `client/model/` |
| 渲染器 | `client/renderer/` |
| 实体战利品表 | `datagen/common/ModEntityLootTablesProvider.java` |
| 实体标签 | `datagen/common/ModEntityTypeTagsProvider.java` |
| 生成修饰符 | `worldgen/biome/ModBiomeModifierProvider.java` |
| 生成事件 | `event/ModMobSpawnEvents.java` |
| 掉落修改 | `event/ModMobDrops.java` |

---

## 引用文档

- `document/rule/program/架构规范.md` — entity 模块职责
- `.claude/skills/client-rendering/SKILL.md` — 渲染器与模型细节
- `.claude/skills/shadow-difficulty/SKILL.md` — 暗影生物难度
