package com.pasterdream.pasterdreammod.component;

import com.pasterdream.pasterdreammod.helper.nonshadowcenteredstring.NonShadowString;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookInfo;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookScreen;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class BookNameListButton extends Button
{
    private DreamNotesBookInfo dreamNotesBookInfo;
    private boolean unlocked;
    private Screen previousScreen;
    private boolean justNowNotHovered = true;
    private long timerDelay = 0;

    public BookNameListButton(int x, int y, DreamNotesBookInfo dreamNotesBookInfo, boolean isUnlocked, Screen previousScreen)
    {
        super(x, y, 100, 10, Component.empty(), BookNameListButton::onButtonPress, DEFAULT_NARRATION);
        this.dreamNotesBookInfo = dreamNotesBookInfo;
        this.unlocked = isUnlocked;
        this.previousScreen = previousScreen;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        if(dreamNotesBookInfo != null)
        {
            if(isHovered)
            {
                guiGraphics.fill(getX(), getY(), getX() + 100, getY() + 10, 0x3f000000);

                String content = unlocked ? dreamNotesBookInfo.title().getString() : Component.translatable("button.pasterdream.此笔记尚未解锁").getString();
                int contentLength = Minecraft.getInstance().font.width(content);

                if(contentLength > 100)
                {
                    if(justNowNotHovered)
                    {
                        justNowNotHovered = false;

                        double targetT = 2;  //理论的开始时间相位

                        double period = (double)(2 * contentLength + 100) / 50; //周期长度
                        double timer = (Util.getMillis() / 1000d) % period; //当前周期中的时刻

                        timerDelay = (long)((targetT - timer) * 1000);
                    }
                    NonShadowString.drawScrollingStringWithOutShadow(guiGraphics, getX() + 50, getY() + 5, 100, 50, content, unlocked ? dreamNotesBookInfo.color() : 0x7F000000, Util.getMillis() + timerDelay);
                }
                    else
                    {
                        NonShadowString.drawTrimmedStringWithOutShadow(guiGraphics, getX() + 50, getY() + 5, 100, unlocked ? dreamNotesBookInfo.title().getString() : Component.translatable("button.pasterdream.此笔记尚未解锁").getString(), unlocked ? dreamNotesBookInfo.color() : 0x7F000000);
                    }
            }
                else
                {
                    justNowNotHovered = true;
                    NonShadowString.drawTrimmedStringWithOutShadow(guiGraphics, getX() + 50, getY() + 5, 100, unlocked ? dreamNotesBookInfo.title().getString() : Component.translatable("button.pasterdream.此笔记尚未解锁").getString(), unlocked ? dreamNotesBookInfo.color() : 0x7F000000);
                }
        }
    }

    private static void onButtonPress(Button button)
    {
        ((BookNameListButton) button).handlePress();
    }

    private void handlePress()
    {
        if (this.unlocked && this.dreamNotesBookInfo != null)
        {
            Minecraft.getInstance().setScreen(new DreamNotesBookScreen(this.dreamNotesBookInfo, previousScreen));
        }
    }

    public void setInfo(DreamNotesBookInfo info, boolean isUnlocked)
    {
        this.dreamNotesBookInfo = info;
        this.unlocked = isUnlocked;
        this.active = isUnlocked;
    }
}
