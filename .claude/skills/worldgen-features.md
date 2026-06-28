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

| 旧 ID | 新 ID | 类型 | 方块 | 所属群系 |
|-------|-------|------|------|---------|
| `biome_dyedream_0_tree` | `dyedream_tree` | tree | dyedream_log + dyedream_leaves | PL, SP |
| `ground_feature_dyedream_0` | `dyedream_ice_pillar` | block_column(冰柱 4~11) | dyedream_ice | SP |
| `ground_feature_dyedream_12` | `dyedream_icestone_blobs` | ore(替换方解石 r=6~9) | ice_stone | SP, FO |

---

## 二、染梦通用地物 (ground_feature_dyedream_*)

> 原作通过 `dyedream_biome.json` tag 跨 4 群系共享，实际分配由用户确认。

| # | 旧 ID | 新 ID | 类型 | 方块 | 群系 | 状态 |
|---|-------|-------|------|------|------|------|
| 0 | `ground_feature_dyedream_0` | `dyedream_ice_pillar` | block_column(冰柱 4~11) | `dyedream_ice` | SP | ✅ |
| 1 | `ground_feature_dyedream_1` | — | lake(水+雪块围边) | water + snow_block | ? | 待分配ID |
| 2 | `ground_feature_dyedream_2` | — | random_patch(云团 xz=2) | `cloud` ❌ | ? | 方块未搬 |
| 3 | `ground_feature_dyedream_3` | — | block_column(冰柱 3~9) | `dyedream_packed_ice` | ? | 待分配ID |
| 4 | `ground_feature_dyedream_4` | — | random_patch(大云团 tries=256) | `cloud` ❌ | ? | 方块未搬 |
| 5 | `ground_feature_dyedream_5` | — | random_patch(海草 tries=7) | `dyedream_seagrass` ❌ | ? (水域) | 方块未搬 |
| 6 | `ground_feature_dyedream_6` | — | coral_tree | vanilla coral | FO | 待分配ID |
| 7 | `ground_feature_dyedream_7` | — | coral_claw | vanilla coral | FO | 待分配ID |
| 8 | `ground_feature_dyedream_8` | — | coral_mushroom | vanilla coral | FO | 待分配ID |
| 9 | `ground_feature_dyedream_9` | — | random_patch(tries=2) | `dyedream_bud_0` → `large_dyedream_bud` | ? | 待分配ID |
| 10 | `ground_feature_dyedream_10` | — | random_patch(tries=2) | `dyedream_bud_1` → `medium_dyedream_bud` | ? | 待分配ID |
| 11 | `ground_feature_dyedream_11` | — | random_patch(tries=2) | `dyedream_bud_1` → `medium_dyedream_bud` | ? | 待分配ID |
| 12 | `ground_feature_dyedream_12` | `dyedream_icestone_blobs` | ore(替换方解石 r=6~9) | `icestone` → `ice_stone` | SP, FO | ✅ |
| 13 | `ground_feature_dyedream_13` | — | simple_random_selector(冰+浮冰替换方解石 r=3~6) | vanilla ice, packed_ice | PL, MM, SP | 待分配ID |
| 14 | `ground_feature_dyedream_14` | — | random_patch(tries=5) | `ice_bud_0` → `ice_bud` | ? | 待分配ID |
| 15 | `ground_feature_dyedream_15` | — | forest_rock(方解石块) | vanilla calcite | PL, MM, SP | 待分配ID |

> `❌` = 对应方块尚未搬运  
> `?` = 群系归属待用户确认  
> `待分配ID` = 已在 mapping.json 标记为"待搬运"，需要确认新 ID 后写入

---

## 三、花卉 (flower_*)

| 旧 ID | 新 ID | 中文名(推测) | 群系 | 状态 |
|-------|-------|------------|------|------|
| `flower_1` | `ferraria_crispa` | 法拉利鸢尾 | ? | 待搬运 |
| `flower_2` | `eustoma` | 洋桔梗 | ? | 待搬运 |
| `flower_3` | `rye` | 黑麦草 | ? | 待搬运 |
| `flower_5` | `malva_sinensis_cavan` | 锦葵 | ? | 待搬运 |
| `flower_6` | `blaze_flower` | 炽焰花 | ? | 待搬运 |
| `flower_7` | `crimson_thorns` | 猩红荆棘 | ? | 待搬运 |
| `flower_8` | `pink_mushroom` | 粉色蘑菇 | ? | 待搬运 |
| `flower_9` | `linht_flower` | 灵光花 | ? | 待搬运 |
| `flower_10` | `tall_pink_mushroom` | 高粉蘑菇 | ? | 待搬运 |
| `flower_11` | `dreaming_lotus` | 梦幻莲花 | ? | 待搬运 |
| `flower_13` | `dyedream_lily_of_the_valley` | 染梦铃兰 | ? | 待搬运 |
| `flower_14` | `singularity_fern` | 奇点蕨 | ? | 待搬运 |
| `flower_15` | `white_orchid_flower` | 白色兰花 | ? | 待搬运 |
| `flower_16` | `edelweiss` | 雪绒花 | **SP** | 待搬运 |
| `flower_18` | — | — | — | 未映射 |

