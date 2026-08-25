package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.shadowdesk;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.DeskScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ShadowDeskScreen extends DeskScreen<ShadowDeskMenu>
{
    public ShadowDeskScreen(ShadowDeskMenu menu, Inventory inv, Component title)
    {
        super(menu, inv, title);
    }

    @Override
    protected void renderContainerBackground(GuiGraphics guiGraphics, int leftPos, int topPos)
    {
        GUIBackGroundRender.rendShadowDeskGUI(guiGraphics, leftPos + 71, topPos);
    }
}
