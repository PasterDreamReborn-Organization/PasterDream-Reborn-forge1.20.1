package com.pasterdream.pasterdreammod.world.item.modelbreakparticleproviderblockitem;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModelBreakParticleProviderBlockItem extends BlockItem
{
    public ModelBreakParticleProviderBlockItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.此方块仅用于提供粒子效果，生存模式无法获取"));
    }
}
