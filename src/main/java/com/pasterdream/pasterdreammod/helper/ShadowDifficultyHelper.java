package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModGameRules;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 暗影生物独立难度辅助类。
 * 读取当前世界的 gamerule 并结合配置文件返回缩放后的数值。
 */
public final class ShadowDifficultyHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShadowDifficultyHelper.class);

    private ShadowDifficultyHelper() {}

    /** 获取当前世界的暗影难度等级 (0-3) */
    public static int getDifficulty(Level level) {
        int diff = level.getLevelData().getGameRules().getInt(ModGameRules.SHADOW_DIFFICULTY);
        return clampTier(diff);
    }

    /** 获取血量倍率 */
    public static double getHealthMultiplier(Level level) {
        return getValue(Config.shadowHealthMultipliers, getDifficulty(level), 1.0);
    }

    /** 获取攻击倍率 */
    public static double getAttackMultiplier(Level level) {
        return getValue(Config.shadowAttackMultipliers, getDifficulty(level), 1.0);
    }

    /** 获取移速倍率 */
    public static double getSpeedMultiplier(Level level) {
        return getValue(Config.shadowSpeedMultipliers, getDifficulty(level), 1.0);
    }

    /** 当前难度是否启用暗影生物特殊技能 */
    public static boolean isSpecialSkillEnabled(Level level) {
        int tier = getDifficulty(level);
        var skills = Config.shadowSpecialSkillsEnabled;
        if (skills == null || tier >= skills.size()) return tier > 0;
        return Boolean.parseBoolean(skills.get(tier));
    }

    /** 获取难度缩放后的阴影傀儡 AoE 技能伤害 */
    public static float getGolemSkillDamage(Level level) {
        return (float) (Config.shadowGolemSkillDamage * getAttackMultiplier(level));
    }

    /** 获取难度缩放后的暗影之手 SAN 扣除量 */
    public static float getShadowHandSanDrain(Level level) {
        return (float) (Config.shadowHandSanDrain * getAttackMultiplier(level));
    }

    // ===== 低理智刷怪 =====

    /**
     * 低理智刷怪是否受难度开关控制。
     * 配置 {@code lowSanSpawnRequiresSpecialSkill=false} 时始终允许，
     * 否则由当前暗影难度的特殊技能开关决定。
     */
    private static boolean isLowSanSpawnGated(Level level) {
        if (!Config.lowSanSpawnRequiresSpecialSkill) return false;
        return !isSpecialSkillEnabled(level);
    }

    /**
     * 根据 SAN 区间尝试生成暗影生物。
     * 由 {@code SanAuraHandler} 在玩家 tick 时调用。
     *
     * @param player       目标玩家
     * @param zone 区间名: "high" / "medium" / "low" / "critical"
     */
    public static void tryLowSanSpawn(ServerPlayer player, String zone) {
        var level = player.serverLevel();
        int tier = getDifficulty(level);
        if (tier == 0) return;
        if (level.isDay()) return;
        if (isLowSanSpawnGated(level)) return;

        double prob = getZoneBaseProb(zone, tier);
        if (prob <= 0) return;

        if (player.getRandom().nextDouble() >= prob) return;

        // 周围暗影生物数量上限
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
        return player.level().getEntitiesOfClass(net.minecraft.world.entity.LivingEntity.class, area,
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
     * 从权重列表中随机选取一个实体并生成在玩家位置。
     * 条目格式: "modid:entity_id:weight"
     */
    private static void spawnRandomEntity(ServerPlayer player, List<? extends String> entries) {
        // 计算总权重
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

        // 尝试 8 次找到合适的生成位置
        for (int attempt = 0; attempt < 8; attempt++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double dist = minR + random.nextDouble() * (maxR - minR);
            double sx = player.getX() + Math.cos(angle) * dist;
            double sz = player.getZ() + Math.sin(angle) * dist;
            double sy = player.getY();

            BlockPos pos = BlockPos.containing(sx, sy, sz);

            // 光照检查：亮度超过阈值（通常是 5）不生成，安全屋照明可防刷
            if (maxLight < 15 && level.getMaxLocalRawBrightness(pos) > maxLight) continue;

            // 向下找第一个非空气方块作为地面
            BlockPos ground = pos;
            while (ground.getY() > level.getMinBuildHeight() && level.getBlockState(ground).isAir()) {
                ground = ground.below();
            }
            // 必须是完整固体方块，不能是花/火把/草等非固体
            if (!level.getBlockState(ground).isSolid()) continue;

            // 生成位置和头上必须是空气
            BlockPos spawnPos = ground.above();
            if (!level.getBlockState(spawnPos).is(Blocks.AIR)) continue;
            if (!level.getBlockState(spawnPos.above()).is(Blocks.AIR)) continue;

            Entity e = type.spawn(level, spawnPos, MobSpawnType.MOB_SUMMONED);
            if (e != null) {
                e.setYRot(random.nextFloat() * 360F);
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

    private static double getValue(java.util.List<? extends Double> list, int index, double fallback) {
        if (list == null || list.isEmpty()) return fallback;
        if (index < list.size()) return list.get(index);
        return list.get(list.size() - 1);
    }
}
