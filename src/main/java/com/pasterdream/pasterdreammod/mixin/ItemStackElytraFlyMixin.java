package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * 风行者效果：无需鞘翅即可进行鞘翅飞行
 * Forge 将鞘翅判定收敛在 IForgeItemStack#canElytraFly / elytraFlightTick 上：
 * - Player#tryToStartFallFlying / LocalPlayer.aiStep：双击跳跃开始滑翔
 * - LivingEntity.aiStep：维持滑翔状态
 * 覆盖后只要持有风行者效果，这两个判定都会返回 true。
 */
@Mixin(value = IForgeItemStack.class, remap = false, priority = 0)
public interface ItemStackElytraFlyMixin {

    @Shadow
    ItemStack self();

    /**
     * @author PasterDream: Reborn
     * @reason 让持有风行者效果的实体无需鞘翅即可开始鞘翅飞行
     */
    @Overwrite(remap = false)
    default boolean canElytraFly(LivingEntity entity) {
        return self().getItem().canElytraFly(self(), entity) || entity.hasEffect(ModEffects.WIND_RUNNER.get());
    }

    /**
     * @author PasterDream: Reborn
     * @reason 让持有风行者效果的实体在滑翔期间维持鞘翅飞行状态
     */
    @Overwrite(remap = false)
    default boolean elytraFlightTick(LivingEntity entity, int flightTicks) {
        return self().getItem().elytraFlightTick(self(), entity, flightTicks) || entity.hasEffect(ModEffects.WIND_RUNNER.get());
    }
}