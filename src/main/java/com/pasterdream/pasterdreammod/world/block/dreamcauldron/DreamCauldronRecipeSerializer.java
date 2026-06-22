package com.pasterdream.pasterdreammod.world.block.dreamcauldron;

import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.recipe.GenericPasterDreamRecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class DreamCauldronRecipeSerializer extends GenericPasterDreamRecipeSerializer<DreamCauldronRecipe>
{
    @Override
    public DreamCauldronRecipe fromJson(ResourceLocation recipeId, JsonObject json)
    {
        List<FluidIngredient> fluidInputs = parseFluidIngredients(json, "fluidInputs");
        List<ItemIngredient> itemInputs = parseItemIngredients(json, "itemInputs");
        List<ItemIngredient> itemOutputs = parseItemIngredients(json, "itemOutputs");
        return new DreamCauldronRecipe(recipeId, fluidInputs, itemInputs, itemOutputs);
    }

    @Override
    public DreamCauldronRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
    {
        int fluidCount = buffer.readVarInt();
        List<FluidIngredient> fluidInputs = new ArrayList<>();
        for (int i = 0; i < fluidCount; i++)
        {
            fluidInputs.add(FluidIngredient.fromNetwork(buffer));
        }

        int inputItemCount = buffer.readVarInt();
        List<ItemIngredient> itemInputs = new ArrayList<>();
        for (int i = 0; i < inputItemCount; i++)
        {
            itemInputs.add(ItemIngredient.fromNetwork(buffer));
        }

        int outputItemCount = buffer.readVarInt();
        List<ItemIngredient> itemOutputs = new ArrayList<>();
        for (int i = 0; i < outputItemCount; i++)
        {
            itemOutputs.add(ItemIngredient.fromNetwork(buffer));
        }

        return new DreamCauldronRecipe(recipeId, fluidInputs, itemInputs, itemOutputs);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, DreamCauldronRecipe recipe)
    {
        buffer.writeVarInt(recipe.getInputFluidIngredients().size());
        for (FluidIngredient fluidIngredient : recipe.getInputFluidIngredients())
        {
            fluidIngredient.toNetwork(buffer);
        }

        buffer.writeVarInt(recipe.getInputItemIngredients().size());
        for (ItemIngredient ingredient : recipe.getInputItemIngredients())
        {
            ingredient.toNetwork(buffer);
        }

        buffer.writeVarInt(recipe.getOutputItemIngredients().size());
        for (ItemIngredient ingredient : recipe.getOutputItemIngredients())
        {
            ingredient.toNetwork(buffer);
        }
    }
}
