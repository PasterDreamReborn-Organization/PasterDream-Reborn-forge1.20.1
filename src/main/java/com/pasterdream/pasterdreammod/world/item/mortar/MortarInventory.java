package com.pasterdream.pasterdreammod.world.item.mortar;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class MortarInventory implements Container
{
    private final ItemStack container;
    private final ItemStack[] inputItems;
    private final ItemStack[] outputItems;
    private static final int INPUT_SLOTS = 8;
    private static final int OUTPUT_SLOTS = 4;
    private static final int TOTAL_SLOTS = INPUT_SLOTS + OUTPUT_SLOTS;
    private static final String INPUT_ITEMS_KEY = "InputItems";
    private static final String OUTPUT_ITEMS_KEY = "OutputItems";

    public MortarInventory(ItemStack container)
    {
        this.container = container;
        this.inputItems = new ItemStack[INPUT_SLOTS];
        this.outputItems = new ItemStack[OUTPUT_SLOTS];
        Arrays.fill(inputItems, ItemStack.EMPTY);
        Arrays.fill(outputItems, ItemStack.EMPTY);
        loadFromNBT();
    }

    private void loadFromNBT()
    {
        CompoundTag tag = container.getOrCreateTag();

        if (tag.contains(INPUT_ITEMS_KEY, Tag.TAG_LIST))
        {
            ListTag list = tag.getList(INPUT_ITEMS_KEY, Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size() && i < INPUT_SLOTS; i++)
            {
                CompoundTag slotTag = list.getCompound(i);
                if (!slotTag.isEmpty())
                {
                    inputItems[i] = ItemStack.of(slotTag);
                }
            }
        }

        if (tag.contains(OUTPUT_ITEMS_KEY, Tag.TAG_LIST))
        {
            ListTag list = tag.getList(OUTPUT_ITEMS_KEY, Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size() && i < OUTPUT_SLOTS; i++)
            {
                CompoundTag slotTag = list.getCompound(i);
                if (!slotTag.isEmpty())
                {
                    outputItems[i] = ItemStack.of(slotTag);
                }
            }
        }
    }

    public void saveToNBT()
    {
        CompoundTag tag = container.getOrCreateTag();

        ListTag inputList = new ListTag();
        for (int i = 0; i < INPUT_SLOTS; i++)
        {
            CompoundTag slotTag = new CompoundTag();
            if (!inputItems[i].isEmpty())
            {
                inputItems[i].save(slotTag);
            }
            inputList.add(slotTag);
        }
        tag.put(INPUT_ITEMS_KEY, inputList);

        ListTag outputList = new ListTag();
        for (int i = 0; i < OUTPUT_SLOTS; i++)
        {
            CompoundTag slotTag = new CompoundTag();
            if (!outputItems[i].isEmpty())
            {
                outputItems[i].save(slotTag);
            }
            outputList.add(slotTag);
        }
        tag.put(OUTPUT_ITEMS_KEY, outputList);
    }


    @Override
    public int getContainerSize()
    {
        return TOTAL_SLOTS;
    }

    @Override
    public boolean isEmpty()
    {
        for (ItemStack itemStack : inputItems) if (!itemStack.isEmpty()) return false;
        for (ItemStack itemStack : outputItems) if (!itemStack.isEmpty()) return false;
        return true;
    }

    @Override
    public ItemStack getItem(int slot)
    {
        if (slot < INPUT_SLOTS)
        {
            return inputItems[slot];
        }
        return outputItems[slot - INPUT_SLOTS];
    }

    @Override
    public ItemStack removeItem(int slot, int amount)
    {
        ItemStack stack = getItem(slot);
        if (stack.isEmpty())
        {
            return ItemStack.EMPTY;
        }
        if (amount >= stack.getCount())
        {
            setItem(slot, ItemStack.EMPTY);
            return stack;
        }
        return stack.split(amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot)
    {
        ItemStack stack = getItem(slot);
        setItem(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack)
    {
        if (slot < INPUT_SLOTS)
        {
            inputItems[slot] = stack;
        }
            else
            {
                outputItems[slot - INPUT_SLOTS] = stack;
            }
        setChanged();
    }

    @Override
    public void setChanged()
    {
        saveToNBT();
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    @Override
    public void clearContent()
    {
        Arrays.fill(inputItems, ItemStack.EMPTY);
        Arrays.fill(outputItems, ItemStack.EMPTY);
        setChanged();
    }

    public ItemStack[] getInputItems()
    {
        return inputItems;
    }

    public ItemStack[] getOutputItems()
    {
        return outputItems;
    }
}
