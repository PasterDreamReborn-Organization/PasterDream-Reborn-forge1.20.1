package com.pasterdream.pasterdreammod.world.item.debugtool.screen;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pasterdream.pasterdreammod.component.arrowbutton.DownArrowButton;
import com.pasterdream.pasterdreammod.component.arrowbutton.UpArrowButton;
import com.pasterdream.pasterdreammod.helper.nonshadowcenteredstring.NonShadowCenteredString;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.helper.stringhelper.StringHelper;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.debugtool.SetEntityNbtPacket;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolEntityEditorMenu;
import com.pasterdream.pasterdreammod.world.item.debugtool.slot.ActiveStatusChangeableSlot;
import com.pasterdream.pasterdreammod.world.item.debugtool.slot.DelegatedSlot;
import com.pasterdream.pasterdreammod.world.item.debugtool.widget.NBTPreviewWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.List;

public class DebugToolEntityEditorScreen extends AbstractContainerScreen<DebugToolEntityEditorMenu>
{
    private NBTPreviewWidget nbtPreviewWidget;
    private NBTPreviewWidget entityPropertiesPreviewWidget;
    private List<Slot> itemSlots;
    private int curiosSlotPageSize;
    private int curiosSlotCurrentPage = 0;
    private UpArrowButton curiosUpArrowButton;
    private DownArrowButton curiosDownArrowButton;
    private int itemHandlerPageSize;
    private int itemHandlerCurrentPage = 0;
    private UpArrowButton itemHandlerUpArrowButton;
    private DownArrowButton itemHandlerDownArrowButton;

    public DebugToolEntityEditorScreen(DebugToolEntityEditorMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);

        itemSlots = menu.slots;
        for(int i = 49; i < 43 + menu.getCuriosSlotCount(); i++)
        {
            ((ActiveStatusChangeableSlot)(itemSlots.get(i))).setActive(false);
        }
        curiosSlotPageSize = Math.max(1, (menu.getCuriosSlotCount() + 5) / 6);

