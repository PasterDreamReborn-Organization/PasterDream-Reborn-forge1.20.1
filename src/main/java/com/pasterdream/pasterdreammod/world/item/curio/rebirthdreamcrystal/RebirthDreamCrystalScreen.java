package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal;

import com.pasterdream.pasterdreammod.component.BookNameListButton;
import com.pasterdream.pasterdreammod.component.arrowbutton.LeftArrowButton;
import com.pasterdream.pasterdreammod.component.arrowbutton.RightArrowButton;
import com.pasterdream.pasterdreammod.helper.nonshadowcenteredstring.NonShadowCenteredString;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.dreamnotesbook.RequestNotesProgressPacket;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookProgressRegistry;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class RebirthDreamCrystalScreen extends AbstractContainerScreen<RebirthDreamCrystalMenu>
{
    private List<String> allDimensions = DreamNotesBookProgressRegistry.getAllDimensions();
    private List<String> singleDimensionsBooks = new ArrayList<>();
    private List<Boolean> singleDimensionProgress = new ArrayList<>();
    int unlockCount = 0;

    private int dimensionPage = 0;
    private int dreamNotesBookListPage = 0;

    private List<BookNameListButton> bookNameListButtons = new ArrayList<>();


    public RebirthDreamCrystalScreen(RebirthDreamCrystalMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
        this.imageWidth = 280;
        this.imageHeight = 264;
    }

    @Override
    protected void init()
    {
        super.init();
        bookNameListButtons.clear();

        Button uploadButton = Button.builder(Component.translatable("button.pasterdream.提交"), button ->
        {

        }).pos(leftPos + 228, topPos + 21).size(32, 16).build();
        addRenderableWidget(uploadButton);

        for(int i = 0; i < 12; i++)
        {
            bookNameListButtons.add(new BookNameListButton(leftPos + 20, topPos + 20 + 10 * i, null, false, this));
            addRenderableWidget(bookNameListButtons.get(i));
        }

        refreshBookListContent();

        LeftArrowButton bookListPrevButton = new LeftArrowButton(leftPos + 20, topPos + 147, button -> bookListPrevPage());
        RightArrowButton bookListNextButton = new RightArrowButton(leftPos + 97, topPos + 147, button -> bookListNextPage());
        LeftArrowButton dimensionPrevButton = new LeftArrowButton(leftPos + 160, topPos + 147, button -> dimensionPrevPage());
        RightArrowButton dimensionNextButton = new RightArrowButton(leftPos + 237, topPos + 147, button -> dimensionNextPage());
        addRenderableWidget(bookListPrevButton);
        addRenderableWidget(bookListNextButton);
        addRenderableWidget(dimensionPrevButton);
        addRenderableWidget(dimensionNextButton);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        ResourceLocation dreamNotesGUI = DreamNotesBookProgressRegistry.getResourceLocationByDimension(allDimensions.get(dimensionPage));
        guiGraphics.blit(dreamNotesGUI, leftPos, topPos, 0, 0, 140, 180, 140, 180);
        guiGraphics.blit(dreamNotesGUI, leftPos + 140, topPos, 0, 0, 140, 180, 140, 180);
        GUIBackGroundRender.rendPasterDreamInventoryGUI(guiGraphics, leftPos + 55, topPos + 180);
        GUIBackGroundRender.rendMinecraftSingleSlot(guiGraphics, leftPos + 159, topPos + 19);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        NonShadowCenteredString.drawCenteredStringWithOutShadow(guiGraphics, leftPos + 70, topPos + 155, (dreamNotesBookListPage + 1) + " / " + (singleDimensionsBooks.size() + 11) / 12, 0xFF000000);
        NonShadowCenteredString.drawCenteredStringWithOutShadow(guiGraphics, leftPos + 210, topPos + 155, (dimensionPage + 1) + " / " + allDimensions.size(), 0xFF000000);
        NonShadowCenteredString.drawCenteredStringWithOutShadow(guiGraphics, leftPos + 210, topPos + 135, Component.translatable("content.pasterdream.book.维度:").append(allDimensions.get(dimensionPage)).getString(), 0xFF000000);
        NonShadowCenteredString.drawCenteredStringWithOutShadow(guiGraphics, leftPos + 210, topPos + 145, Component.translatable("content.pasterdream.book.进度:").append(unlockCount + "/" + singleDimensionsBooks.size()).getString(), 0xFF000000);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta)
    {
        if(mouseY >= topPos && mouseY < topPos + 180)
        {
            if(delta > 0)
            {
                if(mouseX >= leftPos && mouseX < leftPos + 140)
                {
                    bookListPrevPage();
                }
                else
                    if(mouseX >= leftPos + 140 && mouseX < leftPos + 280)
                    {
                        dimensionPrevPage();
                    }
            }
            else
                if(delta < 0)
                {
                    if(mouseX >= leftPos && mouseX < leftPos + 140)
                    {
                        bookListNextPage();
                    }
                    else
                        if(mouseX >= leftPos + 140 && mouseX < leftPos + 280)
                        {
                            dimensionNextPage();
                        }
                }
            return true;
        }

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    private void dimensionPrevPage()
    {
        if(dimensionPage > 0)
        {
            dimensionPage--;
        }
            else
            {
                dimensionPage = allDimensions.size() - 1;
            }
        dreamNotesBookListPage = 0;
        refreshBookListContent();
    }

    private void dimensionNextPage()
    {
        if(dimensionPage < allDimensions.size() - 1)
        {
            dimensionPage++;
        }
            else
            {
                dimensionPage = 0;
            }
        dreamNotesBookListPage = 0;
        refreshBookListContent();
    }

    private void bookListPrevPage()
    {
        if(dreamNotesBookListPage > 0)
        {
            dreamNotesBookListPage--;
        }
            else
            {
                dreamNotesBookListPage = (singleDimensionsBooks.size() + 11) / 12 - 1;
            }
        refreshBookListPreview();
    }

    private void bookListNextPage()
    {
        if(dreamNotesBookListPage < (singleDimensionsBooks.size() + 11) / 12 - 1)
        {
            dreamNotesBookListPage++;
        }
            else
            {
                dreamNotesBookListPage = 0;
            }
        refreshBookListPreview();
    }

    private void refreshBookListContent()
    {
        singleDimensionsBooks = DreamNotesBookProgressRegistry.getNotesByDimension(allDimensions.get(dimensionPage));
        ModNetwork.CHANNEL.sendToServer(new RequestNotesProgressPacket(allDimensions.get(dimensionPage)));
    }

    public void onNotesProgressReceived(String dimension, List<Boolean> singleDimensionProgress)
    {
        if (allDimensions.get(dimensionPage).equals(dimension))
        {
            this.singleDimensionProgress = singleDimensionProgress;
            refreshBookListPreview();
            unlockCount = (int)(singleDimensionProgress.stream().filter(Boolean.TRUE::equals).count());
        }
    }

    private void refreshBookListPreview()
    {
        for(int i = 0; i < 12; i++)
        {
            if(dreamNotesBookListPage * 12 + i < singleDimensionsBooks.size())
            {
                int index = dreamNotesBookListPage * 12 + i;
                bookNameListButtons.get(i).setInfo(DreamNotesBookRegistry.getInfo(singleDimensionsBooks.get(index)), singleDimensionProgress.get(index));
            }
                else
                {
                    bookNameListButtons.get(i).setInfo(null, false);
                }
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {

    }
}
