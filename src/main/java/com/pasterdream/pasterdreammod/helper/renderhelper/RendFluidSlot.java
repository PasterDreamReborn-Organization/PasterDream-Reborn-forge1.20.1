package com.pasterdream.pasterdreammod.helper.renderhelper;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.fluids.FluidStack;

public class RendFluidSlot
{
    public static void rendFluidSlot(GuiGraphics guiGraphics, FluidSlot fluidSlot, int x, int y)
    {
        FluidStack fluidStack = fluidSlot.getFluid();

        if (!fluidStack.isEmpty())
        {
            RendFluidStackTexture.rendFluidTexture(guiGraphics, x + 1, y + 1, fluidStack);

            String amountString = fluidStack.getAmount() + "mB";

            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(0.5f, 0.5f, 1f);
            guiGraphics.drawString(Minecraft.getInstance().font, amountString, (2 * x + 33) - Minecraft.getInstance().font.width(amountString), (2 * y + 34) - Minecraft.getInstance().font.lineHeight, 0xFFFFFF, true);
            guiGraphics.pose().popPose();
        }
            else
            {
                GUIBackGroundRender.rendEmptyFluidSlotBucketShape(guiGraphics, x + 1, y + 1);
            }
    }
}
