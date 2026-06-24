package com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.picnicbasket;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.CrateScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class PicnicBasketScreen extends CrateScreen<PicnicBasketMenu>
{
    public PicnicBasketScreen(PicnicBasketMenu menu, Inventory inv, Component title)
    {
        super(menu, inv, title);
    }

    @Override
    protected void renderContainerBackground(GuiGraphics graphics, int leftPos, int topPos)
    {
        GUIBackGroundRender.rendPicnicBasketGUI(graphics, leftPos + 32, topPos + 12);
    }
}
