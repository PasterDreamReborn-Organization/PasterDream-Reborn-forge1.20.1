package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.FireflyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FireflyModel extends GeoModel<FireflyEntity> {
    @Override
    public ResourceLocation getModelResource(FireflyEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/firefly.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FireflyEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(FireflyEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/firefly.animation.json");
    }
}
