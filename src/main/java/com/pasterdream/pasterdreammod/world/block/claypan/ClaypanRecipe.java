package com.pasterdream.pasterdreammod.world.block.claypan;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.GenericPasterDreamRecipe;
import com.pasterdream.pasterdreammod.recipe.GenericPasterDreamRecipeMatchResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

public class ClaypanRecipe extends GenericPasterDreamRecipe
{
    public ClaypanRecipe(ResourceLocation id, List<FluidIngredient> fluidInputs, List<ItemIngredient> itemOutputs, int processingTime)
    {
        super(id, fluidInputs, List.of(), List.of(), itemOutputs, processingTime);
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        return false;
    }

    @Override
    public Optional<GenericPasterDreamRecipeMatchResult> matchesWithSlots(List<FluidStack> fluidStacks, List<ItemStack> itemStacks)
    {
        return super.matchesWithSlots(fluidStacks, List.of());
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.CLAYPAN_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.CLAYPAN.get();
    }
}
