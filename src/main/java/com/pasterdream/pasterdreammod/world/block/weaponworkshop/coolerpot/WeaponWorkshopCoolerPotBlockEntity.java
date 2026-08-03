package com.pasterdream.pasterdreammod.world.block.weaponworkshop.coolerpot;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WeaponWorkshopCoolerPotBlockEntity extends BlockEntity
{
    public WeaponWorkshopCoolerPotBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.WEAPON_WORKSHOP_COOLER_POT.get(), pos, state);
    }
}
