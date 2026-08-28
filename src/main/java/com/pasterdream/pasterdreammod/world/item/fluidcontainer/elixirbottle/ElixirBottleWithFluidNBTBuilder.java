package com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ElixirBottleWithFluidNBTBuilder
{
    public static ItemStack builder(FluidStack fluidStack)
    {
        ItemStack elixirBottle = new ItemStack(ModItems.ELIXIR_BOTTLE.get());
        CompoundTag fluidTag = new CompoundTag();
        fluidStack.writeToNBT(fluidTag);
        CompoundTag NBT = new CompoundTag();
        NBT.put("Fluid", fluidTag);
        elixirBottle.setTag(NBT);

        return elixirBottle;
    }
}
