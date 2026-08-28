package com.pasterdream.pasterdreammod;

import com.pasterdream.pasterdreammod.helper.BossLimitProfile;
import com.pasterdream.pasterdreammod.helper.BossLimitValues;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    // === 时之沙 ===
    private static final ForgeConfigSpec.IntValue TIME_OF_SAND_COOLDOWN = BUILDER
            .comment("时之沙切换昼夜的冷却时间（秒），默认 300 秒（5 分钟）")
            .defineInRange("timeOfSandCooldownSeconds", 300, 0, Integer.MAX_VALUE);

    // === 怀中御守 ===
    private static final ForgeConfigSpec.IntValue KAICHU_OMAMORI_COOLDOWN = BUILDER
            .comment("怀中御守冷却时间（秒），默认 24 秒")
            .defineInRange("KaichuOmamoriCooldownSeconds", 24, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue FOX_FIRE_LIFETIME = BUILDER
            .comment("狐火立场持续时间（秒），默认 15 秒")
            .defineInRange("FoxFireLifetimeSeconds", 15, 0, Integer.MAX_VALUE);

    // === 冶梦莲转化 ===
    private static final ForgeConfigSpec.BooleanValue DESTROY_DESK_ON_DREAMING_LOTUS_CONVERSION = BUILDER
            .comment("冶梦莲转化为迷梦冶梦莲时是否摧毁染梦书桌，默认 true")
            .define("destroyDeskOnDreamingLotusConversion", true);

    // === 雪绒花转化 ===
    private static final ForgeConfigSpec.BooleanValue DESTROY_DESK_ON_EDELWEISS_CONVERSION = BUILDER
            .comment("雪绒花转化为凌冽雪绒花时是否摧毁染梦书桌，默认 true")
            .define("destroyDeskOnEdelweissConversion", true);

    // ===『天丛云』草薙 ===
    private static final ForgeConfigSpec.IntValue NEED_KILL_ENEMY = BUILDER
            .comment("『天丛云』草薙升级需要的亡魂数量，默认200")
            .defineInRange("the_number_of_kill_enemy_to_evolve", 200, 1, Integer.MAX_VALUE);

    // === 预言卡配置 ===

    //平衡
    private static final ForgeConfigSpec.IntValue MIN_TAKE_EFFECT_DURATION = BUILDER
            .comment("""
                    平衡预言卡：药水等级翻倍/时间减半时，\
                    
                    低于此时长的效果不会被处理，\
                    
                    用于跳过模组饰品提供的永久药水效果。\
                    
                    默认：30秒""")
            .defineInRange("min_take_effect_duration", 30, 1, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue MAX_TAKE_EFFECT_DURATION = BUILDER
            .comment("""
                    平衡预言卡：药水等级翻倍/时间减半时，\
                    
                    高于此时长的效果不会被处理，\
                    
                    用于跳过模组饰品提供的永久药水效果。\
                    
                    默认：1小时（3600秒）""")
            .defineInRange("max_take_effect_duration", 3600, 1, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue MAX_LEVEL = BUILDER
            .comment("高于该等级的药水效果平衡预言卡不会生效，默认255级（不限制）")
            .defineInRange("max_level", 255, 1, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> BALANCE_ALLOWED_EFFECTS = BUILDER
            .comment("平衡预言卡允许翻倍的药水效果 ID 列表（格式：modid:effect_id），"
                    + "\n例：minecraft:regeneration 为生命恢复，minecraft:speed 为速度"
                    + "\n支持模组药水，留空则允许所有")
            .defineListAllowEmpty("balance_allowed_effects",
                    List.of("minecraft:regeneration","minecraft:speed","minecraft:strength","minecraft:luck",
                            "minecraft:jump_boost","minecraft:health_boost","pasterdream:cook"),
                    obj -> obj instanceof String);

    //罪恶
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> SIN_INSTAKILL_ENTITIES = BUILDER
            .comment("罪之预言卡直接秒杀的实体类型 ID 列表（格式：modid:entity_id），"
                    + "\n例：minecraft:vex 为恼鬼，minecraft:creeper 为苦力怕"
                    + "\n支持模组实体。小僵尸（isBaby）的秒杀逻辑为硬编码，不在此列表中。")
            .defineListAllowEmpty("sin_instakill_entities",
                    List.of("minecraft:vex", "minecraft:bat", "minecraft:endermite",
                            "minecraft:silverfish", "minecraft:creeper","minecraft:cave_spider","alexsmobs:centipede_head",
                            "alexsmobs:centipede_body","alexsmobs:centipede_tail","alexsmobs:crimson_mosquito",
                            "alexsmobs:seagull","iceandfire:pixie","twilightforest:pinch_beetle","born_in_chaos_v1:maggot",
                            "born_in_chaos_v1:diamond_termite","born_in_chaos_v1:firelight"),
                    obj -> obj instanceof String);

    //守护
    private static final ForgeConfigSpec.DoubleValue HEALTH_PERCENT = BUILDER
            .comment("守护效果触发时，需要伤害为最大生命值的占比，默认0.3（30%）")
            .defineInRange("health_percent_guard_need", 0.3, 0.0,1.0);

    private static final ForgeConfigSpec.DoubleValue RESIST_DAMAGE = BUILDER
            .comment("守护效果触发时超过最大生命值部分减伤比例，默认0.6（60%）")
            .defineInRange("resist_damage", 0.6, 0.0,1.0);

    //墓园
    private static final ForgeConfigSpec.DoubleValue GRAVEYARD_DAMAGE = BUILDER
            .comment("墓园预言卡伤害，默认50")
            .defineInRange("graveyard_damage", 50.0, 0.0, Double.MAX_VALUE);

    //纷争
    private static final ForgeConfigSpec.DoubleValue CONFLICT_MARK_RANGE = BUILDER
            .comment("纷争预言卡标记后，敌对生物检测被标记实体的范围（格），默认 16")
            .defineInRange("conflict_mark_range", 16.0, 1.0, 128.0);

    private static final ForgeConfigSpec.DoubleValue CONFLICT_CARD_REACH = BUILDER
            .comment("纷争预言卡右键选中实体的最远距离（格），默认 32")
            .defineInRange("conflict_card_reach", 32.0, 4.0, 128.0);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> CONFLICT_MARK_BLACKLIST = BUILDER
            .comment("纷争预言卡无法标记的实体类型 ID 列表（格式：modid:entity_id），"
                    + "\n例：minecraft:iron_golem 为铁傀儡"
                    + "\n用于排除实验假人等不应被标记的实体")
            .defineListAllowEmpty("conflict_mark_blacklist",
                    List.of("dummmmmmy:target_dummy"),
                    obj -> obj instanceof String);

    // === 帕秋莉宝典 ===
    private static final ForgeConfigSpec.BooleanValue GIVE_PATCHOULI_BOOK_ON_FIRST_JOIN = BUILDER
            .comment("玩家首次加入世界时是否发放帕秋莉宝典（需要安装帕秋莉模组才生效），默认 true")
            .define("givePatchouliBookOnFirstJoin", true);

    // === 融梦水晶箱 ===
    private static final ForgeConfigSpec.DoubleValue MELT_DREAM_CRYSTAL_CHEST_NORMAL_Y_SPEED = BUILDER
            .comment("融梦水晶箱普通品质物品抛出速度，默认 0.375")
            .defineInRange("meltDreamCrystalChestNormalYSpeed", 0.375, 0.0, 5.0);

    private static final ForgeConfigSpec.DoubleValue MELT_DREAM_CRYSTAL_CHEST_RARE_Y_SPEED = BUILDER
            .comment("融梦水晶箱稀有品质物品抛出速度，默认 0.375")
            .defineInRange("meltDreamCrystalChestRareYSpeed", 0.375, 0.0, 5.0);

    private static final ForgeConfigSpec.DoubleValue MELT_DREAM_CRYSTAL_CHEST_LEGEND_Y_SPEED = BUILDER
            .comment("融梦水晶箱传奇品质物品抛出速度，默认 0.375")
            .defineInRange("meltDreamCrystalChestLegendYSpeed", 0.375, 0.0, 5.0);

    // === SAN 阈值（ratio = 当前SAN / 最大SAN） ===
    private static final ForgeConfigSpec.DoubleValue SAN_CHEER_UP_THRESHOLD = BUILDER
            .comment("振奋效果触发阈值（ratio >= 该值），默认 0.9（90%）")
            .defineInRange("sanCheerUpThreshold", 0.9, 0.0, 1.0);

    private static final ForgeConfigSpec.DoubleValue SAN_LETHARGY_UPPER_THRESHOLD = BUILDER
            .comment("不振效果上界（ratio < 该值且 >= 下界时触发），默认 0.6（60%）")
            .defineInRange("sanLethargyUpperThreshold", 0.6, 0.0, 1.0);

    private static final ForgeConfigSpec.DoubleValue SAN_LETHARGY_LOWER_THRESHOLD = BUILDER
            .comment("不振效果下界 / 恍惚效果上界，默认 0.4（40%）")
            .defineInRange("sanLethargyLowerThreshold", 0.4, 0.0, 1.0);

    private static final ForgeConfigSpec.DoubleValue SAN_TRANCE_LOWER_THRESHOLD = BUILDER
            .comment("恍惚效果下界 / 疯狂效果上界，默认 0.2（20%）")
            .defineInRange("sanTranceLowerThreshold", 0.2, 0.0, 1.0);

    private static final ForgeConfigSpec.DoubleValue SAN_INSANE_LV2_THRESHOLD = BUILDER
            .comment("疯狂 II 触发阈值（ratio < 该值时升级为 Lv2），默认 0.1（10%）")
            .defineInRange("sanInsaneLv2Threshold", 0.1, 0.0, 1.0);

    private static final ForgeConfigSpec.DoubleValue SAN_INSANE_LV3_THRESHOLD = BUILDER
            .comment("疯狂 III 触发阈值（ratio < 该值时升级为 Lv3），默认 0.01（1%）")
            .defineInRange("sanInsaneLv3Threshold", 0.01, 0.0, 1.0);

    // === 融梦能量条 HUD 位置（预设1：左下角） ===
    private static final ForgeConfigSpec.IntValue MELT_DREAM_ENERGY_BAR_X = BUILDER
            .comment("融梦能量条距屏幕左侧的距离（像素），预设1：9")
            .defineInRange("meltDreamEnergyBarX", 9, 0, 3840);

    private static final ForgeConfigSpec.IntValue MELT_DREAM_ENERGY_BAR_Y_FROM_BOTTOM = BUILDER
            .comment("融梦能量条距屏幕底部的距离（像素），预设1：17")
            .defineInRange("meltDreamEnergyBarYFromBottom", 17, 0, 2160);

    // === 融梦能量条显示配置 ===
    private static final ForgeConfigSpec.IntValue MELT_DREAM_ENERGY_BAR_DECIMAL_PLACES = BUILDER
            .comment("融梦能量条数值小数位数（0~4），默认 1")
            .defineInRange("meltDreamEnergyBarDecimalPlaces", 1, 0, 4);

    private static final ForgeConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_TEXT_CENTERED = BUILDER
            .comment("融梦能量条数值是否居中在bar正中央，默认 false（在bar上方）")
            .define("meltDreamEnergyBarTextCentered", false);

    private static final ForgeConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_ALWAYS_SHOW = BUILDER
            .comment("融梦能量条是否常态显示，默认 true。设为 false 时以下两项生效")
            .define("meltDreamEnergyBarAlwaysShow", true);

    private static final ForgeConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_SHOW_ON_CHANGE = BUILDER
            .comment("融梦能量条：数值变化时显示（仅 alwaysShow=false 时生效），默认 true")
            .define("meltDreamEnergyBarShowOnChange", true);

    private static final ForgeConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_SHOW_ON_SNEAK = BUILDER
            .comment("融梦能量条：蹲下时显示（仅 alwaysShow=false 时生效），默认 true")
            .define("meltDreamEnergyBarShowOnSneak", true);

    // === 精神值条 HUD 位置（预设1：右下角） ===
    private static final ForgeConfigSpec.IntValue SAN_BAR_X_FROM_RIGHT = BUILDER
            .comment("精神值条距屏幕右侧的距离（像素），预设1：34")
            .defineInRange("sanBarXFromRight", 34, 0, 3840);

    private static final ForgeConfigSpec.IntValue SAN_BAR_Y_FROM_BOTTOM = BUILDER
            .comment("精神值条距屏幕底部的距离（像素），预设1：30")
            .defineInRange("sanBarYFromBottom", 30, 0, 2160);

    // === 精神值条预设 ===
    private static final ForgeConfigSpec.IntValue SAN_BAR_PRESET = BUILDER
            .comment("""
                    精神值条显示预设：\

                    1=默认（右下角，显示小数），\

                    2=紧凑（居中于血量与饥饿值之间，缩小，仅显示整数）""")
            .defineInRange("sanBarPreset", 1, 1, 2);

    private static final ForgeConfigSpec.BooleanValue SAN_BAR_PRESET1_SNEAK_PRECISE = BUILDER
            .comment("精神值条预设1：是否仅在下蹲时显示数值，默认 false（始终显示）")
            .define("sanBarPreset1SneakPrecise", false);

    private static final ForgeConfigSpec.BooleanValue SAN_BAR_PRESET1_SNEAK_SHOW_BAR = BUILDER
            .comment("精神值条预设1：是否仅在下蹲时显示SAN条，默认 false（始终显示）")
            .define("sanBarPreset1SneakShowBar", false);

    private static final ForgeConfigSpec.IntValue SAN_BAR_PRESET1_DECIMAL_PLACES = BUILDER
            .comment("精神值条预设1：数值小数位数（0~4），默认 4")
            .defineInRange("sanBarPreset1DecimalPlaces", 4, 0, 4);

    private static final ForgeConfigSpec.DoubleValue SAN_BAR_PRESET2_SCALE = BUILDER
            .comment("精神值条预设2的缩放比例，默认 0.5（28×26 缩小为 14×13）")
            .defineInRange("sanBarPreset2Scale", 0.5, 0.1, 1.5);

    private static final ForgeConfigSpec.DoubleValue SAN_BAR_PRESET2_LOW_THRESHOLD = BUILDER
            .comment("精神值条预设2的低SAN警告阈值（ratio = 当前SAN / 最大SAN），低于该值时数字变红、bar抖动，默认 0.2（20%）")
            .defineInRange("sanBarPreset2LowThreshold", 0.2, 0.0, 1.0);

    private static final ForgeConfigSpec.BooleanValue SAN_BAR_PRESET2_SNEAK_SHOW_IN_CREATIVE = BUILDER
            .comment("精神值条预设2：创造模式下是否仅在下蹲时显示，默认 true")
            .define("sanBarPreset2SneakShowInCreative", true);

    // === 低 San 效果开关 ===
    private static final ForgeConfigSpec.BooleanValue LOW_SAN_OVERLAY = BUILDER
            .comment("低SAN值时的全屏画面叠加效果，默认 true。"
                    + "\n指令 /pasterdreamdebug lowsan overlay 可运行时临时切换，重进存档后按此配置恢复")
            .define("lowSanOverlay", true);

    private static final ForgeConfigSpec.BooleanValue LOW_SAN_JITTER = BUILDER
            .comment("低SAN值时的视角抖动，默认 true。"
                    + "\n指令 /pasterdreamdebug lowsan jitter 可运行时临时切换，重进存档后按此配置恢复")
            .define("lowSanJitter", true);

    private static final ForgeConfigSpec.BooleanValue LOW_SAN_SOUND = BUILDER
            .comment("低SAN值时的循环音效，默认 true。"
                    + "\n指令 /pasterdreamdebug lowsan sound 可运行时临时切换，重进存档后按此配置恢复")
            .define("lowSanSound", true);

    // === 暗影生物独立难度 ===
    private static final ForgeConfigSpec.ConfigValue<List<? extends Double>> SHADOW_HEALTH_MULTIPLIERS = BUILDER
            .comment("暗影生物血量倍率，按难度等级排列 [极简单, 简单, 普通, 困难]，默认 [0.5, 1.0, 1.5, 2.0]")
            .define("shadowHealthMultipliers", List.of(0.5, 1.0, 1.5, 2.0));

    private static final ForgeConfigSpec.ConfigValue<List<? extends Double>> SHADOW_ATTACK_MULTIPLIERS = BUILDER
            .comment("暗影生物攻击倍率，按难度等级排列 [极简单, 简单, 普通, 困难]，默认 [0.5, 1.0, 1.3, 1.8]")
            .define("shadowAttackMultipliers", List.of(0.5, 1.0, 1.3, 1.8));

    private static final ForgeConfigSpec.ConfigValue<List<? extends Double>> SHADOW_SPEED_MULTIPLIERS = BUILDER
            .comment("暗影生物移速倍率，按难度等级排列 [极简单, 简单, 普通, 困难]，默认 [0.7, 1.0, 1.1, 1.25]")
            .define("shadowSpeedMultipliers", List.of(0.7, 1.0, 1.1, 1.25));

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> SHADOW_SPECIAL_SKILLS_ENABLED = BUILDER
            .comment("各难度是否启用暗影生物特殊技能（阴影傀儡AoE、悲泣尖啸怨魂召唤等），按难度等级排列，默认 [false, true, true, true]")
            .defineListAllowEmpty("shadowSpecialSkillsEnabled", List.of("false", "true", "true", "true"),
                    obj -> obj instanceof String);

    private static final ForgeConfigSpec.DoubleValue SHADOW_GOLEM_SKILL_DAMAGE = BUILDER
            .comment("阴影傀儡 AoE 技能基础伤害，默认 15")
            .defineInRange("shadowGolemSkillDamage", 15.0, 0.0, 1000.0);

    private static final ForgeConfigSpec.DoubleValue SHADOW_HAND_SAN_DRAIN = BUILDER
            .comment("暗影之手每次命中扣除的 SAN 基础值，默认 0.02")
            .defineInRange("shadowHandSanDrain", 0.02, 0.0, 100.0);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Double>> SHADOW_LOOT_MULTIPLIERS = BUILDER
            .comment("暗影生物战利品掉落倍率，按难度等级排列 [极简单, 简单, 普通, 困难]，默认 [1.0, 1.0, 1.5, 2.0]"
                    + "\n每个掉落物以 (倍率-1) 的概率额外复制一份")
            .define("shadowLootMultipliers", List.of(1.0, 1.0, 1.5, 2.0));

    // === BOSS 限伤系统 ===
    private static final ForgeConfigSpec.BooleanValue BOSS_SHADOW_DIFFICULTY_AFFECTS_DAMAGE_CAP = BUILDER
            .comment("暗影难度是否影响 BOSS 限伤值（方案B：难度越高 damageCap 越低），默认 false")
            .define("bossShadowDifficultyAffectsDamageCap", false);

    private static final ForgeConfigSpec.BooleanValue BOSS_DAMAGE_CAP_ENABLED = BUILDER
            .comment("是否启用 BOSS 单次伤害限制（单次受击伤害上限），默认 true。"
                    + "\n设为 false 时，单发上限与 DPS 限制均不生效")
            .define("bossDamageCapEnabled", true);

    private static final ForgeConfigSpec.BooleanValue BOSS_DPS_CAP_ENABLED = BUILDER
            .comment("是否启用 BOSS DPS 限制（每秒总伤害上限），默认 false。"
                    + "\n仅在启用单次伤害限制（bossDamageCapEnabled=true）时生效")
            .define("bossDpsCapEnabled", false);

    private static final ForgeConfigSpec.BooleanValue BOSS_RANGE_CAP_ENABLED = BUILDER
            .comment("是否启用 BOSS 远距离减伤（超出限伤距离后伤害线性衰减），默认 false")
            .define("bossRangeCapEnabled", false);

    private static final ForgeConfigSpec.DoubleValue BOSS_DAMAGE_CAP = BUILDER
            .comment("BOSS 单次受击伤害上限（同时也是 DPS 桶容量），默认 40")
            .defineInRange("bossDamageCap", 40.0, 1.0, 1_000_000.0);

    private static final ForgeConfigSpec.DoubleValue BOSS_DPS_CAP = BUILDER
            .comment("BOSS DPS 桶每秒恢复量，默认 200")
            .defineInRange("bossDpsCap", 200.0, 1.0, 1_000_000.0);

    private static final ForgeConfigSpec.DoubleValue BOSS_RANGE_CAP = BUILDER
            .comment("BOSS 限伤距离衰减起始距离（格），超过此距离伤害线性衰减，默认 12")
            .defineInRange("bossRangeCap", 12.0, 1.0, 256.0);

    // === BOSS 限伤系统 · 亚伦柯斯之触独立配置 ===
    private static final ForgeConfigSpec.BooleanValue AARONCOS_DAMAGE_CAP_INDEPENDENT = BUILDER
            .comment("亚伦柯斯之触（左右手）是否使用独立限伤配置（否则沿用上方全局 BOSS 限伤配置），默认 true")
            .define("aaroncosDamageCapIndependent", true);

    private static final ForgeConfigSpec.BooleanValue AARONCOS_SHADOW_DIFFICULTY_AFFECTS_DAMAGE_CAP = BUILDER
            .comment("亚伦柯斯之触：暗影难度是否影响限伤值（难度越高 damageCap 越低），默认 false")
            .define("aaroncosShadowDifficultyAffectsDamageCap", false);

    private static final ForgeConfigSpec.BooleanValue AARONCOS_DAMAGE_CAP_ENABLED = BUILDER
            .comment("亚伦柯斯之触：是否启用单次伤害限制（单次受击伤害上限），默认 true。"
                    + "\n设为 false 时，单发上限与 DPS 限制均不生效")
            .define("aaroncosDamageCapEnabled", true);

    private static final ForgeConfigSpec.BooleanValue AARONCOS_DPS_CAP_ENABLED = BUILDER
            .comment("亚伦柯斯之触：是否启用 DPS 限制（每秒总伤害上限），默认 true。"
                    + "\n仅在启用单次伤害限制（aaroncosDamageCapEnabled=true）时生效")
            .define("aaroncosDpsCapEnabled", true);

    private static final ForgeConfigSpec.BooleanValue AARONCOS_RANGE_CAP_ENABLED = BUILDER
            .comment("亚伦柯斯之触：是否启用远距离减伤（超出限伤距离后伤害线性衰减），默认 false")
            .define("aaroncosRangeCapEnabled", false);

    private static final ForgeConfigSpec.DoubleValue AARONCOS_DAMAGE_CAP = BUILDER
            .comment("亚伦柯斯之触：单次受击伤害上限（同时也是 DPS 桶容量），默认 35")
            .defineInRange("aaroncosDamageCap", 35.0, 1.0, 1_000_000.0);

    private static final ForgeConfigSpec.DoubleValue AARONCOS_DPS_CAP = BUILDER
            .comment("亚伦柯斯之触：DPS 桶每秒恢复量，默认 200")
            .defineInRange("aaroncosDpsCap", 200.0, 1.0, 1_000_000.0);

    private static final ForgeConfigSpec.DoubleValue AARONCOS_RANGE_CAP = BUILDER
            .comment("亚伦柯斯之触：限伤距离衰减起始距离（格），默认 12")
            .defineInRange("aaroncosRangeCap", 12.0, 1.0, 256.0);

    // === BOSS 限伤系统 · 破风骑士独立配置 ===
    private static final ForgeConfigSpec.BooleanValue WIND_KNIGHT_DAMAGE_CAP_INDEPENDENT = BUILDER
            .comment("破风骑士是否使用独立限伤配置（否则沿用上方全局 BOSS 限伤配置），默认 false")
            .define("windKnightDamageCapIndependent", false);

    private static final ForgeConfigSpec.BooleanValue WIND_KNIGHT_DAMAGE_CAP_ENABLED = BUILDER
            .comment("破风骑士：是否启用单次伤害限制（单次受击伤害上限），默认 true。"
                    + "\n设为 false 时，单发上限与 DPS 限制均不生效")
            .define("windKnightDamageCapEnabled", true);

    private static final ForgeConfigSpec.BooleanValue WIND_KNIGHT_DPS_CAP_ENABLED = BUILDER
            .comment("破风骑士：是否启用 DPS 限制（每秒总伤害上限），默认 true。"
                    + "\n仅在启用单次伤害限制（windKnightDamageCapEnabled=true）时生效")
            .define("windKnightDpsCapEnabled", true);

    private static final ForgeConfigSpec.BooleanValue WIND_KNIGHT_RANGE_CAP_ENABLED = BUILDER
            .comment("破风骑士：是否启用远距离减伤（超出限伤距离后伤害线性衰减），默认 true")
            .define("windKnightRangeCapEnabled", true);

    private static final ForgeConfigSpec.DoubleValue WIND_KNIGHT_DAMAGE_CAP = BUILDER
            .comment("破风骑士：单次受击伤害上限（同时也是 DPS 桶容量），默认 20")
            .defineInRange("windKnightDamageCap", 20.0, 1.0, 1_000_000.0);

    private static final ForgeConfigSpec.DoubleValue WIND_KNIGHT_DPS_CAP = BUILDER
            .comment("破风骑士：DPS 桶每秒恢复量，默认 200")
            .defineInRange("windKnightDpsCap", 200.0, 1.0, 1_000_000.0);

    private static final ForgeConfigSpec.DoubleValue WIND_KNIGHT_RANGE_CAP = BUILDER
            .comment("破风骑士：限伤距离衰减起始距离（格），默认 8")
            .defineInRange("windKnightRangeCap", 8.0, 1.0, 256.0);

    private static final ForgeConfigSpec.DoubleValue WIND_KNIGHT_LIGHTNING_CHANCE = BUILDER
            .comment("破风骑士普攻命中后在目标头顶召唤落雷（雷云实体所使用的落雷投射物）的概率，默认 0.5（50%），允许最小值为0，最大值为1")
            .defineInRange("windKnightLightningChance", 0.5, 0.0, 1.0);

    private static final ForgeConfigSpec.BooleanValue WIND_KNIGHT_PASSIVE_INVULNERABLE_ENABLED = BUILDER
            .comment("破风骑士：是否启用受击后概率获得短暂无敌的被动，默认 true")
            .define("windKnightPassiveInvulnerableEnabled", true);

    private static final ForgeConfigSpec.DoubleValue WIND_KNIGHT_PASSIVE_INVULNERABLE_CHANCE = BUILDER
            .comment("破风骑士：被动无敌触发概率（受击后获得无敌的概率），默认 0.2（20%），允许最小值为0，最大值为1")
            .defineInRange("windKnightPassiveInvulnerableChance", 0.2, 0.0, 1.0);

    private static final ForgeConfigSpec.IntValue WIND_KNIGHT_PASSIVE_INVULNERABLE_TICKS = BUILDER
            .comment("破风骑士：被动获得的无敌时长（tick，20 tick = 1 秒），默认 20")
            .defineInRange("windKnightPassiveInvulnerableTicks", 20, 1, Integer.MAX_VALUE);

    // === 亚伦柯斯之触 ===
    private static final ForgeConfigSpec.BooleanValue AARONCOS_TOUCH_IMMUNE_TO_NEGATIVE_EFFECTS = BUILDER
            .comment("亚伦柯斯之触（左右手）是否免疫负面状态效果（有害类效果，如中毒、虚弱、缓慢等），默认 true")
            .define("aaroncosTouchImmuneToNegativeEffects", true);

    // === 大便携储物袋抓取生物 ===
    private static final ForgeConfigSpec.BooleanValue CREATURE_CAPTURE_ENABLED = BUILDER
            .comment("是否启用大便携储物袋（LargeStorageBag）抓取生物的功能，默认 true")
            .define("creatureCaptureEnabled", true);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> CREATURE_CAPTURE_ENTITIES = BUILDER
            .comment("大便携储物袋可抓取的实体 ID 列表（格式：modid:entity_id），"
                    + "\n例：minecraft:chicken 为鸡，pasterdream:pink_chicken 为粉红鸡"
                    + "\n仅 Shift+右键时可抓取，无法抓取 Boss 生物（凋零、末影龙）")
            .defineListAllowEmpty("creatureCaptureEntities",
                    List.of("pasterdream:pink_chicken", "pasterdream:pink_slime",
                            "minecraft:chicken", "minecraft:slime", "minecraft:silverfish",
                            "minecraft:vex", "minecraft:endermite", "minecraft:frog",
                            "minecraft:tadpole", "minecraft:tropical_fish", "minecraft:pufferfish",
                            "minecraft:salmon", "minecraft:cod", "minecraft:wolf", "minecraft:parrot"),
                    obj -> obj instanceof String);

    // === 低理智刷怪（四区间制） ===
    // 区间边界沿用上方 SAN 阈值，此处仅配置各区间的刷怪概率与实体权重
    // 概率为 4 个值，按暗影难度排列 [极简单, 简单, 普通, 困难]
    // 实体格式: "modid:entity_id:weight"，weight 为 0~1 的相对权重

    // 安全理智区间（上界由 lowSanSpawnHighThresholds 按难度控制）
    private static final ForgeConfigSpec.ConfigValue<List<? extends Double>> LOW_SAN_SPAWN_HIGH_PROBS = BUILDER
            .comment("安全理智区间每 tick 刷怪概率 [极简单, 简单, 普通, 困难]，默认 [0, 0, 0, 0]")
            .define("lowSanSpawnHighProbs", List.of(0.0, 0.0, 0.0, 0.0));
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> LOW_SAN_SPAWN_HIGH_ENTITIES = BUILDER
            .comment("振奋区间可生成的实体及权重，默认空")
            .defineListAllowEmpty("lowSanSpawnHighEntities", List.of(), obj -> obj instanceof String);

    // 中理智区间 (sanLethargyLowerThreshold <= ratio < sanLethargyUpperThreshold)
    private static final ForgeConfigSpec.ConfigValue<List<? extends Double>> LOW_SAN_SPAWN_MEDIUM_PROBS = BUILDER
            .comment("不振区间每 tick 刷怪概率 [极简单, 简单, 普通, 困难]，简单难度不刷，默认 [0, 0, 0.001, 0.002]")
            .define("lowSanSpawnMediumProbs", List.of(0.0, 0.0, 0.001, 0.002));
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> LOW_SAN_SPAWN_MEDIUM_ENTITIES = BUILDER
            .comment("不振区间可生成的实体及权重，默认虚弱恐怖尖喙:0.15, 暗影之手:0.3")
            .defineListAllowEmpty("lowSanSpawnMediumEntities",
                    List.of("pasterdream:weakeness_terrorbeak:0.15", "pasterdream:shadow_hand:0.3"),
                    obj -> obj instanceof String);

    // 低理智区间 (sanTranceLowerThreshold <= ratio < sanLethargyLowerThreshold)
    private static final ForgeConfigSpec.ConfigValue<List<? extends Double>> LOW_SAN_SPAWN_LOW_PROBS = BUILDER
            .comment("恍惚区间每 tick 刷怪概率 [极简单, 简单, 普通, 困难]，简单难度不刷，默认 [0, 0, 0.002, 0.004]")
            .define("lowSanSpawnLowProbs", List.of(0.0, 0.0, 0.002, 0.004));
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> LOW_SAN_SPAWN_LOW_ENTITIES = BUILDER
            .comment("恍惚区间可生成的实体及权重，默认虚弱恐怖尖喙:0.4, 暗影之手:0.2")
            .defineListAllowEmpty("lowSanSpawnLowEntities",
                    List.of("pasterdream:weakeness_terrorbeak:0.4", "pasterdream:shadow_hand:0.2",
                            "pasterdream:terrorbeak:0.1"),
                    obj -> obj instanceof String);

    // 极低理智区间 (ratio < sanTranceLowerThreshold)
    private static final ForgeConfigSpec.ConfigValue<List<? extends Double>> LOW_SAN_SPAWN_CRITICAL_PROBS = BUILDER
            .comment("极低理智区间每 tick 刷怪概率 [极简单, 简单, 普通, 困难]，默认 [0, 0.005, 0.005, 0.006]")
            .define("lowSanSpawnCriticalProbs", List.of(0.0, 0.005, 0.005, 0.006));
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> LOW_SAN_SPAWN_CRITICAL_ENTITIES = BUILDER
            .comment("极低理智区间可生成的实体及权重，默认疯狂恐怖尖喙:0.25, 恐怖尖喙:0.2, 虚弱恐怖尖喙:0.15, 暗影之手:0.15")
            .defineListAllowEmpty("lowSanSpawnCriticalEntities",
                    List.of("pasterdream:crazy_terrorbeak:0.25", "pasterdream:terrorbeak:0.2",
                            "pasterdream:weakeness_terrorbeak:0.15", "pasterdream:shadow_hand:0.15"),
                    obj -> obj instanceof String);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Double>> LOW_SAN_SPAWN_HIGH_THRESHOLDS = BUILDER
            .comment("各暗影难度下安全理智区间的下界（低于此值开始刷怪）[极简单, 简单, 普通, 困难]，默认 [0.0, 0.2, 0.6, 0.825]")
            .define("lowSanSpawnHighThresholds", List.of(0.0, 0.2, 0.6, 0.825));

    private static final ForgeConfigSpec.IntValue LOW_SAN_SPAWN_MAX_LIGHT = BUILDER
            .comment("低理智刷怪允许的最大亮度（方块光照），默认 5。设为 15 则无光照限制")
            .defineInRange("lowSanSpawnMaxLight", 5, 0, 15);

    private static final ForgeConfigSpec.DoubleValue LOW_SAN_SPAWN_RADIUS_MIN = BUILDER
            .comment("低理智刷怪距玩家的最小距离（格），默认 7")
            .defineInRange("lowSanSpawnRadiusMin", 7.0, 0.0, 64.0);

    private static final ForgeConfigSpec.DoubleValue LOW_SAN_SPAWN_RADIUS_MAX = BUILDER
            .comment("低理智刷怪距玩家的最大距离（格），默认 12")
            .defineInRange("lowSanSpawnRadiusMax", 12.0, 1.0, 64.0);

    private static final ForgeConfigSpec.IntValue LOW_SAN_SPAWN_MAX_NEARBY = BUILDER
            .comment("低理智刷怪时玩家周围允许存在的最大暗影生物数量，默认 2（饥荒上限）")
            .defineInRange("lowSanSpawnMaxNearby", 2, 0, 20);

    private static final ForgeConfigSpec.BooleanValue LOW_SAN_SPAWN_REQUIRES_SPECIAL_SKILL = BUILDER
            .comment("低理智刷怪是否受暗影难度特殊技能开关控制，默认 true")
            .define("lowSanSpawnRequiresSpecialSkill", true);


    // === 卡莱调料瓶 ===
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> CALAIS_SPICE_BOTTLES = BUILDER
            .comment("卡莱调料瓶消耗层数时可获得的随机增益效果 ID 列表（格式：modid:effect_id），"
                    + "\n例：minecraft:regeneration 为生命恢复，minecraft:speed 为速度"
                    + "\n支持模组药水")
            .defineListAllowEmpty("calaisSpiceBottleBuffs",
                    List.of("minecraft:speed", "minecraft:haste", "minecraft:strength",
                            "minecraft:regeneration", "minecraft:resistance", "minecraft:fire_resistance",
                            "minecraft:absorption", "pasterdream:rest", "pasterdream:cook"),
                    obj -> obj instanceof String);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Double>> CALAIS_SPICE_BOTTLE_WEIGHTS = BUILDER
            .comment("卡莱调料瓶消耗层数时各效果的触发权重（6 个值，依次为：随机增益、san恢复、生命恢复、随机负面、回避增益、doll音效），"
                    + "\n设为 0 则禁用该效果")
            .define("calaisSpiceBottleWeights", List.of(4.0, 4.0, 2.0, 3.0, 1.0, 1.0));

    private static final ForgeConfigSpec.DoubleValue CALAIS_SPICE_BOTTLE_HEAL_MIN = BUILDER
            .comment("卡莱调料瓶生命恢复最小值（半心），默认 1.0")
            .defineInRange("calaisSpiceBottleHealMin", 1.0, 0.5, 20.0);
    private static final ForgeConfigSpec.DoubleValue CALAIS_SPICE_BOTTLE_HEAL_MAX = BUILDER
            .comment("卡莱调料瓶生命恢复最大值（半心），默认 2.0")
            .defineInRange("calaisSpiceBottleHealMax", 2.0, 0.5, 20.0);

    private static final ForgeConfigSpec.DoubleValue CALAIS_SPICE_BOTTLE_SAN_MIN = BUILDER
            .comment("卡莱调料瓶 SAN 恢复最小值，默认 0.5")
            .defineInRange("calaisSpiceBottleSanMin", 0.5, 0.0, 100.0);
    private static final ForgeConfigSpec.DoubleValue CALAIS_SPICE_BOTTLE_SAN_MAX = BUILDER
            .comment("卡莱调料瓶 SAN 恢复最大值，默认 3.0")
            .defineInRange("calaisSpiceBottleSanMax", 3.0, 0.0, 100.0);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> CALAIS_SPICE_BOTTLE_DEBUFFS = BUILDER
            .comment("卡莱调料瓶消耗层数时可施加给敌人的随机负面效果 ID 列表（格式：modid:effect_id），"
                    + "\n例：minecraft:slowness 为缓慢，minecraft:weakness 为虚弱")
            .defineListAllowEmpty("calaisSpiceBottleDebuffs",
                    List.of("pasterdream:confusion"),
                    obj -> obj instanceof String);
    private static final ForgeConfigSpec.IntValue CALAIS_SPICE_BOTTLE_DEBUFF_DURATION = BUILDER
            .comment("卡莱调料瓶负面效果的持续时间（tick），默认 100（5 秒）")
            .defineInRange("calaisSpiceBottleDebuffDuration", 100, 0, 6000);
    private static final ForgeConfigSpec.IntValue CALAIS_SPICE_BOTTLE_DEBUFF_AMPLIFIER = BUILDER
            .comment("卡莱调料瓶负面效果的等级（0=I, 1=II, ...），默认 0")
            .defineInRange("calaisSpiceBottleDebuffAmplifier", 0, 0, 255);

    private static final ForgeConfigSpec.IntValue CALAIS_SPICE_BOTTLE_EVASION_DURATION = BUILDER
            .comment("卡莱调料瓶回避增益持续时间（tick），默认 20（1 秒）")
            .defineInRange("calaisSpiceBottleEvasionDuration", 20, 0, 200);

    // === 村民交易 ===
    private static final ForgeConfigSpec.DoubleValue TOOLSMITH_BLUEPRINT_TRADE_CHANCE = BUILDER
            .comment("工具匠专家（5级）出售精铸工坊蓝图的概率，默认 1.0（100%）")
            .defineInRange("toolsmithBlueprintTradeChance", 1.0, 0.0, 1.0);

    // === 鬼魂之面 ===
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> GHOST_FACE_PROJECTILE_BLACKLIST = BUILDER
            .comment("鬼魂之面不生效的投射物实体类型 ID 列表（格式：modid:entity_id），"
                    + "\n例：supplementaries:slingshot_projectile 为 Supplementary 模组的弹弓弹射物"
                    + "\n支持模组投射物，默认屏蔽弹弓弹射物以避免与弹弓自身机制冲突")
            .defineListAllowEmpty("ghostFaceProjectileBlacklist",
                    List.of("supplementaries:slingshot_projectile","tide:fishing_bobber"),
                    obj -> obj instanceof String);

    private static final ForgeConfigSpec.IntValue GHOST_FACE_CLONE_COOLDOWN = BUILDER
            .comment("鬼魂之面复制弹幕的内置冷却时间（秒），默认 3 秒，设为 0 可关闭冷却")
            .defineInRange("ghostFaceCloneCooldownSeconds", 3, 0, Integer.MAX_VALUE);

    // === 重生之梦水晶 ===
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> REBIRTH_DREAM_CRYSTAL_LOOT = BUILDER
            .comment("重生之梦水晶 shift+右键 释放遗物中的灵魂时可随机获得的物品 ID 列表（格式：modid:item_id），"
                    + "\n例：pasterdream:melt_dream_crystal_fragment 为融梦水晶碎片")
            .defineListAllowEmpty("rebirthDreamCrystalLoot",
                    List.of("pasterdream:melt_dream_crystal_fragment",
                            "pasterdream:life_crystal",
                            "pasterdream:white_crystal",
                            "pasterdream:soul_gem_of_akizuki_ayane",
                            "pasterdream:magnifying_glass_of_sherry",
                            "pasterdream:empty_vessel"),
                    obj -> obj instanceof String);

    // === 暮影之笼 · 据点守卫 ===
    private static final ForgeConfigSpec.DoubleValue BASTION_GUARD_GRANT_RADIUS = BUILDER
            .comment("暮影之笼据点守卫事件完成时授予「据点守卫」进度的半径（格），默认 80")
            .defineInRange("bastionGuardGrantRadius", 80.0, 1.0, 256.0);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    // === 时之沙 ===
    public static int timeOfSandCooldownSeconds;

    // === 怀中御守 ===
    public static int KaichuOmamoriCooldownSeconds;
    public static int FoxFireLifetimeSeconds;

    // ===『天丛云』草薙 ===
    public static int TheNumberofKillEnemytoEvolve;

    // === 融梦水晶箱 ===
    public static double meltDreamCrystalChestNormalYSpeed;
    public static double meltDreamCrystalChestRareYSpeed;
    public static double meltDreamCrystalChestLegendYSpeed;

    // === 冶梦莲转化 ===
    public static boolean destroyDeskOnDreamingLotusConversion;

    // === 雪绒花转化 ===
    public static boolean destroyDeskOnEdelweissConversion;

    // === SAN 阈值 ===
    public static double sanCheerUpThreshold;
    public static double sanLethargyUpperThreshold;
    public static double sanLethargyLowerThreshold;
    public static double sanTranceLowerThreshold;
    public static double sanInsaneLv2Threshold;
    public static double sanInsaneLv3Threshold;

    // === 融梦能量条 & 精神值条 HUD 位置 ===
    public static int meltDreamEnergyBarX;
    public static int meltDreamEnergyBarYFromBottom;
    public static int meltDreamEnergyBarDecimalPlaces;
    public static boolean meltDreamEnergyBarTextCentered;
    public static boolean meltDreamEnergyBarAlwaysShow;
    public static boolean meltDreamEnergyBarShowOnChange;
    public static boolean meltDreamEnergyBarShowOnSneak;
    public static int sanBarXFromRight;
    public static int sanBarYFromBottom;
    public static int sanBarPreset;
    public static boolean sanBarPreset1SneakPrecise;
    public static boolean sanBarPreset1SneakShowBar;
    public static int sanBarPreset1DecimalPlaces;
    public static double sanBarPreset2Scale;
    public static double sanBarPreset2LowThreshold;
    public static boolean sanBarPreset2SneakShowInCreative;

    // === 低 San 效果开关（配置项；/pasterdreamdebug lowsan 指令可运行时临时切换） ===
    public static boolean lowSanOverlay;
    public static boolean lowSanJitter;
    public static boolean lowSanSound;

    // === 预言卡配置 ===

    //平衡
    public static int mintakeeffectduration;
    public static int maxtakeeffectduration;
    public static int maxlevel;
    public static List<? extends String> balanceAllowedEffects;

    //卡莱调料瓶
    public static List<? extends String> calaisSpiceBottleBuffs;
    public static List<? extends Double> calaisSpiceBottleWeights;
    public static double calaisSpiceBottleHealMin;
    public static double calaisSpiceBottleHealMax;
    public static double calaisSpiceBottleSanMin;
    public static double calaisSpiceBottleSanMax;
    public static List<? extends String> calaisSpiceBottleDebuffs;
    public static int calaisSpiceBottleDebuffDuration;
    public static int calaisSpiceBottleDebuffAmplifier;
    public static int calaisSpiceBottleEvasionDuration;

    //罪恶
    public static List<? extends String> sinInstakillEntities;
    private static Set<EntityType<?>> cachedSinInstakillTypes = Set.of();

    //墓园
    public static Double graveyarddamage;

    //纷争
    public static Double conflictMarkRange;
    public static Double conflictCardReach;
    public static List<? extends String> conflictMarkBlacklist;
    private static Set<EntityType<?>> cachedConflictMarkBlacklistTypes = Set.of();

    // === 村民交易 ===
    public static double toolsmithBlueprintTradeChance;

    //鬼魂之面
    public static List<? extends String> ghostFaceProjectileBlacklist;
    public static int ghostFaceCloneCooldownSeconds;
    private static Set<ResourceLocation> cachedGhostFaceBlacklistTypes = Set.of();

    /** 卡莱调料瓶随机增益缓存（解析后的 MobEffect 列表） */
    private static List<MobEffect> cachedCalaisSpiceBottleEffects = List.of();

    public static List<MobEffect> getCalaisSpiceBottleEffects() {
        return cachedCalaisSpiceBottleEffects;
    }

    /** 卡莱调料瓶随机负面效果缓存 */
    private static List<MobEffect> cachedCalaisSpiceBottleDebuffs = List.of();

    public static List<MobEffect> getCalaisSpiceBottleDebuffs() {
        return cachedCalaisSpiceBottleDebuffs;
    }

    // === 重生之梦水晶 ===
    public static List<? extends String> rebirthDreamCrystalLoot;

    // === 暮影之笼 · 据点守卫 ===
    public static double bastionGuardGrantRadius;

    /** 重生之梦水晶随机掉落缓存（解析后的 Item 列表） */
    private static List<Item> cachedRebirthDreamCrystalLoot = List.of();

    public static List<Item> getRebirthDreamCrystalLoot() {
        return cachedRebirthDreamCrystalLoot;
    }

    //守护
    public static Double healthpercentguardneed;
    public static Double resistdamage;

    // === 暗影生物独立难度 ===
    public static List<? extends Double> shadowHealthMultipliers;
    public static List<? extends Double> shadowAttackMultipliers;
    public static List<? extends Double> shadowSpeedMultipliers;
    public static List<String> shadowSpecialSkillsEnabled;
    public static double shadowGolemSkillDamage;
    public static double shadowHandSanDrain;
    public static List<? extends Double> shadowLootMultipliers;

    // BOSS 限伤系统
    public static boolean bossShadowDifficultyAffectsDamageCap;
    public static boolean bossDamageCapEnabled;
    public static boolean bossDpsCapEnabled;
    public static boolean bossRangeCapEnabled;
    public static double bossDamageCap;
    public static double bossDpsCap;
    public static double bossRangeCap;
    public static boolean aaroncosTouchImmuneToNegativeEffects;

    // BOSS 限伤系统 · 独立配置
    public static boolean aaroncosDamageCapIndependent;
    public static boolean aaroncosShadowDifficultyAffectsDamageCap;
    public static boolean aaroncosDamageCapEnabled;
    public static boolean aaroncosDpsCapEnabled;
    public static boolean aaroncosRangeCapEnabled;
    public static double aaroncosDamageCap;
    public static double aaroncosDpsCap;
    public static double aaroncosRangeCap;
    public static boolean windKnightDamageCapIndependent;
    public static boolean windKnightDamageCapEnabled;
    public static boolean windKnightDpsCapEnabled;
    public static boolean windKnightRangeCapEnabled;
    public static double windKnightDamageCap;
    public static double windKnightDpsCap;
    public static double windKnightRangeCap;
    public static double windKnightLightningChance;
    public static boolean windKnightPassiveInvulnerableEnabled;
    public static double windKnightPassiveInvulnerableChance;
    public static int windKnightPassiveInvulnerableTicks;

    /**
     * 解析某 BOSS 档位实际生效的限伤配置。
     * 档位未开启独立配置时回落到全局（GLOBAL）配置。
     */
    public static BossLimitValues getBossLimitValues(BossLimitProfile profile) {
        return switch (profile) {
            case AARONCOS -> aaroncosDamageCapIndependent
                    ? new BossLimitValues(
                            aaroncosShadowDifficultyAffectsDamageCap,
                            aaroncosDamageCapEnabled, aaroncosDpsCapEnabled, aaroncosRangeCapEnabled,
                            (float) aaroncosDamageCap, (float) aaroncosDpsCap, aaroncosRangeCap)
                    : getBossLimitValues(BossLimitProfile.GLOBAL);
            case WIND_KNIGHT -> {
                BossLimitValues base = windKnightDamageCapIndependent
                        ? new BossLimitValues(
                                false,
                                windKnightDamageCapEnabled, windKnightDpsCapEnabled, windKnightRangeCapEnabled,
                                (float) windKnightDamageCap, (float) windKnightDpsCap, windKnightRangeCap)
                        : getBossLimitValues(BossLimitProfile.GLOBAL);
                // 破风骑士非暗影生物，不受暗影难度影响，强制关闭难度缩放
                yield new BossLimitValues(false, base.damageCapEnabled(), base.dpsCapEnabled(),
                        base.rangeCapEnabled(), base.damageCap(), base.dpsCap(), base.rangeCap());
            }
            case GLOBAL -> new BossLimitValues(
                    bossShadowDifficultyAffectsDamageCap,
                    bossDamageCapEnabled, bossDpsCapEnabled, bossRangeCapEnabled,
                    (float) bossDamageCap, (float) bossDpsCap, bossRangeCap);
        };
    }

    // === 大便携储物袋抓取生物 ===
    public static boolean creatureCaptureEnabled;
    public static List<? extends String> creatureCaptureEntities;
    private static Set<EntityType<?>> cachedCaptureEntityTypes = Set.of();

    public static boolean isCapturableEntity(EntityType<?> type) {
        return cachedCaptureEntityTypes.contains(type);
    }

    // === 低理智刷怪 ===
    public static List<? extends Double> lowSanSpawnHighProbs;
    public static List<? extends String> lowSanSpawnHighEntities;
    public static List<? extends Double> lowSanSpawnMediumProbs;
    public static List<? extends String> lowSanSpawnMediumEntities;
    public static List<? extends Double> lowSanSpawnLowProbs;
    public static List<? extends String> lowSanSpawnLowEntities;
    public static List<? extends Double> lowSanSpawnCriticalProbs;
    public static List<? extends String> lowSanSpawnCriticalEntities;
    public static List<? extends Double> lowSanSpawnHighThresholds;
    public static int lowSanSpawnMaxLight;
    public static double lowSanSpawnRadiusMin;
    public static double lowSanSpawnRadiusMax;
    public static int lowSanSpawnMaxNearby;
    public static boolean lowSanSpawnRequiresSpecialSkill;

    // === 帕秋莉宝典 ===
    public static boolean givePatchouliBookOnFirstJoin;

    /**
     * 查询指定实体类型是否在罪之预言卡秒杀列表中。
     * 应在服务端调用（缓存基于配置加载时填充）。
     */
    public static boolean isSinInstakillTarget(EntityType<?> type) {
        return cachedSinInstakillTypes.contains(type);
    }

    /**
     * 查询指定实体类型是否在纷争预言卡标记黑名单中。
     */
    public static boolean isConflictMarkBlacklisted(EntityType<?> type) {
        return cachedConflictMarkBlacklistTypes.contains(type);
    }

    /**
     * 查询指定实体类型是否在鬼魂之面投射物黑名单中。
     */
    public static boolean isGhostFaceProjectileBlacklisted(EntityType<?> type) {
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(type);
        return key != null && cachedGhostFaceBlacklistTypes.contains(key);
    }

    private static void rebuildGhostFaceBlacklistCache() {
        Set<ResourceLocation> set = new HashSet<>();
        for (String idStr : ghostFaceProjectileBlacklist) {
            ResourceLocation rl = ResourceLocation.tryParse(idStr);
            if (rl == null) {
                LOGGER.warn("ghostFaceProjectileBlacklist: invalid resource location '{}', skipping", idStr);
                continue;
            }
            set.add(rl);
        }
        cachedGhostFaceBlacklistTypes = Set.copyOf(set);
    }

    private static void rebuildSinInstakillCache() {
        Set<EntityType<?>> set = new HashSet<>();
        for (String idStr : sinInstakillEntities) {
            ResourceLocation rl = ResourceLocation.tryParse(idStr);
            if (rl == null) {
                LOGGER.warn("sin_instakill_entities: invalid resource location '{}', skipping", idStr);
                continue;
            }
            EntityType<?> et = ForgeRegistries.ENTITY_TYPES.getValue(rl);
            if (et == null) {
                LOGGER.warn("sin_instakill_entities: unknown entity type '{}', skipping", idStr);
                continue;
            }
            set.add(et);
        }
        cachedSinInstakillTypes = Set.copyOf(set);
        LOGGER.info("sin_instakill_entities: loaded {} entity types", cachedSinInstakillTypes.size());
    }

    private static void rebuildConflictMarkBlacklistCache() {
        Set<EntityType<?>> set = new HashSet<>();
        for (String idStr : conflictMarkBlacklist) {
            ResourceLocation rl = ResourceLocation.tryParse(idStr);
            if (rl == null) {
                LOGGER.warn("conflict_mark_blacklist: invalid resource location '{}', skipping", idStr);
                continue;
            }
            EntityType<?> et = ForgeRegistries.ENTITY_TYPES.getValue(rl);
            if (et == null) {
                LOGGER.warn("conflict_mark_blacklist: unknown entity type '{}', skipping", idStr);
                continue;
            }
            set.add(et);
        }
        cachedConflictMarkBlacklistTypes = Set.copyOf(set);
        LOGGER.info("conflict_mark_blacklist: loaded {} entity types", cachedConflictMarkBlacklistTypes.size());
    }

    private static void rebuildCaptureEntityCache() {
        Set<EntityType<?>> set = new HashSet<>();
        for (String idStr : creatureCaptureEntities) {
            ResourceLocation rl = ResourceLocation.tryParse(idStr);
            if (rl == null) {
                LOGGER.warn("creatureCaptureEntities: invalid resource location '{}', skipping", idStr);
                continue;
            }
            EntityType<?> et = ForgeRegistries.ENTITY_TYPES.getValue(rl);
            if (et == null) {
                LOGGER.warn("creatureCaptureEntities: unknown entity type '{}', skipping", idStr);
                continue;
            }
            set.add(et);
        }
        cachedCaptureEntityTypes = Set.copyOf(set);
        LOGGER.info("creatureCaptureEntities: loaded {} entity types", cachedCaptureEntityTypes.size());
    }

    private static void rebuildCalaisSpiceBottleCache() {
        List<MobEffect> list = new ArrayList<>();
        for (String idStr : calaisSpiceBottleBuffs) {
            ResourceLocation rl = ResourceLocation.tryParse(idStr);
            if (rl == null) {
                LOGGER.warn("calaisSpiceBottleBuffs: invalid resource location '{}', skipping", idStr);
                continue;
            }
            MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(rl);
            if (effect == null) {
                LOGGER.warn("calaisSpiceBottleBuffs: unknown effect '{}', skipping", idStr);
                continue;
            }
            list.add(effect);
        }
        cachedCalaisSpiceBottleEffects = List.copyOf(list);
        LOGGER.info("calaisSpiceBottleBuffs: loaded {} effects", cachedCalaisSpiceBottleEffects.size());
    }

    private static void rebuildCalaisSpiceBottleDebuffCache() {
        List<MobEffect> list = new ArrayList<>();
        for (String idStr : calaisSpiceBottleDebuffs) {
            ResourceLocation rl = ResourceLocation.tryParse(idStr);
            if (rl == null) {
                LOGGER.warn("calaisSpiceBottleDebuffs: invalid resource location '{}', skipping", idStr);
                continue;
            }
            MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(rl);
            if (effect == null) {
                LOGGER.warn("calaisSpiceBottleDebuffs: unknown effect '{}', skipping", idStr);
                continue;
            }
            list.add(effect);
        }
        cachedCalaisSpiceBottleDebuffs = List.copyOf(list);
        LOGGER.info("calaisSpiceBottleDebuffs: loaded {} effects", cachedCalaisSpiceBottleDebuffs.size());
    }

    private static void rebuildRebirthDreamCrystalLootCache() {
        List<Item> list = new ArrayList<>();
        for (String idStr : rebirthDreamCrystalLoot) {
            ResourceLocation rl = ResourceLocation.tryParse(idStr);
            if (rl == null) {
                LOGGER.warn("rebirthDreamCrystalLoot: invalid resource location '{}', skipping", idStr);
                continue;
            }
            Item item = ForgeRegistries.ITEMS.getValue(rl);
            if (item == null) {
                LOGGER.warn("rebirthDreamCrystalLoot: unknown item '{}', skipping", idStr);
                continue;
            }
            list.add(item);
        }
        cachedRebirthDreamCrystalLoot = List.copyOf(list);
        LOGGER.info("rebirthDreamCrystalLoot: loaded {} items", cachedRebirthDreamCrystalLoot.size());
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        if (event.getConfig().getSpec() != SPEC) return;

        timeOfSandCooldownSeconds = TIME_OF_SAND_COOLDOWN.get();
        KaichuOmamoriCooldownSeconds = KAICHU_OMAMORI_COOLDOWN.get();
        FoxFireLifetimeSeconds= FOX_FIRE_LIFETIME.get();
        TheNumberofKillEnemytoEvolve= NEED_KILL_ENEMY.get();
        meltDreamCrystalChestNormalYSpeed = MELT_DREAM_CRYSTAL_CHEST_NORMAL_Y_SPEED.get();
        meltDreamCrystalChestRareYSpeed = MELT_DREAM_CRYSTAL_CHEST_RARE_Y_SPEED.get();
        meltDreamCrystalChestLegendYSpeed = MELT_DREAM_CRYSTAL_CHEST_LEGEND_Y_SPEED.get();
        destroyDeskOnDreamingLotusConversion = DESTROY_DESK_ON_DREAMING_LOTUS_CONVERSION.get();
        destroyDeskOnEdelweissConversion = DESTROY_DESK_ON_EDELWEISS_CONVERSION.get();
        mintakeeffectduration= MIN_TAKE_EFFECT_DURATION.get();
        maxtakeeffectduration= MAX_TAKE_EFFECT_DURATION.get();
        maxlevel= MAX_LEVEL.get();
        balanceAllowedEffects = BALANCE_ALLOWED_EFFECTS.get();
        sinInstakillEntities = SIN_INSTAKILL_ENTITIES.get();
        healthpercentguardneed= HEALTH_PERCENT.get();
        resistdamage= RESIST_DAMAGE.get();
        givePatchouliBookOnFirstJoin = GIVE_PATCHOULI_BOOK_ON_FIRST_JOIN.get();
        graveyarddamage = GRAVEYARD_DAMAGE.get();
        conflictMarkRange = CONFLICT_MARK_RANGE.get();
        conflictCardReach = CONFLICT_CARD_REACH.get();
        conflictMarkBlacklist = CONFLICT_MARK_BLACKLIST.get();
        sanCheerUpThreshold = SAN_CHEER_UP_THRESHOLD.get();
        sanLethargyUpperThreshold = SAN_LETHARGY_UPPER_THRESHOLD.get();
        sanLethargyLowerThreshold = SAN_LETHARGY_LOWER_THRESHOLD.get();
        sanTranceLowerThreshold = SAN_TRANCE_LOWER_THRESHOLD.get();
        sanInsaneLv2Threshold = SAN_INSANE_LV2_THRESHOLD.get();
        sanInsaneLv3Threshold = SAN_INSANE_LV3_THRESHOLD.get();
        meltDreamEnergyBarX = MELT_DREAM_ENERGY_BAR_X.get();
        meltDreamEnergyBarYFromBottom = MELT_DREAM_ENERGY_BAR_Y_FROM_BOTTOM.get();
        meltDreamEnergyBarDecimalPlaces = MELT_DREAM_ENERGY_BAR_DECIMAL_PLACES.get();
        meltDreamEnergyBarTextCentered = MELT_DREAM_ENERGY_BAR_TEXT_CENTERED.get();
        meltDreamEnergyBarAlwaysShow = MELT_DREAM_ENERGY_BAR_ALWAYS_SHOW.get();
        meltDreamEnergyBarShowOnChange = MELT_DREAM_ENERGY_BAR_SHOW_ON_CHANGE.get();
        meltDreamEnergyBarShowOnSneak = MELT_DREAM_ENERGY_BAR_SHOW_ON_SNEAK.get();
        sanBarXFromRight = SAN_BAR_X_FROM_RIGHT.get();
        sanBarYFromBottom = SAN_BAR_Y_FROM_BOTTOM.get();
        sanBarPreset = SAN_BAR_PRESET.get();
        sanBarPreset1SneakPrecise = SAN_BAR_PRESET1_SNEAK_PRECISE.get();
        sanBarPreset1SneakShowBar = SAN_BAR_PRESET1_SNEAK_SHOW_BAR.get();
        sanBarPreset1DecimalPlaces = SAN_BAR_PRESET1_DECIMAL_PLACES.get();
        sanBarPreset2Scale = SAN_BAR_PRESET2_SCALE.get();
        sanBarPreset2LowThreshold = SAN_BAR_PRESET2_LOW_THRESHOLD.get();
        sanBarPreset2SneakShowInCreative = SAN_BAR_PRESET2_SNEAK_SHOW_IN_CREATIVE.get();
        lowSanOverlay = LOW_SAN_OVERLAY.get();
        lowSanJitter = LOW_SAN_JITTER.get();
        lowSanSound = LOW_SAN_SOUND.get();
        shadowHealthMultipliers = SHADOW_HEALTH_MULTIPLIERS.get();
        shadowAttackMultipliers = SHADOW_ATTACK_MULTIPLIERS.get();
        shadowSpeedMultipliers = SHADOW_SPEED_MULTIPLIERS.get();
        shadowSpecialSkillsEnabled = List.copyOf(SHADOW_SPECIAL_SKILLS_ENABLED.get());
        shadowGolemSkillDamage = SHADOW_GOLEM_SKILL_DAMAGE.get();
        shadowHandSanDrain = SHADOW_HAND_SAN_DRAIN.get();
        shadowLootMultipliers = SHADOW_LOOT_MULTIPLIERS.get();
        bossShadowDifficultyAffectsDamageCap = BOSS_SHADOW_DIFFICULTY_AFFECTS_DAMAGE_CAP.get();
        bossDamageCapEnabled = BOSS_DAMAGE_CAP_ENABLED.get();
        bossDpsCapEnabled = BOSS_DPS_CAP_ENABLED.get();
        bossRangeCapEnabled = BOSS_RANGE_CAP_ENABLED.get();
        bossDamageCap = BOSS_DAMAGE_CAP.get();
        bossDpsCap = BOSS_DPS_CAP.get();
        bossRangeCap = BOSS_RANGE_CAP.get();
        aaroncosTouchImmuneToNegativeEffects = AARONCOS_TOUCH_IMMUNE_TO_NEGATIVE_EFFECTS.get();
        aaroncosDamageCapIndependent = AARONCOS_DAMAGE_CAP_INDEPENDENT.get();
        aaroncosShadowDifficultyAffectsDamageCap = AARONCOS_SHADOW_DIFFICULTY_AFFECTS_DAMAGE_CAP.get();
        aaroncosDamageCapEnabled = AARONCOS_DAMAGE_CAP_ENABLED.get();
        aaroncosDpsCapEnabled = AARONCOS_DPS_CAP_ENABLED.get();
        aaroncosRangeCapEnabled = AARONCOS_RANGE_CAP_ENABLED.get();
        aaroncosDamageCap = AARONCOS_DAMAGE_CAP.get();
        aaroncosDpsCap = AARONCOS_DPS_CAP.get();
        aaroncosRangeCap = AARONCOS_RANGE_CAP.get();
        windKnightDamageCapIndependent = WIND_KNIGHT_DAMAGE_CAP_INDEPENDENT.get();
        windKnightDamageCapEnabled = WIND_KNIGHT_DAMAGE_CAP_ENABLED.get();
        windKnightDpsCapEnabled = WIND_KNIGHT_DPS_CAP_ENABLED.get();
        windKnightRangeCapEnabled = WIND_KNIGHT_RANGE_CAP_ENABLED.get();
        windKnightDamageCap = WIND_KNIGHT_DAMAGE_CAP.get();
        windKnightDpsCap = WIND_KNIGHT_DPS_CAP.get();
        windKnightRangeCap = WIND_KNIGHT_RANGE_CAP.get();
        windKnightLightningChance = WIND_KNIGHT_LIGHTNING_CHANCE.get();
        windKnightPassiveInvulnerableEnabled = WIND_KNIGHT_PASSIVE_INVULNERABLE_ENABLED.get();
        windKnightPassiveInvulnerableChance = WIND_KNIGHT_PASSIVE_INVULNERABLE_CHANCE.get();
        windKnightPassiveInvulnerableTicks = WIND_KNIGHT_PASSIVE_INVULNERABLE_TICKS.get();
        creatureCaptureEnabled = CREATURE_CAPTURE_ENABLED.get();
        creatureCaptureEntities = CREATURE_CAPTURE_ENTITIES.get();

        lowSanSpawnHighProbs = LOW_SAN_SPAWN_HIGH_PROBS.get();
        lowSanSpawnHighEntities = LOW_SAN_SPAWN_HIGH_ENTITIES.get();
        lowSanSpawnMediumProbs = LOW_SAN_SPAWN_MEDIUM_PROBS.get();
        lowSanSpawnMediumEntities = LOW_SAN_SPAWN_MEDIUM_ENTITIES.get();
        lowSanSpawnLowProbs = LOW_SAN_SPAWN_LOW_PROBS.get();
        lowSanSpawnLowEntities = LOW_SAN_SPAWN_LOW_ENTITIES.get();
        lowSanSpawnCriticalProbs = LOW_SAN_SPAWN_CRITICAL_PROBS.get();
        lowSanSpawnCriticalEntities = LOW_SAN_SPAWN_CRITICAL_ENTITIES.get();
        lowSanSpawnHighThresholds = LOW_SAN_SPAWN_HIGH_THRESHOLDS.get();
        lowSanSpawnMaxLight = LOW_SAN_SPAWN_MAX_LIGHT.get();
        lowSanSpawnRadiusMin = LOW_SAN_SPAWN_RADIUS_MIN.get();
        lowSanSpawnRadiusMax = LOW_SAN_SPAWN_RADIUS_MAX.get();
        lowSanSpawnMaxNearby = LOW_SAN_SPAWN_MAX_NEARBY.get();
        lowSanSpawnRequiresSpecialSkill = LOW_SAN_SPAWN_REQUIRES_SPECIAL_SKILL.get();

        calaisSpiceBottleBuffs = CALAIS_SPICE_BOTTLES.get();
        calaisSpiceBottleWeights = CALAIS_SPICE_BOTTLE_WEIGHTS.get();
        calaisSpiceBottleHealMin = CALAIS_SPICE_BOTTLE_HEAL_MIN.get();
        calaisSpiceBottleHealMax = CALAIS_SPICE_BOTTLE_HEAL_MAX.get();
        calaisSpiceBottleSanMin = CALAIS_SPICE_BOTTLE_SAN_MIN.get();
        calaisSpiceBottleSanMax = CALAIS_SPICE_BOTTLE_SAN_MAX.get();
        calaisSpiceBottleDebuffs = CALAIS_SPICE_BOTTLE_DEBUFFS.get();
        calaisSpiceBottleDebuffDuration = CALAIS_SPICE_BOTTLE_DEBUFF_DURATION.get();
        calaisSpiceBottleDebuffAmplifier = CALAIS_SPICE_BOTTLE_DEBUFF_AMPLIFIER.get();
        calaisSpiceBottleEvasionDuration = CALAIS_SPICE_BOTTLE_EVASION_DURATION.get();

        ghostFaceProjectileBlacklist = GHOST_FACE_PROJECTILE_BLACKLIST.get();
        ghostFaceCloneCooldownSeconds = GHOST_FACE_CLONE_COOLDOWN.get();
        rebuildGhostFaceBlacklistCache();

        rebirthDreamCrystalLoot = REBIRTH_DREAM_CRYSTAL_LOOT.get();
        rebuildRebirthDreamCrystalLootCache();

        toolsmithBlueprintTradeChance = TOOLSMITH_BLUEPRINT_TRADE_CHANCE.get();

        bastionGuardGrantRadius = BASTION_GUARD_GRANT_RADIUS.get();

        rebuildSinInstakillCache();
        rebuildConflictMarkBlacklistCache();
        rebuildCaptureEntityCache();
        rebuildCalaisSpiceBottleCache();
        rebuildCalaisSpiceBottleDebuffCache();
    }
}
