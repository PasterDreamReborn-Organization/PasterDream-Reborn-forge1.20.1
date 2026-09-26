package com.pasterdream.pasterdreammod.world.item.debugtool.screen;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.NbtSaveHandler;
import com.pasterdream.pasterdreammod.world.item.debugtool.widget.NBTEditorWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DebugToolNBTEditorScreen extends Screen
{
    private final Screen previousScreen;
    private final String originalText;
    private final NbtSaveHandler onSave;
    private NBTEditorWidget nbtEditorWidget;

    private Component errorMessage = null;
    private long errorShowUntil = 0;

    protected DebugToolNBTEditorScreen(Screen previousScreen, String originalText, NbtSaveHandler onSave)
    {
        super(Component.literal(""));
        this.previousScreen = previousScreen;
        this.originalText = originalText;
        this.onSave = onSave;
    }

    @Override
    protected void init()
    {
        super.init();

        nbtEditorWidget = new NBTEditorWidget(5, 5, width - 10, height - 26, originalText, onSave);
        addRenderableWidget(nbtEditorWidget);

        Button confirmButton = Button.builder(Component.translatable("button.pasterdream.确认"), button ->
        {
            String text = nbtEditorWidget.getString();
            Component error = onSave.trySave(text);
            if (error == null)
            {
                Minecraft.getInstance().setScreen(previousScreen);
            }
                else
                {
                    this.errorMessage = error;
                    this.errorShowUntil = System.currentTimeMillis() + 5000;
                }
        }).pos(5, height - 21).size(width / 2 - 6, 16).build();
        addRenderableWidget(confirmButton);

        Button cancelButton = Button.builder(Component.translatable("button.pasterdream.取消"), button ->
        {
            onClose();
        }).pos(width / 2 + 1, height - 21).size(width / 2 - 6, 16).build();
        addRenderableWidget(cancelButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        GUIBackGroundRender.rendMinecraftGUIBackground(guiGraphics, 0, 0, width, height);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        if (errorMessage != null)
        {
            if(System.currentTimeMillis() < errorShowUntil)
            {
                int textWidth = Minecraft.getInstance().font.width(errorMessage);
                guiGraphics.drawString(Minecraft.getInstance().font, errorMessage, (width - textWidth) / 2, height - 42, 0xFFFF0000, true);
            }
                else
                {
                    errorMessage = null;
                }
        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY)
    {
        if (nbtEditorWidget != null && button == 0 && nbtEditorWidget.mouseDragged(mouseX, mouseY, button, dragX, dragY))
        {
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public void onClose()
    {
        Minecraft.getInstance().setScreen(previousScreen);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
