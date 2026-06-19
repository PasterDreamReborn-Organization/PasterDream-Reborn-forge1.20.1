package com.pasterdream.pasterdreammod.helper.tooltipadder;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public class AddTooltip
{
    public static void addTooltip(ItemTooltipEvent event)
    {
        //用于向其他已有物品添加tooltip
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        if(stack.is(ModItems.MAGIC_STONE.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.magic_stone"));
        }

    }
}
