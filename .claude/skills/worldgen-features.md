# 染梦维度世界生成地物参考

> 源模组(`biome_dyedream_0`~`_3`)的地物清单与搬运进度  
> 关联：[[port-content]]
>
> 查 ID 映射：`cd document/design && python lookup_id.py <旧ID>`

---

## 群系速查

| 旧 ID | 新 ID | 中文名 | 缩写 |
|-------|-------|--------|------|
| `biome_dyedream_0` | `dyedream_plains` | 染梦平原 | PL |
| `biome_dyedream_1` | `dyedream_mushroom_mountains` | 粉顶菇山地 | MM |
| `biome_dyedream_2` | `dyedream_snowy_plains` | 染梦雪原 | SP |
| `biome_dyedream_3` | `dyedream_frozen_ocean` | 染梦冻洋 | FO |

---

## 一、已搬运地物

### 树地物

| 旧 ID | 新 ID | 配置 | 群系 |
|-------|-------|------|------|
| `biome_dyedream_0_tree` | `dyedream_tree` | ForkingTrunkPlacer(7,2,2) + AcaciaFoliagePlacer(2,0) | PL, SP |
| `biome_dyedream_1_tree` | — | — | 方块未搬(pinkagaric) |
| `biome_dyedream_2_tree` | — | — | 被 dyedream_tree 替代 |

### 地表花草

| 旧 ID | 新 ID | 放置 | 群系 |
|-------|-------|------|------|
| `grass_3` | `stem_grass_patch` | Count(5), scatter(xz=16) | PL |
| `grass_4` | `tall_stem_grass_patch` | Count(3), scatter(xz=16) | PL |
| `grass_5` | `calcite_stalicripe` | Count(20), 方解石笋(大) | PL, MM, SP |
| `grass_6` | `small_calcite_stalicripe` | Count(20), 方解石笋(小) | PL, MM, SP |
| `grass_7` | `dyedream_moss_patch` | Count(20), 染梦苔藓 | 全部 |
| `crop_0a` | `dyedream_corolla_patch` | Rarity(6), cluster(xz=5,age=1) | PL |
| `crop_2a` | `light_ball_patch` | Rarity(6), cluster(xz=5,age=1) | PL |
| `crop_3a` | `cloud_crop_patch` | Rarity(6), cluster(xz=5,age=1) | PL |
| `flower_9` | `linht_flower_patch` | Count(2)+Rarity(32), 苓灯花 | PL, MM, SP |
| `flower_11` | `dreaming_lotus_patch` | 冶梦莲 | PL, MM, SP |
| `flower_13` | `dyedream_lily_patch` | 染梦铃兰 | PL, SP |
| `flower_14` | `singularity_fern_patch` | Count(2)+Rarity(32), 奇异蕨 | PL, MM, SP |
| `flower_16` | `edelweiss_patch` | Count(5)+Rarity(32), 雪绒花 | SP |
| `crop_1a` | `white_corolla_crop_patch` | 苍白雪莲 | PL, SP, MM |
| `crop_4a` | `cotton_crop_patch` | 棉花 | ? |

### 洞穴晶芽

| 旧 ID | 新 ID | 放置 | 群系 |
|-------|-------|------|------|
| `dyedream_bud_0` | `large_dyedream_bud_patch` | Rarity(4), y=-64~320, tries=2 | 全部 |
| `dyedream_bud_1` | `medium_dyedream_bud_patch` | Rarity(4), y=-64~320, tries=2 | 全部 |
| `dyedream_bud_2` | `small_dyedream_bud_patch` | Rarity(4), y=-64~320, tries=2 | 全部 |
| `ice_bud_0` | `ice_bud_patch` | Rarity(8), y=-64~320, tries=5 | SP, FO |

### 矿石

