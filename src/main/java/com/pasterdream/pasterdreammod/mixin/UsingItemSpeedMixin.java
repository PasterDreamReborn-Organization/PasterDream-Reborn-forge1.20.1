package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorItem;
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
 * 萦风合金盔甲「御风之誓·疾射」：满套玩家拉弓 / 弩装填时间 -25%。
 * <p>
 * 原版弓/弩的蓄力进度、拉弓动画、弩瞄准动画、弓的 FOV 拉近全都读取
 * {@code getUseItemRemainingTicks()}，因此直接加速使用计时器即可让
 * 逻辑与动画同步提速。在 {@code updateUsingItem} 末尾额外扣减：每 3 tick
 * 多扣 1，等效速率 4/3（时长 ×0.75）。在 TAIL 注入可保证原版
 * {@code --useItemRemaining == 0} 的完成判定不被跳过。
 */
@Mixin(LivingEntity.class)
public abstract class UsingItemSpeedMixin {

    @Shadow
    protected int useItemRemaining;

    @Unique
    private int pasterdream$drawAccelCounter;

    @Inject(method = "updateUsingItem", at = @At("TAIL"))
    private void pasterdream$fasterDraw(ItemStack stack, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        boolean applies = self instanceof Player player
                && FluffyWindAlloyArmorItem.hasFullSet(player)
                && (stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem);
        if (!applies) {
            this.pasterdream$drawAccelCounter = 0;
            return;
        }
        if (++this.pasterdream$drawAccelCounter >= 3) {
            this.pasterdream$drawAccelCounter = 0;
            this.useItemRemaining--;
        }
    }
}
