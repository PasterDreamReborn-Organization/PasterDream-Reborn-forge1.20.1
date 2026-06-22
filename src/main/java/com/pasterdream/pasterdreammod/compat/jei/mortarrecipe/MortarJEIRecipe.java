package com.pasterdream.pasterdreammod.compat.jei.mortarrecipe;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarRecipe;

import java.util.List;

public class MortarJEIRecipe
{
    private final List<FluidIngredient> inputFluidIngredients;
    private final List<ItemIngredient> inputItemIngredients;
    private final List<FluidIngredient> outputFluidIngredients;
    private final List<ItemIngredient> outputItemIngredients;

    public MortarJEIRecipe(MortarRecipe recipe)
    {
        inputFluidIngredients = recipe.getInputFluidIngredients() != null ? recipe.getInputFluidIngredients() : List.of();
        inputItemIngredients = recipe.getInputItemIngredients() != null ? recipe.getInputItemIngredients() : List.of();
        outputFluidIngredients = recipe.getOutputFluidIngredients() != null ? recipe.getOutputFluidIngredients() : List.of();
        outputItemIngredients = recipe.getOutputItemIngredients() != null ? recipe.getOutputItemIngredients() : List.of();
    }

    public List<FluidIngredient> getInputFluidIngredients()
    {
        return inputFluidIngredients;
    }

    public List<ItemIngredient> getInputItemIngredients()
    {
        return inputItemIngredients;
    }

    public List<FluidIngredient> getOutputFluidIngredients()
    {
        return outputFluidIngredients;
    }

    public List<ItemIngredient> getOutputItemIngredients()
    {
        return outputItemIngredients;
    }
}
