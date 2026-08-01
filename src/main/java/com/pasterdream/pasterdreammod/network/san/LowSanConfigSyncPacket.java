package com.pasterdream.pasterdreammod.network.san;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class LowSanConfigSyncPacket {

    private final boolean overlay;
    private final boolean jitter;
    private final boolean sound;

    public LowSanConfigSyncPacket(boolean overlay, boolean jitter, boolean sound) {
        this.overlay = overlay;
        this.jitter = jitter;
        this.sound = sound;
    }

    public static void encode(LowSanConfigSyncPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBoolean(packet.overlay);
        buffer.writeBoolean(packet.jitter);
        buffer.writeBoolean(packet.sound);
    }

    public static LowSanConfigSyncPacket decode(FriendlyByteBuf buffer) {
        return new LowSanConfigSyncPacket(buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean());
    }

    public static void handle(LowSanConfigSyncPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Config.lowSanOverlay = packet.overlay;
            Config.lowSanJitter = packet.jitter;
            Config.lowSanSound = packet.sound;
        });
        context.get().setPacketHandled(true);
    }

    /** 向所有玩家同步当前值 */
    public static void syncToAll(boolean overlay, boolean jitter, boolean sound) {
        ModNetwork.CHANNEL.send(PacketDistributor.ALL.noArg(),
                new LowSanConfigSyncPacket(overlay, jitter, sound));
    }

    /** 向单个玩家同步当前值 */
    public static void syncToPlayer(ServerPlayer player, boolean overlay, boolean jitter, boolean sound) {
        ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                new LowSanConfigSyncPacket(overlay, jitter, sound));
    }
}
