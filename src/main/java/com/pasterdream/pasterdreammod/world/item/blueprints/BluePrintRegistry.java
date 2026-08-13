package com.pasterdream.pasterdreammod.world.item.blueprints;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class BluePrintRegistry
{
    private static final Map<String,BluePrintInfo> BLUE_PRINTS = new HashMap<>();

    public static void register(String contentName, Component title, ResourceLocation materialNBT, ResourceLocation resultNBT)
    {
        if (BLUE_PRINTS.containsKey(contentName))
        {
            throw new IllegalArgumentException("重复的蓝图：" + contentName);
        }
        BLUE_PRINTS.put(contentName, new BluePrintInfo(title, materialNBT, resultNBT));
    }

    public static BluePrintInfo getInfo(String contentName)
    {
        return BLUE_PRINTS.get(contentName);
    }
}
