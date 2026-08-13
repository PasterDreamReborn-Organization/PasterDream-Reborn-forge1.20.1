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
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() != VillagerProfession.TOOLSMITH) return;
        if (Math.random() >= Config.toolsmithBlueprintTradeChance) return;

        ItemStack blueprint = BluePrintWithNBTToCreativeModeTab.buildNBT("精铸工坊");

        event.getTrades().get(5).add(new BasicItemListing(
                new ItemStack(ModItems.PERGAMYN.get()),
                new ItemStack(Items.EMERALD, 28),
                blueprint,
                10, 5, 0.05f));
    }
}
