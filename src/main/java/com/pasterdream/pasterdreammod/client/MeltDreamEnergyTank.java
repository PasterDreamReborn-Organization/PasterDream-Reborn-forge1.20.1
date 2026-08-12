package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class MeltDreamEnergyTank
{
    private static final int BAR_WIDTH = 72;
    private static final int BAR_HEIGHT = 12;
    private static final int CHANGE_DISPLAY_TICKS = 120;

    private static double lastValue = -1;
    private static int changeTimer = 0;

    public static final IGuiOverlay MELT_DREAM_ENERGY_TANK = (gui, guiGraphics, partialTick, width, height) ->
    {
        var player = Minecraft.getInstance().player;
        if (player == null || Minecraft.getInstance().options.hideGui) return;

        player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
        {
            double value = capability.getMeltDreamEnergy();
            double max = capability.getMaxMeltDreamEnergy();
            if (max == 0) return;

            if (Math.abs(value - lastValue) > 0.001) {
                lastValue = value;
                changeTimer = CHANGE_DISPLAY_TICKS;
            } else if (changeTimer > 0) {
                changeTimer--;
            }

            boolean sneaking = player.isShiftKeyDown();
            boolean visible = Config.meltDreamEnergyBarAlwaysShow
                    || (Config.meltDreamEnergyBarShowOnChange && changeTimer > 0)
                    || (Config.meltDreamEnergyBarShowOnSneak && sneaking);

            if (!visible) return;

            RenderSystem.enableBlend();
            int barX = Config.meltDreamEnergyBarX;
            int barY = height - Config.meltDreamEnergyBarYFromBottom;
            GUIBackGroundRender.rendMeltDreamEnergyBar(guiGraphics, barX, barY);
            GUIBackGroundRender.rendMeltDreamEnergyAmountBar(guiGraphics, barX + 3, barY + 4, value / max);

            String text = String.format("%." + Config.meltDreamEnergyBarDecimalPlaces + "f", value)
                    + "/" + String.format("%.0f", max);
            int textX = barX + BAR_WIDTH / 2;
            int textY = Config.meltDreamEnergyBarTextCentered
                    ? barY + (BAR_HEIGHT - Minecraft.getInstance().font.lineHeight) / 2
                    : barY - 8;

            if (Minecraft.getInstance().font.width(text) <= 90)
            {
                guiGraphics.drawCenteredString(Minecraft.getInstance().font, text, textX, textY, 0xFFFFFFFF);
            }
            else
            {
                guiGraphics.pose().pushPose();
                guiGraphics.pose().scale(0.5f, 0.5f, 1f);
                int scaledTextX = textX * 2;
                int scaledTextY = Config.meltDreamEnergyBarTextCentered
                        ? (barY + 2) * 2
                        : (barY - 4) * 2;
                guiGraphics.drawCenteredString(Minecraft.getInstance().font, text, scaledTextX, scaledTextY, 0xFFFFFF);
                guiGraphics.pose().popPose();
            }
            RenderSystem.disableBlend();
        });
    };
}
