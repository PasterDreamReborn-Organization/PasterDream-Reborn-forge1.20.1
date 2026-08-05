package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class TwilightLanternTileRenderer extends GeoBlockRenderer<TwilightLanternBlockEntity> {
    public TwilightLanternTileRenderer(BlockEntityRendererProvider.Context context) {
        super(new TwilightLanternBlockModel());
    }

    @Override
    public RenderType getRenderType(TwilightLanternBlockEntity animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
