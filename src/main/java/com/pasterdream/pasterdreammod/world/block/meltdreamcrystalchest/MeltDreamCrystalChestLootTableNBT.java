package com.pasterdream.pasterdreammod.world.block.meltdreamcrystalchest;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class MeltDreamCrystalChestLootTableNBT
{
    public static ItemStack testNBT(ItemLike itemLike)
    {
        ItemStack itemStack = new ItemStack(itemLike);

        CompoundTag compoundTag = new CompoundTag();
        ListTag listTag = new ListTag();

        CompoundTag normalLootTable = new CompoundTag();
        normalLootTable.putString("LootTable", "pasterdream:chests/test_loot_table_0");
        normalLootTable.putInt("weight", 4);
        normalLootTable.putFloat("luck_multiplier_increase", 1F);
        listTag.add(normalLootTable);

        CompoundTag rareLootTable = new CompoundTag();
        rareLootTable.putString("LootTable", "pasterdream:chests/test_loot_table_1");
        rareLootTable.putInt("weight", 2);
        rareLootTable.putFloat("luck_multiplier_increase", 2F);
        listTag.add(rareLootTable);

        CompoundTag legendLootTable = new CompoundTag();
        legendLootTable.putString("LootTable", "pasterdream:chests/test_loot_table_2");
        legendLootTable.putInt("weight", 1);
        legendLootTable.putFloat("luck_multiplier_increase", 4F);
        listTag.add(legendLootTable);

        compoundTag.put("LootTables", listTag);
        itemStack.setTag(compoundTag);

        return itemStack;
    }

    public static ItemStack meltDreamCrystalChestDyedreamWorldNBT(ItemLike itemLike)
    {
        ItemStack itemStack = new ItemStack(itemLike);

        CompoundTag compoundTag = new CompoundTag();
        ListTag listTag = new ListTag();

        CompoundTag normalLootTable = new CompoundTag();
        normalLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_normal");
        normalLootTable.putInt("weight", 11);
        normalLootTable.putFloat("luck_multiplier_increase", 1F);
        listTag.add(normalLootTable);

        CompoundTag rareLootTable = new CompoundTag();
        rareLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_rare");
        rareLootTable.putInt("weight", 7);
        rareLootTable.putFloat("luck_multiplier_increase", 2F);
        listTag.add(rareLootTable);

        CompoundTag legendLootTable = new CompoundTag();
        legendLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_legend");
        legendLootTable.putInt("weight", 2);
        legendLootTable.putFloat("luck_multiplier_increase", 4F);
        listTag.add(legendLootTable);

        compoundTag.put("LootTables", listTag);
        itemStack.setTag(compoundTag);

        return itemStack;
    }

    public static ItemStack meltDreamCrystalChestWorldTreeNBT(ItemLike itemLike)
    {
        ItemStack itemStack = new ItemStack(itemLike);

        CompoundTag compoundTag = new CompoundTag();
        ListTag listTag = new ListTag();

        CompoundTag normalLootTable = new CompoundTag();
        normalLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_normal");
        normalLootTable.putInt("weight", 1);
        normalLootTable.putFloat("luck_multiplier_increase", 1F);
        listTag.add(normalLootTable);

        CompoundTag rareLootTable = new CompoundTag();
        rareLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_rare");
        rareLootTable.putInt("weight", 2);
        rareLootTable.putFloat("luck_multiplier_increase", 2F);
        listTag.add(rareLootTable);

        CompoundTag epicLootTable = new CompoundTag();
        epicLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_legend");
        epicLootTable.putInt("weight", 4);
        epicLootTable.putFloat("luck_multiplier_increase", 4F);
        listTag.add(epicLootTable);

        compoundTag.put("LootTables", listTag);
        itemStack.setTag(compoundTag);

        return itemStack;
    }

    public static ItemStack meltDreamCrystalChestWindJourneyWorldNBT(ItemLike itemLike)
    {
        ItemStack itemStack = new ItemStack(itemLike);

        CompoundTag compoundTag = new CompoundTag();
        ListTag listTag = new ListTag();

        CompoundTag normalLootTable = new CompoundTag();
        normalLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_wind_journey_world_normal");
        normalLootTable.putInt("weight", 8);
        normalLootTable.putFloat("luck_multiplier_increase", 1F);
        listTag.add(normalLootTable);

        CompoundTag rareLootTable = new CompoundTag();
        rareLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_wind_journey_world_rare");
        rareLootTable.putInt("weight", 8);
        rareLootTable.putFloat("luck_multiplier_increase", 2F);
        listTag.add(rareLootTable);

        CompoundTag legendLootTable = new CompoundTag();
        legendLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_wind_journey_world_legend");
        legendLootTable.putInt("weight", 4);
        legendLootTable.putFloat("luck_multiplier_increase", 4F);
        listTag.add(legendLootTable);

        compoundTag.put("LootTables", listTag);
        itemStack.setTag(compoundTag);

        return itemStack;
    }

    public static ItemStack onlyNormal(ItemLike itemLike)
    {
        ItemStack itemStack = new ItemStack(itemLike);

        CompoundTag compoundTag = new CompoundTag();
        ListTag listTag = new ListTag();

        CompoundTag normalLootTable = new CompoundTag();
        normalLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_normal");
        normalLootTable.putInt("weight", 1);
        normalLootTable.putFloat("luck_multiplier_increase", 0F);
        listTag.add(normalLootTable);

        compoundTag.put("LootTables", listTag);
        itemStack.setTag(compoundTag);

        return itemStack;
    }

    public static ItemStack onlyRare(ItemLike itemLike)
    {
        ItemStack itemStack = new ItemStack(itemLike);

        CompoundTag compoundTag = new CompoundTag();
        ListTag listTag = new ListTag();

        CompoundTag normalLootTable = new CompoundTag();
        normalLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_normal");
        normalLootTable.putInt("weight", 0);
        normalLootTable.putFloat("luck_multiplier_increase", 0F);
        listTag.add(normalLootTable);

        CompoundTag rareLootTable = new CompoundTag();
        rareLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_rare");
        rareLootTable.putInt("weight", 1);
        rareLootTable.putFloat("luck_multiplier_increase", 0F);
        listTag.add(rareLootTable);

        compoundTag.put("LootTables", listTag);
        itemStack.setTag(compoundTag);

        return itemStack;
    }

    public static ItemStack onlyLegend(ItemLike itemLike)
    {
        ItemStack itemStack = new ItemStack(itemLike);

        CompoundTag compoundTag = new CompoundTag();
        ListTag listTag = new ListTag();

        CompoundTag normalLootTable = new CompoundTag();
        normalLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_normal");
        normalLootTable.putInt("weight", 0);
        normalLootTable.putFloat("luck_multiplier_increase", 0F);
        listTag.add(normalLootTable);

        CompoundTag rareLootTable = new CompoundTag();
        rareLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_rare");
        rareLootTable.putInt("weight", 0);
        rareLootTable.putFloat("luck_multiplier_increase", 0F);
        listTag.add(rareLootTable);

        CompoundTag legendLootTable = new CompoundTag();
        legendLootTable.putString("LootTable", "pasterdream:chests/melt_dream_crystal_chest_dyedream_world_legend");
        legendLootTable.putInt("weight", 1);
        legendLootTable.putFloat("luck_multiplier_increase", 0F);
        listTag.add(legendLootTable);

        compoundTag.put("LootTables", listTag);
        itemStack.setTag(compoundTag);

        return itemStack;
    }
}
