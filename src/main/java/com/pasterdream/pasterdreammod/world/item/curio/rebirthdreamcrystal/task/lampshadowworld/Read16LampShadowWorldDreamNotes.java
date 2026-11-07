package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.lampshadowworld;

import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata.PasterDreamPlayerDreamNoteProgressData;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamSingleTask;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Read16LampShadowWorldDreamNotes implements IPasterDreamSingleTask
{
    @Override
    public Component getTaskName()
    {
        return Component.translatable("task.pasterdream.阅读16本灯影之下寻梦者笔记");
    }

    @Override
    public boolean isTaskProgressShowWithIntOrDouble()
    {
        return true;
    }

    @Override
    public double getTaskProgress(Player player)
    {
        return PasterDreamPlayerDreamNoteProgressData.getProgressData(player, "lamp_shadow_world").stream().filter(Boolean.TRUE::equals).count();
    }

    @Override
    public double getTaskMaxProgress(Player player)
    {
        return 16;
    }
}
