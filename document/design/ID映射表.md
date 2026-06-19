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

| 旧 ID                             | 新 ID                             | 变更说明                                      | 客制化类处理                                              |
|----------------------------------|----------------------------------|-------------------------------------------|-----------------------------------------------------|
| `dyedream_dirt`                  | `dyedream_dirt`                  | 不改                                        | 无需客制化类，直接用 `Block`                                  |
| `dyedream_grass`                 | `dyedream_grass_block`           | 重命名，加 `_block` 后缀                         | 客制化类 `DyedreamGrassBlock`，仅覆写 `canSustainPlant`     |
| `dyedream_log`                   | `dyedream_log`                   | 不改                                        | 无需客制化类，直接用 `RotatedPillarBlock`                     |
| `dyedream_wood`                  | `dyedream_wood`                  | 不改                                        | 无需客制化类，直接用 `RotatedPillarBlock`                     |
| `dyedream_leaves`                | `dyedream_leaves`                | 不改                                        | 无需客制化类，直接用 `LeavesBlock`                            |
| `dyedream_worldtree_leaves`      | `dyedream_worldtree_leaves`      | 不改，材质从拼音 `ran_meng_shi_jie_shu_xie_` 重命名  | 无需客制化类，直接用 `LeavesBlock`                            |
| `amber_candy_ore`                | `amber_candy_ore`                | 不改                                        | 无需客制化类，直接用 `Block`                                  |
| `dyedreamdust_ore`               | `dyedream_dust_ore`              | 重命名，`dyedreamdust` → `dyedream_dust`，补下划线 | 无需客制化类，直接用 `Block`                                  |
| `dyedreamquartz_ore`             | `dyedream_quartz_ore`            | 重命名，补下划线                                  | 无需客制化类，直接用 `Block`                                  |
| `dyedreamquartz_block`           | `dyedream_quartz_block`          | 重命名，补下划线                                  | 无需客制化类，直接用 `Block`                                  |
| `smooth_dyedreamquartz_block`    | `smooth_dyedream_quartz_block`   | 重命名，补下划线                                  | 无需客制化类，直接用 `Block`                                  |
| `bricks_dyedreamquartz_block`    | `bricks_dyedream_quartz_block`   | 重命名，补下划线                                  | 无需客制化类，直接用 `Block`                                  |
| `pillar_dyedreamquartz_block`    | `pillar_dyedream_quartz_block`   | 重命名，补下划线                                  | 无需客制化类，直接用 `RotatedPillarBlock`，旧版用自定义 FACING 代替轴旋转 |
| `chiseled_dyedreamquartz_block`  | `chiseled_dyedream_quartz_block` | 重命名，补下划线                                  | 无需客制化类，直接用 `Block`                                  |
| `dyedreamquartz_block_stairs`    | `dyedream_quartz_block_stairs`   | 重命名，补下划线                                  | 无需客制化类，直接用 `StairBlock`                             |
| `dyedreamquartz_block_slab`      | `dyedream_quartz_block_slab`     | 重命名，补下划线                                  | 无需客制化类，直接用 `SlabBlock`                              |
| `dyedreamquartz_block_wall`      | `dyedream_quartz_block_wall`     | 重命名，补下划线                                  | 无需客制化类，直接用 `WallBlock`                              |
| `dyedream_sapling`               | `dyedream_sapling`               | 不改                                        | 无需客制化类，直接用 `SaplingBlock`，移除不必要的 BlockEntity        |
| `dyedream_planks`                | `dyedream_planks`                | 不改                                        | 无需客制化类，直接用 `Block`                                  |
| `dyedream_planks_stairs`         | `dyedream_planks_stairs`         | 不改                                        | 无需客制化类，直接用 `StairBlock`                             |
| `dyedream_planks_slab`           | `dyedream_planks_slab`           | 不改                                        | 无需客制化类，直接用 `SlabBlock`                              |
| `dyedream_planks_fence`          | `dyedream_planks_fence`          | 不改                                        | 无需客制化类，直接用 `FenceBlock`                             |
| `dyedream_planks_fencegate`      | `dyedream_planks_fencegate`      | 不改                                        | 无需客制化类，直接用 `FenceGateBlock`                         |
| `dyedream_planks_pane`           | `dyedream_planks_pane`           | 不改                                        | 无需客制化类，直接用 `IronBarsBlock`                          |
| `dyedream_planks_door`           | `dyedream_planks_door`           | 不改                                        | 无需客制化类，直接用 `DoorBlock`                              |
| `dyedream_planks_trapdoor`       | `dyedream_planks_trapdoor`       | 不改                                        | 无需客制化类，直接用 `TrapDoorBlock`                          |
| `dyedream_planks_pressure_plate` | `dyedream_planks_pressure_plate` | 不改                                        | 无需客制化类，直接用 `PressurePlateBlock`                     |
| `dyedream_planks_button`         | `dyedream_planks_button`         | 不改                                        | 无需客制化类，直接用 `ButtonBlock`                            |
| `pinkslime_block`                | `pink_slime_block`               | 重命名，补下划线；受重力影响                            | 无需客制化类，直接用 `FallingBlock`                           |
| `pinkagaric_0`                   | `pink_mushroom_block`            | 重命名，按原版风格：菌盖                              | 无需客制化类，直接用 `Block`                                  |
| `pinkagaric_1`                   | `pink_mushroom_stem`             | 重命名，按原版风格：菌柄（有朝向）                         | 无需客制化类，直接用 `RotatedPillarBlock`                     |
| `pinkagaric_2`                   | `pink_mushroom_pores`            | 重命名，按原版风格：6 面菌孔                           | 无需客制化类，直接用 `Block`                                  |
| `pinkagaric_3`                   | `pink_shroomlight`               | 重命名，按原版风格：菌光体                             | 无需客制化类，直接用 `Block`，发光等级 15，cutout 渲染                |
| `flower_8`                       | `pink_mushroom`                  | 重命名，按原版风格：粉顶菇（小型花，缓速效果）                   | 无需客制化类，直接用 `FlowerBlock`                            |
| `flower_10`                      | `tall_pink_mushroom`             | 重命名，按原版风格：高粉顶菇（2 格高）                      | 无需客制化类，直接用 `DoublePlantBlock`                       |
| `dyedream_sand`                  | `dyedream_sand`                  | 不改，受重力影响                                  | 无需客制化类，直接用 `FallingBlock`                           |
| `dyedream_glass`                 | `dyedream_glass`                 | 不改                                | 无需客制化类，直接用 `GlassBlock`                             |
| `dyedream_glasspane`             | `dyedream_glass_pane`            | 重命名，补下划线                       | 无需客制化类，直接用 `IronBarsBlock`                          |
| `carve_dyedream_glass`           | `carve_dyedream_glass`           | 不改                                        | 无需客制化类，直接用 `GlassBlock`                             |
| `carve_dyedream_glasspane`       | `carve_dyedream_glass_pane`      | 重命名，补下划线                                  | 无需客制化类，直接用 `IronBarsBlock`                          |
| `gold_carve_dyedream_glass`      | `gold_carve_dyedream_glass`      | 不改                                        | 无需客制化类，直接用 `GlassBlock`                             |
| `gold_carve_dyedream_glasspane`  | `gold_carve_dyedream_glass_pane` | 重命名，补下划线                                  | 无需客制化类，直接用 `IronBarsBlock`                          |
| `dyedream_bud_2`                 | `small_dyedream_bud`             | 重命名，按原版紫水晶芽风格                             | 无需客制化类，直接用 `AmethystClusterBlock`                   |
| `dyedream_bud_1`                 | `medium_dyedream_bud`            | 重命名                                       | 无需客制化类，直接用 `AmethystClusterBlock`                   |
| `dyedream_bud_0`                 | `large_dyedream_bud`             | 重命名                                       | 无需客制化类，直接用 `AmethystClusterBlock`                   |
| `dyedream_ice`                   | `dyedream_ice`                   | 不改，不融化                                    | 无需客制化类，直接用 `Block`                                  |
| `dyedream_packed_ice`            | `dyedream_packed_ice`            | 不改，不融化                                    | 无需客制化类，直接用 `Block`                                  |
| `dyedream_lartern`               | `dyedream_lartern`               | 不改，发光等级 14                                | 无需客制化类，直接用 `Block`                                  |
| `dyedream_bud_block`             | `dyedream_bud_block`             | 不改，透明方块                                   | 无需客制化类，直接用 `Block`                                  |
| `dyedream_bud_stairs`            | `dyedream_bud_stairs`            | 不改                                        | 无需客制化类，直接用 `StairBlock`                             |
| `dyedream_bud_slab`              | `dyedream_bud_slab`              | 不改                                        | 无需客制化类，直接用 `SlabBlock`                              |
| `dyedream_bud_wall`              | `dyedream_bud_wall`              | 不改                                        | 无需客制化类，直接用 `WallBlock`                              |
| `claypan_0`                      | `claypan`                        | 重命名，删除_0后缀                                | 客制化类                                                |
| `claypan_1`                      | 删除                               | -                                         | -                                                   |
| `claypan_2`                      | 删除                               | -                                         | -                                                   |
| `dream_cauldron`                 | `dream_cauldron`                 | 不改                                        | 客制化类 `DreamCauldronBlock`，含 BlockEntity、Menu、Screen |


