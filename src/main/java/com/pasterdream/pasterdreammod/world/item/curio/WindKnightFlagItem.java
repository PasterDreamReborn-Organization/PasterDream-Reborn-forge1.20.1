package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

/**
 * 风骑士旗帜：佩戴后可将逆风视为顺风（逆风时不再减速，反而加速）。
 */
public class WindKnightFlagItem extends Item implements ICurioItem {

    public WindKnightFlagItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.EXCELLENT));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(ModRarities.qualityTooltip(ModRarities.EXCELLENT));
        list.add(Component.translatable("tooltip.pasterdream.wind_knight_flag.effect1"));
    }
}
