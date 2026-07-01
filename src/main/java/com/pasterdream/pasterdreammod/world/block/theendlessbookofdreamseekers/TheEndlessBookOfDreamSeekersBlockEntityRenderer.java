package com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class TheEndlessBookOfDreamSeekersBlockEntityRenderer extends GeoBlockRenderer<TheEndlessBookOfDreamSeekersBlockEntity>
{
    public TheEndlessBookOfDreamSeekersBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        super(new TheEndlessBookOfDreamSeekersBlockModel());
    }
}
