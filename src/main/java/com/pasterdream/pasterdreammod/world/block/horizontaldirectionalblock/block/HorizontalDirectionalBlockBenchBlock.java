package com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.block;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

public abstract class HorizontalDirectionalBlockBenchBlock extends HorizontalDirectionalGenericBlock
{
    public HorizontalDirectionalBlockBenchBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.MODEL;
    }
}
