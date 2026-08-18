---
name: port-content
description: 从源模组搬运方块、物品、工具、护甲、实体到重置模组。覆盖全流程：分析→映射→注册→渲染→数据生成→材质→语言→物品栏→配方→测试。
---

# 搬运流程

> 本 skill 是跨系统的「搬运」总流程编排。搬运到具体系统时，配合对应系统 skill 获取领域细节：
> [[recipe-container]]（配方/容器）、[[curio]]（饰品）、[[entity]]（实体）、[[worldgen]]（世界生成）、[[advancement-notes]]（进度/笔记）、[[capability]]（能力）、[[skill-system]]（技能）、[[shadow-difficulty]]（暗影难度）、[[datagen]]（数据生成）、[[client-rendering]]（渲染/粒子/音效）。

严格按以下步骤执行，不要跳过。

---

## 步骤 1：明确搬运目标

- 确认用户要搬运的具体方块/物品/实体清单
- 在 `NOT_MODIFY/reference/` 和 `NOT_MODIFY/reference-resources/` 中定位旧版源码与资源（**只读，严禁修改**）

---

## 步骤 2：规范性分析

对每个要搬运的方块/物品，检查并记录：

- **ID 规范**：旧 ID 是否符合 snake_case 语义化命名？是否需要重命名？
- **客制化类**：旧类是否真的必要？

| 情况 | 处理 |
|------|------|
| 方块仅设置属性（硬度/抗性/音效） | 不需要客制化类，直接 `new Block(Properties...)`，含 `FallingBlock`/`RotatedPillarBlock` 等原版子类 |
| 物品无特殊逻辑 | 不需要客制化类，直接 `new Item(Properties...)` |
| 有覆写方法（交互/GUI/食物效果/tick 等） | 创建客制化类 |

- **方块类型判断**：对照旧类确定应使用的原版类（`AmethystClusterBlock`/`FlowerBlock`/`StairBlock` 等）
- **材质/配方**：在 `NOT_MODIFY/old-recipes/` 中按产物名查找旧配方
- 旧 ID 中的 `armor` 字段应删除（`copper_armor_helmet` → `copper_helmet`）
- 工具/护甲的材料 ID 保持一致性（如 `molten_gold_sword` / `molten_gold_helmet`）
- 旧 MCreator ID 中的 word glueing（如 `moltengold`）应加下划线（→ `molten_gold`）
- 用minecraft-dev-mcp判断没有必要的覆写
---

## 步骤 3：登记 ID 映射

- 写入 `document/design/ID映射表.md`
- 运行 `cd tools && python generate_id_mapping.py` 刷新 `mapping.json`

---

## 步骤 4：代码注册

### 方块

- 在 `init/ModBlocks.java` 中注册，**必须**设置 `.mapColor()`
- 有特殊机制 → 创建客制化类放在 `world/block/` 包下
- 在 `init/ModItems.java` 中注册对应 `BlockItem`

### 物品

- 在 `init/ModItems.java` 中注册
- 有特殊机制 → 创建客制化类放在 `world/item/` 包下

### 工具

- 先在 `world/item/ModToolTiers.java` 中定义 `Tier` 枚举值
- 创建 `world/item/<material>tool/` 包，放客制化工具类
- 共享逻辑提取到 `*ToolHelper` 包级工具类
- `SwordItem(Tier, int damage, float speed, Properties)`
- `PickaxeItem(Tier, int damage, float speed, Properties)`
- `AxeItem(Tier, float damage, float speed, Properties)`
- `ShovelItem(Tier, float damage, float speed, Properties)`
- `HoeItem(Tier, int damage, float speed, Properties)`

### 护甲

- 先在 `world/item/ModArmorMaterials.java` 中定义 `ArmorMaterial` 枚举值
- 套装效果：创建 `world/item/armoritem/` 下的客制化头盔类，`inventoryTick` 检测全套施加 buff
- 其余三件用原版 `ArmorItem`（仅头盔需要客制化类）
- 纹理：物品图标 `textures/item/<id>.png`，穿戴模型 `textures/models/armor/<material>_layer_1/2.png`

