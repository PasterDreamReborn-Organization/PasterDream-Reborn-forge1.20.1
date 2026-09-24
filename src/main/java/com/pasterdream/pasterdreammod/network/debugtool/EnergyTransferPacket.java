package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.helper.energycalculator.EnergyCalculator;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergyTransferPacket
{
    private final int fromSlotIndex;
    private final int toSlotIndex;
    private final int amount;

    public EnergyTransferPacket(int fromSlotIndex, int toSlotIndex, int amount)
    {
        this.fromSlotIndex = fromSlotIndex;
        this.toSlotIndex = toSlotIndex;
        this.amount = amount;
    }

    public EnergyTransferPacket(FriendlyByteBuf buffer)
    {
        this.fromSlotIndex = buffer.readVarInt();
        this.toSlotIndex = buffer.readVarInt();
        this.amount = buffer.readVarInt();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(fromSlotIndex);
        buffer.writeVarInt(toSlotIndex);
        buffer.writeVarInt(amount);
    }

    public static void handle(EnergyTransferPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if(player != null)
            {
                AbstractContainerMenu menu = player.containerMenu;

                if(packet.amount != 0)
                {
                    Slot dischargeItemSlot;
                    Slot chargeItemSlot;

                    if(packet.amount > 0)
                    {
                        dischargeItemSlot = menu.getSlot(packet.toSlotIndex);
                        chargeItemSlot = menu.getSlot(packet.fromSlotIndex);
                    }
                        else
                        {
                            dischargeItemSlot = menu.getSlot(packet.fromSlotIndex);
                            chargeItemSlot = menu.getSlot(packet.toSlotIndex);
                        }

                    IEnergyStorage discharge = dischargeItemSlot.getItem().getCapability(ForgeCapabilities.ENERGY).resolve().orElse(null);
                    IEnergyStorage charge = chargeItemSlot.getItem().getCapability(ForgeCapabilities.ENERGY).resolve().orElse(null);

                    if (discharge != null && charge != null)
                    {
                        EnergyCalculator.calculate(discharge, charge, packet.amount);
                        dischargeItemSlot.set(dischargeItemSlot.getItem());
                        chargeItemSlot.set(chargeItemSlot.getItem());
                        menu.broadcastChanges();
                    }
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
