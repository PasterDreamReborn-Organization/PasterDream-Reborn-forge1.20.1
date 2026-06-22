package com.pasterdream.pasterdreammod.network.fluidslot;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FluidSyncPacket
{
    private final int containerId;
    private final FluidStack[] fluids;

    public FluidSyncPacket(int containerId, FluidStack[] fluids)
    {
        this.containerId = containerId;
        this.fluids = fluids;
    }

    public static void encode(FluidSyncPacket message, FriendlyByteBuf buffer)
    {
        buffer.writeInt(message.containerId);
        buffer.writeVarInt(message.fluids.length);
        for (FluidStack stack : message.fluids)
        {
            buffer.writeFluidStack(stack);
        }
    }

    public static FluidSyncPacket decode(FriendlyByteBuf buffer)
    {
        int id = buffer.readInt();
        int count = buffer.readVarInt();
        FluidStack[] fluids = new FluidStack[count];
        for (int i = 0; i < count; i++)
        {
            fluids[i] = buffer.readFluidStack();
        }
        return new FluidSyncPacket(id, fluids);
    }

    public static void handle(FluidSyncPacket message, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            Player player = Minecraft.getInstance().player;
            if (player != null && player.containerMenu instanceof AbstractContainerMenuWithFluidSlot menu && menu.containerId == message.containerId)
            {
                menu.syncFluidsFromClient(message.fluids);
            }
        });
        context.get().setPacketHandled(true);
    }
}
