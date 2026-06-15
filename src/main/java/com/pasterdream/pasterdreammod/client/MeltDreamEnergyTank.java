package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.capability.ModCapabilities;
import com.pasterdream.pasterdreammod.helper.RenderHelper.GUIBackGroundRender;
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
            double energy = capability.getMeltDreamEnergy();
            RenderSystem.enableBlend();

            GUIBackGroundRender.rendMeltDreamEnergyBar(guiGraphics, 9, height - 17);
            GUIBackGroundRender.rendMeltDreamEnergyAmountBar(guiGraphics, 12, height - 13, energy / 100.0);
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, String.valueOf(energy), 46, height - 25, 0xFFFFFFFF);

            RenderSystem.disableBlend();
        });
    };
}
