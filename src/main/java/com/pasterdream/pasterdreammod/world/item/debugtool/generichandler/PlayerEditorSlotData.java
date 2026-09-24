package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PlayerEditorSlotData
{
    private static final String KEY = "pasterdream_debug_tool_slot";

    public static ItemStack get(Player player, int slotIndex)
    {
        CompoundTag compoundTag = player.getPersistentData();
        if (compoundTag.contains(KEY, Tag.TAG_LIST))
        {
            ListTag listTag = compoundTag.getList(KEY, Tag.TAG_COMPOUND);
            if(slotIndex == 0 || slotIndex == 1)
            {
                return ItemStack.of(listTag.getCompound(slotIndex));
            }
        }
        return ItemStack.EMPTY;
    }

    public static void set(Player player, ItemStack itemStack, int slotIndex)
    {
        CompoundTag compoundTag = player.getPersistentData();
        ListTag listTag;
        if (compoundTag.contains(KEY, Tag.TAG_LIST))
        {
            listTag = compoundTag.getList(KEY, Tag.TAG_COMPOUND);
        }
            else
            {
                listTag = new ListTag();
                listTag.add(new CompoundTag());
                listTag.add(new CompoundTag());
                compoundTag.put(KEY, listTag);
            }

        listTag.set(slotIndex, itemStack.isEmpty() ? new CompoundTag() : itemStack.save(new CompoundTag()));
    }
}
