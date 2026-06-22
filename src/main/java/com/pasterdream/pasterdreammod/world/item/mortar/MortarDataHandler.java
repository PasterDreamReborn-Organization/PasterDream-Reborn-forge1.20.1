package com.pasterdream.pasterdreammod.world.item.mortar;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MortarDataHandler
{
    private final ItemStack thisMortarItem;
    private static final int INPUT_ITEM_SLOTS = 8;
    private static final int OUTPUT_ITEM_SLOTS = 4;
    private static final int INPUT_FLUID_SLOTS = 4;
    private static final int OUTPUT_FLUID_SLOTS = 2;
    private final FluidStack[] inputFluids;
    private final FluidStack[] outputFluids;
    private final ItemStack[] inputItems;
    private final ItemStack[] outputItems;

    public MortarDataHandler(ItemStack thisMortarItem)
    {
        this.thisMortarItem = thisMortarItem;
        this.inputFluids = new FluidStack[]{FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY, FluidStack.EMPTY};
        this.outputFluids = new FluidStack[]{FluidStack.EMPTY, FluidStack.EMPTY};
        this.inputItems = new ItemStack[]{ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
        this.outputItems = new ItemStack[]{ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
        ensureNBTStructure();
        loadFromNBT();
    }

    //确认NBT结构
    private void ensureNBTStructure()
    {
        CompoundTag tag = thisMortarItem.getOrCreateTag();

        if (!tag.contains("InputItems"))
        {
            ListTag list = new ListTag();
            for (int i = 0; i < INPUT_ITEM_SLOTS; i++)
            {
                list.add(new CompoundTag());
            }
            tag.put("InputItems", list);
        }

        if (!tag.contains("OutputItems"))
        {
            ListTag list = new ListTag();
            for (int i = 0; i < OUTPUT_ITEM_SLOTS; i++)
            {
                list.add(new CompoundTag());
            }
            tag.put("OutputItems", list);
        }

        if (!tag.contains("InputFluids"))
        {
            ListTag list = new ListTag();
            for (int i = 0; i < INPUT_FLUID_SLOTS; i++)
            {
                list.add(new CompoundTag());
            }
            tag.put("InputFluids", list);
        }

        if (!tag.contains("OutputFluids"))
        {
            ListTag list = new ListTag();
            for (int i = 0; i < OUTPUT_FLUID_SLOTS; i++)
            {
                list.add(new CompoundTag());
            }
            tag.put("OutputFluids", list);
        }
    }

    //获取物品输入槽位
    public ItemStack getInputItem(int slotIndex)
    {
        CompoundTag tag = thisMortarItem.getOrCreateTag();
        ListTag list = tag.getList("InputItems", Tag.TAG_COMPOUND);
        if (slotIndex < 0 || slotIndex >= list.size())
        {
            return ItemStack.EMPTY;
        }
        return ItemStack.of(list.getCompound(slotIndex));
    }

    //设置物品输入槽位
    public void setInputItem(int slotIndex, ItemStack stack)
    {
        CompoundTag tag = thisMortarItem.getOrCreateTag();
        ListTag list = tag.getList("InputItems", Tag.TAG_COMPOUND);
        if (slotIndex < 0 || slotIndex >= list.size())
        {
            return;
        }
        CompoundTag slotTag = list.getCompound(slotIndex);
        if (stack.isEmpty())
        {
            list.set(slotIndex, new CompoundTag());
        }
            else
            {
                stack.save(slotTag);
            }
        tag.put("InputItems", list);
    }

    //获取物品输出槽位
    public ItemStack getOutputItem(int slotIndex)
    {
        CompoundTag tag = thisMortarItem.getOrCreateTag();
        ListTag list = tag.getList("OutputItems", Tag.TAG_COMPOUND);
        if (slotIndex < 0 || slotIndex >= list.size())
        {
            return ItemStack.EMPTY;
        }
        return ItemStack.of(list.getCompound(slotIndex));
    }

    //设置物品输出槽位
    public void setOutputItem(int slotIndex, ItemStack stack)
    {
        CompoundTag tag = thisMortarItem.getOrCreateTag();
        ListTag list = tag.getList("OutputItems", Tag.TAG_COMPOUND);
        if (slotIndex < 0 || slotIndex >= list.size())
        {
            return;
        }
        CompoundTag slotTag = list.getCompound(slotIndex);
        if (stack.isEmpty())
        {
            list.set(slotIndex, new CompoundTag());
        }
            else
            {
                stack.save(slotTag);
            }
        tag.put("OutputItems", list);
    }

    //获取流体输入槽位
    public FluidStack getInputFluid(int slotIndex)
    {
        CompoundTag tag = thisMortarItem.getOrCreateTag();
        ListTag list = tag.getList("InputFluids", Tag.TAG_COMPOUND);
        if (slotIndex < 0 || slotIndex >= list.size())
        {
            return FluidStack.EMPTY;
        }
        return FluidStack.loadFluidStackFromNBT(list.getCompound(slotIndex));
    }

    //设置流体输入槽位
    public void setInputFluid(int slotIndex, FluidStack stack)
    {
        CompoundTag tag = thisMortarItem.getOrCreateTag();
        ListTag list = tag.getList("InputFluids", Tag.TAG_COMPOUND);
        if (slotIndex < 0 || slotIndex >= list.size())
        {
            return;
        }
        CompoundTag fluidTag = list.getCompound(slotIndex);
        if (stack.isEmpty())
        {
            list.set(slotIndex, new CompoundTag());
        }
            else
            {
                stack.writeToNBT(fluidTag);
            }
        tag.put("InputFluids", list);
    }

    //获取流体输出槽位
    public FluidStack getOutputFluid(int slotIndex)
    {
        CompoundTag tag = thisMortarItem.getOrCreateTag();
        ListTag list = tag.getList("OutputFluids", Tag.TAG_COMPOUND);
        if (slotIndex < 0 || slotIndex >= list.size())
        {
            return FluidStack.EMPTY;
        }
        return FluidStack.loadFluidStackFromNBT(list.getCompound(slotIndex));
    }

    //设置流体输出槽位
    public void setOutputFluid(int slotIndex, FluidStack stack)
    {
        CompoundTag tag = thisMortarItem.getOrCreateTag();
        ListTag list = tag.getList("OutputFluids", Tag.TAG_COMPOUND);
        if (slotIndex < 0 || slotIndex >= list.size())
        {
            return;
        }

        CompoundTag fluidTag = list.getCompound(slotIndex);
        if (stack.isEmpty())
        {
            list.set(slotIndex, new CompoundTag());
        }
            else
            {
                stack.writeToNBT(fluidTag);
            }
        tag.put("OutputFluids", list);
    }

    //获取容器
    public ItemStack getThisMortarItem()
    {
        return thisMortarItem;
    }

    //获取所有输入物品
    public List<ItemStack> getAllInputItems()
    {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < INPUT_ITEM_SLOTS; i++)
        {
            items.add(getInputItem(i));
        }
        return items;
    }

    //获取所有输出物品
    public List<ItemStack> getAllOutputItems()
    {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < OUTPUT_ITEM_SLOTS; i++)
        {
            items.add(getOutputItem(i));
        }
        return items;
    }

    //获取所有输入流体
    public List<FluidStack> getAllInputFluids()
    {
        List<FluidStack> fluids = new ArrayList<>();
        for (int i = 0; i < INPUT_FLUID_SLOTS; i++)
        {
            fluids.add(getInputFluid(i));
        }
        return fluids;
    }

    //获取所有输出流体
    public List<FluidStack> getAllOutputFluids()
    {
        List<FluidStack> fluids = new ArrayList<>();
        for (int i = 0; i < OUTPUT_FLUID_SLOTS; i++)
        {
            fluids.add(getOutputFluid(i));
        }
        return fluids;
    }

    //从NBT读取
    private void loadFromNBT()
    {
        CompoundTag tag = thisMortarItem.getOrCreateTag();
        loadFluidList(tag, "InputFluids", inputFluids);
        loadItemList(tag, "InputItems", inputItems);
        loadFluidList(tag, "OutputFluids", outputFluids);
        loadItemList(tag, "OutputItems", outputItems);
    }

    private void loadFluidList(CompoundTag tag, String key, FluidStack[] target)
    {
        if (tag.contains(key, Tag.TAG_LIST))
        {
            ListTag list = tag.getList(key, Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size() && i < target.length; i++)
            {
                target[i] = FluidStack.loadFluidStackFromNBT(list.getCompound(i));
            }
        }
    }

    private void loadItemList(CompoundTag tag, String key, ItemStack[] target)
    {
        if (tag.contains(key, Tag.TAG_LIST))
        {
            ListTag list = tag.getList(key, Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size() && i < target.length; i++)
            {
                target[i] = ItemStack.of(list.getCompound(i));
            }
        }
    }

    //保存至NBT
    public void saveToNBT()
    {
        CompoundTag tag = thisMortarItem.getOrCreateTag();
        tag.put("InputFluids", saveFluidList(inputFluids));
        tag.put("InputItems", saveItemList(inputItems));
        tag.put("OutputFluids", saveFluidList(outputFluids));
        tag.put("OutputItems", saveItemList(outputItems));
    }

    private ListTag saveFluidList(FluidStack[] fluids)
    {
        ListTag list = new ListTag();
        for (FluidStack fluid : fluids)
        {
            CompoundTag slotTag = new CompoundTag();
            if (!fluid.isEmpty())
            {
                fluid.writeToNBT(slotTag);
            }
            list.add(slotTag);
        }
        return list;
    }

    private ListTag saveItemList(ItemStack[] stacks)
    {
        ListTag list = new ListTag();
        for (ItemStack stack : stacks)
        {
            CompoundTag slotTag = new CompoundTag();
            if (!stack.isEmpty())
            {
                stack.save(slotTag);
            }
            list.add(slotTag);
        }
        return list;
    }
}
