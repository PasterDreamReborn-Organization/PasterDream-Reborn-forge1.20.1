package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.helper.RenderHelper.GUIBackGroundRender;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SanTank
{
    public static final IGuiOverlay SAN_TANK = (gui, guiGraphics, partialTick, width, height) ->
    {
        if (Minecraft.getInstance().player == null || Minecraft.getInstance().options.hideGui)
        {
            return;
        }

        Minecraft.getInstance().player.getCapability(ModCapabilities.SAN).ifPresent(capability ->
        {
            double sanValue = capability.getSanValue();
            RenderSystem.enableBlend();

            GUIBackGroundRender.rendSanBar(guiGraphics, width - 34, height - 30);
            GUIBackGroundRender.rendSanAmountBar(guiGraphics, width - 34, height - 30, sanValue / 100.0);
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, String.valueOf(sanValue), width - 20, height - 38, 0xFFFFFFFF);

            RenderSystem.disableBlend();
        });
    };
}
