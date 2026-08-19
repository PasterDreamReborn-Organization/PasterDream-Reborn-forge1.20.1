package com.pasterdream.pasterdreammod.world.block.aaroncoshandchest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AaroncosHandChestBlockModel extends GeoModel<AaroncosHandChestTileEntity> {
    @Override
    public ResourceLocation getModelResource(AaroncosHandChestTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/aaroncos_hand_chest.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AaroncosHandChestTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/aaroncos_hand_chest.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AaroncosHandChestTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/aaroncos_hand_chest.animation.json");
    }
}
