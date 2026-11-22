package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.DeepTreasureItem;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

/**
 * 原版流浪商人（Wandering Trader）交易注入。
 * <p>
 * 数值来源：{@code document/design/游商交易注入设计.md}「一、需求」，调整交易只改该表。
 * <p>
 * 用 Forge 的 {@link WandererTradesEvent} 而不是 Mixin：事件在服务器启动阶段
 * （{@code ServerAboutToStartEvent}）触发一次，结束后 Forge 会把 generic / rare 列表写回
 * 静态表 {@code VillagerTrades.WANDERING_TRADER_TRADES}，此后新生成的游商都按该表抽取。
 * <p>
 * 13 条交易全部加入 generic 池，并保留原版条目。原版 {@code WanderingTrader#updateTrades}
 * 每次从 generic 池等权、不重复地随机抽 5 条，因此池内每条被抽中的概率相同，
 * 但单只游商只展示其中 5 条，无法保证某条必定出现。
 */
public class ModWandererTrades {

    /** 原版游商价格倍率；只影响需求波动，取小值避免价格剧烈漂移。 */
    private static final float PRICE_MULTIPLIER = 0.05f;

    public static void onWandererTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> trades = event.getGenericTrades();

        Item coin = ModItems.MELT_DREAM_COIN.get();
        ItemStack emerald = new ItemStack(Items.EMERALD);

        // ===== 低价值兑换（#1~#3，次数 8~32）=====
        // #1 2~4 绿宝石 -> 1 融梦星币
        trades.add(trade(Items.EMERALD, 2, 4, item(ModItems.MELT_DREAM_COIN), 16, 32, 2));
        // #2 5~7 融梦星币 -> 1 绿宝石
        trades.add(trade(coin, 5, 7, emerald.copy(), 16, 32, 2));
        // #3 1~3 融梦星币 -> 1 蜜脾
        trades.add(trade(coin, 1, 3, new ItemStack(Items.HONEYCOMB), 8, 16, 2));

        // ===== 中价值（#4~#8，次数 1~3）=====
        // #4 10~16 绿宝石 -> 1 梦境果汁
        trades.add(trade(Items.EMERALD, 10, 16, item(ModItems.GLASS_JAR_OF_DREAM_JUICE), 1, 3, 5));
        // #5 20~32 绿宝石 -> 1 超级深海秘宝
        trades.add(trade(Items.EMERALD, 20, 32,
                DeepTreasureItem.createSuper(ModItems.DEEP_SEA_TREASURE.get()), 1, 3, 5));
        // #6 10~16 融梦星币 -> 1 染梦香水
        trades.add(trade(coin, 10, 16, item(ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME), 1, 3, 5));
        // #7 10~16 融梦星币 -> 1 鲜红露滴
        trades.add(trade(coin, 10, 16, item(ModItems.RED_DEW), 1, 3, 5));
        // #8 10~16 融梦星币 -> 1 融梦棉花糖
        trades.add(trade(coin, 10, 16, item(ModItems.MELT_DREAM_COTTON_CANDY), 1, 3, 5));

        // ===== 高价值（#9~#12，次数 1~2）=====
        // #9 20~32 融梦星币 -> 1 凤梨爱琴海
        trades.add(trade(coin, 20, 32, item(ModItems.PINEAPPLE_LOVE_SEA), 1, 2, 8));
        // #10 20~32 融梦星币 -> 1 狐狸雕像
        trades.add(trade(coin, 20, 32, item(ModItems.FOX_SCULPTURE), 1, 2, 8));
        // #11 20~32 融梦星币 -> 1 迷梦冶梦莲
        trades.add(trade(coin, 20, 32, item(ModItems.MISTY_DREAMING_LOTUS), 1, 2, 8));
        // #12 20~32 融梦星币 -> 1 融梦涌泉桶
        trades.add(trade(coin, 20, 32, item(ModItems.MELT_DREAM_LIQUID_BUCKET), 1, 2, 8));

        // ===== 稀有（#13，次数 1）=====
        // #13 24~48 融梦星币 -> 1 融梦水晶碎片
        trades.add(trade(coin, 24, 48, item(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT), 1, 1, 12));
    }

    private static RangeItemListing trade(Item priceItem, int priceMin, int priceMax,
                                          ItemStack result, int usesMin, int usesMax, int xp) {
        return new RangeItemListing(new ItemStack(priceItem), result,
                priceMin, priceMax, usesMin, usesMax, xp, PRICE_MULTIPLIER);
    }

    private static ItemStack item(RegistryObject<Item> item) {
        return new ItemStack(item.get());
    }
}
