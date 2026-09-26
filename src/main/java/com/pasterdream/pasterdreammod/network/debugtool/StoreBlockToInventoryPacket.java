package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.helper.nbthelper.WrappedNBTBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StoreBlockToInventoryPacket
{
    private final BlockPos blockPosition;

    public StoreBlockToInventoryPacket(BlockPos blockPosition)
    {
        this.blockPosition = blockPosition;
    }

    public StoreBlockToInventoryPacket(FriendlyByteBuf buffer)
    {
        this.blockPosition = buffer.readBlockPos();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(blockPosition);
    }

    public static void handle(StoreBlockToInventoryPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if(player != null)
            {
                ServerLevel level = player.serverLevel();
                BlockState state = level.getBlockState(packet.blockPosition);
                Block block = state.getBlock();
                Item blockItem = block.asItem();
                if (blockItem != Items.AIR)
                {
                    ItemStack stack = new ItemStack(blockItem);
                    BlockEntity blockEntity = level.getBlockEntity(packet.blockPosition);
                    if (blockEntity != null)
                    {
                        CompoundTag NBT = blockEntity.saveWithId();
                        if (!NBT.isEmpty())
                        {
                            stack.setTag(WrappedNBTBlockItem.wrapper(NBT));
                        }
                    }

                    if (!player.getInventory().add(stack))
                    {
                        player.drop(stack, false);
                    }
                }

                level.removeBlock(packet.blockPosition, false);
                player.closeContainer();
            }
        });
        context.get().setPacketHandled(true);
    }
}
