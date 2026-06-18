package com.pasterdream.pasterdreammod.world.item.mortar;

import com.pasterdream.pasterdreammod.component.FluidSlot;
import com.pasterdream.pasterdreammod.helper.RenderHelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.MortarFluidInteractPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MortarScreen extends AbstractContainerScreen<MortarMenu>
{
    private FluidSlot inputFluidSlot0;
    private FluidSlot inputFluidSlot1;
    private FluidSlot inputFluidSlot2;
    private FluidSlot inputFluidSlot3;

    private FluidSlot outputFluidSlot0;
    private FluidSlot outputFluidSlot1;

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

        inputFluidSlot0 = createFluidSlot(leftPos + 26, topPos + 5, 0, true);
        inputFluidSlot1 = createFluidSlot(leftPos + 44, topPos + 5, 1, true);
        inputFluidSlot2 = createFluidSlot(leftPos + 26, topPos + 23, 2, true);
        inputFluidSlot3 = createFluidSlot(leftPos + 44, topPos + 23, 3, true);

        outputFluidSlot0 = createFluidSlot(leftPos + 26, topPos + 67, 0, false);
        outputFluidSlot1 = createFluidSlot(leftPos + 44, topPos + 67, 1, false);

        Button MortarButton = Button.builder(Component.translatable("button.pasterdream.mortarbutton"), button ->
        {

        }).pos(leftPos + 44, topPos + 46).size(32, 16).build();

        addRenderableWidget(inputFluidSlot0);
        addRenderableWidget(inputFluidSlot1);
        addRenderableWidget(inputFluidSlot2);
        addRenderableWidget(inputFluidSlot3);
        addRenderableWidget(outputFluidSlot0);
        addRenderableWidget(outputFluidSlot1);
        addRenderableWidget(MortarButton);
    }

    private FluidSlot createFluidSlot(int x, int y, int index, boolean isInput)
    {
        return new FluidSlot(x, y, () -> isInput ? menu.getInputFluidStack(index) : menu.getOutputFluidStack(index), button ->
        {
            ModNetwork.CHANNEL.sendToServer(new MortarFluidInteractPacket(isInput, index, button == 0));
        });
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        GUIBackGroundRender.rendMortarGUI(guiGraphics, leftPos + 21, topPos);
        GUIBackGroundRender.rendPasterDreamInventoryGUI(guiGraphics, leftPos, topPos + 96);
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
