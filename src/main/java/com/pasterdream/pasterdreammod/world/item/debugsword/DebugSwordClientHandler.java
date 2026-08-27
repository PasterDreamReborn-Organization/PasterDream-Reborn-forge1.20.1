package com.pasterdream.pasterdreammod.world.item.debugsword;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * 仅客户端加载的调试之剑客户端处理辅助类。
 * 持有 net.minecraft.client 引用的逻辑必须放在这里，
 * 避免在专用服务器加载 DebugSwordHandler 时触发客户端类加载。
 */
public class DebugSwordClientHandler
{
    public static void showBlockOptionsScreen(PlayerInteractEvent.LeftClickBlock event, Player player)
    {
        if (Minecraft.getInstance().screen instanceof DebugBlockOptionsScreen)
        {
            return;
        }

        Minecraft.getInstance().setScreen(new DebugBlockOptionsScreen(event.getPos(), player, player.level()));
    }
}