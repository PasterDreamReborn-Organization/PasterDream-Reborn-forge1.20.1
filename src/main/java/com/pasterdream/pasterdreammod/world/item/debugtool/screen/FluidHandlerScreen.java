package com.pasterdream.pasterdreammod.world.item.debugtool.screen;

import com.pasterdream.pasterdreammod.component.arrowbutton.DownArrowButton;
import com.pasterdream.pasterdreammod.component.arrowbutton.UpArrowButton;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerScreenWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import com.pasterdream.pasterdreammod.helper.nonshadowcenteredstring.NonShadowCenteredString;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.FluidHandlerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class FluidHandlerScreen extends AbstractContainerScreenWithFluidSlot<FluidHandlerMenu>
{
    private UpArrowButton upArrowButton;
    private DownArrowButton downArrowButton;
    private List<FluidSlot> fluidSlots;
    private int page = 0;

    public FluidHandlerScreen(FluidHandlerMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);

        fluidSlots = menu.getFluidSlots();
        for(int i = 32; i < fluidSlots.size(); i++)
        {
            fluidSlots.get(i).setActive(false);
        }
    }

    @Override
    protected void init()
    {
        imageWidth = 176;
        imageHeight = 176;
        super.init();

        upArrowButton = new UpArrowButton(leftPos + 154, topPos + 7, button -> prevPage());
        downArrowButton = new DownArrowButton(leftPos + 154, topPos + 57, button -> nextPage());
        addRenderableWidget(upArrowButton);
        addRenderableWidget(downArrowButton);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        GUIBackGroundRender.rendItemHandlerAndFluidHandlerScreen(guiGraphics, leftPos, topPos);
        GUIBackGroundRender.rendPasterDreamInventoryGUI(guiGraphics, leftPos + 3, topPos + 86);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
        NonShadowCenteredString.drawCenteredStringWithOutShadow(guiGraphics, leftPos + 162, topPos + 43, String.valueOf(page + 1), 0xFF000000);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta)
    {
        if (delta > 0)
        {
            prevPage();
        }
        else
            if (delta < 0)
            {
                nextPage();
            }
        return true;
    }

    private void prevPage()
    {
        setPageSlotsNotActive(page);
        if(page > 0)
        {
            page--;
        }
            else
            {
                page = (fluidSlots.size() + 31) / 32 - 1;
            }
        setPageSlotsActive(page);
    }

    private void nextPage()
    {
        setPageSlotsNotActive(page);
        if(page < (fluidSlots.size() + 31) / 32 - 1)
        {
            page++;
        }
            else
            {
                page = 0;
            }
        setPageSlotsActive(page);
    }

    private void setPageSlotsActive(int page)
    {
        for(int i = 32 * page + 36; i < Math.min(fluidSlots.size(), 32 * page + 32); i++)
        {
            fluidSlots.get(i).setActive(true);
        }
    }

    private void setPageSlotsNotActive(int page)
    {
        for(int i = 32 * page + 36; i < Math.min(fluidSlots.size(), 32 * page + 32); i++)
        {
            fluidSlots.get(i).setActive(false);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {

    }
}
