package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class MeltDreamEnergyTank
{
    public static final IGuiOverlay MELT_DREAM_ENERGY_TANK = (gui, guiGraphics, partialTick, width, height) ->
    {
        if (Minecraft.getInstance().player == null || Minecraft.getInstance().options.hideGui)
        {
            return;
        }

        Minecraft.getInstance().player.getCapability(ModCapabilities.MELT_DREAM_ENERGY).ifPresent(capability ->
        {
            double meltDreamEnergy = capability.getMeltDreamEnergy();
            double maxMeltDreamEnergy = capability.getMaxMeltDreamEnergy();

            if (maxMeltDreamEnergy != 0)
            {
                RenderSystem.enableBlend();
                GUIBackGroundRender.rendMeltDreamEnergyBar(guiGraphics, 9, height - 17);
                GUIBackGroundRender.rendMeltDreamEnergyAmountBar(guiGraphics, 12, height - 13, meltDreamEnergy / maxMeltDreamEnergy);

                String meltDreamEnergyString = meltDreamEnergy + "/" + maxMeltDreamEnergy;
                if(Minecraft.getInstance().font.width(meltDreamEnergyString) <= 90)
                {
                    guiGraphics.drawCenteredString(Minecraft.getInstance().font, meltDreamEnergyString, 46, height - 25, 0xFFFFFFFF);
                }
                    else
                    {
                        guiGraphics.pose().pushPose();
                        guiGraphics.pose().scale(0.5f, 0.5f, 1f);
                        guiGraphics.drawCenteredString(Minecraft.getInstance().font, meltDreamEnergyString, 92, (2 * height - 42), 0xFFFFFF);
                        guiGraphics.pose().popPose();
                    }
                RenderSystem.disableBlend();
            }
        });
    };
}
