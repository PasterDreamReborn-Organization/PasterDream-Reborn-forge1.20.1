package com.pasterdream.pasterdreammod.world.item.mortar;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerScreenWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.mortar.MortarCraftPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MortarScreen extends AbstractContainerScreenWithFluidSlot<MortarMenu>
{
    public MortarScreen(MortarMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
        this.imageWidth = 170;
        this.imageHeight = 180;
    }

    @Override
    protected void init()
    {
        super.init();

        Button MortarButton = Button.builder(Component.translatable("button.pasterdream.mortarbutton"), button ->
        {
            ModNetwork.CHANNEL.sendToServer(new MortarCraftPacket());
        }).pos(leftPos + 44, topPos + 46).size(32, 16).build();

        addRenderableWidget(MortarButton);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        GUIBackGroundRender.rendMortarGUI(guiGraphics, leftPos + 21, topPos);
        GUIBackGroundRender.rendPasterDreamInventoryGUI(guiGraphics, leftPos, topPos + 96);
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
