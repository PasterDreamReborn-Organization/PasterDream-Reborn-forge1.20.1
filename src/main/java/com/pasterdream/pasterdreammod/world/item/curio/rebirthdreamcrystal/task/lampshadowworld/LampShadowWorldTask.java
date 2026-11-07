package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.lampshadowworld;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata.PasterDreamPlayerDreamNoteProgressData;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamDimensionTask;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamSingleTask;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LampShadowWorldTask implements IPasterDreamDimensionTask
{
    @Override
    public List<IPasterDreamSingleTask> getAllTasks(Player player)
    {
        List<IPasterDreamSingleTask> tasks = new CopyOnWriteArrayList<>();

        tasks.add(new Read16LampShadowWorldDreamNotes());
        tasks.add(new Kill200ShadowMobs());

        return tasks;
    }

    @Override
    public ItemStack getUploadItemStack(Player player)
    {
        return new ItemStack(ModItems.PURE_HORROR.get());
    }

    @Override
    public Runnable completeTask(Player player)
    {
        return () -> PasterDreamPlayerDreamNoteProgressData.setTaskStatus(player, "lamp_shadow_world", true);
    }
}
