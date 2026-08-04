# 亚伦柯斯之触 (Aaroncos's Touch) BOSS 分析与搬运指南

## 概述

"亚伦柯斯之触"是一个双实体 BOSS 战，由两只巨大的手组成：
- **AaroncosLefthand0Entity** — 亚伦柯斯之触 - 左（左手）
- **AaroncosRighthand0Entity** — 亚伦柯斯之触 - 右（右手）

原代码位于 `NOT_MODIFY/reference/` 下，包路径 `net.pasterdream`。新项目尚未搬运任何 BOSS 相关代码。

---

## 一、两个实体对比

| 属性 | 左手 (Left) | 右手 (Right) |
|---|---|---|
| 父类 | `Monster implements GeoEntity` | `Monster implements GeoEntity` |
| 最大生命 | 500 | 500 |
| 护甲 | 10 | 4 |
| 攻击力 | 20 | 18 |
| 移动速度 | 0.25 | 0.4 |
| 飞行速度 | 0.25 | 0.4 |
| 追踪范围 | 32 | 32 |
| 击退抗性 | 1 (100%) | 1 (100%) |
| 尺寸 | 3.5 × 3.5 | 3.5 × 3.5 |
| 经验值 | 100 | 100 |
| 移动类型 | FlyingMoveControl | FlyingMoveControl |
| 导航 | FlyingPathNavigation | FlyingPathNavigation |
| Boss 条颜色 | PINK | PINK |
| 防火 | 是 | 是 |
| 生物类型 | UNDEFINED | UNDEFINED |
| 存活性 | persist/不despawn | persist/不despawn |

### 共同的基类特征
- 无重力（setNoGravity(true) 在 aiStep 和构造函数中强制）
- 无摔伤
- 不可更换维度
- 免疫火焰伤害
- 免疫 `special_entity_tag` 和 `shadow_mob` 标签实体的技能伤害（友军保护）

---

## 二、AI 行为

### 共同 AI
- **Goal 1**: 自定义 MeleeAttackGoal — 攻击范围动态适配目标碰撞箱
- **Goal 2**: 自定义跟随 Goal — 飞向目标眼睛位置，接触时造成伤害
- **Target**: NearestAttackableTargetGoal → 玩家
- **Goal 4**: RandomStrollGoal — 16 格范围内随机飞行

### AI 差异
- 左手有 Goal 2 的飞行跟随逻辑（与右手 Goal 1 相同但分开实现）
- 右手缺少左手的重击 Goal；左手缺少某些闲逛优先级

---

## 三、技能系统（Scoreboard 状态机）

两个手使用 **Scoreboard** 作为技能状态机。所有技能执行期间 `skill=1`，冷却结束后 `skill=0`。

### 核心状态变量
| Scoreboard Key | 含义 | 初始值 |
|---|---|---|
| `switch` | Boss 激活开关 | 0 → 1（spawn 后100t） |
| `skill` | 技能执行锁 | 0=空闲, 1=执行中 |
| 左手专用 | | |
| `skill_sprint` | 冲刺计数 | 0~3，=3 时触发重击 |
| `skill_hit` | 重击计数 | 0~4 |
| `skill_sword` | 剑技冷却 | 0~1，CD=420t |
| 右手专用 | | |
| `skill_magicball` | 魔法球计数 | 0~3，=3 时触发漩涡 |
| `skill_vortex` | 漩涡计数 | 0~4 |
| `skill_tunetotem` | 图腾冷却 | 0~1，CD=600t |

### 左手技能

#### 1. 冲刺技能 (skill_sprint)
- **触发**: every tick (baseTick)，skill!=1 且 switch==1
- **动画**: `skill_sprint`
- **行为**:
  - 16t 后面朝最近玩家
  - 向面朝方向冲刺（速度 × 2.8）
  - 17t、24t 时在 6 格范围内造成 7 点伤害
  - 播放 `stone_break_0` + 爆炸音效 + 粒子
- **CD**: 120t（每三次冲刺后触发技能3）
- **计数满3**: 触发 skill_hit

#### 2. 重击技能 (skill_hit)
- **触发**: skill_sprint 计数达到 3
- **动画**: `skill_hit`
- **行为**: 三次跃起→坠落地面打击
  - 第一击: +2→-10, 15格AOE, 6伤, 困惑10t
  - 第二击: +3→-10, 19格AOE, 7伤, 困惑10t  
  - 第三击: +4→-10, 23格AOE, 8伤, 困惑10t