### 饰品（Curios）

- 客制化类放在 `world/item/curio/` 包下，实现 `ICurioItem`
- 品质使用 `ModRarities`（COMMON/EXCELLENT/SUPERIOR 等），**不要**使用原版 `Rarity`
- 品质 tooltip 用 `ModRarities.qualityTooltip(ModRarities.XXX)`，放在 `appendHoverText` 首位
- 增加幸运值请使用MC原版LUCK

**饰品效果实现方式：**

| 方式 | 适用场景 | 注意 |
|------|---------|------|
| `getAttributeModifiers()` | 静态属性加成（生命值、传送距离等） | 会自动生成 tooltip，**不要**再手动添加效果描述 |
| `curioTick()` | 每 tick 持续效果（药水、放置方块等） | 检查 `!level.isClientSide()` |
| `onEquip()` / `onUnequip()` | 装备/卸下时的一次性属性变更 | 配合 `AttributeInstance.addPermanentModifier/removeModifier` |
| `makesPiglinsNeutral()` | 猪灵中立 | 仅需覆写此方法，无需额外 tooltip |
| `canEquip()` | 禁止重复装备同类饰品 | 用 `CuriosApi.getCuriosInventory().findFirstCurio()` 检测 |
| 事件驱动（外部 handler） | 饰品效果依赖特定游戏事件触发（闪避、受击、攻击等） | 在 `PlayerEvents` 或 `CurioPassiveHandler` 中订阅对应 Forge 事件，用 `CuriosApi.getCuriosInventory()` 检测饰品，见下方说明 |

**饰品标签（Curio 槽位）：**
- **手动维护** `src/main/resources/data/curios/tags/items/<slot>.json`，**不走 datagen**
- 槽位类型：`charm`（护符）、`necklace`（项链）、`ring`（戒指）、`belt`（腰带）、`head`（头部）、`back`（背部）、`body`（身体）
- 每个 slot JSON 中加上 `"minecraft:air"` 条目允许空槽

**饰品被动效果：**
- 全局 tick 处理放在 `CurioPassiveHandler.java`，通过 `@EventBusSubscriber` 订阅 `LivingTickEvent`
- 事件驱动型效果（如闪避后触发、攻击后取消）在 `event/PlayerEvents.java` 中注册 handler，通过 `CuriosApi.getCuriosInventory()` 检测饰品是否装备后施加逻辑
- 需要临时属性修改时，创建自定义 `MobEffect`（放 `world/effect/`），在构造中 `addAttributeModifier()`，注册到 `init/ModEffects.java`，效果图标放 `textures/mob_effect/`

**饰品配方：**
- 统一写入 `curioRecipes()` 方法（独立于 `othersRecipes`）
- 胚胎原胚配方（钛金粒 + 灵魂精华），升级配方（原胚 + 特定材料）

### 实体

- 实体类放在 `world/entity/` 包下
- 在 `init/ModEntities.java` 中注册 `EntityType`（`DeferredRegister<EntityType<?>>`）
- **实体渲染器**统一在 `init/ModEntityRenderer.java` 中注册（参照 `ModBlockEntityRenderer` 模式），不要在 `PasterDreamMod.java` 主类中零散注册
- GeckoLib 动画实体：模型放 `client/model/`，渲染器放 `client/renderer/`
- 非 GeckoLib 实体：在 `client/renderer/` 中创建标准渲染器（可直接复用原版模型，如 `ChickenModel`）
- 实体属性在 `ModEntities.registerAttributes()` 中注册
- 实体 `init()` 方法中注册 `SpawnPlacements`
- 音效优先复用原版 `SoundEvents`，无需自定义

### 刷怪蛋

