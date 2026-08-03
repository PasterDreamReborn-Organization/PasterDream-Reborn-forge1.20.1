package com.pasterdream.pasterdreammod.world.block.weaponworkshop.grindstone;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WeaponWorkshopGrindStoneBlockEntity extends BlockEntity
{
    public WeaponWorkshopGrindStoneBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.WEAPON_WORKSHOP_GRIND_STONE.get(), pos, state);
    }
}
