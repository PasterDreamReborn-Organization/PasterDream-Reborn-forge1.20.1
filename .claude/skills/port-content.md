---
name: port-content
description: 从源模组搬运方块、物品、工具、护甲到重置模组。覆盖全流程：分析→映射→注册→渲染→数据生成→材质→语言→物品栏→配方→测试。
---

# 搬运流程

严格按以下步骤执行，不要跳过。

---

## 步骤 1：明确搬运目标

- 确认用户要搬运的具体方块/物品清单
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

---

## 步骤 3：登记 ID 映射

- 写入 `document/design/ID映射表.md`
- 运行 `cd document/design && python generate_id_mapping.py` 刷新 `mapping.json`

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
| `ModBlockLootTablesProvider.java` | 战利品表 |
| `ModBlockTagsProvider.java` | **必须**补全标签（挖掘工具 + 材质类型 + 功能标签） |
| `ModItemModelsProvider.java` | 物品模型（工具用 `handheldItem()`，普通物品用 `basicItem()`） |
| `ModItemTagsProvider.java` | 物品标签（用 `copy()` 从方块标签同步，工具标签 `ItemTags.SWORDS` 等） |

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
- 对于物品名称，用 `Rarity.UNCOMMON` / `.rarity()` 控制稀有度颜色,不要在翻译值中使用格式化代码直接设置颜色来替代.rarity()

---

## 步骤 10：材质

- 源材质中文拼音 → 重命名为注册 ID 对应英文名
- 复制到 `assets/pasterdream/textures/block/` 或 `textures/item/`
- 如为动画纹理，同步复制同名 `.mcmeta` 文件

---

## 步骤 11：创造模式物品栏

- 在 `init/ModCreativeModeTabs.java` 中添加
- 工具/护甲/武器 → `PASTERDREAM_GEAR_TAB`（装备）
- 方块 → `PASTERDREAM_BLOCKS_TAB`
- 材料 → `PASTERDREAM_ITEMS_TAB`
- 食物 → `PASTERDREAM_FOOD_TAB`

---

## 步骤 12：配方

### 工作台配方

- 在 `document/reference/old-recipes/` 中按旧 ID 搜索
- 写入 `datagen/common/ModRecipesProvider.java`
- 可复用 `datagen/util/RecipeHelpers.java`：
  - `plankFamilyRecipes` — 原木→木板+全套建材
  - `storageCompress` — 9×材料→1×方块
  - `storageDecompress` — 1×方块→9×材料

### 锻造台 / 升级配方保留附魔

- 锻造台配方天然保留 NBT，无需特殊处理
- 工作台升级配方（如钻石→钛金）用 `saveNbtPreserving()` / `saveNbtPreservingShaped()` 包装，保留附魔
- 序列化器在 `ModRecipes.java` 中注册，recipe 类在 `recipe/` 包下

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
- 渲染层统一在 `ClientSetRenderLayer` 中管理
- 标签（挖掘工具、材质、功能）必须补全
- 配方检查 old-recipes 目录
- ID、材质语义化命名，禁止中文拼音
- 不确定的重命名先问用户
- 翻译值不用格式化代码，用 Rarity 控制颜色

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
| 工具等级 | `world/item/ModToolTiers.java` |
| 护甲材料 | `world/item/ModArmorMaterials.java` |
| 创造模式标签 | `init/ModCreativeModeTabs.java` |
| 客制化方块 | `world/block/` |
| 客制化物品 | `world/item/` |
| 数据生成器 | `datagen/common/` |
| 语言文件 | `datagen/lang/` |
| 配方工具 | `datagen/util/RecipeHelpers.java` |
| 渲染层 | `client/ClientSetRenderLayer.java` |
| 材质目录 | `src/main/resources/assets/pasterdream/textures/` |
| 粒子注册 | `init/ModParticleTypes.java` + `client/particle/` |
| 音效注册 | `init/ModSounds.java` + `sounds.json` |
