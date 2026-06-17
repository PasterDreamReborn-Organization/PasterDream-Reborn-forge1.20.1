package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlockEntity;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanMenu;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
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
        if (inv.player instanceof ServerPlayer serverPlayer)
        {
            return new ClaypanMenu(windowId, inv, (ClaypanBlockEntity)blockEntity, serverPlayer);
        }
            else
            {
                return new ClaypanMenu(windowId, inv, (ClaypanBlockEntity)blockEntity);
            }
    }));

    public static final RegistryObject<MenuType<DreamCauldronMenu>> DREAM_CAULDRON = MENUS.register("dream_cauldron", () -> IForgeMenuType.create((windowId, inv, data) ->
    {
        BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
        if (inv.player instanceof ServerPlayer serverPlayer)
        {
            return new DreamCauldronMenu(windowId, inv, (DreamCauldronBlockEntity)blockEntity, serverPlayer);
        }
            else
            {
                return new DreamCauldronMenu(windowId, inv, (DreamCauldronBlockEntity)blockEntity);
            }
    }));

    public static void register(IEventBus eventBus)
    {
        MENUS.register(eventBus);
    }
}
