package com.pasterdream.pasterdreammod.compat.jei.shadowblastfurnacerecipe;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceRecipe;

import java.util.List;

public class ShadowBlastFurnaceJEIRecipe
{
    private final List<FluidIngredient> inputFluidIngredients;
    private final List<ItemIngredient> inputItemIngredients;
    private final List<ItemIngredient> outputItemIngredients;
    private final int processingTime;

    public ShadowBlastFurnaceJEIRecipe(ShadowBlastFurnaceRecipe recipe)
    {
        this.inputFluidIngredients = recipe.getInputFluidIngredients() != null ? recipe.getInputFluidIngredients() : List.of();
        this.inputItemIngredients = recipe.getInputItemIngredients() != null ? recipe.getInputItemIngredients() : List.of();
        this.outputItemIngredients = recipe.getOutputItemIngredients() != null ? recipe.getOutputItemIngredients() : List.of();
        this.processingTime = recipe.getProcessingTime();
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

    public int getProcessingTime()
    {
        return processingTime;
    }
}
