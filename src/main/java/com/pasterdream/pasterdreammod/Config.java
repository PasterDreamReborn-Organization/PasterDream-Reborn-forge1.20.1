package com.pasterdream.pasterdreammod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                            "minecraft:jump_boost","minecraft:health_boost","pasterdream:cook_buff"),
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
                            "alexsmobs:seagull","iceandfire:pixie","twilightforest:pinch_beetle"),
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

    private static final ForgeConfigSpec.DoubleValue SAN_INSAND_LV2_THRESHOLD = BUILDER
            .comment("疯狂 II 触发阈值（ratio < 该值时升级为 Lv2），默认 0.1（10%）")
            .defineInRange("sanInsandLv2Threshold", 0.1, 0.0, 1.0);

    private static final ForgeConfigSpec.DoubleValue SAN_INSAND_LV3_THRESHOLD = BUILDER
            .comment("疯狂 III 触发阈值（ratio < 该值时升级为 Lv3），默认 0.01（1%）")
            .defineInRange("sanInsandLv3Threshold", 0.01, 0.0, 1.0);


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
    public static double sanInsandLv2Threshold;
    public static double sanInsandLv3Threshold;

    // === 低 San 效果开关（可通过 /pasterdreamdebug lowsan 指令运行时切换） ===
    public static boolean lowSanOverlay = true;
    public static boolean lowSanJitter = true;
    public static boolean lowSanSound = true;

    // === 预言卡配置 ===

    //平衡
    public static int mintakeeffectduration;
    public static int maxtakeeffectduration;
    public static int maxlevel;
    public static List<? extends String> balanceAllowedEffects;

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

    //守护
    public static Double healthpercentguardneed;
    public static Double resistdamage;

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
        sanInsandLv2Threshold = SAN_INSAND_LV2_THRESHOLD.get();
        sanInsandLv3Threshold = SAN_INSAND_LV3_THRESHOLD.get();

        rebuildSinInstakillCache();
        rebuildConflictMarkBlacklistCache();
    }
}
