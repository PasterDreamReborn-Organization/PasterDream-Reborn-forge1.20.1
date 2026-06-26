package com.pasterdream.pasterdreammod;

import com.pasterdream.pasterdreammod.client.*;
import com.pasterdream.pasterdreammod.helper.fluidhandler.FluidHandlerResolvers;
import com.pasterdream.pasterdreammod.helper.tooltipadder.AddToolTip;
import com.pasterdream.pasterdreammod.init.*;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
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
        ModBlockEntities.register(modEventBus);     //注册方块实体
        ModFluids.register(modEventBus);            //注册流体
        ModMenus.register(modEventBus);             //注册菜单
        ModRecipes.register(modEventBus);           //注册配方
        ModNetwork.register();                      //注册网络包

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.addListener(this::AddItemTooltip);
        MinecraftForge.EVENT_BUS.addListener(this::AddCommand);
        modEventBus.addListener(this::AddOverlays);
        modEventBus.addListener(this::AddEntityRenderersEvent);

        MinecraftForge.EVENT_BUS.register(this);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);     //加载配置文件
    }

    //在这里输入通用端注册内容
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        FluidHandlerResolvers.FluidHandlerResolverRegister();
        ModFluidContainerRelation.registerFluidContainerRelation();
        ModDreamNotesContentRelation.registerDreamNotesContentRelation();
        ModCropRelation.registerCropRelation();
    }

    //在这里输入客户端注册内容
    private void clientSetup(final FMLClientSetupEvent event)
    {
        ClientSetRenderLayer.register();
        ModScreens.register(event);
        ModBlockEntityRenderer.FMLClientSetupEventRegister(event);
    }

    private void AddItemTooltip(ItemTooltipEvent event)
    {
        AddToolTip.addTooltip(event);
    }

    private void AddCommand(RegisterCommandsEvent event)
    {
        ModCommands.register(event.getDispatcher());
    }

    private void AddOverlays(RegisterGuiOverlaysEvent event)
    {
        event.registerAboveAll("melt_dream_energy", MeltDreamEnergyTank.MELT_DREAM_ENERGY_TANK);
        event.registerAboveAll("san", SanTank.SAN_TANK);
    }

    private void AddEntityRenderersEvent(EntityRenderersEvent.RegisterRenderers event)
    {
        ModBlockEntityRenderer.EntityRenderersEventRegister(event);
    }
}
