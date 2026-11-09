package com.pasterdream.pasterdreammod.helper.nonshadowcenteredstring;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class NonShadowString
{
    public static void drawCenteredStringWithOutShadow(GuiGraphics guiGraphics, int x, int y, String string, int color)
    {
        int width = Minecraft.getInstance().font.width(string);
        int height = Minecraft.getInstance().font.lineHeight;
        guiGraphics.drawString(Minecraft.getInstance().font, string, x - width / 2, y - height / 2, color, false);
    }

    public static void drawScrollingStringWithOutShadow(GuiGraphics guiGraphics, int x, int y, int widthLimit, int speed, String string, int color, long timer)
    {
        int currentStringWidth = Minecraft.getInstance().font.width(string);
        if(currentStringWidth >= widthLimit)
        {
            double period = (double)(2 * currentStringWidth + widthLimit) / speed; //周期长度
            double timer0 = (timer / 1000d) % period; //每周期中的时刻0
            double timer1 = (timer / 1000d + period / 2) % period; //每周期中的时刻1
            int phase0 = (int)(speed * timer0); //相位0
            int phase1 = (int)(speed * timer1); //相位1

            guiGraphics.enableScissor(x - widthLimit / 2, y - 5, x + widthLimit / 2, y + 5);
            drawCenteredStringWithOutShadow(guiGraphics, x + widthLimit / 2 + currentStringWidth / 2 - phase0, y, string, color);
            drawCenteredStringWithOutShadow(guiGraphics, x + widthLimit / 2 + currentStringWidth / 2 - phase1, y, string, color);
            guiGraphics.disableScissor();
        }
            else
            {
                drawCenteredStringWithOutShadow(guiGraphics, x, y, string, color);
            }
    }

    public static void drawTrimmedStringWithOutShadow(GuiGraphics guiGraphics, int x, int y, int widthLimit, String string, int color)
    {
        guiGraphics.enableScissor(x - widthLimit / 2, y - 5, x + widthLimit / 2, y + 5);
        guiGraphics.drawString(Minecraft.getInstance().font, string, x - widthLimit / 2, y - 4, color, false);
        guiGraphics.disableScissor();
    }
}
