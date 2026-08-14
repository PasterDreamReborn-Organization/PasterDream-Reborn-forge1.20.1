package com.pasterdream.pasterdreammod.world.item.blueprints;

import com.pasterdream.pasterdreammod.component.ReadOnlySlot;
import com.pasterdream.pasterdreammod.component.arrowbutton.DownArrowButton;
import com.pasterdream.pasterdreammod.component.arrowbutton.UpArrowButton;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.blueprint.StartBlueprintPlacementPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BluePrintScreen extends Screen
{
    private List<ReadOnlySlot> readOnlySlots = new ArrayList<>();
    private UpArrowButton upArrowButton;
    private DownArrowButton downArrowButton;
    private CompoundTag materialNBT;
    private CompoundTag resultNBT;

    private int sizeX = 0;
    private int sizeY = 0;
    private int sizeZ = 0;
    private int currentY = 0;
    List<List<List<ItemStack>>> ListListListItemStack;

    public BluePrintScreen(CompoundTag materialNBT, CompoundTag resultNBT)
    {
        super(Component.empty());
        this.materialNBT = materialNBT;
        this.resultNBT = resultNBT;
    }

    @Override
    protected void init()
    {
        super.init();

        upArrowButton = new UpArrowButton(width / 2 + 60, height / 2 - 46, button -> nextPage());
        downArrowButton = new DownArrowButton(width / 2 + 60, height / 2 + 26, button -> prevPage());
        addRenderableWidget(upArrowButton);
        addRenderableWidget(downArrowButton);

        Button BluePrintButton = Button.builder(Component.translatable("button.pasterdream.blue_print_button"), button ->
        {
            ModNetwork.CHANNEL.sendToServer(new StartBlueprintPlacementPacket(materialNBT, resultNBT));
            Minecraft.getInstance().setScreen(null);
        }).pos(width / 2 - 88, height / 2 - 8).size(32, 16).build();
        addRenderableWidget(BluePrintButton);

        ListListListItemStack = BluePrintNBTSerializer.serialize(materialNBT);

        if(ListListListItemStack == null)
        {
            return;
        }

        sizeY = ListListListItemStack.size();
        sizeX = ListListListItemStack.get(0).size();
        sizeZ = ListListListItemStack.get(0).get(0).size();

        for(int z = 0; z < sizeZ; z++)
        {
            for(int x = 0; x < sizeX; x++)
            {
                int index = sizeX * z + x;
                ReadOnlySlot readOnlySlot = new ReadOnlySlot(width / 2 - 44 + x * 18, height / 2 - 44 + z * 18, ListListListItemStack.get(currentY).get(x).get(z));
                readOnlySlots.add(readOnlySlot);
                addRenderableWidget(readOnlySlots.get(index));
            }
        }

        refreshFloorSlotPosition();
        refreshFloorItem();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        renderBackground(guiGraphics);
        GUIBackGroundRender.rendBlueprintGUI(guiGraphics, width / 2 - 46, height / 2 - 46);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, (currentY + 1) + "/" + sizeY, width / 2 + 68, height / 2 - 5, 0xFFFFFFFF);
    }

    private void refreshFloorSlotPosition()
    {
        for(int z = 0; z < sizeZ; z++)
        {
            for(int x = 0; x < sizeX; x++)
            {
                readOnlySlots.get(sizeX * z + x).setPosition(width / 2 - 44 + x * 18, height / 2 - 44 + z * 18);
            }
        }
    }

    private void refreshFloorItem()
    {
        for(int z = 0; z < sizeZ; z++)
        {
            for (int x = 0; x < sizeX; x++)
            {
                readOnlySlots.get(z * sizeX + x).setItemStack(ListListListItemStack.get(currentY).get(x).get(z));
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta)
    {
        if (delta > 0)
        {
            prevPage();
        }
        else
            if (delta < 0)
            {
                nextPage();
            }
        return true;
    }

    private void prevPage()
    {
        if (currentY > 0)
        {
            currentY--;
        }
            else
            {
                currentY = sizeY - 1;
            }
        refreshFloorItem();
    }

    private void nextPage()
    {
        if (currentY < sizeY - 1)
        {
            currentY++;
        }
            else
            {
                currentY = 0;
            }
        refreshFloorItem();
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
