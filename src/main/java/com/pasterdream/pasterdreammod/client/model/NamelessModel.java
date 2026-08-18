package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.NamelessEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NamelessModel extends GeoModel<NamelessEntity> {
    @Override
    public ResourceLocation getAnimationResource(NamelessEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/nameless.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(NamelessEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/nameless.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NamelessEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }
}
