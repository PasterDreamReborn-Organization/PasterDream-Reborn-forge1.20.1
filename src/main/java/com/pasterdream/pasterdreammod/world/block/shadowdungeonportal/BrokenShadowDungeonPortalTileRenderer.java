package com.pasterdream.pasterdreammod.world.block.shadowdungeonportal;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BrokenShadowDungeonPortalTileRenderer extends GeoBlockRenderer<BrokenShadowDungeonPortalTileEntity> {
    public BrokenShadowDungeonPortalTileRenderer(BlockEntityRendererProvider.Context context) {
        super(new BrokenShadowDungeonPortalBlockModel());
    }
}
