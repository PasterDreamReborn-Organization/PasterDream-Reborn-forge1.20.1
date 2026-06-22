package com.pasterdream.pasterdreammod.network.fluidslot;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.fluidhandler.FluidHandlerResolvers;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class FluidSlotInteractPacket
{
    private final ResourceLocation handlerKey;
    private final byte[] extraData;
    private final boolean fillMode; //true:取出, false:存入

    public FluidSlotInteractPacket(ResourceLocation handlerKey, FriendlyByteBuf extraData, boolean fillMode)
    {
        this.handlerKey = handlerKey;
        byte[] data = new byte[extraData.readableBytes()];
        extraData.getBytes(extraData.readerIndex(), data);
        this.extraData = data;
        this.fillMode = fillMode;
    }

    public static void encode(FluidSlotInteractPacket message, FriendlyByteBuf friendlyByteBuf)
    {
        friendlyByteBuf.writeResourceLocation(message.handlerKey);
        friendlyByteBuf.writeByteArray(message.extraData);
        friendlyByteBuf.writeBoolean(message.fillMode);
    }

    public static FluidSlotInteractPacket decode(FriendlyByteBuf friendlyByteBuf)
    {
        return new FluidSlotInteractPacket(friendlyByteBuf.readResourceLocation(), new FriendlyByteBuf(Unpooled.wrappedBuffer(friendlyByteBuf.readByteArray())), friendlyByteBuf.readBoolean());
    }

    public static void handle(FluidSlotInteractPacket message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        contextSupplier.get().enqueueWork(() ->
        {
            ServerPlayer player = contextSupplier.get().getSender();
            if (player != null)
            {
                Level level = player.level();

                FluidHandlerResolvers.FluidHandlerResolver resolver = FluidHandlerResolvers.get(message.handlerKey);
                if (resolver == null)
                {
                    return;
                }

                FriendlyByteBuf dataBuf = new FriendlyByteBuf(Unpooled.wrappedBuffer(message.extraData));
                dataBuf.resetReaderIndex();
                IFluidHandler handler = resolver.resolve(level, dataBuf);
                if (handler == null)
                {
                    return;
                }

                ItemStack fluidContainer = player.containerMenu.getCarried();
                if (fluidContainer.isEmpty())
                {
                    return;
                }

                //记录原流体（用于音效）
                ResourceLocation fluidId = null;
                if (message.fillMode)
                {
                    FluidStack current = handler.getFluidInTank(0);
                    if (!current.isEmpty())
                    {
                        fluidId = ForgeRegistries.FLUIDS.getKey(current.getFluid());
                    }
                }
                    else
                    {
                        ItemStack single = fluidContainer.copy();
                        single.setCount(1);
                        fluidId = FluidUtil.getFluidHandler(single).map(fluidHandlerItem ->
                        {
                            FluidStack fluidStack = fluidHandlerItem.getFluidInTank(0);
                            return fluidStack.isEmpty() ? null : ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid());
                        }).orElse(null);
                    }

                FluidActionResult result;
                if (message.fillMode)
                {
                    result = FluidUtil.tryFillContainer(fluidContainer, handler, Integer.MAX_VALUE, null, true);
                }
                    else
                    {
                        result = FluidUtil.tryEmptyContainer(fluidContainer, handler, Integer.MAX_VALUE, null, true);
                    }
                if (result.isSuccess())
                {
                    final ItemStack emptyFluidContainer = result.getResult();
                    int fluidContainerCount = fluidContainer.getCount();
                    if(fluidContainerCount > 1)
                    {
                        fluidContainer.shrink(1);
                        contextSupplier.get().enqueueWork(() -> player.containerMenu.setCarried(fluidContainer));
                        if (!player.getInventory().add(emptyFluidContainer))
                        {
                            player.drop(emptyFluidContainer, false);
                        }
                    }
                    else
                        if (fluidContainerCount == 1)
                        {
                            contextSupplier.get().enqueueWork(() -> player.containerMenu.setCarried(emptyFluidContainer));
                        }

                    //通知更新
                    if (message.handlerKey.equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "block_entity")))
                    {
                        FriendlyByteBuf updateBuffer = new FriendlyByteBuf(Unpooled.wrappedBuffer(message.extraData));
                        updateBuffer.resetReaderIndex();
                        BlockPos blockPosition = updateBuffer.readBlockPos();
                        BlockEntity blockEntity = level.getBlockEntity(blockPosition);
                        if (blockEntity != null)
                        {
                            blockEntity.setChanged();
                            level.sendBlockUpdated(blockPosition, level.getBlockState(blockPosition), level.getBlockState(blockPosition), 3);
                        }
                    }
                    else
                        if(message.handlerKey.equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "item")))
                        {
                            //此处暂时留空
                        }

                    //发送音效包
                    if (fluidId != null)
                    {
                        ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new FluidSoundPacket(fluidId, message.fillMode));
                    }
                }
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