- **CD**: 100t（skill_hit 重置为 0）

#### 3. 剑技 (skill_sword)
- **触发**: 受伤时 (hurt)，HP>1, skill==0, switch==1
- **动画**: `skill_sword`
- **行为**:
  - 自身下坠 (-2 velocity)
  - 获得 DAMAGE_RESISTANCE I 120t
  - 30 格内非友方目标施加 CONFUSION 60t
  - 在 57t~112t 期间发射 7 波剑气（AaroncosLefthandSkillSword0Procedure）
  - 每波: 16格AOE 8伤害 + CONFUSION 20t（仅玩家）
  - 播放 `stone_break` + `sword_wave` + 爆炸音效
- **CD**: 420t（skill_sword 重新可用）

### 右手技能

#### 1. 魔法球技能 (skill_magicball)
- **触发**: every tick (baseTick)，skill!=1 且 switch==1
- **动画**: `skill_magicball`
- **行为**:
  - 35t 后面朝最近玩家
  - 向面朝方向发射 ShadowMagicball 实体（速度 ×3, ×2, ×3）
  - 播放 `stone_break_0` + 爆炸音效
- **CD**: 90t（每三次后触发技能3）
- **计数满3**: 触发 skill_vortex

#### 2. 漩涡技能 (skill_vortex)
- **触发**: skill_magicball 计数达到 3
- **动画**: `skill_vortex`
- **行为**:
  - 自身坠落 (-5 velocity)
  - 自身 MOVEMENT_SLOWDOWN 40t
  - 42t 后: **所有玩家**（64格内）
    - 受到 4 点伤害
    - CONFUSION 10t
    - 击飞 0.2
    - 在玩家位置放置 SHADOW_VORTEX 方块
    - 在自身位置也放置 SHADOW_VORTEX
    - 播放 `shadow_vortex` 音效
- **CD**: 120t（skill_vortex 重置为 0）

#### 3. 调谐图腾技能 (skill_tunetotem)
- **触发**: 受伤时 (hurt)，HP>1, skill==0, switch==1
- **动画**: `skill_tunetotem`
- **行为**:
  - 自身下坠 (-2 velocity)
  - DAMAGE_RESISTANCE I 40t
  - 15 格内非友方 CONFUSION 60t
  - MOVEMENT_SLOWDOWN 120t（10t 后）
  - 21t 后：随机生成 ShadowTuneTotem 并自身后撤
  - 播放 `stone_break` 音效
- **CD**: 600t

---

## 四、血锁机制 (Blood Lock) — 双方共用

当 HP ≤ 100 时触发（通过 entity NBT 的 `blood_lock` 标记防重复）：
1. 获得 DAMAGE_RESISTANCE III，持续 1200t（60秒）
2. 移除 SHADOW_SILENCE_BUFF 效果
3. 在身边 3×3 范围随机生成 4 只 ShadowHand
4. 80 格内所有玩家:
   - DARKNESS 60t
   - CONFUSION 60t
   - RESTRAINMOVE_BLOCK 60t
5. 播放 aaroncos_spawn 音效

---

## 五、生命周期

### 诞生 (Spawn)
1. 设置动画 → `spawn`
2. 获得 MOVEMENT_SLOWDOWN 80t + DAMAGE_RESISTANCE 100t + HEAL 100t
3. 设置速度为 (0, -2, 0)，生命设为 1
4. 播放 aaroncos_spawn 音效
5. 粒子爆发 SHADOW_STONE_PARTICLE × 64
6. **左手**: 70t 后爆炸（半径3）+ 75t 后设置速度 (0, -0.4, 0)
7. **右手**: 47t 后爆炸（半径3）+ 51t 后设置速度 (0, -0.4, 0)
8. 100t 后 `switch` score 设为 1（激活技能系统）

### 死亡
- **左/右手** `tickDeath()`: deathTime 到 40 时移除实体
- **左手 death**: 播放 wither 死亡音效 + SHADOW_STONE 粒子 + 爆炸（半径4）
- **右手 death**: 调用 `AaroncosLefthandDeathProcedure`（与左手共用同样的终点处理）
- 左手在 `die()` 时播放 `death` 动画

---

## 六、Boss 条 HUD

文件: `AaroncosLefthandBossBar.java`（客户端 Only）

