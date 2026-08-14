package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.HighvoltageThundercloudEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HighvoltageThundercloudModel extends GeoModel<HighvoltageThundercloudEntity> {
    @Override
    public ResourceLocation getModelResource(HighvoltageThundercloudEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/highvoltage_thundercloud.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HighvoltageThundercloudEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + animatable.getTexture() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(HighvoltageThundercloudEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/highvoltage_thundercloud.animation.json");
    }
}
