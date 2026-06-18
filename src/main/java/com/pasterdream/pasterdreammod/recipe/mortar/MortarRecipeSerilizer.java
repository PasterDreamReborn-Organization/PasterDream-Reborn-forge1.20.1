package com.pasterdream.pasterdreammod.recipe.mortar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pasterdream.pasterdreammod.helper.fluidingredient.FluidIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MortarRecipeSerilizer implements RecipeSerializer<MortarRecipe>
{
    @Override
    public MortarRecipe fromJson(ResourceLocation recipeId, JsonObject json)
    {
        //解析流体输入
        List<FluidIngredient> inputFluidIngredients = new ArrayList<>();
        if (json.has("fluidInputs"))
        {
            JsonArray fluidArray = json.getAsJsonArray("fluidInputs");
            for (var elem : fluidArray)
            {
                JsonObject object = elem.getAsJsonObject();
                int amount = object.get("amount").getAsInt();
                FluidIngredient fluidIngredient;
                if (object.has("fluid"))
                {
                    String fluidName = object.get("fluid").getAsString();
                    Fluid fluid = ForgeRegistries.FLUIDS.getValue(ResourceLocation.parse(fluidName));
                    fluidIngredient = FluidIngredient.of(fluid, amount);
                }
                else
                    if (object.has("fluidtag"))
                    {
                        String tagName = object.get("fluidtag").getAsString();
                        TagKey<Fluid> tag = TagKey.create(net.minecraft.core.registries.Registries.FLUID, ResourceLocation.parse(tagName));
                        fluidIngredient = FluidIngredient.of(tag, amount);
                    }
                        else
                        {
                            throw new IllegalArgumentException("研钵配方存在无法匹配的流体输入");
                        }
                inputFluidIngredients.add(fluidIngredient);
            }
        }

        //解析物品输入
        List<Ingredient> inputItemIngredients = new ArrayList<>();
        if (json.has("itemInputs"))
        {
            JsonArray itemArray = json.getAsJsonArray("itemInputs");
            for (var elem : itemArray)
            {
                Ingredient ing = Ingredient.fromJson(elem);
                inputItemIngredients.add(ing);
            }
        }

        //解析流体输出
        List<FluidIngredient> outputFluidIngredients = new ArrayList<>();
        if (json.has("fluidOutputs"))
        {
            JsonArray fluidArray = json.getAsJsonArray("fluidOutputs");
            for (var elem : fluidArray)
            {
                JsonObject object = elem.getAsJsonObject();
                int amount = object.get("amount").getAsInt();
                FluidIngredient fluidIngredient;
                if (object.has("fluid"))
                {
                    String fluidName = object.get("fluid").getAsString();
                    Fluid fluid = ForgeRegistries.FLUIDS.getValue(ResourceLocation.parse(fluidName));
                    fluidIngredient = FluidIngredient.of(fluid, amount);
                }
                    else
                    {
                        throw new IllegalArgumentException("研钵配方存在无法匹配的流体输入");
                    }
                outputFluidIngredients.add(fluidIngredient);
            }
        }

        //解析物品输出
        List<Ingredient> outputItemIngredients = new ArrayList<>();
        if (json.has("itemOutputs"))
        {
            JsonArray itemArray = json.getAsJsonArray("itemOutputs");
            for (var elem : itemArray)
            {
                Ingredient ing = Ingredient.fromJson(elem);
                outputItemIngredients.add(ing);
            }
        }

        return new MortarRecipe(recipeId, inputFluidIngredients, inputItemIngredients, outputFluidIngredients, outputItemIngredients);
    }

    @Nullable
    @Override
    public MortarRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
    {
        int inputFluidCount = buffer.readVarInt();
        List<FluidIngredient> inputFluidIngredients = new ArrayList<>(inputFluidCount);
        for (int i = 0; i < inputFluidCount; i++)
        {
            boolean isTag = buffer.readBoolean();
            int amount = buffer.readVarInt();
            if (isTag)
            {
                ResourceLocation tagId = buffer.readResourceLocation();
                TagKey<Fluid> tag = TagKey.create(net.minecraft.core.registries.Registries.FLUID, tagId);
                inputFluidIngredients.add(FluidIngredient.of(tag, amount));
            }
                else
                {
                    ResourceLocation fluidId = buffer.readResourceLocation();
                    Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
                    inputFluidIngredients.add(FluidIngredient.of(fluid, amount));
                }
        }

        int inputItemCount = buffer.readVarInt();
        List<Ingredient> inputItemIngredients = new ArrayList<>(inputItemCount);
        for (int i = 0; i < inputItemCount; i++)
        {
            inputItemIngredients.add(Ingredient.fromNetwork(buffer));
        }

        int outputFluidCount = buffer.readVarInt();
        List<FluidIngredient> outputFluidIngredients = new ArrayList<>(outputFluidCount);
        for (int i = 0; i < outputFluidCount; i++)
        {
            outputFluidIngredients.add(FluidIngredient.of(ForgeRegistries.FLUIDS.getValue(buffer.readResourceLocation()), buffer.readVarInt()));
        }

        int outputItemCount = buffer.readVarInt();
        List<Ingredient> outputItemIngredients = new ArrayList<>(outputItemCount);
        for (int i = 0; i < outputItemCount; i++)
        {
            outputItemIngredients.add(Ingredient.fromNetwork(buffer));
        }

        return new MortarRecipe(recipeId, inputFluidIngredients, inputItemIngredients, outputFluidIngredients, outputItemIngredients);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, MortarRecipe recipe)
    {
        buffer.writeVarInt(recipe.getInputFluidIngredients().size());
        for (FluidIngredient fluidIngredient : recipe.getInputFluidIngredients())
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

        buffer.writeVarInt(recipe.getInputItemIngredients().size());
        for (Ingredient ingredient : recipe.getInputItemIngredients())
        {
            ingredient.toNetwork(buffer);
        }

        buffer.writeVarInt(recipe.getOutputFluidIngredients().size());
        for (FluidIngredient fluidIngredient : recipe.getOutputFluidIngredients())
        {
            buffer.writeVarInt(fluidIngredient.getAmount());
            buffer.writeResourceLocation(ForgeRegistries.FLUIDS.getKey(fluidIngredient.getFluid()));
        }

        buffer.writeVarInt(recipe.getOutputItemIngredients().size());
        for (Ingredient ingredient : recipe.getOutputItemIngredients())
        {
            ingredient.toNetwork(buffer);
        }
    }
}
