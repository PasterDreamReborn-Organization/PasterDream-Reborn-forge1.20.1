package com.pasterdream.pasterdreammod.world.block.ecologyglassjar;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class EcologyGlassJarBlockEntityRenderer extends GeoBlockRenderer<EcologyGlassJarBlockEntity> {
    public EcologyGlassJarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new EcologyGlassJarBlockModel());
    }

    @Override
    public RenderType getRenderType(EcologyGlassJarBlockEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
