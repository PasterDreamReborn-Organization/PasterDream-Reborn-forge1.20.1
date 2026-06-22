package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
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
            double maxSanValue = capability.getMaxSanValue();

            if (maxSanValue != 0)
            {
                RenderSystem.enableBlend();
                GUIBackGroundRender.rendSanBar(guiGraphics, width - 34, height - 30);
                GUIBackGroundRender.rendSanAmountBar(guiGraphics, width - 34, height - 30, sanValue / maxSanValue);

                String sanString = sanValue + "/" + maxSanValue;
                if(Minecraft.getInstance().font.width(sanString) <= 38)
                {
                    guiGraphics.drawCenteredString(Minecraft.getInstance().font, sanString, width - 20, height - 38, 0xFFFFFFFF);
                }
                    else
                    {
                        guiGraphics.pose().pushPose();
                        guiGraphics.pose().scale(0.5f, 0.5f, 1f);
                        guiGraphics.drawCenteredString(Minecraft.getInstance().font, sanString, 2 * width - 40, (2 * height - 68), 0xFFFFFF);
                        guiGraphics.pose().popPose();
                    }
                RenderSystem.disableBlend();
            }
        });
    };
}
