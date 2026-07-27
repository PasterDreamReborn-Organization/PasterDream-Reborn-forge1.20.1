package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.client.model.Modelslime;
import com.pasterdream.pasterdreammod.client.renderer.*;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ModEntityRenderer {
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.TERRASWORD_WAVE.get(), TerraswordWaveRenderer::new);
        event.registerEntityRenderer(ModEntities.FOX_FIRE.get(), FoxFireRenderer::new);
        event.registerEntityRenderer(ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get(), MeltDreamCrystalEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.PINK_CHICKEN.get(), PinkChickenRenderer::new);
        event.registerEntityRenderer(ModEntities.PINK_SLIME.get(), PinkSlimeRenderer::new);
        event.registerEntityRenderer(ModEntities.GOLDEN_FOX.get(), GoldenFoxRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_GOLEM.get(), ShadowGolemRenderer::new);
        event.registerEntityRenderer(ModEntities.TERRORBEAK.get(), TerrorbeakRenderer::new);
        event.registerEntityRenderer(ModEntities.CRAZY_TERRORBEAK.get(), CrazyTerrorbeakRenderer::new);
        event.registerEntityRenderer(ModEntities.WEAKENESS_TERRORBEAK.get(), WeakenessTerrorbeakRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_HAND.get(), ShadowHandRenderer::new);
        event.registerEntityRenderer(ModEntities.THROWN_PINK_EGG.get(), ThrownItemRenderer::new);
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(Modelslime.LAYER_LOCATION, Modelslime::createBodyLayer);
    }
}
