package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.JellyfishEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class JellyfishModel extends GeoModel<JellyfishEntity> {
    @Override
    public ResourceLocation getModelResource(JellyfishEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/jellyfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(JellyfishEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(JellyfishEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/jellyfish.animation.json");
    }
}
