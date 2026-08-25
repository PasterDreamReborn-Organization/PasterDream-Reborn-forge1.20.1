package com.pasterdream.pasterdreammod.world.block.itemcontainer.openedmeltdreamcrystalchest;

import com.pasterdream.pasterdreammod.world.block.itemcontainer.ItemContainerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class OpenedMeltDreamCrystalChestBlock extends ItemContainerBlock
{
    public OpenedMeltDreamCrystalChestBlock(Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        return new OpenedMeltDreamCrystalChestBlockEntity(blockPosition, blockState);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        return box(0, 0, 0, 16, 15, 16);
    }
}
