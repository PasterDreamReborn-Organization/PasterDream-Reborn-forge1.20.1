package com.pasterdream.pasterdreammod.network.dreamnotesbook;

import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.RebirthDreamCrystalScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class NotesProgressResponsePacket
{
    private String dimension;
    private List<Boolean> progressData;

    public NotesProgressResponsePacket(String dimension, List<Boolean> progressData)
    {
        this.dimension = dimension;
        this.progressData = progressData;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeUtf(dimension);
        buffer.writeVarInt(progressData.size());
        for(boolean isRead : progressData)
        {
            buffer.writeBoolean(isRead);
        }
    }

    public static NotesProgressResponsePacket decode(FriendlyByteBuf buffer)
    {
        String dimension = buffer.readUtf();
        int size = buffer.readVarInt();
        List<Boolean> progressData = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
        {
            progressData.add(buffer.readBoolean());
        }
        return new NotesProgressResponsePacket(dimension, progressData);
    }

    public static void handle(NotesProgressResponsePacket message, Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->
        {
            if (Minecraft.getInstance().screen instanceof RebirthDreamCrystalScreen screen)
            {
                screen.onNotesProgressReceived(message.dimension, message.progressData);
            }
        });
        context.setPacketHandled(true);
    }
}
