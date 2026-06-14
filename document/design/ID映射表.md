# ID 映射表

> 源模组旧 ID → 重置模组新 ID 对照表  
> 每次搬运方块/物品时**必须**登记，无论是否修改 ID

---

## 使用说明

- 搬运一个方块/物品时，先在此表中查找是否已登记
- 未登记的，按实际搬运情况填写
- 变更说明须简洁明了：`不改` / `重命名` / `合并变体` / `拆分` 等

---

## 方块映射

| 旧 ID | 新 ID | 变更说明 | 客制化类处理 |
|-------|-------|---------|------------|
| `dyedream_dirt` | `dyedream_dirt` | 不改 | 无需客制化类，直接用 `Block` |
| `dyedream_grass` | `dyedream_grass_block` | 重命名，加 `_block` 后缀 | 客制化类 `DyedreamGrassBlock`，仅覆写 `canSustainPlant` |
| `dyedream_log` | `dyedream_log` | 不改 | 无需客制化类，直接用 `RotatedPillarBlock` |
| `dyedream_wood` | `dyedream_wood` | 不改 | 无需客制化类，直接用 `RotatedPillarBlock` |
| `dyedream_leaves` | `dyedream_leaves` | 不改 | 无需客制化类，直接用 `LeavesBlock` |
| `dyedream_worldtree_leaves` | `dyedream_worldtree_leaves` | 不改，材质从拼音 `ran_meng_shi_jie_shu_xie_` 重命名 | 无需客制化类，直接用 `LeavesBlock` |
| `amber_candy_ore` | `amber_candy_ore` | 不改 | 无需客制化类，直接用 `Block` |
| `dyedreamdust_ore` | `dyedream_dust_ore` | 重命名，`dyedreamdust` → `dyedream_dust`，补下划线 | 无需客制化类，直接用 `Block` |
| `dyedreamquartz_ore` | `dyedream_quartz_ore` | 重命名，补下划线 | 无需客制化类，直接用 `Block` |
| `dyedreamquartz_block` | `dyedream_quartz_block` | 重命名，补下划线 | 无需客制化类，直接用 `Block` |
| `smooth_dyedreamquartz_block` | `smooth_dyedream_quartz_block` | 重命名，补下划线 | 无需客制化类，直接用 `Block` |
| `bricks_dyedreamquartz_block` | `bricks_dyedream_quartz_block` | 重命名，补下划线 | 无需客制化类，直接用 `Block` |
| `pillar_dyedreamquartz_block` | `pillar_dyedream_quartz_block` | 重命名，补下划线 | 无需客制化类，直接用 `RotatedPillarBlock`，旧版用自定义 FACING 代替轴旋转 |
| `chiseled_dyedreamquartz_block` | `chiseled_dyedream_quartz_block` | 重命名，补下划线 | 无需客制化类，直接用 `Block` |
| `dyedreamquartz_block_stairs` | `dyedream_quartz_block_stairs` | 重命名，补下划线 | 无需客制化类，直接用 `StairBlock` |
| `dyedreamquartz_block_slab` | `dyedream_quartz_block_slab` | 重命名，补下划线 | 无需客制化类，直接用 `SlabBlock` |
| `dyedreamquartz_block_wall` | `dyedream_quartz_block_wall` | 重命名，补下划线 | 无需客制化类，直接用 `WallBlock` |
| `dyedream_sapling` | `dyedream_sapling` | 不改 | 无需客制化类，直接用 `SaplingBlock`，移除不必要的 BlockEntity |
| `dyedream_planks` | `dyedream_planks` | 不改 | 无需客制化类，直接用 `Block` |
| `dyedream_planks_stairs` | `dyedream_planks_stairs` | 不改 | 无需客制化类，直接用 `StairBlock` |
| `dyedream_planks_slab` | `dyedream_planks_slab` | 不改 | 无需客制化类，直接用 `SlabBlock` |
| `dyedream_planks_fence` | `dyedream_planks_fence` | 不改 | 无需客制化类，直接用 `FenceBlock` |
| `dyedream_planks_fencegate` | `dyedream_planks_fencegate` | 不改 | 无需客制化类，直接用 `FenceGateBlock` |
| `dyedream_planks_pane` | `dyedream_planks_pane` | 不改 | 无需客制化类，直接用 `IronBarsBlock` |
| `dyedream_planks_door` | `dyedream_planks_door` | 不改 | 无需客制化类，直接用 `DoorBlock` |
| `dyedream_planks_trapdoor` | `dyedream_planks_trapdoor` | 不改 | 无需客制化类，直接用 `TrapDoorBlock` |
| `dyedream_planks_pressure_plate` | `dyedream_planks_pressure_plate` | 不改 | 无需客制化类，直接用 `PressurePlateBlock` |
| `dyedream_planks_button` | `dyedream_planks_button` | 不改 | 无需客制化类，直接用 `ButtonBlock` |
| `pinkslime_block` | `pink_slime_block` | 重命名，补下划线；受重力影响 | 无需客制化类，直接用 `FallingBlock` |
| `pinkagaric_0` | `pink_mushroom_block` | 重命名，按原版风格：菌盖 | 无需客制化类，直接用 `Block` |
| `pinkagaric_1` | `pink_mushroom_stem` | 重命名，按原版风格：菌柄（有朝向） | 无需客制化类，直接用 `RotatedPillarBlock` |
| `pinkagaric_2` | `pink_mushroom_pores` | 重命名，按原版风格：6 面菌孔 | 无需客制化类，直接用 `Block` |
| `pinkagaric_3` | `pink_shroomlight` | 重命名，按原版风格：菌光体 | 无需客制化类，直接用 `Block`，发光等级 15，cutout 渲染 |
| `flower_8` | `pink_mushroom` | 重命名，按原版风格：粉顶菇（小型花，缓速效果） | 无需客制化类，直接用 `FlowerBlock` |
| `flower_10` | `tall_pink_mushroom` | 重命名，按原版风格：高粉顶菇（2 格高） | 无需客制化类，直接用 `DoublePlantBlock` |
| `dyedream_sand` | `dyedream_sand` | 不改，受重力影响 | 无需客制化类，直接用 `FallingBlock` |
| `dyedream_glass` | `dyedream_glass` | 不改，发光等级 12，仅精准采集可掉落 | 无需客制化类，直接用 `Block` |
| `dyedream_glasspane` | `dyedream_glasspane` | 不改，发光等级 12，仅精准采集可掉落 | 无需客制化类，直接用 `IronBarsBlock` |
| `carve_dyedream_glass` | `carve_dyedream_glass` | 不改 | 无需客制化类，直接用 `Block` |
| `carve_dyedream_glasspane` | `carve_dyedream_glasspane` | 不改 | 无需客制化类，直接用 `IronBarsBlock` |
| `gold_carve_dyedream_glass` | `gold_carve_dyedream_glass` | 不改 | 无需客制化类，直接用 `Block` |
| `gold_carve_dyedream_glasspane` | `gold_carve_dyedream_glasspane` | 不改 | 无需客制化类，直接用 `IronBarsBlock` |
| `dyedream_bud_2` | `small_dyedream_bud` | 重命名，按原版紫水晶芽风格 | 无需客制化类，直接用 `AmethystClusterBlock` |
| `dyedream_bud_1` | `medium_dyedream_bud` | 重命名 | 无需客制化类，直接用 `AmethystClusterBlock` |
| `dyedream_bud_0` | `large_dyedream_bud` | 重命名 | 无需客制化类，直接用 `AmethystClusterBlock` |
| `dyedream_ice` | `dyedream_ice` | 不改，不融化 | 无需客制化类，直接用 `Block` |
| `dyedream_packed_ice` | `dyedream_packed_ice` | 不改，不融化 | 无需客制化类，直接用 `Block` |
| `dyedream_lartern` | `dyedream_lartern` | 不改，发光等级 14 | 无需客制化类，直接用 `Block` |
| `dyedream_bud_block` | `dyedream_bud_block` | 不改，透明方块 | 无需客制化类，直接用 `Block` |
| `dyedream_bud_stairs` | `dyedream_bud_stairs` | 不改 | 无需客制化类，直接用 `StairBlock` |
| `dyedream_bud_slab` | `dyedream_bud_slab` | 不改 | 无需客制化类，直接用 `SlabBlock` |
| `dyedream_bud_wall` | `dyedream_bud_wall` | 不改 | 无需客制化类，直接用 `WallBlock` |

