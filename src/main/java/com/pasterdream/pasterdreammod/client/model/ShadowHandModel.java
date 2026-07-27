package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.ShadowHandEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowHandModel extends GeoModel<ShadowHandEntity> {
    @Override
    public ResourceLocation getAnimationResource(ShadowHandEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_hand.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(ShadowHandEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_hand.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowHandEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }
}
