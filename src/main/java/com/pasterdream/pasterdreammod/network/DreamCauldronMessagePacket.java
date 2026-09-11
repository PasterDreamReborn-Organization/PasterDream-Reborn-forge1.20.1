package com.pasterdream.pasterdreammod.network;

import com.pasterdream.pasterdreammod.client.network.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 法术工厂（融梦釜）提示信息：服务端结算后发送给触发操作的玩家。
 * 客户端若正打开融梦釜界面，则在物品槽上方渲染该文本；否则回落到动作栏。
 */
public class DreamCauldronMessagePacket
{
    private final Component message;

    public DreamCauldronMessagePacket(Component message)
    {
        this.message = message;
    }

    public static void encode(DreamCauldronMessagePacket message, FriendlyByteBuf buffer)
    {
        buffer.writeComponent(message.message);
    }

    public static DreamCauldronMessagePacket decode(FriendlyByteBuf buffer)
    {
        return new DreamCauldronMessagePacket(buffer.readComponent());
    }

    public static void handle(DreamCauldronMessagePacket message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() ->
        {
            if (context.getDirection().getReceptionSide().isClient())
            {
                ClientPacketHandlers.handleDreamCauldronMessage(message.message);
            }
        });
        context.setPacketHandled(true);
    }
}
