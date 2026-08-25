package com.pasterdream.pasterdreammod.world.block.itemcontainer.crate;

import com.pasterdream.pasterdreammod.world.block.itemcontainer.ItemContainerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class CrateBlockEntity extends ItemContainerBlockEntity
{
    public CrateBlockEntity(BlockEntityType<?> type, BlockPos blockPosition, BlockState state, String nameKey)
    {
        super(type, blockPosition, state, nameKey);
    }

    @Override
    public int setItemStackHandlerSize()
    {
        return 15;
    }
}
