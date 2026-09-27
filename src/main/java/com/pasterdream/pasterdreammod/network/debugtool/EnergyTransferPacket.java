package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.helper.energycalculator.EnergyCalculator;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.EnergyStorageLaunchData;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.IDebugItemHandlerAndFluidHandlerAndEnergyStorageEditorMenu;
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
    private final int menuId;
    private final int amount;

    public EnergyTransferPacket(int menuId, int amount)
    {
        this.menuId = menuId;
        this.amount = amount;
    }

    public EnergyTransferPacket(FriendlyByteBuf buffer)
    {
        this.menuId = buffer.readVarInt();
        this.amount = buffer.readVarInt();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(menuId);
        buffer.writeVarInt(amount);
    }

    public static void handle(EnergyTransferPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if (player != null && player.containerMenu.containerId == packet.menuId && player.containerMenu instanceof IDebugItemHandlerAndFluidHandlerAndEnergyStorageEditorMenu menu && packet.amount != 0)
            {
                EnergyStorageLaunchData external = menu.provideEnergyStorageExternal();
                EnergyStorageLaunchData target = menu.provideEnergyStorageTarget();
                if (external != null && target != null)
                {
                    IEnergyStorage disCharge;
                    IEnergyStorage charge;
                    int amount;

                    if (packet.amount > 0)
                    {
                        disCharge = external.handler();
                        charge = target.handler();
                        amount = packet.amount;;
                    }
                        else
                        {
                            disCharge = target.handler();
                            charge = external.handler();
                            amount = -packet.amount;
                        }

                    EnergyCalculator.calculate(disCharge, charge, amount);
                    external.onChanged().run();
                    target.onChanged().run();
                    player.containerMenu.broadcastChanges();
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
