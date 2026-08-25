package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk;

import com.pasterdream.pasterdreammod.world.block.itemcontainer.ItemContainerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class DeskBlockEntity extends ItemContainerBlockEntity
{
    public DeskBlockEntity(BlockEntityType<?> type, BlockPos blockPosition, BlockState state, String nameKey)
    {
        super(type, blockPosition, state, nameKey);
    }

    @Override
    public int setItemStackHandlerSize()
    {
        return 1;
    }
}
