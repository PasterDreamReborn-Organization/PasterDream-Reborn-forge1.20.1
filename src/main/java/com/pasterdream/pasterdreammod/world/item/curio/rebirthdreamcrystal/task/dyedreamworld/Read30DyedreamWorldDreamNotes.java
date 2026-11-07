package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.dyedreamworld;

import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata.PasterDreamPlayerDreamNoteProgressData;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamSingleTask;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Read30DyedreamWorldDreamNotes implements IPasterDreamSingleTask
{
    @Override
    public Component getTaskName()
    {
        return Component.translatable("task.pasterdream.阅读30本染梦世界寻梦者笔记");
    }

    @Override
    public boolean isTaskProgressShowWithIntOrDouble()
    {
        return true;
    }

    @Override
    public double getTaskProgress(Player player)
    {
        return PasterDreamPlayerDreamNoteProgressData.getProgressData(player, "dyedream_world").stream().filter(Boolean.TRUE::equals).count();
    }

    @Override
    public double getTaskMaxProgress(Player player)
    {
        return 30;
    }
}
