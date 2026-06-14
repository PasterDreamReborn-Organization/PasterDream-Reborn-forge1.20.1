package com.pasterdream.pasterdreammod.component;

import com.pasterdream.pasterdreammod.helper.RenderHelper.GUIBackGroundRender;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;

public class DreamCauldronButton extends Button
{
    public DreamCauldronButton(Builder builder)
    {
        super(builder);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        if(isHovered())
        {
            GUIBackGroundRender.rendDreamCauldronButtonHoveredGUI(guiGraphics, getX(), getY());
        }
            else
            {
                GUIBackGroundRender.rendDreamCauldronButtonGUI(guiGraphics, getX(), getY());
            }
    }
}
