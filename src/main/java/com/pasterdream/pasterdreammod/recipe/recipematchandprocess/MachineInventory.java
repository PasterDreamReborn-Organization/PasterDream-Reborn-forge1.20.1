package com.pasterdream.pasterdreammod.recipe.recipematchandprocess;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public record MachineInventory(List<ItemStack> inputItemStacks, List<FluidStack> inputFluidStacks, List<ItemStack> outputItemStacks, List<FluidStack> outputFluidStacks) {}
