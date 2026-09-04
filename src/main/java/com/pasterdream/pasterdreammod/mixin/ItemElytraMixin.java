package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * 风行者效果：无需鞘翅即可开始/维持鞘翅飞行。
 * 接口 mixin @Overwrite IForgeItem#canElytraFly / elytraFlightTick 默认方法（Forge 类，原名即生产名，remap=false）。
 * 原始实现恒返回 false（真实鞘翅由 ElytraItem 等类级覆盖，不经过接口默认方法），
 * 覆盖为「持有风行者效果即放行」对其他物品零影响。
 * 调用链：Player.tryToStartFallFlying / LocalPlayer.aiStep / LivingEntity.updateFallFlying
 * 三处 invokevirtual ItemStack.canElytraFly 解析到 IForgeItemStack 默认方法，
 * 内部再 invokevirtual Item.canElytraFly（无鞘翅时落到 IForgeItem 默认实现），此处一处覆盖全覆盖。
 * @Overwrite 不抢占调用点指令：
 * - 与 legendary_spellbooks @Redirect Player/LocalPlayer 调用点不冲突
 * - ironsspellbooks @Overwrite IForgeItemStack 默认方法（priority=0 先应用）之后，其覆盖体
 *   仍调用 Item.canElytraFly，本覆盖在其后生效，效果共存
 */
@Mixin(value = IForgeItem.class, remap = false)
public interface ItemElytraMixin {

    /**
     * @author pasterdream
     * @reason 风行者效果：无需鞘翅即可开始鞘翅飞行
     */
    @Overwrite(remap = false)
    default boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return entity.hasEffect(ModEffects.WIND_RUNNER.get());
    }

    /**
     * @author pasterdream
     * @reason 风行者效果：无需鞘翅即可维持鞘翅飞行
     */
    @Overwrite(remap = false)
    default boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        return entity.hasEffect(ModEffects.WIND_RUNNER.get());
    }
}