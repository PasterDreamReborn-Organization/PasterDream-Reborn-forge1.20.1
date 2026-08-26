package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

/**
 * 纸飞机：佩戴后增大风向对自身的影响（顺风/逆风 buff 提升一级）。
 */
public class PaperPlaneItem extends Item implements ICurioItem {

    public PaperPlaneItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.COMMON));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.paper_plane.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.paper_plane.description"));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            slotContext.entity().getPersistentData().putDouble("player_tailwind_force", 1);
            slotContext.entity().getPersistentData().putDouble("player_deadwind_force", 1);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            slotContext.entity().getPersistentData().putDouble("player_tailwind_force", 0);
            slotContext.entity().getPersistentData().putDouble("player_deadwind_force", 0);
        }
    }
}
