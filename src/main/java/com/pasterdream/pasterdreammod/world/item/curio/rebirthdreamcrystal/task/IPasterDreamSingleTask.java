package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public interface IPasterDreamSingleTask
{
    Component getTaskName();
    boolean isTaskProgressShowWithIntOrDouble();
    double getTaskProgress(Player player);
    double getTaskMaxProgress(Player player);

    default boolean isFinished(Player player)
    {
        return getTaskProgress(player) >= getTaskMaxProgress(player);
    }
}
