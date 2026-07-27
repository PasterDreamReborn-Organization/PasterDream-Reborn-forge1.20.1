package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.ShadowGolemEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowGolemModel extends GeoModel<ShadowGolemEntity> {
    @Override
    public ResourceLocation getAnimationResource(ShadowGolemEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_golem.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(ShadowGolemEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_golem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowGolemEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }
}
