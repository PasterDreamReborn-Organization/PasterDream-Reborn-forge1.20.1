package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.helper.itemwithnbt.blueprintwithnbt.BluePrintWithNBT;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.blueprints.BluePrintWithNBTToCreativeModeTab;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;

public class ModVillagerTrades {

    /** 原版村民价格需求倍率；取小值避免价格剧烈漂移。 */
    private static final float PRICE_MULTIPLIER = 0.05f;

    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.TOOLSMITH) {
            addToolsmithBlueprintTrade(event);
        } else if (event.getType() == VillagerProfession.FISHERMAN) {
            addFishermanDeepSeaTreasureTrade(event);
        }
    }

    /**
     * 工具匠（5 级）按配置概率售卖精铸工坊蓝图。
     */
    private static void addToolsmithBlueprintTrade(VillagerTradesEvent event) {
        if (Math.random() >= Config.toolsmithBlueprintTradeChance) return;

        ItemStack blueprint = BluePrintWithNBTToCreativeModeTab.buildNBT("精铸工坊");

        event.getTrades().get(5).add(new BasicItemListing(
                new ItemStack(ModItems.PERGAMYN.get()),
                new ItemStack(Items.EMERALD, 28),
                blueprint,
                10, 5, 0.05f));
    }

    /**
     * 大师渔夫（5 级）售卖主世界普通深海秘宝：16~32 绿宝石，每次补货可成交 4 次。
     * <p>
     * 不额外做概率判定：原版 {@code Villager#addOffersFromItemListings} 每级只从该级交易池等权、
     * 不重复抽 2 条，本条目加入后与原有条目共同竞争，因此新生成的大师渔夫有概率出现该交易。
     */
    private static void addFishermanDeepSeaTreasureTrade(VillagerTradesEvent event) {
        event.getTrades().get(5).add(new RangeItemListing(
                new ItemStack(Items.EMERALD),
                new ItemStack(ModItems.DEEP_SEA_TREASURE.get()),
                16, 32,
                4, 4,
                30, PRICE_MULTIPLIER));
    }
}
