package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.shadowdesk;

import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.DeskBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ShadowDeskBlock extends DeskBlock
{
    public ShadowDeskBlock(Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ShadowDeskBlockEntity(pos, state);
    }
}
