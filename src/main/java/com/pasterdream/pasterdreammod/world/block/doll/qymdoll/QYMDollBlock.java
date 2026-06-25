package com.pasterdream.pasterdreammod.world.block.doll.qymdoll;

import com.pasterdream.pasterdreammod.world.block.doll.DollBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class QYMDollBlock extends DollBlock
{
    public QYMDollBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new QYMDollBlockEntity(pos, state);
    }
}
