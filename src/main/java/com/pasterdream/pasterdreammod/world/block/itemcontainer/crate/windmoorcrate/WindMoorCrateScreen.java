package com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.windmoorcrate;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.CrateScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WindMoorCrateScreen extends CrateScreen<WindMoorCrateMenu>
{
    public WindMoorCrateScreen(WindMoorCrateMenu menu, Inventory inv, Component title)
    {
        super(menu, inv, title);
    }

    @Override
    protected void renderContainerBackground(GuiGraphics guiGraphics, int leftPos, int topPos)
    {
        GUIBackGroundRender.rendWindMoorCrateGUI(guiGraphics, leftPos + 27, topPos);
    }
}
