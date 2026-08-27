package com.pasterdream.pasterdreammod.world.item.prophecycard;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

/**
 * 仅客户端加载的预言卡客户端效果辅助类。
 * 持有 net.minecraft.client 引用的逻辑必须放在这里，
 * 避免在专用服务器加载 ProphecyCardItem 时触发客户端类加载。
 */
public class ProphecyCardClientEffect
{
    public static void showTotemEffect(ItemStack stack)
    {
        Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
    }
}