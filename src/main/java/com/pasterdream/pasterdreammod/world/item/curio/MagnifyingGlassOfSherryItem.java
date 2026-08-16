package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class MagnifyingGlassOfSherryItem extends Item implements ICurioItem {

    public MagnifyingGlassOfSherryItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.MIRACLE));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity()).map(handler ->
                            handler.findFirstCurio(stack.getItem()).isEmpty())
                    .orElse(true);
        }
        return true;
    }

    public static boolean isWearing(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.MAGNIFYING_GLASS_OF_SHERRY.get()).isPresent())
                .orElse(false);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.magnifying_glass_of_sherry.1"));
        list.add(Component.translatable("tooltip.pasterdream.magnifying_glass_of_sherry.2"));
        list.add(Component.translatable("tooltip.pasterdream.magnifying_glass_of_sherry.3"));
        list.add(Component.translatable("tooltip.pasterdream.magnifying_glass_of_sherry.4"));
        list.add(Component.translatable("tooltip.pasterdream.magnifying_glass_of_sherry.5"));
        list.add(Component.translatable("tooltip.pasterdream.magnifying_glass_of_sherry.6"));
        list.add(Component.translatable("tooltip.pasterdream.magnifying_glass_of_sherry.7"));
        list.add(Component.translatable("tooltip.pasterdream.magnifying_glass_of_sherry.8"));
    }
}
