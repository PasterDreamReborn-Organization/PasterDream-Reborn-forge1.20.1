package com.pasterdream.pasterdreammod.world.item.debugtool.widget;

import com.pasterdream.pasterdreammod.helper.nonshadowcenteredstring.NonShadowString;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.List;

public class NBTPreviewWidget extends AbstractScrollWidget
{
    private List<String> stringList;
    private final int lineHeight = Minecraft.getInstance().font.lineHeight + 1;

    public NBTPreviewWidget(int x, int y, int width, int height, List<String> stringList)
    {
        super(x, y, Math.max(6, width), Math.max(9, height), Component.literal(""));

        this.width = Math.max(6, width);
        this.height = Math.max(9, height);
        this.stringList = stringList;
    }

    @Override
    protected int getInnerHeight()
    {
        return Math.max(lineHeight, stringList.size() * lineHeight);
    }

    @Override
    protected double scrollRate()
    {
        return lineHeight;
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        if(!stringList.isEmpty())
        {
            for (int i = 0; i < stringList.size(); i++)
            {
                guiGraphics.drawString(Minecraft.getInstance().font, stringList.get(i), getX() + 2, getY() + 2 + lineHeight * i, 0xFFFFFFFF, false);
            }
        }
            else
            {
                NonShadowString.drawCenteredStringWithOutShadow(guiGraphics, width / 2, height / 2, "无NBT", 0xFF000000);
            }
    }

    @Override
    protected void renderDecorations(GuiGraphics guiGraphics)
    {
        int sliderHeight = this.getScrollBarHeight();
        int sliderStartX = this.getX() + this.width - 6;
        int sliderFinalX = this.getX() + this.width - 1;
        int sliderStartY;
        int sliderFinalY;

        if(sliderHeight >= height)
        {
            sliderHeight = height;
            sliderStartY = this.getY();
        }
            else
            {
                sliderStartY = Math.max(this.getY(), (int)this.scrollAmount * (this.height - sliderHeight) / this.getMaxScrollAmount() + this.getY());
            }

        sliderFinalY = sliderStartY + sliderHeight;

        guiGraphics.fill(sliderStartX, sliderStartY, sliderFinalX, sliderFinalY, -8355712);
        guiGraphics.fill(sliderStartX + 1, sliderStartY + 1, sliderFinalX - 1, sliderFinalY - 1, -4144960);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (!this.visible)
        {
            return false;
        }

        boolean onScrollbar = mouseX >= getX() + width - 6 && mouseX < getX() + width - 1 && mouseY >= getY() && mouseY < getY() + height;
        if (onScrollbar && button == 0)
        {
            scrolling = true;
            return true;
        }
        return onScrollbar;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY)
    {
        if (visible && isFocused() && scrolling)
        {
            if (mouseY < getY())
            {
                setScrollAmount(0);
            }
            else
                if (mouseY > getY() + height)
                {
                    setScrollAmount(getMaxScrollAmount());
                }
                    else
                    {
                        int barHeight = getScrollBarHeight();
                        double rate = (height == barHeight ? 1 : Math.max(1, getMaxScrollAmount() / (height - barHeight)));
                        setScrollAmount(scrollAmount + dragY * rate);
                    }
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button)
    {
        if (button == 0)
        {
            scrolling = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput)
    {

    }

    public void setListString(List<String> stringList)
    {
        scrollAmount = 0;
        this.stringList = stringList;
    }
}
