package com.pasterdream.pasterdreammod.world.block.doll.uuzdoll;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class UUZDollBlockEntityRenderer extends GeoBlockRenderer<UUZDollBlockEntity>
{
    public UUZDollBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        super(new UUZDollBlockModel());
    }
}
