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

public class TerraFloatingIslandItem extends Item implements ICurioItem {
    public TerraFloatingIslandItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        list.add(ModRarities.qualityTooltip(ModRarities.EPIC));
        list.add(Component.translatable("tooltip.pasterdream.terra_floating_island.desc1"));
        list.add(Component.translatable("tooltip.pasterdream.terra_floating_island.desc2"));
        list.add(Component.translatable("tooltip.pasterdream.terra_floating_island.desc3"));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        // Effects are handled in TerraBladeItem.tryFireSwordWave:
        // energy cost -0.05 (0.1 → 0.05), sword wave damage +30%, ignores i-frames
    }
}
