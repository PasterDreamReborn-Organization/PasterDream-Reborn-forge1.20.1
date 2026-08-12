package com.pasterdream.pasterdreammod.world.block.shadowblastfurnace;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerScreenWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ShadowBlastFurnaceScreen extends AbstractContainerScreenWithFluidSlot<ShadowBlastFurnaceMenu>
{
    public ShadowBlastFurnaceScreen(ShadowBlastFurnaceMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
        this.imageWidth = 170;
        this.imageHeight = 196;
    }

    @Override
    protected void init()
    {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        GUIBackGroundRender.rendShadowBlastFurnaceGUI(guiGraphics, leftPos + 22, topPos);
        GUIBackGroundRender.rendPasterDreamInventoryGUI(guiGraphics, leftPos, topPos + 112);
        GUIBackGroundRender.rendShadowBlastFurnaceShadowLiquidAmountBar(guiGraphics, leftPos + 97, topPos + 27, menu.getBlockEntity().getFluidTank(0).getFluid().getAmount() / 9000.0);

        int progress = menu.getProgress();
        int maxProgress = menu.getMaxProgress();
        if (maxProgress != 0)
        {
            GUIBackGroundRender.rendShadowBlastFurnaceProcessBar(guiGraphics, leftPos + 66, topPos + 52, (double)progress / maxProgress);
        }

        int lastTick = maxProgress - progress;
        guiGraphics.drawCenteredString(minecraft.getInstance().font, String.format("%02d", (lastTick / 72000)) + "h" + String.format("%02d", ((lastTick % 72000) / 1200)) + "m" + String.format("%02d", ((lastTick % 1200) / 20)) + "s" + String.format("%02d", (lastTick % 20)) + "tick", leftPos + (imageWidth / 2), topPos + 104, 0xFFFFFFFF);

        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
    }

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
