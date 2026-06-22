package com.pasterdream.pasterdreammod.compat.jei.claypanrecipe;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanRecipe;

import java.util.List;

public class ClaypanJEIRecipe
{
    private final List<FluidIngredient> inputFluidIngredients;
    private final List<ItemIngredient> outputItemIngredients;
    private final int processingTime;

    public ClaypanJEIRecipe(ClaypanRecipe recipe)
    {
        this.inputFluidIngredients = recipe.getInputFluidIngredients() != null ? recipe.getInputFluidIngredients() : List.of();
        this.outputItemIngredients = recipe.getOutputItemIngredients() != null ? recipe.getOutputItemIngredients() : List.of();
        this.processingTime = recipe.getProcessingTime();
    }

    public List<FluidIngredient> getInputFluidIngredients()
    {
        return inputFluidIngredients;
    }

    public List<ItemIngredient> getOutputItemIngredients()
    {
        return outputItemIngredients;
    }

    public int getProcessingTime()
    {
        return processingTime;
    }
}
