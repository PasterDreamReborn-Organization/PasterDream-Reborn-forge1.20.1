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
        event.registerEntityRenderer(ModEntities.FIREFLY.get(), FireflyRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_GOLEM.get(), ShadowGolemRenderer::new);
        event.registerEntityRenderer(ModEntities.TERRORBEAK.get(), TerrorbeakRenderer::new);
        event.registerEntityRenderer(ModEntities.CRAZY_TERRORBEAK.get(), TerrorbeakRenderer::new);
        event.registerEntityRenderer(ModEntities.WEAKENESS_TERRORBEAK.get(), TerrorbeakRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_HAND.get(), ShadowHandRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_GHOST.get(), ShadowGhostRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_SQUEAL_GHOST.get(), ShadowGhostRenderer::new);
        event.registerEntityRenderer(ModEntities.WAILING_SHADOW_GHOST.get(), ShadowGhostRenderer::new);
        event.registerEntityRenderer(ModEntities.FRIENDLY_SHADOW_GHOST.get(), ShadowGhostRenderer::new);
        event.registerEntityRenderer(ModEntities.SQUEAL_WAVE_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.THROWN_PINK_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.THROWN_POTION_BOTTLE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.REJUVENATION_BOTTLE_ENTITY.get(), EmptyEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.PEBBLE_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.BLACK_BEETLE.get(), BlackBeetleRenderer::new);
        event.registerEntityRenderer(ModEntities.BLACK_BEETLE_MOTHER.get(), BlackBeetleMotherRenderer::new);
        event.registerEntityRenderer(ModEntities.WHITE_SWORD_RAIN_PROJECTILE.get(), EmptyEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.SHAKING_CRYSTAL.get(), ShakingCrystalRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_MAGICBALL.get(), ShadowMagicballRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_TUNE_TOTEM.get(), ShadowTuneTotemRenderer::new);
        event.registerEntityRenderer(ModEntities.AARONCOS_LEFT_HAND.get(), AaroncosLeftHandRenderer::new);
        event.registerEntityRenderer(ModEntities.AARONCOS_RIGHT_HAND.get(), AaroncosRightHandRenderer::new);
        event.registerEntityRenderer(ModEntities.WIND_KNIGHT.get(), WindKnightRenderer::new);
        event.registerEntityRenderer(ModEntities.THUNDERCLOUD.get(), ThundercloudRenderer::new);
        event.registerEntityRenderer(ModEntities.HIGHVOLTAGE_THUNDERCLOUD.get(), HighvoltageThundercloudRenderer::new);
        event.registerEntityRenderer(ModEntities.LIGHTNING_PROJECTILE.get(), EmptyEntityRenderer::new);
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(Modelslime.LAYER_LOCATION, Modelslime::createBodyLayer);
    }
}
