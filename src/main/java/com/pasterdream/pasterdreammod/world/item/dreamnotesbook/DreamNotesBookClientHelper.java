package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import net.minecraft.client.Minecraft;

/**
 * 仅客户端加载的寻梦者笔记（书）客户端辅助类。
 * 持有 net.minecraft.client 引用的逻辑必须放在这里，
 * 避免在专用服务器加载 DreamNotesBookItem 时触发客户端类加载。
 */
public class DreamNotesBookClientHelper
{
    public static void openDreamNotesBookScreen(DreamNotesBookInfo info)
    {
        Minecraft.getInstance().setScreen(new DreamNotesBookScreen(info));
    }
}