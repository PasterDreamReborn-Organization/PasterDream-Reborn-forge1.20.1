package com.pasterdream.pasterdreammod.helper.cooldown;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

/**
 * 战技冷却辅助。
 * <p>
 * 冷却真值即玩家物品栏的原版物品冷却：{@link #applySharedCooldown} 对
 * {@code pasterdream:skill_cooldown} 标签下所有物品写入原版冷却，判定用
 * {@code player.getCooldowns().isOnCooldown(item)}。物品的 {@code use} / {@code useOn}
 * 由原版 {@code ServerPlayerGameMode} 自动按冷却拦截。
 */
public class SkillCooldownHelper {

    private SkillCooldownHelper() {}

    // ==================== 共享战技冷却 ====================

    /**
     * 对 {@code pasterdream:skill_cooldown} 标签下所有物品施加共享冷却，
     * 时长按玩家的 {@code skill_cooldown_reduction} 属性缩放。
     */
    public static void applySharedCooldown(Player player, int baseTicks) {
        int scaledTicks = scaleCooldown(player, baseTicks);
        if (scaledTicks <= 0) return;
        applySharedCooldownTicks(player, scaledTicks);
    }

    /**
     * 施加共享冷却，时长**不**随 {@code skill_cooldown_reduction} 缩放。
     * <p>
     * 用于需与实体计时严格对齐的冷却：萦风雷矛投掷虚影的兜底飞回时长（200t）必须 == 冷却时长，
     * 否则冷却会先于虚影结束，玩家可在旧虚影仍在场上时再次投掷（叠虚影 / 提前解冷却）。
     */
    public static void applySharedCooldownExact(Player player, int ticks) {
        applySharedCooldownTicks(player, Math.max(1, ticks));
    }

    private static void applySharedCooldownTicks(Player player, int finalTicks) {
        if (finalTicks <= 0) return;
        BuiltInRegistries.ITEM.getTagOrEmpty(ModItemTags.SKILL_COOLDOWN)
                .forEach(holder -> player.getCooldowns().addCooldown(holder.value(), finalTicks));
    }

    /** 清除共享冷却：移除 {@code pasterdream:skill_cooldown} 标签下所有物品的原版冷却。 */
    public static void clearSharedCooldown(Player player) {
        BuiltInRegistries.ITEM.getTagOrEmpty(ModItemTags.SKILL_COOLDOWN)
                .forEach(holder -> {
                    if (player.getCooldowns().isOnCooldown(holder.value())) {
                        player.getCooldowns().removeCooldown(holder.value());
                    }
                });
    }

    // ==================== 数值 ====================

    /** 按玩家的 {@code skill_cooldown_reduction} 属性缩放基础冷却时长（tick，0 = 无冷却）。 */
    public static int scaleCooldown(Player player, int baseTicks) {
        return Math.round(baseTicks * getSkillCooldownMultiplier(player));
    }

    /**
     * Returns the player's skill cooldown multiplier (default 1.0 = no change).
     */
    public static float getSkillCooldownMultiplier(Player player) {
        return (float) CooldownReductionHelper.getCooldownFactor(
                player, ModAttributes.SKILL_COOLDOWN_REDUCTION.get());
    }

    /** Returns the player's raw skill cooldown reduction (default 0.0 = no reduction). */
    public static float getSkillCooldownReduction(Player player) {
        return (float) CooldownReductionHelper.getReduction(
                player, ModAttributes.SKILL_COOLDOWN_REDUCTION.get());
    }

    /**
     * Returns the player's skill damage multiplier (default 1.0 = no change).
     * This is layered on top of each weapon's own damage formula.
     */
    public static float getSkillDamageMultiplier(Player player) {
        return getAttributeValue(player, ModAttributes.SKILL_DAMAGE_RATE.get(), 1.0f);
    }

    private static float getAttributeValue(Player player, net.minecraft.world.entity.ai.attributes.Attribute attr, float fallback) {
        AttributeInstance inst = player.getAttribute(attr);
        return inst != null ? (float) inst.getValue() : fallback;
    }
}
