package com.pasterdream.pasterdreammod.world.block.itemcontainer.openedmeltdreamcrystalchest;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class OpenedMeltDreamCrystalChestItemRenderer extends GeoItemRenderer<OpenedMeltDreamCrystalChestItem>
{
    public OpenedMeltDreamCrystalChestItemRenderer()
    {
        super(new OpenedMeltDreamCrystalChestItemModel());
    }

    @Override
    public RenderType getRenderType(OpenedMeltDreamCrystalChestItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick)
    {
        return RenderType.entityTranslucent(texture);
    }
}
