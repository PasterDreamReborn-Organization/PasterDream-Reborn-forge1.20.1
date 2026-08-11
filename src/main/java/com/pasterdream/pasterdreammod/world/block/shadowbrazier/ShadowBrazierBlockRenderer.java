package com.pasterdream.pasterdreammod.world.block.shadowbrazier;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ShadowBrazierBlockRenderer extends GeoBlockRenderer<ShadowBrazierBlockEntity> {
    public ShadowBrazierBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new ShadowBrazierBlockModel());
    }

    @Override
    public RenderType getRenderType(ShadowBrazierBlockEntity animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
