package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.world.entity.ShadowTuneTotemEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowTuneTotemModel extends GeoModel<ShadowTuneTotemEntity> {
    @Override
    public ResourceLocation getAnimationResource(ShadowTuneTotemEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", "animations/shadow_rune_totem.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(ShadowTuneTotemEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", "geo/shadow_rune_totem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowTuneTotemEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/" + entity.getTexture() + ".png");
    }
}
