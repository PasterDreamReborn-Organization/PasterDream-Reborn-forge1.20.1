package com.pasterdream.pasterdreammod.world.block.doll.uuzdoll;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.world.block.doll.DollBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class UUZDollBlockEntity extends DollBlockEntity
{
    public UUZDollBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.UUZ_DOLL.get(), pos, state, "block." + PasterDreamMod.MOD_ID + ".uuz_doll");
    }
}
