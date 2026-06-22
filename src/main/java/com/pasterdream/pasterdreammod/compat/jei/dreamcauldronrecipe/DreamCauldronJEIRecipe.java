package com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronRecipe;

import java.util.List;

public class DreamCauldronJEIRecipe
{
    private final List<FluidIngredient> inputFluidIngredients;
    private final List<ItemIngredient> inputItemIngredients;
    private final List<ItemIngredient> outputItemIngredients;

    public DreamCauldronJEIRecipe(DreamCauldronRecipe recipe)
    {
        this.inputFluidIngredients = recipe.getInputFluidIngredients() != null ? recipe.getInputFluidIngredients() : List.of();
        this.inputItemIngredients = recipe.getInputItemIngredients() != null ? recipe.getInputItemIngredients() : List.of();
        this.outputItemIngredients = recipe.getOutputItemIngredients() != null ? recipe.getOutputItemIngredients() : List.of();
    }

    public List<FluidIngredient> getInputFluidIngredients()
    {
        return inputFluidIngredients;
    }

    public List<ItemIngredient> getInputItemIngredients()
    {
        return inputItemIngredients;
    }

    public List<ItemIngredient> getOutputItemIngredients()
    {
        return outputItemIngredients;
    }
}
