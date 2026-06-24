package com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PicnicBasketBlockModel extends GeoModel<PicnicBasketBlockEntity>
{
    @Override
    public ResourceLocation getModelResource(PicnicBasketBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/picnic_basket.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PicnicBasketBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/picnic_basket.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PicnicBasketBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/picnic_basket.animation.json");
    }
}
