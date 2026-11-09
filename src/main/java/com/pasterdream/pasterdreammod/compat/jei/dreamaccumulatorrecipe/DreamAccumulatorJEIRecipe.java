package com.pasterdream.pasterdreammod.compat.jei.dreamaccumulatorrecipe;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

public class DreamAccumulatorJEIRecipe
{
    private final List<TagKey<Biome>> biomeTags;
    private final int time;
    private final ItemIngredient catalystIngredients;
    private final double catalystMultiplier;
    private final ItemStack output;

    public DreamAccumulatorJEIRecipe(DreamAccumulatorRecipe recipe)
    {
        this.biomeTags = recipe.getBiomeTags();
        this.time = recipe.getTime();
        this.catalystIngredients = recipe.getCatalyst();
        this.catalystMultiplier = recipe.getCatalystMultiplier();
        this.output = recipe.getOutput() != null ? recipe.getOutput() : ItemStack.EMPTY;
    }

    public List<TagKey<Biome>> getBiomeTags()
    {
        return biomeTags;
    }

    public int getTime()
    {
        return time;
    }

    public ItemIngredient getCatalystIngredients()
    {
        return catalystIngredients;
    }

    public double getCatalystMultiplier()
    {
        return catalystMultiplier;
    }

    public ItemStack getOutput()
    {
        return output;
    }
}
