package com.pasterdream.pasterdreammod.world.block.itemcontainer;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class ItemContainerScreen<T extends ItemContainerMenu<?>> extends AbstractContainerScreen<T>
{
    public ItemContainerScreen(T menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    public abstract int setScreenWidth();
    public abstract int setScreenHeight();
    public abstract int setInventoryLeftPosition();
    public abstract int setInventoryTopPosition();

    @Override
    protected void init()
    {
        imageWidth = setScreenWidth();
        imageHeight = setScreenHeight();
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        renderContainerBackground(guiGraphics, leftPos, topPos);
        GUIBackGroundRender.rendPasterDreamInventoryGUI(guiGraphics, setInventoryLeftPosition(), setInventoryTopPosition());
    }

    protected abstract void renderContainerBackground(GuiGraphics graphics, int leftPos, int topPos);

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {

    }

}
