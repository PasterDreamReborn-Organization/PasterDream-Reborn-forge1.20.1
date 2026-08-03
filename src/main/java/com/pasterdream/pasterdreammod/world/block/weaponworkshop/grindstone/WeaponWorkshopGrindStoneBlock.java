package com.pasterdream.pasterdreammod.world.block.weaponworkshop.grindstone;

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

public class WeaponWorkshopGrindStoneBlock extends HorizontalDirectionalBlockBenchBaseEntityBlock
{
    public WeaponWorkshopGrindStoneBlock(Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        return new WeaponWorkshopGrindStoneBlockEntity(blockPosition, blockState);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        Direction facing = state.getValue(FACING);
        switch (facing)
        {
            case EAST : return box(0, 0, -1, 18, 20, 17);
            case SOUTH: return box(-1, 0, 0, 17, 20, 18);
            case WEST : return box(-2, 0, -1, 16, 20, 17);
            case NORTH: return box(-1, 0, -2, 17, 20, 16);
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
