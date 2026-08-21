package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.world.entity.AaroncosLeftHandEntity;
import com.pasterdream.pasterdreammod.world.entity.AaroncosRightHandEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class AaroncosHandBossBar {

    /**
     * 绘制在顶部的组合 BOSS 条。通过 Forge overlay 渲染（可靠地在每帧 HUD 绘制），
     * 并根据当前活跃的原版 BOSS 条数量下移纵坐标，从而排在其它 BOSS 条下方、共用布局。
     */
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

        // 接入共享布局：原版 BOSS 条下方第一个槽位 + 本类型槽位
        int yTop = ModBossBarLayout.baseY() + ModBossBarLayout.slot(level, ModBossBarLayout.BossBarSlot.AARONCOS_HANDS) * 19;

        Minecraft.getInstance().getProfiler().push("aaroncos_hand_boss_bar");
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        int xBase = width / 2;

        if (leftHand != null && leftHand.isAlive()) {
            double percent = leftHand.getHealth() / leftHand.getMaxHealth();
            int barX = xBase - 116;
            GUIBackGroundRender.rendAaroncosHandBossBarLeftBackground(guiGraphics, barX - 4, yTop);
            GUIBackGroundRender.rendAaroncosHandBossBarLeft(guiGraphics, barX + 3, yTop + 6, percent);
        }

        if (rightHand != null && rightHand.isAlive()) {
            double percent = rightHand.getHealth() / rightHand.getMaxHealth();
            GUIBackGroundRender.rendAaroncosHandBossBarRightBackground(guiGraphics, xBase + 4, yTop);
            GUIBackGroundRender.rendAaroncosHandBossBarRight(guiGraphics, xBase + 7, yTop + 6, percent);
        }

        guiGraphics.drawCenteredString(Minecraft.getInstance().font, "§l亚伦柯斯之触", xBase, yTop - 9, -1);

        RenderSystem.disableBlend();
        Minecraft.getInstance().getProfiler().pop();
    };
}
