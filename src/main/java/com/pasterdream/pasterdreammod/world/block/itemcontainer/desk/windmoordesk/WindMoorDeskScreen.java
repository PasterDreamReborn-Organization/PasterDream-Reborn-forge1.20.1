package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.windmoordesk;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.DeskScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WindMoorDeskScreen extends DeskScreen<WindMoorDeskMenu>
{
    public WindMoorDeskScreen(WindMoorDeskMenu menu, Inventory inv, Component title)
    {
        super(menu, inv, title);
    }

    @Override
    protected void renderContainerBackground(GuiGraphics guiGraphics, int leftPos, int topPos)
    {
        GUIBackGroundRender.rendWindMoorDeskGUI(guiGraphics, leftPos + 71, topPos);
    }
}
