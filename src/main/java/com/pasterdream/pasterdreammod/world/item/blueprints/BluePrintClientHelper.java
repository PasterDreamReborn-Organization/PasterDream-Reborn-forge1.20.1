package com.pasterdream.pasterdreammod.world.item.blueprints;

import com.pasterdream.pasterdreammod.helper.localnbtreader.LocalNBTReader;
import net.minecraft.client.Minecraft;

/**
 * 仅客户端加载的蓝图客户端辅助类。
 * 持有 net.minecraft.client 引用的逻辑必须放在这里，
 * 避免在专用服务器加载 BluePrintItem 时触发客户端类加载。
 */
public class BluePrintClientHelper
{
    public static void openBluePrintScreen(BluePrintInfo info)
    {
        Minecraft.getInstance().setScreen(new BluePrintScreen(
                LocalNBTReader.getCompoundTag(info.materialNBT()),
                LocalNBTReader.getCompoundTag(info.resultNBT())));
    }
}