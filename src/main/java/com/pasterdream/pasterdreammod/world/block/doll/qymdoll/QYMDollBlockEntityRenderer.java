package com.pasterdream.pasterdreammod.world.block.doll.qymdoll;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class QYMDollBlockEntityRenderer extends GeoBlockRenderer<QYMDollBlockEntity>
{
    public QYMDollBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        super(new QYMDollBlockModel());
    }
}
