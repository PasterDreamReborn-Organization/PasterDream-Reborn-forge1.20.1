package com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerScreenWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WeaponWorkshopBlastFurnaceScreen extends AbstractContainerScreenWithFluidSlot<WeaponWorkshopBlastFurnaceMenu>
{
    public WeaponWorkshopBlastFurnaceScreen(WeaponWorkshopBlastFurnaceMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
        this.imageWidth = 170;
        this.imageHeight = 192;
    }

    @Override
    protected void init()
    {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        GUIBackGroundRender.rendWeaponWorkshopBlastFurnaceGUI(guiGraphics, leftPos + 20, topPos);
        GUIBackGroundRender.rendPasterDreamInventoryGUI(guiGraphics, leftPos, topPos + 108);
        GUIBackGroundRender.rendWeaponWorkshopBlastFurnaceLavaAmountBar(guiGraphics, leftPos + 112, topPos + 38, menu.getBlockEntity().getFluidTank(0).getFluid().getAmount() / 4000.0);
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
