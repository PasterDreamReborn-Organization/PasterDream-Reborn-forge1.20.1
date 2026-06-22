package com.pasterdream.pasterdreammod.world.block.claypan;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerScreenWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ClaypanScreen extends AbstractContainerScreenWithFluidSlot<ClaypanMenu>
{
    public ClaypanScreen(ClaypanMenu menu, Inventory inv, Component title)
    {
        super(menu, inv, title);
        this.imageWidth = 170;
        this.imageHeight = 121;
    }

    @Override
    protected void init()
    {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        GUIBackGroundRender.rendClaypanGUI(guiGraphics, leftPos + 44, topPos);
        GUIBackGroundRender.rendPasterDreamInventoryGUI(guiGraphics, leftPos, topPos + 37);

        int progress = menu.getProgress();
        int maxProgress = menu.getMaxProgress();
        if (maxProgress != 0)
        {
            GUIBackGroundRender.rendArrow(guiGraphics, leftPos + 74, topPos + 7, (double)progress / maxProgress);
        }

        int lastTick = maxProgress - progress;
        guiGraphics.drawCenteredString(minecraft.getInstance().font, String.format("%02d", (lastTick / 72000)) + "h" + String.format("%02d", ((lastTick % 72000) / 1200)) + "m" + String.format("%02d", ((lastTick % 1200) / 20)) + "s" + String.format("%02d", (lastTick % 20)) + "tick", leftPos + (imageWidth / 2), topPos + 29, 0xFFFFFFFF);

        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {

    }
}