        for(int i = 49 + menu.getCuriosSlotCount(); i < 43 + menu.getCuriosSlotCount() + menu.getItemHandlerSlotCount(); i++)
        {
            ((ActiveStatusChangeableSlot)(itemSlots.get(i))).setActive(false);
        }
        itemHandlerPageSize = Math.max(1, (menu.getItemHandlerSlotCount() + 5) / 6);
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
                        if(index == 37 || index == 38)
                        {
                            slot.x = width / 2 - 62 + 18 * (index - 37);
                            slot.y = height * 3 / 8 - 39;
                        }
                        else
                            if(index >= 39 && index <= 42)
                            {
                                slot.x = width / 2 - 8 + 18 * (index - 39);
                                slot.y = height * 3 / 8 - 39;
                            }
                            else
                                if(index >= 43 && index < 43 + menu.getCuriosSlotCount())
                                {
                                    slot.x = width / 2 - 80 + 18 * ((index - 43) % 6);
                                    slot.y =  height * 5 / 8 - 61;
                                }
                                else
                                    if(index >= 43 + menu.getCuriosSlotCount() && index < 43 + menu.getCuriosSlotCount() + menu.getItemHandlerSlotCount())
                                    {
                                        slot.x = width / 2 - 80 + 18 * ((index - menu.getCuriosSlotCount() - 43) % 6);
                                        slot.y =  height * 7 / 8 - 82;
                                    }
        }

        CompoundTag NBT = menu.getEntityNBT();
        nbtPreviewWidget = new NBTPreviewWidget(5, 5, width / 2 - 95, height - 26, StringHelper.ListStringFromString(NBT == null ? "" : NBT.toString(), width / 2 - 104));
        addRenderableWidget(nbtPreviewWidget);

        Button NBTEditorButton = Button.builder(Component.translatable("button.pasterdream.编辑NBT"), button ->
        {
            CompoundTag nbt = menu.getEntityNBT();
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

                ModNetwork.CHANNEL.sendToServer(new SetEntityNbtPacket(menu.getEntityId() , parsed));
                return null;
            }));
        }).pos(5, height - 21).size(width / 2 - 95, 16).build();
        addRenderableWidget(NBTEditorButton);

        menu.clearNbtListeners();
        menu.addNbtListener(nbt -> nbtPreviewWidget.setListString(StringHelper.ListStringFromString(nbt == null ? "" : nbt.toString(), width / 2 - 104)));

        curiosUpArrowButton = new UpArrowButton(width / 2 + 28, height * 5 / 8 - 64, button -> curiosPrevPage());
        curiosDownArrowButton = new DownArrowButton(width / 2 + 66, height * 5 / 8 - 64, button -> curiosNextPage());
        addRenderableWidget(curiosUpArrowButton);
        addRenderableWidget(curiosDownArrowButton);

        itemHandlerUpArrowButton = new UpArrowButton(width / 2 + 28, height * 7 / 8 - 85, button -> itemHandlerPrevPage());
        itemHandlerDownArrowButton = new DownArrowButton(width / 2 + 66, height * 7 / 8 - 85, button -> itemHandlerNextPage());
        addRenderableWidget(itemHandlerUpArrowButton);
        addRenderableWidget(itemHandlerDownArrowButton);
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

        for(int i = 0; i < 2; i++)
        {
            GUIBackGroundRender.rendMinecraftSingleSlot(guiGraphics, width / 2 - 63 + 18 * i, height * 3 / 8 - 40);
        }

        for(int i = 0; i < 4; i++)
        {
            GUIBackGroundRender.rendMinecraftSingleSlot(guiGraphics, width / 2 - 9 + 18 * i, height * 3 / 8 - 40);
        }

        for(int i = 0; i < 6; i++)
        {
            GUIBackGroundRender.rendMinecraftSingleSlot(guiGraphics, width / 2 - 81 + 18 * i, height * 5 / 8 - 62);
        }

        for(int i = 0; i < 6; i++)
        {
            GUIBackGroundRender.rendMinecraftSingleSlot(guiGraphics, width / 2 - 81 + 18 * i, height * 7 / 8 - 83);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);

        NonShadowCenteredString.drawCenteredStringWithOutShadow(guiGraphics, width / 2 + 54, height * 5 / 8 - 52, String.valueOf(curiosSlotCurrentPage + 1), 0xFF000000);
        NonShadowCenteredString.drawCenteredStringWithOutShadow(guiGraphics, width / 2 + 54, height * 7 / 8 - 73, String.valueOf(itemHandlerCurrentPage + 1), 0xFF000000);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY)
    {
        boolean needReturn = false;

        if(nbtPreviewWidget != null && button == 0 && nbtPreviewWidget.mouseDragged(mouseX, mouseY, button, dragX, dragY))
        {
            needReturn = true;
        }

        if(entityPropertiesPreviewWidget != null && button == 0 && entityPropertiesPreviewWidget.mouseDragged(mouseX, mouseY, button, dragX, dragY))
        {
            needReturn = true;
        }

        if(needReturn)
        {
            return true;
        }
        else
        {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {

    }

    private void curiosPrevPage()
    {
        setCuriosSlotActive(curiosSlotCurrentPage, false);
        if(curiosSlotCurrentPage > 0)
        {
            curiosSlotCurrentPage--;
        }
            else
            {
                curiosSlotCurrentPage = curiosSlotPageSize - 1;
            }
        setCuriosSlotActive(curiosSlotCurrentPage, true);
    }

    private void curiosNextPage()
    {
        setCuriosSlotActive(curiosSlotCurrentPage, false);
        if(curiosSlotCurrentPage < curiosSlotPageSize - 1)
        {
            curiosSlotCurrentPage++;
        }
            else
            {
                curiosSlotCurrentPage = 0;
            }
        setCuriosSlotActive(curiosSlotCurrentPage, true);
    }

    private void itemHandlerPrevPage()
    {
        setItemHandlerSlotActive(itemHandlerCurrentPage, false);
        if(itemHandlerCurrentPage > 0)
        {
            itemHandlerCurrentPage--;
        }
            else
            {
                itemHandlerCurrentPage = itemHandlerPageSize - 1;
            }
        setItemHandlerSlotActive(itemHandlerCurrentPage, true);
    }

    private void itemHandlerNextPage()
    {
        setItemHandlerSlotActive(itemHandlerCurrentPage, false);
        if(itemHandlerCurrentPage < itemHandlerPageSize - 1)
        {
            itemHandlerCurrentPage++;
        }
            else
            {
                itemHandlerCurrentPage = 0;
            }
        setItemHandlerSlotActive(itemHandlerCurrentPage, true);
    }

    private void setCuriosSlotActive(int page, boolean isActive)
    {
        int startIndex = page * 6 + 43;
        for(int i = startIndex; i < Math.min(startIndex + 6, 43 + menu.getCuriosSlotCount()); i++)
        {
            ((ActiveStatusChangeableSlot)(itemSlots.get(i))).setActive(isActive);
        }
    }

    private void setItemHandlerSlotActive(int page, boolean isActive)
    {
        int startIndex = page * 6 + menu.getCuriosSlotCount() + 43;
        for(int i = startIndex; i < Math.min(startIndex + 6, 43 + menu.getCuriosSlotCount() + menu.getItemHandlerSlotCount()); i++)
        {
            ((ActiveStatusChangeableSlot)(itemSlots.get(i))).setActive(isActive);
        }
    }
}
