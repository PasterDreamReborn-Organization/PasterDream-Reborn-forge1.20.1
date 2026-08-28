package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class WarFlagItem extends Item implements ICurioItem {
    public WarFlagItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.ANCIENT));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.war_flag.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.war_flag.effect2"));
        list.add(Component.translatable("tooltip.pasterdream.war_flag.effect3"));
        list.add(Component.translatable("tooltip.pasterdream.war_flag.description"));
    }
}
