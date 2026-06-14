package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.level.block.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PasterDreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    @SuppressWarnings({"deprecation", "removal"})
    public static void onClientSetup(FMLClientSetupEvent event) {
        // ===== cutout 渲染（镂空/十字） =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_PLANKS_PANE.get(), RenderType.cutout());

        // ===== translucent 渲染（半透明玻璃） =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_GLASSPANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CARVE_DYEDREAM_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CARVE_DYEDREAM_GLASSPANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_CARVE_DYEDREAM_GLASSPANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PINK_SLIME_BLOCK.get(), RenderType.translucent());
    }
}
