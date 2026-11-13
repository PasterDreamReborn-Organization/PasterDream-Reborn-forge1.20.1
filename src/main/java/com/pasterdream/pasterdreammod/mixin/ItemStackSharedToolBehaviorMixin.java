package com.pasterdream.pasterdreammod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.pasterdream.pasterdreammod.world.item.fluffywindalloy.FluffyWindAlloyTool;
import com.pasterdream.pasterdreammod.world.item.fluffywindalloy.FluffyWindAlloyToolHelper;
import com.pasterdream.pasterdreammod.world.item.meltdreamtool.MeltDreamTool;
import com.pasterdream.pasterdreammod.world.item.meltdreamtool.MeltDreamToolHelper;
import com.pasterdream.pasterdreammod.world.item.moltengoldtool.MoltenGoldTool;
import com.pasterdream.pasterdreammod.world.item.moltengoldtool.MoltenGoldToolHelper;
import com.pasterdream.pasterdreammod.world.item.shadowalloytool.ShadowAlloyTool;
import com.pasterdream.pasterdreammod.world.item.shadowalloytool.ShadowAlloyToolHelper;
import com.pasterdream.pasterdreammod.world.item.shadowerosiontool.ShadowErosionTool;
import com.pasterdream.pasterdreammod.world.item.shadowerosiontool.ShadowErosionToolHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * 工具系列「公共 tooltip」的唯一注入点。
 * <p>
 * 在 {@link ItemStack#getTooltipLines} 处理原版属性词条前注入：此时工具自身词条及 NBT 描述已加入列表，
 * 属性、物品 ID 与模组名尚未加入，因此公共词条位于它们之间。
 * <p>
 * 用「独立 instanceof 分支」而不是接口 default：default 既压不过父类具体方法（JLS「class wins」），
 * 也无法叠加——附属模组想让一件工具同时拥有多个系列的公共词条时，多个同签名 default 会冲突。
 * 分支写法下命中几个接口就追加几组词条，互不覆盖。新增系列时在下面追加一个分支即可。
 * <p>
 * 背包 tick 见 {@link ItemSharedToolBehaviorMixin}。
 */
@Mixin(ItemStack.class)
public class ItemStackSharedToolBehaviorMixin {

    @Inject(method = "getTooltipLines", at = @At(value = "FIELD",
            target = "Lnet/minecraft/world/item/ItemStack$TooltipPart;MODIFIERS:Lnet/minecraft/world/item/ItemStack$TooltipPart;",
            opcode = Opcodes.GETSTATIC))
    private void pasterdream$sharedToolTooltip(Player player, TooltipFlag flag,
                                               CallbackInfoReturnable<List<Component>> cir,
                                               @Local(ordinal = 0) List<Component> tooltip) {
        ItemStack stack = (ItemStack) (Object) this;
        Item item = stack.getItem();
        Level level = player == null ? null : player.level();

        if (item instanceof MeltDreamTool) {
            MeltDreamToolHelper.appendHoverText(stack, level, tooltip, flag);
        }
        if (item instanceof MoltenGoldTool) {
            MoltenGoldToolHelper.appendHoverText(stack, level, tooltip, flag);
        }
        if (item instanceof ShadowAlloyTool) {
            ShadowAlloyToolHelper.appendHoverText(stack, level, tooltip, flag);
        }
        if (item instanceof ShadowErosionTool) {
            ShadowErosionToolHelper.appendHoverText(stack, level, tooltip, flag);
        }
        if (item instanceof FluffyWindAlloyTool) {
            FluffyWindAlloyToolHelper.appendHoverText(stack, level, tooltip, flag);
        }
    }
}
