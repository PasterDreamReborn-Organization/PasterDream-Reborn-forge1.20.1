---
name: worldgen
description: 新增/修改世界生成（群系、维度类型、噪声设置、LevelStem、地物 ConfiguredFeature/PlacedFeature、结构、自定义 Feature）与运行时跨维度传送。覆盖 bootstrap 数据生成模式与跨维度传送的效果补发 + 落点同步约定。当用户要加群系/维度/结构/地物、改世界生成、做维度传送时使用。
---

# 世界生成（Worldgen）

世界生成对象**不走 DeferredRegister**，而是通过数据生成器的 `RegistrySetBuilder` + `DatapackBuiltinEntriesProvider` 模式产出 JSON。核心规范见 `document/rule/program/架构规范.md`「世界生成」段。

---

## 注册体系

| 对象 | 类 | Registry |
|------|-----|----------|
| 配置的地物 | `worldgen/ModConfiguredFeatures.java` | `CONFIGURED_FEATURE` |
| 放置的地物 | `worldgen/ModPlacedFeatures.java` | `PLACED_FEATURE` |
| 群系 | `worldgen/biome/ModBiomes.java` | `BIOME` |
| 群系修改器 | `worldgen/biome/ModBiomeModifiers.java` | `BIOME_MODIFIERS` |
| 维度类型 | `worldgen/dimension/ModDimensionTypes.java` | `DIMENSION_TYPE` |
| 噪声设置 | `worldgen/dimension/ModNoiseSettings.java` | `NOISE_SETTINGS` |
| 维度 | `worldgen/dimension/ModLevelStems.java` | `LEVEL_STEM` |

全部由 `datagen/common/ModWorldGenProvider.java`（`DatapackBuiltinEntriesProvider`）串联，`RegistrySetBuilder` 按依赖顺序注册（BIOME / DIMENSION_TYPE / NOISE_SETTINGS 在 LEVEL_STEM 之前）。

---

## 关键约束

- 自定义 `Feature<?>` 实现放 `worldgen/feature/`，与注册入口类分离
- 结构数据生成 Provider 放 `worldgen/structures/`（`ModStructureProvider` / `ModStructureSetProvider` / `ModTemplatePoolProvider`）
- 自定义 `DimensionSpecialEffects` 用客户端 Mixin（`FogRendererMixin`、`LevelRendererMixin`）注入，注入约束见 [[mixin]]（禁止优先 `@Overwrite`）
- 运行时通过 `ResourceKey` 引用（如 `ModTreeGrowers` → `ModConfiguredFeatures.DYEDREAM_TREE`）

---

## 维度/群系搬运流程

1. 维度类型 → `ModDimensionTypes.java`（`DimensionType`，含 MonsterSettings）
2. 噪声设置 → `ModNoiseSettings.java`（`NoiseGeneratorSettings`，含 NoiseRouter + SurfaceRules）
3. 维度注册 → `ModLevelStems.java`（`LevelStem`，关联 BiomeSource + ChunkGenerator）
4. 群系注册 → `ModBiomes.java`（`Biome.BiomeBuilder`，含特效、生物生成、雕刻器、特征列表）
5. 自定义 DimensionSpecialEffects → 客户端 Mixin

---

## 运行时维度传送

跨维度把玩家送往自定义维度/主世界，统一调用：

```java
TeleportHelper.teleportToDimension(player, target, x, y, z, yRot, xRot); // helper/TeleportHelper.java
```

**关键坑**：六参 `ServerPlayer.teleportTo(ServerLevel, ...)` 只发送 `ClientboundRespawnPacket`，客户端会重建一个不带效果实例的 `LocalPlayer`；且重生包与位置包同 tick 连发时，客户端处理重生期间可能忽略位置包，停留在原传送门坐标并继续上报移动，服务端确认窗口（20 tick）超时后接受旧坐标把玩家拉回入口，反复触发传送。本地通常正常，高延迟服务器会卡「加载地形」且 ping 飙升，服务端状态却正常。

`teleportToDimension` 已统一处理：带 `Set` 的 `teleportTo` 重载（落点加 `POST_TELEPORT` 票）→ 补发状态效果 → 后续 3 tick 强制同步落点。禁止跨维度直连六参 `teleportTo`。

- 同维度传送（目标 `ServerLevel == player.level()`）不触发重生包，可直接 `teleportTo`
- 已是跨维度调用点的参考：`event/PlayerEvents.java`（睡觉）、`world/wind/WindDirectionHandler.java`、`world/block/portal/DyedreamWorldPortalTeleporter.java`、`world/block/portal/DyedreamCrackBlock.java`、`world/block/twilightlantern/LampShadowWorldTeleporter.java`、`world/dimension/AaroncosArenaTeleporter.java`
- 自动化逻辑详见 `document/rule/program/程序规范文档.md`「跨维度 teleportTo 需延迟同步落点」

---

## 文件速查

| 用途 | 路径 |
|------|------|
| 地物 bootstrap | `worldgen/ModConfiguredFeatures.java` / `ModPlacedFeatures.java` |
| 自定义 Feature | `worldgen/feature/` |
| 群系 | `worldgen/biome/ModBiomes.java` |
| 群系修改器 | `worldgen/biome/ModBiomeModifiers.java` + `ModBiomeModifierProvider.java` |
| 维度 | `worldgen/dimension/` |
| 结构 Provider | `worldgen/structures/` |
| 世界生成 datagen | `datagen/common/ModWorldGenProvider.java` |
| 结构生成辅助 | `helper/structuregenerate/` |
| 结构生成事件 | `event/ModWorldGenEvents.java` |
| 跨维度传送（效果补发 + 落点同步） | `helper/TeleportHelper.java` |

---

## 引用文档

- `document/rule/program/架构规范.md` — 世界生成数据流、子包划分
- `document/design/风之旅途维度搬运分析.md` / `风之旅途风向机制搬运分析.md` — 维度搬运案例
