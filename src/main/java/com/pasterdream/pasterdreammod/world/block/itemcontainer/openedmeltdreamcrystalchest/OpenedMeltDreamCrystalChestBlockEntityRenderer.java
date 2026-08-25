package com.pasterdream.pasterdreammod.world.block.itemcontainer.openedmeltdreamcrystalchest;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import javax.annotation.Nullable;

public class OpenedMeltDreamCrystalChestBlockEntityRenderer extends GeoBlockRenderer<OpenedMeltDreamCrystalChestBlockEntity>
{
    public OpenedMeltDreamCrystalChestBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        super(new OpenedMeltDreamCrystalChestBlockModel());
    }

    @Override
    public RenderType getRenderType(OpenedMeltDreamCrystalChestBlockEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick)
    {
        return RenderType.entityTranslucent(texture);
    }
}
