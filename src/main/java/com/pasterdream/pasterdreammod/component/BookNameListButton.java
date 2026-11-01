package com.pasterdream.pasterdreammod.component;

import com.pasterdream.pasterdreammod.helper.nonshadowcenteredstring.NonShadowCenteredString;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookInfo;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookScreen;
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
        if(isHovered() && dreamNotesBookInfo != null)
        {
            guiGraphics.fill(getX(), getY(), getX() + 100, getY() + 10, 0x3f000000);
        }

        if (dreamNotesBookInfo != null)
        {
            NonShadowCenteredString.drawCenteredStringWithOutShadow(guiGraphics, getX() + 50, getY() + 5, unlocked ? dreamNotesBookInfo.title().getString() : Component.translatable("button.pasterdream.此笔记尚未解锁").getString(), unlocked ? dreamNotesBookInfo.color() : 0x7F000000);
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
