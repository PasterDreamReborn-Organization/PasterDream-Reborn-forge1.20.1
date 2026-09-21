package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PlayerEditorSlotData
{
    private static final String KEY = "pasterdream_debug_tool_slot";

    public static ItemStack get(Player player)
    {
        CompoundTag compoundTag = player.getPersistentData();
        return compoundTag.contains(KEY) ? ItemStack.of(compoundTag.getCompound(KEY)) : ItemStack.EMPTY;
    }

    public static void set(Player player, ItemStack itemStack)
    {
        CompoundTag compoundTag = player.getPersistentData();
        if (itemStack.isEmpty())
        {
            compoundTag.remove(KEY);
        }
            else
            {
                compoundTag.put(KEY, itemStack.save(new CompoundTag()));
            }
    }

    public static void clear(Player player)
    {
        player.getPersistentData().remove(KEY);
    }
}
