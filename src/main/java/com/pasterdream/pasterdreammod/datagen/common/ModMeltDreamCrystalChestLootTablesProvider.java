package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.functions.SpawnEntityFunction;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModMeltDreamCrystalChestLootTablesProvider implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "chests/melt_dream_crystal_chest_dyedream_world_normal"),
                LootTable.lootTable()
                        // ===== 染梦基础材料，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DUST.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_NUGGET.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 9.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN_PILE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_SLIMEBALL.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_NUGGET.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                        )
                        // ===== 稀有材料/矿物，2次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.MOLTEN_GOLD_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHARGED_AMETHYST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINEAPPLE_LOVE_SEA.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // ===== 饰品，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.HEALTH_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RABBIT_FOOT_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FIRE_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag(){ {
                                            putInt("lv", 1);
                                        }})))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag(){ {
                                            putInt("lv", 2);
                                        }})))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.TRAVELER_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GARLAND.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.NATURE_BELT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(7)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // ===== 染梦装备，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_HELMET.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_CHESTPLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_LEGGINGS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_BOOTS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SWORD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_AXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SHOVEL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_HOE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_PICKAXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(150))
                        )
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "chests/melt_dream_crystal_chest_dyedream_world_rare"),
                LootTable.lootTable()
                        // ===== 染梦基础材料，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DUST.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_NUGGET.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 9.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN_PILE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_SLIMEBALL.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_NUGGET.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                        )
                        // ===== 稀有材料/矿物，2次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.MOLTEN_GOLD_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHARGED_AMETHYST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINEAPPLE_LOVE_SEA.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LIFE_CRYSTAL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // ===== 饰品，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.HEALTH_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RABBIT_FOOT_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FIRE_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag(){ {
                                            putInt("lv", 1);
                                        }})))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag(){ {
                                            putInt("lv", 2);
                                        }})))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.TRAVELER_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GARLAND.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.NATURE_BELT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(7)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // ===== 染梦装备，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_HELMET.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_CHESTPLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_LEGGINGS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_BOOTS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SWORD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_AXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SHOVEL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_HOE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_PICKAXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(150))
                        )
                        //===== 稀有装备 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.FEATHER_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CROSS_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CARAPAX_CHARM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SAND_OF_TIME.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_LIQUID_BUCKET.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ELIXIR_BOTTLE_OF_RAGE_ELIXIR.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DUKE_COIN_CURIO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LIGHT_BUTTERFLY_CURIO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SCULK_UPGRADE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.YINHUL_COTTON_CANDY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SNOW_VOW_HEAD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SWEET_DREAM_MUSIC_DISC.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SNOWFALL_DREAM_MUSIC_DISC.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SLIVER_BELL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FOURLEAF_CLOVER_CURIO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LOOT_GENERATOR.get())//吞掉这个物品
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SpawnEntityFunction.Builder.spawnOnly(
                                                ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get(), 1, 0)))
                        )
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "chests/melt_dream_crystal_chest_dyedream_world_legend"),
                LootTable.lootTable()
                        // ===== 染梦基础材料，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DUST.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_NUGGET.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 9.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN_PILE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_SLIMEBALL.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_NUGGET.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                        )
                        // ===== 稀有材料/矿物，2次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.MOLTEN_GOLD_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHARGED_AMETHYST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINEAPPLE_LOVE_SEA.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LIFE_CRYSTAL.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // ===== 饰品，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.HEALTH_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RABBIT_FOOT_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FIRE_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag(){ {
                                            putInt("lv", 1);
                                        }})))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag(){ {
                                            putInt("lv", 2);
                                        }})))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.TRAVELER_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GARLAND.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.NATURE_BELT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(7)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FOURLEAF_CLOVER_CURIO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // ===== 染梦装备，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_HELMET.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_CHESTPLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_LEGGINGS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_BOOTS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SWORD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_AXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SHOVEL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_HOE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_PICKAXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(150))
                        )
                        //===== 传奇装备 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.BOBOJI_CURIO.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BROKEN_HERO_SWORD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.TERRA_FLOATING_ISLAND.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.QYM_DOLL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ALLKINDS_RING.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.HIYORI_BUTTERFLY_HAIRPIN.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LIGHT_BUTTERFLY_CURIO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.YINHUL_COTTON_CANDY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SNOW_VOW_HEAD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SWEET_DREAM_MUSIC_DISC.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SNOWFALL_DREAM_MUSIC_DISC.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SLIVER_BELL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SAND_OF_TIME.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_LIQUID_BUCKET.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DUKE_COIN_CURIO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ELIXIR_BOTTLE_OF_RAGE_ELIXIR.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LEGENDARY_DRAGON_HORN_ICE_CREAM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LOOT_GENERATOR.get())//吞掉这个物品
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SpawnEntityFunction.Builder.spawnOnly(
                                                ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get(), 1, 0)))
                        )
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "chests/melt_dream_crystal_chest_wind_journey_world_normal"),
                LootTable.lootTable()
                        // ===== 风旅基础材料，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN_PILE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.HAIRY_MOSS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                        )
                        // ===== 稀有材料/矿物，2次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.MOLTEN_GOLD_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHARGED_AMETHYST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_JELLY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // ===== 饰品，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.HEALTH_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RABBIT_FOOT_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FIRE_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag(){ {
                                            putInt("lv", 1);
                                        }})))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag(){ {
                                            putInt("lv", 2);
                                        }})))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.TRAVELER_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GARLAND.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.NATURE_BELT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(7)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // ===== 凝风铁装备，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_SWORD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_PICKAXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_AXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_SHOVEL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_HOE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(150))
                        )
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "chests/melt_dream_crystal_chest_wind_journey_world_rare"),
                LootTable.lootTable()
                        // ===== 风旅基础材料，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN_PILE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.HAIRY_MOSS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                        )
                        // ===== 稀有材料/矿物，2次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.MOLTEN_GOLD_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHARGED_AMETHYST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_JELLY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // ===== 饰品，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.HEALTH_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RABBIT_FOOT_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FIRE_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag(){ {
                                            putInt("lv", 1);
                                        }})))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag(){ {
                                            putInt("lv", 2);
                                        }})))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.TRAVELER_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GARLAND.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.NATURE_BELT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(7)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // ===== 凝风铁装备，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_SWORD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_PICKAXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_AXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_SHOVEL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_HOE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(150))
                        )
                        //===== 稀有装备 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.FEATHER_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CROSS_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CARAPAX_CHARM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LIFE_CRYSTAL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SAND_OF_TIME.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_LIQUID_BUCKET.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ELIXIR_BOTTLE_OF_RAGE_ELIXIR.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DUKE_COIN_CURIO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LIGHT_BUTTERFLY_CURIO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SCULK_UPGRADE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.YINHUL_COTTON_CANDY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SNOW_VOW_HEAD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SLIVER_BELL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_KNIGHT_FLAG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.PAPER_PLANE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_VANE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BREAK_WIND_CURTAIN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_JOURNEY_MUSIC_DISC.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LUCK_ENHANCE_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FOURLEAF_CLOVER_CURIO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LOOT_GENERATOR.get())//吞掉这个物品
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SpawnEntityFunction.Builder.spawnOnly(
                                                ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get(), 1, 0)))
                        )
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "chests/melt_dream_crystal_chest_wind_journey_world_legend"),
                LootTable.lootTable()
                        // ===== 风旅基础材料，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN_PILE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.HAIRY_MOSS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                        )
                        // ===== 稀有材料/矿物，2次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.MOLTEN_GOLD_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHARGED_AMETHYST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_JELLY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // ===== 饰品，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.HEALTH_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RABBIT_FOOT_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FIRE_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag(){ {
                                            putInt("lv", 1);
                                        }})))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag(){ {
                                            putInt("lv", 2);
                                        }})))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.TRAVELER_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GARLAND.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.NATURE_BELT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(7)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FOURLEAF_CLOVER_CURIO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // ===== 凝风铁装备，固定1次抽取 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_SWORD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_PICKAXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_AXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_SHOVEL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_HOE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(150))
                        )
                        //===== 传奇装备 =====
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.BOBOJI_CURIO.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BROKEN_HERO_SWORD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.TERRA_FLOATING_ISLAND.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.QYM_DOLL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ALLKINDS_RING.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.HIYORI_BUTTERFLY_HAIRPIN.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LIGHT_BUTTERFLY_CURIO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.YINHUL_COTTON_CANDY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SNOW_VOW_HEAD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SLIVER_BELL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SAND_OF_TIME.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_LIQUID_BUCKET.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DUKE_COIN_CURIO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LIFE_CRYSTAL.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ELIXIR_BOTTLE_OF_RAGE_ELIXIR.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LEGENDARY_DRAGON_HORN_ICE_CREAM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_JOURNEY_MUSIC_DISC.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FLUFFY_WIND_ALLOY_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FLUFFY_WIND_ALLOY_SWORD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FLUFFY_WIND_ALLOY_PICKAXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FLUFFY_WIND_ALLOY_AXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FLUFFY_WIND_ALLOY_SHOVEL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FLUFFY_WIND_ALLOY_HOE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_KNIGHT_FLAG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BREAK_WIND_CURTAIN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LUCK_ENHANCE_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LOOT_GENERATOR.get())//吞掉这个物品
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SpawnEntityFunction.Builder.spawnOnly(
                                                ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get(), 1, 0)))
                        )
        );
    }

}
