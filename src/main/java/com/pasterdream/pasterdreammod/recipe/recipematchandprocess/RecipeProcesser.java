package com.pasterdream.pasterdreammod.recipe.recipematchandprocess;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeProcesser
{
    public static MachineInventory recipeProcessor(MachineInventory matchedRecipeInputsAndOutputs, MachineInventoryWithFluidSlotMaxStackSize machineInventory)
    {
        MachineInventory backUp = new MachineInventory(machineInventory.inputItemStacks().stream().map(ItemStack::copy).collect(Collectors.toList()), machineInventory.inputFluidStacks().stream().map(FluidStack::copy).collect(Collectors.toList()), machineInventory.outputItemStacks().stream().map(ItemStack::copy).collect(Collectors.toList()), machineInventory.outputFluidStacks().stream().map(FluidStack::copy).collect(Collectors.toList()));
        //匹配输入物品
        List<ItemStack> matchedInputItemStacks = matchedRecipeInputsAndOutputs.inputItemStacks();
        List<ItemStack> machineInputItemStacks = machineInventory.inputItemStacks();

        int matchedInputItemSize = matchedInputItemStacks.size();
        int machineInputItemSize = machineInputItemStacks.size();

        for(int i = 0; i < matchedInputItemSize; i++)
        {
            ItemStack matchedInputItemStack = matchedInputItemStacks.get(i);
            int matchedItemCount = matchedInputItemStack.getCount();
            for(int j = 0; j < machineInputItemSize; j++)
            {
                ItemStack machineInputItemStack = machineInputItemStacks.get(j);
                if (ItemStack.isSameItem(matchedInputItemStack, machineInputItemStack))
                {
                    int machineItemCount = machineInputItemStack.getCount();
                    if(machineItemCount >= matchedItemCount)
                    {
                        machineInputItemStack.shrink(matchedItemCount);
                        machineInputItemStacks.set(j, machineInputItemStack);
                        matchedItemCount = 0;
                        matchedInputItemStacks.set(i, ItemStack.EMPTY);
                        break;
                    }
                        else
                        {
                            machineInputItemStacks.set(j, ItemStack.EMPTY);
                            matchedItemCount -= machineItemCount;
                            matchedInputItemStack.setCount(matchedItemCount);
                            matchedInputItemStacks.set(i, matchedInputItemStack);
                        }
                }
            }

            if(matchedItemCount > 0)
            {   //物品原材料不足
                return backUp;
            }
        }

        //匹配输入流体
        List<FluidStack> matchedInputFluidStacks = matchedRecipeInputsAndOutputs.inputFluidStacks();
        List<FluidStack> machineInputFluidStacks = machineInventory.inputFluidStacks();

        int matchedInputFluidSize = matchedInputFluidStacks.size();
        int machineInputFluidSize = machineInputFluidStacks.size();

        for(int i = 0; i < matchedInputFluidSize; i++)
        {
            FluidStack matchedInputFluidStack = matchedInputFluidStacks.get(i);
            int matchedFluidAmount = matchedInputFluidStack.getAmount();
            for(int j = 0; j < machineInputFluidSize; j++)
            {
                FluidStack machineInputFluidStack = machineInputFluidStacks.get(j);
                if (matchedInputFluidStack.getFluid().equals(machineInputFluidStack.getFluid()))
                {
                    int machineFluidAmount = machineInputFluidStack.getAmount();
                    if(machineFluidAmount >= matchedFluidAmount)
                    {
                        machineInputFluidStack.shrink(matchedFluidAmount);
                        machineInputFluidStacks.set(j, machineInputFluidStack);
                        matchedFluidAmount = 0;
                        matchedInputFluidStacks.set(i, FluidStack.EMPTY);
                        break;
                    }
                    else
                    {
                        machineInputFluidStacks.set(j, FluidStack.EMPTY);
                        matchedFluidAmount -= machineFluidAmount;
                        matchedInputFluidStack.setAmount(matchedFluidAmount);
                        matchedInputFluidStacks.set(i, matchedInputFluidStack);
                    }
                }

            }

            if(matchedFluidAmount > 0)
            {   //流体原材料不足
                return backUp;
            }
        }

        //判断物品输出
        List<ItemStack> matchedOutputItemStacks = matchedRecipeInputsAndOutputs.outputItemStacks();
        List<ItemStack> machineOutputItemStacks = machineInventory.outputItemStacks();

        int matchedOutputItemSize = matchedOutputItemStacks.size();
        int machineOutputItemSize = machineOutputItemStacks.size();

        for(int i = 0; i < matchedOutputItemSize; i++)
        {
            ItemStack matchedOutputItemStack = matchedOutputItemStacks.get(i);
            int matchedOutputItemCount = matchedOutputItemStack.getCount();
            for(int j = 0; j < machineOutputItemSize; j++)
            {
                ItemStack machineOutputItemStack = machineOutputItemStacks.get(j);
                int machineOutputItemCount = machineOutputItemStack.getCount();
                int machineOutputItemMaxStackSize = machineOutputItemStack.getMaxStackSize();

                if(ItemStack.isSameItemSameTags(machineOutputItemStack, matchedOutputItemStack))
                {
                    if(machineOutputItemCount + matchedOutputItemCount <= machineOutputItemMaxStackSize)
                    {
                        machineOutputItemStack.setCount(machineOutputItemCount + matchedOutputItemCount);
                        machineOutputItemStacks.set(j, machineOutputItemStack);
                        matchedOutputItemStack = ItemStack.EMPTY;
                        matchedOutputItemStacks.set(i, ItemStack.EMPTY);
                        break;
                    }
                        else
                        {
                            machineOutputItemStack.setCount(machineOutputItemMaxStackSize);
                            machineOutputItemStacks.set(j, machineOutputItemStack);
                            matchedOutputItemCount -= (machineOutputItemMaxStackSize - machineOutputItemCount);
                            matchedOutputItemStack.shrink(machineOutputItemMaxStackSize - machineOutputItemCount);
                            matchedOutputItemStacks.set(i, matchedOutputItemStack);
                        }
                }
                else
                    if(machineOutputItemStack.isEmpty())
                    {
                        machineOutputItemStacks.set(j, matchedOutputItemStack.copy());
                        matchedOutputItemStack = ItemStack.EMPTY;
                        matchedOutputItemStacks.set(i, ItemStack.EMPTY);
                        break;
                    }
            }

            if (!matchedOutputItemStack.isEmpty())
            {   //物品输出空间不足
                return backUp;
            }
        }

        //判断流体输出
        List<FluidStack> matchedOutputFluidStacks = matchedRecipeInputsAndOutputs.outputFluidStacks();
        List<FluidStack> machineOutputFluidStacks = machineInventory.outputFluidStacks();

        int matchedOutputFluidSize = matchedOutputFluidStacks.size();
        int machineOutputFluidSize = machineOutputFluidStacks.size();

        for(int i = 0; i < matchedOutputFluidSize; i++)
        {
            FluidStack matchedOutputFluidStack = matchedOutputFluidStacks.get(i);
            int matchedOutputFluidAmount = matchedOutputFluidStack.getAmount();
            for(int j = 0; j < machineOutputFluidSize; j++)
            {
                FluidStack machineOutputFluidStack = machineOutputFluidStacks.get(j);
                int machineOutputFluidAmount = machineOutputFluidStack.getAmount();
                int machineOutputFluidMaxStackSize = machineInventory.FluidSlotMaxStackSize();

                if(machineOutputFluidStack.getFluid() == matchedOutputFluidStack.getFluid() && FluidStack.areFluidStackTagsEqual(machineOutputFluidStack, matchedOutputFluidStack))
                {
                    if(machineOutputFluidAmount + matchedOutputFluidAmount <= machineOutputFluidMaxStackSize)
                    {
                        machineOutputFluidStack.setAmount(machineOutputFluidAmount + matchedOutputFluidAmount);
                        machineOutputFluidStacks.set(j, machineOutputFluidStack);
                        matchedOutputFluidStack = FluidStack.EMPTY;
                        matchedOutputFluidStacks.set(i, FluidStack.EMPTY);
                        break;
                    }
                        else
                        {
                            machineOutputFluidStack.setAmount(machineOutputFluidMaxStackSize);
                            machineOutputFluidStacks.set(j, machineOutputFluidStack);
                            matchedOutputFluidAmount -= (machineOutputFluidMaxStackSize - machineOutputFluidAmount);
                            matchedOutputFluidStack.shrink(machineOutputFluidMaxStackSize - machineOutputFluidAmount);
                            matchedOutputFluidStacks.set(i, matchedOutputFluidStack);
                        }
                }
                else
                    if(machineOutputFluidStack.isEmpty())
                    {
                        machineOutputFluidStacks.set(j, matchedOutputFluidStack.copy());
                        matchedOutputFluidStack = FluidStack.EMPTY;
                        matchedOutputFluidStacks.set(i, FluidStack.EMPTY);
                        break;
                    }
            }

            if (!matchedOutputFluidStack.isEmpty())
            {   //流体输出空间不足
                return backUp;
            }
        }

        return new MachineInventory(machineInputItemStacks, machineInputFluidStacks, machineOutputItemStacks, machineOutputFluidStacks);
    }
}
