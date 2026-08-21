# 更新日志

## 未发布 (基于 v0.0.1-alpha)

### 新增

**钓鱼深海秘宝系统**
- 重构钓鱼战利品为 loot table 注入方式（`FishingLootsHandler`），通过 `LootTableLoadEvent` 向 FISHING 战利品表注入深海秘宝 sub-table，不依赖 Mixin 或 `ItemFishedEvent`，对所有钓鱼方式（原版、水产养殖、车万女仆等）均可生效
- 双层概率结构：主池 `weight=10, quality=2` 受 player luck 影响，sub-table 内 `normal(30,0) + super(1,1)` 使 super 变体比例随 luck 增长
- 覆盖主世界全部 9 个 ocean 变体群系 + 染梦维度 2 个海洋群系（`dyedream_frozen_ocean` / `dyedream_ocean`），1.20.1 不支持 biome tag 故逐个列出
- datagen：`ModFishingLootTablesProvider` 生成 sub-table JSON

**星者祈愿钓竿（star_wish_rod）**
- 搬运并重写 `StarWishRodItem`，继承 `FishingRodItem`，移除原作 MCreator Procedure 依赖
- Lure+5 自带加成（等效 Lure V 等鱼速度），写入 `use()` 方法，不依赖外部 Mixin/事件
- 通过 `star_fishing_rods` 物品标签 + 战利品表 `MatchTool` 条件，手持时额外必出 super 变体
- 修复原作无法在附魔台附魔的 bug（显式 `isEnchantable() = true`）
- 出杆切换模型（cast predicate），在 `PasterDreamMod.registerItemProperties()` 中注册

**创造模式物品栏**
- 新增"帕斯特之梦 | 拓展栏"（Paster Dream | Expansion），来源旧 `paster_tab_9`


**San 阈值状态效果系统**
- 搬运 4 个由 San 百分比决定的状态效果，接入 `SanAuraHandler`：
  - `cheer_up`（振奋）— San ≥ 90% 时给予，-0.1 瞬身术冷却 / +5% 移速 / +5% 攻速
  - `lethargy`（不振）— San 40%~60% 时给予，+0.5 瞬身术冷却 / -1% 移速 / -10% 攻速
  - `trance`（恍惚）— San 20%~40% 时给予，+1.0 瞬身术冷却 / -2% 移速 / -20% 攻速 / -1 攻击力
  - `insand_buff`（疯狂）— San < 20% 时给予，三级强度（<10% / <1%），属性大幅减益 + 客户端画面效果
- 源版阈值从固定值（≤60/≤40/≤20）改为百分比判定（San/maxSan）
- 去掉未搬运的 `SKILLCD` 属性，`TELEPORTATIONCD` 替换为 `BLINK_CD`

**低 San 客户端效果**
- `LoseMind` HUD 覆盖层：San < 20% 时屏幕逐渐浮现 `lose_mind_gui.png`，三级渐进 alpha
- 画面抖动：疯狂 II 级（±0.3°/±0.2°）和 III 级（±1.0°/±0.7°），客户端执行
- 音效：疯狂 II 级以上周期性播放 `losemind0.ogg`（69s ambient，自动循环/停止）
- 效果开关配置（`Config.lowSanOverlay` / `lowSanJitter` / `lowSanSound`）
- 指令 `/pasterdreamdebug san lowsan overlay|jitter|sound set|get` 运行时切换

**San 系统全局开关**
- `SanAuraHandler` 入口检查 `isSanEnabled`，禁用时跳过全部 San 计算和阈值效果
- `SanTank` HUD 禁用时隐藏
- 现有食物/饮品/饰品均已受 `San.addSanValue()` 内置检查保护

**饰品**
- 搬运 `hiyori_butterfly_hairpin`（妃爱的蝴蝶结发夹），品质 EPIC，head 槽位，+2 最大生命值 / +15 幸运 / +0.96 理智光环，`pasterdream.luck` 改为原版 `generic.luck`
- 搬运 `allkinds_ring`（万象神戒），品质 LEGENDARY，ring 槽位，+4 生命 / +2 攻击 / +0.1 攻速 / +0.2 触及距离 / +0.5 方块触及 / +5% 移速，跳过战技系统属性（skillCd/skillMultiplier/teleportationCd）

