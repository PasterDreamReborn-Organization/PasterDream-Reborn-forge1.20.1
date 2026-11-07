package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.windjourneyworld;

import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamSingleTask;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class FinishAngelBeats implements IPasterDreamSingleTask
{
    @Override
    public Component getTaskName()
    {
        return Component.translatable("task.pasterdream.完成天使的心跳挑战");
    }

    @Override
    public boolean isTaskProgressShowWithIntOrDouble()
    {
        return true;
    }

    @Override
    public double getTaskProgress(Player player)
    {
        if(player instanceof ServerPlayer serverPlayer)
        {
            Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(ResourceLocation.fromNamespaceAndPath("pasterdream", "story/angel_beats"));
            return (advancement != null && serverPlayer.getAdvancements().getOrStartProgress(advancement).isDone()) ? 1 : 0;
        }
        return 0;
    }

    @Override
    public double getTaskMaxProgress(Player player)
    {
        return 1;
    }
}
