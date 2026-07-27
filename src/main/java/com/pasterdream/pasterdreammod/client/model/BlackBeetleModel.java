package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.beetle.BlackBeetleEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BlackBeetleModel extends GeoModel<BlackBeetleEntity> {
    @Override
    public ResourceLocation getAnimationResource(BlackBeetleEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/black_beetle.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(BlackBeetleEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/black_beetle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlackBeetleEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }
}
