package com.pasterdream.pasterdreammod.world.block;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowVortexDisplayModel extends GeoModel<ShadowVortexDisplayItem> {
    @Override
    public ResourceLocation getModelResource(ShadowVortexDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_vortex.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowVortexDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/shadow_vortex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShadowVortexDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_vortex.animation.json");
    }
}
