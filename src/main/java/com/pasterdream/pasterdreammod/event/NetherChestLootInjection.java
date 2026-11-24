package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 原版下界宝箱战利品注入 —— 追加炙焰金系物品。
 * <p>
 * 通过 {@link LootTableLoadEvent} 在原版表加载完成后追加一个独立池（1 次抽取 + 高权重空条目），
 * 不覆盖原版表内容：原版条目与其他模组的注入都保持原样。
 * <p>
 * 覆盖 5 张下界箱子表：下界要塞（{@code chests/nether_bridge}）与堡垒遗迹的 4 张表。
 * 废弃传送门（{@code chests/ruined_portal}）同时在主世界生成，为避免主世界也能开出炙焰金，暂不注入。
 * <p>
 * 每张表的空条目权重与各物品权重、数量见 {@link #createSpecs()}；调整投放概率只改该表。
 * 每个池总权重固定为 100，空条目权重 = 100 - 物品权重之和，因此物品总概率 = 各物品权重之和 / 100。
 */
public final class NetherChestLootInjection {

    /**
     * 各表的注入配置。
     * <p>
     * 延迟到首次事件调用时构建：{@link LootTableLoadEvent} 在注册表就绪后触发，
     * 此时 {@code RegistryObject#get()} 才可靠，避免类初始化早于物品注册。
     */
    private static Map<ResourceLocation, PoolSpec> specs;

    private NetherChestLootInjection() {
    }

    public static void onLootTableLoad(LootTableLoadEvent event) {
        PoolSpec spec = specs().get(event.getName());
        if (spec == null) {
            return;
        }

        LootPool.Builder pool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(EmptyLootItem.emptyItem().setWeight(spec.emptyWeight()));
        for (Entry entry : spec.entries()) {
            pool.add(LootItem.lootTableItem(entry.item())
                    .setWeight(entry.weight())
                    .apply(SetItemCountFunction.setCount(
                            UniformGenerator.between(entry.min(), entry.max()))));
        }
        event.getTable().addPool(pool.build());
    }

    private static Map<ResourceLocation, PoolSpec> specs() {
        if (specs == null) {
            specs = createSpecs();
        }
        return specs;
    }

    /**
     * 各表的权重配置。
     * <p>
     * 堡垒宝藏是高价值箱：物品总概率更高、附魔金苹果更常见；
     * 其余表以炙焰金粒为主，炙焰金锭次之，炙焰金苹果与附魔炙焰金苹果为低频奖励。
     */
    private static Map<ResourceLocation, PoolSpec> createSpecs() {
        Map<ResourceLocation, PoolSpec> map = new HashMap<>();

        // 下界要塞（2~4 抽）：约 30% 开出物品，以金粒 2~6 个为主
        map.put(BuiltInLootTables.NETHER_BRIDGE, new PoolSpec(70, List.of(
                new Entry(ModItems.MOLTEN_GOLD_NUGGET.get(), 12, 2, 6),
                new Entry(ModItems.MOLTEN_GOLD_INGOT.get(), 10, 1, 2),
                new Entry(ModItems.MOLTEN_GOLD_APPLE.get(), 6, 1, 1),
                new Entry(ModItems.ENCHANTED_MOLTEN_GOLD_APPLE.get(), 2, 1, 1))));

        // 堡垒遗迹·宝藏（3 抽的高价值箱）：约 50% 开出物品，附魔金苹果 7%
        map.put(BuiltInLootTables.BASTION_TREASURE, new PoolSpec(50, List.of(
                new Entry(ModItems.MOLTEN_GOLD_NUGGET.get(), 15, 4, 8),
                new Entry(ModItems.MOLTEN_GOLD_INGOT.get(), 15, 2, 4),
                new Entry(ModItems.MOLTEN_GOLD_APPLE.get(), 13, 1, 1),
                new Entry(ModItems.ENCHANTED_MOLTEN_GOLD_APPLE.get(), 7, 1, 1))));

        // 堡垒遗迹·其他（生活区，1 抽）：约 22% 开出物品
        map.put(BuiltInLootTables.BASTION_OTHER, new PoolSpec(78, List.of(
                new Entry(ModItems.MOLTEN_GOLD_NUGGET.get(), 12, 1, 3),
                new Entry(ModItems.MOLTEN_GOLD_INGOT.get(), 5, 1, 2),
                new Entry(ModItems.MOLTEN_GOLD_APPLE.get(), 4, 1, 1),
                new Entry(ModItems.ENCHANTED_MOLTEN_GOLD_APPLE.get(), 1, 1, 1))));

        // 堡垒遗迹·桥（1 抽）：约 22% 开出物品
        map.put(BuiltInLootTables.BASTION_BRIDGE, new PoolSpec(78, List.of(
                new Entry(ModItems.MOLTEN_GOLD_NUGGET.get(), 12, 1, 3),
                new Entry(ModItems.MOLTEN_GOLD_INGOT.get(), 5, 1, 2),
                new Entry(ModItems.MOLTEN_GOLD_APPLE.get(), 4, 1, 1),
                new Entry(ModItems.ENCHANTED_MOLTEN_GOLD_APPLE.get(), 1, 1, 1))));

        // 堡垒遗迹·疣猪兽棚（1 抽）：约 24% 开出物品
        map.put(BuiltInLootTables.BASTION_HOGLIN_STABLE, new PoolSpec(76, List.of(
                new Entry(ModItems.MOLTEN_GOLD_NUGGET.get(), 12, 1, 4),
                new Entry(ModItems.MOLTEN_GOLD_INGOT.get(), 6, 1, 2),
                new Entry(ModItems.MOLTEN_GOLD_APPLE.get(), 5, 1, 1),
                new Entry(ModItems.ENCHANTED_MOLTEN_GOLD_APPLE.get(), 1, 1, 1))));

        return Map.copyOf(map);
    }

    /** 单个池的物品条目：物品、权重、数量区间（含两端）。 */
    private record Entry(Item item, int weight, int min, int max) {
    }

    /** 单张表的池配置：空条目权重 + 物品条目。 */
    private record PoolSpec(int emptyWeight, List<Entry> entries) {
    }
}
