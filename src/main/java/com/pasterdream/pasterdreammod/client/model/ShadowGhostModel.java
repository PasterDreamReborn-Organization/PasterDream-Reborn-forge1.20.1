package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.ITextureVariant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.model.GeoModel;

public class ShadowGhostModel<T extends LivingEntity & GeoEntity & ITextureVariant> extends GeoModel<T> {
    @Override
    public ResourceLocation getAnimationResource(T entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_ghost.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(T entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_ghost.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }
}