---

## 物品映射

| 旧 ID | 新 ID | 变更说明 | 客制化类处理 |
|-------|-------|---------|------------|
| `amber_candy` | `amber_candy` | 不改，食物属性：回复 0.5 饱食度 + 3s 生命恢复 I | 无需客制化类，直接用 `Item` + `FoodProperties` |
| `dyedream_dust` | `dyedream_dust` | 不改 | 无需客制化类，直接用 `Item` |
| `dyedream_dust_piece` | `dyedream_dust_piece` | 不改 | 无需客制化类，直接用 `Item` |
| `pink_slimeball` | `pink_slimeball` | 不改 | 无需客制化类，直接用 `Item` |
| `mortar` | `mortar` | 不改 | 无需客制化类，直接用 `Item` |
| `dyedream_dye` | `dyedream_dye` | 不改 | 无需客制化类，直接用 `Item` |
| `dyedream_bud_nugget` | `dyedream_bud_nugget` | 不改 | 无需客制化类，直接用 `Item` |
| `dyedreamquartz` | `dyedream_quartz` | 重命名，补下划线 | 无需客制化类，直接用 `Item` |

---

## 实体映射

| 旧 ID | 新 ID | 变更说明 |
|-------|-------|---------|
| | | |

---

## 配方文件名映射

| 旧配方名 | 新配方名 | 产物 |
|----------|---------|------|
| `crafting_0.json` | | |
