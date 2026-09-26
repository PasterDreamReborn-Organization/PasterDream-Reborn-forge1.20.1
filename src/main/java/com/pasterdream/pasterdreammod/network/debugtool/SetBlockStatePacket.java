package com.pasterdream.pasterdreammod.network.debugtool;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class SetBlockStatePacket
{
    private final BlockPos blockPosition;
    private final String stateString;

    public SetBlockStatePacket(BlockPos blockPosition, String stateString)
    {
        this.blockPosition = blockPosition;
        this.stateString = stateString;
    }

    public SetBlockStatePacket(FriendlyByteBuf buffer)
    {
        this.blockPosition = buffer.readBlockPos();
        this.stateString = buffer.readUtf();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(blockPosition);
        buffer.writeUtf(stateString);
    }

    public static void handle(SetBlockStatePacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();

            if(player != null)
            {
                ServerLevel level = player.serverLevel();
                try
                {
                    BlockStateParser.BlockResult result = BlockStateParser.parseForBlock(level.holderLookup(Registries.BLOCK), packet.stateString, false);
                    level.setBlock(packet.blockPosition, result.blockState(), 3);
                    ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SyncBlockStatePacket(BlockStateParser.serialize(result.blockState())));
                }
                    catch (CommandSyntaxException e)
                    {

                    }
            }
        });
        context.get().setPacketHandled(true);
    }
}
