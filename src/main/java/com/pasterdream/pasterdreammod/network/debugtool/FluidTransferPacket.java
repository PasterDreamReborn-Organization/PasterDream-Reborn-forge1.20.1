package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.fluidslot.FluidSyncPacket;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.FluidHandlerContainerWrapper;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.FluidHandlerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class FluidTransferPacket
{
    private final int menuId;
    private final boolean extract;   //true:方块->物品;false:物品->方块

    public FluidTransferPacket(int menuId, boolean extract)
    {
        this.menuId = menuId;
        this.extract = extract;
    }

    public FluidTransferPacket(FriendlyByteBuf buffer)
    {
        this.menuId = buffer.readVarInt();
        this.extract = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(menuId);
        buffer.writeBoolean(extract);
    }

    public static void handle(FluidTransferPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if(player != null && player.containerMenu.containerId == packet.menuId && player.containerMenu instanceof FluidHandlerMenu menu)
            {
                Slot containerSlot = menu.getSlot(36);
                ItemStack containerItem = containerSlot.getItem();

                if (!containerItem.isEmpty())
                {
                    IFluidHandler targetHandler = menu.getFluidHandler();
                    if (targetHandler != null)
                    {
                        FluidActionResult result = packet.extract ? FluidUtil.tryFillContainer(containerItem, targetHandler, 2147483647, null, true) : FluidUtil.tryEmptyContainer(containerItem, targetHandler, 2147483647, null, true);
                        if (result.isSuccess())
                        {
                            if (containerItem.getCount() == 1)
                            {
                                containerSlot.set(result.getResult());
                            }
                                else
                                {
                                    ItemStack remaining = new ItemStack(containerItem.getItem(), containerItem.getCount() - 1);
                                    remaining.setTag(containerItem.getTag());
                                    containerSlot.set(remaining);

                                    ItemStack newItem = result.getResult();
                                    if (!newItem.isEmpty())
                                    {
                                        if (!player.getInventory().add(newItem))
                                        {
                                            player.drop(newItem, false);
                                        }
                                    }
                                }

                            containerSlot.setChanged();
                            menu.broadcastChanges();

                            if (menu.getOnChanged() != null)
                            {
                                menu.getOnChanged().run();
                            }

                            FluidStack[] allFluids = menu.getFluidSlots().stream().map(slot -> slot.getFluid()).toArray(FluidStack[]::new);
                            ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new FluidSyncPacket(menu.containerId, allFluids));
                        }
                    }
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
