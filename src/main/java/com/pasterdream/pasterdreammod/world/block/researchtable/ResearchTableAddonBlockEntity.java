package com.pasterdream.pasterdreammod.world.block.researchtable;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

public class ResearchTableAddonBlockEntity extends BlockEntity
{
    public ResearchTableAddonBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.RESEARCH_TABLE_ADDON.get(), pos, state);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        BlockPos mainPosition = getBlockPos().relative(getBlockState().getValue(ResearchTableBlock.FACING).getClockWise());
        BlockEntity main = level.getBlockEntity(mainPosition);

        if (main != null)
        {
            return main.getCapability(cap, side);
        }

        return super.getCapability(cap, side);
    }
}
