package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.AshBoneWingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AshBoneWingModel extends GeoModel<AshBoneWingEntity> {
    @Override
    public ResourceLocation getModelResource(AshBoneWingEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/ash_bone_wing.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AshBoneWingEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(AshBoneWingEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/ash_bone_wing.animation.json");
    }
}
