package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;

/**
 * 袭击胜利战利品注入 —— 数据驱动方式
 * <p>
 * 原版 GiveGiftToHero 已为每个职业配置了专属战利品表，
 * 牧师对应 {@code minecraft:gameplay/hero_of_the_village/cleric_gift}。
 * 此处在原版战利品表加载时追加自定义物品，不替换原版内容。
 */
public class ModRaidRewardsProvider {

    public static void onLootTableLoad(LootTableLoadEvent event) {
        // 只注入牧师（cleric）袭击奖励战利品表
        if (!event.getName().equals(BuiltInLootTables.CLERIC_GIFT)) {
            return;
        }

        // 追加自定义物品池 —— 与原版牧师奖励共存
        event.getTable().addPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                // ★ 在此添加/修改你想要牧师额外抛出的物品
                .add(LootItem.lootTableItem(ModItems.FORTUNE_JELLY.get())
                        .apply(SetItemCountFunction.setCount(
                                UniformGenerator.between(1.0F, 3.0F))))
                .build()
        );
    }
}
