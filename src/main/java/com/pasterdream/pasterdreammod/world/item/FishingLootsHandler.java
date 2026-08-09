package com.pasterdream.pasterdreammod.world.item;

import com.mojang.logging.LogUtils;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class FishingLootsHandler {

    private static final org.slf4j.Logger LOGGER = LogUtils.getLogger();

    public static final ResourceLocation DEEP_SEA_TREASURE =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "gameplay/fishing/deep_sea_treasure");
    public static final ResourceLocation DYEDREAM_DEEP_SEA_TREASURE =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "gameplay/fishing/dyedream_deep_sea_treasure");
    public static final ResourceLocation SHADOW_DEEP_SEA_TREASURE =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "gameplay/fishing/shadow_deep_sea_treasure");
    public static final ResourceLocation SHADOW_JUNK =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "gameplay/fishing/shadow_junk");

    private static final ResourceKey<Level> DYEDREAM_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world"));
    private static final ResourceKey<Level> LAMP_SHADOW_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));
    private static final ResourceKey<Biome> SHADOW_OCEAN =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ocean"));

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (!event.getName().equals(BuiltInLootTables.FISHING)) return;
        LootPool originalMainPool = event.getTable().getPool("main");
        if (originalMainPool == null) return;

        // 提取原版 main 池的条目，保留其他模组（如 Aquaculture）注入的条目
        LootPoolEntryContainer[] originalEntries = getPoolEntries(originalMainPool);
        if (originalEntries.length == 0) return;

        event.setTable(LootTable.lootTable()
                // 主世界池 — 保留原版/其他模组注入的条目，追加深海秘宝
                .withPool(createPoolWithExtraEntry(originalEntries,
                        LocationCheck.checkLocation(
                                LocationPredicate.Builder.location().setDimension(Level.OVERWORLD)),
                        LootTableReference.lootTableReference(DEEP_SEA_TREASURE)
                                .setWeight(10).setQuality(2)))
                // 染梦维度池 — 保留原版条目，追加染梦深海秘宝
                .withPool(createPoolWithExtraEntry(originalEntries,
                        LocationCheck.checkLocation(
                                LocationPredicate.Builder.location().setDimension(DYEDREAM_WORLD)),
                        LootTableReference.lootTableReference(DYEDREAM_DEEP_SEA_TREASURE)
                                .setWeight(10).setQuality(2)))
                // 浸影维度·垃圾池 — 灯影维度所有群系
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(LocationCheck.checkLocation(
                                LocationPredicate.Builder.location().setDimension(LAMP_SHADOW_WORLD)))
                        .add(LootTableReference.lootTableReference(SHADOW_JUNK)
                                .setWeight(1)))
                // 浸影维度·秘宝池 — 仅在阴影之海触发
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(LocationCheck.checkLocation(
                                LocationPredicate.Builder.location()
                                        .setDimension(LAMP_SHADOW_WORLD)
                                        .setBiome(SHADOW_OCEAN)))
                        .add(EmptyLootItem.emptyItem()
                                .setWeight(90))
                        .add(LootTableReference.lootTableReference(SHADOW_DEEP_SEA_TREASURE)
                                .setWeight(10).setQuality(2)))
                // 兜底池 — 其他维度保持原版行为（含其他模组注入的条目）
                .withPool(createPoolWithExtraEntry(originalEntries,
                        InvertedLootItemCondition.invert(
                                AnyOfCondition.anyOf(
                                        LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location().setDimension(Level.OVERWORLD)),
                                        LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location().setDimension(DYEDREAM_WORLD)),
                                        LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location().setDimension(LAMP_SHADOW_WORLD)))),
                        null))
                .build());
        LOGGER.info("[FishingLoots] Replaced fishing loot table with dimension-aware pools");
    }

    /**
     * 通过反射提取 LootPool 的条目数组，保留其他模组注入的条目。
     */
    private static LootPoolEntryContainer[] getPoolEntries(LootPool pool) {
        try {
            Field field = ObfuscationReflectionHelper.findField(LootPool.class, "f_79023_");
            field.setAccessible(true);
            LootPoolEntryContainer[] entries = (LootPoolEntryContainer[]) field.get(pool);
            return entries.clone();
        } catch (Exception e) {
            LOGGER.error("[FishingLoots] Failed to extract pool entries", e);
            return new LootPoolEntryContainer[0];
        }
    }

    /**
     * 创建一个保留原版条目并可选追加额外条目的战利品池 Builder。
     * 通过反射直接修改 Builder 内部的 entries 列表，绕过不可变 API 限制。
     *
     * @param baseEntries 从原版 main 池提取的条目（含其他模组注入）
     * @param condition   维度/群系条件
     * @param extraEntry  额外追加的条目，为 null 则不追加
     * @return LootPool.Builder 可直接传给 {@link LootTable.Builder#withPool}
     */
    private static LootPool.Builder createPoolWithExtraEntry(
            LootPoolEntryContainer[] baseEntries,
            LootItemCondition.Builder condition,
            LootPoolEntryContainer.Builder<?> extraEntry) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(condition)
                .add(EmptyLootItem.emptyItem().setWeight(0)); // 占位条目，随后清除

        try {
            Field field = ObfuscationReflectionHelper.findField(LootPool.Builder.class, "f_79067_");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<LootPoolEntryContainer> entries = (List<LootPoolEntryContainer>) field.get(builder);
            entries.clear();
            entries.addAll(Arrays.asList(baseEntries));
            if (extraEntry != null) {
                entries.add(extraEntry.build());
            }
        } catch (Exception e) {
            LOGGER.error("[FishingLoots] Failed to inject pool entries into builder", e);
        }

        return builder;
    }
}
