package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetRenderLayer
{
    @SuppressWarnings({"deprecation", "removal"})
    public static void register()
    {
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
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_ICE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_PACKED_ICE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_LARTERN.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_BUD_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_BUD_STAIRS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_BUD_SLAB.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_BUD_WALL.get(), RenderType.translucent());
    }
}
