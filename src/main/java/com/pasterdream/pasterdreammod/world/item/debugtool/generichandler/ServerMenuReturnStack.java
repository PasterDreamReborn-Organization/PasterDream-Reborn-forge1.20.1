package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.WeakHashMap;

public class ServerMenuReturnStack
{
    private static final Map<Player, MenuProvider> STACK = new WeakHashMap<>();

    private ServerMenuReturnStack(){}

    public static void push(Player player, MenuProvider provider)
    {
        STACK.put(player, provider);
    }

    public static MenuProvider pop(Player player)
    {
        return STACK.remove(player);
    }

    public static void clear(Player player)
    {
        STACK.remove(player);
    }
}
