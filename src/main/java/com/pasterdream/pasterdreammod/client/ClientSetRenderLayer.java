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
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADOW_PANE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WIND_MOOR_PANE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RUST_BLACK_METAL_BARS.get(), RenderType.cutout());

        // ===== translucent 渲染（半透明玻璃） =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CARVE_DYEDREAM_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CLARITY_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CLARITY_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CARVE_CLARITY_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CARVE_CLARITY_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FRAME_CLARITY_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FRAME_CLARITY_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PINK_SLIME_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_ICE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_PACKED_ICE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BIG_BUBBLE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CLOUD.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DARK_CLOUD.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.THICK_CLOUD.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADOW.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.THICK_SHADOW.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADOW_FISSURE_1.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADOW_FISSURE_3.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADOW_FISSURE_5.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADOW_VORTEX.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_BUD_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_BUD_STAIRS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_BUD_SLAB.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_BUD_WALL.get(), RenderType.translucent());

        // ===== 染梦睡莲 / 染梦莲花 =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_LILY_PAD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DYEDREAM_LOTUS.get(), RenderType.cutout());

        // ===== 阴影植物系列 =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADOW_CANDLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADOW_SHORT_ROOTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADOW_ROOTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADOW_STEM_FERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADOW_SPROUTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADOW_FERN.get(), RenderType.cutout());

        // ===== 冰凌晶芽 =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ICE_BUD.get(), RenderType.cutout());

        // ===== 失落剑冢 =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LOST_SWORD_TOMB.get(), RenderType.cutout());

        // ===== 陶罐 =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CLAY_POT.get(), RenderType.cutout());
        // ===== 阴影陶罐 =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADOW_CLAY_POT.get(), RenderType.cutout());

        // ===== 小石子 =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEBBLE.get(), RenderType.cutout());

        // ===== 植物系列（花） =====
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.EDELWEISS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.NIPPY_EDELWEISS.get(), RenderType.cutout());

        // ===== 盆栽植物 =====
        for (var potted : ModBlocks.POTTED_PLANTS.values()) {
            ItemBlockRenderTypes.setRenderLayer(potted.get(), RenderType.cutout());
        }
    }
}
