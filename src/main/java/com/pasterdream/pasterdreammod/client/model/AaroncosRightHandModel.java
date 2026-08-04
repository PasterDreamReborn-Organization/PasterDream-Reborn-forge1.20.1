package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.world.entity.AaroncosRightHandEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AaroncosRightHandModel extends GeoModel<AaroncosRightHandEntity> {
    @Override
    public ResourceLocation getAnimationResource(AaroncosRightHandEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", "animations/aaroncos_right_hand.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(AaroncosRightHandEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", "geo/aaroncos_right_hand.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AaroncosRightHandEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/" + entity.getTexture() + ".png");
    }
}
