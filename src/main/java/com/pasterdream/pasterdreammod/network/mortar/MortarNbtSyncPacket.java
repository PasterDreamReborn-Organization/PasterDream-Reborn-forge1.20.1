package com.pasterdream.pasterdreammod.network.mortar;

import com.pasterdream.pasterdreammod.world.item.mortar.MortarItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MortarNbtSyncPacket
{
    private final CompoundTag nbt;

    public MortarNbtSyncPacket(CompoundTag nbt)
    {
        this.nbt = nbt;
    }

    public static void encode(MortarNbtSyncPacket msg, FriendlyByteBuf buf)
    {
        buf.writeNbt(msg.nbt);
    }

    public static MortarNbtSyncPacket decode(FriendlyByteBuf buf)
    {
        return new MortarNbtSyncPacket(buf.readNbt());
    }

    public static void handle(MortarNbtSyncPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            if (ctx.get().getDirection().getReceptionSide().isClient())
            {
                var player = net.minecraft.client.Minecraft.getInstance().player;
                if (player != null)
                {
                    var stack = player.getMainHandItem();
                    if (stack.getItem() instanceof MortarItem)
                    {
                        stack.setTag(msg.nbt);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}