package com.pasterdream.pasterdreammod.world.block.aaroncoseye;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AaroncosEyeBlockModel extends GeoModel<AaroncosEyeTileEntity> {
    @Override
    public ResourceLocation getModelResource(AaroncosEyeTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/aaroncos_hand_spawn_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AaroncosEyeTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/aaroncos_eye.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AaroncosEyeTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/aaroncos_hand_spawn_block.animation.json");
    }
}
