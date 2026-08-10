package com.pasterdream.pasterdreammod.world.block.shadowhandtrap;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowHandTrapItemModel extends GeoModel<ShadowHandTrapItem> {
    @Override
    public ResourceLocation getModelResource(ShadowHandTrapItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_hand_trap.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowHandTrapItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/shadow_hand_trap.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShadowHandTrapItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_hand_trap.animation.json");
    }
}
