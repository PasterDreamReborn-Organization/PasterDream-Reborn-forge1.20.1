package com.pasterdream.pasterdreammod.world.dimension;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public final class LampShadowWorldDimension {
    private LampShadowWorldDimension() {}

    public static final ResourceKey<Level> LAMP_SHADOW_WORLD = ResourceKey.create(
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("minecraft", "dimension")),
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class DimensionSpecialEffectsHandler {
        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
            DimensionSpecialEffects customEffect = new DimensionSpecialEffects(
                    Float.NaN,                          // cloudLevel: 无云
                    true,                               // hasGround
                    DimensionSpecialEffects.SkyType.NONE, // 无天空
                    false,                              // forceBrightLightmap
                    true                                // constantAmbientLight
            ) {
                @Override
                public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                    return new Vec3(0.5215686275, 0.5215686275, 0.5215686275);
                }

                @Override
                public boolean isFoggyAt(int x, int y) {
                    return false;
                }

                // Forge 语义：返回 true = 取消（跳过）原版雨雪渲染，返回 false = 正常渲染。
                // 灯影之下需要下雨，故这里必须返回 false（用黑色雨纹理由 LevelRendererMixin 处理）。
                @Override
                public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick,
                                                  net.minecraft.client.renderer.LightTexture lightTexture,
                                                  double camX, double camY, double camZ) {
                    return false;
                }

                @Override
                public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
                    return false;
                }
            };
            event.register(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"), customEffect);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        // TODO: 待搬运原作的进入/离开维度流程后实装
        // Entity entity = event.getEntity();
        // if (event.getFrom() == LAMP_SHADOW_WORLD) {
        //     LampShadowPr1Procedure.execute(entity);
        // }
        // if (event.getTo() == LAMP_SHADOW_WORLD) {
        //     LampShadowPr0Procedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
        // }
    }
}
