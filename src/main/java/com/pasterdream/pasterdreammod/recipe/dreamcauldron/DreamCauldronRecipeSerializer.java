package com.pasterdream.pasterdreammod.recipe.dreamcauldron;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.helper.fluidingredient.FluidIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DreamCauldronRecipeSerializer implements RecipeSerializer<DreamCauldronRecipe>
{
    public static final DreamCauldronRecipeSerializer INSTANCE = new DreamCauldronRecipeSerializer();

    @Override
    public DreamCauldronRecipe fromJson(ResourceLocation recipeId, JsonObject json)
    {
        //解析流体输入
        List<FluidIngredient> fluidIngredients = new ArrayList<>();
        if (json.has("fluidInputs"))
        {
            JsonArray fluidArray = json.getAsJsonArray("fluidInputs");
            for (var elem : fluidArray)
            {
                JsonObject object = elem.getAsJsonObject();
                int amount = object.get("amount").getAsInt();
                FluidIngredient fIng;
                if (object.has("fluid"))
                {
                    String fluidName = object.get("fluid").getAsString();
                    Fluid fluid = ForgeRegistries.FLUIDS.getValue(ResourceLocation.parse(fluidName));
                    fIng = FluidIngredient.of(fluid, amount);
                }
                else
                    if (object.has("fluidtag"))
                    {
                        String tagName = object.get("fluidtag").getAsString();
                        TagKey<Fluid> tag = TagKey.create(net.minecraft.core.registries.Registries.FLUID, ResourceLocation.parse(tagName));
                        fIng = FluidIngredient.of(tag, amount);
                    }
                        else
                        {
                            throw new IllegalArgumentException("法术工厂配方存在无法匹配的流体输入");
                        }
                fluidIngredients.add(fIng);
            }
        }

        //解析物品输入
        List<Ingredient> itemIngredients = new ArrayList<>();
        if (json.has("itemInputs"))
        {
            JsonArray itemArray = json.getAsJsonArray("itemInputs");
            for (var elem : itemArray)
            {
                Ingredient ing = Ingredient.fromJson(elem);
                itemIngredients.add(ing);
            }
        }

        //解析输出
        JsonObject resultObj = json.getAsJsonObject("result");
        ItemStack output = new ItemStack(ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(resultObj.get("item").getAsString())), resultObj.has("count") ? resultObj.get("count").getAsInt() : 1);

        return new DreamCauldronRecipe(recipeId, fluidIngredients, itemIngredients, output);
    }

    @Nullable
    @Override
    public DreamCauldronRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
    {
        int fluidCount = buffer.readVarInt();
        List<FluidIngredient> fluidIngredients = new ArrayList<>(fluidCount);
        for (int i = 0; i < fluidCount; i++)
        {
            boolean isTag = buffer.readBoolean();
            int amount = buffer.readVarInt();
            if (isTag)
            {
                ResourceLocation tagId = buffer.readResourceLocation();
                TagKey<Fluid> tag = TagKey.create(net.minecraft.core.registries.Registries.FLUID, tagId);
                fluidIngredients.add(FluidIngredient.of(tag, amount));
            }
                else
                {
                    ResourceLocation fluidId = buffer.readResourceLocation();
                    Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
                    fluidIngredients.add(FluidIngredient.of(fluid, amount));
                }
        }

        int itemCount = buffer.readVarInt();
        List<Ingredient> itemIngredients = new ArrayList<>(itemCount);
        for (int i = 0; i < itemCount; i++)
        {
            itemIngredients.add(Ingredient.fromNetwork(buffer));
        }

        ItemStack output = buffer.readItem();

        return new DreamCauldronRecipe(recipeId, fluidIngredients, itemIngredients, output);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, DreamCauldronRecipe recipe)
    {
        buffer.writeVarInt(recipe.getFluidIngredients().size());
        for (FluidIngredient fluidIngredient : recipe.getFluidIngredients())
        {
            buffer.writeBoolean(fluidIngredient.isTag());
            buffer.writeVarInt(fluidIngredient.getAmount());
            if (fluidIngredient.isTag())
            {
                buffer.writeResourceLocation(fluidIngredient.getTag().location());
            }
                else
                {
                    buffer.writeResourceLocation(ForgeRegistries.FLUIDS.getKey(fluidIngredient.getFluid()));
                }
        }

        buffer.writeVarInt(recipe.getItemIngredients().size());
        for (Ingredient ingredient : recipe.getItemIngredients())
        {
            ingredient.toNetwork(buffer);
        }

        buffer.writeItem(recipe.getResultItem(null));
    }
}
