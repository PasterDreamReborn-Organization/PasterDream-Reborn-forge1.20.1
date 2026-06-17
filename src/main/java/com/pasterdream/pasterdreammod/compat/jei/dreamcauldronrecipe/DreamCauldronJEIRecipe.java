package com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe;

import com.pasterdream.pasterdreammod.helper.fluidingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.recipe.dreamcauldron.IDreamCauldronRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class DreamCauldronJEIRecipe
{
    private final List<FluidIngredient> fluidIngredients;
    private final List<Ingredient> itemIngredients;
    private final ItemStack output;

    public DreamCauldronJEIRecipe(IDreamCauldronRecipe recipe)
    {
        this.fluidIngredients = recipe.getFluidIngredients();
        this.itemIngredients = recipe.getItemIngredients();
        this.output = recipe.getResultItem(null);
    }

    public List<FluidIngredient> getFluidIngredients()
    {
        return fluidIngredients;
    }

    public List<Ingredient> getItemIngredients()
    {
        return itemIngredients;
    }

    public ItemStack getOutput()
    {
        return output;
    }
}
