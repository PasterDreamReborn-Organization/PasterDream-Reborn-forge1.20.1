package com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket;

import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.CrateBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PicnicBasketBlock extends CrateBlock
{
    public PicnicBasketBlock(Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new PicnicBasketBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        Direction facing = state.getValue(FACING);
        if(facing == Direction.NORTH || facing == Direction.SOUTH)
        {
            return box(0, 0, 3, 16, 9, 13);
        }
            else
            {
                return box(3, 0, 0, 13, 9, 16);
            }
    }
}
