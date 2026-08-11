package com.pasterdream.pasterdreammod.world.block.shadowblastfurnace;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.GenericPasterDreamRecipe;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.GenericPasterDreamRecipeMatchResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

public class ShadowBlastFurnaceRecipe extends GenericPasterDreamRecipe
{
    public ShadowBlastFurnaceRecipe(ResourceLocation id, List<FluidIngredient> fluidInputs, List<ItemIngredient> itemInputs, List<ItemIngredient> itemOutputs, int processingTime)
    {
        super(id, fluidInputs, itemInputs, List.of(), itemOutputs, processingTime);
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        return false;
    }

    @Override
    public Optional<GenericPasterDreamRecipeMatchResult> matchesWithSlots(List<FluidStack> fluidStacks, List<ItemStack> itemStacks)
    {
        return super.matchesWithSlots(fluidStacks, itemStacks);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.SHADOW_BLAST_FURNACE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.SHADOW_BLAST_FURNACE.get();
    }
}
