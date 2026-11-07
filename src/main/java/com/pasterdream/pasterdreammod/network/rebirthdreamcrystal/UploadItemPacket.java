package com.pasterdream.pasterdreammod.network.rebirthdreamcrystal;

import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.RebirthDreamCrystalMenu;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata.PasterDreamPlayerDreamNoteProgressData;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamDimensionTask;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamSingleTask;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookProgressRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UploadItemPacket
{
    private String dimension;

    public UploadItemPacket(String dimension)
    {
        this.dimension = dimension;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeUtf(dimension);
    }

    public static UploadItemPacket decode(FriendlyByteBuf buffer)
    {
        return new UploadItemPacket(buffer.readUtf());
    }

    public static void handle(UploadItemPacket message, Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->
        {
            ServerPlayer player = context.getSender();
            if (player != null)
            {
                AbstractContainerMenu menu = player.containerMenu;
                if(menu instanceof RebirthDreamCrystalMenu rebirthDreamCrystalMenu)
                {
                    IPasterDreamDimensionTask dimensionTask = DreamNotesBookProgressRegistry.getTaskByDimension(message.dimension);
                    if(dimensionTask != null && dimensionTask.isAllSingleTaskCompleted(player) && !PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, message.dimension))
                    {
                        ItemStack needUploadItemStack = dimensionTask.getUploadItemStack(player);
                        ItemStack currentUploadItemStack = rebirthDreamCrystalMenu.getUploadSlotItemStack();
                        if(ItemStack.isSameItem(needUploadItemStack, currentUploadItemStack) && currentUploadItemStack.getCount() >= needUploadItemStack.getCount())
                        {
                            currentUploadItemStack.shrink(needUploadItemStack.getCount());
                            PasterDreamPlayerDreamNoteProgressData.setItem(player, currentUploadItemStack);
                            dimensionTask.completeTask(player).run();
                        }
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
