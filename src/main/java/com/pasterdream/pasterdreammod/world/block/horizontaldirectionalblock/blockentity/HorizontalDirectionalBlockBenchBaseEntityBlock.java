package com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.blockentity;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

public abstract class HorizontalDirectionalBlockBenchBaseEntityBlock extends HorizontalDirectionalGenericBaseEntityBlock
{
    public HorizontalDirectionalBlockBenchBaseEntityBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.MODEL;
    }
}
