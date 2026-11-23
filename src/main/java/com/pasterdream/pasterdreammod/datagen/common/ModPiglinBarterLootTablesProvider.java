package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.helper.PiglinBarterHelper;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import com.pasterdream.pasterdreammod.world.item.PotionBottleRegistry;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

/**
 * 猪灵交易战利品表（上下文 {@code PIGLIN_BARTER}）：每种自定义货币一张表。
 *
 * <p>表 id 约定见 {@link PiglinBarterHelper#tableId(net.minecraft.world.item.Item)}：
 * {@code pasterdream:gameplay/piglin_bartering/<物品命名空间>/<物品路径>}。
 * 金锭不在此生成（沿用原版 {@code minecraft:gameplay/piglin_bartering}）。</p>
 */
public class ModPiglinBarterLootTablesProvider implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(PiglinBarterHelper.tableId(ModItems.MOLTEN_GOLD_INGOT.get()), moltenGoldIngotTable());
    }

    /**
     * 炙焰金锭：下界深层矿石熔炼所得，产出一批下界物资，
     * 低权重给到下界合金相关与下界之星。
     */
    private static LootTable.Builder moltenGoldIngotTable() {
        CompoundTag infernoTag = new CompoundTag();
        infernoTag.putString(PotionBottleItem.TAG_POTION_TYPE, PotionBottleItem.TYPE_INFERNO);

        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(entry(Items.BONE, 30, 12, 16))
                .add(entry(Items.GLOWSTONE, 30, 8, 12))
                .add(entry(Items.MAGMA_CREAM, 30, 8, 12))
                .add(entry(Items.FIRE_CHARGE, 30, 8, 12))
                .add(entry(Items.OBSIDIAN, 30, 8, 12))
                .add(entry(ModItems.SOUL_DUST.get(), 30, 8, 12))
                .add(entry(Items.ENDER_PEARL, 30, 8, 12))
                .add(entry(Items.LANTERN, 30, 8, 12))
                .add(entry(Items.CAMPFIRE, 30, 8, 12))
                .add(entry(Items.QUARTZ, 30, 8, 12))
                .add(entry(Items.GHAST_TEAR, 20, 8, 12))
                .add(entry(Items.EXPERIENCE_BOTTLE, 20, 8, 12))
                .add(entry(Items.BLAZE_ROD, 20, 8, 12))
                .add(entry(Items.CRYING_OBSIDIAN, 20, 8, 12))
                .add(entry(Items.NETHER_WART, 20, 8, 12))
                .add(entry(Items.ENDER_EYE, 20, 4, 8))
                .add(entry(Items.ANVIL, 20, 1, 1))
                .add(entry(Items.LODESTONE, 15, 1, 1))
                .add(entryWithTag(PotionBottleRegistry.POTION_BOTTLE.get(), 15, 1, 2, infernoTag))
                .add(entry(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 10, 1, 1))
                .add(entry(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, 10, 1, 1))
                .add(entry(Items.NETHERITE_SCRAP, 5, 1, 2))
                .add(entry(Items.ANCIENT_DEBRIS, 5, 1, 2))
                .add(entry(Items.NETHER_STAR, 1, 1, 1)));
    }

    /**
     * 构建一个带权重与数量区间的掉落条目。
     */
    private static LootPoolEntryContainer.Builder<?> entry(ItemLike item, int weight, int min, int max) {
        LootPoolSingletonContainer.Builder<?> builder = LootItem.lootTableItem(item);
        builder = builder.apply(SetItemCountFunction.setCount(count(min, max)));
        builder = builder.setWeight(weight);
        return builder;
    }

    /**
     * 构建一个带权重、数量区间与 NBT 的掉落条目（用于药剂瓶这类靠 NBT 区分变体的物品）。
     */
    private static LootPoolEntryContainer.Builder<?> entryWithTag(ItemLike item, int weight, int min, int max,
                                                                 CompoundTag tag) {
        LootPoolSingletonContainer.Builder<?> builder = LootItem.lootTableItem(item);
        builder = builder.apply(SetItemCountFunction.setCount(count(min, max)));
        builder = builder.apply(SetNbtFunction.setTag(tag));
        builder = builder.setWeight(weight);
        return builder;
    }

    /**
     * 数量区间：min == max 时用固定值，否则用均匀分布。
     */
    private static NumberProvider count(int min, int max) {
        return min == max ? ConstantValue.exactly(min) : UniformGenerator.between(min, max);
    }
}
