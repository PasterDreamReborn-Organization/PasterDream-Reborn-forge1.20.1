# ID 映射表

> 源模组旧 ID → 重置模组新 ID 对照表  
> 仅**搬运已有旧 ID** 的方块/物品时登记，新方块或原作废案无需登记

---

## 使用说明

- 搬运一个方块/物品时，先在此表中查找是否已登记
- 未登记的，按实际搬运情况填写
- 变更说明须简洁明了：`不改` / `重命名` / `合并变体` / `拆分` 等

---

## 方块映射

| 旧 ID                             | 新 ID                             | 变更说明                                          | 客制化类处理                                                                                                           |
|----------------------------------|----------------------------------|-----------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| `dyedream_dirt`                  | `dyedream_dirt`                  | 不改                                            | 无需客制化类，直接用 `Block`                                                                                               |
| `dyedream_grass`                 | `dyedream_grass_block`           | 重命名，加 `_block` 后缀                             | 客制化类 `DyedreamGrassBlock`，仅覆写 `canSustainPlant`                                                                  |
| `dyedream_log`                   | `dyedream_log`                   | 不改                                            | 无需客制化类，直接用 `RotatedPillarBlock`                                                                                  |
| `dyedream_wood`                  | `dyedream_wood`                  | 不改                                            | 无需客制化类，直接用 `RotatedPillarBlock`                                                                                  |
| `dyedream_leaves`                | `dyedream_leaves`                | 不改                                            | 无需客制化类，直接用 `LeavesBlock`                                                                                         |
| `dyedream_worldtree_leaves`      | `dyedream_worldtree_leaves`      | 不改，材质从拼音 `ran_meng_shi_jie_shu_xie_` 重命名      | 无需客制化类，直接用 `LeavesBlock`                                                                                         |
| `amber_candy_ore`                | `amber_candy_ore`                | 不改                                            | 无需客制化类，直接用 `Block`                                                                                               |
| `dyedreamdust_ore`               | `dyedream_dust_ore`              | 重命名，`dyedreamdust` → `dyedream_dust`，补下划线     | 无需客制化类，直接用 `Block`                                                                                               |
| `dyedreamquartz_ore`             | `dyedream_quartz_ore`            | 重命名，补下划线                                      | 无需客制化类，直接用 `Block`                                                                                               |
| `dyedreamquartz_block`           | `dyedream_quartz_block`          | 重命名，补下划线                                      | 无需客制化类，直接用 `Block`                                                                                               |
| `smooth_dyedreamquartz_block`    | `smooth_dyedream_quartz_block`   | 重命名，补下划线                                      | 无需客制化类，直接用 `Block`                                                                                               |
| `bricks_dyedreamquartz_block`    | `bricks_dyedream_quartz_block`   | 重命名，补下划线                                      | 无需客制化类，直接用 `Block`                                                                                               |
| `pillar_dyedreamquartz_block`    | `pillar_dyedream_quartz_block`   | 重命名，补下划线                                      | 无需客制化类，直接用 `RotatedPillarBlock`，旧版用自定义 FACING 代替轴旋转                                                              |
| `chiseled_dyedreamquartz_block`  | `chiseled_dyedream_quartz_block` | 重命名，补下划线                                      | 无需客制化类，直接用 `Block`                                                                                               |
| `dyedreamquartz_block_stairs`    | `dyedream_quartz_block_stairs`   | 重命名，补下划线                                      | 无需客制化类，直接用 `StairBlock`                                                                                          |
| `dyedreamquartz_block_slab`      | `dyedream_quartz_block_slab`     | 重命名，补下划线                                      | 无需客制化类，直接用 `SlabBlock`                                                                                           |
| `dyedreamquartz_block_wall`      | `dyedream_quartz_block_wall`     | 重命名，补下划线                                      | 无需客制化类，直接用 `WallBlock`                                                                                           |
| `dyedream_sapling`               | `dyedream_sapling`               | 不改                                            | 无需客制化类，直接用 `SaplingBlock`，移除不必要的 BlockEntity                                                                     |
| `dyedream_planks`                | `dyedream_planks`                | 不改                                            | 无需客制化类，直接用 `Block`                                                                                               |
| `dyedream_planks_stairs`         | `dyedream_stairs`                | 重命名，删除 `_planks` 字段                           | 无需客制化类，直接用 `StairBlock`                                                                                          |
| `dyedream_planks_slab`           | `dyedream_slab`                  | 重命名，删除 `_planks` 字段                           | 无需客制化类，直接用 `SlabBlock`                                                                                           |
| `dyedream_planks_fence`          | `dyedream_fence`                 | 重命名，删除 `_planks` 字段                           | 无需客制化类，直接用 `FenceBlock`                                                                                          |
| `dyedream_planks_fencegate`      | `dyedream_fence_gate`            | 重命名，删除 `_planks` 字段，加 `_` 分隔                  | 无需客制化类，直接用 `FenceGateBlock`                                                                                      |
| `dyedream_planks_pane`           | `dyedream_pane`                  | 重命名，删除 `_planks` 字段                           | 无需客制化类，直接用 `IronBarsBlock`                                                                                       |
| `dyedream_planks_door`           | `dyedream_door`                  | 重命名，删除 `_planks` 字段                           | 无需客制化类，直接用 `DoorBlock`                                                                                           |
| `dyedream_planks_trapdoor`       | `dyedream_trapdoor`              | 重命名，删除 `_planks` 字段                           | 无需客制化类，直接用 `TrapDoorBlock`                                                                                       |
| `dyedream_planks_pressure_plate` | `dyedream_pressure_plate`        | 重命名，删除 `_planks` 字段                           | 无需客制化类，直接用 `PressurePlateBlock`                                                                                  |
| `dyedream_planks_button`         | `dyedream_button`                | 重命名，删除 `_planks` 字段                           | 无需客制化类，直接用 `ButtonBlock`                                                                                         |
| `pinkslime_block`                | `pink_slime_block`               | 重命名，补下划线；受重力影响                                | 无需客制化类，直接用 `FallingBlock`                                                                                        |
| `pinkagaric_0`                   | `pink_mushroom_block`            | 重命名，按原版风格：菌盖                                  | 无需客制化类，直接用 `Block`                                                                                               |
| `pinkagaric_1`                   | `pink_mushroom_stem`             | 重命名，按原版风格：菌柄（有朝向）                             | 无需客制化类，直接用 `RotatedPillarBlock`                                                                                  |
| `pinkagaric_2`                   | `pink_mushroom_pores`            | 重命名，按原版风格：6 面菌孔                               | 无需客制化类，直接用 `Block`                                                                                               |
| `pinkagaric_3`                   | `pink_shroomlight`               | 重命名，按原版风格：菌光体                                 | 无需客制化类，直接用 `Block`，发光等级 15，cutout 渲染                                                                             |
| `flower_8`                       | `pink_mushroom`                  | 重命名，按原版风格：粉顶菇（小型花，缓速效果）                       | 无需客制化类，直接用 `FlowerBlock`                                                                                         |
| `flower_10`                      | `tall_pink_mushroom`             | 重命名，按原版风格：高粉顶菇（2 格高）                          | 无需客制化类，直接用 `DoublePlantBlock`                                                                                    |
| `dyedream_sand`                  | `dyedream_sand`                  | 不改，受重力影响                                      | 无需客制化类，直接用 `FallingBlock`                                                                                        |
| `dyedream_glass`                 | `dyedream_glass`                 | 不改                                            | 无需客制化类，直接用 `GlassBlock`                                                                                          |
| `dyedream_glasspane`             | `dyedream_glass_pane`            | 重命名，补下划线                                      | 无需客制化类，直接用 `IronBarsBlock`                                                                                       |
| `carve_dyedream_glass`           | `carve_dyedream_glass`           | 不改                                            | 无需客制化类，直接用 `GlassBlock`                                                                                          |
| `carve_dyedream_glasspane`       | `carve_dyedream_glass_pane`      | 重命名，补下划线                                      | 无需客制化类，直接用 `IronBarsBlock`                                                                                       |
| `gold_carve_dyedream_glass`      | `gold_carve_dyedream_glass`      | 不改                                            | 无需客制化类，直接用 `GlassBlock`                                                                                          |
| `gold_carve_dyedream_glasspane`  | `gold_carve_dyedream_glass_pane` | 重命名，补下划线                                      | 无需客制化类，直接用 `IronBarsBlock`                                                                                       |
| `dyedream_bud_2`                 | `small_dyedream_bud`             | 重命名，按原版紫水晶芽风格                                 | 无需客制化类，直接用 `AmethystClusterBlock`                                                                                |
| `dyedream_bud_1`                 | `medium_dyedream_bud`            | 重命名                                           | 无需客制化类，直接用 `AmethystClusterBlock`                                                                                |
| `dyedream_bud_0`                 | `large_dyedream_bud`             | 重命名                                           | 无需客制化类，直接用 `AmethystClusterBlock`                                                                                |
| `dyedream_ice`                   | `dyedream_ice`                   | 不改，不融化                                        | 无需客制化类，直接用 `Block`                                                                                               |
| `dyedream_packed_ice`            | `dyedream_packed_ice`            | 不改，不融化                                        | 无需客制化类，直接用 `Block`                                                                                               |
| `dyedream_lartern`               | `dyedream_crystal_lantern`       | 重命名，修正拼写错误（原 lartern→crystal_lantern），发光等级 14 | 无需客制化类，直接用 `Block`                                                                                               |
| `dyedream_bud_block`             | `dyedream_bud_block`             | 不改，透明方块                                       | 无需客制化类，直接用 `Block`                                                                                               |
| `dyedream_bud_stairs`            | `dyedream_bud_stairs`            | 不改                                            | 无需客制化类，直接用 `StairBlock`                                                                                          |
| `dyedream_bud_slab`              | `dyedream_bud_slab`              | 不改                                            | 无需客制化类，直接用 `SlabBlock`                                                                                           |
| `dyedream_bud_wall`              | `dyedream_bud_wall`              | 不改                                            | 无需客制化类，直接用 `WallBlock`                                                                                           |
| `claypan_0`                      | `claypan`                        | 重命名，删除_0后缀                                    | 客制化类                                                                                                             |
| `claypan_1`                      | 删除                               | -                                             | -                                                                                                                |
| `claypan_2`                      | 删除                               | -                                             | -                                                                                                                |
| `dream_cauldron`                 | `dream_cauldron`                 | 不改                                            | 客制化类 `DreamCauldronBlock`，含 BlockEntity、Menu、Screen以及Geckolib特殊渲染                                                |
| `dyedream_block`                 | `dyedream_alloy_block`           | 重命名，补充 `alloy_` 前缀与系列一致                       | 无需客制化类，直接用 `Block`                                                                                               |
| `big_bubble`                     | `big_bubble`                     | 不改                                            | 无需客制化类，直接用 `HalfTransparentBlock`；`neighborChanged` 特殊逻辑暂不实现                                                     |
| `ice_stone`                      | `ice_stone`                      | 不改                                            | 无需客制化类，直接用 `Block`                                                                                               |
| `ice_bud_0`                      | `ice_bud`                        | 重命名，删除 `_0` 后缀，仅此一种变体                         | 无需客制化类，直接用 `AmethystClusterBlock`                                                                                |
| `cloud`                          | `cloud`                          | 不改                                            | 无需客制化类，直接用 `HalfTransparentBlock`，旧版手动覆写 `skipRendering`/`propagatesSkylightDown` 等，新版 `HalfTransparentBlock` 内置 |
| `dark_cloud`                     | `dark_cloud`                     | 不改                                            | 无需客制化类，直接用 `HalfTransparentBlock`                                                                                |
| `polished_calcite`               | `polished_calcite`               | 不改                                            | 无需客制化类，直接用 `Block`                                                                                               |
| `polished_calcite_stairs`        | `polished_calcite_stairs`        | 不改                                            | 无需客制化类，直接用 `StairBlock`                                                                                          |
| `polished_calcite_slab`          | `polished_calcite_slab`          | 不改                                            | 无需客制化类，直接用 `SlabBlock`                                                                                           |
| `polished_calcite_wall`          | `polished_calcite_wall`          | 不改                                            | 无需客制化类，直接用 `WallBlock`                                                                                           |
| `calcite_tiles`                  | `calcite_tiles`                  | 不改                                            | 无需客制化类，直接用 `Block`                                                                                               |
| `calcite_tiles_stairs`           | `calcite_tiles_stairs`           | 不改                                            | 无需客制化类，直接用 `StairBlock`                                                                                          |
| `calcite_tiles_slab`             | `calcite_tiles_slab`             | 不改 (旧版翻译键填错成"楼梯"，实际是台阶)                       | 无需客制化类，直接用 `SlabBlock`                                                                                           |
| `calcite_tiles_wall`             | `calcite_tiles_wall`             | 不改                                            | 无需客制化类，直接用 `WallBlock`                                                                                           |
| `titanium_ore`                   | `titanium_ore`                   | 不改                                            | 无需客制化类，直接用 `Block`                                                                                               |
| `deepslate_titanium_ore`         | `deepslate_titanium_ore`         | 不改                                            | 无需客制化类，直接用 `Block`                                                                                               |
| `moltengold_ore`                 | `molten_gold_ore`                | 重命名，补下划线                                      | 无需客制化类，直接用 `Block`                                                                                               |
| `soul_ore`                       | `soul_ore`                       | 不改                                            | 客制化类 `SoulOreBlock`，覆写 getCollisionShape 降低碰撞箱（14px 高）                                                           |
| `raw_titanium_block`             | `raw_titanium_block`             | 不改                                            | 无需客制化类，直接用 `Block`                                                                                               |
| `salt_block`                     | `salt_block`                     | 不改                                            | 无需客制化类，直接用 `Block`                                                                                               |
| `titanium_block`                 | `titanium_block`                 | 不改                                            | 无需客制化类，直接用 `Block`                                                                                               |
| `moltengold_block`               | `molten_gold_block`              | 重命名，补下划线                                      | 无需客制化类，直接用 `Block`                                                                                               |
| `charged_amethyst_block`         | `charged_amethyst_block`         | 不改                                            | 无需客制化类，直接用 `HalfTransparentBlock`                                                                                |
| `goldenrod`                      | `goldenrod`                      | 不改                                            | 无需客制化类，直接用 `FlowerBlock`                                                                                         |
| `flower_1`                       | `ferraria_crispa`                | 重命名，魔星兰                                       | 无需客制化类，直接用 `FlowerBlock`                                                                                         |
| `flower_2`                       | `eustoma`                        | 重命名，洋桔梗                                       | 无需客制化类，直接用 `FlowerBlock`                                                                                         |
| `flower_5`                       | `malva_sinensis_cavan`           | 重命名，锦葵                                        | 无需客制化类，直接用 `FlowerBlock`                                                                                         |
| `flower_9`                       | `linht_flower`                   | 重命名，苓灯花                                       | 无需客制化类，直接用 `FlowerBlock`                                                                                         |
| `flower_11`                      | `dreaming_lotus`                 | 重命名，冶梦莲                                       | 无需客制化类，直接用 `FlowerBlock`                                                                                         |
| `flower_12`                      | `misty_dreaming_lotus`           | 重命名，迷梦冶梦莲                                     | 无需客制化类，直接用 `DoublePlantBlock`                                                                                    |
| `flower_14`                      | `singularity_fern`               | 重命名，奇异蕨                                       | 无需客制化类，直接用 `GrassBlock`                                                                                          |
| `grass_3`                        | `stem_grass`                     | 重命名，茎草                                        | 无需客制化类，直接用 `GrassBlock`                                                                                          |
| `grass_4`                        | `tall_stem_grass`                | 重命名，高茎草                                       | 无需客制化类，直接用 `DoublePlantBlock`                                                                                    |
| `grass_7`                        | `dyedream_moss`                  | 重命名，染梦苔藓                                      | 无需客制化类，直接用 `GrassBlock`                                                                                          |
| `dyedream_desk`                  | `dyedream_desk`                  | 不改                                            | 客制化类 `DyedreamDeskBlock`，含 BlockEntity、Menu、Screen                                                               |
| `shadow_desk`                    | `shadow_desk`                    | 不改                                            | 客制化类 `ShadowDeskBlock`，含 BlockEntity、Menu、Screen                                                                 |
| `picnic_basket`                  | `picnic_basket`                  | 不改                                            | 客制化类 `PicnicBusketBlock`，含 BlockEntity、Menu、Screen以及Geckolib特殊渲染                                                 |
| `shadow_chest`                   | `shadow_chest`                   | 不改                                            | 客制化类 `ShadowChestBlock`，含 BlockEntity、Menu、Screen以及Geckolib特殊渲染                                                  |
| `windmoor_crate`                 | `wind_moor_crate`                | 重命名，补下划线                                      | 客制化类 `WindMoorCrateBlock`，含 BlockEntity、Menu、Screen                                                              |
| `qym_doll_0`                     | `qym_doll`                       | 重命名，删除 `_0` 后缀                                | 客制化类 `QYMDollBlock`，因Geckolib特殊渲染实现空BlockEntity                                                                  |
| `uuz_doll_0`                     | `uuz_doll`                       | 重命名，删除 `_0` 后缀                                | 客制化类 `UUZDollBlock`，因Geckolib特殊渲染实现空BlockEntity                                                                  |




