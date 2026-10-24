package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModLootTables;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModShadowDungeonRepositoryLootTablesProvider implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(
                ModLootTables.SHADOW_DUNGEON_REPOSITORY_COMMON,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.SHADOW.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_STONE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.THICK_SHADOW.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.DARK_CLOUD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_PLANKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                        )
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.NIGHTMARE_FUEL.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.RUST_BLACK_METAL_GRAIN.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.BLACK_METAL_GRAIN.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.SOUL_DUST.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_CANDLE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SOUL_ESSENCE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_LIGHT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(1))
                        )
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 6.0F))))
                                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ATTACK_ENHANCE_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LUCK_ENHANCE_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(3))
                        )
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.LAMP_SHADOW_BROKEN_NOTE.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMPTY_PROPHECY_CARD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(12))
                        )
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.ICE_SHADOW_CURIO.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WAR_FLAG.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GHOST_FACE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_BREATH.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CALAIS_SPICE_BOTTLE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(39))
                        )
                        .withPool(createShadowAlloyPool())
        );

        consumer.accept(
                ModLootTables.SHADOW_DUNGEON_REPOSITORY_CORE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.BLACK_METAL_INGOT.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.NIGHTMARE_FUEL.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.SOUL_ESSENCE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_LIGHT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                        )
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 6.0F))))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(8.0F, 16.0F))))
                                .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ATTACK_ENHANCE_STONE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LUCK_ENHANCE_STONE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(2))
                        )
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.ICE_SHADOW_CURIO.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WAR_FLAG.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GHOST_FACE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_BREATH.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CALAIS_SPICE_BOTTLE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMPTY_PROPHECY_CARD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(2)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(15.0F, 30.0F))
                                                .allowTreasure()))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(8))
                        )
                        .withPool(createShadowAlloyPool())
        );

        consumer.accept(
                ModLootTables.SHADOW_DUNGEON_REPOSITORY_SUPPLY,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(Items.BREAD)
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_MILK.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.COOKED_CHICKEN)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.APPLE)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHOCOLATE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_JELLY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.IRON_INGOT)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 6.0F))))
                                .add(LootItem.lootTableItem(Items.ARROW)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(8.0F, 16.0F))))
                                .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_CANDLE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_LIGHT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(1))
                        )
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.BLACK_METAL_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ATTACK_ENHANCE_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(10))
                        )
        );
    }

    private static LootPool.Builder createShadowAlloyPool() {
        return LootPool.lootPool()
                .setBonusRolls(ConstantValue.exactly(0.0F))
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(ModItems.SHADOW_ALLOY_INGOT.get())
                        .setWeight(3)
                        .apply(SetItemCountFunction.setCount(
                                ConstantValue.exactly(1.0F))))
                .add(EmptyLootItem.emptyItem()
                        .setWeight(197));
    }
}
