package com.pasterdream.pasterdreammod.world.block.aaroncoshandchest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AaroncosHandChestDisplayModel extends GeoModel<AaroncosHandChestDisplayItem> {
    @Override
    public ResourceLocation getModelResource(AaroncosHandChestDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/aaroncos_hand_chest.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AaroncosHandChestDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/aaroncos_hand_chest.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AaroncosHandChestDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/aaroncos_hand_chest.animation.json");
    }
}
