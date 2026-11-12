package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.item.meltdreamtool.MeltDreamTool;
import com.pasterdream.pasterdreammod.world.item.meltdreamtool.MeltDreamToolHelper;
import com.pasterdream.pasterdreammod.world.item.shadowalloytool.ShadowAlloyTool;
import com.pasterdream.pasterdreammod.world.item.shadowalloytool.ShadowAlloyToolHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 工具系列「背包 tick」共享行为的唯一注入点。
 * <p>
 * 为什么不放接口 default：{@code SwordItem}/{@code PickaxeItem} 等父类从 {@link Item} 继承了**具体**的
 * {@code inventoryTick}，按 JLS「class wins」，父类具体方法永远压过接口 default，子类不写 override 时不会执行；
 * 更关键的是接口 default 无法叠加——附属模组若想让一件工具同时拥有多个系列的被动
 * （如同时实现 {@link MeltDreamTool} 与 {@link ShadowAlloyTool}），两个同签名 default 会直接冲突。
 * <p>
 * 因此这里用「独立 instanceof 分支」显式派发：命中几个系列接口就执行几个系列的 Helper，互不覆盖，
 * 也不需要子类再写 {@code super.inventoryTick(...); XxxToolHelper.onInventoryTick(...);}。
 * 新增系列时在下面追加一个分支即可。
 * <p>
 * 公共 tooltip 见 {@link ItemStackSharedToolBehaviorMixin}。
 */
@Mixin(Item.class)
public class ItemSharedToolBehaviorMixin {

    @Inject(method = "inventoryTick", at = @At("TAIL"))
    private void pasterdream$sharedToolInventoryTick(ItemStack stack, Level level, Entity entity, int slot,
                                                     boolean selected, CallbackInfo ci) {
        Object self = this;
        if (self instanceof MeltDreamTool) {
            MeltDreamToolHelper.onInventoryTick(stack, level, entity, slot, selected);
        }
        if (self instanceof ShadowAlloyTool) {
            ShadowAlloyToolHelper.onInventoryTick(stack, level, entity, slot, selected);
        }
    }
}
