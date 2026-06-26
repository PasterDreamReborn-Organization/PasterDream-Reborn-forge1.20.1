package com.pasterdream.pasterdreammod.datagen.util;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

/**
 * 战利品生成工具方法，供 data generator 调用。
 */
public final class LootHelpers{
    private LootHelpers() {}
    // === 树叶战利品相关  ===

    /** 普通树叶掉落树苗的概率 — 与原版 NORMAL_LEAVES_SAPLING_CHANCES 一致 */
    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = {0.05F, 0.0625F, 0.083333336F, 0.1F};
    /** 普通树叶掉落木棍的概率 — 与原版私有字段一致 */
    private static final float[] NORMAL_LEAVES_STICK_CHANCES = {0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};
    /** 果实掉落概率 **/
    // MC原版概率为{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F}
    private static final float[] LEAVES_FRUIT_CHANCES = {0.05F, 0.0625F, 0.083333336F, 0.1F};
    /** 剪刀或精准采集条件 — 用公开 API 构建，避免引用原版 private 字段 */
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = MatchTool.toolMatches(
                    ItemPredicate.Builder.item().of(Items.SHEARS))
            .or(MatchTool.toolMatches(
                    ItemPredicate.Builder.item().hasEnchantment(
                            new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))));

    /**
     * 构建普通树叶（无额外掉落）战利品表：精准采集/剪刀 → 树叶自身；否则 → 树苗(时运) + 木棍。
     */
    public static LootTable.Builder createLeavesDrops(Block leaves, Block sapling) {
        var saplingEntry = LootItem.lootTableItem(sapling)
                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, NORMAL_LEAVES_SAPLING_CHANCES))
                .when(ExplosionCondition.survivesExplosion());

        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(leaves)
                                .when(HAS_SHEARS_OR_SILK_TOUCH)
                                .otherwise(saplingEntry)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(HAS_SHEARS_OR_SILK_TOUCH.invert())
                        .add(LootItem.lootTableItem(Items.STICK)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyExplosionDecay.explosionDecay())
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, NORMAL_LEAVES_STICK_CHANCES))));
    }
    /**
     * 构建草战利品表：精准采集/剪刀 → 草自身
     */
    public static LootTable.Builder creategrassesDrops(Block grasses) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(grasses)
                                .when(HAS_SHEARS_OR_SILK_TOUCH).otherwise(EmptyLootItem.emptyItem())));
    }
    /**
     * 构建高草战利品表：精准采集/剪刀 → x2草
     */
    public static LootTable.Builder createhighgrassesDrops(Block grasses,Block highgrasses) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(highgrasses)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                                ))
                        .when(HAS_SHEARS_OR_SILK_TOUCH)
                        .add(LootItem.lootTableItem(grasses)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))
        );
    }
    /**
     * 构建高花战利品表：直接掉落花
     */
    public static LootTable.Builder createhighflowerDrops(Block highflower) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(highflower)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                                ))
                        .add(LootItem.lootTableItem(highflower)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
        );
    }


    /**
     * 构建带果实掉落的树叶战利品表：精准采集/剪刀 → 树叶自身；否则 → 树苗(时运) + 木棍 + 果实。
     */
    public static LootTable.Builder createLeavesDrops(Block leaves, Block sapling, ItemLike fruit) {
        return createLeavesDrops(leaves, sapling)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(HAS_SHEARS_OR_SILK_TOUCH.invert())
                        .add(LootItem.lootTableItem(fruit)
                                .apply(ApplyExplosionDecay.explosionDecay())
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, LEAVES_FRUIT_CHANCES))));
    }

}
