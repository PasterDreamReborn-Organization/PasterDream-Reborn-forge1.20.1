package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.beetle.BlackBeetleMotherEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BlackBeetleMotherModel extends GeoModel<BlackBeetleMotherEntity> {
    @Override
    public ResourceLocation getAnimationResource(BlackBeetleMotherEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/black_beetle_mother.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(BlackBeetleMotherEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/black_beetle_mother.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlackBeetleMotherEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }
}
