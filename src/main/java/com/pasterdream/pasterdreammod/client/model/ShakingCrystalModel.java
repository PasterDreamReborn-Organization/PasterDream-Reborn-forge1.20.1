package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.shakingcrystal.ShakingCrystalEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShakingCrystalModel extends GeoModel<ShakingCrystalEntity> {
    @Override
    public ResourceLocation getModelResource(ShakingCrystalEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shaking_crystal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShakingCrystalEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + animatable.getTexture() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShakingCrystalEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shaking_crystal.animation.json");
    }
}
