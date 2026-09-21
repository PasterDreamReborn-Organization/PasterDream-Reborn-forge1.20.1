package com.pasterdream.pasterdreammod.world.item.debugtool.widget;

import com.pasterdream.pasterdreammod.helper.stringhelper.StringHelper;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.NbtSaveHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class NBTEditorWidget extends AbstractScrollWidget
{
    private final int lineHeight = Minecraft.getInstance().font.lineHeight + 1;
    private final MultilineTextField textField;

    public NBTEditorWidget(int x, int y, int width, int height, String string, NbtSaveHandler onSave)
    {
        super(x, y, width, height, Component.literal(""));
        this.textField = new MultilineTextField();
        this.textField.setWidgetWidth(width - 8);

        this.textField.setAllString(new StringBuilder(string == null ? "" : string));
        this.textField.refreshLines();
        this.textField.cancelSelection();
        this.textField.syncLineAndColumnFromIndex();
    }

    @Override
    protected int getInnerHeight()
    {
        return Math.max(lineHeight, textField.getLines().size() * (lineHeight));
    }

    @Override
    protected double scrollRate()
    {
        return lineHeight;
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        int lineY = getY() + 2;
        for (int i = 0; i < textField.getLines().size(); i++)
        {
            String line = textField.getLines().get(i).toString();
            guiGraphics.drawString(Minecraft.getInstance().font, line, getX() + 2, lineY, 0xFFFFFFFF, false);
            lineY += Minecraft.getInstance().font.lineHeight + 1;
        }

        if (isFocused() && (System.currentTimeMillis() / 500) % 2 == 0)
        {
            int cursorX = getX() + 1 + Minecraft.getInstance().font.width(textField.getLines().get(textField.getCursorLine()).substring(0, textField.getCursorColumn()));
            int cursorY = getY() + 2 + textField.getCursorLine() * (lineHeight);
            guiGraphics.fill(cursorX, cursorY, cursorX + 1, cursorY + lineHeight - 2, 0xFFFFFFFF);
        }

        if (textField.getIsSelected())
        {
            int startLine = textField.getSelectionStartLine();
            int startColumn = textField.getSelectionStartColumn();
            int endLine = textField.getSelectionEndLine();
            int endColumn = textField.getSelectionEndColumn();

            if (startLine > endLine || (startLine == endLine && startColumn > endColumn))
            {
                int a = startLine;
                startLine = endLine;
                endLine = a;

                int b = startColumn;
                startColumn = endColumn;
                endColumn = b;
            }

            for (int i = startLine; i <= endLine; i++)
            {
                String lineText = textField.getLines().get(i);
                int lineLen = lineText.length();

                int fromString = (i == startLine) ? Math.min(startColumn, lineLen) : 0;
                int toString = (i == endLine) ? Math.min(endColumn, lineLen) : lineLen;

                int startX = getX() + 2 + Minecraft.getInstance().font.width(lineText.substring(0, fromString));
                int endX = getX() + 2 + Minecraft.getInstance().font.width(lineText.substring(0, toString));
                int startY  = getY() + 2 + i * lineHeight;

                guiGraphics.fill(startX, startY, endX, startY + lineHeight - 2, 0x800000FF);
            }
        }
    }

    @Override
    protected void renderDecorations(GuiGraphics guiGraphics)
    {
        int sliderHeight = this.getScrollBarHeight();
        int sliderStartX = this.getX() + this.width - 6;
        int sliderFinalX = this.getX() + this.width - 1;
        int sliderStartY;
        int sliderFinalY;

        if(sliderHeight >= height)
        {
            sliderHeight = height;
            sliderStartY = this.getY();
        }
            else
            {
                sliderStartY = Math.max(this.getY(), (int)this.scrollAmount * (this.height - sliderHeight) / this.getMaxScrollAmount() + this.getY());
            }

        sliderFinalY = sliderStartY + sliderHeight;

        guiGraphics.fill(sliderStartX, sliderStartY, sliderFinalX, sliderFinalY, -8355712);
        guiGraphics.fill(sliderStartX + 1, sliderStartY + 1, sliderFinalX - 1, sliderFinalY - 1, -4144960);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers)
    {
        if (!isFocused())
        {
            return false;
        }

        //如果有选区，先删除选中内容
        if (textField.getIsSelected())
        {
            textField.deleteSelection();
        }

        //在当前光标位置插入字符
        textField.insertText(codePoint);
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (!isFocused()) return false;

        switch (keyCode)
        {
            case GLFW.GLFW_KEY_BACKSPACE -> textField.backspace();
            case GLFW.GLFW_KEY_DELETE -> textField.delete();
            case GLFW.GLFW_KEY_ENTER -> textField.insertText('\n');
            case GLFW.GLFW_KEY_UP -> textField.moveCursorUp();
            case GLFW.GLFW_KEY_DOWN -> textField.moveCursorDown();
            case GLFW.GLFW_KEY_LEFT -> textField.moveCursorLeft();
            case GLFW.GLFW_KEY_RIGHT -> textField.moveCursorRight();
            case GLFW.GLFW_KEY_HOME -> textField.moveCursorToStart();
            case GLFW.GLFW_KEY_END -> textField.moveCursorToEnd();
            case GLFW.GLFW_KEY_A ->
            {
                if (Screen.hasControlDown())
                {
                    textField.selectAll();
                }
            }
            case GLFW.GLFW_KEY_C ->
            {
                if (Screen.hasControlDown())
                {
                    textField.copyToClipboard();
                }
            }
            case GLFW.GLFW_KEY_V ->
            {
                if (Screen.hasControlDown())
                {
                    textField.pasteFromClipboard();
                }
            }
            case GLFW.GLFW_KEY_X ->
            {
                if (Screen.hasControlDown())
                {
                    textField.cutToClipboard();
                }
            }
            default ->
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if(mouseY >= getY() && mouseY < getY() + height)
        {
            //滚动条
            if (mouseX >= getX() + width - 6 && mouseX < getX() + width - 1)
            {
                scrolling = true;
                return true;
            }
            else    //文本编辑
                if(mouseX >= getX() && mouseX < getX() + width - 6)
                {
                    int relX = (int) (mouseX - getX());
                    int relY = (int) (mouseY - getY());

                    int[] lineAndColumn = textField.getLineAndColumnFromCoordinates(relX, relY);
                    textField.setCursorLine(lineAndColumn[0]);
                    textField.setCursorColumn(lineAndColumn[1]);
                    textField.syncIndexFromLineAndColumn();
                    textField.setIsSelected(false);
                    textField.setSelectionStartLine(lineAndColumn[0]);
                    textField.setSelectionStartColumn(lineAndColumn[1]);
                    textField.setSelectionEndLine(lineAndColumn[0]);
                    textField.setSelectionEndColumn(lineAndColumn[1]);
                    return true;
                }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY)
    {
        if(scrolling)
        {
            if (mouseY < getY())
            {
                setScrollAmount(0);
            }
            else
                if (mouseY > getY() + height)
                {
                    setScrollAmount(getMaxScrollAmount());
                }
                    else
                    {
                        int barHeight = getScrollBarHeight();
                        double rate = (height == barHeight ? 1 : Math.max(1, getMaxScrollAmount() / (height - barHeight)));
                        setScrollAmount(scrollAmount + dragY * rate);
                    }
            return true;
        }
        else
            if(mouseX >= getX() && mouseX < getX() + width - 6 && mouseY >= getY() && mouseY <= getY() + height)
            {
                int relX = (int) (mouseX - getX());
                int relY = (int) (mouseY - getY());
                int[] lineAndColumn = textField.getLineAndColumnFromCoordinates(relX, relY);

                textField.setCursorLine(lineAndColumn[0]);
                textField.setCursorColumn(lineAndColumn[1]);
                textField.syncIndexFromLineAndColumn();
                textField.setSelectionEndLine(lineAndColumn[0]);
                textField.setSelectionEndColumn(lineAndColumn[1]);

                textField.setIsSelected(!(textField.getSelectionStartLine() == textField.getSelectionEndLine() && textField.getSelectionStartColumn() == textField.getSelectionEndColumn()));
                return true;
            }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button)
    {
        scrolling = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput)
    {

    }

    public String getString()
    {
        return textField.allString.toString();
    }

    public class MultilineTextField
    {
        private StringBuilder allString = new StringBuilder();
        private List<String> lines = new ArrayList<>();
        private int cursorLine = 0;
        private int cursorColumn = 0;
        private int cursorIndex = 0;
        private int selectionStartLine = 0;
        private int selectionStartColumn = 0;
        private int selectionEndLine = 0;
        private int selectionEndColumn = 0;
        private boolean isSelected = false;
        private int widgetWidth = 8;

        public List<String> getLines()
        {
            return lines;
        }

        public int getCursorLine()
        {
            return cursorLine;
        }

        public int getCursorColumn()
        {
            return cursorColumn;
        }

        public int getCursorIndex()
        {
            return cursorIndex;
        }

        public int getSelectionStartLine()
        {
            return selectionStartLine;
        }

        public int getSelectionStartColumn()
        {
            return selectionStartColumn;
        }

        public int getSelectionEndLine()
        {
            return selectionEndLine;
        }

        public int getSelectionEndColumn()
        {
            return selectionEndColumn;
        }

        public boolean getIsSelected()
        {
            return isSelected;
        }

        public int getWidgetWidth()
        {
            return widgetWidth;
        }

        public void setAllString(StringBuilder allString)
        {
            this.allString = allString;
        }

        public void setLines(List<String> lines)
        {
            this.lines = lines;
        }

        public void setCursorLine(int cursorLine)
        {
            this.cursorLine = cursorLine;
        }

        public void setCursorColumn(int cursorColumn)
        {
            this.cursorColumn = cursorColumn;
        }

        public void setCursorIndex(int cursorIndex)
        {
            this.cursorIndex = cursorIndex;
        }

        public void setSelectionStartLine(int selectionStartLine)
        {
            this.selectionStartLine = selectionStartLine;
        }

        public void setSelectionStartColumn(int selectionStartColumn)
        {
            this.selectionStartColumn = selectionStartColumn;
        }

        public void setSelectionEndLine(int selectionEndLine)
        {
            this.selectionEndLine = selectionEndLine;
        }

        public void setSelectionEndColumn(int selectionEndColumn)
        {
            this.selectionEndColumn = selectionEndColumn;
        }

        public void setIsSelected(boolean isSelected)
        {
            this.isSelected = isSelected;
        }

        public void setWidgetWidth(int widgetWidth)
        {
            this.widgetWidth = Math.max(8, widgetWidth);
            refreshLines();
            syncLineAndColumnFromIndex();
        }

        //通过行列数获取字符引索
        public int getCharIndexFromLineAndColumn(int line, int column)
        {
            int charIndex = 0;
            for(int i = 0; i < line; i++)
            {
                charIndex += lines.get(i).length();
            }
            charIndex += column;

            return charIndex;
        }

        //通过字符引索获取行列数
        public int[] getLineAndColumnFromCharIndex(int charIndex)
        {
            int line = 0;
            int column = 0;
            int charCount = 0;
            for(int i = 0; i < lines.size(); i++)
            {
                if(i != lines.size() - 1)
                {
                    if(charCount + lines.get(i).length() <= charIndex)
                    {
                        charCount += lines.get(i).length();
                    }
                        else
                        {
                            line = i;
                            column = charIndex - charCount;
                            break;
                        }
                }
                    else
                    {
                        line = i;
                        column = charIndex - charCount;
                        break;
                    }
            }

            return new int[]{line, column};
        }

        //通过光标引索同步行列数
        public void syncLineAndColumnFromIndex()
        {
            int[] lineAndColumn = getLineAndColumnFromCharIndex(cursorIndex);
            cursorLine = lineAndColumn[0];
            cursorColumn = lineAndColumn[1];
        }

        //通过行列数同步光标引索
        public void syncIndexFromLineAndColumn()
        {
            cursorIndex = getCharIndexFromLineAndColumn(cursorLine, cursorColumn);
        }

        //获取选取始末字符引索
        public int[] getSelectionStartAndEndCharIndex()
        {
            int start = getCharIndexFromLineAndColumn(selectionStartLine, selectionStartColumn);
            int end = getCharIndexFromLineAndColumn(selectionEndLine, selectionEndColumn);
            return start <= end ? new int[]{start, end} : new int[]{end, start};
        }

        //获取选取字符
        public String getSelectionString(int[] selection)
        {
            return allString.substring(selection[0], selection[1]);
        }

        //通过屏幕坐标获取行列数
        public int[] getLineAndColumnFromCoordinates(int x, int y)
        {
            int line = (y - 2) / lineHeight;
            if(line >= lines.size())
            {
                line = lines.size() - 1;
            }

            int column = Minecraft.getInstance().font.plainSubstrByWidth(lines.get(line), Math.max(0, x)).length();
            return new int[]{line, column};
        }

        //光标距离左侧的像素距离
        public int getDistanceFromCursorToLeft()
        {
            return Minecraft.getInstance().font.width(lines.get(cursorLine).substring(0, cursorColumn));
        }

        //删除选区
        public void deleteSelection()
        {
            int[] selection = textField.getSelectionStartAndEndCharIndex();
            allString.delete(selection[0], selection[1]);
            if(selectionStartLine < selectionEndLine || selectionStartLine == selectionEndLine && selectionStartColumn <= selectionEndColumn)
            {
                cursorLine = selectionStartLine;
                cursorColumn = selectionStartColumn;
            }
                else
                {
                    cursorLine = selectionEndLine;
                    cursorColumn = selectionEndColumn;
                }

            syncIndexFromLineAndColumn();
            cancelSelection();
            refreshLines();
        }

        //插入字符
        public void insertText(char codePoint)
        {
            if(isSelected)
            {
                deleteSelection();
            }

            allString.insert(cursorIndex, codePoint);
            refreshLines();
            moveCursorRight();
        }

        //BackSpace
        public void backspace()
        {
            if(isSelected)
            {
                deleteSelection();
            }
            else
                if(cursorIndex > 0)
                {
                    allString.deleteCharAt(getCharIndexFromLineAndColumn(cursorLine, cursorColumn) - 1);
                    moveCursorLeft();
                    refreshLines();
                }
        }

        //delete
        public void delete()
        {
            if(isSelected)
            {
                deleteSelection();
            }
            else
                if(cursorIndex < allString.length())
                {
                    allString.deleteCharAt(getCharIndexFromLineAndColumn(cursorLine, cursorColumn));
                    refreshLines();
                }
        }

        //全选
        public void selectAll()
        {
            isSelected = true;
            selectionStartLine = 0;
            selectionStartColumn = 0;
            selectionEndLine = lines.size() - 1;
            selectionEndColumn = lines.get(selectionEndLine).length();
        }

        //方向键上
        public void moveCursorUp()
        {
            cancelSelection();
            if(cursorLine > 0)
            {
                cursorColumn = Minecraft.getInstance().font.plainSubstrByWidth(lines.get(cursorLine - 1), getDistanceFromCursorToLeft()).length();
                cursorLine--;
                syncIndexFromLineAndColumn();
            }
        }

        //方向键下
        public void moveCursorDown()
        {
            cancelSelection();
            if(cursorLine < lines.size() - 1)
            {
                cursorColumn = Minecraft.getInstance().font.plainSubstrByWidth(lines.get(cursorLine + 1), getDistanceFromCursorToLeft()).length();
                cursorLine++;
                syncIndexFromLineAndColumn();
            }
        }

        //方向键左
        public void moveCursorLeft()
        {
            cancelSelection();
            if(cursorIndex > 0)
            {
                cursorIndex--;
                syncLineAndColumnFromIndex();
            }
        }

        //方向键右
        public void moveCursorRight()
        {
            cancelSelection();
            if(cursorIndex < allString.length())
            {
                cursorIndex++;
                syncLineAndColumnFromIndex();
            }
        }

        //复制
        public void copyToClipboard()
        {
            String selected = getSelectionString(getSelectionStartAndEndCharIndex());
            if (!selected.isEmpty())
            {
                Minecraft.getInstance().keyboardHandler.setClipboard(selected);
            }
        }

        //粘贴
        public void pasteFromClipboard()
        {
            String content = Minecraft.getInstance().keyboardHandler.getClipboard();

            if(isSelected)
            {
                deleteSelection();
            }

            allString.insert(cursorIndex, content);
            cursorIndex += content.length();
            refreshLines();
            syncLineAndColumnFromIndex();
        }

        //剪切
        public void cutToClipboard()
        {
            String selected = getSelectionString(getSelectionStartAndEndCharIndex());
            if (!selected.isEmpty())
            {
                Minecraft.getInstance().keyboardHandler.setClipboard(selected);
                deleteSelection();
            }
        }

        //回到开头
        public void moveCursorToStart()
        {
            cancelSelection();
            cursorLine = 0;
            cursorColumn = 0;
            cursorIndex = 0;
        }

        //去到结尾
        public void moveCursorToEnd()
        {
            cancelSelection();
            cursorLine = lines.size() - 1;
            cursorColumn = lines.get(cursorLine).length() + 1;
            cursorIndex = allString.length();
        }

        //取消选择
        public void cancelSelection()
        {
            isSelected = false;
            selectionStartLine = 0;
            selectionStartColumn = 0;
            selectionEndLine = 0;
            selectionEndColumn = 0;
        }

        //刷新每行内容
        public void refreshLines()
        {
            lines = StringHelper.ListStringFromString(allString.toString(), widgetWidth);
        }
    }
}
