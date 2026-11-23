package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.world.dimension.DyedreamDimension;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 染梦世界流浪商人的独立交易表。
 * <p>
 * 原版 {@code WandererTradesEvent} 只在服务器启动阶段触发一次，事件里没有实体/维度上下文，
 * 结果被写回全局静态表 {@code VillagerTrades.WANDERING_TRADER_TRADES}，因此无法按维度分表。
 * 这里改为在生成交易时判断维度：染梦世界的游商使用「原版基础池 + 染梦专属池」，
 * 其他维度仍使用全局池（原版 + {@link ModWandererTrades} 注入的 13 条）。
 * <p>
 * 原版基础池在 {@link ModWandererTrades#onWandererTrades} 注入前快照，因此不含本模组注入的 13 条；
 * 染梦专属条目直接加在 {@link #GENERIC} / {@link #RARE} 里即可（数值同步到
 * {@code document/design/游商交易注入设计.md}）。
 * <p>
 * 开关：{@code Config#dyedreamWandererTradesEnabled}（配置项 {@code dyedreamWandererTradesEnabled}，默认 true），
 * 关闭后染梦游商回退到全局交易池。
 */
public final class DyedreamWandererTrades {

    /** 与原版一致：generic 池随机抽 5 条，rare 池随机抽 1 条。 */
    private static final int GENERIC_PICKS = 5;
    private static final int RARE_PICKS = 1;

    /** 染梦专属交易条目（generic 池，与原版一样每只游商最多抽 5 条）。 */
    public static final List<VillagerTrades.ItemListing> GENERIC = new ArrayList<>();

    /** 染梦专属交易条目（rare 池，与原版一样每只游商最多抽 1 条）。 */
    public static final List<VillagerTrades.ItemListing> RARE = new ArrayList<>();

    private static VillagerTrades.ItemListing[] vanillaGeneric = new VillagerTrades.ItemListing[0];
    private static VillagerTrades.ItemListing[] vanillaRare = new VillagerTrades.ItemListing[0];
    private static boolean vanillaCaptured;

    private DyedreamWandererTrades() {
    }

    /**
     * 在 {@link ModWandererTrades} 向全局池注入之前快照原版条目。
     * 由 {@code WandererTradesEvent} 监听器调用，只在第一次生效。
     */
    public static void captureVanillaPools() {
        if (vanillaCaptured) {
            return;
        }
        VillagerTrades.ItemListing[] generic = VillagerTrades.WANDERING_TRADER_TRADES.get(1);
        VillagerTrades.ItemListing[] rare = VillagerTrades.WANDERING_TRADER_TRADES.get(2);
        if (generic == null || rare == null) {
            return;
        }
        vanillaGeneric = generic.clone();
        vanillaRare = rare.clone();
        vanillaCaptured = true;
    }

    /** 该游商是否应使用染梦独立表。 */
    public static boolean applies(WanderingTrader trader) {
        return vanillaCaptured
                && Config.dyedreamWandererTradesEnabled
                && trader.level().dimension().equals(DyedreamDimension.DYEDREAM_WORLD);
    }

    /**
     * 按原版规则从「原版基础池 + 染梦专属池」抽取交易并填入 {@code trader} 的 offers。
     * 调用时 {@code trader} 的 offers 必须已初始化（{@code updateTrades} 只会在
     * {@code AbstractVillager#getOffers} 初始化 offers 之后被调用）。
     */
    public static void fillOffers(WanderingTrader trader) {
        MerchantOffers offers = trader.getOffers();
        RandomSource random = trader.getRandom();
        addOffers(offers, trader, merged(vanillaGeneric, GENERIC), GENERIC_PICKS, random);
        addOffers(offers, trader, merged(vanillaRare, RARE), RARE_PICKS, random);
    }

    private static VillagerTrades.ItemListing[] merged(VillagerTrades.ItemListing[] vanilla,
                                                       List<VillagerTrades.ItemListing> extra) {
        if (extra.isEmpty()) {
            return vanilla;
        }
        VillagerTrades.ItemListing[] result = new VillagerTrades.ItemListing[vanilla.length + extra.size()];
        System.arraycopy(vanilla, 0, result, 0, vanilla.length);
        for (int i = 0; i < extra.size(); i++) {
            result[vanilla.length + i] = extra.get(i);
        }
        return result;
    }

    private static void addOffers(MerchantOffers offers, WanderingTrader trader,
                                  VillagerTrades.ItemListing[] listings, int count, RandomSource random) {
        if (listings.length == 0) {
            return;
        }
        Set<Integer> indices = new LinkedHashSet<>();
        if (listings.length > count) {
            while (indices.size() < count) {
                indices.add(random.nextInt(listings.length));
            }
        } else {
            for (int i = 0; i < listings.length; i++) {
                indices.add(i);
            }
        }
        for (int index : indices) {
            MerchantOffer offer = listings[index].getOffer(trader, random);
            if (offer != null) {
                offers.add(offer);
            }
        }
    }
}
