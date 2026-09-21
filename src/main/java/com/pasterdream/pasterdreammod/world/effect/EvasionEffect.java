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
 * 回避无敌帧 buff，每级可抵挡一次伤害。
 * 效果无法被牛奶 / totem / {@code /effect clear} 或其它 mod 的清除逻辑移除，
 * 仅当伤害触发消耗一层时通过 {@link #allowRemoval(Entity)} 显式授权后才会被移除；
 * 效果自身计时到期不受影响。细节见 {@link ProtectedRemovalEffect}。
 */
public class EvasionEffect extends MobEffect implements ProtectedRemovalEffect {

    /** 显式授权移除的实体 UUID 集合（一次性：授权后随即被消费，避免常驻内存） */
    private static final Set<UUID> ALLOWED_REMOVALS = Collections.synchronizedSet(new HashSet<>());

    public EvasionEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xD5F5E3);
    }

    /** 显式授权移除回避（成功回避一次伤害、消耗一层时调用），随后效果才可被真正移除。 */
    public static void allowRemoval(Entity entity) {
        if (entity != null) {
            ALLOWED_REMOVALS.add(entity.getUUID());
        }
    }

    @Override
    public boolean hasRemovalAllowance(LivingEntity entity) {
        return ALLOWED_REMOVALS.contains(entity.getUUID());
    }

    @Override
    public boolean consumeRemovalAllowance(LivingEntity entity) {
        return ALLOWED_REMOVALS.remove(entity.getUUID());
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
