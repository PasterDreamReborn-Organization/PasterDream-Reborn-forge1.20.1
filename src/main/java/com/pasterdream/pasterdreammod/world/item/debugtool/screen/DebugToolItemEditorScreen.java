package com.pasterdream.pasterdreammod.world.item.debugtool.screen;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerScreenWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import com.pasterdream.pasterdreammod.helper.nonshadowcenteredstring.NonShadowCenteredString;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.helper.stringhelper.GetItemProperties;
import com.pasterdream.pasterdreammod.helper.stringhelper.StringHelper;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.debugtool.EnergyTransferPacket;
import com.pasterdream.pasterdreammod.network.debugtool.OpenItemHandlerPacket;
import com.pasterdream.pasterdreammod.network.menu.SetSlotNbtPacket;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.ClientItemHandlerContext;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolItemEditorMenu;
import com.pasterdream.pasterdreammod.world.item.debugtool.widget.NBTPreviewWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class DebugToolItemEditorScreen extends AbstractContainerScreenWithFluidSlot<DebugToolItemEditorMenu>
{
    private NBTPreviewWidget nbtPreviewWidget;
    private NBTPreviewWidget itemPropertiesPreviewWidget;
    private boolean thisItemIsNotHaveItemHandler = false;

    public DebugToolItemEditorScreen(DebugToolItemEditorMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init()
    {
        imageWidth = width;
        imageHeight = height;
        super.init();

        for (Slot slot : menu.slots)
        {
            int index = slot.index;
            if(index >= 0 && index <= 8)
            {
                slot.x = width / 2 - 80 + 18 * index;
                slot.y = height - 21;
            }
            else
                if(index >= 9 && index <= 35)
                {
                    slot.x = width / 2 - 80 + 18 * ((index - 9) % 9);
                    slot.y = height - 79 + (index - 9) / 9 * 18;
                }
                else
                    if(index == 36)
                    {
                        slot.x = width / 2 - 8;
                        slot.y = height / 8 - 18;
                    }
                    else
                        if(index >= 37 && index <= 44)
                        {
                            slot.x = width / 2 - 71 + 18 * (index - 37);
                            slot.y = height * 5 / 8 - 61;
                        }
                        else
                            if(index == 45)
                            {
                                slot.x = width / 2 - 8;
                                slot.y = height * 7 / 8 - 82;
                            }
        }

        for (FluidSlot fluidSlot : menu.getFluidSlots())
        {
            int index = fluidSlot.fluidSlotIndex;
            fluidSlot.x = width / 2 - 72 + 18 * index;
            fluidSlot.y = height * 5 / 8 - 62;
        }

        ItemStack _36SlotItemStack = menu.getSlot(36).getItem();
        CompoundTag NBT = _36SlotItemStack.getTag();
        nbtPreviewWidget = new NBTPreviewWidget(5, 5, width / 2 - 95, height - 26, StringHelper.ListStringFromString(NBT == null ? "" : NBT.toString(), width / 2 - 104));
        menu.addEditorSlotListener(itemStack ->
        {
            CompoundTag nbt = itemStack == null ? null : itemStack.getTag();
            nbtPreviewWidget.setListString(StringHelper.ListStringFromString(nbt == null ? "" : nbt.toString(), width / 2 - 104));
        });
        addRenderableWidget(nbtPreviewWidget);

        Button NBTEditorButton = Button.builder(Component.translatable("button.pasterdream.编辑NBT"), button ->
        {
            CompoundTag nbt = _36SlotItemStack.getTag();
            Minecraft.getInstance().setScreen(new DebugToolNBTEditorScreen(this, nbt == null ? "" : nbt.toString(), savedText ->
            {
                CompoundTag parsed;
                try
                {
                    parsed = savedText.isBlank() ? null : TagParser.parseTag(savedText);
                }
                    catch (CommandSyntaxException e)
                    {
                        return Component.translatable("error.pasterdream.无效的NBT", e.getMessage());
                    }

                ModNetwork.CHANNEL.sendToServer(new SetSlotNbtPacket(36, parsed));
                return null;
            }));
        }).pos(5, height - 21).size(width / 2 - 95, 16).build();
        addRenderableWidget(NBTEditorButton);

        List<String> itemProperties = StringHelper.ListStringFromString(GetItemProperties.getItemProperties(_36SlotItemStack), width / 2 - 104);
        itemPropertiesPreviewWidget = new NBTPreviewWidget(width / 2 + 90, 5, width / 2 - 95, height - 10, itemProperties);
        menu.addEditorSlotListener(itemStack ->
        {
            List<String> changedItemProperties = StringHelper.ListStringFromString(GetItemProperties.getItemProperties(itemStack), width / 2 - 104);
            itemPropertiesPreviewWidget.setListString(changedItemProperties);
        });
        addRenderableWidget(itemPropertiesPreviewWidget);

        Button ItemHandlerButton = Button.builder(Component.translatable("button.pasterdream.操作ItemHandler"), button ->
        {
            IItemHandler handler = _36SlotItemStack.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve().orElse(null);
            if (handler != null)
            {
                thisItemIsNotHaveItemHandler = false;
                ClientItemHandlerContext.set(handler);
                ModNetwork.CHANNEL.sendToServer(new OpenItemHandlerPacket(menu.containerId, 36));
            }
                else
                {
                    thisItemIsNotHaveItemHandler = true;
                }
        }).pos(width / 2 - 48, height * 3 / 8 - 39).size(96, 16).build();
        addRenderableWidget(ItemHandlerButton);

        EditBox amountBox = new EditBox(Minecraft.getInstance().font, width / 2 + 44, height * 7 / 8 - 82,  36, 16, Component.literal("FE"));
        amountBox.setValue("0");
        amountBox.setMaxLength(10);
        addRenderableWidget(amountBox);

        Button chargeButton = Button.builder(Component.translatable("button.pasterdream.充电"), button ->
        {
            int energyAmount = 0;
            try
            {
                energyAmount = Integer.parseInt(amountBox.getValue().trim());
            }
                catch (NumberFormatException e)
                {

                }
            ModNetwork.CHANNEL.sendToServer(new EnergyTransferPacket(36, 45, energyAmount));
        }).pos(width / 2 + 10, height * 7 / 8 - 82).size(32, 16).build();
        addRenderableWidget(chargeButton);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        GUIBackGroundRender.rendMinecraftGUIBackground(guiGraphics, width / 2 - 85, 0, 170, height / 4 - 21);
        GUIBackGroundRender.rendMinecraftGUIBackground(guiGraphics, width / 2 - 85, height / 4 - 21, 170, height / 4 - 21);
        GUIBackGroundRender.rendMinecraftGUIBackground(guiGraphics, width / 2 - 85, height / 2 - 42, 170, height / 4 - 21);
        GUIBackGroundRender.rendMinecraftGUIBackground(guiGraphics, width / 2 - 85, height * 3 / 4 - 63, 170, height / 4 - 21);

        GUIBackGroundRender.rendPasterDreamInventoryGUI(guiGraphics, width / 2 - 85, height - 84);

        GUIBackGroundRender.rendMinecraftGUIBackground(guiGraphics, 0, 0, width / 2 - 85, height);
        GUIBackGroundRender.rendMinecraftGUIBackground(guiGraphics, width / 2 + 85, 0, width / 2 - 85, height);

        GUIBackGroundRender.rendMinecraftSingleSlot(guiGraphics, width / 2 - 9, height / 8 - 19);

        for(int i = 0; i < 8; i++)
        {
            GUIBackGroundRender.rendMinecraftSingleSlot(guiGraphics, width / 2 - 72 + 18 * i, height * 5 / 8 - 62);
        }

        GUIBackGroundRender.rendMinecraftSingleSlot(guiGraphics, width / 2 - 9, height * 7 / 8 - 83);

        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);

        if(thisItemIsNotHaveItemHandler)
        {
            NonShadowCenteredString.drawCenteredStringWithOutShadow(guiGraphics, width / 2, height * 3 / 8 - 18, Component.translatable("message.pasterdream.无ItemHandler").getString(), 0xFFFF0000);
        }

        if(menu.energyStorage == null)
        {
            NonShadowCenteredString.drawCenteredStringWithOutShadow(guiGraphics, width / 2, height * 7 / 8 - 87, Component.translatable("message.pasterdream.无EnergyStorage").getString(), 0xFFFF0000);
            guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("message.pasterdream.不可放电"), width / 2 - 80, height * 7 / 8 - 82, 0xFFFF0000, false);
            guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("message.pasterdream.不可充电"), width / 2 - 80, height * 7 / 8 - 73, 0xFFFF0000, false);
        }
            else
            {
                IEnergyStorage energyStorage = menu.energyStorage;;
                NonShadowCenteredString.drawCenteredStringWithOutShadow(guiGraphics, width / 2, height * 7 / 8 - 87, energyStorage.getEnergyStored() + "FE/" + energyStorage.getMaxEnergyStored() + "FE", 0xFF000000);

                if(energyStorage.canExtract())
                {
                    guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("message.pasterdream.可放电"), width / 2 - 80, height * 7 / 8 - 82, 0xFF00FF00, false);
                }
                    else
                    {
                        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("message.pasterdream.不可放电"), width / 2 - 80, height * 7 / 8 - 82, 0xFFFF0000, false);
                    }

                if(energyStorage.canReceive())
                {
                    guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("message.pasterdream.可充电"), width / 2 - 80, height * 7 / 8 - 73, 0xFF00FF00, false);
                }
                    else
                    {
                        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("message.pasterdream.不可充电"), width / 2 - 80, height * 7 / 8 - 73, 0xFFFF0000, false);
                    }
            }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY)
    {
        if (nbtPreviewWidget != null && button == 0 && nbtPreviewWidget.mouseDragged(mouseX, mouseY, button, dragX, dragY))
        {
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {

    }
}
