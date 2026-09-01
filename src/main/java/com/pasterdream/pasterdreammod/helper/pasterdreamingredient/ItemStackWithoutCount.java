package com.pasterdream.pasterdreammod.helper.pasterdreamingredient;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class ItemStackWithoutCount
{
    @Nullable
    private final Item item;
    @Nullable
    private final CompoundTag nbt;

    public ItemStackWithoutCount(@Nullable Item item, @Nullable CompoundTag nbt)
    {
        this.item = item;
        this.nbt = nbt;
    }

    public Item getItem()
    {
        return item;
    }

    public CompoundTag getNbt()
    {
        return nbt;
    }

    public boolean hasNbt()
    {
        return nbt != null;
    }

    public static boolean isSame(ItemStackWithoutCount itemStackWithoutCount0, ItemStackWithoutCount itemStackWithoutCount1)
    {
        if(itemStackWithoutCount0.hasNbt() && itemStackWithoutCount1.hasNbt())
        {
            return itemStackWithoutCount0.getItem() == itemStackWithoutCount1.getItem() && itemStackWithoutCount0.getNbt().equals(itemStackWithoutCount1.getNbt());
        }
            else
            {
                return itemStackWithoutCount0.getItem() == itemStackWithoutCount1.getItem();
            }
    }
}
