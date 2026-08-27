package com.pasterdream.pasterdreammod.world.item.dreamnotes;

import net.minecraft.client.Minecraft;

/**
 * 仅客户端加载的梦笔记客户端辅助类。
 * 持有 net.minecraft.client 引用的逻辑必须放在这里，
 * 避免在专用服务器加载 DreamNotesItem 时触发客户端类加载。
 */
public class DreamNotesClientHelper
{
    public static void openDreamNotesScreen(String content)
    {
        Minecraft.getInstance().setScreen(new DreamNotesScreen(content));
    }
}