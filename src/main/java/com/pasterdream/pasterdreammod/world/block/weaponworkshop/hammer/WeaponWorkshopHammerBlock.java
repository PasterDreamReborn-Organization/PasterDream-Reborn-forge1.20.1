package com.pasterdream.pasterdreammod.world.block.weaponworkshop.hammer;

import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.block.HorizontalDirectionalBlockBenchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WeaponWorkshopHammerBlock extends HorizontalDirectionalBlockBenchBlock
{
    public WeaponWorkshopHammerBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        Direction facing = state.getValue(FACING);
        if(facing == Direction.NORTH || facing == Direction.SOUTH)
        {
            return box(3, 0, 5, 13, 26, 11);
        }
        else
        {
            return box(5, 0, 3, 11, 26, 13);
        }
    }
}
