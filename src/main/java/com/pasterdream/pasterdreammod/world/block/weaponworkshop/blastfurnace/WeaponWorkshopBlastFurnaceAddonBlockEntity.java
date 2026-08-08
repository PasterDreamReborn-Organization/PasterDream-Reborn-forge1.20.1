package com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace;

import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition._2x4x2_CalculatePartPosition;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

public class WeaponWorkshopBlastFurnaceAddonBlockEntity extends BlockEntity
{
    public WeaponWorkshopBlastFurnaceAddonBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.WEAPON_WORKSHOP_BLAST_FURNACE_ADDON.get(), pos, state);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        BlockPos mainPosition = _2x4x2_CalculatePartPosition.getMainPosFromAddon(getBlockPos(), getBlockState().getValue(WeaponWorkshopBlastFurnaceBlock.FACING), getBlockState().getValue(WeaponWorkshopBlastFurnaceBlock.PART));
        BlockEntity main = level.getBlockEntity(mainPosition);

        if (main != null)
        {
            return main.getCapability(cap, side);
        }

        return super.getCapability(cap, side);
    }
}
