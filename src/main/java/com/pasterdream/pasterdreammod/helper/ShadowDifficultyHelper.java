package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModGameRules;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 暗影生物独立难度辅助类。
 * 支持世界级 gamerule 默认值 + 每玩家独立覆盖。
 */
public final class ShadowDifficultyHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShadowDifficultyHelper.class);
    private static final String PERSISTENT_KEY = "pasterdream.shadowDifficulty";

    private ShadowDifficultyHelper() {}

    // ===== 难度查询 =====

    /** 获取世界默认暗影难度等级 (0-3)，读取 gamerule */
    public static int getDifficulty(Level level) {
        int diff = level.getLevelData().getGameRules().getInt(ModGameRules.SHADOW_DIFFICULTY);
        return clampTier(diff);
    }

    /** 获取玩家的暗影难度等级：有个人覆盖则用个人覆盖，否则回退到默认玩家 gamerule */
    public static int getDifficulty(Player player) {
        if (player.getPersistentData().contains(PERSISTENT_KEY)) {
            return clampTier(player.getPersistentData().getInt(PERSISTENT_KEY));
        }
        int diff = player.level().getLevelData().getGameRules()
                .getInt(ModGameRules.PLAYER_SHADOW_DIFFICULTY);
        return clampTier(diff);
    }

    /**
     * 获取实体关联的难度上下文。
     * 优先级：源玩家 tag → 攻击目标 → 最近玩家 → 世界默认
     */
    public static int getDifficultyContext(LivingEntity entity) {
        // 1. 低 SAN 刷怪时写入的源玩家 UUID
        if (entity.getPersistentData().hasUUID("ShadowSourcePlayer")) {
            Player source = entity.level().getPlayerByUUID(
                    entity.getPersistentData().getUUID("ShadowSourcePlayer"));
            if (source != null) return getDifficulty(source);
        }
        // 2. 实体的攻击目标
        if (entity instanceof Mob mob && mob.getTarget() instanceof Player target) {
            return getDifficulty(target);
        }
        // 3. 最近玩家
        Player nearest = entity.level().getNearestPlayer(entity, 64);
        if (nearest != null) return getDifficulty(nearest);
        // 4. 世界默认
        return getDifficulty(entity.level());
    }

    // ===== 每玩家覆盖管理 =====

    /** 设置玩家的暗影难度覆盖 */
    public static void setPlayerDifficulty(Player player, int tier) {
        player.getPersistentData().putInt(PERSISTENT_KEY, clampTier(tier));
    }

    /** 清除玩家的暗影难度覆盖，恢复使用世界默认 */
    public static void clearPlayerDifficulty(Player player) {
        player.getPersistentData().remove(PERSISTENT_KEY);
    }

    /** 玩家是否有个人难度覆盖 */
    public static boolean hasPlayerOverride(Player player) {
        return player.getPersistentData().contains(PERSISTENT_KEY);
    }

    // ===== 属性倍率 (Level → 世界默认) =====

    public static double getHealthMultiplier(Level level) {
        return getValue(Config.shadowHealthMultipliers, getDifficulty(level), 1.0);
    }

    public static double getAttackMultiplier(Level level) {
        return getValue(Config.shadowAttackMultipliers, getDifficulty(level), 1.0);
    }

    public static double getSpeedMultiplier(Level level) {
        return getValue(Config.shadowSpeedMultipliers, getDifficulty(level), 1.0);
    }

    public static boolean isSpecialSkillEnabled(Level level) {
        int tier = getDifficulty(level);
        var skills = Config.shadowSpecialSkillsEnabled;
        if (skills == null || tier >= skills.size()) return tier > 0;
        return Boolean.parseBoolean(skills.get(tier));
    }

    public static float getGolemSkillDamage(Level level) {
        return (float) (Config.shadowGolemSkillDamage * getAttackMultiplier(level));
    }

    public static float getShadowHandSanDrain(Level level) {
        return (float) (Config.shadowHandSanDrain * getAttackMultiplier(level));
    }

    public static double getLootMultiplier(Level level) {
        return getValue(Config.shadowLootMultipliers, getDifficulty(level), 1.0);
    }

    // ===== 属性倍率 (Player → 每玩家覆盖) =====

    public static double getHealthMultiplier(Player player) {
        return getValue(Config.shadowHealthMultipliers, getDifficulty(player), 1.0);
    }

    public static double getAttackMultiplier(Player player) {
        return getValue(Config.shadowAttackMultipliers, getDifficulty(player), 1.0);
    }

    public static double getSpeedMultiplier(Player player) {
        return getValue(Config.shadowSpeedMultipliers, getDifficulty(player), 1.0);
    }

    public static boolean isSpecialSkillEnabled(Player player) {
        int tier = getDifficulty(player);
        var skills = Config.shadowSpecialSkillsEnabled;
        if (skills == null || tier >= skills.size()) return tier > 0;
        return Boolean.parseBoolean(skills.get(tier));
    }

    /** 获取难度缩放后的阴影傀儡 AoE 技能伤害（使用玩家的难度） */
    public static float getGolemSkillDamage(Player player) {
        return (float) (Config.shadowGolemSkillDamage * getAttackMultiplier(player));
    }

    /** 获取难度缩放后的暗影之手 SAN 扣除量（对特定玩家） */
    public static float getShadowHandSanDrain(Player player) {
        return (float) (Config.shadowHandSanDrain * getAttackMultiplier(player));
    }

    public static double getLootMultiplier(Player player) {
        return getValue(Config.shadowLootMultipliers, getDifficulty(player), 1.0);
    }

    // ===== 属性倍率 (根据难度等级直接查询) =====

    /** 据难度等级获取血量倍率（无 Player/Level 时使用） */
    public static double getHealthMultiplier(int tier) {
        return getValue(Config.shadowHealthMultipliers, clampTier(tier), 1.0);
    }

    /** 据难度等级获取攻击倍率（无 Player/Level 时使用） */
    public static double getAttackMultiplier(int tier) {
        return getValue(Config.shadowAttackMultipliers, clampTier(tier), 1.0);
    }

    /** 据难度等级获取移速倍率（无 Player/Level 时使用） */
    public static double getSpeedMultiplier(int tier) {
        return getValue(Config.shadowSpeedMultipliers, clampTier(tier), 1.0);
    }

    /** 据难度等级查询特殊技能是否启用 */
    public static boolean isSpecialSkillEnabled(int tier) {
        int t = clampTier(tier);
        var skills = Config.shadowSpecialSkillsEnabled;
        if (skills == null || t >= skills.size()) return t > 0;
        return Boolean.parseBoolean(skills.get(t));
    }

    /** 据难度等级获取 AoE 技能伤害 */
    public static float getGolemSkillDamage(int tier) {
        return (float) (Config.shadowGolemSkillDamage * getAttackMultiplier(tier));
    }

    /** 据难度等级获取 SAN 扣除量 */
    public static float getShadowHandSanDrain(int tier) {
        return (float) (Config.shadowHandSanDrain * getAttackMultiplier(tier));
    }

    /** 据难度等级获取战利品倍率 */
    public static double getLootMultiplier(int tier) {
        return getValue(Config.shadowLootMultipliers, clampTier(tier), 1.0);
    }

    // ===== 低理智刷怪 =====

    private static boolean isLowSanSpawnGated(Level level) {
        if (!Config.lowSanSpawnRequiresSpecialSkill) return false;
        return !isSpecialSkillEnabled(level);
    }

    /**
     * 根据 SAN 区间尝试生成暗影生物。
     * 使用玩家个人难度进行门控和概率计算。
     */
    public static void tryLowSanSpawn(ServerPlayer player, String zone) {
        var level = player.serverLevel();
        int tier = getDifficulty(player);
        if (tier == 0) return;
        if (level.isDay()) return;
        if (Config.lowSanSpawnRequiresSpecialSkill && !isSpecialSkillEnabled(tier)) return;

        double prob = getZoneBaseProb(zone, tier);
        if (prob <= 0) return;

        if (player.getRandom().nextDouble() >= prob) return;

        int maxNearby = Config.lowSanSpawnMaxNearby;
        if (maxNearby > 0) {
            int nearby = countNearbyShadowMobs(player);
            if (nearby >= maxNearby) return;
        }

        List<? extends String> entities = getZoneEntities(zone);
        if (entities == null || entities.isEmpty()) return;

        spawnRandomEntity(player, entities);
    }

    private static int countNearbyShadowMobs(ServerPlayer player) {
        double range = Config.lowSanSpawnRadiusMax;
        AABB area = AABB.ofSize(player.position(), range * 2, range * 2, range * 2);
        return player.level().getEntitiesOfClass(LivingEntity.class, area,
                e -> e.getType().is(ModEntityTypeTags.SHADOW_MOB) && e != player).size();
    }

    private static double getZoneBaseProb(String zone, int tier) {
        var list = switch (zone) {
            case "high"     -> Config.lowSanSpawnHighProbs;
            case "medium"   -> Config.lowSanSpawnMediumProbs;
            case "low"      -> Config.lowSanSpawnLowProbs;
            case "critical" -> Config.lowSanSpawnCriticalProbs;
            default -> null;
        };
        return getValue(list, tier, 0.0);
    }

    private static List<? extends String> getZoneEntities(String zone) {
        return switch (zone) {
            case "high"     -> Config.lowSanSpawnHighEntities;
            case "medium"   -> Config.lowSanSpawnMediumEntities;
            case "low"      -> Config.lowSanSpawnLowEntities;
            case "critical" -> Config.lowSanSpawnCriticalEntities;
            default -> List.of();
        };
    }

    /**
     * 从权重列表中随机选取一个实体并生成在玩家附近。
     * 条目格式: "modid:entity_id:weight"
     */
    private static void spawnRandomEntity(ServerPlayer player, List<? extends String> entries) {
        double totalWeight = 0;
        for (String entry : entries) {
            double w = parseWeight(entry);
            if (w > 0) totalWeight += w;
        }
        if (totalWeight <= 0) return;

        double roll = player.getRandom().nextDouble() * totalWeight;
        double cumulative = 0;
        for (String entry : entries) {
            double w = parseWeight(entry);
            if (w <= 0) continue;
            cumulative += w;
            if (roll <= cumulative) {
                spawnFromEntry(player, entry);
                return;
            }
        }
    }

    private static void spawnFromEntry(ServerPlayer player, String entry) {
        String[] parts = entry.split(":", 3);
        if (parts.length < 2) return;
        ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]);
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(rl);
        if (type == null) {
            LOGGER.warn("lowSanSpawn: unknown entity type '{}', skipping", rl);
            return;
        }

        ServerLevel level = player.serverLevel();
        int maxLight = Config.lowSanSpawnMaxLight;
        double minR = Config.lowSanSpawnRadiusMin;
        double maxR = Config.lowSanSpawnRadiusMax;
        var random = player.getRandom();

        for (int attempt = 0; attempt < 8; attempt++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double dist = minR + random.nextDouble() * (maxR - minR);
            double sx = player.getX() + Math.cos(angle) * dist;
            double sz = player.getZ() + Math.sin(angle) * dist;
            double sy = player.getY();

            BlockPos pos = BlockPos.containing(sx, sy, sz);

            if (maxLight < 15 && level.getMaxLocalRawBrightness(pos) > maxLight) continue;

            BlockPos ground = pos;
            while (ground.getY() > level.getMinBuildHeight() && level.getBlockState(ground).isAir()) {
                ground = ground.below();
            }
            if (!level.getBlockState(ground).isSolid()) continue;

            BlockPos spawnPos = ground.above();
            if (!level.getBlockState(spawnPos).is(Blocks.AIR)) continue;
            if (!level.getBlockState(spawnPos.above()).is(Blocks.AIR)) continue;

            Entity e = type.spawn(level, spawnPos, MobSpawnType.MOB_SUMMONED);
            if (e != null) {
                e.setYRot(random.nextFloat() * 360F);
                // 标记源玩家，供后续属性缩放和行为判定使用
                e.getPersistentData().putUUID("ShadowSourcePlayer", player.getUUID());
                // 堕落者之印：源玩家佩戴封印时，暗影生物协助作战
                if (CuriosApi.getCuriosInventory(player)
                        .map(h -> h.findFirstCurio(ModItems.SEAL_OF_THE_CORRUPTED.get()).isPresent())
                        .orElse(false)) {
                    e.getPersistentData().putBoolean("pasterdream:seal_friendly", true);
                }
                return;
            }
        }
    }

    private static double parseWeight(String entry) {
        String[] parts = entry.split(":", 3);
        if (parts.length < 3) return 0;
        try {
            return Double.parseDouble(parts[2]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // ===== 通用工具 =====

    private static int clampTier(int tier) {
        if (tier < 0) return 0;
        if (tier > 3) return 3;
        return tier;
    }

    private static double getValue(@Nullable List<? extends Double> list, int index, double fallback) {
        if (list == null || list.isEmpty()) return fallback;
        if (index < list.size()) return list.get(index);
        return list.get(list.size() - 1);
    }
}
