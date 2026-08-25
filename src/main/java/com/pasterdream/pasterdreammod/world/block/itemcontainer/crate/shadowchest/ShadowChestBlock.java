package com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.shadowchest;

import com.pasterdream.pasterdreammod.helper.multiblockproperties.voxelshapecalculator.VoxelShapeCalculator;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.CrateBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShadowChestBlock extends CrateBlock
{
    public ShadowChestBlock(Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ShadowChestBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        List<VoxelShape> ListVoxelShape = VoxelShapeCalculator.calculateAllDirectionVoxelShapeFromEastVoxelShape(4 / 16.0, 0, 2 / 16.0, 12 / 16.0, 6 / 16.0, 14 / 16.0);
        Direction facing = state.getValue(FACING);
        return switch (facing)
        {
            case EAST  -> ListVoxelShape.get(0);
            case SOUTH -> ListVoxelShape.get(1);
            case WEST  -> ListVoxelShape.get(2);
            case NORTH -> ListVoxelShape.get(3);
            default -> box(0, 0, 0, 16, 16, 16);
        };
    }
}
