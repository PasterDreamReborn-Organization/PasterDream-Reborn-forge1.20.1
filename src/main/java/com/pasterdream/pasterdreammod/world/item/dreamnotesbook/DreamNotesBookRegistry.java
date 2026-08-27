package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class DreamNotesBookRegistry
{
    private static final Map<String, DreamNotesBookInfo> NOTES = new HashMap<>();
    private static final List<ResourceLocation> MODEL_LOCATIONS = new ArrayList<>();

    public static void register(String contentName, Component title, String author, Component content, ResourceLocation GUI, int GUI_X, int GUI_Y, int contentStartX, int contentStartY, int contentFinalX, int contentFinalY, ResourceLocation itemTexture, int color)
    {
        if (NOTES.containsKey(contentName))
        {
            throw new IllegalArgumentException("重复的寻梦者笔记（书）内容：" + contentName);
        }
        NOTES.put(contentName, new DreamNotesBookInfo(title, author, content, GUI, GUI_X, GUI_Y, contentStartX, contentStartY, contentFinalX, contentFinalY, itemTexture, color));
        MODEL_LOCATIONS.add(itemTexture);
    }

    public static DreamNotesBookInfo getInfo(String contentName)
    {
        return NOTES.get(contentName);
    }

    public static List<ResourceLocation> getModelLocations()
    {
        return Collections.unmodifiableList(MODEL_LOCATIONS);
    }
}