| 旧 ID | 新 ID | size | 群系 |
|-------|-------|------|------|
| `titanium_ore` | `titanium_ore` | 8, triangle(钻石分布) | 全部 |
| `amber_candy_ore` | `amber_candy_ore` | 24, y=-60~320 | 全部 |
| `dyedreamdust_ore` | `dyedream_dust_ore` | 16, y=-60~320 | 全部 |
| `dyedreamquartz_ore` | `dyedream_quartz_ore` | 28, y=-60~320 | 全部 |
| `deepslate_titanium_ore` | `deepslate_titanium_ore` | 8, y=-64~0 | 全部 |
| `moltengold_ore` | `molten_gold_ore` | 4, y=0~64 | 全部 |
| `soul_ore` | `soul_ore` | 8, y=0~128 | 全部 |

### 团块 (地下替换方解石)

| 旧 ID | 新 ID | 方块 | size | count | 群系 |
|-------|-------|------|------|-------|------|
| `ground_feature_dyedream_12` | `dyedream_ice_stone_blobs` | ice_stone | 64 | 6 | SP, FO |
| `ground_feature_dyedream_13` | 拆分为两个 | — | — | — | — |
| — | `dyedream_ice_blobs` | dyedream_ice | 64 | 6 | PL, MM |
| — | `dyedream_packed_ice_blobs` | dyedream_packed_ice | 64 | 6 | PL, MM |
| — | `vanilla_ice_blobs` | vanilla ice | 64 | 6 | SP, FO |
| — | `vanilla_packed_ice_blobs` | vanilla packed_ice | 64 | 6 | SP, FO |

### 地表团块

| 旧 ID | 新 ID | 类型 | size | 群系 |
|-------|-------|------|------|------|
| `ground_feature_dyedream_15` | `calcite_boulder` | ORE(替换地表+空气) | 24 | PL, MM, SP, FO |
| `ground_feature_dyedream_0` | `dyedream_ice_pillar` | block_column(4~11) | — | SP |

### 其他完整花卉/作物 (已搬，群系由用户自行分配)

| 旧 ID | 新 ID | 中文名 |
|-------|-------|--------|
| `flower_1` | `ferraria_crispa_patch` | 魔星兰 |
| `flower_2` | `eustoma_patch` | 洋桔梗 |
| `flower_3` | `rye_patch` | 黑麦草 |
| `flower_5` | `malva_sinensis_cavan_patch` | 锦葵 |
| `flower_6` | `blaze_flower_patch` | 炽焰花 |
| `flower_8` | `pink_mushroom_patch` | 粉色蘑菇 |
| `flower_10` | `tall_pink_mushroom_patch` | 高粉蘑菇 |
| `flower_15` | `white_orchid_flower_patch` | 白色兰花 |
| `goldenrod` | `goldenrod_patch` | 秋麒麟 |

---

## 二、技术要点

- **HeightmapPlacement**: Forge 1.20.1 私有构造函数，通过 `onHeightmap()` 反射创建
- **作物 AGE**: 直接生成 `age=1` 成熟态，避免随机刻
- **MultiNoiseBiomeSource**: 通过反射访问私有构造函数 `MultiNoiseBiomeSource(Either<...>)`
- **团块 vs 矿石**: 团块用 `UNDERGROUND_ORES` step，`discard_chance_on_air_exposure=0.5` 减少气泡
- **SurfaceWaterDepthFilter.forMaxDepth(0)**: 禁止水域生成（方解石团块）

---

## 三、未搬运 / 待处理

| 类别 | 内容 | 原因 |
|------|------|------|
| `cloud` 相关地物 | `ground_feature_2,4` | 方块未搬 |
| `dyedream_seagrass` | `ground_feature_5` | 方块未搬 |
| 珊瑚 | `ground_feature_6~8` | 待搬运 |
| `flower_7` | 猩红荆棘 | 待搬运 |
| `flower_18` | — | 未映射 |
| `grass_1,8~15` | — | 未映射 |
| 阴影维度地物 | `shadow_*` | 方块未搬 |
| 风之旅地物 | `wind_journey_*` | 方块未搬 |
| `pinkagaric_0/1` 树 | `biome_dyedream_1_tree` | 方块未搬 |