- 在 `init/ModItems.java` 中注册 `ForgeSpawnEggItem`（需要 `import net.minecraftforge.common.ForgeSpawnEggItem`）
- 刷怪蛋物品模型用 `spawnEggItem()` 辅助方法（父模型 `minecraft:item/template_spawn_egg`），在 `ModItemModelsProvider` 中调用
- 刷怪蛋放入 `PASTERDREAM_DREAM_DEBUG_TAB`（调试物品栏），不放其他物品栏
- 颜色：第一个参数底色，第二个参数斑点色；尽量选原作中的颜色

### 实体战利品表

- 新建 `datagen/common/ModEntityLootTablesProvider.java`，实现 `LootTableSubProvider`
- 在 `ModDataGenerator` 的 `LootTableProvider` 中添加 `SubProviderEntry(ModEntityLootTablesProvider::new, LootContextParamSets.ENTITY)`
- 实体战利品表**不要**使用 `ApplyBonusCount` / 时运函数——`ENTITY` 上下文验证时缺少 `tool` 参数会导致 datagen 失败

### 实体生成（群系修饰符）

- 在 `worldgen/biome/ModBiomeModifierProvider.java` 中使用 `addSpawns()` 方法（与 `addPatch`/`addFeature` 并列）
- 标签为 `forge:add_spawns`，指定生物群系、实体类型、权重和数量
- **必须**确保实体的生成条件方块在 `BlockTags.ANIMALS_SPAWNABLE_ON` 标签中（`ModBlockTagsProvider` 维护）

### 技能

- 已有瞬身术（Blink）作为参考架构：
  - 按键绑定 → `init/ModKeyMappings.java`
  - 自定义属性 → `init/ModAttributes.java`
  - 网络数据包 → `network/skill/`
  - 技能核心逻辑 → `world/skill/`
  - 效果标记 → `world/effect/`
- 新技能遵循相同链路

---

## 步骤 5：渲染层

- 如需 cutout / translucent 渲染，在 `client/ClientSetRenderLayer.java` 中注册
- 不需要在 BlockStateProvider 中手动设置 `renderType`

---

## 步骤 6：数据生成器注册

所有 Provider 位于 `datagen/common/`：

| Provider | 职责 |
|----------|------|
| `ModBlockStateProvider.java` | 方块状态与模型 |
| `ModBlockLootTablesProvider.java` | 方块战利品表 |
| `ModEntityLootTablesProvider.java` | 实体战利品表（`LootTableSubProvider`，上下文 `ENTITY`） |
| `ModBlockTagsProvider.java` | **必须**补全标签（挖掘工具 + 材质类型 + 功能标签 + `ANIMALS_SPAWNABLE_ON`） |
| `ModItemModelsProvider.java` | 物品模型（工具用 `handheldItem()`，普通物品用 `basicItem()`，刷怪蛋用 `spawnEggItem()`） |
| `ModItemTagsProvider.java` | 物品标签（用 `copy()` 从方块标签同步，工具标签 `ItemTags.SWORDS` 等） |
| `ModEntityTypeTagsProvider.java` | 实体类型标签（如 `shadow_mob`），走 datagen，**不要**手动写 JSON |
| `ModBiomeTagsProvider.java` | 群系标签（如 `dyedream_world_biome`、`shadow_mob_spawn_biome`） |
| `ModFluidTagsProvider.java` | 流体标签 |

---

## 步骤 7：粒子

- `init/ModParticleTypes.java` 注册 `SimpleParticleType`
- `client/particle/` 下创建粒子行为类（继承 `TextureSheetParticle`）+ Provider
- `client/particle/ModParticles.java` 中注册 `RegisterParticleProvidersEvent`
- 纹理 JSON 手动维护在 `assets/pasterdream/particles/`（**不走 datagen**）

---

## 步骤 8：音效

