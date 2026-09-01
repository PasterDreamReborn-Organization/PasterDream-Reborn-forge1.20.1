package com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidStackWithoutAmount;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemStackWithoutCount;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RecipeMatcher
{
    private static MachineInventory matchedRecipeInputsAndOutputs = new MachineInventory(new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    @Nullable
    public static <T extends IProcessingRecipe> MatchedRecipeResult<T> match(List<ItemStack> inputItems, List<FluidStack> inputFluids, Collection<T> recipes)
    {
        boolean isEmpty = true;
        for(ItemStack itemStack : inputItems)
        {
            if(itemStack != ItemStack.EMPTY)
            {
                isEmpty = false;
                break;
            }
        }

        if(isEmpty)
        {
            for(FluidStack fluidStack : inputFluids)
            {
                if(fluidStack != FluidStack.EMPTY)
                {
                    isEmpty = false;
                    break;
                }
            }
        }

        if(isEmpty)
        {
            return null;
        }

        Set<ItemStackWithoutCount> inputItemTypes = new HashSet<>();
        for(ItemStack itemStack : inputItems)
        {
            boolean needSkip = false;

            for(ItemStackWithoutCount itemStackWithoutCount : inputItemTypes)
            {
                if(ItemStackWithoutCount.isSame(itemStackWithoutCount, new ItemStackWithoutCount(itemStack.getItem(), itemStack.getTag())))
                {
                    needSkip = true;
                }
            }

            if(!itemStack.isEmpty() && !needSkip)
            {
                inputItemTypes.add(new ItemStackWithoutCount(itemStack.getItem(), itemStack.getTag()));
            }
        }

        Set<FluidStackWithoutAmount> inputFluidTypes = new HashSet<>();
        for(FluidStack fluidStack : inputFluids)
        {
            boolean needSkip = false;

            for(FluidStackWithoutAmount fluidStackWithoutAmount : inputFluidTypes)
            {
                if(FluidStackWithoutAmount.isSame(fluidStackWithoutAmount, new FluidStackWithoutAmount(fluidStack.getFluid(), fluidStack.getTag())))
                {
                    needSkip = true;
                }
            }

            if(!fluidStack.isEmpty() && !needSkip)
            {
                inputFluidTypes.add(new FluidStackWithoutAmount(fluidStack.getFluid(), fluidStack.getTag()));
            }
        }

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

    private static boolean matchesTypes(IProcessingRecipe recipe, Set<ItemStackWithoutCount> InputItemTypes, Set<FluidStackWithoutAmount> inputFluidTypes)
    {
        Set<ItemStackWithoutCount> copyInputItemTypes = new HashSet<>(InputItemTypes);
        Set<FluidStackWithoutAmount> copyInputFluidTypes = new HashSet<>(inputFluidTypes);

        matchedRecipeInputsAndOutputs = new MachineInventory(new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        for (ItemIngredient itemIngredient : recipe.getInputItems())
        {
            if(itemIngredient.isTag())
            {   //tag匹配
                List<ItemStack> listItemStackFromTag = itemIngredient.getListItemStackFromTag();
                boolean isMatched = false;
                for(ItemStack itemStack : listItemStackFromTag)
                {
                    ItemStackWithoutCount recipeItemType = new ItemStackWithoutCount(itemStack.getItem(), itemStack.getTag());
                    ItemStackWithoutCount needRemovedItem = recipeItemType;

                    for(ItemStackWithoutCount inputItemType : copyInputItemTypes)
                    {
                        if (ItemStackWithoutCount.isSame(recipeItemType, inputItemType))
                        {
                            needRemovedItem = inputItemType;
                            isMatched = true;
                            matchedRecipeInputsAndOutputs.inputItemStacks().add(itemStack);
                            break;
                        }
                    }

                    if(isMatched)
                    {
                        copyInputItemTypes.remove(needRemovedItem);
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
                boolean isMatched = false;
                ItemStackWithoutCount recipeItemType = new ItemStackWithoutCount(itemStack.getItem(), itemStack.getTag());
                ItemStackWithoutCount needRemovedItem = recipeItemType;

                for(ItemStackWithoutCount inputItemType : copyInputItemTypes)
                {
                    if (ItemStackWithoutCount.isSame(recipeItemType, inputItemType))
                    {
                        needRemovedItem = inputItemType;
                        isMatched = true;
                        matchedRecipeInputsAndOutputs.inputItemStacks().add(itemStack);
                        break;
                    }
                }

                if(!isMatched)
                {   //匹配失败

                    return false;
                }
                else
                {
                    copyInputItemTypes.remove(needRemovedItem);
                }
            }
        }

        if (!copyInputItemTypes.isEmpty())
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
                    FluidStackWithoutAmount recipeFluidType = new FluidStackWithoutAmount(fluidStack.getFluid(), fluidStack.getTag());
                    FluidStackWithoutAmount needRemovedFluid = recipeFluidType;

                    for(FluidStackWithoutAmount inputFluidType : copyInputFluidTypes)
                    {
                        if (FluidStackWithoutAmount.isSame(recipeFluidType, inputFluidType))
                        {
                            needRemovedFluid = inputFluidType;
                            isMatched = true;
                            matchedRecipeInputsAndOutputs.inputFluidStacks().add(fluidStack);
                            break;
                        }
                    }

                    if(isMatched)
                    {
                        copyInputFluidTypes.remove(needRemovedFluid);
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
                boolean isMatched = false;
                FluidStackWithoutAmount recipeFluidType = new FluidStackWithoutAmount(fluidStack.getFluid(), fluidStack.getTag());
                FluidStackWithoutAmount needRemovedFluid = recipeFluidType;

                for(FluidStackWithoutAmount inputFluidType : copyInputFluidTypes)
                {
                    if (FluidStackWithoutAmount.isSame(recipeFluidType, inputFluidType))
                    {
                        needRemovedFluid = inputFluidType;
                        isMatched = true;
                        matchedRecipeInputsAndOutputs.inputFluidStacks().add(fluidStack);
                        break;
                    }
                }

                if(!isMatched)
                {   //匹配失败
                    return false;
                }
                else
                {
                    copyInputFluidTypes.remove(needRemovedFluid);
                }
            }
        }

        if (!copyInputFluidTypes.isEmpty())
        {   //流体输入槽有配方以外的流体
            return false;
        }

        return true;
    }
}
