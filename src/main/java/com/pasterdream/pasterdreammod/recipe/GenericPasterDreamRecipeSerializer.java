package com.pasterdream.pasterdreammod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericPasterDreamRecipeSerializer<T extends GenericPasterDreamRecipe> implements RecipeSerializer<T>
{
    protected List<FluidIngredient> parseFluidIngredients(JsonObject json, String key)
    {
        List<FluidIngredient> list = new ArrayList<>();
        if (json.has(key))
        {
            JsonArray array = json.getAsJsonArray(key);
            for (var elem : array)
            {
                list.add(FluidIngredient.fromJson(elem));
            }
        }
        return list;
    }

    protected List<ItemIngredient> parseItemIngredients(JsonObject json, String key)
    {
        List<ItemIngredient> list = new ArrayList<>();
        if (json.has(key))
        {
            JsonArray array = json.getAsJsonArray(key);
            for (var elem : array)
            {
                list.add(ItemIngredient.fromJson(elem));
            }
        }
        return list;
    }

    protected int parseProcessingTime(JsonObject json)
    {
        if(json.has("processingTime"))
        {
            return json.get("processingTime").getAsInt();
        }
        return 0;
    }

    @Override
    public abstract T fromJson(ResourceLocation recipeId, JsonObject json);

    @Override
    public abstract T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer);

    @Override
    public abstract void toNetwork(FriendlyByteBuf buffer, T recipe);
}
