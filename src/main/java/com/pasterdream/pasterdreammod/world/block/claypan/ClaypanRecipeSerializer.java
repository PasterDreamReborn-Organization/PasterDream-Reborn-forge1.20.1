package com.pasterdream.pasterdreammod.world.block.claypan;

import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.recipe.GenericPasterDreamRecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ClaypanRecipeSerializer extends GenericPasterDreamRecipeSerializer<ClaypanRecipe>
{
    @Override
    public ClaypanRecipe fromJson(ResourceLocation recipeId, JsonObject json)
    {
        List<FluidIngredient> fluidInputs = parseFluidIngredients(json, "fluidInputs");
        List<ItemIngredient> itemOutputs = parseItemIngredients(json, "itemOutputs");
        int processingTime = parseProcessingTime(json);
        return new ClaypanRecipe(recipeId, fluidInputs, itemOutputs, processingTime);
    }

    @Override
    public ClaypanRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
    {
        int inputfluidCount = buffer.readVarInt();
        List<FluidIngredient> fluidInputs = new ArrayList<>();
        for (int i = 0; i < inputfluidCount; i++)
        {
            fluidInputs.add(FluidIngredient.fromNetwork(buffer));
        }

        int outputItemCount = buffer.readVarInt();
        List<ItemIngredient> itemOutputs = new ArrayList<>();
        for (int i = 0; i < outputItemCount; i++)
        {
            itemOutputs.add(ItemIngredient.fromNetwork(buffer));
        }
        int processingTime = buffer.readVarInt();
        return new ClaypanRecipe(recipeId, fluidInputs, itemOutputs, processingTime);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ClaypanRecipe recipe)
    {
        buffer.writeVarInt(recipe.getInputFluidIngredients().size());
        for (FluidIngredient fluidIngredient : recipe.getInputFluidIngredients())
        {
            fluidIngredient.toNetwork(buffer);
        }

        buffer.writeVarInt(recipe.getOutputItemIngredients().size());
        for (ItemIngredient ingredient : recipe.getOutputItemIngredients())
        {
            ingredient.toNetwork(buffer);
        }
        buffer.writeVarInt(recipe.getProcessingTime());
    }
}
