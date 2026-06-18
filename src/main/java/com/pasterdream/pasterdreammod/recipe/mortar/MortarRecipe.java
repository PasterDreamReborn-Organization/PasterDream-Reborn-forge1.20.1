package com.pasterdream.pasterdreammod.recipe.mortar;

import com.pasterdream.pasterdreammod.helper.fluidingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.recipematchresult.MortarRecipeMatchResult;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

public class MortarRecipe implements IMortarRecipe
{
    private final ResourceLocation id;
    private final List<FluidIngredient> inputFluidIngredients;
    private final List<Ingredient> inputItemIngredients;
    private final List<FluidIngredient> outputFluidIngredients;
    private final List<Ingredient> outputItemIngredients;

    public MortarRecipe(ResourceLocation id,  List<FluidIngredient> inputFluidIngredients, List<Ingredient> inputItemIngredients, List<FluidIngredient> outputFluidIngredients, List<Ingredient> outputItemIngredients)
    {
        this.id = id;
        this.inputFluidIngredients = inputFluidIngredients;
        this.inputItemIngredients = inputItemIngredients;
        this.outputFluidIngredients = outputFluidIngredients;
        this.outputItemIngredients = outputItemIngredients;
    }

    @Override
    public boolean matches(List<FluidStack> fluidStacks, List<ItemStack> itemStacks)
    {
        //匹配流体
        boolean[] fluidUsed = new boolean[fluidStacks.size()];
        for (FluidIngredient fluidIngredient : inputFluidIngredients)
        {
            boolean matched = false;
            for (int i = 0; i < fluidStacks.size(); i++)
            {
                if (!fluidUsed[i] && fluidIngredient.test(fluidStacks.get(i)))
                {
                    fluidUsed[i] = true;
                    matched = true;
                    break;
                }
            }

            if (!matched)
            {
                return false;
            }
        }

        //匹配物品
        boolean[] itemUsed = new boolean[itemStacks.size()];
        for (Ingredient ingredient : inputItemIngredients)
        {
            boolean matched = false;
            for (int i = 0; i < itemStacks.size(); i++)
            {
                if (!itemUsed[i] && ingredient.test(itemStacks.get(i)))
                {
                    itemUsed[i] = true;
                    matched = true;
                    break;
                }
            }
            if (!matched)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public Optional<MortarRecipeMatchResult> matchesWithSlots(List<FluidStack> fluidStacks, List<ItemStack> itemStacks)
    {
        //匹配流体
        int fluidInputCount = inputFluidIngredients.size();
        if (fluidInputCount > fluidStacks.size())
        {
            return Optional.empty();
        }

        int[] fluidSlotMap = new int[fluidInputCount];
        boolean[] fluidUsed = new boolean[fluidStacks.size()];

        for (int i = 0; i < fluidInputCount; i++)
        {
            FluidIngredient fluidIngredient = inputFluidIngredients.get(i);
            boolean matched = false;
            for (int j = 0; j < fluidStacks.size(); j++)
            {
                if (!fluidUsed[j] && fluidIngredient.test(fluidStacks.get(j)))
                {
                    fluidUsed[j] = true;
                    fluidSlotMap[i] = j;
                    matched = true;
                    break;
                }
            }

            if (!matched)
            {
                return Optional.empty();
            }
        }

        //匹配物品
        int itemInputCount = inputItemIngredients.size();
        if (itemInputCount > itemStacks.size())
        {
            return Optional.empty();
        }
        int[] itemSlotMap = new int[itemInputCount];
        boolean[] itemUsed = new boolean[itemStacks.size()];

        for (int i = 0; i < itemInputCount; i++)
        {
            Ingredient ing = inputItemIngredients.get(i);
            boolean matched = false;
            for (int j = 0; j < itemStacks.size(); j++)
            {
                if (!itemUsed[j] && ing.test(itemStacks.get(j)))
                {
                    itemUsed[j] = true;
                    itemSlotMap[i] = j;
                    matched = true;
                    break;
                }
            }

            if (!matched)
            {
                return Optional.empty();
            }
        }

        return Optional.of(new MortarRecipeMatchResult(this, fluidSlotMap, itemSlotMap));
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.MORTAR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.MORTAR.get();
    }


    @Override
    public List<FluidIngredient> getInputFluidIngredients()
    {
        return inputFluidIngredients;
    }

    @Override
    public List<Ingredient> getInputItemIngredients()
    {
        return inputItemIngredients;
    }

    @Override
    public List<FluidIngredient> getOutputFluidIngredients()
    {
        return outputFluidIngredients;
    }

    @Override
    public List<Ingredient> getOutputItemIngredients()
    {
        return outputItemIngredients;
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        return false;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }
}
