package com.pasterdream.pasterdreammod.world.item.storagebagitem;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class LargeStorageBagScreen extends AbstractContainerScreen<LargeStorageBagMenu> {

    public LargeStorageBagScreen(LargeStorageBagMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 210;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        // 大储物袋贴图 128×110 居中于 176 宽屏幕内
        GUIBackGroundRender.rendBigStorageBagGUI(guiGraphics, x + 24, y);
        // 玩家库存背景 170×84
        GUIBackGroundRender.rendPasterDreamInventoryGUI(guiGraphics, x, y + 124);
    }
}
