package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public class ClientSetRenderLayer
{
    @SuppressWarnings({"deprecation", "removal"})
    public static void register()
    {
        // ===== cutout 渲染（镂空/十字） =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_PANE.get(), RenderType.cutout());

        // ===== translucent 渲染（半透明玻璃） =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CARVE_DYEDREAM_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PINK_SLIME_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_ICE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_PACKED_ICE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BIG_BUBBLE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CLOUD.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DARK_CLOUD.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_BUD_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_BUD_STAIRS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_BUD_SLAB.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_BUD_WALL.get(), RenderType.translucent());

        // ===== 冰凌晶芽 =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ICE_BUD.get(), RenderType.cutout());
    }
}
