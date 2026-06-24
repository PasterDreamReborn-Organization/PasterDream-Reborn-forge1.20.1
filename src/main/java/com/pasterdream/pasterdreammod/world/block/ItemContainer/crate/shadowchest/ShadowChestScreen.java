package com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.shadowchest;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.world.block.ItemContainer.crate.CrateScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ShadowChestScreen extends CrateScreen<ShadowChestMenu>
{
    public ShadowChestScreen(ShadowChestMenu menu, Inventory inv, Component title)
    {
        super(menu, inv, title);
    }

    @Override
    protected void renderContainerBackground(GuiGraphics graphics, int leftPos, int topPos)
    {
        GUIBackGroundRender.rendShadowChestGUI(graphics, leftPos + 36, topPos + 14);
    }
}
