package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SanTank
{
    private static final int BAR_WIDTH = 28;
    private static final int BAR_HEIGHT = 26;

    public static final IGuiOverlay SAN_TANK = (gui, guiGraphics, partialTick, width, height) ->
    {
        var player = Minecraft.getInstance().player;
        if (player == null || Minecraft.getInstance().options.hideGui) return;

        player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
        {
            if (!capability.getIsSanEnabled()) return;

            double sanValue = capability.getSanValue();
            double maxSanValue = capability.getMaxSanValue();

            if (maxSanValue != 0)
            {
                if (Config.sanBarPreset == 2) {
                    renderPreset2(guiGraphics, width, height, sanValue, maxSanValue);
                } else {
                    renderPreset1(guiGraphics, width, height, sanValue, maxSanValue);
                }
            }
        });
    };

    /** 预设1：默认右下角，显示小数 + 最大值。支持仅下蹲显示bar、仅下蹲显示数值 */
    private static void renderPreset1(GuiGraphics guiGraphics, int width, int height, double sanValue, double maxSanValue)
    {
        var player = Minecraft.getInstance().player;
        boolean sneaking = player != null && player.isShiftKeyDown();

        if (Config.sanBarPreset1SneakShowBar && !sneaking) return;

        RenderSystem.enableBlend();
        int barX = width - Config.sanBarXFromRight;
        int barY = height - Config.sanBarYFromBottom;
        GUIBackGroundRender.rendSanBar(guiGraphics, barX, barY);
        GUIBackGroundRender.rendSanAmountBar(guiGraphics, barX, barY, sanValue / maxSanValue);

        if (Config.sanBarPreset1SneakPrecise && !sneaking) {
            RenderSystem.disableBlend();
            return;
        }

        String sanString = String.format("%." + Config.sanBarPreset1DecimalPlaces + "f", sanValue)
                + "/" + String.format("%.0f", maxSanValue);
        if (Minecraft.getInstance().font.width(sanString) <= 38)
        {
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, sanString, barX + 14, barY - 8, 0xFFFFFFFF);
        }
        else
        {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(0.5f, 0.5f, 1f);
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, sanString, (barX + 14) * 2, (barY - 4) * 2, 0xFFFFFF);
            guiGraphics.pose().popPose();
        }
        RenderSystem.disableBlend();
    }

    /** 预设2：紧凑模式，居中于血量与饥饿值之间，缩小比例，仅显示整数。低SAN时数字变红、bar抖动 */
    private static void renderPreset2(GuiGraphics guiGraphics, int width, int height, double sanValue, double maxSanValue)
    {
        RenderSystem.enableBlend();
        float scale = (float) Config.sanBarPreset2Scale;
        int scaledWidth = (int) (BAR_WIDTH * scale);
        int barX = width / 2 - scaledWidth / 2;
        int barY = height - 48;
        double ratio = sanValue / maxSanValue;
        boolean lowSan = ratio < Config.sanBarPreset2LowThreshold;

        int sx = (int) (barX / scale);
        int syBase = (int) (barY / scale);
        int sy = syBase;
        if (lowSan) {
            sy += (int) ((Math.random() - 0.5) * 4);
        }

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 200);
        guiGraphics.pose().scale(scale, scale, 1f);
        GUIBackGroundRender.rendSanBar(guiGraphics, sx, sy);
        GUIBackGroundRender.rendSanAmountBar(guiGraphics, sx, sy, ratio);

        String sanString = String.format("%.0f%%", ratio * 100);
        int textX = sx + BAR_WIDTH / 2;
        int textY = syBase + BAR_HEIGHT / 2 - 4;
        int color = lowSan ? 0xFFFF5555 : 0xFFFFFFFF;
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, sanString, textX + 1, textY, color);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, sanString, textX, textY, color);
        guiGraphics.pose().popPose();
        RenderSystem.disableBlend();
    }
}
