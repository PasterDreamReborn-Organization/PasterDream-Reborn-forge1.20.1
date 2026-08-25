package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.windmoordesk;

import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.DeskBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class WindMoorDeskBlock extends DeskBlock
{
    public WindMoorDeskBlock(Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new WindMoorDeskBlockEntity(pos, state);
    }
}