---

## 物品映射

| 旧 ID                      | 新 ID                                        | 变更说明                                                      | 客制化类处理                               |
|---------------------------|---------------------------------------------|-----------------------------------------------------------|--------------------------------------|
| `amber_candy`             | `amber_candy`                               | 不改，食物属性：回复 0.5 饱食度 + 3s 生命恢复 I                            | 无需客制化类，直接用 `Item` + `FoodProperties` |
| `dyedream_dust`           | `dyedream_dust`                             | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dyedream_dust_piece`     | `dyedream_dust_piece`                       | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `pink_slimeball`          | `pink_slimeball`                            | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `mortar`                  | `mortar`                                    | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dyedream_dye`            | `dyedream_dye`                              | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dyedream_bud_nugget`     | `dyedream_bud_nugget`                       | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `raw_titanium`            | `raw_titanium`                              | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `titanium_ingot`          | `titanium_ingot`                            | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `titanium_nugget`         | `titanium_nugget`                           | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dyedream_base`           | `raw_dyedream_alloy_ingot`                  | 重命名，`dyedream_base` → `raw_dyedream_alloy_ingot`（染梦合金锭粗胚） | 无需客制化类，直接用 `Item`                    |
| `dyedream_ingot`          | `dyedream_alloy_ingot`                      | 重命名，`dyedream_ingot` → `dyedream_alloy_ingot`（染梦合金锭）      | 无需客制化类，直接用 `Item`                    |
| `dyedream_nugget`         | `dyedream_alloy_nugget`                     | 重命名，补充 `alloy_` 前缀与系列一致                                   | 无需客制化类，直接用 `Item`                    |
| `dyedreamquartz`          | `dyedream_quartz`                           | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `moltengold_ingot`        | `molten_gold_ingot`                         | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `moltengold_nugget`       | `molten_gold_nugget`                        | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `raw_moltengold`          | `raw_molten_gold`                           | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `meltdream_liquid_bucket` | `meltdream_liquid_bucket`                   | 不改                                                        | 无需客制化类，直接用 `BucketItem`              |
| `shadow_liquid_bucket`    | `shadow_liquid_bucket`                      | 不改                                                        | 无需客制化类，直接用 `BucketItem`              |
| `glassjar`                | `glass_jar`                                 | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `water_glassjar`          | `glass_jar_of_water`                        | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `milk_glassjar`           | `glass_jar_of_milk`                         | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `yeast`                   | `glass_jar_of_yeast`                        | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `guiding_drug`            | `glass_jar_of_guiding_drug`                 | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `wind_plant_extract`      | `glass_jar_of_wind_plant_extract`           | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `dreamjuice`              | `glass_jar_of_dream_juice`                  | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `goldenrod_tea`           | `glass_jar_of_goldenrod_tea`                | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `dyedream_perfume`        | `glass_jar_of_dyedream_perfume`             | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `glass_cup`               | `glass_cup`                                 | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `apple_juice`             | `glass_cup_of_apple_juice`                  | 重命名，更改命名格式为 `glass_cup_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `dyedream_flower_tea_0`   | `glass_cup_of_uncooked_dyedream_flower_tea` | 重命名，更改命名格式为 `glass_cup_of_xxx`，加入`uncooked_` 前缀           | 无需客制化类，直接用 `Item`                    |
| `dyedream_flower_tea`     | `glass_cup_of_cooked_dyedream_flower_tea`   | 重命名，更改命名格式为 `glass_cup_of_xxx`，加入`cooked_` 前缀             | 无需客制化类，直接用 `Item`                    |
| `dyedream_juice`          | `glass_cup_of_dyedream_juice`               | 重命名，更改命名格式为 `glass_cup_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `honey_juice`             | `glass_cup_of_honey_juice`                  | 重命名，更改命名格式为 `glass_cup_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `watermelon_juice`        | `glass_cup_of_watermelon_juice`             | 重命名，更改命名格式为 `glass_cup_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `ryeseed`                 | `rye_seed`                                  | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `flour`                   | `flour`                                     | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dough`                   | `dough`                                     | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `eggdough`                | `dough_with_egg`                            | 重命名，`eggdough` → `dough_with_egg`                         | 无需客制化类，直接用 `Item`                    |
| `coarse_salt`             | `coarse_salt`                               | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `salt`                    | `salt`                                      | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `pinkegg`                 | `pink_egg`                                  | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `chocolate`               | `chocolate`                                 | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dyedream_fruit`          | `dyedream_fruit`                            | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `cake_base`               | `cake_base`                                 | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `buncake_0`               | `cream_bun_cake`                            | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `buncake_1`               | `berry_bun_cake`                            | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `buncake_2`               | `tuber_bun_cake`                            | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `buncake_3`               | `watermelon_bun_cake`                       | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `buncake_4`               | `pumpkin_bun_cake`                          | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `buncake_5`               | `glow_berry_bun_cake`                       | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `buncake_6`               | `dyedream_fruit_bun_cake`                   | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `chocolate_matcha_cake`   | `chocolate_matcha_cake`                     | 不改                                                        | 无需客制化类，直接用 `Item`                    |
---

## 流体映射

| 旧 ID               | 新 ID                | 变更说明 | 客制化类处理            |
|--------------------|---------------------|----|-------------------|
| `meltdream_liquid` | `melt_dream_liquid` | 不改 | 合并变体，使用统一Fluid的注册 |
| `shadow_liquid`    | `shadow_liquid`     | 不改 | 合并变体，使用统一Fluid的注册 |


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
