package com.pasterdream.pasterdreammod.world.block.dreamcauldron;

import com.pasterdream.pasterdreammod.component.DreamCauldronButton;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerScreenWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.DreamCauldronCraftPacket;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DreamCauldronScreen extends AbstractContainerScreenWithFluidSlot<DreamCauldronMenu>
{
    /** 法术工厂操作结果提示（渲染在三物品槽上方），超时后自动消失 */
    private static final long MESSAGE_DURATION_MS = 3000L;

    private Component cauldronMessage;
    private long cauldronMessageTime;

    public DreamCauldronScreen(DreamCauldronMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
        this.imageWidth = 180;
        this.imageHeight = 189;
    }

    /** 由 DreamCauldronMessagePacket 调用：在釜界面顶部显示提示 */
    public void showCauldronMessage(Component message)
    {
        this.cauldronMessage = message;
        this.cauldronMessageTime = Util.getMillis();
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
        if (cauldronMessage != null && Util.getMillis() - cauldronMessageTime <= MESSAGE_DURATION_MS)
        {
            // 居中于三个物品槽正上方
            int x = (imageWidth - font.width(cauldronMessage)) / 2;
            guiGraphics.drawString(font, cauldronMessage, x, -8, 0xFFFFFF, false);
        }
        else
        {
            cauldronMessage = null;
        }
    }
}
