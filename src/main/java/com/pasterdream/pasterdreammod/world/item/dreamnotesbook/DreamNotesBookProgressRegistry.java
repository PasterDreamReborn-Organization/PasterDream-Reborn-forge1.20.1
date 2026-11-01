package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class DreamNotesBookProgressRegistry
{
    private static Map<String, String> NOTES_DIMENSION = new HashMap<>();
    private static Map<String, List<String>> DIMENSION_NOTES = new HashMap<>();
    private static List<String> ALL_DIMENSIONS = new ArrayList<>();
    private static Map<String, ResourceLocation> DIMENSION_GUI = new HashMap<>();

    public static void notesDimensionRegister(String contentName, String dimension)
    {
        if(NOTES_DIMENSION.containsKey(contentName))
        {
            throw new IllegalArgumentException("重复的寻梦者笔记（书）维度绑定：" + contentName);
        }
        NOTES_DIMENSION.put(contentName, dimension);
        DIMENSION_NOTES.computeIfAbsent(dimension, string -> new ArrayList<>()).add(contentName);
    }

    public static void dimensionListRegister(String dimension)
    {
        ALL_DIMENSIONS.add(dimension);
    }

    public static void dimensionGUIRegister(String dimension, ResourceLocation gui)
    {
        if(DIMENSION_GUI.containsKey(dimension))
        {
            throw new IllegalArgumentException("重复的维度GUI绑定：" + dimension);
        }
        DIMENSION_GUI.put(dimension, gui);
    }

    public static List<String> getAllDimensions()
    {
        return ALL_DIMENSIONS;
    }

    public static String getDimensionByNotes(String contentName)
    {
        return NOTES_DIMENSION.getOrDefault(contentName, null);
    }

    public static List<String> getNotesByDimension(String dimension)
    {
        return DIMENSION_NOTES.getOrDefault(dimension, new ArrayList<>());
    }

    public static ResourceLocation getResourceLocationByDimension(String dimension)
    {
        return DIMENSION_GUI.get(dimension);
    }
}