- 自定义 Forge GUI Overlay (`IGuiOverlay`)
- 从玩家 persistent NBT 读取 `left_hand_hp` 和 `right_hand_hp`
- 渲染两个手的血量条在屏幕上方
- 标题: `§l亚伦柯斯之触`
- 贴图: `textures/screens/aaroncos_hand_hp.png`
- 血量数据由 `AaroncosLefthandTickBossbarProcedure`（左手）和 `AaroncosRighthandSkillTunetotemProcedure`（右手，通过 persistent data 写入 `aaroncos_righthand_0_hp` 等）更新

---

## 七、竞技场系统

### 生成方块 (AaroncoshandspawnblockBlock)
- 不可破坏 (hardness=-1, resistance=3600000)
- 每 20t tick 一次，执行 `AaroncoshandspawnblockPr1Procedure`
- 右键交互执行 `AaroncoshandspawnblockPr0Procedure`

### 战斗流程 (AaroncoshandspawnblockPr1Procedure)
1. **time0 计数器**: 每 tick +1
2. **time0 = 50**: 生成 2 只 Terrorbeak（z±12）
3. **time0 = 100**: 再次生成 2 只 Terrorbeak
4. **time0 = 150**: 第三次生成 2 只 Terrorbeak + 播放 `aaroncos_music`
5. **两只手都死亡后**:
   - 在竞技场中心下方放置 AaroncosHandChest（奖励箱）
   - 销毁生成方块
   - 授予所有玩家成就 `achievement_shadow_e_0`
   - 移除 SHADOW_SPYON_BUFF 效果
   - 倒计时 20 秒 → 传送回主世界重生点
   - 清理竞技场内所有非玩家实体

### 竞技场维度
- `aaroncos_arena_world` 维度
- 包含 `aaroncos_arena.nbt` 结构（1MB 的 NBT 文件）
- 传送门结构 `aaroncos_arena_portals`

---

## 八、依赖清单

### 需要已搬运的实体
| 实体 | 状态（新项目） | 用途 |
|---|---|---|
| ShadowHandEntity | ✅ 已搬运 | 血锁机制召唤 |
| TerrorbeakEntity | ✅ 已搬运 | 竞技场波次 |
| ShadowMagicballEntity | ❌ 未搬运 | 右手魔法球技能 |
| ShadowTuneTotemEntity | ❌ 未搬运 | 右手图腾技能 |

### 需要已搬运的效果
| 效果 | 状态 | 用途 |
|---|---|---|
| SHADOW_SILENCE_BUFF | 待确认 | 技能沉默（血锁移除） |
| CONFUSION_BUFF | 待确认 | 大量技能施加 |
| RESTRAINMOVE_BLOCK_BUFF | 待确认 | 血锁施加 |
| SHADOW_SPYON_BUFF | 待确认 | 竞技场追踪 |

### 需要已搬运的方块/物品
| 类型 | 名称 | 用途 |
|---|---|---|
| 方块 | Aaroncoshandspawnblock | 竞技场生成控制器 |
| 方块 | AaroncosHandChest | BOSS 奖励箱 |
| 方块 | SHADOW_VORTEX | 右手漩涡技能 |
| 方块 | AaroncosArenaPortals | 竞技场传送门 |
| 物品 | AaroncosArenaCreateItem | 创建竞技场 |
| 物品 | AaroncosDiscItem | BOSS 音乐唱片 |
| 物品 | 双手生成蛋 | 创造模式生成 |

### 资源文件
- **贴图**: `aaroncos_lefthand_0.png` + `_light.png`, `aaroncos_righthand_0.png` + `_light.png`
- **Geo 模型**: `aaroncos_lefthand_0.geo.json` (10KB), `aaroncos_righthand_0.geo.json` (12KB)
- **动画**: `aaroncos_lefthand_0.animation.json` (1.7KB), `aaroncos_righthand_0.animation.json` (4.6KB)
- **音效**: `aaroncos_music.ogg` (2.4MB), `aaroncos_spawn.ogg` (145KB)
- **Boss UI 贴图**: `aaroncos_hand_hp.png`
- **数据**: 维度定义、世界生成、结构 NBT、进度、标签、配方

### 标签
- `pasterdream:shadow_mob` — 影系生物（双手 + 其他影怪）
- `pasterdream:special_entity_tag` — 特殊实体（免伤保护）

