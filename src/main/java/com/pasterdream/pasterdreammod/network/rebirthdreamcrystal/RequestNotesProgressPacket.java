package com.pasterdream.pasterdreammod.network.rebirthdreamcrystal;

import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata.PasterDreamPlayerDreamNoteProgressData;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamDimensionTask;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamSingleTask;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookProgressRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RequestNotesProgressPacket
{
    private String dimension;

    public RequestNotesProgressPacket(String dimension)
    {
        this.dimension = dimension;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeUtf(dimension);
    }

    public static RequestNotesProgressPacket decode(FriendlyByteBuf buffer)
    {
        return new RequestNotesProgressPacket(buffer.readUtf());
    }

    public static void handle(RequestNotesProgressPacket message, Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->
        {
            ServerPlayer player = context.getSender();
            if (player != null)
            {
                List<Boolean> progressData = PasterDreamPlayerDreamNoteProgressData.getProgressData(player, message.dimension);
                IPasterDreamDimensionTask dimensionTask = DreamNotesBookProgressRegistry.getTaskByDimension(message.dimension);
                List<Double> taskProgress = new ArrayList<>();
                List<Double> taskMaxProgress = new ArrayList<>();
                ItemStack needUploadItemStack = ItemStack.EMPTY;
                boolean isCompleteThisDimensionTasks = false;

                if(dimensionTask != null)
                {
                    for(IPasterDreamSingleTask singleTask : dimensionTask.getAllTasks(player))
                    {
                        taskProgress.add(singleTask.getTaskProgress(player));
                        taskMaxProgress.add(singleTask.getTaskMaxProgress(player));
                    }
                    needUploadItemStack = dimensionTask.getUploadItemStack(player);
                    isCompleteThisDimensionTasks = PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, message.dimension);
                }

                ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new NotesProgressResponsePacket(message.dimension, progressData, taskProgress, taskMaxProgress, needUploadItemStack, isCompleteThisDimensionTasks));
            }
        });
        context.setPacketHandled(true);
    }
}
