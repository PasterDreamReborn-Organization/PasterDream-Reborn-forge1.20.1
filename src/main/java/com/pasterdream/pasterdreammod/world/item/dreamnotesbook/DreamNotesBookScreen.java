package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import com.pasterdream.pasterdreammod.component.arrowbutton.LeftArrowButton;
import com.pasterdream.pasterdreammod.component.arrowbutton.RightArrowButton;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.List;

public class DreamNotesBookScreen extends Screen
{
    private LeftArrowButton leftArrowButton;
    private RightArrowButton rightArrowButton;

    private DreamNotesBookInfo dreamNotesBookInfo;

    private Component title;
    private String author;
    private Component content;
    private ResourceLocation GUI;
    private int GUI_X;
    private int GUI_Y;
    private int contentStartX;
    private int contentStartY;
    private int contentFinalX;
    private int contentFinalY;

    private int GUIStartX;
    private int GUIStartY;

    private int color;

    private List<List<String>> pageContent = new ArrayList<>();

    private int totalPage = 0;
    private int currentPage = 0;

    public DreamNotesBookScreen(DreamNotesBookInfo dreamNotesBookInfo)
    {
        super(Component.empty());
        this.dreamNotesBookInfo = dreamNotesBookInfo;
    }

    @Override
    protected void init()
    {
        super.init();
        serializerDreamNotesBookInfo();
        GUIStartX = width / 2 - GUI_X / 2;
        GUIStartY = height / 2 - GUI_Y / 2;

        leftArrowButton = new LeftArrowButton(width / 2 - 50, height - 20, button -> prevPage());
        rightArrowButton = new RightArrowButton(width / 2 + 28, height - 20, button -> nextPage());
        addRenderableWidget(leftArrowButton);
        addRenderableWidget(rightArrowButton);
    }

    private void serializerDreamNotesBookInfo()
    {
        if(dreamNotesBookInfo != null)
        {
            title = dreamNotesBookInfo.title();
            author = dreamNotesBookInfo.author();
            content = dreamNotesBookInfo.content();
            GUI = dreamNotesBookInfo.GUI();
            GUI_X = dreamNotesBookInfo.GUI_X();
            GUI_Y = dreamNotesBookInfo.GUI_Y();
            contentStartX = dreamNotesBookInfo.contentStartX();
            contentStartY = dreamNotesBookInfo.contentStartY();
            contentFinalX = dreamNotesBookInfo.contentFinalX();
            contentFinalY = dreamNotesBookInfo.contentFinalY();
            color = dreamNotesBookInfo.color();

            pageContent = warpListTextToPage(wrapTextToLine(content.getString(), contentFinalX - contentStartX), contentFinalY - contentStartY);
            totalPage = pageContent.size() + 1;
        }
            else
            {
                title = null;
                author = null;
                content = null;
                GUI = GUIBackGroundRender.DREAM_NOTES_BOOK_DYEDREAM_WORLD_GUI;
                GUI_X = 140;
                GUI_Y = 180;
                contentStartX = 10;
                contentStartY = 10;
                contentFinalX = 130;
                contentFinalY = 170;
                color = 0xFFFFFFFF;

                totalPage = 1;
            }
    }

    //将字符串按宽度拆分为行
    private List<String> wrapTextToLine(String text, int width)
    {
        List<String> lines = new ArrayList<>();
        String[] paragraphs = text.replace("\r\n", "\n").replace("$(br)", "\n").split("\n", -1);
        for (String paragraph : paragraphs)
        {
            if (paragraph.isEmpty())
            {
                lines.add("");
            }
                else
                {
                    if (font.width(paragraph) <= Math.max(8, width))
                    {
                        lines.add(paragraph);
                    }
                        else
                        {
                            String remaining = paragraph;
                            while (!remaining.isEmpty())
                            {
                                String trimmed = font.plainSubstrByWidth(remaining, Math.max(8, width));
                                remaining = remaining.substring(trimmed.length());
                                lines.add(trimmed);
                            }
                        }
                }
        }
        return lines;
    }

    //将拆分成行的字符串按高度拆分页
    private List<List<String>> warpListTextToPage(List<String> warpedText, int height)
    {
        List<List<String>> pages = new ArrayList<>();

        int linesPerPage = Math.max(1, (height / font.lineHeight));
        int totalLine = warpedText.size();
        int currentLine = 0;

        while(currentLine < totalLine)
        {
            List<String> buffer = new ArrayList<>();
            for (int i = 0; i < linesPerPage; i++)
            {
                if(currentLine < totalLine)
                {
                    buffer.add(warpedText.get(currentLine));
                }
                    else
                    {
                        break;
                    }
                currentLine++;
            }
            pages.add(buffer);
        }

        return pages;
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

    private void nextPage()
    {
        if(currentPage < totalPage - 1)
        {
            currentPage++;
            playPageTurnSound();
        }
    }

    private void prevPage()
    {
        if(currentPage > 0)
        {
            currentPage--;
            playPageTurnSound();
        }
    }

    private void playPageTurnSound()
    {
        if (Minecraft.getInstance().player != null)
        {
            Minecraft.getInstance().player.playSound(SoundEvents.BOOK_PAGE_TURN, 1.0f, 1.0f);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        renderBackground(guiGraphics);

        guiGraphics.blit(GUI, GUIStartX, GUIStartY, 0, 0, GUI_X, GUI_Y, GUI_X, GUI_Y);
        rendPageContent(guiGraphics);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
        drawCenteredStringWithOutShadow(guiGraphics, width / 2, height - 12, (currentPage + 1) + " / " + totalPage, 0xFFFFFFFF);
    }

    private void rendPageContent(GuiGraphics guiGraphics)
    {
        if(dreamNotesBookInfo == null)
        {
            drawCenteredStringWithOutShadow(guiGraphics, width / 2, height / 2, "内容无法解析！", 0xFFFF0000);
            return;
        }

        if(currentPage == 0)
        {
            drawCenteredStringWithOutShadow(guiGraphics, width / 2, GUIStartY + contentStartY + 4 * font.lineHeight, title.getString(), color);
            drawCenteredStringWithOutShadow(guiGraphics, width / 2, GUIStartY + contentStartY + 6 * font.lineHeight, author, color);
        }
            else
            {
                List<String> stringInPage = pageContent.get(currentPage - 1);
                int totalLines = stringInPage.size();

                for(int i = 0; i < totalLines; i++)
                {
                    guiGraphics.drawString(font, stringInPage.get(i), GUIStartX + contentStartX, GUIStartY + contentStartY + i * font.lineHeight, color, false);
                }
            }
    }

    private void drawCenteredStringWithOutShadow(GuiGraphics guiGraphics, int x, int y, String string, int color)
    {
        int width = font.width(string);
        int height = font.lineHeight;

        guiGraphics.drawString(font, string, x - width / 2, y - height / 2, color, false);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
