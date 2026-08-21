package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.world.entity.WindKnightEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class WindKnightBossBar {

    /**
     * 破风骑士自定义 BOSS 条。材质为两张带外框的整图（空/满），
     * 空图垫底保证外框完整，满图按血量百分比裁剪宽度叠加。
     * 布局与原版 BOSS 条一致，排在其下方。
     */
    public static final IGuiOverlay OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {
        var player = Minecraft.getInstance().player;
        var level = Minecraft.getInstance().level;
        if (player == null || level == null || Minecraft.getInstance().options.hideGui)
            return;

        WindKnightEntity knight = null;
        for (var entity : level.entitiesForRendering()) {
            if (entity instanceof WindKnightEntity windKnight) {
                knight = windKnight;
                break;
            }
        }

        if (knight == null || !knight.isAlive())
            return;

        // 接入共享布局：原版 BOSS 条下方第一个槽位 + 本类型槽位
        int yTop = ModBossBarLayout.baseY() + ModBossBarLayout.slot(level, ModBossBarLayout.BossBarSlot.WIND_KNIGHT) * 19;

        Minecraft.getInstance().getProfiler().push("wind_knight_boss_bar");
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        double percent = knight.getHealth() / knight.getMaxHealth();
        int x = width / 2 - 98;

        GUIBackGroundRender.rendWindKnightBossBarEmpty(guiGraphics, x, yTop);
        GUIBackGroundRender.rendWindKnightBossBarFull(guiGraphics, x, yTop, percent);

        guiGraphics.drawCenteredString(Minecraft.getInstance().font, "§l破风骑士", width / 2, yTop - 9, -1);

        RenderSystem.disableBlend();
        Minecraft.getInstance().getProfiler().pop();
    };
}