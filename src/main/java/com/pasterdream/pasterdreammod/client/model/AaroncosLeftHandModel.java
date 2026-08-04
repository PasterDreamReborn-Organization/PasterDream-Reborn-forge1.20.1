package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.world.entity.AaroncosLeftHandEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AaroncosLeftHandModel extends GeoModel<AaroncosLeftHandEntity> {
    @Override
    public ResourceLocation getAnimationResource(AaroncosLeftHandEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", "animations/aaroncos_left_hand.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(AaroncosLeftHandEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", "geo/aaroncos_left_hand.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AaroncosLeftHandEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/" + entity.getTexture() + ".png");
    }
}
