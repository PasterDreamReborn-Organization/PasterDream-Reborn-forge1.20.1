package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolBlockEditorMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncBlockStatePacket
{
    private final String stateString;

    public SyncBlockStatePacket(String string)
    {
        this.stateString = string;
    }

    public SyncBlockStatePacket(FriendlyByteBuf buffer)
    {
        this.stateString = buffer.readUtf();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeUtf(stateString);
    }

    public static void handle(SyncBlockStatePacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            Player player = Minecraft.getInstance().player;
            if (player != null && player.containerMenu instanceof DebugToolBlockEditorMenu menu)
            {
                menu.setServerBlockStateString(packet.stateString);
            }
        });
        context.get().setPacketHandled(true);
    }
}
