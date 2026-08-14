package com.pasterdream.pasterdreammod.world.block.windknightaltar;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import javax.annotation.Nullable;

public class WindKnightAltarBlockEntityRenderer extends GeoBlockRenderer<WindKnightAltarBlockEntity> {
    public WindKnightAltarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new WindKnightAltarBlockModel());
    }

    @Override
    public RenderType getRenderType(WindKnightAltarBlockEntity animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
