package com.pasterdream.pasterdreammod.world.block.aaroncoseye;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AaroncosEyeDisplayModel extends GeoModel<AaroncosEyeDisplayItem> {
    @Override
    public ResourceLocation getModelResource(AaroncosEyeDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/aaroncos_hand_spawn_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AaroncosEyeDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/aaroncos_eye.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AaroncosEyeDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/aaroncos_hand_spawn_block.animation.json");
    }
}
