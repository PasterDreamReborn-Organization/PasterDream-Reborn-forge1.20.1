package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.ThundercloudEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ThundercloudModel extends GeoModel<ThundercloudEntity> {
    @Override
    public ResourceLocation getModelResource(ThundercloudEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/thundercloud.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ThundercloudEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + animatable.getTexture() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ThundercloudEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/thundercloud.animation.json");
    }
}
