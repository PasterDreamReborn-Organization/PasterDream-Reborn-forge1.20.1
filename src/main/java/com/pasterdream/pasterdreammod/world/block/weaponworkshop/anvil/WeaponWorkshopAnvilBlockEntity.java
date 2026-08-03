package com.pasterdream.pasterdreammod.world.block.weaponworkshop.anvil;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WeaponWorkshopAnvilBlockEntity extends BlockEntity
{
    public WeaponWorkshopAnvilBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.WEAPON_WORKSHOP_ANVIL.get(), pos, state);
    }
}