> 方块均已注册 ✅，只需确定群系归属即可搬

---

## 四、草类 / 地面覆盖 (grass_*)

| 旧 ID | 新 ID | 说明 | 群系 | 状态 |
|-------|-------|------|------|------|
| `grass_1` | — | — | ? | 未映射 |
| `grass_3` | `stem_grass` | 茎草 | ? | 待搬运 |
| `grass_4` | `tall_stem_grass` | 高茎草 | ? | 待搬运 |
| `grass_5` | `polished_calcite_stalicripe` | 磨制方解石笋(大) | PL, MM, SP | 待搬运 |
| `grass_6` | `small_polished_calcite_stalicripe` | 磨制方解石笋(小) | PL, MM, SP | 待搬运 |
| `grass_7` | `dyedream_moss` | 染梦苔藓 | ? | 待搬运 |
| `grass_8`~`15` | — | — | ? | 未映射 |

> `grass_5`/`grass_6` = 用户提到的"方解石笋"  
> `未映射` = mapping.json 中无记录，且部分可能在 NOT_MODIFY 中无对应 JSON

---

## 五、独立地物 / 矿石

| 旧 ID | 新 ID | 类型 | 群系 | 状态 |
|-------|-------|------|------|------|
| `dyedream_ice` | `dyedream_ice` | 冰块特征 | ? | 待搬运 |
| `dyedream_packed_ice` | `dyedream_packed_ice` | 浮冰特征 | ? | 待搬运 |
| `dyedream_sand` | `dyedream_sand` | 沙地斑块 | ? | 待搬运 |
| `dyedream_sapling` | `dyedream_sapling` | 树苗(可能非地物) | — | — |
| `goldenrod` | `goldenrod` | 一枝黄花 | ? | 待搬运 |
| `dyedream_lily_pad` | — | 睡莲 | ? | 未映射 |
| `dyedream_lotus` | — | 荷花 | ? | 未映射 |
| `jungle_spore_plant` | — | 孢子植物 | ? | 未映射 |
| `vine_0` | — | 藤蔓 | ? | 未映射 |
| `white_sand` | — | 白沙(风之旅?) | — | 未映射 |
| `shadow_fungus` | — | 影菌(阴影维度) | — | 未映射 |
| `congeal_wind_ore` | — | 凝风矿 | ? | 未映射 |
| `windrunner_crystal_ore` | — | 风行者水晶矿 | ? | 未映射 |
| `ground_overworld_0` | — | 主世界地物(?) | — | 未映射 |
| `amber_candy_ore` | `amber_candy_ore` | 琥珀糖矿 | ? | 待搬运 |
| `dyedreamdust_ore` | `dyedream_dust_ore` | 染梦尘矿 | ? | 待搬运 |
| `dyedreamquartz_ore` | `dyedream_quartz_ore` | 染梦石英矿 | ? | 待搬运 |
| `titanium_ore` | `titanium_ore` | 钛矿 | ? | 待搬运 |
| `deepslate_titanium_ore` | `deepslate_titanium_ore` | 深层钛矿 | ? | 待搬运 |
| `moltengold_ore` | `molten_gold_ore` | 炙焰金矿 | ? | 待搬运 |
| `soul_ore` | `soul_ore` | 灵魂矿 | ? | 待搬运 |
| `crop_0a`~`4a` | 见映射表 | 作物野生生成 | ? | 待搬运 |

---

## 六、树地物

| 旧 ID | 新 ID | 树干 | 树叶 | 群系 | 状态 |
|-------|-------|------|------|------|------|
| `biome_dyedream_0_tree` | `dyedream_tree` | dyedream_log ✅ | dyedream_leaves ✅ | PL, SP | ✅ |
| `biome_dyedream_1_tree` | — | pinkagaric_1 ❌ | pinkagaric_0 ❌ | MM | 方块未搬 |
| `biome_dyedream_2_tree` | — | dyedream_log ✅ | dyedream_leaves ✅ | — | 被 dyedream_tree 替代 |

---

## 下一步：立即可搬 (方块已就绪)

| 顺序 | 旧 ID | 内容 | 群系 |
|------|-------|------|------|
| 1 | `flower_16` | 雪绒花 (edelweiss) | SP |
| 2 | `ground_feature_dyedream_15` | 方解石块 (forest_rock) | PL, MM, SP |
| 3 | `grass_5` + `grass_6` | 方解石笋(大/小) | PL, MM, SP |
| 4 | `ground_feature_dyedream_13` | 冰团块 | PL, MM, SP |
| 5 | `ground_feature_dyedream_6~8` | 珊瑚 tree/claw/mushroom | FO |
| 6 | `ground_feature_dyedream_14` | 冰芽 (ice_bud) | ? |
| 7 | `ground_feature_dyedream_9~11` | 染梦芽 (bud) | ? |
| 8 | `ground_feature_dyedream_3` | 浮冰冰柱 | ? |
| 9 | 花草类 flower_*, grass_* | 各花各草 | 待确认群系 |

> 每搬完一个即填入 mapping.json 新 ID、更新本文档状态
