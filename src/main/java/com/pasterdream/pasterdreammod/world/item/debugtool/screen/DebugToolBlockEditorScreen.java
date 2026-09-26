package com.pasterdream.pasterdreammod.world.item.debugtool.screen;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.helper.stringhelper.GetBlockProperties;
import com.pasterdream.pasterdreammod.helper.stringhelper.GetItemProperties;
import com.pasterdream.pasterdreammod.helper.stringhelper.StringHelper;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.debugtool.SetBlockEntityNbtPacket;
import com.pasterdream.pasterdreammod.network.debugtool.SetBlockStatePacket;
import com.pasterdream.pasterdreammod.network.menu.SetSlotNbtPacket;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolBlockEditorMenu;
import com.pasterdream.pasterdreammod.world.item.debugtool.widget.NBTPreviewWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class DebugToolBlockEditorScreen extends AbstractContainerScreen<DebugToolBlockEditorMenu>
{
    private NBTPreviewWidget nbtPreviewWidget;
    private NBTPreviewWidget blockStatePreviewWidget;
    private NBTPreviewWidget blockPropertiesPreviewWidget;
    private boolean thisItemIsNotHaveItemHandler = false;
    private boolean thisItemIsNotHaveFluidHandler = false;

    public DebugToolBlockEditorScreen(DebugToolBlockEditorMenu menu, Inventory playerInventory, Component title)
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
                    if(index == 37)
                    {
                        slot.x = width / 2 - 8;
                        slot.y = height * 7 / 8 - 82;
                    }
        }

        CompoundTag NBT = menu.getServerBlockNbt();
        nbtPreviewWidget = new NBTPreviewWidget(5, 5, width / 2 - 95, height - 26, StringHelper.ListStringFromString(NBT == null ? "" : NBT.toString(), width / 2 - 104));
        addRenderableWidget(nbtPreviewWidget);

        Button NBTEditorButton = Button.builder(Component.translatable("button.pasterdream.编辑NBT"), button ->
        {
            CompoundTag nbt = menu.getServerBlockNbt();
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

                ModNetwork.CHANNEL.sendToServer(new SetBlockEntityNbtPacket(menu.getBlockPosition(), parsed));
                return null;
            }));
        }).pos(5, height - 21).size(width / 2 - 95, 16).build();
        addRenderableWidget(NBTEditorButton);

        menu.clearNbtListeners();
        menu.addNbtListener(nbt -> nbtPreviewWidget.setListString(StringHelper.ListStringFromString(nbt == null ? "" : nbt.toString(), width / 2 - 104)));

        BlockState blockState = menu.getBlockState();
        blockStatePreviewWidget = new NBTPreviewWidget(width / 2 + 90, 5, width / 2 - 95, height / 3 - 26, StringHelper.ListStringFromString(BlockStateParser.serialize(blockState), width / 2 - 104));
        addRenderableWidget(blockStatePreviewWidget);

        Button BlockStateEditorButton = Button.builder(Component.translatable("button.pasterdream.编辑BlockState"), button ->
        {
            BlockState editBlockState = menu.getBlockState();
            Minecraft.getInstance().setScreen(new DebugToolNBTEditorScreen(this, BlockStateParser.serialize(editBlockState), savedText ->
            {
                if (savedText.isBlank())
                {
                    return Component.translatable("error.pasterdream.无效的BlockState", "内容为空");
                }

                try
                {
                    BlockStateParser.parseForBlock(Minecraft.getInstance().level.holderLookup(Registries.BLOCK), savedText.trim(), false);
                }
                    catch (CommandSyntaxException e)
                    {
                        return Component.translatable("error.pasterdream.无效的BlockState", e.getMessage());
                    }

                ModNetwork.CHANNEL.sendToServer(new SetBlockStatePacket(menu.getBlockPosition(), savedText.trim()));
                return null;
            }));
        }).pos(width / 2 + 90, height / 3 - 21).size(width / 2 - 95, 16).build();
        addRenderableWidget(BlockStateEditorButton);

        menu.clearBlockStateListeners();
        menu.addBlockStateListener(stateString -> blockStatePreviewWidget.setListString(StringHelper.ListStringFromString(stateString, width / 2 - 104)));

        List<String> blockProperties = StringHelper.ListStringFromString(GetBlockProperties.getBlockProperties(menu.getBlockState(), menu.getLevel(), menu.getBlockPosition()), width / 2 - 104);
        blockPropertiesPreviewWidget = new NBTPreviewWidget(width / 2 + 90, height / 3 + 5, width / 2 - 95, height * 2 / 3 - 10, blockProperties);
        addRenderableWidget(blockPropertiesPreviewWidget);
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
        GUIBackGroundRender.rendMinecraftGUIBackground(guiGraphics, width / 2 + 85, 0, width / 2 - 85, height / 3);
        GUIBackGroundRender.rendMinecraftGUIBackground(guiGraphics, width / 2 + 85, height / 3, width / 2 - 85, height * 2 / 3);

        GUIBackGroundRender.rendMinecraftSingleSlot(guiGraphics, width / 2 - 9, height / 8 - 19);
        GUIBackGroundRender.rendMinecraftSingleSlot(guiGraphics, width / 2 - 9, height * 7 / 8 - 83);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY)
    {
        boolean needReturn = false;

        if(nbtPreviewWidget != null && button == 0 && nbtPreviewWidget.mouseDragged(mouseX, mouseY, button, dragX, dragY))
        {
            needReturn = true;
        }

        if(blockStatePreviewWidget != null && button == 0 && blockStatePreviewWidget.mouseDragged(mouseX, mouseY, button, dragX, dragY))
        {
            needReturn = true;
        }

        if(blockPropertiesPreviewWidget != null && button == 0 && blockPropertiesPreviewWidget.mouseDragged(mouseX, mouseY, button, dragX, dragY))
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
}
