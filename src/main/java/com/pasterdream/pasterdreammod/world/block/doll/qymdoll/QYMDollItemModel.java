package com.pasterdream.pasterdreammod.world.block.doll.qymdoll;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class QYMDollItemModel extends GeoModel<QYMDollItem>
{
    @Override
    public ResourceLocation getModelResource(QYMDollItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/qym_doll.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(QYMDollItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/qym_doll.png");
    }

    @Override
    public ResourceLocation getAnimationResource(QYMDollItem animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/qym_doll.animation.json");
    }
}
