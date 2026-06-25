package com.pasterdream.pasterdreammod.world.block.doll.qymdoll;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.world.block.doll.DollBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class QYMDollBlockEntity extends DollBlockEntity
{
    public QYMDollBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.QYM_DOLL.get(), pos, state, "block." + PasterDreamMod.MOD_ID + ".qym_doll");
    }
}
