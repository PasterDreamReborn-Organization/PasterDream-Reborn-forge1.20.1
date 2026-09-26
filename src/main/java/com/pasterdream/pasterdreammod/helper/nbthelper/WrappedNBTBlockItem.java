package com.pasterdream.pasterdreammod.helper.nbthelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

public class WrappedNBTBlockItem
{
    public static CompoundTag wrapper(CompoundTag originalNBT)
    {
        CompoundTag wrapper = new CompoundTag();
        wrapper.put("BlockEntityTag", originalNBT);

        CompoundTag display = new CompoundTag();
        ListTag lore = new ListTag();
        lore.add(StringTag.valueOf("\"(+NBT)\""));
        display.put("Lore", lore);
        wrapper.put("display", display);
        return wrapper;
    }
}