- `init/ModSounds.java` 注册 `SoundEvent`
- `src/main/resources/assets/pasterdream/sounds.json` 手动维护
- `src/main/resources/assets/pasterdream/sounds/<name>.ogg` 手动放置
- 在物品类 `use()` / `hurtEnemy()` 中调用 `level.playSound()`

---

## 步骤 9：语言文件

- `datagen/lang/ModEnUsLangProvider.java` / `ModZhCnLangProvider.java`
- 对于物品名称，用 `Rarity.UNCOMMON` / `.rarity()` 控制稀有度颜色，不要在翻译值中使用格式化代码直接设置颜色来替代 `.rarity()`

---

## 步骤 10：材质

- 源材质中文拼音 → 重命名为注册 ID 对应英文名
- 复制到 `assets/pasterdream/textures/block/` 或 `textures/item/`
- 实体纹理：非 GeckoLib → `textures/entities/`，GeckoLib → `textures/entity/`
- 如为动画纹理，同步复制同名 `.mcmeta` 文件

---

## 步骤 11：创造模式物品栏

- 在 `init/ModCreativeModeTabs.java` 中添加
- 工具/护甲/武器 → `PASTERDREAM_GEAR_TAB`（装备）
- 方块 → `PASTERDREAM_BLOCKS_TAB`
- 材料 → `PASTERDREAM_ITEMS_TAB`
- 食物 → `PASTERDREAM_FOOD_TAB`
- 刷怪蛋 → `PASTERDREAM_DREAM_DEBUG_TAB`（调试物品栏）

---

## 步骤 12：配方

### 工作台配方

- 在 `NOT_MODIFY/old-recipes/` 中按旧 ID 搜索
- 写入 `datagen/common/ModRecipesProvider.java`
- 可复用 `datagen/util/RecipeHelpers.java`：
  - `plankFamilyRecipes` — 原木→木板+全套建材
  - `storageCompress` — 9×材料→1×方块
  - `storageDecompress` — 1×方块→9×材料

### 锻造台 / 升级配方保留附魔

- 锻造台配方天然保留 NBT，无需特殊处理
- 工作台升级配方（如钻石→钛金）用 `saveNbtPreserving()` / `saveNbtPreservingShaped()` 包装，保留附魔
- 序列化器在 `ModRecipes.java` 中注册，recipe 类在 `recipe/` 包下

### JEI 配方兼容

- JEI 兼容代码放 `compat/jei/`，新建对应配方目录
- 在 `ModJEIPlugin.java` 中注册配方类别

---

## 步骤 13：数据生成运行

- `runData` 生成所有数据文件
- 检查 `src/generated/` 下的 JSON

---

## 步骤 14：客户端测试

- `runClient` 验证：模型、纹理、动画、发光、中英文名、配方、战利品

---

## 重要原则

- 能用原版类的不要写客制化类（含 `FallingBlock`、`RotatedPillarBlock`、`AmethystClusterBlock` 等）
- 客制化方块放 `world/block/`，客制化物品放 `world/item/`
- 实体类放 `world/entity/`，模型和渲染器放 `client/model/` + `client/renderer/`
- 渲染层统一在 `ClientSetRenderLayer` 中管理
- 标签（挖掘工具、材质、功能）必须补全
- 配方检查 old-recipes 目录
- ID、材质语义化命名，禁止中文拼音
- 不确定的重命名先问用户
- 翻译值不用格式化代码，用 Rarity 控制颜色
- 技能系统遵循：按键绑定 → 网络包 → 技能核心 → 特效处理 → 效果标记 的链路

---

## 相关文件路径速查

