package com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ShadowChestBlockEntityRenderer extends GeoBlockRenderer<ShadowChestBlockEntity>
{
    public ShadowChestBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        super(new ShadowChestBlockModel());
    }
}
