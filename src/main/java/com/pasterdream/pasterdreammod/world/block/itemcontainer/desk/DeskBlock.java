package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk;

import com.pasterdream.pasterdreammod.world.block.itemcontainer.ItemContainerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class DeskBlock extends ItemContainerBlock
{
    public DeskBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        return box(2, 0, 2, 14, 12, 14);
    }
}
