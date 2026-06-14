package com.pasterdream.pasterdreammod;

import com.pasterdream.pasterdreammod.client.ClientSetRenderLayer;
import com.pasterdream.pasterdreammod.helper.FluidContainerCapability.FluidContainerCapabilityHandler;
import com.pasterdream.pasterdreammod.helper.FluidHandler.FluidHandlerResolvers;
import com.pasterdream.pasterdreammod.helper.tooltipadder.AddTooltip;
import com.pasterdream.pasterdreammod.init.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib.GeckoLib;

@Mod(PasterDreamMod.MOD_ID)
public class PasterDreamMod
{
    public static final String MOD_ID = "pasterdream";

    public PasterDreamMod(FMLJavaModLoadingContext context)
    {
        GeckoLib.initialize();

        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);             //注册物品
        ModCreativeModeTabs.register(modEventBus);  //注册创造模式物品栏
        ModBlocks.register(modEventBus);            //注册方块
        ModFluids.register(modEventBus);
        ModNetwork.register();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::AddItemTooltip);
        modEventBus.addListener(this::AddAttachCapabilities);

        MinecraftForge.EVENT_BUS.register(this);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);     //加载配置文件
    }

    //在这里输入通用端注册内容
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        FluidHandlerResolvers.FluidHandlerResolverRegister();
        ModFluidContainerRelation.registerFluidContainerRelation();
    }

    //在这里输入客户端注册内容
    private void clientSetup(final FMLClientSetupEvent event)
    {
        ClientSetRenderLayer.register();
    }

    private void AddItemTooltip(ItemTooltipEvent event)
    {
        AddTooltip.addTooltip(event);
    }

    private void AddAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event)
    {
        FluidContainerCapabilityHandler.attachCapabilities(event);
    }
}
