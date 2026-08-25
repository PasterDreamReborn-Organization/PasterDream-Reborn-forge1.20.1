package com.pasterdream.pasterdreammod.world.block.itemcontainer.openedmeltdreamcrystalchest;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.ItemContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class OpenedMeltDreamCrystalChestScreen extends ItemContainerScreen<OpenedMeltDreamCrystalChestMenu>
{
    public OpenedMeltDreamCrystalChestScreen(OpenedMeltDreamCrystalChestMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    public int setScreenWidth()
    {
        return 170;
    }

    public int setScreenHeight()
    {
        return 170;
    }

    public int setInventoryLeftPosition()
    {
        return leftPos;
    }

    public int setInventoryTopPosition()
    {
        return topPos + 86;
    }

    @Override
    protected void renderContainerBackground(GuiGraphics guiGraphics, int leftPos, int topPos)
    {
        GUIBackGroundRender.rendMeltDreamCrystalChestGUI(guiGraphics, leftPos + 51, topPos);
    }
}
