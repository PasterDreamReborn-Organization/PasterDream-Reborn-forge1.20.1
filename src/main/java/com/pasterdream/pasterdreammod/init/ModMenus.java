package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket.PicnicBasketBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket.PicnicBasketMenu;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest.ShadowChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest.ShadowChestMenu;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.windmoorcrate.WindMoorCrateBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.windmoorcrate.WindMoorCrateMenu;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlockEntity;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanMenu;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.desk.dyedreamdesk.DyedreamDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.desk.dyedreamdesk.DyedreamDeskMenu;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.desk.shadowdesk.ShadowDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.desk.shadowdesk.ShadowDeskMenu;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorMenu;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronMenu;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.openedmeltdreamcrystalchest.OpenedMeltDreamCrystalChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.openedmeltdreamcrystalchest.OpenedMeltDreamCrystalChestMenu;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableBlockEntity;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableMenu;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceMenu;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarMenu;
import com.pasterdream.pasterdreammod.world.item.StorgeBagItem.StorageBagMenu;
import com.pasterdream.pasterdreammod.world.item.StorgeBagItem.LargeStorageBagMenu;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus
{
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, PasterDreamMod.MOD_ID);

    public static final RegistryObject<MenuType<ClaypanMenu>> CLAYPAN = MENUS.register("claypan", () -> IForgeMenuType.create((windowId, inv, data) ->
    {
        BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
        return new ClaypanMenu(windowId, inv, (ClaypanBlockEntity)blockEntity);
    }));

    public static final RegistryObject<MenuType<DreamCauldronMenu>> DREAM_CAULDRON = MENUS.register("dream_cauldron", () -> IForgeMenuType.create((windowId, inv, data) ->
    {
        BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
        return new DreamCauldronMenu(windowId, inv, (DreamCauldronBlockEntity)blockEntity);
    }));

    public static final RegistryObject<MenuType<MortarMenu>> MORTAR = MENUS.register("mortar", () -> IForgeMenuType.create((id, inventory, data) ->
    {
        InteractionHand hand = data.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        ItemStack stack = inventory.player.getItemInHand(hand);
        return new MortarMenu(id, inventory, stack, hand);
    }));

    public static final RegistryObject<MenuType<DyedreamDeskMenu>> DYEDREAM_DESK = MENUS.register("dyedream_desk", () -> IForgeMenuType.create((windowId, inv, data) ->
    {
        BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
        return new DyedreamDeskMenu(windowId, inv, (DyedreamDeskBlockEntity) blockEntity);
    }));

    public static final RegistryObject<MenuType<ShadowDeskMenu>> SHADOW_DESK = MENUS.register("shadow_desk", () -> IForgeMenuType.create((windowId, inv, data) ->
    {
        BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
        return new ShadowDeskMenu(windowId, inv, (ShadowDeskBlockEntity) blockEntity);
    }));

    public static final RegistryObject<MenuType<PicnicBasketMenu>> PICNIC_BASKET = MENUS.register("picnic_basket", () -> IForgeMenuType.create((windowId, inv, data) ->
    {
        BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
        return new PicnicBasketMenu(windowId, inv, (PicnicBasketBlockEntity) blockEntity);
    }));

    public static final RegistryObject<MenuType<ShadowChestMenu>> SHADOW_CHEST = MENUS.register("shadow_chest", () -> IForgeMenuType.create((windowId, inv, data) ->
    {
        BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
        return new ShadowChestMenu(windowId, inv, (ShadowChestBlockEntity) blockEntity);
    }));

    public static final RegistryObject<MenuType<WindMoorCrateMenu>> WIND_MOOR_CRATE = MENUS.register("wind_moor_crate", () -> IForgeMenuType.create((windowId, inv, data) ->
    {
        BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
        return new WindMoorCrateMenu(windowId, inv, (WindMoorCrateBlockEntity) blockEntity);
    }));

    public static final RegistryObject<MenuType<ResearchTableMenu>> RESEARCH_TABLE = MENUS.register("research_table", () -> IForgeMenuType.create((windowId, inv, data) ->
    {
        BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
        return new ResearchTableMenu(windowId, inv, (ResearchTableBlockEntity) blockEntity);
    }));

    public static final RegistryObject<MenuType<StorageBagMenu>> STORAGE_BAG = MENUS.register("storage_bag", () -> IForgeMenuType.create(StorageBagMenu::new));
    public static final RegistryObject<MenuType<LargeStorageBagMenu>> LARGE_STORAGE_BAG = MENUS.register("large_storage_bag", () -> IForgeMenuType.create(LargeStorageBagMenu::new));

    public static final RegistryObject<MenuType<OpenedMeltDreamCrystalChestMenu>> OPENED_MELT_DREAM_CRYSTAL_CHEST = MENUS.register("opened_melt_dream_crystal_chest", () -> IForgeMenuType.create((windowId, inv, data) ->
    {
        BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
        return new OpenedMeltDreamCrystalChestMenu(windowId, inv, (OpenedMeltDreamCrystalChestBlockEntity) blockEntity);
    }));

    public static final RegistryObject<MenuType<DreamAccumulatorMenu>> DREAM_ACCUMULATOR = MENUS.register("dream_accumulator", () -> IForgeMenuType.create((windowId, inv, data) ->
    {
        BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
        return new DreamAccumulatorMenu(windowId, inv, (DreamAccumulatorBlockEntity) blockEntity);
    }));

    public static final RegistryObject<MenuType<WeaponWorkshopBlastFurnaceMenu>> WEAPON_WORKSHOP_BLAST_FURNACE = MENUS.register("weapon_woekshop_blast_furnace", () -> IForgeMenuType.create((windowId, inv, data) ->
    {
        BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
        return new WeaponWorkshopBlastFurnaceMenu(windowId, inv, (WeaponWorkshopBlastFurnaceBlockEntity) blockEntity);
    }));

    public static void register(IEventBus eventBus)
    {
        MENUS.register(eventBus);
    }
}
