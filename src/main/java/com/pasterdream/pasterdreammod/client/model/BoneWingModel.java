package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.BoneWingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BoneWingModel extends GeoModel<BoneWingEntity> {
    @Override
    public ResourceLocation getModelResource(BoneWingEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/bone_wing.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BoneWingEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(BoneWingEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/bone_wing.animation.json");
    }
}