| 用途 | 路径 |
|------|------|
| 源模组源码 | `NOT_MODIFY/reference/net/pasterdream/`（只读） |
| 源模组资源 | `NOT_MODIFY/reference-resources/assets/pasterdream/`（只读） |
| 旧配方 | `NOT_MODIFY/old-recipes/` |
| ID 映射 | `document/design/ID映射表.md` |
| 方块注册 | `init/ModBlocks.java` |
| 物品注册 | `init/ModItems.java` |
| 实体注册 | `init/ModEntities.java` |
| 工具等级 | `world/item/ModToolTiers.java` |
| 护甲材料 | `world/item/ModArmorMaterials.java` |
| 创造模式标签 | `init/ModCreativeModeTabs.java` |
| 客制化方块 | `world/block/` |
| 客制化物品 | `world/item/` |
| 客制化饰品 | `world/item/curio/` |
| 饰品被动处理 | `world/item/curio/CurioPassiveHandler.java` |
| 效果注册 | `init/ModEffects.java` |
| 效果实现 | `world/effect/` |
| 效果纹理 | `src/main/resources/assets/pasterdream/textures/mob_effect/` |
| 玩家事件 | `event/PlayerEvents.java` |
| 饰品槽位标签 | `src/main/resources/data/curios/tags/items/`（手动维护） |
| 品质系统 | `world/item/ModRarities.java` |
| 实体实现 | `world/entity/` |
| 实体模型 | `client/model/`（GeckoLib `GeoModel`） |
| 实体渲染器 | `client/renderer/`（GeckoLib `GeoEntityRenderer` 或标准渲染器） |
| 实体渲染器注册 | `init/ModEntityRenderer.java` |
| 实体战利品表 | `datagen/common/ModEntityLootTablesProvider.java` |
| 实体生成修饰符 | `worldgen/biome/ModBiomeModifierProvider.java`（`addSpawns` 方法） |
| 技能系统 | `world/skill/` |
| 技能网络包 | `network/skill/` |
| 按键绑定 | `init/ModKeyMappings.java` |
| 自定义属性 | `init/ModAttributes.java` |
| 能力系统 | `capability/`（MeltDreamEnergy + SAN） |
| 数据生成器 | `datagen/common/` |
| 实体标签 | `datagen/common/ModEntityTypeTagsProvider.java`（走 datagen，不手动 JSON） |
| 语言文件 | `datagen/lang/` |
| 配方工具 | `datagen/util/RecipeHelpers.java` |
| 战利品表工具 | `datagen/util/LootHelpers.java` |
| 渲染层 | `client/ClientSetRenderLayer.java` |
| 材质目录 | `src/main/resources/assets/pasterdream/textures/` |
| Geo 模型 | `src/main/resources/assets/pasterdream/geo/`（.geo.json） |
| 动画文件 | `src/main/resources/assets/pasterdream/animations/`（.animation.json） |
| 自定义模型 | `src/main/resources/assets/pasterdream/models/custom/` |
| 显示设置 | `src/main/resources/assets/pasterdream/models/displaysettings/` |
| 粒子注册 | `init/ModParticleTypes.java` + `client/particle/` |
| 粒子纹理 | `src/main/resources/assets/pasterdream/particles/`（手动维护） |
| 音效注册 | `init/ModSounds.java` |
| 音效定义 | `src/main/resources/assets/pasterdream/sounds.json`（手动维护） |
| 音效文件 | `src/main/resources/assets/pasterdream/sounds/`（.ogg） |
| Mixin 注入 | `mixin/` |
| Mixin 配置 | `src/main/resources/pasterdream.mixins.json` |
| JEI 兼容 | `compat/jei/` |
| 模组配置 | `Config.java` |

---

## 步骤 15：进度搬运

### 15.1 定位源进度

- 在 `NOT_MODIFY/reference-resources/data/pasterdream/advancements/` 中查找进度 JSON
- 在 `NOT_MODIFY/reference-resources/assets/pasterdream/lang/` 中查找标题/描述翻译 key
- 原作进度分为两类：**隐藏进度**（`achievement_hide_X`，发笔记用，无 display）和**可见进度**（`achievement_a/b/c/d_X`，有 display、frame、parent 树）

### 15.2 进度定义

所有进度统一在 `datagen/common/ModAdvancementProvider.java` 中定义。

