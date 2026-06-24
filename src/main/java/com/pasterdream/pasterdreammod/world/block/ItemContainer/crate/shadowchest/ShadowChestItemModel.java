package com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowChestItemModel extends GeoModel<ShadowChestItem>
{
    @Override
    public ResourceLocation getModelResource(ShadowChestItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_chest.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowChestItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/shadow_chest.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShadowChestItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_chest.animation.json");
    }
}
