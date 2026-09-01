package com.pasterdream.pasterdreammod.helper.pasterdreamingredient;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

public class FluidStackWithoutAmount
{
    @Nullable
    private final Fluid fluid;
    @Nullable
    private final CompoundTag nbt;

    public FluidStackWithoutAmount(@Nullable Fluid fluid, @Nullable CompoundTag nbt)
    {
        this.fluid = fluid;
        this.nbt = nbt;
    }

    public Fluid getFluid()
    {
        return fluid;
    }

    public CompoundTag getNbt()
    {
        return nbt;
    }

    public boolean hasNbt()
    {
        return nbt != null;
    }

    public static boolean isSame(FluidStackWithoutAmount fluidStackWithoutAmount0, FluidStackWithoutAmount fluidStackWithoutAmount1)
    {
        if(fluidStackWithoutAmount0.hasNbt() && fluidStackWithoutAmount1.hasNbt())
        {
            return fluidStackWithoutAmount0.getFluid() == fluidStackWithoutAmount1.getFluid() && fluidStackWithoutAmount0.getNbt().equals(fluidStackWithoutAmount1.getNbt());
        }
            else
            {
                return fluidStackWithoutAmount0.getFluid() == fluidStackWithoutAmount1.getFluid();
            }
    }
}