---

## 物品映射

| 旧 ID                           | 新 ID                                        | 变更说明                                                      | 客制化类处理                               |
|--------------------------------|---------------------------------------------|-----------------------------------------------------------|--------------------------------------|
| `amber_candy`                  | `amber_candy`                               | 不改，食物属性：回复 0.5 饱食度 + 3s 生命恢复 I                            | 无需客制化类，直接用 `Item` + `FoodProperties` |
| `dyedream_dust`                | `dyedream_dust`                             | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dyedream_dust_piece`          | `dyedream_dust_piece`                       | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `pink_slimeball`               | `pink_slimeball`                            | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `mortar`                       | `mortar`                                    | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dyedream_dye`                 | `dyedream_dye`                              | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dyedream_bud_nugget`          | `dyedream_bud_nugget`                       | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `raw_titanium`                 | `raw_titanium`                              | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `titanium_ingot`               | `titanium_ingot`                            | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `titanium_nugget`              | `titanium_nugget`                           | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dyedream_base`                | `raw_dyedream_alloy_ingot`                  | 重命名，`dyedream_base` → `raw_dyedream_alloy_ingot`（染梦合金锭粗胚） | 无需客制化类，直接用 `Item`                    |
| `dyedream_ingot`               | `dyedream_alloy_ingot`                      | 重命名，`dyedream_ingot` → `dyedream_alloy_ingot`（染梦合金锭）      | 无需客制化类，直接用 `Item`                    |
| `dyedream_nugget`              | `dyedream_alloy_nugget`                     | 重命名，补充 `alloy_` 前缀与系列一致                                   | 无需客制化类，直接用 `Item`                    |
| `dyedreamquartz`               | `dyedream_quartz`                           | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `moltengold_ingot`             | `molten_gold_ingot`                         | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `moltengold_nugget`            | `molten_gold_nugget`                        | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `raw_moltengold`               | `raw_molten_gold`                           | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `meltdream_liquid_bucket`      | `meltdream_liquid_bucket`                   | 不改                                                        | 无需客制化类，直接用 `BucketItem`              |
| `shadow_liquid_bucket`         | `shadow_liquid_bucket`                      | 不改                                                        | 无需客制化类，直接用 `BucketItem`              |
| `glassjar`                     | `glass_jar`                                 | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `water_glassjar`               | `glass_jar_of_water`                        | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `milk_glassjar`                | `glass_jar_of_milk`                         | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `yeast`                        | `glass_jar_of_yeast`                        | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `guiding_drug`                 | `glass_jar_of_guiding_drug`                 | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `wind_plant_extract`           | `glass_jar_of_wind_plant_extract`           | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `dreamjuice`                   | `glass_jar_of_dream_juice`                  | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `goldenrod_tea`                | `glass_jar_of_goldenrod_tea`                | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `dyedream_perfume`             | `glass_jar_of_dyedream_perfume`             | 重命名，更改命名格式为 `glass_jar_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `glass_cup`                    | `glass_cup`                                 | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `apple_juice`                  | `glass_cup_of_apple_juice`                  | 重命名，更改命名格式为 `glass_cup_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `dyedream_flower_tea_0`        | `glass_cup_of_uncooked_dyedream_flower_tea` | 重命名，更改命名格式为 `glass_cup_of_xxx`，加入`uncooked_` 前缀           | 无需客制化类，直接用 `Item`                    |
| `dyedream_flower_tea`          | `glass_cup_of_cooked_dyedream_flower_tea`   | 重命名，更改命名格式为 `glass_cup_of_xxx`，加入`cooked_` 前缀             | 无需客制化类，直接用 `Item`                    |
| `dyedream_juice`               | `glass_cup_of_dyedream_juice`               | 重命名，更改命名格式为 `glass_cup_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `honey_juice`                  | `glass_cup_of_honey_juice`                  | 重命名，更改命名格式为 `glass_cup_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `watermelon_juice`             | `glass_cup_of_watermelon_juice`             | 重命名，更改命名格式为 `glass_cup_of_xxx`                            | 无需客制化类，直接用 `Item`                    |
| `ryeseed`                      | `rye_seed`                                  | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `flour`                        | `flour`                                     | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dough`                        | `dough`                                     | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `eggdough`                     | `dough_with_egg`                            | 重命名，`eggdough` → `dough_with_egg`                         | 无需客制化类，直接用 `Item`                    |
| `coarse_salt`                  | `coarse_salt`                               | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `salt`                         | `salt`                                      | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `pinkegg`                      | `pink_egg`                                  | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `chocolate`                    | `chocolate`                                 | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dyedream_fruit`               | `dyedream_fruit`                            | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `fig`                          | `fig`                                       | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `cake_base`                    | `cake_base`                                 | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `buncake_0`                    | `cream_bun_cake`                            | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `buncake_1`                    | `berry_bun_cake`                            | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `buncake_2`                    | `tuber_bun_cake`                            | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `buncake_3`                    | `watermelon_bun_cake`                       | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `buncake_4`                    | `pumpkin_bun_cake`                          | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `buncake_5`                    | `glow_berry_bun_cake`                       | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `buncake_6`                    | `dyedream_fruit_bun_cake`                   | 重命名，更改命名格式为 `xxx_bun_cake`                                | 无需客制化类，直接用 `Item`                    |
| `chocolate_matcha_cake`        | `chocolate_matcha_cake`                     | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `ricecake`                     | `rice_cake`                                 | 重命名，补下划线                                                  | 无需客制化类，直接用 `Item`                    |
| `dyedream_popsicle`            | `dyedream_popsicle`                         | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `fried_egg`                    | `fried_egg`                                 | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `bacon_and_egg`                | `bacon_and_egg`                             | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `odd_bacon_and_egg`            | `odd_bacon_and_egg`                         | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `heart_chocolate_0`            | `heart_chocolate`                           | 重命名，为心形巧克力加上颜色前缀（黑色除外）                                    | 无需客制化类，直接用 `Item`                    |
| `heart_chocolate_1`            | `white_heart_chocolate`                     | 重命名，为心形巧克力加上颜色前缀（黑色除外）                                    | 无需客制化类，直接用 `Item`                    |
| `heart_chocolate_2`            | `pink_heart_chocolate`                      | 重命名，为心形巧克力加上颜色前缀（黑色除外）                                    | 无需客制化类，直接用 `Item`                    |
| `bread_slice`                  | `bread_slice`                               | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `swiss_roll`                   | `swiss_roll`                                | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `sandwich_0`                   | `sandwich`                                  | 重命名，去除掉 `_0`                                              | 无需客制化类，直接用 `Item`                    |
| `wafer_biscuit`                | `wafer_biscuit`                             | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `stuffed_wafer_cookies`        | `stuffed_wafer_cookies`                     | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `gingerbread_man`              | `gingerbread_man`                           | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `candy_cane`                   | `candy_cane`                                | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `popping_candy`                | `popping_candy`                             | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `yinhul_cotton_candy`          | `yinhul_cotton_candy`                       | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dream_cotton_candy`           | `melt_dream_cotton_candy`                   | 重命名，前面加入 `melt_`                                          | 无需客制化类，直接用 `Item`                    |
| `bubble_gum`                   | `bubble_gum`                                | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `light_organ`                  | `light_organ`                               | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `jellyfish_mud`                | `jellyfish_mud`                             | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `jellyfish_jello`              | `jellyfish_jello`                           | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `queer_soup`                   | `queer_soup`                                | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `legend_dragon_horn_ice_cream` | `legendary_dragon_horn_ice_cream`           | 重命名，`legend`改为`legendary`                                 | 无需客制化类，直接用 `Item`                    |
| `elixir_bottle`                | `elixir_bottle`                             | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `meltdream_elixir_bottle`      | `elixir_bottle_of_melt_dream`               | 重命名，命名格式改为`elixir_bottle_of_xxx`                          | 无需客制化类，直接用 `Item`                    |
| `rage_elixir_0`                | `elixir_bottle_of_rage_elixir`              | 重命名，命名格式改为`elixir_bottle_of_xxx`，去除`_0`                   | 无需客制化类，直接用 `Item`                    |
| `pineapple_love_sea`           | `pineapple_love_sea`                        | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `magic_stone`                  | `magic_stone`                               | 不改，Tooltip 文字待后续实现                                        | 无需客制化类，直接用 `Item`                    |
| `soul_dust`                    | `soul_dust`                                 | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `soul_essence`                 | `soul_essence`                              | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `charged_amethyst`             | `charged_amethyst`                          | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `dyedream_corolla`             | `dyedream_corolla`                          | 不改                                                        | 无需客制化类，直接用 `Item`                    |
| `meltdream_crystal_0`          | `melt_dream_crystal_fragment`               | 重命名，添加下划线和`fragment`，去除`_0`                               | 无需客制化类，直接用 `Item`                    |
| `dreamnotes_0`                 | `dream_notes_dyedream_world`                | 整个物品机制重做，合并相同类型，使用NBT区分（NBT内容使用特殊绑定框架）                    | 客制化类 `DreamNotesItem`                |
| `dreamnotes_1`                 | 删除                                          | -                                                         | -                                    |
| `dreamnotes_2`                 | 删除                                          | -                                                         | -                                    |
| `dreamnotes_3`                 | 删除                                          | -                                                         | -                                    |
| `dreamnotes_4`                 | 删除                                          | -                                                         | -                                    |
| `dreamnotes_5`                 | 删除                                          | -                                                         | -                                    |
| `dreamnotes_6`                 | 删除                                          | -                                                         | -                                    |
| `dreamnotes_7`                 | 删除                                          | -                                                         | -                                    |
| `dreamnotes_8`                 | `dream_notes_lamp_shadow_world`             | 整个物品机制重做，合并相同类型，使用NBT区分（NBT内容使用特殊绑定框架）                                                         | 客制化类 `DreamNotesItem`                                    |
| `dreamnotes_9`                 | 删除                                          | -                                                         | -                                    |
| `dreamnotes_10`                | 删除                                          | -                                                         | -                                    |
| `dreamnotes_11`                | 删除                                          | -                                                         | -                                    |
| `dreamnotes_12`                | 删除                                          | -                                                         | -                                    |
| `dreamnotes_13`                | 删除                                          | -                                                         | -                                    |
| `dreamnotes_14`                | `dream_notes_wind_journey_world`            | 整个物品机制重做，合并相同类型，使用NBT区分（NBT内容使用特殊绑定框架）                                                         | 客制化类 `DreamNotesItem`                                    |
| `sculk_heart`                  | `sculk_heart`                               | 不改                                                        | 无需客制化类，直接用 `Item`                        |
| `blackstick`                   | `black_stick`                               | 重命名，加下划线                                                  | 无需客制化类，直接用 `Item`                        |





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

## 维度映射

| 旧 ID                          | 新 ID                          | 变更说明             | 备注                                                   |
|-------------------------------|-------------------------------|------------------|------------------------------------------------------|
| `dyedream_world`              | `dyedream_world`              | 不改，已实现           | `DimensionType` + `NoiseSettings` + `LevelStem` 已完成 datagen |
| `lamp_shadow_world`           | `lamp_shadow_world`           | 不改，待搬运           | -                                                    |
| `wind_journey_world`          | `wind_journey_world`          | 不改，待搬运           | -                                                    |
| `aaroncos_arena_world`        | `aaroncos_arena_world`        | 不改，待搬运           | -                                                    |

---

## 群系映射

| 旧 ID                         | 新 ID                          | 变更说明               | 备注                                |
|--------------------------------|-------------------------------|--------------------|-----------------------------------|
| `biome_dyedream_0`             | `dyedream_plains`             | 重命名，已实现            | 染梦平原，`ModBiomes` 中完成 datagen        |
| `biome_dyedream_1`             | 待搬运                           | -                  | 染梦蘑菇林                             |
| `biome_dyedream_2`             | 待搬运                           | -                  | 染梦雪原                             |
| `biome_dyedream_3`             | 待搬运                           | -                  | 染梦冻洋                             |
| `biome_shadow_0`               | 待搬运                           | -                  | 阴影之地                             |
| `biome_shadow_1`               | 待搬运                           | -                  | 阴影森林                             |
| `biome_shadow_2`               | 待搬运                           | -                  | 阴影古迹                             |
| `wind_journey_biome_0`         | 待搬运                           | -                  | 风泊群岛                             |
| `wind_journey_biome_1`         | 待搬运                           | -                  | 迷梦云层                             |
| `aaroncos_arena_biome`         | 待搬运                           | -                  | 亚伦柯斯竞技场                          |
