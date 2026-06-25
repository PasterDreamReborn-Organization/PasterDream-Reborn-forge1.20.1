package com.pasterdream.pasterdreammod.world.block.doll.uuzdoll;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class UUZDollItemModel extends GeoModel<UUZDollItem>
{
    @Override
    public ResourceLocation getModelResource(UUZDollItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/uuz_doll.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UUZDollItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/uuz_doll.png");
    }

    @Override
    public ResourceLocation getAnimationResource(UUZDollItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/uuz_doll.animation.json");
    }
}