**Frame 对照：**
| 原作 frame | `FrameType` |
|-----------|-------------|
| `task` | `FrameType.TASK` |
| `goal` | `FrameType.GOAL` |
| `challenge` | `FrameType.CHALLENGE` |

**Criterion 选择：**

| 触发条件 | 使用 |
|---------|------|
| 获得物品 | `InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.XXX.get())` |
| 饮用/食用物品 | `ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(...).build())` |
| 读取笔记 | `ReadDreamNoteTrigger.TriggerInstance.forContent("noteContentKey")` |
| 自定义事件 | 见 `advancement/criterion/` 下的自定义 Trigger |

### 15.3 笔记分发模式

**模式 A：获得物品 → 得进度 → 发笔记（`AdvancementEvent` 驱动）**

- 适用：原作隐藏进度由 `inventory_changed` 触发且有 loot reward 发笔记
- 在 `PlayerEvents.java` 的 `ADVANCEMENT_NOTE_CONTENT` 映射中注册：`进度ID → contentKey`
- `onAdvancementEarned` 自动查表发放笔记 + 消息
- 消息 key 由进度 ID 动态拼接：`message.pasterdream.<path用.替换/>.found_note`
- 示例：`pure_and_flawless`（whiteCorolla）、`dream_fertilizer`（dreamFertilizer）

**模式 B：获得物品 → 得进度（仅 XP，无笔记）**

- 适用：原作可见进度无 loot reward
- 只需在 `ModAdvancementProvider` 中定义进度 + 语言条目
- 示例：`dyedream_dust`、`melt_dream_liquid_bucket`、`melt_dream_crystal_fragment`

**模式 C：交互 → 发笔记 → 读笔记 → 得进度（`ReadDreamNoteTrigger` 驱动）**

- 适用：原作进度由 `impossible` 触发，实际通过读取笔记获得
- 在方块/物品的交互代码中发放笔记（检查玩家是否已有进度 + 背包是否已有笔记）
- 进度 criterion 使用 `ReadDreamNoteTrigger.forContent("contentKey")`
- 示例：`dyedream_crack`（睡觉触发）、`dyedream_world`（进入维度触发）、`dream_accumulator`（打开蓄梦池触发）

**模式 D：饮用/食用 → 得进度**

- 适用：原作由 `consume_item` 触发
- **重要**：若物品使用自定义 `finishUsingItem()`（如 `PasterDreamDrinkItem`），需手动调用 `CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack)` 和 `player.awardStat(Stats.ITEM_USED.get(this))`
- 示例：`glass_jar_of_dream_juice`

### 15.4 睡眠笔记分发门禁

`dyedreamCreak` 笔记通过睡眠事件分发，但需先满足门禁：
- 玩家必须先**身体接触**染梦裂隙方块（`entityInside()` → 获得 `first_contact_dyedream_crack`），睡觉时才触发笔记计时器
- **不可**让首次睡觉直接触发笔记

### 15.5 语言条目

在 `datagen/lang/ModZhCnLangProvider.java` 和 `ModEnUsLangProvider.java` 中添加：
- `advancements.pasterdream.<category>.<name>.title`
- `advancements.pasterdream.<category>.<name>.description`
- 若有笔记：`message.pasterdream.<path>.found_note`

### 15.6 相关文件

| 用途 | 路径 |
|------|------|
| 进度定义 | `datagen/common/ModAdvancementProvider.java` |
| 笔记分发表 | `event/PlayerEvents.java` → `ADVANCEMENT_NOTE_CONTENT` |
| 笔记分发事件 | `event/PlayerEvents.java` → `onAdvancementEarned()` |
| 自定义触发器注册 | `init/ModCriteriaTriggers.java` |
| 自定义触发器实现 | `advancement/criterion/` |
| 语言文件 | `datagen/lang/ModZhCnLangProvider.java` / `ModEnUsLangProvider.java` |
