package com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WeaponWorkshopBlastFurnaceAddonBlockEntity extends BlockEntity
{
    public WeaponWorkshopBlastFurnaceAddonBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.WEAPON_WORKSHOP_BLAST_FURNACE_ADDON.get(), pos, state);
    }
}
