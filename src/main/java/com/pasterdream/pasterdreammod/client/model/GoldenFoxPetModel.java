package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.GoldenFoxPetEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GoldenFoxPetModel extends GeoModel<GoldenFoxPetEntity> {
    @Override
    public ResourceLocation getModelResource(GoldenFoxPetEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/golden_fox_pet.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GoldenFoxPetEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/gloden_fox_pet_light.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GoldenFoxPetEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/golden_fox_pet.animation.json");
    }
}
