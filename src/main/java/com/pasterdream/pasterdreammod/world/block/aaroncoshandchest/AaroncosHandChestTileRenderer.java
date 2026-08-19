package com.pasterdream.pasterdreammod.world.block.aaroncoshandchest;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import javax.annotation.Nullable;

public class AaroncosHandChestTileRenderer extends GeoBlockRenderer<AaroncosHandChestTileEntity> {
    public AaroncosHandChestTileRenderer(BlockEntityRendererProvider.Context context) {
        super(new AaroncosHandChestBlockModel());
    }

    @Override
    public RenderType getRenderType(AaroncosHandChestTileEntity animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
