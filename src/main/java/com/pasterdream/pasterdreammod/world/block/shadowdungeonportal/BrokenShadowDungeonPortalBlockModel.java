package com.pasterdream.pasterdreammod.world.block.shadowdungeonportal;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BrokenShadowDungeonPortalBlockModel extends GeoModel<BrokenShadowDungeonPortalTileEntity> {
    @Override
    public ResourceLocation getModelResource(BrokenShadowDungeonPortalTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/broken_shadow_dungeon_portal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BrokenShadowDungeonPortalTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/shadow_dungeon_portal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BrokenShadowDungeonPortalTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/broken_shadow_dungeon_portal.animation.json");
    }
}
