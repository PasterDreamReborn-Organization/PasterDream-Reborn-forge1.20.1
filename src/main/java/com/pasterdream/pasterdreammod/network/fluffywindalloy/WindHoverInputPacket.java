package com.pasterdream.pasterdreammod.network.fluffywindalloy;

import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 客户端跳跃键（空格）状态同步：供萦风合金盔甲「滞空」在服务端判定。
 * 1.20.1 服务端仅在骑乘时收到跳跃输入，故单独同步。
 */
public class WindHoverInputPacket {

    private final boolean jumping;

    public WindHoverInputPacket(boolean jumping) {
        this.jumping = jumping;
    }

    public static void encode(WindHoverInputPacket message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.jumping);
    }

    public static WindHoverInputPacket decode(FriendlyByteBuf buffer) {
        return new WindHoverInputPacket(buffer.readBoolean());
    }

    public static void handle(WindHoverInputPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player != null) {
                player.getPersistentData().putBoolean(FluffyWindAlloyArmorHandler.NBT_HOVER_JUMP, message.jumping);
            }
        });
        context.get().setPacketHandled(true);
    }
}
