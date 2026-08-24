package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 暗影窥视 —— 使玩家成为暗影的「窥视」目标，每 tick 触发一次暗影入侵。
 * 效果无法被牛奶解除，由暗影入侵完成或击败亚伦柯斯之触时移除。
 * 外部 mod / 命令 / 牛奶 / totem 的清除逻辑会被 ShadowSpyonProtectionMixin 拦截，
 * 仅当通过 {@link #allowRemoval(Entity)} 显式授权后才能移除。
 */
public class ShadowSpyonEffect extends MobEffect {

    /** 显式授权移除的实体 UUID 集合（一次性：授权后随即被消费，避免常驻内存） */
    private static final Set<UUID> ALLOWED_REMOVALS = Collections.synchronizedSet(new HashSet<>());

    public ShadowSpyonEffect() {
        super(MobEffectCategory.HARMFUL, -13421773);
    }

    /** 显式授权移除暗影窥视（入侵完成 / 击败亚伦柯斯之触时调用），随后效果才可被真正移除。 */
    public static void allowRemoval(Entity entity) {
        if (entity != null) {
            ALLOWED_REMOVALS.add(entity.getUUID());
        }
    }

    /** 消费一次授权：已授权则返回 true（允许移除），否则返回 false（阻止移除）。 */
    public static boolean consumeRemovalAllowance(LivingEntity entity) {
        return ALLOWED_REMOVALS.remove(entity.getUUID());
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        ShadowIntrudeHandler.tick(entity);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return Collections.emptyList();
    }
}