---

## 九、新项目 vs 原项目差异（搬运注意事项）

| 项目 | 原项目 (NOT_MODIFY) | 新项目 (src) |
|---|---|---|
| 包路径 | `net.pasterdream` | `com.pasterdream.pasterdreammod` |
| MODID 常量 | `PasterdreamMod.MODID` | `PasterDreamMod.MOD_ID` |
| 实体注册 | `PasterdreamModEntities.REGISTRY` 内含所有实体 | `ModEntities.REGISTRY` 在独立的 init 包 |
| 注册方式 | DeferredRegister + 手动 register() | DeferredRegister + 统一 register() 静态方法 |
| init() | 空方法 `static void init() {}` | 包含 `SpawnPlacements.register()` 调用 |
| setAnimation | 仅调 `entityData.set(ANIMATION, animation)` | 同时设置 `this.animationprocedure` + `entityData.set` + `onSyncedDataUpdated` |
| 粒子注册 | `PasterdreamModParticleTypes` | `ModParticleTypes` |
| 效果注册 | `PasterdreamModMobEffects` | `ModEffects` |
| 方块注册 | `PasterdreamModBlocks` | `ModBlocks` |
| 物品注册 | `PasterdreamModItems` | `ModItems` |
| 实体渲染 | `PasterdreamModEntityRenderers` | `ModEntityRenderer` |

---

## 十、建议搬运顺序

### 阶段 1: 准备工作
1. 确认 `ModEffects` 中有 `SHADOW_SILENCE_BUFF`、`CONFUSION_BUFF` 等效果
2. 确认 `ModParticleTypes` 中有 `SHADOW_STONE_PARTICLE`
3. 搬运 `ShadowMagicballEntity` 实体
4. 搬运 `ShadowTuneTotemEntity` 实体
5. 搬运 `SHADOW_VORTEX` 方块

### 阶段 2: 搬运双手实体
1. 搬运 `AaroncosLefthand0Entity`（含模型、渲染器、动画）
2. 搬运 `AaroncosRighthand0Entity`（含模型、渲染器、动画）
3. 在 `ModEntities` 中注册
4. 在 `ModEntityRenderer` 中注册渲染器

### 阶段 3: 搬运技能程序
1. 将 20 个 Procedure 文件重写为新项目的包路径
2. 注意 `PasterdreamMod.queueServerWork()` 在新项目中可能改名
3. 注意 TAG、REGISTRIES 等 API 路径的一致性
4. 将 Scoreboard 状态机逻辑直接整合或保持不变

### 阶段 4: 搬运 Boss UI
1. 搬运 `AaroncosLefthandBossBar.java`（客户端 Forge Overlay）
2. 注册到新项目的 Overlay 系统

### 阶段 5: 搬运竞技场系统
1. `AaroncoshandspawnblockBlock` + TileEntity
2. `AaroncosHandChestBlock` + TileEntity
3. `AaroncosArenaPortalsBlock`
4. 维度定义 + 世界生成
5. 竞技场结构 NBT
6. `AaroncosArenaCreateItem`

### 阶段 6: 资源整合
1. 动画 .json 文件 → `assets/pasterdream/animations/`
2. Geo 模型 → `assets/pasterdream/geo/`
3. 贴图 → `assets/pasterdream/textures/entities/`
4. 音效 → `assets/pasterdream/sounds/` + sounds.json
5. 语言文件更新
6. 数据文件（dimension, worldgen, structures, tags, recipes, advancements）

---

## 十一、Scoreboard 状态机的替代方案

原作使用 Scoreboard 实现技能状态机，这是因为 MCreator 生成的代码限制。新项目可以考虑：
- **方案 A（推荐）**: 直接在 Entity 类中使用 `int` 字段替代 Scoreboard，如 `skillCooldown`、`skillState`、`hitCount` 等。状态更清晰，无需依赖 Scoreboard API。
- **方案 B（快速）**: 保留 Scoreboard 方式，直接搬运原代码。优点是改动最小，但代码冗余度高。
- **建议**: 采用方案 A，用实体字段+`baseTick()` 计数管理所有技能冷却和状态，提升可维护性。

---

## 十二、文件总览

