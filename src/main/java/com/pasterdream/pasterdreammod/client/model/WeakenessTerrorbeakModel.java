package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.WeakenessTerrorbeakEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WeakenessTerrorbeakModel extends GeoModel<WeakenessTerrorbeakEntity> {
    @Override
    public ResourceLocation getAnimationResource(WeakenessTerrorbeakEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/weakness_terrorbeak.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(WeakenessTerrorbeakEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/weakness_terrorbeak.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WeakenessTerrorbeakEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }
}
