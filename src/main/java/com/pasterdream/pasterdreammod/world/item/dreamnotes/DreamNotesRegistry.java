package com.pasterdream.pasterdreammod.world.item.dreamnotes;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DreamNotesRegistry
{
    private static final Map<String, DreamNotesInfo> NOTES = new ConcurrentHashMap<>();

    public static void register(String content, int width, int height, ResourceLocation texture)
    {
        if (NOTES.containsKey(content))
        {
            throw new IllegalArgumentException("重复的寻梦者笔记内容：" + content);
        }
        NOTES.put(content, new DreamNotesInfo(width, height, texture));
    }

    public static DreamNotesInfo getInfo(String content)
    {
        return NOTES.get(content);
    }
}
