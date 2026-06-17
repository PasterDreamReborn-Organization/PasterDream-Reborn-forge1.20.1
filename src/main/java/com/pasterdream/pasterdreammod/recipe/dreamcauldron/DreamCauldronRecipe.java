package com.pasterdream.pasterdreammod.recipe.dreamcauldron;

import com.pasterdream.pasterdreammod.helper.fluidingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.recipematchresult.RecipeMatchResult;
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

public class DreamCauldronRecipe implements IDreamCauldronRecipe
{
    private final ResourceLocation id;
    private final List<FluidIngredient> fluidIngredients;
    private final List<Ingredient> itemIngredients;
    private final ItemStack output;

    public DreamCauldronRecipe(ResourceLocation id,  List<FluidIngredient> fluidIngredients, List<Ingredient> itemIngredients, ItemStack output)
    {
        this.id = id;
        this.fluidIngredients = fluidIngredients;
        this.itemIngredients = itemIngredients;
        this.output = output;
    }

    @Override
    public boolean matches(List<FluidStack> fluidStacks, List<ItemStack> itemStacks)
    {
        //匹配流体
        boolean[] fluidUsed = new boolean[fluidStacks.size()];
        for (FluidIngredient fIng : fluidIngredients)
        {
            boolean matched = false;
            for (int i = 0; i < fluidStacks.size(); i++)
            {
                if (!fluidUsed[i] && fIng.test(fluidStacks.get(i)))
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
        for (Ingredient ing : itemIngredients)
        {
            boolean matched = false;
            for (int i = 0; i < itemStacks.size(); i++)
            {
                if (!itemUsed[i] && ing.test(itemStacks.get(i)))
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
    public Optional<RecipeMatchResult> matchesWithSlots(List<FluidStack> fluidStacks, List<ItemStack> itemStacks)
    {
        //匹配流体
        int fluidInputCount = fluidIngredients.size();
        if (fluidInputCount > fluidStacks.size())
        {
            return Optional.empty();
        }

        int[] fluidSlotMap = new int[fluidInputCount];
        boolean[] fluidUsed = new boolean[fluidStacks.size()];

        for (int i = 0; i < fluidInputCount; i++)
        {
            FluidIngredient fIng = fluidIngredients.get(i);
            boolean matched = false;
            for (int j = 0; j < fluidStacks.size(); j++)
            {
                if (!fluidUsed[j] && fIng.test(fluidStacks.get(j)))
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
        int itemInputCount = itemIngredients.size();
        if (itemInputCount > itemStacks.size())
        {
            return Optional.empty();
        }
        int[] itemSlotMap = new int[itemInputCount];
        boolean[] itemUsed = new boolean[itemStacks.size()];

        for (int i = 0; i < itemInputCount; i++)
        {
            Ingredient ing = itemIngredients.get(i);
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

        return Optional.of(new RecipeMatchResult(this, fluidSlotMap, itemSlotMap));
    }

    @Override
    public List<FluidIngredient> getFluidIngredients()
    {
        return fluidIngredients;
    }

    @Override
    public List<Ingredient> getItemIngredients()
    {
        return itemIngredients;
    }

    @Override
    public ItemStack getResultItem()
    {
        return output.copy();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess)
    {
        return output.copy();
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.DREAM_CAULDRON_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.DREAM_CAULDRON.get();
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess)
    {
        return output.copy();
    }

    @Override
    public boolean matches(Container container, Level level)
    {
        return false;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }
}
