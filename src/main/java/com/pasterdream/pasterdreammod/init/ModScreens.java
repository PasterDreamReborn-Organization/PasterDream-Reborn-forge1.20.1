package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket.PicnicBasketScreen;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest.ShadowChestScreen;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.windmoorcrate.WindMoorCrateScreen;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanScreen;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.desk.dyedreamdesk.DyedreamDeskScreen;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.desk.shadowdesk.ShadowDeskScreen;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorScreen;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronScreen;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.openedmeltdreamcrystalchest.OpenedMeltDreamCrystalChestScreen;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableScreen;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceScreen;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableScreen;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarScreen;
import com.pasterdream.pasterdreammod.world.item.StorgeBagItem.StorageBagScreen;
import com.pasterdream.pasterdreammod.world.item.StorgeBagItem.LargeStorageBagScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModScreens
{
    public static void register(FMLClientSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            MenuScreens.register(ModMenus.CLAYPAN.get(), ClaypanScreen::new);
            MenuScreens.register(ModMenus.DREAM_CAULDRON.get(), DreamCauldronScreen::new);
            MenuScreens.register(ModMenus.MORTAR.get(), MortarScreen::new);
            MenuScreens.register(ModMenus.DYEDREAM_DESK.get(), DyedreamDeskScreen::new);
            MenuScreens.register(ModMenus.SHADOW_DESK.get(), ShadowDeskScreen::new);
            MenuScreens.register(ModMenus.PICNIC_BASKET.get(), PicnicBasketScreen::new);
            MenuScreens.register(ModMenus.SHADOW_CHEST.get(), ShadowChestScreen::new);
            MenuScreens.register(ModMenus.WIND_MOOR_CRATE.get(), WindMoorCrateScreen::new);
            MenuScreens.register(ModMenus.RESEARCH_TABLE.get(), ResearchTableScreen::new);
            MenuScreens.register(ModMenus.STORAGE_BAG.get(), StorageBagScreen::new);
            MenuScreens.register(ModMenus.LARGE_STORAGE_BAG.get(), LargeStorageBagScreen::new);
            MenuScreens.register(ModMenus.OPENED_MELT_DREAM_CRYSTAL_CHEST.get(), OpenedMeltDreamCrystalChestScreen::new);
            MenuScreens.register(ModMenus.DREAM_ACCUMULATOR.get(), DreamAccumulatorScreen::new);
            MenuScreens.register(ModMenus.WEAPON_WORKSHOP_CRAFTING_TABLE.get(), WeaponWorkshopCraftingTableScreen::new);
            MenuScreens.register(ModMenus.WEAPON_WORKSHOP_BLAST_FURNACE.get(), WeaponWorkshopBlastFurnaceScreen::new);
        });
    }
}
