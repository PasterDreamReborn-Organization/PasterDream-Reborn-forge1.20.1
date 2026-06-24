package com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PicnicBasketBlockEntityRenderer extends GeoBlockRenderer<PicnicBasketBlockEntity>
{
    public PicnicBasketBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        super(new PicnicBasketBlockModel());
    }
}
