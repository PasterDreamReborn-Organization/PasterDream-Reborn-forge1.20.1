package com.pasterdream.pasterdreammod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    private void setBlackRenderSky(PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable setupFog, CallbackInfo ci)
    {
        if (Minecraft.getInstance().level == null)
        {
            return;
        }

        if (Minecraft.getInstance().level.dimension().location().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world")))
        {
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder builder = tesselator.getBuilder();
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            builder.vertex(poseStack.last().pose(), -1, -1, 0).color(0f, 0f, 0f, 1f).endVertex();
            builder.vertex(poseStack.last().pose(),  1, -1, 0).color(0f, 0f, 0f, 1f).endVertex();
            builder.vertex(poseStack.last().pose(),  1,  1, 0).color(0f, 0f, 0f, 1f).endVertex();
            builder.vertex(poseStack.last().pose(), -1,  1, 0).color(0f, 0f, 0f, 1f).endVertex();
            tesselator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            ci.cancel();
        }
    }

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    private void setCyanRenderSky(PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable setupFog, CallbackInfo ci)
    {
        if (Minecraft.getInstance().level == null)
        {
            return;
        }

        if (Minecraft.getInstance().level.dimension().location().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world")))
        {
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder builder = tesselator.getBuilder();
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            builder.vertex(poseStack.last().pose(), -1, -1, 0).color(0.5333333333f, 0.9568627451f, 0.9215686275f, 1f).endVertex();
            builder.vertex(poseStack.last().pose(),  1, -1, 0).color(0.5333333333f, 0.9568627451f, 0.9215686275f, 1f).endVertex();
            builder.vertex(poseStack.last().pose(),  1,  1, 0).color(0.5333333333f, 0.9568627451f, 0.9215686275f, 1f).endVertex();
            builder.vertex(poseStack.last().pose(), -1,  1, 0).color(0.5333333333f, 0.9568627451f, 0.9215686275f, 1f).endVertex();
            tesselator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            ci.cancel();
        }
    }

    @Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
    private void pasterdream$cancelWindJourneyClouds(PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ, CallbackInfo ci)
    {
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.dimension().location().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world")))
        {
            ci.cancel();
        }
    }
}
