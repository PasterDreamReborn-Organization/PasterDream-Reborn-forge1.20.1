package com.pasterdream.pasterdreammod.world.block.itemcontainer.crate;

import com.pasterdream.pasterdreammod.world.block.itemcontainer.ItemContainerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class CrateBlock extends ItemContainerBlock
{
    public CrateBlock(Properties properties)
    {
        super(properties);
    }

    public abstract VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context);
}
