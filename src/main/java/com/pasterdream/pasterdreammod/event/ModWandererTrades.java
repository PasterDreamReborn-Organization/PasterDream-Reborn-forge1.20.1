package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.DeepTreasureItem;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 原版流浪商人（Wandering Trader）的本模组交易。
 * <p>
 * 数值来源：{@code document/design/游商交易注入设计.md}「一、需求」，调整交易只改该表。
 * <p>
 * 两种投放方式由配置项决定（{@link Config#wanderingTraderTradesInjectToVanillaPool}）：
 * <ul>
 *   <li><b>注入原表</b>（{@code true}）：监听 {@link WandererTradesEvent}，把 13 条加入 generic 池。
 *       事件在服务器启动阶段（{@code ServerAboutToStartEvent}）触发一次，结束后 Forge 把列表写回
 *       静态表 {@code VillagerTrades.WANDERING_TRADER_TRADES}。原版每只游商从 generic 池等权、
 *       不重复地随机抽 5 条，因此单条不保证出现。</li>
 *   <li><b>末尾追加</b>（{@code false}，默认）：不改原版池，由 {@link WanderingTraderTradesMixin}
 *       在原版 {@code updateTrades} 结束后，从 {@link #TRADES} 中随机抽
 *       {@link Config#wanderingTraderTradesAppendCount} 条不重复交易追加到 offers 末尾，保证每只必出。</li>
 * </ul>
 * 开关：{@code Config#wanderingTraderTradesEnabled}（配置项 {@code wanderingTraderTradesEnabled}，默认 true），
 * 关闭时两种模式都不投放，原版交易不受影响。
 * <p>
 * 染梦世界生成的游商不走这里：{@link DyedreamWandererTrades} 会改用「原版基础池 + 染梦表自己的条目」
 * 的独立表，并按相同的投放方式配置处理自己的 17 条。两张表互不引用：染梦表自己持有一份同款 13 条，
 * 改这里不影响它，反之亦然。
 */
public class ModWandererTrades {

    /** 原版游商价格倍率；只影响需求波动，取小值避免价格剧烈漂移。 */
    private static final float PRICE_MULTIPLIER = 0.05f;

    /** 本模组提供给游商的 13 条交易：注入模式进原版池，追加模式从末尾补入。 */
    public static final List<VillagerTrades.ItemListing> TRADES = new ArrayList<>();

    private static boolean tradesRegistered;

    /**
     * 本次服务器启动是否真的把条目写进了原版池。
     * <p>
     * 该字段在 {@link #onWandererTrades}（服务器启动阶段）确定后不再变化，作为两种模式的唯一判据：
     * 运行中通过配置重载改 {@code wanderingTraderTradesInjectToVanillaPool} 不会改变已构建的静态池，
     * 若各处直接读实时配置值，会出现「池里已有注入条目却又走追加分支」或反向的不一致。
     */
    private static boolean injectedToVanillaPool;

    public static void onWandererTrades(WandererTradesEvent event) {
        // 必须在注入前快照原版基础池：染梦独立表以它为底
        DyedreamWandererTrades.captureVanillaPools();
        // 染梦表拥有自己独立的一份条目（与这里互不引用），双方各自维护
        DyedreamWandererTrades.registerTrades();
        registerTrades();
        injectedToVanillaPool = Config.wanderingTraderTradesEnabled
                && Config.wanderingTraderTradesInjectToVanillaPool;
        if (injectedToVanillaPool) {
            event.getGenericTrades().addAll(TRADES);
        }
    }

    /** 本次服务器启动是否已把 13 条注入原版池；追加模式据此判断是否还要补条目。 */
    public static boolean isInjectedToVanillaPool() {
        return injectedToVanillaPool;
    }

    /**
     * 构造本模组的 13 条交易。方法幂等，只在第一次调用时生效。
     * <p>
     * 由 {@link #onWandererTrades} 在 {@code WandererTradesEvent}（注册表已就绪）中调用；
     * 追加模式也依赖该列表，因此无论开关与模式如何都会构造。
     */
    public static void registerTrades() {
        if (tradesRegistered) {
            return;
        }
        tradesRegistered = true;

        Item coin = ModItems.MELT_DREAM_COIN.get();
        ItemStack emerald = new ItemStack(Items.EMERALD);

        // ===== 低价值兑换（#1~#3，次数 8~32）=====
        // #1 2~4 绿宝石 -> 1 融梦星币
        TRADES.add(trade(Items.EMERALD, 2, 4, item(ModItems.MELT_DREAM_COIN), 16, 32, 2));
        // #2 5~7 融梦星币 -> 1 绿宝石
        TRADES.add(trade(coin, 5, 7, emerald.copy(), 16, 32, 2));
        // #3 1~3 融梦星币 -> 1 蜜脾
        TRADES.add(trade(coin, 1, 3, new ItemStack(Items.HONEYCOMB), 8, 16, 2));

        // ===== 中价值（#4~#8，次数 1~3）=====
        // #4 10~16 绿宝石 -> 1 梦境果汁
        TRADES.add(trade(Items.EMERALD, 10, 16, item(ModItems.GLASS_JAR_OF_DREAM_JUICE), 1, 3, 5));
        // #5 20~32 绿宝石 -> 1 超级深海秘宝
        TRADES.add(trade(Items.EMERALD, 20, 32,
                DeepTreasureItem.createSuper(ModItems.DEEP_SEA_TREASURE.get()), 1, 3, 5));
        // #6 10~16 融梦星币 -> 1 染梦香水
        TRADES.add(trade(coin, 10, 16, item(ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME), 1, 3, 5));
        // #7 10~16 融梦星币 -> 1 鲜红露滴
        TRADES.add(trade(coin, 10, 16, item(ModItems.RED_DEW), 1, 3, 5));
        // #8 10~16 融梦星币 -> 1 融梦棉花糖
        TRADES.add(trade(coin, 10, 16, item(ModItems.MELT_DREAM_COTTON_CANDY), 1, 3, 5));

        // ===== 高价值（#9~#12，次数 1~2）=====
        // #9 20~32 融梦星币 -> 1 凤梨爱琴海
        TRADES.add(trade(coin, 20, 32, item(ModItems.PINEAPPLE_LOVE_SEA), 1, 2, 8));
        // #10 20~32 融梦星币 -> 1 狐狸雕像
        TRADES.add(trade(coin, 20, 32, item(ModItems.FOX_SCULPTURE), 1, 2, 8));
        // #11 20~32 融梦星币 -> 1 迷梦冶梦莲
        TRADES.add(trade(coin, 20, 32, item(ModItems.MISTY_DREAMING_LOTUS), 1, 2, 8));
        // #12 20~32 融梦星币 -> 1 融梦涌泉桶
        TRADES.add(trade(coin, 20, 32, item(ModItems.MELT_DREAM_LIQUID_BUCKET), 1, 2, 8));

        // ===== 稀有（#13，次数 1）=====
        // #13 24~48 融梦星币 -> 1 融梦水晶碎片
        TRADES.add(trade(coin, 24, 48, item(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT), 1, 1, 12));
    }

    /**
     * 从 {@code candidates} 中随机抽取不重复的 {@code count} 条，生成 offer 并追加到 {@code offers} 末尾。
     * <p>
     * 追加模式的全局池与染梦独立表共用；超过可选条目数时按可选条目数封顶。
     */
    public static void appendRandomOffers(MerchantOffers offers, WanderingTrader trader,
                                          List<VillagerTrades.ItemListing> candidates, int count) {
        if (count <= 0 || candidates.isEmpty()) {
            return;
        }
        int amount = Math.min(count, candidates.size());
        RandomSource random = trader.getRandom();
        Set<Integer> indices = new LinkedHashSet<>();
        while (indices.size() < amount) {
            indices.add(random.nextInt(candidates.size()));
        }
        for (int index : indices) {
            MerchantOffer offer = candidates.get(index).getOffer(trader, random);
            if (offer != null) {
                offers.add(offer);
            }
        }
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
