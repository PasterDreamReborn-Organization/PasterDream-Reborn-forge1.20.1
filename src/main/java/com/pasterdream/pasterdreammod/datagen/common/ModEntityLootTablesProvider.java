package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.functions.ApplyEntityLootingFunction;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModEntityLootTablesProvider implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "entities/pink_chicken"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.CHICKEN)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(EmptyLootItem.emptyItem().setWeight(2))
                                .add(LootItem.lootTableItem(Items.FEATHER)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                                .add(LootItem.lootTableItem(Items.FEATHER)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "entities/pink_slime"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.PINK_SLIMEBALL.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                                .add(EmptyLootItem.emptyItem().setWeight(2)))
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "entities/golden_fox"),
                LootTable.lootTable()
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "entities/shadow_golem"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_STONE_TILES.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 3)))
                                        .apply(new ApplyEntityLootingFunction.Builder(Enchantments.MOB_LOOTING, 1))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_LIGHT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                        .apply(new ApplyEntityLootingFunction.Builder(Enchantments.MOB_LOOTING, 1))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.RUST_BLACK_METAL_GRAIN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 7)))
                                        .apply(new ApplyEntityLootingFunction.Builder(Enchantments.MOB_LOOTING, 1))))
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "entities/terrorbeak"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.NIGHTMARE_FUEL.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                        .apply(new ApplyEntityLootingFunction.Builder(Enchantments.MOB_LOOTING, 1))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.SHADOW.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                        .apply(new ApplyEntityLootingFunction.Builder(Enchantments.MOB_LOOTING, 1))))
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "entities/crazy_terrorbeak"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.NIGHTMARE_FUEL.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))
                                        .apply(new ApplyEntityLootingFunction.Builder(Enchantments.MOB_LOOTING, 1))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModBlocks.THICK_SHADOW.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                        .apply(new ApplyEntityLootingFunction.Builder(Enchantments.MOB_LOOTING, 1))))
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "entities/weakeness_terrorbeak"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.NIGHTMARE_FUEL.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
                                        .apply(new ApplyEntityLootingFunction.Builder(Enchantments.MOB_LOOTING, 1))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModBlocks.THICK_SHADOW.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                        .apply(new ApplyEntityLootingFunction.Builder(Enchantments.MOB_LOOTING, 1))))
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "entities/shadow_hand"),
                LootTable.lootTable()
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "entities/shadow_ghost"),
                ghostLoot()
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "entities/shadow_squeal_ghost"),
                ghostLoot()
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "entities/wailing_shadow_ghost"),
                ghostLoot()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.NIGHTMARE_FUEL.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                        .apply(new ApplyEntityLootingFunction.Builder(Enchantments.MOB_LOOTING, 1))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.SHADOW.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                        .apply(new ApplyEntityLootingFunction.Builder(Enchantments.MOB_LOOTING, 1))))
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "entities/friendly_shadow_ghost"),
                ghostLoot()
        );
    }

    private LootTable.Builder ghostLoot() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.SOUL_DUST.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                .apply(new ApplyEntityLootingFunction.Builder(Enchantments.MOB_LOOTING, 1))));
    }
}
