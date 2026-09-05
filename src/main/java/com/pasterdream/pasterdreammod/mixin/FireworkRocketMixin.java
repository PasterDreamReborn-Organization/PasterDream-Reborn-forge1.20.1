package com.pasterdream.pasterdreammod.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * 烟花助推不再抹除外部来源（如破风幕帐）给予的超额动量。
 * 原版 {@code FireworkRocketEntity#tick} 在烟花附身鞘翅飞行时，每 tick 把玩家速度向视线方向的
 * 巡航速率折半收敛（阻尼常数 1.5 + 常数 0.1 → 平衡速度约 1.7 格/tick），导致穿过破风幕帐获得的
 * 一次性冲量（5 格/tick）在 2~3 tick 内被抹平，烟花飞行时幕帐看起来毫无加速效果。
 * 这里在玩家沿视线方向的速度超过巡航速率时，让火箭只做转向、不再减速，从而保留幕帐冲量；
 * 对普通烟花巡航（速度低于上限）行为无影响，客户端/服务端同步执行无预测抖动。
 */
@Mixin(FireworkRocketEntity.class)
public class FireworkRocketMixin {

    /** 原版烟花巡航速率上限（平衡速度约 1.7，留出转向余量） */
    private static final double CRUISE_SPEED_CAP = 1.75;

    @Shadow
    private LivingEntity attachedToEntity;

    /**
     * 替换附身者速度更新调用：超速时保持沿视线方向的多余动量（垂直分量随转向逐渐并入视线方向）。
     */
    @ModifyArg(method = "tick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"),
            index = 0)
    private Vec3 preserveExternalMomentum(Vec3 vanillaMotion) {
        LivingEntity rider = this.attachedToEntity;
        if (rider == null || !rider.isFallFlying()) return vanillaMotion;

        Vec3 look = rider.getLookAngle();
        Vec3 velocity = rider.getDeltaMovement();
        double forwardSpeed = velocity.dot(look);
        if (forwardSpeed <= CRUISE_SPEED_CAP) return vanillaMotion;

        // 只转向不减速：0.5*v + 0.5*|v_forward|*look —— 视线方向分量保持，垂直分量每 tick 减半收敛
        return velocity.scale(0.5).add(look.scale(forwardSpeed * 0.5));
    }
}