**效果**
- 搬运 `cook`（料理），+1.2 理智光环/级，方案 B：`applyEffectTick` + Player 检查，避免非玩家实体因 SAN_VARIABILITY 属性未注册而崩溃
- 给 15 种食品添加料理 buff：各色包蛋饼(1min)/巧克力抹茶蛋糕(1min)/培根煎蛋(1min)/奇怪培根煎蛋(2min)/瑞士卷(1min)/三明治(1min)/夹心华夫饼(1min)/姜饼人(1min)/拐杖糖(1min)/染梦果包蛋饼(1min)/菠萝爱海(5min)

**世界生成**
- 添加染梦海洋生物群系
- 添加方解石尖锥地物生成功能
- 为芦苇和丛林孢子囊添加世界生成（芦苇：红树林沼泽/沼泽，丛林孢子囊：丛林）
- 魔星兰和锦葵生成限制为平原、丛林和繁华森林
- 赤荆棘和烈焰花可生成在下界绯红森林群系
- 更改棉花和兰麦的生成群系
- 添加染梦地表植物生成限制功能，防止植物生成在不合适的方块上
- 重构晶芽生成配置以支持不同地面类型
- 添加染梦藤蔓世界生成（平均每 16 区块一簇，2-4 格高柱状）

**方块**
- 染梦草方块新增骨粉催生功能，根据所在群系催生不同植物
- 加入丛林孢子囊方块，无时运情况下掉落 1 个丛林孢子，初始亮度为 6
- 加入四叶草植株
- 粉蘑菇和高粉蘑菇限制仅可放置在拥有 `#minecraft:dirt` 标签的方块上
- 茎草添加 `replaceable` 属性，支持手持方块右键替换
- 加入 4 种材质提供方块

**物品**
- 添加饰品系统支持
- 搬运雪兔脚项链（跳跃提升II + 幸运值+1）、轻羽护符（瞬身术消耗-5%、距离+20%）、生命项链（最大生命值+4）、业火项链（防火 + 燃烧急迫II + 行走留火焰）、十字章项链（瞬身术回避时间延长至0.5秒）
- 补全饰品原胚配方（护身符/项链/戒指/腰带原胚）
- 搬运自然腰带（理智光环 +0.48/分钟，耐久消耗机制）
- 搬运旅者腰带（瞬身术消耗 -0.5）
- 搬运迷梦旅者腰带（+3% 移动速度 + 瞬身术冷却 -0.1）
- 搬运邪眼护身符（使末影人保持中立）
- 搬运海波护身符（+30% 游泳速度、理智光环 +0.96，禁止重复装备）
- 搬运龟壳护身符（-8% 移动速度、+2 护甲、+1 盔甲韧性，禁止重复装备）
- 搬运世界树种荚（染梦世界树环境下消耗饱食度并产出融梦能量）
- 搬运花环（头部饰品，理智光环 +0.48，每 tick 0.4% 概率损耗耐久，配方：8 小花围圈合成）
- 搬运幸运四叶草（通用饰品，+1 最大生命值 +6 幸运，禁止重复装备，四叶草植株破坏掉落）
- 搬运雪下的誓言（头部饰品，附带自定义效果 snow_vow：幸运 +3、免疫燃烧与冻结，每 tick 给附近 3.5 格内玩家施加效果）
- 搬运夜明蝶（通用饰品，低亮度环境消耗融梦能量获得夜视）
- 搬运光明飞蝶（通用饰品，品质上古，低亮度夜视 + 免疫黑暗效果，配方：夜明蝶 + 白厄水晶 + 灵魂精华）
- 搬运白厄水晶（材料物品，制作白厄剑的核心材料）
- 搬运啵啵鸡的华丽飞羽（通用饰品，品质传说，+5% 速度、+0.1 瞬身术距离、-0.2 瞬身术冷却、-0.4 瞬身术消耗，属性通过 onEquip 附加而非 getAttributeModifiers 自动生成 tooltip）
- 搬运琴雨梦的蝴蝶星发卡（头部饰品，品质神迹，所有生物不主动攻击玩家但可反击，防火属性）
- 搬运秋麒麟茶效果（`goldenrod_tea`）：饮用后持续移除饥饿与反胃，百科未收录隐藏效果
- 搬运染梦香水效果（`dyedream_perfume`）：饮用后清空未睡眠天数、幻翼不再攻击，配合 `PhantomTargetMixin` 被动拦截幻翼目标锁定
- 瞬身术与啵啵鸡饰品解耦，冷却公式统一为属性驱动，不再硬编码特定物品检测
- 搬运强击戒指与露滴戒指系列饰品
- 添加笔记残页和未解析笔记物品
- 加入羊皮纸
- 添加园艺钳及其相关功能
- 新增朔漠系列武器
- 新增草薙系列武器及粒子效果
- 添加灵魂粒子效果
- 添加寻梦者的永恒书卷（含 GeckoLib 动画）
- 添加研究台（含 JEI 兼容、GeckoLib 动画及音效）
- 加入丛林孢子物品
- 提高了6种小蛋糕的饱食度
- 食物与饮品将显示对应的状态效果
- 搬运公爵赌场币（`duke_coin_curio`）：品质优秀，+7 幸运值、-7 瞬身术冷却，禁止重复装备，通过 `onEquip`/`onUnequip` 附加永久属性修饰器
- 搬运攻击强化石（`attack_enhance_stone`）与幸运强化石（`luck_enhance_stone`）：精铸工坊镶嵌材料，分别提供攻击力 +0.1~+0.5 与幸运 +1~+2 的随机加成，堆叠 16，品质普通
- 搬运热能匕首（`thermal_dagger`）：主手时 +0.5 游泳速度，攻击命中播放音效并生成气泡粒子，钛金粒修复，防火属性，致敬《深海迷航》（Subnautica）
- 搬运深海秘宝（`deep_sea_treasure`）与染梦深海秘宝（`dyedream_deep_treasure`）：右键开启宝匣获取战利品，NBT `deep_treasure_super` 区分 super 变体（附魔光效 + 高级战利品表），创造模式物品栏同时提供普通与 super 变体


