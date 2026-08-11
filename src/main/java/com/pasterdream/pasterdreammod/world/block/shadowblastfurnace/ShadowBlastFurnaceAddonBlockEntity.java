package com.pasterdream.pasterdreammod.world.block.shadowblastfurnace;

import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition._3x3x3_CalculatePartPosition;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

public class ShadowBlastFurnaceAddonBlockEntity extends BlockEntity
{
    public ShadowBlastFurnaceAddonBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.SHADOW_BLAST_FURNACE_ADDON.get(), pos, state);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        BlockPos mainPosition = _3x3x3_CalculatePartPosition.getMainPosFromAddon(getBlockPos(), getBlockState().getValue(ShadowBlastFurnaceBlock.FACING), getBlockState().getValue(ShadowBlastFurnaceBlock.PART));
        BlockEntity main = level.getBlockEntity(mainPosition);

        if (main != null)
        {
            return main.getCapability(cap, side);
        }

        return super.getCapability(cap, side);
    }
}
