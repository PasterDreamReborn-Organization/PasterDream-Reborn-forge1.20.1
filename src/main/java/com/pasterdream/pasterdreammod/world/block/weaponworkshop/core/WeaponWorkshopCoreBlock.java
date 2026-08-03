package com.pasterdream.pasterdreammod.world.block.weaponworkshop.core;

import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblockbenchblock.block.HorizontalDirectionalBlockBenchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WeaponWorkshopCoreBlock extends HorizontalDirectionalBlockBenchBlock
{
    public WeaponWorkshopCoreBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        Direction facing = state.getValue(FACING);
        if(facing == Direction.NORTH || facing == Direction.SOUTH)
        {
            return box(-3, 0, -1, 19, 13, 17);
        }
            else
            {
                return box(-1, 0, -3, 17, 13, 19);
            }
    }
}
