package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolBlockEditorMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncBlockNbtPacket
{
    private final CompoundTag compoundTag;

    public SyncBlockNbtPacket(CompoundTag compoundTag)
    {
        this.compoundTag = compoundTag;
    }

    public SyncBlockNbtPacket(FriendlyByteBuf buffer)
    {
        this.compoundTag = buffer.readNbt();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeNbt(compoundTag);
    }

    public static void handle(SyncBlockNbtPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            Player player = Minecraft.getInstance().player;
            if (player != null && player.containerMenu instanceof DebugToolBlockEditorMenu menu)
            {
                menu.setServerBlockNbt(packet.compoundTag);
            }
        });
        context.get().setPacketHandled(true);
    }
}
