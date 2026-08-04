package com.pasterdream.pasterdreammod.world.block.lifecrystal;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class LifeCrystalBlockEntityRenderer extends GeoBlockRenderer<LifeCrystalBlockEntity> {
    public LifeCrystalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new LifeCrystalBlockModel());
    }
}