**战利品系统**
- 战利品生成器：NBT 设置战利品表，Shift+右键容器即可按种子生成战利品
- 在创造模式物品栏加入 4 种预设战利品生成器
- 加入染梦遗迹通用战利品表
- 加入渔夫小屋战利品表
- 加入野餐篮战利品表
- 加入 4 张深海秘宝战利品表（普通/super × 主世界/染梦），通过 datagen 生成
- 为容器方块添加含战利品表时首次开启的行为

**钓鱼系统**
- 搬运钓鱼战利品事件（`FishingLootsHandler`）：钓鱼时根据幸运值、天气、月相、海之眷顾等级等因素，在主世界海洋群系概率掉落深海秘宝，在染梦冻洋概率掉落染梦深海秘宝
- 修复原版 NBT 堆叠问题：宝匣不再存储 `deep_treasure_luck` 浮点值，同类型宝匣可正常堆叠
- 宝匣开启接入玩家幸运属性（`LootContextParamSets.CHEST` + `THIS_ENTITY`），`bonus_rolls: 0.1` 使幸运值影响战利品抽取次数
- 预留星愿钓竿（`star_wish_rod`）接口，待搬运后激活 luck ×2.2 与 10% super 概率

**技能系统**
- 添加瞬身术技能：按 C 键向视线方向冲刺，附带短暂回避无敌帧
- 瞬身术拥有 3 个自定义属性（冷却/消耗/距离），可通过装备和效果叠加
- 回避 buff 成功抵消伤害时触发粒子爆发、音效和冲刺特效
- 添加对应按键绑定、网络数据包和伤害事件处理

**系统**
- 添加环境 SAN 值影响和融梦能量值限制机制
- 封装 SAN 值和融梦能量值的相关方法
- 重排物品组的创造模式物品栏，与植物分类与原版一致

### 修复
- 修复染梦维度群系错位——海洋与陆地位置混淆
- 修正染梦睡莲与染梦荷花的地图颜色为粉色
- 修复染梦裂隙和寻梦者的永恒书卷会被流体冲掉的问题
- 修正北海若引潮剑的命名规范
- 修复部分错误配方
- 赤荆棘加入路径类型覆写，让生物尝试避开
- 修复 `PasterDreamDrinkItem` 中 `FluidContainerRegistry.getEntryForFillToEmpty()` 返回 null 导致 NPE 崩溃


### 重构
- 瞬身术相关类重命名为 Blink（TeleportationSkill → BlinkSkill 等）
- 移除地物映射，完善项目结构文档
- 重构声音系统并重命名相关物品
- 重构染梦群系配置代码结构
- 重构晶芽生成配置以支持不同地面类型
- 重构理智光环为属性驱动架构：新增 SAN_VARIABILITY 属性，装备/效果/环境通过属性修饰符叠加 San 变化率
- 理智环境修正独立为 SanEnvironmentModifier 模块，移除原作夜间强制扣 San 的昼夜修正
- 光照和群系修正保留，低 San debuff 惩罚效果留待后续重新设计
