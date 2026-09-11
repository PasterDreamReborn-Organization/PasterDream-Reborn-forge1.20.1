package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

/**
 * 风行者效果：无需鞘翅即可开始/维持鞘翅飞行。
 * 通过给 {@link Item} 合并 IForgeItem#canElytraFly / elytraFlightTick 的具体实现来覆盖接口默认方法，
 * 不使用 @Overwrite，也不与其它模组对 IForgeItem 接口默认方法的注入冲突（接口注入无法在接口 mixin 中使用）。
 * 原始接口默认实现恒返回 false（真实鞘翅由 ElytraItem 等子类级覆盖，子类方法优先，不受影响）。
 * 调用链：Player.tryToStartFallFlying / LocalPlayer.aiStep / LivingEntity.updateFallFlying
 * 三处 invokevirtual ItemStack.canElytraFly 解析到 IForgeItemStack 默认方法，
 * 内部再 invokevirtual Item.canElytraFly，此处一处覆盖所有物品。
 */
@Mixin(Item.class)
public abstract class ItemElytraMixin {

    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return entity.hasEffect(ModEffects.WIND_RUNNER.get());
    }

    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        return entity.hasEffect(ModEffects.WIND_RUNNER.get());
    }
}
