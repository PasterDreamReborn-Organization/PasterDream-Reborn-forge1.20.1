package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

/**
 * 钓鱼深海秘宝战利品表 datagen provider。
 * <p>
 * 每个 biome 两个 pool：
 * <ol>
 *   <li>基础池 —— normal(weight=30,q=0) + super(weight=1,q=1)，super 比例随 luck 增长</li>
 *   <li>星者祈愿池 —— 仅手持星者祈愿时生效，必出 super 变体<br>
 *       用物品 tag {@code pasterdream:star_fishing_rods} 匹配，该 tag 应在
 *       {@code ModItemTagsProvider} 中定义</li>
 * </ol>
 */
public class ModFishingLootTablesProvider implements LootTableSubProvider {

    private static final ResourceKey<Level> DYEDREAM_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world"));

    private static final TagKey<Item> STAR_FISHING_RODS =
            TagKey.create(Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "star_fishing_rods"));

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        // 深海秘宝 —— 主世界全部 9 个 ocean 变体群系
        LootTable.Builder deepSea = LootTable.lootTable();
        for (String biome : new String[]{"ocean", "deep_ocean", "warm_ocean", "lukewarm_ocean", "cold_ocean",
                "deep_lukewarm_ocean", "deep_cold_ocean", "frozen_ocean", "deep_frozen_ocean"}) {
            ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath("minecraft", biome));
            deepSea.withPool(basePool(ModItems.DEEP_SEA_TREASURE.get(), Level.OVERWORLD, biomeKey));
            deepSea.withPool(starRodPool(ModItems.DEEP_SEA_TREASURE.get(), Level.OVERWORLD, biomeKey));
        }
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "gameplay/fishing/deep_sea_treasure"),
                deepSea
        );

        // 染梦深海秘宝 —— 染梦维度 2 个海洋群系
        LootTable.Builder dyedreamDeepSea = LootTable.lootTable();
        for (String biome : new String[]{"dyedream_frozen_ocean", "dyedream_ocean"}) {
            ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, biome));
            dyedreamDeepSea.withPool(basePool(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get(), DYEDREAM_WORLD, biomeKey));
            dyedreamDeepSea.withPool(starRodPool(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get(), DYEDREAM_WORLD, biomeKey));
        }
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "gameplay/fishing/dyedream_deep_sea_treasure"),
                dyedreamDeepSea
        );

        // 浸影深海秘宝 —— 浸影维度 1 个海洋群系
        ResourceKey<Level> SHADOW_WORLD =
                ResourceKey.create(Registries.DIMENSION,
                        ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));
        LootTable.Builder shadowDeepSea = LootTable.lootTable();
        ResourceKey<Biome> shadowOceanKey = ResourceKey.create(Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ocean"));
        shadowDeepSea.withPool(basePool(ModItems.SHADOW_DEEP_SEA_TREASURE.get(), SHADOW_WORLD, shadowOceanKey));
        shadowDeepSea.withPool(starRodPool(ModItems.SHADOW_DEEP_SEA_TREASURE.get(), SHADOW_WORLD, shadowOceanKey));
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "gameplay/fishing/shadow_deep_sea_treasure"),
                shadowDeepSea
        );

        // 灯影之下·钓鱼垃圾池
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "gameplay/fishing/shadow_junk"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(4)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.FLINT).setWeight(4)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.BONE).setWeight(4)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RUST_BLACK_METAL_BARS.get()).setWeight(4)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_SPROUTS.get()).setWeight(4)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_FUNGUS.get()).setWeight(4)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BLACK_STICK.get()).setWeight(4)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.ARROW).setWeight(4)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.STICK).setWeight(4)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.STRING).setWeight(4)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_HELMET).setWeight(2)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 0.95F))))
                                .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(2)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 0.95F))))
                                .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(2)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 0.95F))))
                                .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(2)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 0.95F))))
                                .add(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(2)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BROKEN_NOTE.get()).setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BLACK_METAL_GRAIN.get()).setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BLACK_METAL_INGOT.get()).setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        )
        );
    }

    /** 基础池：normal(30,0) + super(1,1)，super 比例随 luck 增长 */
    private static LootPool.Builder basePool(net.minecraft.world.item.Item item, ResourceKey<Level> dimension, ResourceKey<Biome> biome) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(LocationCheck.checkLocation(
                        LocationPredicate.Builder.location()
                                .setDimension(dimension)
                                .setBiome(biome)
                ))
                .add(LootItem.lootTableItem(item).setWeight(30).setQuality(0))
                .add(LootItem.lootTableItem(item).setWeight(1).setQuality(1)
                        .apply(SetNbtFunction.setTag(superTag())));
    }

    /** 占星者祈愿池：仅手持 tag 内钓竿时生效，必出 super 变体 */
    private static LootPool.Builder starRodPool(net.minecraft.world.item.Item item, ResourceKey<Level> dimension, ResourceKey<Biome> biome) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(LocationCheck.checkLocation(
                        LocationPredicate.Builder.location()
                                .setDimension(dimension)
                                .setBiome(biome)
                ))
                .when(MatchTool.toolMatches(
                        ItemPredicate.Builder.item().of(STAR_FISHING_RODS)
                ))
                .add(LootItem.lootTableItem(item).setWeight(1).setQuality(0)
                        .apply(SetNbtFunction.setTag(superTag())));
    }

    private static CompoundTag superTag() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("deep_treasure_super", true);
        return tag;
    }
}
