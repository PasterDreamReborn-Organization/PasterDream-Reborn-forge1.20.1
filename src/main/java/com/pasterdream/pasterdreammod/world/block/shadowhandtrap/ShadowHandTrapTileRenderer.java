package com.pasterdream.pasterdreammod.world.block.shadowhandtrap;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ShadowHandTrapTileRenderer extends GeoBlockRenderer<ShadowHandTrapBlockEntity> {
    public ShadowHandTrapTileRenderer(BlockEntityRendererProvider.Context context) {
        super(new ShadowHandTrapBlockModel());
    }
}
