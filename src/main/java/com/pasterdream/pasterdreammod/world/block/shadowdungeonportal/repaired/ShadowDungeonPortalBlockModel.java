package com.pasterdream.pasterdreammod.world.block.shadowdungeonportal.repaired;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowDungeonPortalBlockModel extends GeoModel<ShadowDungeonPortalTileEntity> {
    @Override
    public ResourceLocation getModelResource(ShadowDungeonPortalTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_dungeon_portal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowDungeonPortalTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/shadow_dungeon_portal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShadowDungeonPortalTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_dungeon_portal.animation.json");
    }
}