### 需要新建/搬运的 Java 源文件
```
entity/
  AaroncosLefthand0Entity.java      (363行) — 左手实体
  AaroncosRighthand0Entity.java     (314行) — 右手实体
  model/
    AaroncosLefthand0Model.java     (25行)
    AaroncosRighthand0Model.java    (25行)
  layer/
    AaroncosLefthand0Layer.java     (29行)
    AaroncosRighthand0Layer.java    (29行)
client/renderer/
  AaroncosLefthand0Renderer.java    (44行)
  AaroncosRighthand0Renderer.java   (44行)

procedures/  (技能逻辑，约20个文件，共约2000行)
  AaroncosLefthandSkillProcedure.java
  AaroncosLefthandSkillSwordProcedure.java
  AaroncosLefthandSkillSword0Procedure.java
  AaroncosLefthandSkillSprintProcedure.java
  AaroncosLefthandSkillHitProcedure.java
  AaroncosLefthand0SpawnProcedure.java
  AaroncosLefthandDeathingProcedure.java
  AaroncosLefthandDeathProcedure.java
  AaroncosLefthandTickBossbarProcedure.java
  AaroncosRighthandSkillProcedure.java
  AaroncosRighthandSkillMagicballProcedure.java
  AaroncosRighthandSkillVortexProcedure.java
  AaroncosRighthandSkillTunetotemProcedure.java
  AaroncosRighthandSpawnProcedure.java
  AaroncosHandBloodlockProcedure.java
  AaroncoshandspawnblockPr0Procedure.java
  AaroncoshandspawnblockPr1Procedure.java
  AaroncosHandChestPr0Procedure.java
  AaroncosArenaPortalsPr0Procedure.java
  AaroncosArenaWorldPr0Procedure.java

block/
  AaroncoshandspawnblockBlock.java  (145行)
  AaroncosHandChestBlock.java       (145行)
  AaroncosArenaPortalsBlock.java    (61行)
  entity/
    AaroncoshandspawnblockTileEntity.java
    AaroncosHandChestTileEntity.java
  model/
    AaroncosHandChestBlockModel.java
    AaroncosHandChestDisplayModel.java
    AaroncoshandspawnblockBlockModel.java
    AaroncoshandspawnblockDisplayModel.java
  renderer/
    AaroncosHandChestTileRenderer.java
    AaroncosHandChestDisplayItemRenderer.java
    AaroncoshandspawnblockTileRenderer.java
    AaroncoshandspawnblockDisplayItemRenderer.java

item/
  AaroncosArenaCreateItem.java      (35行)
  AaroncosDiscItem.java             (26行)
  block/display/
    AaroncosHandChestDisplayItem.java
    AaroncoshandspawnblockDisplayItem.java

world/dimension/
  AaroncosArenaWorldDimension.java  (54行)

PasterdreamHud.java                 (23行)
AaroncosLefthandBossBar.java        (63行)
```

### 需要搬运的资源文件
```
assets/pasterdream/
  animations/
    aaroncos_lefthand_0.animation.json
    aaroncos_righthand_0.animation.json
    aaroncos_hand_chest.animation.json
    aaroncos_hand_spawn_block.animation.json
  geo/
    aaroncos_lefthand_0.geo.json
    aaroncos_righthand_0.geo.json
    aaroncos_hand_chest.geo.json
    aaroncos_hand_spawn_block.geo.json
  textures/entities/
    aaroncos_lefthand_0.png
    aaroncos_lefthand_0_light.png
    aaroncos_righthand_0.png
    aaroncos_righthand_0_light.png
  textures/block/
    aaroncos_hand_chest.png
    aaroncos_hand_spawn_block.png
  textures/screens/
    aaroncos_hand_hp.png
  sounds/
    aaroncos_music.ogg
    aaroncos_spawn.ogg
  models/block/item/custom/displaysettings/  (~17个JSON)
  blockstates/  (3个JSON)
data/pasterdream/
  dimension/aaroncos_arena_world.json
  dimension_type/aaroncos_arena_world.json
  worldgen/biome/aaroncos_arena_biome.json
  worldgen/structure/aaroncos_arena_portal.json
  worldgen/structure_set/aaroncos_arena_portal.json
  worldgen/template_pool/aaroncos_arena_portal.json
  structures/aaroncos_arena.nbt          (1.0 MB)
  structures/aaroncos_arena_portals.nbt  (11.6 KB)
  advancements/achievement_shadow_e_0.json
  tags/entity_types/shadow_mob.json
  recipes/crafting_276.json
```
