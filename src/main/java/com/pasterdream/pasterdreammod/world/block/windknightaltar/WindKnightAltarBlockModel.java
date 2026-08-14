package com.pasterdream.pasterdreammod.world.block.windknightaltar;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WindKnightAltarBlockModel extends GeoModel<WindKnightAltarBlockEntity> {
    private static final String[] STAGE_NAMES = {
            "wind_knight_altar",
            "wind_knight_altar_torso",
            "wind_knight_altar_arms",
            "wind_knight_altar_head",
            "wind_knight_altar_complete"
    };

    private String stageName(WindKnightAltarBlockEntity animatable) {
        int stage = animatable.getBlockState().getValue(WindKnightAltarBlock.STAGE);
        return STAGE_NAMES[stage];
    }

    @Override
    public ResourceLocation getModelResource(WindKnightAltarBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/" + stageName(animatable) + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WindKnightAltarBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/block/wind_knight_altar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WindKnightAltarBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/" + stageName(animatable) + ".animation.json");
    }
}
