package com.pasterdream.pasterdreammod.recipe.mortar;

import com.pasterdream.pasterdreammod.helper.fluidingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.recipematchresult.MortarRecipeMatchResult;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

public interface IMortarRecipe extends Recipe<Container>
{
    List<FluidIngredient> getInputFluidIngredients();
    List<Ingredient> getInputItemIngredients();
    List<FluidIngredient> getOutputFluidIngredients();
    List<Ingredient> getOutputItemIngredients();

    boolean matches(List<FluidStack> fluidStacks, List<ItemStack> itemStacks);

    Optional<MortarRecipeMatchResult> matchesWithSlots(List<FluidStack> fluidStacks, List<ItemStack> itemStacks);
}
