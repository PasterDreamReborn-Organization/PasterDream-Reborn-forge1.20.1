package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModLootTables;
import com.pasterdream.pasterdreammod.world.functions.SpawnEntityFunction;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

// 园艺钳破坏方块走另一个战利品表
public class ModPliersCuttingLootTablesProvider implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        // 白桦树叶
        consumer.accept(
                ModLootTables.pliersCuttingLoot(Blocks.BIRCH_LEAVES),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.BIRCH_SAPLING)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(6))
                        )
        );
        // 橡树树叶，园艺钳特殊效果：大幅增加苹果掉率
        consumer.accept(
                ModLootTables.pliersCuttingLoot(Blocks.OAK_LEAVES),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.OAK_SAPLING)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(6))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.APPLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F,3.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(9))
                        )
        );
        // 针叶树树叶
        consumer.accept(
                ModLootTables.pliersCuttingLoot(Blocks.SPRUCE_LEAVES),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.SPRUCE_SAPLING)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(6))
                        )
        );
        // 樱花树树叶
        consumer.accept(
                ModLootTables.pliersCuttingLoot(Blocks.CHERRY_LEAVES),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.CHERRY_SAPLING)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(6))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.PINK_PETALS)
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F,4.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(5))
                        )
        );
        // 金合欢树树叶
        consumer.accept(
                ModLootTables.pliersCuttingLoot(Blocks.ACACIA_LEAVES),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.ACACIA_SAPLING)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(6))
                        )
        );
        // 丛林树树叶
        consumer.accept(
                ModLootTables.pliersCuttingLoot(Blocks.JUNGLE_LEAVES),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.JUNGLE_SAPLING)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(7))
                        )
        );
        // 深色橡树树叶
        consumer.accept(
                ModLootTables.pliersCuttingLoot(Blocks.DARK_OAK_LEAVES),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.DARK_OAK_SAPLING)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(7))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.APPLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F,3.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(9))
                        )
        );
        // 杜鹃花树叶
        consumer.accept(
                ModLootTables.pliersCuttingLoot(Blocks.AZALEA_LEAVES),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.FLOWERING_AZALEA)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.AZALEA)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(5))
                        )
        );
        // 盛开的杜鹃花树叶
        consumer.accept(
                ModLootTables.pliersCuttingLoot(Blocks.FLOWERING_AZALEA_LEAVES),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.FLOWERING_AZALEA)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.AZALEA)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(5))
                        )
        );
        // 红树树叶
        consumer.accept(
                ModLootTables.pliersCuttingLoot(Blocks.MANGROVE_LEAVES),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(8))
                        )
        );
        // 染梦树叶
        consumer.accept(
                ModLootTables.pliersCuttingLoot(ModBlocks.DYEDREAM_LEAVES.get()),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SAPLING.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(6))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F,3.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(9))
                        )
        );
        // 染梦世界树树叶
        consumer.accept(
                ModLootTables.pliersCuttingLoot(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get()),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SAPLING.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(6))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F,3.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(9))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DUST_PIECE.get())
                                        .setWeight(50)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F,3.0F))))
                                // 0.1%概率掉落世界树种荚
                                .add(LootItem.lootTableItem(ModItems.WORLDTREE_SEEDPOD.get())
                                        .setWeight(1))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(949))
                        )

        );
        // 风泊树叶1
        consumer.accept(
                ModLootTables.pliersCuttingLoot(ModBlocks.WIND_MOOR_LEAVES_0.get()),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_MOOR_SAPLING.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(6))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.FIG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F,3.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(9))
                        )
                        // 概率生成鹦鹉
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.LOOT_GENERATOR.get())//吞掉这个物品
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SpawnEntityFunction.Builder.spawnOnly(
                                                EntityType.PARROT, 1, 0)))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(99))
                        )
        );
        // 风泊树叶2
        consumer.accept(
                ModLootTables.pliersCuttingLoot(ModBlocks.WIND_MOOR_LEAVES_1.get()),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_MOOR_SAPLING.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(6))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.FIG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F,3.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(9))
                        )
                        // 概率生成鹦鹉
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.LOOT_GENERATOR.get())//吞掉这个物品
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SpawnEntityFunction.Builder.spawnOnly(
                                                EntityType.PARROT, 1, 0)))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(99))
                        )
        );
        // 眠椰树叶
        consumer.accept(
                ModLootTables.pliersCuttingLoot(ModBlocks.SLUMBER_PALM_LEAVES.get()),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SLUMBER_PALM_SAPLING.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(6))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.SLUMBER_PALM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F,3.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(9))
                        )
        );
    }
}
