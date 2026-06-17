package com.pasterdream.pasterdreammod.world.block.dreamcauldron;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.component.DreamCauldronButton;
import com.pasterdream.pasterdreammod.component.FluidSlot;
import com.pasterdream.pasterdreammod.helper.RenderHelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.DreamCauldronCraftPacket;
import com.pasterdream.pasterdreammod.network.FluidSlotInteractPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DreamCauldronScreen extends AbstractContainerScreen<DreamCauldronMenu>
{
    private FluidSlot fluidSlot0;
    private FluidSlot fluidSlot1;

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

        fluidSlot0 = new FluidSlot(leftPos + 162, topPos + 18, () -> menu.getBlockEntity().getFluidTank(0).getFluid(), button ->
        {
            FriendlyByteBuf extra = new FriendlyByteBuf(Unpooled.buffer());
            extra.writeBlockPos(menu.getBlockEntity().getBlockPos());
            extra.writeInt(0);
            ModNetwork.CHANNEL.sendToServer(new FluidSlotInteractPacket(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "block_entity"), extra, button == 0));
        });
        addRenderableWidget(fluidSlot0);

        fluidSlot1 = new FluidSlot(leftPos + 9, topPos + 45, () -> menu.getBlockEntity().getFluidTank(1).getFluid(), button ->
        {
            FriendlyByteBuf extra = new FriendlyByteBuf(Unpooled.buffer());
            extra.writeBlockPos(menu.getBlockEntity().getBlockPos());
            extra.writeInt(1);
            ModNetwork.CHANNEL.sendToServer(new FluidSlotInteractPacket(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "block_entity"), extra, button == 0));
        });
        addRenderableWidget(fluidSlot1);

        addRenderableWidget(new DreamCauldronButton(leftPos + 71, topPos + 67, button -> ModNetwork.CHANNEL.sendToServer(new DreamCauldronCraftPacket(menu.getBlockEntity().getBlockPos()))));
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        GUIBackGroundRender.rendDreamCauldronGUI(guiGraphics, leftPos + 9, topPos);
        GUIBackGroundRender.rendPasterDreamInventoryGUI(guiGraphics, leftPos + 5, topPos + 105);
        GUIBackGroundRender.rendDreamCauldronMeltDreamLiquidBar(guiGraphics, leftPos + 149, topPos + 19, menu.getBlockEntity().getFluidTank(0).getFluid().getAmount() / 2000.0);
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
