package com.pasterdream.pasterdreammod.world.block.shadowbrazier;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowBrazierDisplayModel extends GeoModel<ShadowBrazierDisplayItem> {
    @Override
    public ResourceLocation getModelResource(ShadowBrazierDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_brazier.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowBrazierDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/shadow_brazier.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShadowBrazierDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_brazier.animation.json");
    }
}
