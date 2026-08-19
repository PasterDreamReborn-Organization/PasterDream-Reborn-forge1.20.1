package com.pasterdream.pasterdreammod.world.block.aaroncoseye;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import javax.annotation.Nullable;

public class AaroncosEyeTileRenderer extends GeoBlockRenderer<AaroncosEyeTileEntity> {
    public AaroncosEyeTileRenderer(BlockEntityRendererProvider.Context context) {
        super(new AaroncosEyeBlockModel());
    }

    @Override
    public RenderType getRenderType(AaroncosEyeTileEntity animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
