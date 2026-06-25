package com.pasterdream.pasterdreammod.world.block.doll.qymdoll;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class QYMDollBlockModel extends GeoModel<QYMDollBlockEntity>
{
    @Override
    public ResourceLocation getModelResource(QYMDollBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/qym_doll.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(QYMDollBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/qym_doll.png");
    }

    @Override
    public ResourceLocation getAnimationResource(QYMDollBlockEntity animatable)
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/qym_doll.animation.json");
    }
}
