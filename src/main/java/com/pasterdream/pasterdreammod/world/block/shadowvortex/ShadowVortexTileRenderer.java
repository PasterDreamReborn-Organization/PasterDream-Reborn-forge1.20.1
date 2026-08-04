package com.pasterdream.pasterdreammod.world.block.shadowvortex;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ShadowVortexTileRenderer extends GeoBlockRenderer<ShadowVortexTileEntity> {
    public ShadowVortexTileRenderer(BlockEntityRendererProvider.Context context) {
        super(new ShadowVortexBlockModel());
    }
}
