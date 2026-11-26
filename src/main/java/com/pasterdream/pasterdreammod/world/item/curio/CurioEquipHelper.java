package com.pasterdream.pasterdreammod.world.item.curio;

import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

/**
 * 饰品「同一种只能装备一件」的通用判定。
 *
 * <p>Curios 在读档与数据包同步时会把槽位里的物品重新过一遍校验：
 * {@code CurioInventoryCapability.readTag -> ItemHandler.isItemValid -> ICurioItem.canEquip}，
 * 传进来的 {@link ItemStack} 就是槽里已经装着的那一件。若判重写成「槽里已有同类就拒绝」，
 * 读档时它会命中自己而返回 {@code false}，物品随即被 {@code loseInvalidStack} 丢出，
 * 下一 tick 由 {@code handleInvalidStacks()} 用 {@code ItemHandlerHelper.giveItemToPlayer}
 * 送回主物品栏——表现为「重进存档后饰品从饰品栏掉回背包」。
 *
 * <p>因此判重必须放行自身槽位：命中项与本次校验的槽位相同（同 identifier + index），
 * 或干脆是同一个 {@link ItemStack} 实例时返回 {@code true}，只拦真正多出来的那一件。
 */
public final class CurioEquipHelper {

    private CurioEquipHelper() {
    }

    /**
     * 判断这件饰品能否放进 {@code slotContext} 指向的槽位：只禁止同类饰品的第二件。
     */
    public static boolean allowsSingleCopy(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() == null) {
            return true;
        }
        return CuriosApi.getCuriosInventory(slotContext.entity())
                .map(handler -> handler.findFirstCurio(stack.getItem())
                        .map(result -> isSameSlot(result, slotContext, stack))
                        .orElse(true))
                .orElse(true);
    }

    private static boolean isSameSlot(SlotResult result, SlotContext checking, ItemStack stack) {
        if (result.stack() == stack) {
            return true;
        }
        SlotContext found = result.slotContext();
        return found.identifier().equals(checking.identifier()) && found.index() == checking.index();
    }
}
