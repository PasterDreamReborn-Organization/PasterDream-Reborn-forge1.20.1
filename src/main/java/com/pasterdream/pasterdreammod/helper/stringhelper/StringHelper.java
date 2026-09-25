package com.pasterdream.pasterdreammod.helper.stringhelper;

import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class StringHelper
{
    public static List<String> ListStringFromString(String string, int width)
    {
        List<String> lines = new ArrayList<>();
        String[] paragraphs = string.replace("\r\n", "\n").replace("$(br)", "\n").split("\n", -1);
        for (String paragraph : paragraphs)
        {
            if (paragraph.isEmpty())
            {
                lines.add("");
            }
            else
                if (Minecraft.getInstance().font.width(paragraph) <= Math.max(8, width))
                {
                    lines.add(paragraph);
                }
                    else
                    {
                        String remaining = paragraph;
                        while (!remaining.isEmpty())
                        {
                            String trimmed = Minecraft.getInstance().font.plainSubstrByWidth(remaining, Math.max(8, width));
                            remaining = remaining.substring(trimmed.length());
                            lines.add(trimmed);
                        }
                    }
        }
        return lines;
    }
}
