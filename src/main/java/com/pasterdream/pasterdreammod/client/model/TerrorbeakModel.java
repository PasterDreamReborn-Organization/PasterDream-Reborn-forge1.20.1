package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.terrorbeak.TerrorbeakEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TerrorbeakModel extends GeoModel<TerrorbeakEntity> {
    @Override
    public ResourceLocation getAnimationResource(TerrorbeakEntity entity) {
        String anim = entity.getTexture().startsWith("crazy_")
                ? "animations/crazy_terrorbeak.animation.json"
                : "animations/terrorbeak.animation.json";
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, anim);
    }

    @Override
    public ResourceLocation getModelResource(TerrorbeakEntity entity) {
        String geo = "geo/" + entity.getTexture() + ".geo.json";
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, geo);
    }

    @Override
    public ResourceLocation getTextureResource(TerrorbeakEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "textures/entities/" + entity.getTexture() + ".png");
    }
}
