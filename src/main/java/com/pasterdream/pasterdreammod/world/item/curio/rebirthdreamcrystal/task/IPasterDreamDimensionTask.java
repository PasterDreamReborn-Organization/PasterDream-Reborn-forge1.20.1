package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IPasterDreamDimensionTask
{
    List<IPasterDreamSingleTask> getAllTasks(Player player);
    ItemStack getUploadItemStack(Player player);
    Runnable completeTask(Player player);
}
