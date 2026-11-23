package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.dimension.DyedreamDimension;
import com.pasterdream.pasterdreammod.world.item.DeepTreasureItem;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 染梦世界流浪商人的独立交易表。
 * <p>
 * 原版 {@code WandererTradesEvent} 只在服务器启动阶段触发一次，事件里没有实体/维度上下文，
 * 结果被写回全局静态表 {@code VillagerTrades.WANDERING_TRADER_TRADES}，因此无法按维度分表。
 * 这里改为在生成交易时判断维度：染梦世界的游商使用「原版基础池 + 染梦表自己的条目」，
 * 其他维度仍使用全局池（原版 + {@link ModWandererTrades} 注入的 13 条）。
 * <p>
 * 两张注入表<b>相互独立</b>：本表在 {@link #registerTrades()} 里自行登记同款 13 条与染梦专属条目，
 * 不引用 {@link ModWandererTrades} 的任何条目；改主世界的 13 条不会影响染梦，反之亦然。
 * 原版基础池在 {@link ModWandererTrades#onWandererTrades} 注入前快照，因此快照本身不含 13 条。
 * 数值同步到 {@code document/design/游商交易注入设计.md}。
 * <p>
 * 开关：{@code Config#dyedreamWandererTradesEnabled}（配置项 {@code dyedreamWandererTradesEnabled}，默认 true），
 * 关闭后染梦游商回退到全局交易池；{@code wanderingTraderTradesEnabled} 只作用于主世界，与染梦表无关。
 * 投放方式与主世界共用 {@link Config#wanderingTraderTradesInjectToVanillaPool}：注入模式把 17 条并入
 * generic / rare 池随机抽取，追加模式保持原版池不变并在末尾固定追加
 * {@link Config#wanderingTraderTradesAppendCount} 条本表条目。
 */
public final class DyedreamWandererTrades {

    /** 与原版一致：generic 池随机抽 5 条，rare 池随机抽 1 条。 */
    private static final int GENERIC_PICKS = 5;
    private static final int RARE_PICKS = 1;

    /** 染梦游商专属交易的价格倍率；与全局注入一致，只影响需求波动，取小值避免价格剧烈漂移。 */
    private static final float PRICE_MULTIPLIER = 0.05f;

    /** 染梦专属交易条目（generic 池，与原版一样每只游商最多抽 5 条）。 */
    public static final List<VillagerTrades.ItemListing> GENERIC = new ArrayList<>();

    /** 染梦专属交易条目（rare 池，与原版一样每只游商最多抽 1 条）。 */
    public static final List<VillagerTrades.ItemListing> RARE = new ArrayList<>();

    private static VillagerTrades.ItemListing[] vanillaGeneric = new VillagerTrades.ItemListing[0];
    private static VillagerTrades.ItemListing[] vanillaRare = new VillagerTrades.ItemListing[0];
    private static boolean vanillaCaptured;
    private static boolean tradesRegistered;

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

    /**
     * 登记染梦独立表的全部条目（{@link #GENERIC} / {@link #RARE}）：同款 13 条 + 染梦专属 4 条。
     * <p>
     * 由 {@link ModWandererTrades#onWandererTrades} 在 {@code WandererTradesEvent} 中调用：
     * 此时注册表已就绪，可以安全取模组物品。方法幂等，只在第一次调用时生效。
     * <p>
     * 条目全部在本类内独立构造，不引用 {@link ModWandererTrades}；数值来源见
     * {@code document/design/游商交易注入设计.md}「五、染梦世界独立交易表」。
     */
    public static void registerTrades() {
        if (tradesRegistered) {
            return;
        }
        tradesRegistered = true;

        Item coin = ModItems.MELT_DREAM_COIN.get();

        // ===== 与全局表同款的 13 条（本表自己持有一份，与主世界注入表互不引用）=====
        // #1 2~4 绿宝石 -> 1 融梦星币
        GENERIC.add(trade(Items.EMERALD, 2, 4, item(ModItems.MELT_DREAM_COIN), 16, 32, 2));
        // #2 5~7 融梦星币 -> 1 绿宝石
        GENERIC.add(trade(coin, 5, 7, new ItemStack(Items.EMERALD), 16, 32, 2));
        // #3 1~3 融梦星币 -> 1 蜜脾
        GENERIC.add(trade(coin, 1, 3, new ItemStack(Items.HONEYCOMB), 8, 16, 2));
        // #4 10~16 绿宝石 -> 1 梦境果汁
        GENERIC.add(trade(Items.EMERALD, 10, 16, item(ModItems.GLASS_JAR_OF_DREAM_JUICE), 1, 3, 5));
        // #5 20~32 绿宝石 -> 1 超级深海秘宝
        GENERIC.add(trade(Items.EMERALD, 20, 32,
                DeepTreasureItem.createSuper(ModItems.DEEP_SEA_TREASURE.get()), 1, 3, 5));
        // #6 10~16 融梦星币 -> 1 染梦香水
        GENERIC.add(trade(coin, 10, 16, item(ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME), 1, 3, 5));
        // #7 10~16 融梦星币 -> 1 鲜红露滴
        GENERIC.add(trade(coin, 10, 16, item(ModItems.RED_DEW), 1, 3, 5));
        // #8 10~16 融梦星币 -> 1 融梦棉花糖
        GENERIC.add(trade(coin, 10, 16, item(ModItems.MELT_DREAM_COTTON_CANDY), 1, 3, 5));
        // #9 20~32 融梦星币 -> 1 凤梨爱琴海
        GENERIC.add(trade(coin, 20, 32, item(ModItems.PINEAPPLE_LOVE_SEA), 1, 2, 8));
        // #10 20~32 融梦星币 -> 1 狐狸雕像
        GENERIC.add(trade(coin, 20, 32, item(ModItems.FOX_SCULPTURE), 1, 2, 8));
        // #11 20~32 融梦星币 -> 1 迷梦冶梦莲
        GENERIC.add(trade(coin, 20, 32, item(ModItems.MISTY_DREAMING_LOTUS), 1, 2, 8));
        // #12 20~32 融梦星币 -> 1 融梦涌泉桶
        GENERIC.add(trade(coin, 20, 32, item(ModItems.MELT_DREAM_LIQUID_BUCKET), 1, 2, 8));
        // #13 24~48 融梦星币 -> 1 融梦水晶碎片
        GENERIC.add(trade(coin, 24, 48, item(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT), 1, 1, 12));

        // ===== 染梦专属 4 条（仅本表持有）=====
        // 10~16 融梦星币 -> 1 超级染梦深海秘宝（NBT deep_treasure_super=true），1~3 次
        GENERIC.add(new RangeItemListing(new ItemStack(coin),
                DeepTreasureItem.createSuper(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get()),
                10, 16, 1, 3, 5, PRICE_MULTIPLIER));
        // 24~48 融梦星币 -> 1 生命水晶，1 次
        GENERIC.add(new RangeItemListing(new ItemStack(coin),
                new ItemStack(ModItems.LIFE_CRYSTAL.get()),
                24, 48, 1, 1, 12, PRICE_MULTIPLIER));
        // 12~24 融梦星币 -> 1 银狐棉花糖，1~3 次
        GENERIC.add(new RangeItemListing(new ItemStack(coin),
                new ItemStack(ModItems.YINHUL_COTTON_CANDY.get()),
                12, 24, 1, 3, 5, PRICE_MULTIPLIER));
        // 32~64 融梦星币 -> 1 梦之釜，1 次
        GENERIC.add(new RangeItemListing(new ItemStack(coin),
                new ItemStack(ModItems.DREAM_CAULDRON.get()),
                32, 64, 1, 1, 12, PRICE_MULTIPLIER));
    }

    private static RangeItemListing trade(Item priceItem, int priceMin, int priceMax,
                                          ItemStack result, int usesMin, int usesMax, int xp) {
        return new RangeItemListing(new ItemStack(priceItem), result,
                priceMin, priceMax, usesMin, usesMax, xp, PRICE_MULTIPLIER);
    }

    private static ItemStack item(RegistryObject<Item> item) {
        return new ItemStack(item.get());
    }

    /** 该游商是否应使用染梦独立表。 */
    public static boolean applies(WanderingTrader trader) {
        return vanillaCaptured
                && Config.dyedreamWandererTradesEnabled
                && trader.level().dimension().equals(DyedreamDimension.DYEDREAM_WORLD);
    }

    /**
     * 按配置的投放方式填充 {@code trader} 的 offers。
     * <p>
     * 注入模式：按原版规则从「原版基础池 + 染梦表条目」抽取 5+1 条。
     * 追加模式：原版池保持不变，先按原版规则抽 5+1 条，再从染梦表条目中随机追加
     * {@link Config#wanderingTraderTradesAppendCount} 条（保证出现）。
     * <p>
     * 调用时 {@code trader} 的 offers 必须已初始化（{@code updateTrades} 只会在
     * {@code AbstractVillager#getOffers} 初始化 offers 之后被调用）。
     */
    public static void fillOffers(WanderingTrader trader) {
        MerchantOffers offers = trader.getOffers();
        RandomSource random = trader.getRandom();
        if (ModWandererTrades.isInjectedToVanillaPool()) {
            addOffers(offers, trader, merged(vanillaGeneric, GENERIC), GENERIC_PICKS, random);
            addOffers(offers, trader, merged(vanillaRare, RARE), RARE_PICKS, random);
            return;
        }
        addOffers(offers, trader, vanillaGeneric, GENERIC_PICKS, random);
        addOffers(offers, trader, vanillaRare, RARE_PICKS, random);
        List<VillagerTrades.ItemListing> candidates = new ArrayList<>(GENERIC);
        candidates.addAll(RARE);
        ModWandererTrades.appendRandomOffers(offers, trader, candidates,
                Config.wanderingTraderTradesAppendCount);
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
