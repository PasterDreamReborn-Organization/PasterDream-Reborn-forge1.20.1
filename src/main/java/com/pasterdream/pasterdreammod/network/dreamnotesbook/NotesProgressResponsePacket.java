package com.pasterdream.pasterdreammod.network.dreamnotesbook;

import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.RebirthDreamCrystalScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class NotesProgressResponsePacket
{
    private String dimension;
    private List<Boolean> progressData;
    List<Double> taskProgress;
    List<Double> taskMaxProgress;
    ItemStack needUploadItemStack;
    boolean isCompleteThisDimensionTasks;

    public NotesProgressResponsePacket(String dimension, List<Boolean> progressData, List<Double> taskProgress, List<Double> taskMaxProgress, ItemStack needUploadItemStack, boolean isCompleteThisDimensionTasks)
    {
        this.dimension = dimension;
        this.progressData = progressData;
        this.taskProgress = taskProgress;
        this.taskMaxProgress = taskMaxProgress;
        this.needUploadItemStack = needUploadItemStack;
        this.isCompleteThisDimensionTasks = isCompleteThisDimensionTasks;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeUtf(dimension);

        buffer.writeVarInt(progressData.size());
        for(boolean isRead : progressData)
        {
            buffer.writeBoolean(isRead);
        }

        buffer.writeVarInt(taskProgress.size());
        for(double progress : taskProgress)
        {
            buffer.writeDouble(progress);
        }

        buffer.writeVarInt(taskMaxProgress.size());
        for(double maxProgress : taskMaxProgress)
        {
            buffer.writeDouble(maxProgress);
        }

        buffer.writeItem(needUploadItemStack);

        buffer.writeBoolean(isCompleteThisDimensionTasks);
    }

    public static NotesProgressResponsePacket decode(FriendlyByteBuf buffer)
    {
        String dimension = buffer.readUtf();

        int progressDataSize = buffer.readVarInt();
        List<Boolean> progressData = new ArrayList<>(progressDataSize);
        for (int i = 0; i < progressDataSize; i++)
        {
            progressData.add(buffer.readBoolean());
        }

        int taskProgressSize = buffer.readVarInt();
        List<Double> taskProgressSizeData = new ArrayList<>(taskProgressSize);
        for (int i = 0; i < taskProgressSize; i++)
        {
            taskProgressSizeData.add(buffer.readDouble());
        }

        int taskMaxProgressSize = buffer.readVarInt();
        List<Double> taskMaxProgressSizeData = new ArrayList<>(taskMaxProgressSize);
        for (int i = 0; i < taskMaxProgressSize; i++)
        {
            taskMaxProgressSizeData.add(buffer.readDouble());
        }

        ItemStack needUploadItemStack = buffer.readItem();

        boolean isCompleteThisDimensionTasks = buffer.readBoolean();

        return new NotesProgressResponsePacket(dimension, progressData, taskProgressSizeData, taskMaxProgressSizeData, needUploadItemStack, isCompleteThisDimensionTasks);
    }

    public static void handle(NotesProgressResponsePacket message, Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->
        {
            if (Minecraft.getInstance().screen instanceof RebirthDreamCrystalScreen screen)
            {
                screen.onNotesProgressReceived(message.dimension, message.progressData, message.taskProgress, message.taskMaxProgress, message.needUploadItemStack, message.isCompleteThisDimensionTasks);
            }
        });
        context.setPacketHandled(true);
    }
}
