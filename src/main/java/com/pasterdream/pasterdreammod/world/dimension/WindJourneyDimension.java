package com.pasterdream.pasterdreammod.world.dimension;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 风之旅途维度（wind_journey_world）引用与客户端天空/雾特效。
 * 群系背景音乐由 {@code ModBiomes} 中 {@code backgroundMusic()} 配合
 * {@code SituationalMusicMixin} 和 {@code MusicManagerMixin} 统一管理，
 * 此处不再额外播放音效，避免与 MusicManager 叠加。
 */
public final class WindJourneyDimension {
    private WindJourneyDimension() {}

    public static final ResourceKey<Level> WIND_JOURNEY_WORLD = ResourceKey.create(
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("minecraft", "dimension")),
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world"));

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class DimensionSpecialEffectsHandler {
        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
            DimensionSpecialEffects customEffect = new DimensionSpecialEffects(
                    Float.NaN,                              // cloudLevel: 无云
                    true,                                   // hasGround
                    DimensionSpecialEffects.SkyType.NONE,   // 无天空（无太阳/月亮）
                    false,                                  // forceBrightLightmap
                    false                                   // constantAmbientLight
            ) {
                @Override
                public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                    return new Vec3(0.5333333333, 0.9568627451, 0.9215686275);
                }

                @Override
                public boolean isFoggyAt(int x, int y) {
                    return false;
                }
            };
            event.register(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world"), customEffect);
        }
    }
}
