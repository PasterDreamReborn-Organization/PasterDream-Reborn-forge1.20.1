package com.pasterdream.pasterdreammod.world.block.doll.uuzdoll;

import com.pasterdream.pasterdreammod.world.block.doll.DollBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class UUZDollBlock extends DollBlock
{
    public UUZDollBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new UUZDollBlockEntity(pos, state);
    }
}
