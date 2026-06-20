package com.pasterdream.pasterdreammod.recipe;

import com.pasterdream.pasterdreammod.helper.fluidingredient.FluidIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

public interface IGenericPasterDreamRecipe
{
    List<FluidIngredient> getInputFluidIngredients();
    List<Ingredient> getInputItemIngredients();
    List<FluidIngredient> getOutputFluidIngredients();
    List<Ingredient> getOutputItemIngredients();
    int getProcessingTime();

    boolean matches(List<FluidStack> fluidStacks, List<ItemStack> itemStacks);
    Optional<GenericPasterDreamRecipeMatchResult> matchesWithSlots(List<FluidStack> fluidStacks, List<ItemStack> itemStacks);
}
