package com.pasterdream.pasterdreammod.world.block.shadowhandtrap;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowHandTrapBlockModel extends GeoModel<ShadowHandTrapBlockEntity> {
    @Override
    public ResourceLocation getModelResource(ShadowHandTrapBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_hand_trap.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowHandTrapBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/shadow_hand_trap.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShadowHandTrapBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_hand_trap.animation.json");
    }
}
