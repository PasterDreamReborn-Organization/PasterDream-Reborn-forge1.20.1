package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.network.FluidSlotInteractPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class FluidPacketHelper
{
    public static FluidSlotInteractPacket createForBlockEntity(BlockPos pos, int tankIndex, boolean fillMode)
    {
        FriendlyByteBuf extra = new FriendlyByteBuf(Unpooled.buffer());
        extra.writeBlockPos(pos);
        extra.writeInt(tankIndex);
        return new FluidSlotInteractPacket(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "block_entity"), extra, fillMode);
    }
}
