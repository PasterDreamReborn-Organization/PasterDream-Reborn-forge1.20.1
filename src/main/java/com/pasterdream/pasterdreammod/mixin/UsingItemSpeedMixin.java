package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 拉弓 / 弩装填速度：读取玩家 {@link ModAttributes#RANGED_CHARGE_TIME_REDUCTION} 属性，
 * 按「时间缩减」语义加速使用计时器（{@code 0.25} → 时长 ×0.75）。
 * <p>
 * 原版弓/弩的蓄力进度、拉弓动画、弩瞄准动画、弓的 FOV 拉近全都读取
 * {@code getUseItemRemainingTicks()}，因此直接加速使用计时器即可让
 * 逻辑与动画同步提速。在 {@code updateUsingItem} 末尾额外扣减：每 tick 的额外
 * 扣减量为 {@code 值 / (1 - 值)}，小数部分累积到下一次，因此可支持任意比例，
 * 不再局限于 1/3。在 TAIL 注入可保证原版 {@code --useItemRemaining == 0}
 * 的完成判定不被跳过。
 */
@Mixin(LivingEntity.class)
public abstract class UsingItemSpeedMixin {

    /** 每 tick 额外扣减 tick 数的小数累积（不足 1 tick 的部分留到下一 tick）。 */
    @Unique
    private double pasterdream$chargeTimeCredit;

    @Inject(method = "updateUsingItem", at = @At("TAIL"))
    private void pasterdream$applyRangedChargeSpeed(ItemStack stack, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self instanceof Player player)
                || !(stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem)) {
            this.pasterdream$chargeTimeCredit = 0.0D;
            return;
        }

        double reduction = Mth.clamp(
                player.getAttributeValue(ModAttributes.RANGED_CHARGE_TIME_REDUCTION.get()), -0.95D, 0.95D);
        if (reduction == 0.0D) {
            this.pasterdream$chargeTimeCredit = 0.0D;
            return;
        }

        this.pasterdream$chargeTimeCredit += reduction / (1.0D - reduction);
        int extra = (int) this.pasterdream$chargeTimeCredit;
        if (extra != 0) {
            this.pasterdream$chargeTimeCredit -= extra;
            self.useItemRemaining = Math.max(0, self.useItemRemaining - extra);
        }
    }
}
