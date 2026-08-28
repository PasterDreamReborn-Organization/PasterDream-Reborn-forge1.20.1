package com.pasterdream.pasterdreammod.world.block.dreamaccumulator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class DreamAccumulatorRecipeSerializer implements RecipeSerializer<DreamAccumulatorRecipe>
{
    @Override
    public DreamAccumulatorRecipe fromJson(ResourceLocation id, JsonObject json)
    {
        List<TagKey<Biome>> biomeTags = new ArrayList<>();
        if (json.has("biomeTags"))
        {
            JsonArray array = json.getAsJsonArray("biomeTags");
            for (JsonElement element : array)
            {
                ResourceLocation tagId = ResourceLocation.parse(element.getAsString());
                biomeTags.add(TagKey.create(Registries.BIOME, tagId));
            }
        }

        int time = json.get("time").getAsInt();
        ItemIngredient catalyst = ItemIngredient.fromJson(json.get("catalyst"));
        float multiplier = json.get("catalystMultiplier").getAsFloat();
        ItemStack output = ShapedRecipe.itemStackFromJson(json.getAsJsonObject("output"));

        return new DreamAccumulatorRecipe(id, biomeTags, time, catalyst, multiplier, output);
    }

    @Override
    public DreamAccumulatorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
    {
        int size = buffer.readVarInt();
        List<TagKey<Biome>> biomeTags = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
        {
            biomeTags.add(TagKey.create(Registries.BIOME, buffer.readResourceLocation()));
        }

        int time = buffer.readVarInt();
        ItemIngredient catalyst = ItemIngredient.fromNetwork(buffer);
        double catalystMultiplier = buffer.readDouble();
        ItemStack output = buffer.readItem();
        return new DreamAccumulatorRecipe(id, biomeTags, time, catalyst, catalystMultiplier, output);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, DreamAccumulatorRecipe recipe)
    {
        List<TagKey<Biome>> tags = recipe.getBiomeTags();
        buf.writeVarInt(tags.size());
        for (TagKey<Biome> tag : tags)
        {
            buf.writeResourceLocation(tag.location());
        }

        buf.writeVarInt(recipe.getTime());
        recipe.getCatalyst().toNetwork(buf);
        buf.writeDouble(recipe.getCatalystMultiplier());
        buf.writeItem(recipe.getOutput());
    }
}
