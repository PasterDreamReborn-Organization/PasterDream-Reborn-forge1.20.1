package com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PicnicBasketItemModel extends GeoModel<PicnicBasketItem>
{
    @Override
    public ResourceLocation getModelResource(PicnicBasketItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/picnic_basket.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PicnicBasketItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/picnic_basket.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PicnicBasketItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/picnic_basket.animation.json");
    }
}
