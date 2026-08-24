package com.pasterdream.pasterdreammod.world.block.shadowdungeonportal.repaired;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ShadowDungeonPortalTileRenderer extends GeoBlockRenderer<ShadowDungeonPortalTileEntity> {
    public ShadowDungeonPortalTileRenderer(BlockEntityRendererProvider.Context context) {
        super(new ShadowDungeonPortalBlockModel());
    }
}
