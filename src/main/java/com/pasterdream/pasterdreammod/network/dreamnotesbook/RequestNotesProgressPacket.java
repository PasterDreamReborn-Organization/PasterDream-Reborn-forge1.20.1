package com.pasterdream.pasterdreammod.network.dreamnotesbook;

import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata.PasterDreamPlayerDreamNoteProgressData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

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
                ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new NotesProgressResponsePacket(message.dimension, progressData));
            }
        });
        context.setPacketHandled(true);
    }
}
