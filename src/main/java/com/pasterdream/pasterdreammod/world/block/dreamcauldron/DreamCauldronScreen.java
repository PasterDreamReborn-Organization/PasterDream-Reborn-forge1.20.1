package com.pasterdream.pasterdreammod.world.block.dreamcauldron;

import com.pasterdream.pasterdreammod.component.DreamCauldronButton;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerScreenWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.DreamCauldronCraftPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DreamCauldronScreen extends AbstractContainerScreenWithFluidSlot<DreamCauldronMenu>
{
    public DreamCauldronScreen(DreamCauldronMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
        this.imageWidth = 180;
        this.imageHeight = 189;
    }

    @Override
    protected void init()
    {
        super.init();
        addRenderableWidget(new DreamCauldronButton(leftPos + 71, topPos + 67, button -> ModNetwork.CHANNEL.sendToServer(new DreamCauldronCraftPacket(menu.getBlockEntity().getBlockPos()))));
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        GUIBackGroundRender.rendDreamCauldronGUI(guiGraphics, leftPos + 9, topPos);
        GUIBackGroundRender.rendPasterDreamInventoryGUI(guiGraphics, leftPos + 5, topPos + 105);
        GUIBackGroundRender.rendDreamCauldronMeltDreamLiquidBar(guiGraphics, leftPos + 149, topPos + 19, menu.getBlockEntity().getFluidTank(0).getFluid().getAmount() / 2000.0);
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
