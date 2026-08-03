package com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblockbenchblock.block;

import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public abstract class HorizontalDirectionalGeckolibBlock extends HorizontalDirectionalGenericBlock
{
    public HorizontalDirectionalGeckolibBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
