package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.world.entity.AaroncosLeftHandEntity;
import com.pasterdream.pasterdreammod.world.entity.AaroncosRightHandEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class AaroncosHandBossBar {
    public static final IGuiOverlay OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {
        var player = Minecraft.getInstance().player;
        var level = Minecraft.getInstance().level;
        if (player == null || level == null || Minecraft.getInstance().options.hideGui)
            return;

        // Find hands directly from the client-side entity list (health synced automatically)
        AaroncosLeftHandEntity leftHand = null;
        AaroncosRightHandEntity rightHand = null;
        for (var entity : level.entitiesForRendering()) {
            if (leftHand == null && entity instanceof AaroncosLeftHandEntity left) {
                leftHand = left;
            } else if (rightHand == null && entity instanceof AaroncosRightHandEntity right) {
                rightHand = right;
            }
            if (leftHand != null && rightHand != null)
                break;
        }

        if (leftHand == null && rightHand == null)
            return;

        Minecraft.getInstance().getProfiler().push("aaroncos_hand_boss_bar");
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        int xBase = width / 2;

        if (leftHand != null && leftHand.isAlive()) {
            double percent = leftHand.getHealth() / leftHand.getMaxHealth();
            int barX = xBase - 116;
            GUIBackGroundRender.rendAaroncosHandBossBarLeftBackground(guiGraphics, barX - 4, 12);
            GUIBackGroundRender.rendAaroncosHandBossBarLeft(guiGraphics, barX + 3, 18, percent);
        }

        if (rightHand != null && rightHand.isAlive()) {
            double percent = rightHand.getHealth() / rightHand.getMaxHealth();
            GUIBackGroundRender.rendAaroncosHandBossBarRightBackground(guiGraphics, xBase + 4, 12);
            GUIBackGroundRender.rendAaroncosHandBossBarRight(guiGraphics, xBase + 7, 18, percent);
        }

        guiGraphics.drawCenteredString(Minecraft.getInstance().font, "§l亚伦柯斯之触", xBase, 3, -1);

        RenderSystem.disableBlend();
        Minecraft.getInstance().getProfiler().pop();
    };
}
