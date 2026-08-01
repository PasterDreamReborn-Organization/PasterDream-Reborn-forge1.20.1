package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.network.*;
import com.pasterdream.pasterdreammod.network.animationstatechange.AnimationStateChangePacket;
import com.pasterdream.pasterdreammod.network.debugsword.DebugBlockActionPacket;
import com.pasterdream.pasterdreammod.network.fluidslot.FluidSlotInteractPacket;
import com.pasterdream.pasterdreammod.network.fluidslot.FluidSoundPacket;
import com.pasterdream.pasterdreammod.network.fluidslot.FluidSyncPacket;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.IsNotNeedSyncPacket;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MaxMeltDreamEnergySyncPacket;
import com.pasterdream.pasterdreammod.network.meltdreamenergy.MeltDreamEnergySyncPacket;
import com.pasterdream.pasterdreammod.network.mortar.MortarCraftPacket;
import com.pasterdream.pasterdreammod.network.mortar.MortarNbtSyncPacket;
import com.pasterdream.pasterdreammod.network.researchtable.ResearchTableCopyPacket;
import com.pasterdream.pasterdreammod.network.researchtable.ResearchTableResearchPacket;
import com.pasterdream.pasterdreammod.network.curio.CurioActivationPacket;
import com.pasterdream.pasterdreammod.network.curio.KaichuOmamoriActivationPacket;
import com.pasterdream.pasterdreammod.network.skill.BlinkPacket;
import com.pasterdream.pasterdreammod.network.skill.TerraBladeSwingPacket;
import com.pasterdream.pasterdreammod.network.san.IsSanEnableSyncPacket;
import com.pasterdream.pasterdreammod.network.san.LowSanConfigSyncPacket;
import com.pasterdream.pasterdreammod.network.san.MaxSanSyncPacket;
import com.pasterdream.pasterdreammod.network.san.SanSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetwork
{
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int id = 0;

    public static void register()
    {
        CHANNEL.registerMessage(id++, FluidSyncPacket.class, FluidSyncPacket::encode, FluidSyncPacket::decode, FluidSyncPacket::handle);
        CHANNEL.registerMessage(id++, FluidSlotInteractPacket.class, FluidSlotInteractPacket::encode, FluidSlotInteractPacket::decode, FluidSlotInteractPacket::handle);
        CHANNEL.registerMessage(id++, FluidSoundPacket.class, FluidSoundPacket::encode, FluidSoundPacket::decode, FluidSoundPacket::handle);

        CHANNEL.registerMessage(id++, DreamCauldronCraftPacket.class, DreamCauldronCraftPacket::encode, DreamCauldronCraftPacket::decode, DreamCauldronCraftPacket::handle);
        CHANNEL.registerMessage(id++, MortarNbtSyncPacket.class, MortarNbtSyncPacket::encode, MortarNbtSyncPacket::decode, MortarNbtSyncPacket::handle);
        CHANNEL.registerMessage(id++, MortarCraftPacket.class, MortarCraftPacket::encode, MortarCraftPacket::decode, MortarCraftPacket::handle);
        CHANNEL.registerMessage(id++, ResearchTableCopyPacket.class, ResearchTableCopyPacket::encode, ResearchTableCopyPacket::decode, ResearchTableCopyPacket::handle);
        CHANNEL.registerMessage(id++, ResearchTableResearchPacket.class, ResearchTableResearchPacket::encode, ResearchTableResearchPacket::decode, ResearchTableResearchPacket::handle);

        CHANNEL.registerMessage(id++, DebugBlockActionPacket.class, DebugBlockActionPacket::encode, DebugBlockActionPacket::decode, DebugBlockActionPacket::handle);

        CHANNEL.registerMessage(id++, MeltDreamEnergySyncPacket.class, MeltDreamEnergySyncPacket::encode, MeltDreamEnergySyncPacket::decode, MeltDreamEnergySyncPacket::handle);
        CHANNEL.registerMessage(id++, IsNotNeedSyncPacket.class, IsNotNeedSyncPacket::encode, IsNotNeedSyncPacket::decode, IsNotNeedSyncPacket::handle);
        CHANNEL.registerMessage(id++, MaxMeltDreamEnergySyncPacket.class, MaxMeltDreamEnergySyncPacket::encode, MaxMeltDreamEnergySyncPacket::decode, MaxMeltDreamEnergySyncPacket::handle);

        CHANNEL.registerMessage(id++, SanSyncPacket.class, SanSyncPacket::encode, SanSyncPacket::decode, SanSyncPacket::handle);
        CHANNEL.registerMessage(id++, IsSanEnableSyncPacket.class, IsSanEnableSyncPacket::encode, IsSanEnableSyncPacket::decode, IsSanEnableSyncPacket::handle);
        CHANNEL.registerMessage(id++, MaxSanSyncPacket.class, MaxSanSyncPacket::encode, MaxSanSyncPacket::decode, MaxSanSyncPacket::handle);
        CHANNEL.registerMessage(id++, LowSanConfigSyncPacket.class, LowSanConfigSyncPacket::encode, LowSanConfigSyncPacket::decode, LowSanConfigSyncPacket::handle);

        CHANNEL.registerMessage(id++, AnimationStateChangePacket.class, AnimationStateChangePacket::encode, AnimationStateChangePacket::decode, AnimationStateChangePacket::handle);

        CHANNEL.registerMessage(id++, BlinkPacket.class, BlinkPacket::encode, BlinkPacket::decode, BlinkPacket::handle);

        CHANNEL.registerMessage(id++, TerraBladeSwingPacket.class, TerraBladeSwingPacket::encode, TerraBladeSwingPacket::decode, TerraBladeSwingPacket::handle);

        CHANNEL.registerMessage(id++, CurioActivationPacket.class, CurioActivationPacket::encode, CurioActivationPacket::decode, CurioActivationPacket::handle);
        CHANNEL.registerMessage(id++, KaichuOmamoriActivationPacket.class, KaichuOmamoriActivationPacket::encode, KaichuOmamoriActivationPacket::decode, KaichuOmamoriActivationPacket::handle);
    }

    public static void sendMeltDreamEnergySyncPacketToPlayer(MeltDreamEnergySyncPacket packet, ServerPlayer player)
    {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendMaxMeltDreamEnergySyncPacketToPlayer(MaxMeltDreamEnergySyncPacket packet, ServerPlayer player)
    {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendSanSyncPacketToPlayer(SanSyncPacket packet, ServerPlayer player)
    {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendMaxSanSyncPacketToPlayer(MaxSanSyncPacket packet, ServerPlayer player)
    {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendIsSanEnableSyncPacketToPlayer(IsSanEnableSyncPacket packet, ServerPlayer player)
    {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }
}
