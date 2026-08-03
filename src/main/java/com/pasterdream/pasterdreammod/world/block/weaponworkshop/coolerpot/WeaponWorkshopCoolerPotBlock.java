package com.pasterdream.pasterdreammod.world.block.weaponworkshop.coolerpot;

import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.blockentity.HorizontalDirectionalBlockBenchBaseEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WeaponWorkshopCoolerPotBlock extends HorizontalDirectionalBlockBenchBaseEntityBlock
{
    public WeaponWorkshopCoolerPotBlock(Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        return new WeaponWorkshopCoolerPotBlockEntity(blockPosition, blockState);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        Direction facing = state.getValue(FACING);
        switch (facing)
        {
            case EAST : return box(1, 0, 3, 14, 14, 12);
            case SOUTH: return box(4, 0, 1, 13, 14, 14);
            case WEST : return box(2, 0, 4, 15, 14, 13);
            case NORTH: return box(3, 0, 2, 12, 14, 15);
            default   : return box(0, 0, 0, 16, 16, 16);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type)
    {
        return null;
    }
}
