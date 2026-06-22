package com.pasterdream.pasterdreammod.recipe.recipematchandprocess;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class RecipeMatcher
{
    private static MachineInventory matchedRecipeInputsAndOutputs = new MachineInventory(new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    @Nullable
    public static <T extends IProcessingRecipe> MatchedRecipeResult<T> match(List<ItemStack> inputItems, List<FluidStack> inputFluids, Collection<T> recipes)
    {
        Set<Item> inputItemTypes = inputItems.stream().filter(itemStack -> !itemStack.isEmpty()).map(ItemStack::getItem).collect(Collectors.toSet());
        Set<Fluid> inputFluidTypes = inputFluids.stream().filter(fluidStack -> !fluidStack.isEmpty()).map(FluidStack::getFluid).collect(Collectors.toSet());

        for (T recipe : recipes)
        {
            if (matchesTypes(recipe, inputItemTypes, inputFluidTypes))
            {
                for(ItemIngredient itemIngredient : recipe.getOutputItems())
                {
                    matchedRecipeInputsAndOutputs.outputItemStacks().add(itemIngredient.getItemStack());
                }

                for(FluidIngredient fluidIngredient : recipe.getOutputFluids())
                {
                    matchedRecipeInputsAndOutputs.outputFluidStacks().add(fluidIngredient.getFluidStack());
                }

                return new MatchedRecipeResult<>(recipe, matchedRecipeInputsAndOutputs);
            }
        }
        return null;
    }

    private static boolean matchesTypes(IProcessingRecipe recipe, Set<Item> inputItemTypes, Set<Fluid> inputFluidTypes)
    {
        matchedRecipeInputsAndOutputs = new MachineInventory(new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        for (ItemIngredient itemIngredient : recipe.getInputItems())
        {
            if(itemIngredient.isTag())
            {   //tag匹配
                List<ItemStack> listItemStackFromTag = itemIngredient.getListItemStackFromTag();
                boolean isMatched = false;
                for(ItemStack itemStack : listItemStackFromTag)
                {
                    if (inputItemTypes.contains(itemStack.getItem()))
                    {   //成功匹配
                        inputItemTypes.remove(itemStack.getItem());
                        isMatched = true;
                        matchedRecipeInputsAndOutputs.inputItemStacks().add(itemStack);
                        break;
                    }
                }

                if(!isMatched)
                {   //匹配失败
                    return false;
                }

            }
                else
                {   //item匹配
                    ItemStack itemStack = itemIngredient.getItemStack();
                    if (inputItemTypes.contains(itemStack.getItem()))
                    {   //成功匹配
                        inputItemTypes.remove(itemStack.getItem());
                        matchedRecipeInputsAndOutputs.inputItemStacks().add(itemStack);
                    }
                        else
                        {   //匹配失败
                            return false;
                        }
                }
        }

        if (!inputItemTypes.isEmpty())
        {   //物品输入槽有配方以外的物品
            return false;
        }

        //流体匹配
        for (FluidIngredient fluidIngredient : recipe.getInputFluids())
        {
            if(fluidIngredient.isTag())
            {   //tag匹配
                List<FluidStack> listFluidStackFromTag = fluidIngredient.getListFluidStackFromTag();
                boolean isMatched = false;
                for(FluidStack fluidStack : listFluidStackFromTag)
                {
                    if (inputFluidTypes.contains(fluidStack.getFluid()))
                    {   //成功匹配
                        inputFluidTypes.remove(fluidStack.getFluid());
                        isMatched = true;
                        matchedRecipeInputsAndOutputs.inputFluidStacks().add(fluidStack);
                        break;
                    }
                }

                if(!isMatched)
                {   //匹配失败
                    return false;
                }
            }
            else
            {   //fluid匹配
                FluidStack fluidStack = fluidIngredient.getFluidStack();
                if (inputFluidTypes.contains(fluidStack.getFluid()))
                {   //成功匹配
                    inputFluidTypes.remove(fluidStack.getFluid());
                    matchedRecipeInputsAndOutputs.inputFluidStacks().add(fluidStack);
                }
                    else
                    {   //匹配失败
                        return false;
                    }
            }
        }

        if (!inputFluidTypes.isEmpty())
        {   //物品输入槽有配方以外的物品
            return false;
        }

        return true;
    }
}
