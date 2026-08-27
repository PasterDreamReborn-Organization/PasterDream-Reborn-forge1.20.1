package com.pasterdream.pasterdreammod.network.curio;

import com.pasterdream.pasterdreammod.client.network.ClientPacketHandlers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CurioActivationPacket {

    private final Item item;

    public CurioActivationPacket(Item item) {
        this.item = item;
    }

    public static void encode(CurioActivationPacket message, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(BuiltInRegistries.ITEM.getKey(message.item));
    }

    public static CurioActivationPacket decode(FriendlyByteBuf buffer) {
        Item item = BuiltInRegistries.ITEM.get(buffer.readResourceLocation());
        return new CurioActivationPacket(item);
    }

    public static void handle(CurioActivationPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ClientPacketHandlers.handleCurioActivation(message.item));
        context.get().setPacketHandled(true);
    }
}
