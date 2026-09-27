package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolEntityEditorMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncEntityNbtPacket
{
    private final CompoundTag NBT;

    public SyncEntityNbtPacket(CompoundTag NBT)
    {
        this.NBT = NBT;
    }

    public SyncEntityNbtPacket(FriendlyByteBuf buffer)
    {
        this.NBT = buffer.readNbt();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeNbt(NBT);
    }

    public static void handle(SyncEntityNbtPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            Player player = Minecraft.getInstance().player;
            if (player != null && player.containerMenu instanceof DebugToolEntityEditorMenu menu)
            {
                menu.setServerEntityNbt(packet.NBT);
            }
        });
        context.get().setPacketHandled(true);
    }
}
