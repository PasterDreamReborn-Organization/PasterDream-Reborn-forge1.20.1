package com.pasterdream.pasterdreammod.network;

import com.pasterdream.pasterdreammod.world.item.mortar.MortarItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MortarFluidInteractPacket
{
    private final boolean isInput;
    private final int index;
    private final boolean fillMode;

    public MortarFluidInteractPacket(boolean isInput, int index, boolean fillMode)
    {
        this.isInput = isInput;
        this.index = index;
        this.fillMode = fillMode;   //true:研钵流体槽位->玩家流体容器物品；false:玩家流体容器物品->研钵流体槽位
    }

    public static void encode(MortarFluidInteractPacket message, FriendlyByteBuf buffer)
    {
        buffer.writeBoolean(message.isInput);
        buffer.writeInt(message.index);
        buffer.writeBoolean(message.fillMode);
    }

    public static MortarFluidInteractPacket decode(FriendlyByteBuf buffer)
    {
        return new MortarFluidInteractPacket(buffer.readBoolean(), buffer.readInt(), buffer.readBoolean());
    }

    public static void handle(MortarFluidInteractPacket message, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayer player = context.get().getSender();
            if (player == null)
            {
                return;
            }

            ItemStack mortarItemStack = player.getMainHandItem();
            if (!(mortarItemStack.getItem() instanceof MortarItem))
            {
                mortarItemStack = player.getOffhandItem();
                if (!(mortarItemStack.getItem() instanceof MortarItem))
                {
                    return;
                }
            }

            String key = message.isInput ? "InputFluids" : "OutputFluids";
            ListTag list = mortarItemStack.getOrCreateTag().getList(key, Tag.TAG_LIST);

            if (message.index < 0 || message.index >= list.size())
            {
                System.err.println("message.index = " + message.index);
                System.out.println("list.size() = " + list.size());
                return;
            }

            CompoundTag fluidTag = list.getCompound(message.index);
            FluidStack current = FluidStack.loadFluidStackFromNBT(fluidTag);
            ItemStack heldItem = player.containerMenu.getCarried();
            if (heldItem.isEmpty())
            {
                return;
            }

            IFluidHandler handler = new FluidHandlerWrapper(list, message.index, mortarItemStack, key);
            FluidActionResult result;
            if (message.fillMode)
            {
                result = FluidUtil.tryFillContainer(heldItem, handler, Integer.MAX_VALUE, null, true);
            }
                else
                {
                    result = FluidUtil.tryEmptyContainer(heldItem, handler, Integer.MAX_VALUE, null, true);
                }

            if (result.isSuccess())
            {
                player.containerMenu.setCarried(result.getResult());
            }
        });
        context.get().setPacketHandled(true);
    }

    private static class FluidHandlerWrapper implements IFluidHandler
    {
        private final ListTag list;
        private final int index;
        private final ItemStack container;
        private final String key;

        public FluidHandlerWrapper(ListTag list, int index, ItemStack container, String key)
        {
            this.list = list;
            this.index = index;
            this.container = container;
            this.key = key;
        }

        private FluidStack getFluid()
        {
            CompoundTag tag = list.getCompound(index);
            return FluidStack.loadFluidStackFromNBT(tag);
        }

        private void setFluid(FluidStack fluid)
        {
            CompoundTag tag = list.getCompound(index);
            if (fluid.isEmpty())
            {
                list.set(index, new CompoundTag());
            }
                else
                {
                    fluid.writeToNBT(tag);
                }
            container.getOrCreateTag().put(key, list);
        }

        @Override
        public int getTanks()
        {
            return 1;
        }

        @Override
        public FluidStack getFluidInTank(int tank)
        {
            return getFluid();
        }

        @Override
        public int getTankCapacity(int tank)
        {
            return 1000;
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack)
        {
            return true;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action)
        {
            if (resource.isEmpty())
            {
                return 0;
            }

            FluidStack currentFluidStack = getFluid();

            int amount = 1000 - currentFluidStack.getAmount();
            if (amount <= 0)
            {
                return 0;
            }

            if (action.execute())
            {
                if (currentFluidStack.isEmpty())
                {
                    currentFluidStack = resource.copy();
                    currentFluidStack.setAmount(amount);
                }
                else
                    if (currentFluidStack.isFluidEqual(resource))
                    {
                        currentFluidStack.grow(amount);
                    }
                        else
                        {
                            return 0;
                        }
                setFluid(currentFluidStack);
            }
            return amount;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action)
        {
            FluidStack current = getFluid();
            if (current.isEmpty() || !current.isFluidEqual(resource))
            {
                return FluidStack.EMPTY;
            }
            int amount = Math.min(resource.getAmount(), current.getAmount());
            FluidStack drained = current.copy();
            drained.setAmount(amount);
            if (action.execute())
            {
                current.shrink(amount);
                setFluid(current);
            }
            return drained;
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action)
        {
            FluidStack current = getFluid();
            if (current.isEmpty() || maxDrain <= 0)
            {
                return FluidStack.EMPTY;
            }

            int amount = Math.min(maxDrain, current.getAmount());
            FluidStack drained = current.copy();
            drained.setAmount(amount);
            if (action.execute())
            {
                current.shrink(amount);
                setFluid(current);
            }
            return drained;
        }
    }
}
