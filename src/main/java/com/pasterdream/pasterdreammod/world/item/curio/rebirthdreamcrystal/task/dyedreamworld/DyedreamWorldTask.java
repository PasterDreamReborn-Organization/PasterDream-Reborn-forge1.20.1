package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.dyedreamworld;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata.PasterDreamPlayerDreamNoteProgressData;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamDimensionTask;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamSingleTask;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DyedreamWorldTask implements IPasterDreamDimensionTask
{
    @Override
    public List<IPasterDreamSingleTask> getAllTasks(Player player)
    {
        List<IPasterDreamSingleTask> tasks = new CopyOnWriteArrayList<>();

        tasks.add(new Read30DyedreamWorldDreamNotes());

        return tasks;
    }

    @Override
    public ItemStack getUploadItemStack(Player player)
    {
        return new ItemStack(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get());
    }

    @Override
    public Runnable completeTask(Player player)
    {
        return () -> PasterDreamPlayerDreamNoteProgressData.setTaskStatus(player, "dyedream_world", true);
    }
}
