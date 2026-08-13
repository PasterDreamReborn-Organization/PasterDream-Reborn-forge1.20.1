package com.pasterdream.pasterdreammod.world.item.blueprints;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class BluePrintWithNBTToCreativeModeTab
{
    public static ItemStack buildNBT(String content)
    {
        ItemStack itemStack = new ItemStack(ModItems.BLUE_PRINT.get());
        CompoundTag nbt = new CompoundTag();
        nbt.putString("content", content);
        itemStack.setTag(nbt);
        return itemStack;
    }
}
