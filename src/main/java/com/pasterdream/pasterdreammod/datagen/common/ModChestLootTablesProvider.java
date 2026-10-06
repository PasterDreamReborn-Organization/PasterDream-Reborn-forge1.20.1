package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;
public class ModChestLootTablesProvider implements LootTableSubProvider {
    /**
     *璧勬枡鏉ユ簮浜?minecraft wiki:
     * bonus_rolls锛氾紙榛樿涓?锛夋牴鎹垬鍒╁搧涓婁笅鏂囨彁渚涚殑骞歌繍鍊煎鍔犳娊鍙栨鏁般€傛父鎴忎細灏嗙帺瀹跺垢杩愬€煎睘鎬х殑鍊煎拰閽撻奔鏃跺伐鍏蜂笂fishing_luck_bonus榄斿拻鏁堟灉鐨勭瓑绾х浉鍔犲悗锛屼笌姝ゅ瓧娈电殑鍊肩浉涔樺苟鍚戜笅鍙栨暣锛屼綔涓洪澶栫殑鎶藉彇娆℃暟銆?
     * 鎹㈠彞璇濊锛屾娊鍙栨鏁扮殑璁＄畻鍏紡涓猴細final_rolls = [rolls + luck * bonus_rolls]
     **/
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/picnic_basket_overworld"),//鍘焞oots_relic_9
                LootTable.lootTable()
                        // 骞歌繍褰卞搷绯绘暟0.15锛岀Щ闄ゅ師鐗堢殑2-4娆℃娊鍙栵紝鏀逛负鍥哄畾4娆℃娊鍙?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(4.0F))
                                .add(LootItem.lootTableItem(Items.BREAD)
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.APPLE)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.FRIED_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.PUMPKIN_PIE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.MUSHROOM_STEW)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.COOKED_CHICKEN)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_MILK.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.BACON_AND_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.SWEET_BERRIES)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.COOKIE)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_HONEY_JUICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.DRIED_KELP)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CREAM_BUN_CAKE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHOCOLATE.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/picnic_basket_dyedream_world"),
                LootTable.lootTable()
                        // 骞歌繍褰卞搷绯绘暟0.15锛岀Щ闄ゅ師鐗堢殑2-4娆℃娊鍙栵紝鏀逛负鍥哄畾4娆℃娊鍙?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(4.0F))
                                .add(LootItem.lootTableItem(Items.BREAD)
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_HEART_CHOCOLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_DREAM_JUICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT_BUN_CAKE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_WATERMELON_JUICE.get())//TODO:鍔犲叆鐪犳ぐ鏍戠敓鎴愬悗鏇挎崲涓烘捣鐩愮湢妞伴ギ
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.APPLE)//TODO:鍔犲叆鐪犳ぐ鏍戠敓鎴愬悗鏇挎崲涓虹湢妞?
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.SANDWICH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.POPPING_CANDY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_HONEY_JUICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CREAM_BUN_CAKE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.BUBBLE_GUM.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/dyedream_relic_chest_loot_common"),//鍘焞oots_relic_0
                LootTable.lootTable()
                        // 鏌撴ⅵ鐗╁搧锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.15
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DUST_PIECE.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_SLIME_BLOCK.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GALAXY_JELLY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(5))
                        )
                        // 妞嶇墿锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.1
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SAPLING.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD_CROP_AGE_1.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.LIGHT_BALL_CROP_AGE_1.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_LILY_OF_THE_VALLEY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.SINGULARITY_FERN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.LINHT_FLOWER.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_MUSHROOM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )

                        .withPool(LootPool.lootPool()
                                // 椋熸潗&椋熺墿锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.15
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(3.0F))
                                .add(LootItem.lootTableItem(ModItems.DOUGH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.FLOUR.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.DOUGH_WITH_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.CAKE_BASE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHOCOLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.SUGAR)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_WATER.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FRIED_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.BACON_AND_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ODD_BACON_AND_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RICE_CAKE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SANDWICH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BUBBLE_GUM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_YEAST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SANDWICH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )

                        .withPool(LootPool.lootPool()
                                // 寤烘潗锛屽浐瀹?鎶藉彇锛屼笉鎻愪緵棰濆鎶藉彇娆℃暟
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.POLISHED_CALCITE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.CALCITE_TILES.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_QUARTZ.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 16.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_GLASS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.CARVE_DYEDREAM_GLASS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 16.0F))))
                                .add(LootItem.lootTableItem(ModItems.PILLAR_DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.BRICKS_DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_LOG.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_PLANKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SAND.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))

                        )
                        .withPool(LootPool.lootPool()
                                // 瑁呭&宸ュ叿锛屽浐瀹?鎶藉彇锛屼笉鎻愪緵棰濆鎶藉彇娆℃暟
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.PALE_BONENEEDLE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MORTAR.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.PLIERS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SORBENT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DYE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(2.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_PICKAXE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_SWORD)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_SHOVEL)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_AXE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_HOE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.SHEARS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_HELMET)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_LEGGINGS)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_BOOTS)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                        )
                        .withPool(LootPool.lootPool()
                                // 鏉愭枡鐗╁搧锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.25
                                .setBonusRolls(ConstantValue.exactly(0.25F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DUST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_NUGGET.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_NUGGET.get())
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_BROKEN_NOTE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(90))
                        )
                        .withPool(LootPool.lootPool()
                                // 鏌撴ⅵ瑁呭
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_UPGRADE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SWORD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_PICKAXE.get())
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
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_UPGRADE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(400))
                        )
                        .withPool(LootPool.lootPool()
                                // 楗板搧锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇绯绘暟
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SOUL_ESSENCE.get())
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
                                .add(LootItem.lootTableItem(ModItems.TRAVELER_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(30)))
                        .withPool(LootPool.lootPool()
                                // 闄勯瓟涔︼紝鍥哄畾1鎶藉彇锛屾棤骞歌繍淇绯绘暟
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 20.0F))
                                                .allowTreasure()))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(3))
                        )
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/dyedream_relic_chest_loot_rare"),//鍘焞oots_relic_1
                LootTable.lootTable()
                        // 鏌撴ⅵ鐗╁搧锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.2
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.2F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DUST_PIECE.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_SLIME_BLOCK.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.GALAXY_JELLY.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(9))
                        )
                        // 妞嶇墿锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.1
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SAPLING.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD_CROP_AGE_1.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.LIGHT_BALL_CROP_AGE_1.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_LILY_OF_THE_VALLEY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.SINGULARITY_FERN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.LINHT_FLOWER.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_MUSHROOM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )

                        .withPool(LootPool.lootPool()
                                // 椋熸潗&椋熺墿锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.15
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(3.0F))
                                .add(LootItem.lootTableItem(ModItems.DOUGH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.FLOUR.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.DOUGH_WITH_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CAKE_BASE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHOCOLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.SUGAR)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_WATER.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FRIED_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.BACON_AND_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ODD_BACON_AND_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RICE_CAKE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SANDWICH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BUBBLE_GUM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_YEAST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SANDWICH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )

                        .withPool(LootPool.lootPool()
                                // 寤烘潗锛屽浐瀹?鎶藉彇锛屼笉鎻愪緵棰濆鎶藉彇娆℃暟
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.POLISHED_CALCITE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.CALCITE_TILES.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_QUARTZ.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_GLASS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.CARVE_DYEDREAM_GLASS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 16.0F))))
                                .add(LootItem.lootTableItem(ModItems.PILLAR_DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.BRICKS_DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_LOG.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_PLANKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SAND.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))

                        )
                        .withPool(LootPool.lootPool()
                                // 瑁呭&宸ュ叿锛屽浐瀹?鎶藉彇锛屽垢杩愪慨姝ｇ郴鏁?.1
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.PALE_BONENEEDLE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MORTAR.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SORBENT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DYE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(2.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_PICKAXE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_SWORD)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_SHOVEL)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_AXE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_HOE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.SHEARS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_HELMET)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_LEGGINGS)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_BOOTS)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                        )
                        .withPool(LootPool.lootPool()
                                // 鏉愭枡鐗╁搧锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.25
                                .setBonusRolls(ConstantValue.exactly(0.25F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DUST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_NUGGET.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_NUGGET.get())
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(80))
                        )
                        .withPool(LootPool.lootPool()
                                // 鏌撴ⅵ瑁呭
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_UPGRADE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SWORD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_PICKAXE.get())
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
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_UPGRADE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(400))
                        )
                        .withPool(LootPool.lootPool()
                                // 楗板搧锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇绯绘暟
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SOUL_ESSENCE.get())
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
                                .add(LootItem.lootTableItem(ModItems.TRAVELER_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(50)))
                        .withPool(LootPool.lootPool()
                                // 闄勯瓟涔︼紝鍥哄畾1鎶藉彇锛屾棤骞歌繍淇绯绘暟
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 20.0F))
                                                .allowTreasure()))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(3))
                        )

        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/shadow_relic_chest_loot_common"),//鍘焞oots_relic_3
                LootTable.lootTable()
                        // 闃村奖寤虹瓚鏂瑰潡锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.15
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.SHADOW.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.DARK_CLOUD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.THICK_SHADOW.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_STEM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_PLANKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(1))
                        )
                        // 闃村奖妞嶇墿锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_SHORT_ROOTS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_ROOTS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_STEM_FERN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_SPROUTS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_FERN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WHITE_ORCHID_FLOWER.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_FUNGUS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                        )
                        // 鏉愭枡&鏉傜墿锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.15
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.COBWEB)
                                        .setWeight(10)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_CANDLE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.RUST_BLACK_METAL_GRAIN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_LIQUID_BUCKET.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LAMP_SHADOW_BROKEN_NOTE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )
                        // 绋€鏈夌墿鍝佹贩鍏ワ紝鍥哄畾1鎶藉彇锛屾棤骞歌繍淇
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.NIGHTMARE_FUEL.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.LAMP_SHADOW_BROKEN_NOTE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CALAIS_SPICE_BOTTLE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMPTY_PROPHECY_CARD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GHOST_FACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_BREATH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ICE_SHADOW_CURIO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WAR_FLAG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(40))
                        )
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/desert_fortress_chest"),//鍘焞oots_relic_4
                LootTable.lootTable()
                        // 娌欐紶寤烘潗锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.SAND)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(5.0F, 7.0F))))
                                .add(LootItem.lootTableItem(Items.SANDSTONE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(Items.CHISELED_SANDSTONE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )
                        // 妞嶇墿&椋熺墿锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.DEAD_BUSH)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.RYE_SEED.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.BEETROOT)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // 鏉傜墿锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.COBWEB)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 5.0F))))
                                .add(LootItem.lootTableItem(Items.STRING)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 5.0F))))
                                .add(LootItem.lootTableItem(Items.BONE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.ARROW)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                        )
                        // 宸ュ叿&瑁呭锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.NAME_TAG)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.FLINT_AND_STEEL)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // 鏉愭枡&璐甸噸鐗╁搧锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.PERGAMYN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_INK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.LEATHER)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.GOLD_NUGGET)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 5.0F))))
                        )
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/desert_cottage_chest"),
                LootTable.lootTable()
                        // 娌欐紶寤烘潗锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.SAND)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(5.0F, 7.0F))))
                                .add(LootItem.lootTableItem(Items.SANDSTONE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(Items.CHISELED_SANDSTONE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )
                        // 妞嶇墿&椋熺墿锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.DEAD_BUSH)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.RYE_SEED.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.BEETROOT)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RICE_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 7.0F))))
                        )
                        // 宸ュ叿&瑁呭锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.NAME_TAG)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.FLINT_AND_STEEL)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        // 鏉愭枡&璐甸噸鐗╁搧锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.PERGAMYN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_INK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ATTACK_ENHANCE_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.LUCK_ENHANCE_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.PROTECT_DECK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                        )
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/fisherman_hut_chest"),//鍘焞oots_relic_5
                LootTable.lootTable()
                        // 姘翠骇锛屾敼涓哄浐瀹?娆℃娊鍙栵紝涓嶅彈骞歌繍褰卞搷
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(Items.COD)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 6.0F))))
                                .add(LootItem.lootTableItem(Items.SALMON)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.TROPICAL_FISH)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.PUFFERFISH)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.SEAGRASS)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 7.0F))))
                                .add(LootItem.lootTableItem(Items.KELP)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 5.0F))))
                                .add(LootItem.lootTableItem(Items.SEA_PICKLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                        )
                        //椋熸潗锛屾娊鍙?娆★紝鏃犲垢杩愪慨姝?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_WATER.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.WATER_BUCKET)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.COARSE_SALT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                        )
                        //鏉傜墿锛屾娊鍙?娆★紝鏃犲垢杩愪慨姝?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.INK_SAC)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.STRING)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.SCUTE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.FISHING_ROD)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(10.0F, 20.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.NAME_TAG)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FABRIC.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 20.0F))
                                                .allowTreasure()))
                        )
                        //鐪熸椿楸肩湡濂藉悆锛屾娊鍙?娆★紝鏃犲垢杩愪慨姝?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.WAFER_BISCUIT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.STUFFED_WAFER_COOKIES.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.BEETROOT_SOUP)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.DRIED_KELP)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.BREAD)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.BREAD_SLICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )
                        //椋熺墿锛屾娊鍙?娆★紝鏃犲垢杩愪慨姝?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.COD_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.SALMON_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.PUFFERFISH_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.TROPICAL_FISH_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.AXOLOTL_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        //閽撻奔瀹濆專锛屾娊鍙?娆★紝0.25骞歌繍淇绯绘暟
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.25F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.DEEP_SEA_TREASURE.get())
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/ecosystem_bubble_chest"),
                LootTable.lootTable()
                        // 姘翠骇锛屾敼涓哄浐瀹?娆℃娊鍙栵紝涓嶅彈骞歌繍褰卞搷
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(Items.COD)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 6.0F))))
                                .add(LootItem.lootTableItem(Items.SALMON)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.TROPICAL_FISH)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.PUFFERFISH)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.SEAGRASS)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 7.0F))))
                                .add(LootItem.lootTableItem(Items.KELP)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 5.0F))))
                                .add(LootItem.lootTableItem(Items.SEA_PICKLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                        )
                        //椋熸潗锛屾娊鍙?娆★紝鏃犲垢杩愪慨姝?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_WATER.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.WATER_BUCKET)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.COARSE_SALT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                        )
                        //鏉傜墿锛屾娊鍙?娆★紝鏃犲垢杩愪慨姝?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.INK_SAC)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.STRING)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.SCUTE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.FISHING_ROD)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(10.0F, 20.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.NAME_TAG)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FABRIC.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1.0F, 20.0F))
                                                .allowTreasure()))
                        )
                        //鐪熸椿楸肩湡濂藉悆锛屾娊鍙?娆★紝鏃犲垢杩愪慨姝?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.WAFER_BISCUIT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_HEART_CHOCOLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.DRIED_KELP)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.BREAD)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT_BUN_CAKE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )
                        //椋熺墿锛屾娊鍙?娆★紝鏃犲垢杩愪慨姝?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.COD_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.SALMON_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.PUFFERFISH_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.TROPICAL_FISH_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.AXOLOTL_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        //鏌撴ⅵ閽撻奔瀹濆專锛屾娊鍙?娆★紝0.25骞歌繍淇绯绘暟
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.25F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get())
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )
        );

        //娴嬭瘯鎴樺埄鍝佸垪琛?
        consumer.accept(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/test_loot_table_0"), LootTable.lootTable()
                //鎶藉彇1娆★紝0骞歌繍鎶藉彇锛屾瘡娆?涓懡浠ゆ柟鍧?
                .withPool(LootPool.lootPool()
                        .setBonusRolls(ConstantValue.exactly(0F))
                        .setRolls(ConstantValue.exactly(1F))
                        .add(LootItem.lootTableItem(Items.COMMAND_BLOCK)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))))
                //鎶藉彇1娆★紝0骞歌繍鎶藉彇锛屾瘡娆?-16涓▏灏忕惔闆ㄦⅵ鐜╁伓
                .withPool(LootPool.lootPool()
                        .setBonusRolls(ConstantValue.exactly(0F))
                        .setRolls(ConstantValue.exactly(1F))
                        .add(LootItem.lootTableItem(ModItems.QYM_DOLL.get())
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 16F)))))
                //鎶藉彇1娆★紝0骞歌繍鎶藉彇锛屾瘡娆?/3姒傜巼鑾峰緱1涓粨鏋勬柟鍧楋紝1/3姒傜巼鑾峰緱1涓粨鏋勭┖浣?
                .withPool(LootPool.lootPool()
                        .setBonusRolls(ConstantValue.exactly(0F))
                        .setRolls(ConstantValue.exactly(1F))
                        .add(LootItem.lootTableItem(Items.STRUCTURE_BLOCK)
                                .setWeight(2)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F))))
                        .add(LootItem.lootTableItem(Items.STRUCTURE_VOID)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))))
                //闅忔満鎶藉彇2-16娆★紝0骞歌繍鎶藉彇锛屾瘡娆?涓牬纰庣矑瀛愭彁渚涙柟鍧?
                .withPool(LootPool.lootPool()
                        .setBonusRolls(ConstantValue.exactly(0F))
                        .setRolls(UniformGenerator.between(2F, 16F))
                        .add(LootItem.lootTableItem(ModItems.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0.get())
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F)))))
                //鎶藉彇1娆★紝16骞歌繍褰卞搷锛屾瘡娆?涓牬纰庣矑瀛愭彁渚涙柟鍧?
                .withPool(LootPool.lootPool()
                        .setBonusRolls(ConstantValue.exactly(16F))
                        .setRolls(ConstantValue.exactly(1F))
                        .add(LootItem.lootTableItem(ModItems.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1.get())
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F))))));

        //娴嬭瘯鎴樺埄鍝佸垪琛?
        consumer.accept(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/test_loot_table_1"), LootTable.lootTable()
                //鎶藉彇1娆★紝0骞歌繍鎶藉彇锛屾瘡娆?涓繛閿佸瀷鍛戒护鏂瑰潡
                .withPool(LootPool.lootPool()
                        .setBonusRolls(ConstantValue.exactly(0F))
                        .setRolls(ConstantValue.exactly(1F))
                        .add(LootItem.lootTableItem(Items.CHAIN_COMMAND_BLOCK)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F))))));

        //娴嬭瘯鎴樺埄鍝佸垪琛?
        consumer.accept(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/test_loot_table_2"), LootTable.lootTable()
                //鎶藉彇1娆★紝0骞歌繍鎶藉彇锛屾瘡娆?涓惊鐜瀷鍛戒护鏂瑰潡
                .withPool(LootPool.lootPool()
                        .setBonusRolls(ConstantValue.exactly(0F))
                        .setRolls(ConstantValue.exactly(1F))
                        .add(LootItem.lootTableItem(Items.REPEATING_COMMAND_BLOCK)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F))))));

        // === 椋庝箣鏃呴€旂淮搴︽垬鍒╁搧 ===

        // 椋庝箣鏃呴€旈€氱敤鎴樺埄鍝侊紙鍘?loots_relic_6锛夛細鐑皵鐞?娉㈠/鐏垫ⅵ/椋庤溅灏忓眿/澶辫惤椋庨獞澹仐杩?
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/wind_journey_chest"),
                LootTable.lootTable()
                        // 缁村害鐗硅壊鐗╁搧锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.15
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MAGIC_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(5))
                        )
                        // 妞嶇墿锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.1
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.HAIRY_MOSS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_CLEAVING_GRASS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_FEATHER_GRASS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_ISLAND_REED.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )
                        // 椋熺墿锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.15
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(3.0F))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_JELLY.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.FORTUNE_JELLY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.BREAD)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.APPLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.COOKED_CHICKEN)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_MILK.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHOCOLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.JELLYFISH_MUD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.JELLYFISH_JELLO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.FIG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                        )
                        // 寤烘潗锛屽浐瀹?鎶藉彇锛屼笉鎻愪緵棰濆鎶藉彇娆℃暟
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CYAN_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.CYAN_STONE_BRICKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.MOSSY_CYAN_STONE_BRICKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_MOOR_LOG.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_MOOR_PLANKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 16.0F))))
                                .add(LootItem.lootTableItem(ModItems.THICK_CLOUD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 16.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_LANTERN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                        )
                        // 瑁呭&宸ュ叿锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.1
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_PICKAXE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_SWORD.get())
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
                                .add(LootItem.lootTableItem(Items.IRON_PICKAXE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_SWORD)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 4.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 12.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag() { {
                                            put("Fireworks", new CompoundTag() { {
                                                putByte("Flight", (byte) 2);
                                                put("Explosions", new ListTag() { {
                                                    add(new CompoundTag() { {
                                                        putByte("Type", (byte) 1);
                                                        putIntArray("Colors", new int[] { 0x9DD6FF });
                                                        putIntArray("FadeColors", new int[] { 0xFFFFFF });
                                                    } });
                                                } });
                                            } });
                                        } })))
                                .add(LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 12.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag() { {
                                            put("Fireworks", new CompoundTag() { {
                                                putByte("Flight", (byte) 2);
                                            } });
                                        } })))
                                .add(LootItem.lootTableItem(Items.FEATHER)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 8.0F))))
                        )
                        // 鏉愭枡鐗╁搧锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.25
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.25F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(90))
                        )
                        // 绋€鏈夋潗鏂欙紝鍥哄畾1鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?锛堣惁椋庡悎閲戦敪涓築oss浜у嚭鐨勫欢浼革紝姒傜巼鏋佷綆锛?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(1.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.FLUFFY_WIND_ALLOY_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(400))
                        )
                        // 鐗规畩鐗╁搧锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇绯绘暟
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.PAPER_PLANE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_VANE.get())
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
                                .add(LootItem.lootTableItem(ModItems.WIND_JOURNEY_MUSIC_DISC.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LUCK_ENHANCE_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(120)))
                        // 闄勯瓟涔︼紝鍥哄畾1鎶藉彇锛屾棤骞歌繍淇绯绘暟
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(1.0F, 20.0F))
                                                .allowTreasure()))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(3))
                        )
        );

        // 椋庝箣鏃呴€旂█鏈夋垬鍒╁搧锛堝師 loots_relic_7锛夛細椋庡矝/椋庤溅灏忓眿/澶辫惤椋庨獞澹仐杩?
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/wind_journey_chest_rare"),
                LootTable.lootTable()
                        // 缁村害鐗硅壊鐗╁搧锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.2
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.2F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.MAGIC_STONE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(9))
                        )
                        // 妞嶇墿锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.1
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.HAIRY_MOSS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_CLEAVING_GRASS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_FEATHER_GRASS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_ISLAND_REED.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                        )
                        // 椋熺墿锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.15
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(3.0F))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_JELLY.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.FORTUNE_JELLY.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.BREAD)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.APPLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.COOKED_CHICKEN)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_MILK.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHOCOLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.JELLYFISH_MUD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.JELLYFISH_JELLO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.FIG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                        )
                        // 寤烘潗锛屽浐瀹?鎶藉彇锛屼笉鎻愪緵棰濆鎶藉彇娆℃暟
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CYAN_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 48.0F))))
                                .add(LootItem.lootTableItem(ModItems.CYAN_STONE_BRICKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 48.0F))))
                                .add(LootItem.lootTableItem(ModItems.MOSSY_CYAN_STONE_BRICKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 48.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_MOOR_LOG.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 48.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_MOOR_PLANKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 48.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.THICK_CLOUD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_LANTERN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 6.0F))))
                        )
                        // 瑁呭&宸ュ叿锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.1
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_PICKAXE.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(2.0F, 5.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_SWORD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(2.0F, 5.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_SHOVEL.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(2.0F, 5.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_PICKAXE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(2.0F, 5.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.IRON_SWORD)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(2.0F, 5.0F))
                                                .allowTreasure()))
                                .add(LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(6.0F, 16.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag() { {
                                            put("Fireworks", new CompoundTag() { {
                                                putByte("Flight", (byte) 3);
                                                put("Explosions", new ListTag() { {
                                                    add(new CompoundTag() { {
                                                        putByte("Type", (byte) 1);
                                                        putIntArray("Colors", new int[] { 0x9DD6FF, 0xFFFFFF });
                                                        putIntArray("FadeColors", new int[] { 0x55AAFF });
                                                    } });
                                                } });
                                            } });
                                        } })))
                                .add(LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(6.0F, 16.0F)))
                                        .apply(SetNbtFunction.setTag(new CompoundTag() { {
                                            put("Fireworks", new CompoundTag() { {
                                                putByte("Flight", (byte) 3);
                                            } });
                                        } })))
                                .add(LootItem.lootTableItem(Items.FEATHER)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 10.0F))))
                        )
                        // 鏉愭枡鐗╁搧锛屽浐瀹?鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?.25
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.25F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 6.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(80))
                        )
                        // 绋€鏈夋潗鏂欙紝鍥哄畾1鎶藉彇锛屽垢杩愬奖鍝嶇郴鏁?锛堣惁椋庡悎閲戦敪涓築oss浜у嚭鐨勫欢浼革紝姒傜巼鏋佷綆锛?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(1.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.FLUFFY_WIND_ALLOY_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(300))
                        )
                        // 鐗规畩鐗╁搧锛屽浐瀹?鎶藉彇锛屾棤骞歌繍淇绯绘暟
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.PAPER_PLANE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_VANE.get())
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
                                .add(LootItem.lootTableItem(ModItems.WIND_JOURNEY_MUSIC_DISC.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(70)))
                        // 闄勯瓟涔︼紝鍥哄畾1鎶藉彇锛屾棤骞歌繍淇绯绘暟
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(5.0F, 25.0F))
                                                .allowTreasure()))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(2))
                        )
        );

        // 鍦ｈ癁鏍戞垬鍒╁搧锛堝師 loots_relic_8锛?
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,"chests/christmas_tree_chest"),
                LootTable.lootTable()
                        // 鐢滈&椋熺墿锛屾娊鍙?~5娆★紝鏃犲垢杩愪慨姝?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0F))
                                .setRolls(UniformGenerator.between(3F, 5F))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_MILK.get())
                                        .setWeight(7)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 3F))))
                                .add(LootItem.lootTableItem(ModItems.CREAM_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(ModItems.BERRY_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(ModItems.TUBER_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(ModItems.WATERMELON_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(ModItems.PUMPKIN_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(ModItems.GLOW_BERRY_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_APPLE_JUICE.get())
                                        .setWeight(10)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 4F))))
                                .add(LootItem.lootTableItem(ModItems.GARLAND.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(LootItem.lootTableItem(ModItems.LUCK_ENHANCE_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(LootItem.lootTableItem(ModItems.CHOCOLATE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 3F))))
                                .add(LootItem.lootTableItem(ModItems.WAFER_BISCUIT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 3F))))
                                .add(LootItem.lootTableItem(ModItems.STUFFED_WAFER_COOKIES.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(ModItems.SWISS_ROLL.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(ModItems.HEART_CHOCOLATE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(ModItems.WHITE_HEART_CHOCOLATE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_HEART_CHOCOLATE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(LootItem.lootTableItem(ModItems.CANDY_CANE.get())
                                        .setWeight(20)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 5F))))
                                .add(LootItem.lootTableItem(ModItems.GINGERBREAD_MAN.get())
                                        .setWeight(20)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 5F))))
                                .add(LootItem.lootTableItem(Items.APPLE)
                                        .setWeight(20)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 5F))))
                                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(LootItem.lootTableItem(Items.CAKE)
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(LootItem.lootTableItem(Items.COOKED_CHICKEN)
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(Items.SWEET_BERRIES)
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 7F))))
                                .add(LootItem.lootTableItem(Items.HONEY_BOTTLE)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(ModItems.JELLYFISH_MUD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.JELLYFISH_JELLO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.FIG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_JELLY.get())
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 5.0F)))))
                        // 瑁呴グ&褰╃伅锛屾娊鍙?~3娆★紝鏃犲垢杩愪慨姝?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0F))
                                .setRolls(UniformGenerator.between(2F, 3F))
                                .add(LootItem.lootTableItem(Items.SPRUCE_LEAVES)
                                        .setWeight(8)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(16F, 32F))))
                                .add(LootItem.lootTableItem(Items.YELLOW_STAINED_GLASS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4F, 8F))))
                                .add(LootItem.lootTableItem(Items.RED_STAINED_GLASS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4F, 8F))))
                                .add(LootItem.lootTableItem(Items.BLUE_STAINED_GLASS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4F, 8F))))
                                .add(LootItem.lootTableItem(Items.WHITE_STAINED_GLASS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4F, 8F))))
                                .add(LootItem.lootTableItem(Items.PINK_STAINED_GLASS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4F, 8F))))
                                .add(LootItem.lootTableItem(ModItems.CHRISTMAS_LIGHTS.get())
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(5F, 9F)))))
                        // 绋€鏈夋帀钀斤紝鎶藉彇1娆★紙鍘熶綔 tabitem_1 鍗犱綅鏉冮噸100 鈫?绌烘潯鐩級锛屾棤骞歌繍淇
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0F))
                                .setRolls(ConstantValue.exactly(1F))
                                .add(LootItem.lootTableItem(ModItems.SNOW_VOW_HEAD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(LootItem.lootTableItem(ModItems.SNOWFALL_DREAM_MUSIC_DISC.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(100)))
                        // 闆櫙鐗╁搧锛屾娊鍙?~2娆★紝鏃犲垢杩愪慨姝?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0F))
                                .setRolls(UniformGenerator.between(1F, 2F))
                                .add(LootItem.lootTableItem(Items.SNOWBALL)
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(LootItem.lootTableItem(Items.POWDER_SNOW_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(LootItem.lootTableItem(Items.SNOW_BLOCK)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(LootItem.lootTableItem(Items.ICE)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(LootItem.lootTableItem(ModItems.EDELWEISS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                        UniformGenerator.between(1F, 30F))
                                                .allowTreasure())))
                        // 瀹濈煶锛屾娊鍙?娆★紝鏃犲垢杩愪慨姝?
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0F))
                                .setRolls(ConstantValue.exactly(1F))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1F))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 3F))))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 2F))))
                                .add(LootItem.lootTableItem(Items.AMETHYST_SHARD)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1F, 3F))))
                                .add(LootItem.lootTableItem(Items.SPRUCE_LEAVES)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(8F, 16F)))))
        );


    }
}
