package com.pasterdream.pasterdreammod.recipe.dreamcauldron;

import com.pasterdream.pasterdreammod.helper.fluidingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.recipematchresult.DreamCauldronRecipeMatchResult;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

public interface IDreamCauldronRecipe extends Recipe<Container>
{
    List<FluidIngredient> getFluidIngredients();
    List<Ingredient> getItemIngredients();
    ItemStack getResultItem();

    boolean matches(List<FluidStack> fluidStacks, List<ItemStack> itemStacks);

    Optional<DreamCauldronRecipeMatchResult> matchesWithSlots(List<FluidStack> fluidStacks, List<ItemStack> itemStacks);
}
