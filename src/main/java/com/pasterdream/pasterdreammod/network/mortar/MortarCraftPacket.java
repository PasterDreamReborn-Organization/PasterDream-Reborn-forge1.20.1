package com.pasterdream.pasterdreammod.network.mortar;

import com.pasterdream.pasterdreammod.world.item.mortar.MortarMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MortarCraftPacket
{
    public MortarCraftPacket() {}

    public static void encode(MortarCraftPacket msg, FriendlyByteBuf buf)
    {

    }

    public static MortarCraftPacket decode(FriendlyByteBuf buf)
    {
        return new MortarCraftPacket();
    }

    public static void handle(MortarCraftPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            ServerPlayer player = ctx.get().getSender();
            if (player != null)
            {
                AbstractContainerMenu menu = player.containerMenu;
                if (menu instanceof MortarMenu mortarMenu)
                {
                    mortarMenu.craft();
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
