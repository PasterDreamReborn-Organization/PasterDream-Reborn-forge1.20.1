package com.pasterdream.pasterdreammod.recipe;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.recipe.recipematchandprocess.IProcessingRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

public abstract class GenericPasterDreamRecipe implements IProcessingRecipe,IGenericPasterDreamRecipe, Recipe<Container>
{
    private final ResourceLocation id;
    private final List<FluidIngredient> inputFluidIngredients;
    private final List<ItemIngredient> inputItemIngredients;
    private final List<FluidIngredient> outputFluidIngredients;
    private final List<ItemIngredient> outputItemIngredients;
    private final int processingTime;

    public GenericPasterDreamRecipe(ResourceLocation id, List<FluidIngredient> inputFluidIngredients, List<ItemIngredient> inputItemIngredients, List<FluidIngredient> outputFluidIngredients, List<ItemIngredient> outputItemIngredients, int processingTime)
    {
        this.id = id;
        this.inputFluidIngredients = inputFluidIngredients;
        this.inputItemIngredients = inputItemIngredients;
        this.outputFluidIngredients = outputFluidIngredients;
        this.outputItemIngredients = outputItemIngredients;
        this.processingTime = processingTime;
    }

    @Override
    public abstract RecipeSerializer<?> getSerializer();
    @Override
    public abstract RecipeType<?> getType();

    @Override
    public boolean matches(List<FluidStack> fluidStacks, List<ItemStack> itemStacks)
    {
        return matchesWithSlots(fluidStacks, itemStacks).isPresent();
    }

    @Override
    public Optional<GenericPasterDreamRecipeMatchResult> matchesWithSlots(List<FluidStack> fluidStacks, List<ItemStack> itemStacks)
    {
        int[] fluidSlotMap = new int[0];
        int[] itemSlotMap = new int[0];

        if (!fluidStacks.isEmpty())
        {
            //匹配流体
            int fluidInputCount = inputFluidIngredients.size();
            if (fluidInputCount > fluidStacks.size())
            {
                return Optional.empty();
            }

            fluidSlotMap = new int[fluidInputCount];
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
        }

        if(!itemStacks.isEmpty())
        {
            //匹配物品
            int itemInputCount = inputItemIngredients.size();
            if (itemInputCount > itemStacks.size())
            {
                return Optional.empty();
            }

            itemSlotMap = new int[itemInputCount];
            boolean[] itemUsed = new boolean[itemStacks.size()];

            for (int i = 0; i < itemInputCount; i++)
            {
                ItemIngredient itemIngredient = inputItemIngredients.get(i);
                boolean matched = false;
                for (int j = 0; j < itemStacks.size(); j++)
                {
                    if (!itemUsed[j] && itemIngredient.test(itemStacks.get(j)))
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
        }

        return Optional.of(new GenericPasterDreamRecipeMatchResult(this, fluidSlotMap, itemSlotMap));
    }

    @Override
    public List<ItemIngredient> getInputItems()
    {
        return inputItemIngredients;
    }

    @Override
    public List<FluidIngredient> getInputFluids()
    {
        return inputFluidIngredients;
    }

    @Override
    public List<ItemIngredient> getOutputItems()
    {
        return outputItemIngredients;
    }

    @Override
    public List<FluidIngredient> getOutputFluids()
    {
        return outputFluidIngredients;
    }

    @Override
    public List<FluidIngredient> getInputFluidIngredients()
    {
        return inputFluidIngredients;
    }

    @Override
    public List<ItemIngredient> getInputItemIngredients()
    {
        return inputItemIngredients;
    }

    @Override
    public List<FluidIngredient> getOutputFluidIngredients()
    {
        return outputFluidIngredients;
    }

    @Override
    public List<ItemIngredient> getOutputItemIngredients()
    {
        return outputItemIngredients;
    }

    @Override
    public int getProcessingTime()
    {
        return processingTime;
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
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
