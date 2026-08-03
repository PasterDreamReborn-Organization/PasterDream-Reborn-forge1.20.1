package com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.blockentity;

import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public abstract class HorizontalDirectionalGeckolibBaseEntityBlock extends HorizontalDirectionalGenericBaseEntityBlock
{
    public HorizontalDirectionalGeckolibBaseEntityBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
