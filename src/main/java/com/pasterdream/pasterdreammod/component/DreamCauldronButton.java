package com.pasterdream.pasterdreammod.component;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class DreamCauldronButton extends Button
{
    public DreamCauldronButton(int x, int y, OnPress onPress)
    {
        super(x, y, 38, 15, Component.empty(), onPress, DEFAULT_NARRATION);
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
