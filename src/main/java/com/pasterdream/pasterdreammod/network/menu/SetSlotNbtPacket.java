package com.pasterdream.pasterdreammod.network.menu;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SetSlotNbtPacket
{
    private final int slotIndex;
    @Nullable
    private final CompoundTag tag;

    public SetSlotNbtPacket(int slotIndex, @Nullable CompoundTag tag)
    {
        this.slotIndex = slotIndex;
        this.tag = tag;
    }

    public SetSlotNbtPacket(FriendlyByteBuf buf)
    {
        this.slotIndex = buf.readVarInt();
        this.tag = buf.readNbt();
    }

    public void encode(FriendlyByteBuf buf)
    {
        buf.writeVarInt(slotIndex);
        buf.writeNbt(tag);
    }

    public static void handle(SetSlotNbtPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if(player != null)
            {
                AbstractContainerMenu menu = player.containerMenu;
                if(packet.slotIndex >= 0 && packet.slotIndex < menu.slots.size())
                {
                    Slot slot = menu.getSlot(packet.slotIndex);
                    ItemStack itemStack = slot.getItem();
                    if (!itemStack.isEmpty())
                    {
                        itemStack.setTag(packet.tag == null ? null : packet.tag.copy());
                        slot.set(itemStack);
                        menu.broadcastChanges();
                    }
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
