package com.pasterdream.pasterdreammod.world.item.blueprints;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class BluePrintNBTSerializer
{
    public static List<List<List<ItemStack>>> serialize(CompoundTag bluePrintNBT)
    {
        if(bluePrintNBT == null)
        {
            System.err.println("bluePrintNBT == null");
            return null;
        }

        ListTag size = bluePrintNBT.getList("size", ListTag.TAG_INT);
        int sizeX = size.getInt(0);
        int sizeY = size.getInt(1);
        int sizeZ = size.getInt(2);

        List<List<List<ItemStack>>> ListListListItemStack = new ArrayList<>();
        for (int y = 0; y < sizeY; y++)
        {
            List<List<ItemStack>> ListListItemStack = new ArrayList<>(sizeX);
            for (int x = 0; x < sizeX; x++)
            {
                List<ItemStack> ListItemStack = new ArrayList<>(sizeZ);
                for (int z = 0; z < sizeZ; z++)
                {
                    ListItemStack.add(ItemStack.EMPTY);
                }
                ListListItemStack.add(ListItemStack);
            }
            ListListListItemStack.add(ListListItemStack);
        }

        ListTag palette = bluePrintNBT.getList("palette", ListTag.TAG_COMPOUND);
        List<ItemStack> paletteItemStack = new ArrayList<>();
        for(int i = 0; i < palette.size(); i++)
        {
            String name = palette.getCompound(i).getString("Name");
            Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(name));
            ItemStack itemStack;

            if(item != null)
            {
                itemStack = new ItemStack(item);
            }
                else
                {
                    itemStack = new ItemStack(Items.BARRIER);
                    itemStack.setHoverName(Component.literal(name));
                }

            paletteItemStack.add(itemStack);
        }

        ListTag blocks = bluePrintNBT.getList("blocks", ListTag.TAG_COMPOUND);
        for(int i = 0; i < blocks.size(); i++)
        {
            CompoundTag compoundTag = blocks.getCompound(i);
            ListTag pos = compoundTag.getList("pos", ListTag.TAG_INT);
            ListListListItemStack.get(pos.getInt(1)).get(pos.getInt(0)).set(pos.getInt(2), paletteItemStack.get(compoundTag.getInt("state")));
        }

        return ListListListItemStack;
    }
}
