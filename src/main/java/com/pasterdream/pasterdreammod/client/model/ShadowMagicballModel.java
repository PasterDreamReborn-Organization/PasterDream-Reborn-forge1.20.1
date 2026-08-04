package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.world.entity.ShadowMagicballEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowMagicballModel extends GeoModel<ShadowMagicballEntity> {
    @Override
    public ResourceLocation getAnimationResource(ShadowMagicballEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", "animations/shadow_magicball.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(ShadowMagicballEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", "geo/shadow_magicball.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowMagicballEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/" + entity.getTexture() + ".png");
    }
}
